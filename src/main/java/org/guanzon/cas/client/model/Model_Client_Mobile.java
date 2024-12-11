package org.guanzon.cas.client.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.RecordStatus;
import org.json.simple.JSONObject;

public class Model_Client_Mobile extends Model{
    Model_Client_Master poClient;
    
    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());
            
            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);
            
            //assign default values
            poEntity.updateString("cMobileTp", "0");
            poEntity.updateString("cOwnerxxx", "0");
            poEntity.updateString("cPrimaryx", Logical.NO);
            poEntity.updateString("cNewMobil", Logical.YES);
            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);
            //end - assign default values

            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

            ID = poEntity.getMetaData().getColumnLabel(1);
            
            //initialize other connections
            poClient = new Model_Client_Master();
            poClient.setApplicationDriver(poGRider);
            poClient.setXML("Model_Client_Master");
            poClient.setTableName("Client_Master");
            poClient.initialize();
            //end - initialize other connections
            
            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }
    
    public JSONObject setMobileId(String mobileId){
        return setValue("sMobileID", mobileId);
    }

    public String getMobileId(){
        return (String) getValue("sMobileID");
    }
    
    public JSONObject setClientId(String clientId){
        return setValue("sClientID", clientId);
    }

    public String getClientId(){
        return (String) getValue("sClientID");
    }
    
    public String getClientName(){
        if (!"".equals((String) getValue("sClientID"))){
            if (poClient.getEditMode() == EditMode.READY &&
                poClient.getClientId().equals((String) getValue("sClientID")))
                return poClient.getCompanyName();
            else{
                poJSON = poClient.openRecord((String) getValue("sClientID"));

                if ("success".equals((String) poJSON.get("result")))
                    return poClient.getCompanyName();
                else return "";
            }
        } else return "";
    }
    
    public JSONObject setMobileNo(String mobileNo){
        return setValue("sMobileNo", mobileNo);
    }

    public String getMobileNo(){
        return (String) getValue("sMobileNo");
    }
    
    public JSONObject setMobileType(String mobileType){
        return setValue("cMobileTp", mobileType);
    }

    public String getMobileType(){
        return (String) getValue("cMobileTp");
    }
    
    public JSONObject setOwnershipType(String ownershipType){
        return setValue("cOwnerxxx", ownershipType);
    }

    public String getOwnershipType(){
        return (String) getValue("cOwnerxxx");
    }
    
    public JSONObject isPrimaryMobile(boolean isPrimaryMobile){
        return setValue("cPrimaryx", isPrimaryMobile ? "1" : "0");
    }

    public boolean isPrimaryMobile(){
        return ((String) getValue("cPrimaryx")).equals("1");
    }
    
    public JSONObject isAcceptingMarketing(boolean isAcceptingMarketing){
        return setValue("cIncdMktg", isAcceptingMarketing ? "1" : "0");
    }

    public boolean isAcecptingMarketing(){
        return ((String) getValue("cIncdMktg")).equals("1");
    }
    
    public JSONObject setNetworkType(String networkType){
        return setValue("cSubscrbr", networkType);
    }

    public String getNetworkType(){
        return (String) getValue("cSubscrbr");
    }
    
    public JSONObject isNewContact(boolean isNewContact){
        return setValue("cNewMobil", isNewContact ? "1" : "0");
    }

    public boolean isNewContact(){
        return ((String) getValue("cNewMobil")).equals("1");
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
}