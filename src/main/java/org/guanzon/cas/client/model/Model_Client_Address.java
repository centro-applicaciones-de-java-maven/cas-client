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
import org.guanzon.cas.parameter.model.Model_Barangay;
import org.guanzon.cas.parameter.model.Model_TownCity;
import org.json.simple.JSONObject;

public class Model_Client_Address implements GEntity{
    LogWrapper logwrapr = new LogWrapper("Model_Client_Address", "cas-error.log");
    
    private final String XML = "Model_Client_Address.xml";
    
    GRider poGRider;                //application driver
    CachedRowSet poEntity;          //rowset
    JSONObject poJSON;              //json container
    int pnEditMode;                 //edit mode
    
    Model_Barangay poBarangay;
    Model_TownCity poTownCity;
    
    public Model_Client_Address(GRider value) {
        if (value == null) {
            System.err.println("Application Driver is not set.");
            System.exit(1);
        }

        poGRider = value;
        
        poBarangay = new Model_Barangay(poGRider);
        poTownCity = new Model_TownCity(poGRider);
        
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
        return "Client_Address";
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
        setAddressId(MiscUtil.getNextCode(getTable(), "sAddrssID", true, poGRider.getConnection(), ""));

        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }

    @Override
    public JSONObject openRecord(String barangayId) {
        poJSON = new JSONObject();

        String lsSQL = MiscUtil.makeSelect(this);

        //replace the condition based on the primary key column of the record
        lsSQL = MiscUtil.addCondition(lsSQL, "sAddrssID = " + SQLUtil.toSQL(barangayId));

        ResultSet loRS = poGRider.executeQuery(lsSQL);

        try {
            if (loRS.next()) {
                for (int lnCtr = 1; lnCtr <= loRS.getMetaData().getColumnCount(); lnCtr++) {
                    setValue(lnCtr, loRS.getObject(lnCtr));
                }
                
                MiscUtil.close(loRS);
                
                //connect to other table
                poJSON = poBarangay.openRecord((String) getValue("sBrgyIDxx"));
                if (!((String)poJSON.get("result")).equals("success")) 
                    System.err.println("Barangay.openRecord: " + poJSON.toJSONString());
                
                poJSON = poTownCity.openRecord((String) getValue("sTownIDxx"));
                if (!((String)poJSON.get("result")).equals("success")) 
                    System.err.println("TownCity.openRecord: " + poJSON.toJSONString());
                
                
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
            
            setModifiedDate(poGRider.getServerDate());
            
            if (pnEditMode == EditMode.ADDNEW) {
                //replace with the primary key column info
                setAddressId(MiscUtil.getNextCode(getTable(), "sAddrssID", true, poGRider.getConnection(), ""));

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
                Model_Client_Address loOldEntity = new Model_Client_Address(poGRider);

                //replace with the primary key column info
                JSONObject loJSON = loOldEntity.openRecord(this.getClientId());

                if ("success".equals((String) loJSON.get("result"))) {
                    //replace the condition based on the primary key column of the record
                    lsSQL = MiscUtil.makeSQL(this, loOldEntity, "sAddrssID = " + SQLUtil.toSQL(this.getAddressId()));

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
    
    public JSONObject setAddressId(String mobileId){
        return setValue("sAddrssID", mobileId);
    }

    public String getAddressId(){
        return (String) getValue("sAddrssID");
    }
    
    public JSONObject setClientId(String clientId){
        return setValue("sClientID", clientId);
    }

    public String getClientId(){
        return (String) getValue("sClientID");
    }
    
    public JSONObject setHouseNo(String houseNo){
        return setValue("sHouseNox", houseNo);
    }

    public String getHouseNo(){
        return (String) getValue("sHouseNox");
    }
    
    public JSONObject setAddress(String address){
        return setValue("sAddressx", address);
    }

    public String getAddress(){
        return (String) getValue("sAddressx");
    }
    
    public JSONObject setBaragayId(String barangayId){
        setValue("sBrgyIDxx", barangayId);
        poBarangay.openRecord((String) getValue("sBrgyIDxx"));
        
        return poJSON;
    }

    public String getBaragayId(){
        return (String) getValue("sBrgyIDxx");
    }
    
    public String getBarangayName(){
        if (poBarangay.getEditMode() == EditMode.UPDATE)
            return poBarangay.getBarangayName();
        else
            return "";
    }
    
    public JSONObject setTownId(String townId){
        setValue("sTownIDxx", townId);
        poTownCity.openRecord((String) getValue("sTownIDxx"));
        
        return poJSON;
    }

    public String getTownId(){
        return (String) getValue("sTownIDxx");
    }
    
    public String getTownName(){
        if (poTownCity.getEditMode() == EditMode.UPDATE)
            return poTownCity.getTownName();
        else
            return "";
    }
    
    public String getProvinceId(){
        if (poTownCity.getEditMode() == EditMode.UPDATE)
            return poTownCity.getProvinceId();
        else
            return "";
    }
    
    public String getProvinceName(){
        if (poTownCity.getEditMode() == EditMode.UPDATE)
            return poTownCity.getProvinceName();
        else
            return "";
    }
    
    public JSONObject setLatitude(String latitude){
        return setValue("nLatitude", latitude);
    }
    
    public double getLatitude(){
        return (double) getValue("nLatitude");
    }
    
    public JSONObject setLongitude(String longitude){
        return setValue("nLongitud", longitude);
    }
    
    public double getLongitude(){
        return (double) getValue("nLongitud");
    }
    
    public JSONObject isPrimaryMobile(boolean isPrimaryMobile){
        return setValue("cPrimaryx", isPrimaryMobile ? "1" : "0");
    }

    public boolean isPrimaryMobile(){
        return ((String) getValue("cPrimaryx")).equals("1");
    }
    
    public JSONObject isOfficeAddress(boolean isOfficeAddress){
        return setValue("cOfficexx", isOfficeAddress ? "1" : "0");
    }

    public boolean isOfficeAddress(){
        return ((String) getValue("cOfficexx")).equals("1");
    }
    
    public JSONObject isProvinceAddress(boolean isProvinceAddress){
        return setValue("cProvince", isProvinceAddress ? "1" : "0");
    }

    public boolean isProvinceAddress(){
        return ((String) getValue("cProvince")).equals("1");
    }
    
    public JSONObject isBillingAddress(boolean isBillingAddress){
        return setValue("cBillingx", isBillingAddress ? "1" : "0");
    }

    public boolean isBillingAddress(){
        return ((String) getValue("cBillingx")).equals("1");
    }
    
    public JSONObject isShippingAddress(boolean isShippingAddress){
        return setValue("cShipping", isShippingAddress ? "1" : "0");
    }

    public boolean isShippingAddress(){
        return ((String) getValue("cShipping")).equals("1");
    }
    
    public JSONObject isCurrentAddress(boolean isCurrentAddress){
        return setValue("cCurrentx", isCurrentAddress ? "1" : "0");
    }

    public boolean isCurrentAddress(){
        return ((String) getValue("cCurrentx")).equals("1");
    }
    
    public JSONObject isLTMSAddress(boolean isLTMSAddress){
        return setValue("cLTMSAddx", isLTMSAddress ? "1" : "0");
    }

    public boolean isLTMSAddress(){
        return ((String) getValue("cLTMSAddx")).equals("1");
    }
    
    public JSONObject setSourceCode(String sourceCode){
        return setValue("sSourceCd", sourceCode);
    }

    public String getSourceCode(){
        return (String) getValue("sSourceCd");
    }
    
    public JSONObject setSourceNo(String sourceNo){
        return setValue("sReferNox", sourceNo);
    }

    public String getSourceNo(){
        return (String) getValue("sReferNox");
    }
    
    public JSONObject setRecordStatus(String recordStatus){
        return setValue("cRecdStat", recordStatus);
    }
    
    public String getRecordStatus(){
        return (String) getValue("cRecdStat");
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
            
            poEntity.updateString("cPrimaryx", Logical.NO);
            poEntity.updateString("cOfficexx", Logical.NO);
            poEntity.updateString("cProvince", Logical.NO);
            poEntity.updateString("cBillingx", Logical.NO);
            poEntity.updateString("cShipping", Logical.NO);
            poEntity.updateString("cCurrentx", Logical.NO);
            poEntity.updateString("cLTMSAddx", Logical.NO);
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
