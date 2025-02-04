package org.guanzon.cas.client.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.cas.client.services.ClientModel;
import org.guanzon.cas.parameter.model.Model_Brand;
import org.guanzon.cas.parameter.model.Model_Category;
import org.guanzon.cas.parameter.services.ParamModels;
import org.json.simple.JSONObject;

/**
 *
 * @author Maynard
 */
public class Model_Account_Client_Accreditation extends Model {

    private Model_Category poCategory;
    private Model_Client_Master poClientMaster;
    private Model_Client_Mobile poClientMobile;
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
//            poEntity.updateString("dTransact", RecordStatus.ACTIVE);
//            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);
//            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);
//            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);
//            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);
//            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);
            poEntity.updateString("dApproved", "0000-00-00");
            
            poEntity.updateString("cTranStat", RecordStatus.INACTIVE);
            //end - assign default values

            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

            ID = ("sTransNox");

            //initialize other connections
            ParamModels model = new ParamModels(poGRider);
            poCategory = model.Category();
            
            ClientModel clientmodel = new ClientModel(poGRider);
            poClientMaster = clientmodel.ClientMaster();
            poClientAddress = clientmodel.ClientAddress();
            poClientInstitutionContact = clientmodel.ClientInstitutionContact();            
            poClientMobile = clientmodel.ClientMobile();
            
            
            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }

    public Model_Category Category() {
        if (!"".equals((String) getValue("sCategrCd"))) {
            if (poCategory.getEditMode() == EditMode.READY
                    && poCategory.getCategoryId().equals((String) getValue("sCategrCd"))) {
                return poCategory;
            } else {
                poJSON = poCategory.openRecord((String) getValue("sCategrCd"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poCategory;
                } else {
                    poCategory.initialize();
                    return poCategory;
                }
            }
        } else {
            poCategory.initialize();
            return poCategory;
        }
    }
    
    public Model_Client_Master ClientMaster() {
        System.out.println("Model_Client_Master == " + (String) getValue("sClientID"));
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
    
    public Model_Client_Address ClientAddress() {
         System.out.println("Model_Client_Address == " + (String) getValue("sClientID"));
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
    
    
    
        public Model_Client_Institution_Contact ClientInstitutionContact() {
         System.out.println("Model_Client_Institution_Contact == " + (String) getValue("sClientID"));            
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
        public Model_Client_Mobile ClientMobile() {
        if (!"".equals((String) getValue("sClientID"))) {
            if (poClientMobile.getEditMode() == EditMode.READY
                    && poClientMobile.getClientId().equals((String) getValue("sContctID"))) {
                return poClientMobile;
            } else {
                poJSON = poClientMobile.openRecord((String) getValue("sContctID"));

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
        return MiscUtil.getNextCode(getTable(), ID, true, poGRider.getConnection(), poGRider.getBranchCode());
    }
}
