package org.guanzon.cas.client.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.appdriver.agent.services.ReferenceCache;
import org.guanzon.cas.parameter.model.Model_Category;
import org.json.simple.JSONObject;

public class Model_Account_Client_Accreditation extends Model {

    private Model_Category poCategory;
    private Model_Client_Master poClientMaster;
    private Model_Client_Address poClientAddress;
    private Model_Client_Institution_Contact poClientInstitutionContact;
    private Model_AP_Client_Master poAPClient;

    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());

            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);

            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

            //assign default values
            poEntity.updateObject("dTransact", poGRider.getServerDate());
            poEntity.updateString("cAcctType", "0"); //0 - AP; 1 - AR;
            poEntity.updateString("cTranType", "0"); //0 - Accreditation; 1 - Blacklisting;
            poEntity.updateNull("dApproved");
            poEntity.updateString("cTranStat", RecordStatus.INACTIVE);
            //end - assign default values

            ID = "sTransNox";

            //poCategory/poClientMaster/poClientAddress/poClientInstitutionContact are intentionally
            //NOT constructed here - see the matching accessor methods below, which build each
            //lazily on first access so opening this record never touches those tables.
            //poAPClient has no accessor anywhere in this class (dead field even before this
            //change - nothing ever read it), so it is simply no longer constructed at all.

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

    public String getContactId() {
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

    public Model_Category Category() throws SQLException, GuanzonException {
        if (this.poCategory == null) {
            this.poCategory = new Model_Category();
            this.poCategory.setApplicationDriver(poGRider);
            this.poCategory.setXML("Model_Category");
            this.poCategory.setTableName("Category");
            this.poCategory.initialize();
        }

        String categoryId = (String) getValue("sCategrCd");

        if (!"".equals(categoryId)) {
            if (ReferenceCache.tryLoad("Category", categoryId, this.poCategory)) {
                return this.poCategory;
            }

            this.poJSON = this.poCategory.openRecord(categoryId);
            System.out.print(this.poJSON);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Category", categoryId, this.poCategory);
                return this.poCategory;
            }
            this.poCategory.initialize();
            return this.poCategory;
        }

        System.out.print("category code is empty");
        poCategory.initialize();
        return this.poCategory;
    }

    public Model_Client_Master Client() throws SQLException, GuanzonException {
        if (this.poClientMaster == null) {
            this.poClientMaster = new Model_Client_Master();
            this.poClientMaster.setApplicationDriver(poGRider);
            this.poClientMaster.setXML("Model_Client_Master");
            this.poClientMaster.setTableName("Client_Master");
            this.poClientMaster.initialize();
        }

        String clientId = (String) getValue("sClientID");

        if (!"".equals(clientId)) {
            if (ReferenceCache.tryLoad("Client_Master", clientId, this.poClientMaster)) {
                return this.poClientMaster;
            }

            this.poJSON = this.poClientMaster.openRecord(clientId);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Client_Master", clientId, this.poClientMaster);
                return this.poClientMaster;
            }

            this.poClientMaster.initialize();
            return this.poClientMaster;
        }
        poClientMaster.initialize();
        return this.poClientMaster;
    }

    public Model_Client_Address ClientAddress() throws SQLException, GuanzonException {
        if (this.poClientAddress == null) {
            this.poClientAddress = new Model_Client_Address();
            this.poClientAddress.setApplicationDriver(poGRider);
            this.poClientAddress.setXML("Model_Client_Address");
            this.poClientAddress.setTableName("Client_Address");
            this.poClientAddress.initialize();
        }

        String addressId = (String) getValue("sAddrssID");

        if (!"".equals(addressId)) {
            if (ReferenceCache.tryLoad("Client_Address", addressId, this.poClientAddress)) {
                return this.poClientAddress;
            }

            this.poJSON = this.poClientAddress.openRecord(addressId);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Client_Address", addressId, this.poClientAddress);
                return this.poClientAddress;
            }
            this.poClientAddress.initialize();
            return this.poClientAddress;
        }
        poClientAddress.initialize();
        return this.poClientAddress;
    }

    public Model_Client_Institution_Contact ClientInstitutionContact() throws SQLException, GuanzonException {
        if (this.poClientInstitutionContact == null) {
            this.poClientInstitutionContact = new Model_Client_Institution_Contact();
            this.poClientInstitutionContact.setApplicationDriver(poGRider);
            this.poClientInstitutionContact.setXML("Model_Client_Institution_Contact_Person");
            this.poClientInstitutionContact.setTableName("Client_Institution_Contact_Person");
            this.poClientInstitutionContact.initialize();
        }

        String contactId = (String) getValue("sContctID");

        if (!"".equals(contactId)) {
            if (ReferenceCache.tryLoad("Client_Institution_Contact_Person", contactId, this.poClientInstitutionContact)) {
                return this.poClientInstitutionContact;
            }

            this.poJSON = this.poClientInstitutionContact.openRecord(contactId);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Client_Institution_Contact_Person", contactId, this.poClientInstitutionContact);
                return this.poClientInstitutionContact;
            }
            this.poClientInstitutionContact.initialize();
            return this.poClientInstitutionContact;
        }
        poClientInstitutionContact.initialize();
        return this.poClientInstitutionContact;
    }

}
