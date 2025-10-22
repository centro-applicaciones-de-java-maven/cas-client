package ph.com.guanzongroup.cas.client.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.impl.Model;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.RecordStatus;
import ph.com.guanzongroup.cas.client.services.ClientModels;
import ph.com.guanzongroup.cas.parameter.model.Model_Category;
import ph.com.guanzongroup.cas.parameter.services.ParamModels;
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

            //initialize other connections
            ParamModels model = new ParamModels(poGRider);
            poCategory = model.Category();

            poClientMaster = new ClientModels(poGRider).ClientMaster();
            poClientAddress = new ClientModels(poGRider).ClientAddress();
            poClientInstitutionContact = new ClientModels(poGRider).ClientInstitutionContact();

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
        if (!"".equals(getValue("sCategrCd"))) {
            if (this.poCategory.getEditMode() == 1 && this.poCategory
                    .getCategoryId().equals(getValue("sCategrCd"))) {
                return this.poCategory;
            }
            this.poJSON = this.poCategory.openRecord((String) getValue("sCategrCd"));
            if ("success".equals(this.poJSON.get("result"))) {
                return this.poCategory;
            }
            this.poCategory.initialize();
            return this.poCategory;
        }
        poCategory.initialize();
        return this.poCategory;
    }

    public Model_Client_Master Client() throws SQLException, GuanzonException {
        if (!"".equals(getValue("sClientID"))) {
            if (this.poClientMaster.getEditMode() == 1 && this.poClientMaster
                    .getClientId().equals(getValue("sClientID"))) {
                return this.poClientMaster;
            }
            this.poJSON = this.poClientMaster.openRecord((String) getValue("sClientID"));
            if ("success".equals(this.poJSON.get("result"))) {
                return this.poClientMaster;
            }
            this.poClientMaster.initialize();
            return this.poClientMaster;
        }
        poClientMaster.initialize();
        return this.poClientMaster;
    }

    public Model_Client_Address ClientAddress() throws SQLException, GuanzonException {
        if (!"".equals(getValue("sAddrssID"))) {
            if (this.poClientAddress.getEditMode() == 1 && this.poClientAddress
                    .getAddressId().equals(getValue("sAddrssID"))) {
                return this.poClientAddress;
            }
            this.poJSON = this.poClientAddress.openRecord((String) getValue("sAddrssID"));
            if ("success".equals(this.poJSON.get("result"))) {
                return this.poClientAddress;
            }
            this.poClientAddress.initialize();
            return this.poClientAddress;
        }
        poClientAddress.initialize();
        return this.poClientAddress;
    }

    public Model_Client_Institution_Contact ClientInstitutionContact() throws SQLException, GuanzonException {
        if (!"".equals(getValue("sContctID"))) {
            if (this.poClientInstitutionContact.getEditMode() == 1 && this.poClientInstitutionContact
                    .getClientId().equals(getValue("sContctID"))) {
                return this.poClientInstitutionContact;
            }
            this.poJSON = this.poClientInstitutionContact.openRecord((String) getValue("sContctID"));
            if ("success".equals(this.poJSON.get("result"))) {
                return this.poClientInstitutionContact;
            }
            this.poClientInstitutionContact.initialize();
            return this.poClientInstitutionContact;
        }
        poClientInstitutionContact.initialize();
        return this.poClientInstitutionContact;
    }

}
