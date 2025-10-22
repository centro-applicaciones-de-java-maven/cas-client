package ph.com.guanzongroup.cas.client.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.impl.Model;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.RecordStatus;
import ph.com.guanzongroup.cas.parameter.model.Model_Term;
import org.json.simple.JSONObject;

public class Model_AR_Client_Master extends Model{      
    //reference objects
    Model_Client_Master poClientMaster;
    Model_Client_Address poClientAddress;
    Model_Client_Institution_Contact poClientInstitutionContact;
    Model_Client_Mobile poClientMobile;
    Model_Term poTerm;
    
    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());
            
            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);
            
            //assign default values
            poEntity.updateObject("dCltSince", "1900-01-01");
            poEntity.updateObject("dBegDatex", "1900-01-01");
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
            
            //initialize reference objects
            poTerm = new Model_Term();
            poTerm.setApplicationDriver(poGRider);
            poTerm.setXML("Model_Term");
            poTerm.setTableName("Term");
            poTerm.initialize();
            
            poClientMaster = new Model_Client_Master();
            poClientMaster.setApplicationDriver(poGRider);
            poClientMaster.setXML("Model_Client_Master");
            poClientMaster.setTableName("Client_Master");
            poClientMaster.initialize();
            
            poClientAddress = new Model_Client_Address();
            poClientAddress.setApplicationDriver(poGRider);
            poClientAddress.setXML("Model_Client_Address");
            poClientAddress.setTableName("Client_Address");
            poClientAddress.initialize();
            
            poClientInstitutionContact = new Model_Client_Institution_Contact();
            poClientInstitutionContact.setApplicationDriver(poGRider);
            poClientInstitutionContact.setXML("Model_Client_Institution_Contact_Person");
            poClientInstitutionContact.setTableName("Client_Institution_Contact_Person");
            poClientInstitutionContact.initialize();
            
            poClientMobile = new Model_Client_Mobile();
            poClientMobile.setApplicationDriver(poGRider);
            poClientMobile.setXML("Model_Client_Mobile");
            poClientMobile.setTableName("Client_Mobile");
            poClientMobile.initialize();
            //end - initialize reference objects
            
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

    public JSONObject setLedgerNo(int ledgerNo){
        return setValue("nLedgerNo", ledgerNo);
    }
    
    public int getLedgerNo(){
        return (int) getValue("nLedgerNo");
    }  
    
    public JSONObject setVatable(String vatable){
        return setValue("cVatablex", vatable);
    }
    
    public String getVatable(){
        return (String) getValue("cVatablex");
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
    
    //reference object models
    public Model_Term Term() throws SQLException, GuanzonException{
        if (!"".equals((String) getValue("sTermIDxx"))) {
            if (poTerm.getEditMode() == EditMode.READY
                    && poTerm.getTermId().equals((String) getValue("sTermIDxx"))) {
                return poTerm;
            } else {
                poJSON = poTerm.openRecord((String) getValue("sTermIDxx"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poTerm;
                } else {
                    poTerm.initialize();
                    return poTerm;
                }
            }
        } else {
            poTerm.initialize();
            return poTerm;
        }
    }
        
    public Model_Client_Master Client() throws SQLException, GuanzonException{    
        if (!"".equals((String) getValue("sClientID"))) {
            if (poClientMaster.getEditMode() == EditMode.READY
                    && poClientMaster.getClientId().equals((String) getValue("sClientID"))) {
                return poClientMaster;
            } else {
                poJSON = poClientMaster.openRecord((String) getValue("sClientID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poClientMaster;
                } else {
                    poClientMaster.initialize();
                    return poClientMaster;
                }
            }
        } else {
            poClientMaster.initialize();
            return poClientMaster;
        }
    }
    
    public Model_Client_Address ClientAddress() throws SQLException, GuanzonException{
        if (!"".equals((String) getValue("sClientID"))) {
            if (poClientAddress.getEditMode() == EditMode.READY
                    && poClientAddress.getClientId().equals((String) getValue("sAddrssID"))) {
                return poClientAddress;
            } else {
                
                System.out.println("before = " + (String) poJSON.get("result"));
                poJSON = poClientAddress.openRecord((String) getValue("sAddrssID"));
                
                System.out.println("after = " + (String) poJSON.get("result"));
                if ("success".equals((String) poJSON.get("result"))) {
                    return poClientAddress;
                } else {
                    poClientAddress.initialize();
                    return poClientAddress;
                }
            }
        } else {
            poClientAddress.initialize();
            return poClientAddress;
        }
    }
    
    public Model_Client_Institution_Contact ClientInstitutionContact() throws SQLException, GuanzonException{
        
            System.out.println("Client_Institution_Contact == " + (String) getValue("sClientID"));
        if (!"".equals((String) getValue("sClientID"))) {
            if (poClientInstitutionContact.getEditMode() == EditMode.READY
                    && poClientInstitutionContact.getClientId().equals((String) getValue("sContctID"))) {
                return poClientInstitutionContact;
            } else {
                poJSON = poClientInstitutionContact.openRecord((String) getValue("sContctID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poClientInstitutionContact;
                } else {
                    poClientInstitutionContact.initialize();
                    return poClientInstitutionContact;
                }
            }
        } else {
            poClientInstitutionContact.initialize();
            return poClientInstitutionContact;
        }
    }
    
        public Model_Client_Mobile ClientMobile() throws SQLException, GuanzonException{
            System.out.println("mobile == " + (String) getValue("sClientID"));
        if (!"".equals((String) getValue("sClientID"))) {
            if (poClientMobile.getEditMode() == EditMode.READY
                    && poClientMobile.getClientId().equals((String) getValue("sClientID"))) {
                return poClientMobile;
            } else {
                poJSON = poClientMobile.openRecord((String) getValue("sClientID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poClientMobile;
                } else {
                    poClientMobile.initialize();
                    return poClientMobile;
                }
            }
        } else {
            poClientMobile.initialize();
            return poClientMobile;
        }
    }
}