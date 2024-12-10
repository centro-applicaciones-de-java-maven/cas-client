package org.guanzon.cas.client.model;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.rowset.CachedRowSet;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.appdriver.iface.GEntity;
import org.guanzon.cas.parameter.model.Model_Country;
import org.guanzon.cas.parameter.model.Model_TownCity;
import org.json.simple.JSONObject;

public class Model_Client_Master implements GEntity{
    LogWrapper logwrapr = new LogWrapper("Model_Client_Master", "cas-error.log");
    
    private final String XML = "Model_Client_Master.xml";
    
    GRider poGRider;                //application driver
    CachedRowSet poEntity;          //rowset
    JSONObject poJSON;              //json container
    int pnEditMode;                 //edit mode
    
    Model_TownCity poTownCity;
    Model_Country poCountry;
    
    public Model_Client_Master(GRider value) {
        if (value == null) {
            System.err.println("Application Driver is not set.");
            System.exit(1);
        }

        poGRider = value;
        
        poTownCity = new Model_TownCity(poGRider);
        poCountry = new Model_Country(poGRider);
        
        initialize();
    }

    @Override
    public String getColumn(int columnIndex) {
        try {
            return poEntity.getMetaData().getColumnLabel(columnIndex);
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
        }
        return "";
    }

    @Override
    public int getColumn(String columnName) {
        try {
            return MiscUtil.getColumnIndex(poEntity, columnName);
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
        }
        return -1;
    }

    @Override
    public int getColumnCount() {
        try {
            return poEntity.getMetaData().getColumnCount();
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
        }

        return -1;
    }

    @Override
    public int getEditMode() {
        return pnEditMode;
    }

    @Override
    public String getTable() {
        return "Client_Master";
    }

    @Override
    public Object getValue(int columnIndex) {
        try {
            return poEntity.getObject(columnIndex);
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
        }
        return null;
    }

    @Override
    public Object getValue(String columnName) {
        try {
            return poEntity.getObject(MiscUtil.getColumnIndex(poEntity, columnName));
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
        }
        return null;
    }

    @Override
    public JSONObject setValue(int columnIndex, Object value) {
        try {
            poJSON = MiscUtil.validateColumnValue(System.getProperty("sys.default.path.metadata") + XML, MiscUtil.getColumnLabel(poEntity, columnIndex), value);
            if ("error".equals((String) poJSON.get("result"))) {
                return poJSON;
            }

            poEntity.updateObject(columnIndex, value);
            poEntity.updateRow();

            poJSON = new JSONObject();
            poJSON.put("result", "success");
            poJSON.put("value", getValue(columnIndex));
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
        }

        return poJSON;
    }

    @Override
    public JSONObject setValue(String colunmName, Object value) {
        poJSON = new JSONObject();

        try {
            return setValue(MiscUtil.getColumnIndex(poEntity, colunmName), value);
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
        }
        return poJSON;
    }

    @Override
    public JSONObject newRecord() {
        pnEditMode = EditMode.ADDNEW;

        //replace with the primary key column info
        setClientId(MiscUtil.getNextCode(getTable(), "sClientID", true, poGRider.getConnection(), ""));

        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }

    @Override
    public JSONObject openRecord(String barangayId) {
        poJSON = new JSONObject();

        String lsSQL = MiscUtil.makeSelect(this);

        //replace the condition based on the primary key column of the record
        lsSQL = MiscUtil.addCondition(lsSQL, "sClientID = " + SQLUtil.toSQL(barangayId));

        ResultSet loRS = poGRider.executeQuery(lsSQL);

        try {
            if (loRS.next()) {
                for (int lnCtr = 1; lnCtr <= loRS.getMetaData().getColumnCount(); lnCtr++) {
                    setValue(lnCtr, loRS.getObject(lnCtr));
                }
                
                MiscUtil.close(loRS);
                
                //connect to other table
                poJSON = poTownCity.openRecord((String) getValue("sBirthPlc"));
                if (!((String)poJSON.get("result")).equals("success")) 
                    System.err.println("TownCity.openRecord: " + poJSON.toJSONString());
                
                poJSON = poCountry.openRecord((String) getValue("sCitizenx"));
                if (!((String)poJSON.get("result")).equals("success")) 
                    System.err.println("Country.openRecord: " + poJSON.toJSONString());
                
                pnEditMode = EditMode.READY;

                poJSON = new JSONObject();
                poJSON.put("result", "success");
                poJSON.put("message", "Record loaded successfully.");
            } else {
                poJSON = new JSONObject();
                poJSON.put("result", "error");
                poJSON.put("message", "No record to load.");
            }
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
        }
        
        if (((String) poJSON.get("result")).equals("success")) pnEditMode = EditMode.READY;

        return poJSON;
    }

    @Override
    public JSONObject updateRecord() {
        poJSON = new JSONObject();
        
        if (pnEditMode != EditMode.READY){
            poJSON.put("result", "error");
            poJSON.put("message", "No record was loaded.");
        } else {
            pnEditMode = EditMode.UPDATE;
            poJSON.put("result", "success");
        }
        
        return poJSON;
    }
    
    @Override
    public JSONObject saveRecord() {
        poJSON = new JSONObject();

        if (pnEditMode == EditMode.ADDNEW || pnEditMode == EditMode.UPDATE) {
            String lsSQL;
            
            setModifyingId(poGRider.getUserID());
            setModifiedDate(poGRider.getServerDate());
            
            if (pnEditMode == EditMode.ADDNEW) {
                //replace with the primary key column info
                setClientId(MiscUtil.getNextCode(getTable(), "sClientID", true, poGRider.getConnection(), ""));

                lsSQL = MiscUtil.makeSQL(this);

                if (!lsSQL.isEmpty()) {
                    if (poGRider.executeQuery(lsSQL, getTable(), poGRider.getBranchCode(), "") > 0) {
                        poJSON = new JSONObject();
                        poJSON.put("result", "success");
                        poJSON.put("message", "Record saved successfully.");
                    } else {
                        poJSON = new JSONObject();
                        poJSON.put("result", "error");
                        poJSON.put("message", poGRider.getErrMsg());
                    }
                } else {
                    poJSON = new JSONObject();
                    poJSON.put("result", "error");
                    poJSON.put("message", "No record to save.");
                }
            } else {
                Model_Client_Master loOldEntity = new Model_Client_Master(poGRider);

                //replace with the primary key column info
                poJSON = loOldEntity.openRecord(this.getClientId());

                if ("success".equals((String) poJSON.get("result"))) {
                    //replace the condition based on the primary key column of the record
                    lsSQL = MiscUtil.makeSQL(this, loOldEntity, "sClientID = " + SQLUtil.toSQL(this.getClientId()));

                    if (!lsSQL.isEmpty()) {
                        if (poGRider.executeQuery(lsSQL, getTable(), poGRider.getBranchCode(), "") > 0) {
                            poJSON = new JSONObject();
                            poJSON.put("result", "success");
                            poJSON.put("message", "Record saved successfully.");
                        } else {
                            poJSON = new JSONObject();
                            poJSON.put("result", "error");
                            poJSON.put("message", poGRider.getErrMsg());
                        }
                    } else {
                        poJSON = new JSONObject();
                        poJSON.put("result", "success");
                        poJSON.put("message", "No updates has been made.");
                    }
                } else {
                    poJSON = new JSONObject();
                    poJSON.put("result", "error");
                    poJSON.put("message", "Record discrepancy. Unable to save record.");
                }
            }
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "Invalid update mode. Unable to save record.");
            return poJSON;
        }
        
        if (((String) poJSON.get("result")).equals("success")) openRecord((String) getValue(0));

        return poJSON;
    }
    
    public JSONObject setClientId(String clientId){
        return setValue("sClientID", clientId);
    }

    public String getClientId(){
        return (String) getValue("sClientID");
    }
    
    public JSONObject setClientType(String clientType){
        return setValue("cClientTp", clientType);
    }

    public String getClientType(){
        return (String) getValue("cClientTp");
    }
    
    public JSONObject setLastName(String lastName){
        return setValue("sLastName", lastName);
    }

    public String getLastName(){
        return (String) getValue("sLastName");
    }
    
    public JSONObject setFirstName(String firstName){
        return setValue("sFrstName", firstName);
    }

    public String getFirstName(){
        return (String) getValue("sFrstName");
    }
    
    public JSONObject setMiddleName(String middleName){
        return setValue("sMiddName", middleName);
    }

    public String getMiddleName(){
        return (String) getValue("sMiddName");
    }
    
    public JSONObject setSuffixName(String suffixName){
        return setValue("sSuffixNm", suffixName);
    }

    public String getSuffixName(){
        return (String) getValue("sSuffixNm");
    }
    
    public JSONObject setMothersMaidenName(String mothersMaidenName){
        return setValue("sMaidenNm", mothersMaidenName);
    }

    public String getMothersMaidenName(){
        return (String) getValue("sMaidenNm");
    }
    
    public JSONObject setFullName(String fullName){
        return setValue("sCompnyNm", fullName);
    }

    public String getFullName(){
        return (String) getValue("sCompnyNm");
    }
    
    public JSONObject setGender(String genderCode){
        return setValue("cGenderCd", genderCode);
    }

    public String getGender(){
        return (String) getValue("cGenderCd");
    }
    
    public JSONObject setCitizenshipId(String citizenshipId){
        setValue("sCitizenx", citizenshipId);
        poCountry.openRecord((String) getValue("sCitizenx"));
        
        return poJSON;
    }

    public String getCitizenshipId(){
        return (String) getValue("sCitizenx");
    }
    
    public String getCitizenshipName(){
        if (poCountry.getEditMode() == EditMode.UPDATE)
            return poCountry.getCountryName();
        else
            return "";
    }
    
    public JSONObject setBirthDate(Date birthDate){
        return setValue("dBirthDte", birthDate);
    }

    public Date getBirthDate(){
        return (Date) getValue("dBirthDte");
    }
    
    public JSONObject setBirthPlaceId(String birthPlaceId){
        setValue("sBirthPlc", birthPlaceId);
        poTownCity.openRecord((String) getValue("sBirthPlc"));
        
        return poJSON;
    }

    public String getBirthPlaceId(){
        return (String) getValue("sBirthPlc");
    }
    
    public String getBirthPlaceTownName(){
        if (poTownCity.getEditMode() == EditMode.UPDATE)
            return poTownCity.getTownName();
        else
            return "";
    }
    
    public String getBirthPlaceProvinceId(){
        if (poTownCity.getEditMode() == EditMode.UPDATE)
            return poTownCity.getProvinceId();
        else
            return "";
    }
    
    public String getBirthPlaceProvinceName(){
        if (poTownCity.getEditMode() == EditMode.UPDATE)
            return poTownCity.getProvinceName();
        else
            return "";
    }
    
    public JSONObject setAdditionalInfo(String additionalInfo){
        return setValue("sAddlInfo", additionalInfo);
    }

    public String getAdditionalInfo(){
        return (String) getValue("sAddlInfo");
    }
    
    public JSONObject setSpouseId(String spouseId){
        return setValue("sSpouseID", spouseId);
    }

    public String getSpouseId(){
        return (String) getValue("sSpouseID");
    }
    
    public JSONObject setTaxIdNumber(String taxIdNumber){
        return setValue("sTaxIDNox", taxIdNumber);
    }

    public String getTaxIdNumber(){
        return (String) getValue("sTaxIDNox");
    }
    
    public JSONObject setLTOClientId(String ltoClientID){
        return setValue("sLTOIDxxx", ltoClientID);
    }

    public String getLTOClientId(){
        return (String) getValue("sLTOIDxxx");
    }
    
    public JSONObject setPhNationalId(String phNationalId){
        return setValue("sPHBNIDxx", phNationalId);
    }

    public String getPhNationalId(){
        return (String) getValue("sPHBNIDxx");
    }
    
    public JSONObject isLoanReceivableClient(boolean isLoanReceivableClient){
        return setValue("cLRClient", isLoanReceivableClient ? "1" : "0");
    }

    public boolean isLoanReceivableClient(){
        return ((String) getValue("cLRClient")).equals("1");
    }
    
    public JSONObject isMotorcycleClient(boolean isMotorcycleClient){
        return setValue("cMCClient", isMotorcycleClient ? "1" : "0");
    }

    public boolean isMotorcycleClient(){
        return ((String) getValue("cMCClient")).equals("1");
    }
    
    public JSONObject isServiceCenterClient(boolean isServiceCenterClient){
        return setValue("cSCClient", isServiceCenterClient ? "1" : "0");
    }

    public boolean isServiceCenterClient(){
        return ((String) getValue("cSCClient")).equals("1");
    }
    
    public JSONObject isSparePartsClient(boolean isSparePartsClient){
        return setValue("isServiceCenterClient", isSparePartsClient ? "1" : "0");
    }

    public boolean isSparePartsClient(){
        return ((String) getValue("isServiceCenterClient")).equals("1");
    }
    
    public JSONObject isMobilePhoneClient(boolean isMobilePhoneClient){
        return setValue("cCPClient", isMobilePhoneClient ? "1" : "0");
    }

    public boolean isMobilePhoneClient(){
        return ((String) getValue("cCPClient")).equals("1");
    }
    
    public JSONObject setRecordStatus(String recordStatus){
        return setValue("cRecdStat", recordStatus);
    }
    
    public String getRecordStatus(){
        return (String) getValue("cRecdStat");
    }
    
    public JSONObject setModifyingId(String modifyingId){
        return setValue("sModified", modifyingId);
    }
    
    public String getModifyingId(){
        return (String) getValue("sModified");
    }
    
    public JSONObject setModifiedDate(Date modifiedDate){
        return setValue("dModified", modifiedDate);
    }
    
    public Date getModifiedDate(){
        return (Date) getValue("dModified");
    }

    @Override
    public void list() {
        Method[] methods = this.getClass().getMethods();

        System.out.println("--------------------------------------------------------------------");
        System.out.println("LIST OF PUBLIC METHODS FOR " + this.getClass().getName() + ":");
        System.out.println("--------------------------------------------------------------------");
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }

    private void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());

            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);
            
            poEntity.updateString("cLRClient", Logical.NO);
            poEntity.updateString("cMCClient", Logical.NO);
            poEntity.updateString("cSCClient", Logical.NO);
            poEntity.updateString("cSPClient", Logical.NO);
            poEntity.updateString("cCPClient", Logical.NO);
            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);

            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }
}