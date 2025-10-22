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
import org.guanzon.cas.parameter.model.Model_Term;

public class Model_AP_Client_Master extends Model {

    private Model_Term poTerm;
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
            poEntity.updateNull("dCltSince");
            poEntity.updateNull("dBegDatex");
            poEntity.updateObject("cVatablex", Logical.NO);
            poEntity.updateObject("nDiscount", 0.00d);
            poEntity.updateObject("nCredLimt", 0.00d);
            poEntity.updateObject("nABalance", 0.00d);
            poEntity.updateObject("nOBalance", 0.00d);
            poEntity.updateObject("nBegBalxx", 0.00d);
            poEntity.updateObject("nLedgerNo", 0);
            poEntity.updateObject("cAutoHold", Logical.NO);
            poEntity.updateObject("cHoldAcct", Logical.NO);
            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);
            poEntity.updateObject("dModified", poGRider.getServerDate());
            //end - assign default values

            ID = "sClientID";

            //initialize other connections
            ParamModels model = new ParamModels(poGRider);
            poCategory = model.Category();

            poClientMaster = new ClientModels(poGRider).ClientMaster();
            poClientAddress = new ClientModels(poGRider).ClientAddress();
            poClientInstitutionContact = new ClientModels(poGRider).ClientInstitutionContact();
            poTerm = new ParamModels(poGRider).Term();

            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }

    public JSONObject setClientId(String clientId) {
        return setValue("sClientID", clientId);
    }

    public String getClientId() {
        return (String) getValue("sClientID");
    }

    public JSONObject setAddressId(String addressId) {
        return setValue("sAddrssID", addressId);
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

    public JSONObject setCategoryCode(String categoryCode) {
        return setValue("sCategrCd", categoryCode);
    }

    public String getCategoryCode() {
        return (String) getValue("sCategrCd");
    }

    public JSONObject setdateClientSince(Date dateClientSince) {
        return setValue("dCltSince", dateClientSince);
    }

    public Date getdateClientSince() {
        return (Date) getValue("dCltSince");
    }

    public JSONObject setBeginningDate(Date beginningDate) {
        return setValue("dBegDatex", beginningDate);
    }

    public Date getBeginningDate() {
        return (Date) getValue("dBegDatex");
    }

    public JSONObject setBeginningBalance(Double beginningBalancce) {
        return setValue("nBegBalxx", beginningBalancce);
    }

    public Double getBeginningBalance() {
        return Double.valueOf(getValue("nBegBalxx").toString());
    }

    public JSONObject setTermId(String termId) {
        return setValue("sTermIDxx", termId);
    }

    public String getTermId() {
        return (String) getValue("sTermIDxx");
    }

    public JSONObject setDiscount(Double discount) {
        return setValue("nDiscount", discount);
    }

    public Double getDiscount() {
        return Double.valueOf(getValue("nDiscount").toString());
    }

    public JSONObject setCreditLimit(Double creditLimit) {
        return setValue("nCredLimt", creditLimit);
    }

    public Double getCreditLimit() {
        return Double.valueOf(getValue("nCredLimt").toString());
    }

    public JSONObject setAccountBalance(Double accountBalance) {
        return setValue("nABalance", accountBalance);
    }

    public Double getAccountBalance() {
        return Double.valueOf(getValue("nABalance").toString());
    }

    public JSONObject setOBalance(Double oBalance) {
        return setValue("nOBalance", oBalance);
    }

    public Double getOBalance() {
        return Double.valueOf(getValue("nOBalance").toString());
    }

    public JSONObject setLedgerNo(String ledgerNo) {
        return setValue("nLedgerNo", ledgerNo);
    }

    public String getLedgerNo() {
        return (String) getValue("nLedgerNo");
    }

    public JSONObject setVatable(String vatable) {
        return setValue("cVatablex", vatable);
    }

    public String getVatable() {
        return (String) getValue("cVatablex");
    }

    public JSONObject setAutoHold(String autoHold) {
        return setValue("cAutoHold", autoHold);
    }

    public String getAutoHold() {
        return (String) getValue("cAutoHold");
    }

    public JSONObject setRecordStatus(String recordStatus) {
        return setValue("cRecdStat", recordStatus);
    }

    public String getRecordStatus() {
        return (String) getValue("cRecdStat");
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
        return "";
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

    public Model_Term Term() throws SQLException, GuanzonException {
        if (!"".equals(getValue("sTermIDxx"))) {
            if (this.poTerm.getEditMode() == 1 && this.poTerm
                    .getTermId().equals(getValue("sTermIDxx"))) {
                return this.poTerm;
            }
            this.poJSON = this.poTerm.openRecord((String) getValue("sTermIDxx"));
            if ("success".equals(this.poJSON.get("result"))) {
                return this.poTerm;
            }
            this.poTerm.initialize();
            return this.poTerm;
        }
        poTerm.initialize();
        return this.poTerm;
    }

}
