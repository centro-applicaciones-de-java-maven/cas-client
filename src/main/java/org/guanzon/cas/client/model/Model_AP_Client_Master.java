package org.guanzon.cas.client.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.cas.parameter.model.Model_Category;
import org.guanzon.cas.parameter.services.ParamModels;
import org.json.simple.JSONObject;
import org.guanzon.cas.client.services.ClientModels;

public class Model_AP_Client_Master extends Model{      
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
            poEntity.updateNull("dCltSince");
            poEntity.updateNull("dBegDatex");
            poEntity.updateObject("cVatablex", Logical.NO);
            poEntity.updateObject("nDiscount", 0.00);
            poEntity.updateObject("nCredLimt", 0.00);
            poEntity.updateObject("nABalance", 0.00);
            poEntity.updateObject("nOBalance", 0.00);
            poEntity.updateObject("nBegBalxx", 0.00);
            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);
            //end - assign default values

            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

            ID = "sClientID";            
            
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

    public JSONObject setClientId(String clientId){
        return setValue("sClientID", clientId);
    }
    
    public String getClientId(){
        return (String) getValue("sClientID");
    }
    
    public JSONObject setAddressId(String addressId){
        return setValue("sAddrssID", addressId);
    }
    
    public String getAddressId(){
        return (String) getValue("sAddrssID");
    }
    
    public JSONObject setContactId(String contactId){
        return setValue("sContctID", contactId);
    }
    
    public String getContactId(){
        return (String) getValue("sContctID");
    }
    
    public JSONObject setCategoryCode(String categoryCode){
        return setValue("sCategrCd", categoryCode);
    }
    
    public String getCategoryCode(){
        return (String) getValue("sCategrCd");
    }
    
    public JSONObject setdateClientSince(Date dateClientSince){
        return setValue("dCltSince", dateClientSince);
    }
    
    public Date getdateClientSince(){
        return (Date) getValue("dCltSince");
    }
    
    public JSONObject setBeginningDate(Date beginningDate){
        return setValue("dBegDatex", beginningDate);
    }
    
    public Date getBeginningDate(){
        return (Date) getValue("dBegDatex");
    }
    
    public JSONObject setBeginningBalance(Number beginningBalancce){
        return setValue("nBegBalxx", beginningBalancce);
    }
    
    public Number getBeginningBalance(){
        return (Number) getValue("nBegBalxx");
    }   
    
    public JSONObject setTermId(String termId){
        return setValue("sTermIDxx", termId );
    }
    
    public String getTermId(){
        return (String) getValue("sTermIDxx");
    }
    
    public JSONObject setDiscount(Number discount) {
        return setValue("nDiscount", discount);
    }

    public Number getDiscount() {
        return (Number) getValue("nDiscount");
    }
    
    public JSONObject setCreditLimit(Number creditLimit) {
        return setValue("nCredLimt", creditLimit);
    }

    public Number getCreditLimit() {
        return (Number) getValue("nCredLimt");
    }
    
    public JSONObject setAccountBalance(Number accountBalance) {
        return setValue("nABalance", accountBalance);
    }

    public Number getAccountBalance() {
        return (Number) getValue("nABalance");
    }

    public JSONObject setOBalance(Number oBalance) {
        return setValue("nOBalance", oBalance);
    }

    public Number getOBalance() {
        return (Number) getValue("nOBalance");
    }

    public JSONObject setLedgerNo(String ledgerNo){
        return setValue("nLedgerNo", ledgerNo);
    }
    
    public String getLedgerNo(){
        return (String) getValue("nLedgerNo");
    }  
    
    public JSONObject setVatable(String vatable){
        return setValue("cVatablex", vatable);
    }
    
    public String getVatable(){
        return (String) getValue("cVatablex");
    }
    
    public JSONObject setAutoHold(String autoHold){
        return setValue("cAutoHold", autoHold);
    }
    
    public String getAutoHold(){
        return (String) getValue("cAutoHold");
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
    public String getNextCode() {
        return "";
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