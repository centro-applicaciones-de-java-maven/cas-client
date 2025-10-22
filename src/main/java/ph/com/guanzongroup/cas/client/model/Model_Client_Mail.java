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

public class Model_Client_Mail extends Model{
    Model_Client_Master poClient;
    
    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());
            
            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);
            
            //assign default values
            poEntity.updateString("cOwnerxxx", "0");
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
            poClient.setTableName("Model_Client_Master");
            poClient.initialize();
            //end - initialize other connections
            
            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }
        
    public JSONObject setEmailId(String mobileId){
        return setValue("sEmailIDx", mobileId);
    }

    public String getEmailId(){
        return (String) getValue("sEmailIDx");
    }
    
    public JSONObject setClientId(String clientId){
        return setValue("sClientID", clientId);
    }

    public String getClientId(){
        return (String) getValue("sClientID");
    }
    
    public JSONObject setMailAddress(String emailAddress){
        return setValue("sEMailAdd", emailAddress);
    }

    public String getMailAddress(){
        return (String) getValue("sEMailAdd");
    }
    
    public JSONObject isPrimaryEmail(boolean isPrimaryMobile){
        return setValue("cPrimaryx", isPrimaryMobile ? "1" : "0");
    }

    public boolean isPrimaryEmail(){
        return ((String) getValue("cPrimaryx")).equals("1");
    }
    
    public JSONObject setOwnershipType(String ownershipType){
        return setValue("cOwnerxxx", ownershipType);
    }

    public String getOwnershipType(){
        return (String) getValue("cOwnerxxx");
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