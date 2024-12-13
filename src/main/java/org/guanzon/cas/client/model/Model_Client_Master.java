package org.guanzon.cas.client.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.cas.parameter.model.Model_Country;
import org.guanzon.cas.parameter.model.Model_TownCity;
import org.json.simple.JSONObject;

public class Model_Client_Master extends Model{
    Model_TownCity poTownCity;
    Model_Country poCountry;
    
    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());
            
            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);
            
            //assign default values
            poEntity.updateObject("dBirthDte", SQLUtil.toDate("1900-01-01", SQLUtil.FORMAT_SHORT_DATE));
            poEntity.updateString("cLRClient", Logical.NO);
            poEntity.updateString("cMCClient", Logical.NO);
            poEntity.updateString("cSCClient", Logical.NO);
            poEntity.updateString("cSPClient", Logical.NO);
            poEntity.updateString("cCPClient", Logical.NO);
            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);
            //end - assign default values

            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

            ID = poEntity.getMetaData().getColumnLabel(1);
            
            //initialize other connections
            poTownCity = new Model_TownCity();
            poTownCity.setApplicationDriver(poGRider);
            poTownCity.setXML("Model_TownCity");
            poTownCity.setTableName("TownCity");
            poTownCity.initialize();
            
            poCountry = new Model_Country();
            poCountry.setApplicationDriver(poGRider);
            poCountry.setXML("Model_Country");
            poCountry.setTableName("Country");
            poCountry.initialize();
            //end - initialize other connections
            
            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }
    
    public Model_TownCity BirthTown(){
        if (!"".equals((String) getValue("sBirthPlc"))){
            if (poTownCity.getEditMode() == EditMode.READY && 
                poTownCity.getTownId().equals((String) getValue("sBirthPlc")))
                return poTownCity;
            else{
                poJSON = poTownCity.openRecord((String) getValue("sBirthPlc"));

                if ("success".equals((String) poJSON.get("result")))
                    return poTownCity;
                else {
                    poTownCity.initialize();
                    return poTownCity;
                }
            }
        } else {
            poTownCity.initialize();
            return poTownCity;
        }
    }
    
    public Model_Country Citizenship(){
        if (!"".equals((String) getValue("sCitizenx"))){
            if (poCountry.getEditMode() == EditMode.READY && 
                poCountry.getCountryId().equals((String) getValue("sCitizenx")))
                return poCountry;
            else{
                poJSON = poCountry.openRecord((String) getValue("sCitizenx"));

                if ("success".equals((String) poJSON.get("result")))
                    return poCountry;
                else {
                    poTownCity.initialize();
                    return poCountry;
                }
            }
        } else {
            poCountry.initialize();
            return poCountry;
        }
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
    
    public JSONObject setCompanyName(String fullName){
        if ("0".equals((String) getValue("cClientTp"))){
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "Unable to assign company name on individual accounts.");
            return poJSON;
        }

        return setValue("sCompnyNm", fullName);
    }

    public String getCompanyName(){
        if ("1".equals((String) getValue("cClientTp"))){
            return (String) getValue("sCompnyNm");
        } else {
            return ((String) getValue("sLastName") + ", " + 
                    (String) getValue("sFrstName") + 
                    ("".equals((String) getValue("sSuffixNm")) ? "" : " " + (String) getValue("sSuffixNm")) + 
                    (String) getValue("sMiddName")).trim();
        }        
    }
    
    public JSONObject setGender(String genderCode){
        return setValue("cGenderCd", genderCode);
    }

    public String getGender(){
        return (String) getValue("cGenderCd");
    }
    
    public JSONObject setCitizenshipId(String citizenshipId){        
        return setValue("sCitizenx", citizenshipId);
    }

    public String getCitizenshipId(){
        return (String) getValue("sCitizenx");
    }
    
    public JSONObject setBirthDate(Date birthDate){
        return setValue("dBirthDte", birthDate);
    }

    public Date getBirthDate(){
        return (Date) getValue("dBirthDte");
    }
    
    public JSONObject setBirthPlaceId(String birthPlaceId){        
        return setValue("sBirthPlc", birthPlaceId);
    }

    public String getBirthPlaceId(){
        return (String) getValue("sBirthPlc");
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
    public String getNextCode(){
        return MiscUtil.getNextCode(getTable(), ID, true, poGRider.getConnection(), poGRider.getBranchCode()); 
    }
}