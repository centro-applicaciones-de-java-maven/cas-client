package org.guanzon.cas.client.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.agent.services.ReferenceCache;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.cas.parameter.model.Model_Term;
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
            
            //poTerm/poClientMaster/poClientAddress/poClientInstitutionContact/poClientMobile are
            //intentionally NOT constructed here - see the matching accessor methods below, which
            //build each lazily on first access so opening this record never touches those tables.

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
        if (poTerm == null) {
            poTerm = new Model_Term();
            poTerm.setApplicationDriver(poGRider);
            poTerm.setXML("Model_Term");
            poTerm.setTableName("Term");
            poTerm.initialize();
        }

        String termId = (String) getValue("sTermIDxx");

        if (!"".equals(termId)) {
            if (poTerm.getEditMode() == EditMode.READY
                    && poTerm.getTermId().equals(termId)) {
                return poTerm;
            } else {
                if (ReferenceCache.tryLoad("Term", termId, poTerm)) {
                    return poTerm;
                }

                poJSON = poTerm.openRecord(termId);

                if ("success".equals((String) poJSON.get("result"))) {
                    ReferenceCache.store("Term", termId, poTerm);
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
        if (poClientMaster == null) {
            poClientMaster = new Model_Client_Master();
            poClientMaster.setApplicationDriver(poGRider);
            poClientMaster.setXML("Model_Client_Master");
            poClientMaster.setTableName("Client_Master");
            poClientMaster.initialize();
        }

        String clientId = (String) getValue("sClientID");

        if (!"".equals(clientId)) {
            if (poClientMaster.getEditMode() == EditMode.READY
                    && poClientMaster.getClientId().equals(clientId)) {
                return poClientMaster;
            } else {
                if (ReferenceCache.tryLoad("Client_Master", clientId, poClientMaster)) {
                    return poClientMaster;
                }

                poJSON = poClientMaster.openRecord(clientId);

                if ("success".equals((String) poJSON.get("result"))) {
                    ReferenceCache.store("Client_Master", clientId, poClientMaster);
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

    //NOTE: pre-existing bug kept as-is (not introduced by this change, flagged separately) -
    //the cache-match check below compares poClientAddress.getClientId() (the WRONG id-getter for
    //this child) against getValue("sAddrssID"), instead of checking the address's own id.
    public Model_Client_Address ClientAddress() throws SQLException, GuanzonException{
        if (poClientAddress == null) {
            poClientAddress = new Model_Client_Address();
            poClientAddress.setApplicationDriver(poGRider);
            poClientAddress.setXML("Model_Client_Address");
            poClientAddress.setTableName("Client_Address");
            poClientAddress.initialize();
        }

        String addressId = (String) getValue("sAddrssID");

        if (!"".equals((String) getValue("sClientID"))) {
            if (poClientAddress.getEditMode() == EditMode.READY
                    && poClientAddress.getClientId().equals(addressId)) {
                return poClientAddress;
            } else {
                if (ReferenceCache.tryLoad("Client_Address", addressId, poClientAddress)) {
                    return poClientAddress;
                }

                System.out.println("before = " + (String) poJSON.get("result"));
                poJSON = poClientAddress.openRecord(addressId);

                System.out.println("after = " + (String) poJSON.get("result"));
                if ("success".equals((String) poJSON.get("result"))) {
                    ReferenceCache.store("Client_Address", addressId, poClientAddress);
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

    //NOTE: pre-existing bug kept as-is (not introduced by this change, flagged separately) -
    //the cache-match check below compares poClientInstitutionContact.getClientId() (the WRONG
    //id-getter for this child) against getValue("sContctID"), instead of checking its own id.
    public Model_Client_Institution_Contact ClientInstitutionContact() throws SQLException, GuanzonException{
        if (poClientInstitutionContact == null) {
            poClientInstitutionContact = new Model_Client_Institution_Contact();
            poClientInstitutionContact.setApplicationDriver(poGRider);
            poClientInstitutionContact.setXML("Model_Client_Institution_Contact_Person");
            poClientInstitutionContact.setTableName("Client_Institution_Contact_Person");
            poClientInstitutionContact.initialize();
        }

        String contactId = (String) getValue("sContctID");

        System.out.println("Client_Institution_Contact == " + (String) getValue("sClientID"));
        if (!"".equals((String) getValue("sClientID"))) {
            if (poClientInstitutionContact.getEditMode() == EditMode.READY
                    && poClientInstitutionContact.getClientId().equals(contactId)) {
                return poClientInstitutionContact;
            } else {
                if (ReferenceCache.tryLoad("Client_Institution_Contact_Person", contactId, poClientInstitutionContact)) {
                    return poClientInstitutionContact;
                }

                poJSON = poClientInstitutionContact.openRecord(contactId);

                if ("success".equals((String) poJSON.get("result"))) {
                    ReferenceCache.store("Client_Institution_Contact_Person", contactId, poClientInstitutionContact);
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
        if (poClientMobile == null) {
            poClientMobile = new Model_Client_Mobile();
            poClientMobile.setApplicationDriver(poGRider);
            poClientMobile.setXML("Model_Client_Mobile");
            poClientMobile.setTableName("Client_Mobile");
            poClientMobile.initialize();
        }

        String clientId = (String) getValue("sClientID");

        System.out.println("mobile == " + clientId);
        if (!"".equals(clientId)) {
            if (poClientMobile.getEditMode() == EditMode.READY
                    && poClientMobile.getClientId().equals(clientId)) {
                return poClientMobile;
            } else {
                if (ReferenceCache.tryLoad("Client_Mobile", clientId, poClientMobile)) {
                    return poClientMobile;
                }

                poJSON = poClientMobile.openRecord(clientId);

                if ("success".equals((String) poJSON.get("result"))) {
                    ReferenceCache.store("Client_Mobile", clientId, poClientMobile);
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