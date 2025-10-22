package ph.com.guanzongroup.cas.client.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.impl.Model;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.RecordStatus;
import org.json.simple.JSONObject;

public class Model_Client_Institution_Contact extends Model{
    Model_Client_Master poClient;
    
    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());
            
            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);
            
            //assign default values
            poEntity.updateString("cPrimaryx", Logical.NO);
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
        
    public JSONObject setContactPId(String contactPersionId){
        return setValue("sContctID", contactPersionId);
    }

    public String getContactPId(){
        return (String) getValue("sContctID");
    }
    
    public JSONObject setClientId(String clientId){
        return setValue("sClientID", clientId);
    }

    public String getClientId(){
        return (String) getValue("sClientID");
    }
    
    public JSONObject setContactPersonName(String contactPersonName){
        return setValue("sCPerson1", contactPersonName);
    }

    public String getContactPersonName(){
        return (String) getValue("sCPerson1");
    }
    
    public JSONObject setContactPersonPosition(String contactPersonPosition){
        return setValue("sCPPosit1", contactPersonPosition);
    }

    public String getContactPersonPosition(){
        return (String) getValue("sCPPosit1");
    }
    
    public JSONObject setMobileNo(String mobileNo){
        return setValue("sMobileNo", mobileNo);
    }

    public String getMobileNo(){
        return (String) getValue("sMobileNo");
    }
    
    public JSONObject setLandlineNo(String landlineNo){
        return setValue("sTelNoxxx", landlineNo);
    }

    public String getLandlineNo(){
        return (String) getValue("sTelNoxxx");
    }
    
    public JSONObject setFaxNo(String faxMachineNo){
        return setValue("sFaxNoxxx", faxMachineNo);
    }

    public String getFaxNo(){
        return (String) getValue("sFaxNoxxx");
    }
    
    public JSONObject setMailAddress(String emailAddress){
        return setValue("sEMailAdd", emailAddress);
    }

    public String getMailAddress(){
        return (String) getValue("sEMailAdd");
    }
    
    public JSONObject setSocMedAccount1(String socialMediaAccount){
        return setValue("sAccount1", socialMediaAccount);
    }

    public String getSocMedAccount1(){
        return (String) getValue("sAccount1");
    }
    
    public JSONObject setSocMedAccount2(String socialMediaAccount){
        return setValue("sAccount2", socialMediaAccount);
    }

    public String getSocMedAccount2(){
        return (String) getValue("sAccount2");
    }
    
    public JSONObject setSocMedAccount3(String socialMediaAccount){
        return setValue("sAccount3", socialMediaAccount);
    }

    public String getSocMedAccount3(){
        return (String) getValue("sAccount3");
    }
    
    public JSONObject setRemarks(String remarks){
        return setValue("sRemarksx", remarks);
    }

    public String getRemarks(){
        return (String) getValue("sRemarksx");
    }

    public JSONObject isPrimaryContactPersion(boolean isPrimaryContactPersion){
        return setValue("cPrimaryx", isPrimaryContactPersion ? "1" : "0");
    }

    public boolean isPrimaryContactPersion(){
        return ((String) getValue("cPrimaryx")).equals("1");
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
    public String getNextCode(){
        return MiscUtil.getNextCode(getTable(), ID, true, poGRider.getGConnection().getConnection(), poGRider.getBranchCode()); 
    }
    
    public Model_Client_Master Client() throws SQLException, GuanzonException{
        return poClient;
    }
}