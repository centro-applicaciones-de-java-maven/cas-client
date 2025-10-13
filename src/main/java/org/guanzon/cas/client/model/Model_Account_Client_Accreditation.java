package org.guanzon.cas.client.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.cas.client.services.ClientModels;
import org.guanzon.cas.parameter.model.Model_Category;
import org.guanzon.cas.parameter.services.ParamModels;
import org.json.simple.JSONObject;

public class Model_Account_Client_Accreditation extends Model {
    private Model_Category poCategory;
    private Model_Client_Master poClientMaster;
    private Model_Client_Address poClientAddress;    
    private Model_Client_Institution_Contact poClientInstitutionContact;

    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());

            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);

            //assign default values
            poEntity.updateString("cAcctType", "0"); //0 - AP; 1 - AR;
            poEntity.updateString("cTranType", "0"); //0 - Accreditation; 1 - Blacklisting;
            poEntity.updateNull("dApproved");
            poEntity.updateString("cTranStat", RecordStatus.INACTIVE);
            //end - assign default values

            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

            ID = "sTransNox";

            //initialize other connections
            ParamModels model = new ParamModels(poGRider);
            poCategory = model.Category();
            
            ClientModels clientmodel = new ClientModels(poGRider);
            poClientMaster = clientmodel.ClientMaster();
            poClientAddress = clientmodel.ClientAddress();
            poClientInstitutionContact = clientmodel.ClientInstitutionContact();            
            
            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }

    public JSONObject setTransactionNo(String transactionNo) {
        return setValue("sTransNox", transactionNo);
    }

    public String getTransactionNo() {
        return (String) getValue("sTransNox");
    }
    
    public JSONObject setDateTransact(Date dateTransact) {
        return setValue("dTransact", dateTransact);
    }

    public Date getDateTransact() {
        return (Date) getValue("dTransact");
    }
    
    public JSONObject setAccountType(String accountType) {
        return setValue("cAcctType", accountType);
    }

    public String getAccountType() {
        return (String) getValue("cAcctType");
    }
    
    public JSONObject setClientId(String clientId) {
        return setValue("sClientID", clientId);
    }

    public String getClientId() {
        return (String) getValue("sClientID");
    }
    
    public JSONObject setAddressId(String addressID) {
        return setValue("sAddrssID", addressID);
    }

    public String getAddressId() {
        return (String) getValue("sAddrssID");
    }
    
    public JSONObject setContactId(String contactId) {
        return setValue("sContctID", contactId);
    }

    public String getContatId() {
        return (String) getValue("sContctID");
    }
    
    public JSONObject setRemarks(String remarks) {
        return setValue("sRemarksx", remarks);
    }

    public String getRemarks() {
        return (String) getValue("sRemarksx");
    }
     
    public JSONObject setTransactionType(String transactionType) {
        return setValue("cTranType", transactionType);
    }

    public String getTransactionType() {
        return (String) getValue("cTranType");
    }   
     
    public JSONObject setCategoryCode(String categoryCode) {
        return setValue("sCategrCd", categoryCode);
    }

    public String getCategoryCode() {
        return (String) getValue("sCategrCd");
    }     
    
    public JSONObject setRecordStatus(String recordStatus) {
        return setValue("cTranStat", recordStatus);
    }

    public String getRecordStatus() {
        return (String) getValue("cTranStat");
    }
    
    public JSONObject setApproved(String approved) {
        return setValue("sApproved", approved);
    }

    public String getApproved() {
        return (String) getValue("sApproved");
    }
    
    public JSONObject setDateApproved(Date dateApproved) {
        return setValue("dApproved", dateApproved);
    }

    public Date getDateApproved() {
        return (Date) getValue("dApproved");
    }
    
    public JSONObject setModifyingId(String modifyingId) {
        return setValue("sModified", modifyingId);
    }

    public String getModifyingId() {
        return (String) getValue("sModified");
    }

    public JSONObject setModifiedDate(Date modifiedDate) {
        return setValue("dModified", modifiedDate);
    }

    public Date getModifiedDate() {
        return (Date) getValue("dModified");
    }
    
    @Override
    public String getNextCode() {
        return MiscUtil.getNextCode(getTable(), ID, true, poGRider.getGConnection().getConnection(), poGRider.getBranchCode());
    }
    
    public Model_Category Category() throws SQLException, GuanzonException{
        return poCategory;
    }
    
    public Model_Client_Master Client() throws SQLException, GuanzonException{
        return poClientMaster;
    }
    
    public Model_Client_Address ClientAddress() throws SQLException, GuanzonException{
        return poClientAddress;
    }
    
    public Model_Client_Institution_Contact ClientInstitutionContact() throws SQLException, GuanzonException{
        return poClientInstitutionContact;
    }
}