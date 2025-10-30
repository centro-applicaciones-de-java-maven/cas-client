package org.guanzon.cas.client.account;

import java.sql.SQLException;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.ClientType;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.appdriver.iface.GValidator;
import org.guanzon.cas.client.ClientGUI;
import org.guanzon.cas.client.model.Model_Account_Client_Accreditation;
import org.guanzon.cas.client.services.ClientControllers;
import org.guanzon.cas.client.services.ClientModels;
import org.guanzon.cas.client.validator.ClientAccreditationValidatorFactory;
import org.json.simple.JSONObject;

public class Account_Accreditation extends Parameter {

    private Model_Account_Client_Accreditation poModel;
    private String psApprovalUser = "";

    @Override
    public void initialize() throws SQLException, GuanzonException {
        super.initialize();

        psRecdStat = TransactionStatus.STATE_OPEN;

        ClientModels model = new ClientModels(poGRider);
        poModel = model.ClientAccreditation();
    }

    @Override
    public JSONObject isEntryOkay() throws SQLException, GuanzonException, CloneNotSupportedException {
        poJSON = new JSONObject();
        
        //initialize validator
        GValidator loValidator = ClientAccreditationValidatorFactory.make(poGRider.getIndustry());
        
        //initialize params for app validator
        loValidator.setApplicationDriver(poGRider);
        loValidator.setTransactionStatus(poModel.getRecordStatus());
        loValidator.setMaster(poModel);
        
        //validate
        poJSON = loValidator.validate();
        
        //if validation not success
        if ("error".equals((String) poJSON.get("result"))) {
            return poJSON;
        }
        
        //if validator requires approval
        if (poJSON.containsKey("isRequiredApproval") && Boolean.TRUE.equals(poJSON.get("isRequiredApproval"))) {
            
            //get approval from approving officer
            poJSON = ShowDialogFX.getUserApproval(poGRider);
            if ("error".equals((String) poJSON.get("result"))) {
                return poJSON;
            }
            
            //if success, return approving officer user id
            psApprovalUser = poJSON.get("sUserIDxx") != null
                    ? poJSON.get("sUserIDxx").toString()
                    : poGRider.getUserID();
            
            //add approver's id to result
            poJSON.put("sApproved", psApprovalUser);
        }

        //initialize model date modified and modifier
        poModel.setModifyingId((poGRider.getUserID()));
        poModel.setModifiedDate(poGRider.getServerDate());

        poJSON.put("result", "success");
        return poJSON;
    }

    @Override
    public Model_Account_Client_Accreditation getModel() {
        return poModel;
    }

    @Override
    public JSONObject searchRecord(String value, boolean byCode) throws SQLException, GuanzonException {
        String lsSQL = getSQ_Browse();

        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Transaction No»Date»Name",
                "sTransNox»dTransact»sCompnyNm",
                "sTransNox»dTransact»sCompnyNm",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sTransNox"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
      @Override
    public String getSQ_Browse() {
        String lsSQL;
        String lsCondition = "";

        if (psRecdStat.length() > 1) {
            for (int lnCtr = 0; lnCtr <= psRecdStat.length() - 1; lnCtr++) {
                lsCondition += ", " + SQLUtil.toSQL(Character.toString(psRecdStat.charAt(lnCtr)));
            }

            lsCondition = "a.cTranStat IN (" + lsCondition.substring(2) + ")";
        } else {
            lsCondition = "a.cTranStat = " + SQLUtil.toSQL(psRecdStat);
        }

        lsSQL = " SELECT "
                + " a.sTransNox, "
                + " a.cAcctType, "
                + " a.sClientID, "
                + " a.sAddrssID, "
                + " a.sContctID, "
                + " a.dTransact, "
                + " a.cAcctType, "
                + " a.sRemarksx, "
                + " a.cTranType, "
                + " a.sCategrCd, "
                + " a.cTranStat, "
                + " b.sCompnyNm, "
                + " c.sAddrssID, "
                + " d.sMobileNo, "
                + " IFNULL(CONCAT(c.sHouseNox, ', ', c.sAddressx, ', ', c.sBrgyIDxx, ', ', c.sTownIDxx), '') xAddressx"
                + " FROM Account_Client_Accreditation a "
                + " LEFT JOIN Client_Master b "
                + " ON a.sClientID = b.sClientID "
                + " LEFT JOIN Client_Address c "
                + " ON a.sAddrssID = c.sAddrssID "
                + " LEFT JOIN Client_Institution_Contact_Person d "
                + " ON a.sContctID = d.sContctID ";

        if (!lsCondition.isEmpty()) {
            lsSQL = MiscUtil.addCondition(lsSQL, lsCondition);
        }

        return lsSQL;
    }

    public JSONObject searchCategory(String fsValue, boolean fbByCode) throws SQLException, GuanzonException {
        JSONObject loJSON;

        String lsSQL = "SELECT"
                + " sCategrCd"
                + ", sDescript"
                + " FROM Category  WHERE cRecdStat = '1'";

        if (fbByCode) {
            lsSQL = MiscUtil.addCondition(lsSQL, "sCategrCd = " + SQLUtil.toSQL(fsValue));
        } else {
            lsSQL = MiscUtil.addCondition(lsSQL, "sDescript LIKE " + SQLUtil.toSQL(fsValue + "%"));
        }
        System.out.println("Category Query = " + lsSQL);
        loJSON = ShowDialogFX.Search(
                poGRider,
                lsSQL,
                fsValue,
                "Code»Description",
                "sCategrCd»sDescript",
                "sCategrCd»sDescript",
                fbByCode ? 0 : 1);

        if (loJSON != null) {
            loJSON.put("result", "success");
            getModel().setCategoryCode((String) loJSON.get("sCategrCd"));
        } else {
            loJSON.put("result", "success");
            loJSON.put("message", "No record selected.");
        }

        return loJSON;
    }

    public JSONObject searchClient(String fsValue, boolean fbByCode) throws SQLException, GuanzonException, Exception {
        JSONObject loJSON = new JSONObject();

        if (fbByCode) {
            if (fsValue.equals(getModel().getClientId())) {
                loJSON.put("result", "success");
                return loJSON;
            }else{
                loJSON.put("result", "error");
                loJSON.put("message", "Client not found.");
                return loJSON;
            }
        } else {
            
            String lsSQL = "SELECT"
                + " sClientID"
                + ", sCompnyNm"
                + " FROM Client_Master"
                + " WHERE cRecdStat= '1'";
            
            loJSON = ShowDialogFX.Search(poGRider, 
                    lsSQL, 
                    fsValue, 
                    "Client ID»Client Name", 
                    "sClientID»sCompnyNm",
                    "sClientID»sCompnyNm",
                    fbByCode ? 0 : 1);
            
            //initialize Client GUI
            ClientGUI loClient = new ClientGUI();

            loClient.setGRider(poGRider);
            loClient.setLogWrapper(null);

            //filter client type 
            loClient.setClientType(ClientType.INSTITUTION);
            
            //searchRecord(fsValue,fbByCode) will run make sure to set client and bycode
            //bycode true client id
            //bycode false company

            //set search by code
            loClient.setByCode(fbByCode);
            
            if (loJSON != null) {
                getModel().setClientId(loJSON.get("sClientID").toString());
                
                //set client id
                loClient.setClientId(getModel().getClientId());
                
                //search record
                loClient.searchRecord(getModel().Client().getCompanyName(), fbByCode);
            }else {
                loClient.setClientId("");
            }

            //load record
            CommonUtils.showModal(loClient);

            //load if button 
            if (!loClient.isCancelled()) {
                
                System.out.println("Client Id: " + loClient.getClient().getModel().getClientId());
                System.out.println("address Id: " + loClient.getClient().Address(0).getClientId());
                System.out.println("contact person Id: " + loClient.getClient().InstiContact(0).getClientId());

                getModel().setClientId(loClient.getClient().getModel().getClientId());
                getModel().setAddressId(loClient.getClient().Address(0).getClientId() != null ? loClient.getClient().Address(0).getClientId() : "");
                getModel().setContactId(loClient.getClient().InstiContact(0).getClientId() != null ? loClient.getClient().InstiContact(0).getClientId() : "");
                
                loJSON.put("result", "success");
                return loJSON;
            } else {
                loJSON.put("result", "error");
                loJSON.put("message", "No record selected.");
                return loJSON;
            }
        }
    }

    public JSONObject searchClientContact(String fsValue, boolean fbByCode) throws SQLException, GuanzonException {
        JSONObject loJSON;

        String lsSQL = "SELECT"
                + " a.sContctID"
                + " ,a.sCPerson1"
                + " ,a.sMobileNo"
                + " , b.sClientID"
                + " , b.sCompnyNm"
                + " FROM Client_Institution_Contact_Person a "
                + " LEFT JOIN Client_Master b ON a.sClientID = b.sClientID"
                + " WHERE a.cRecdStat = " + SQLUtil.toSQL(RecordStatus.ACTIVE)
                + " AND b.cClientTp = " + SQLUtil.toSQL(Logical.NO);

        if (fbByCode) {
            lsSQL = MiscUtil.addCondition(lsSQL, "a.sContctID = " + SQLUtil.toSQL(fsValue));
        } else {
            lsSQL = MiscUtil.addCondition(lsSQL, "b.sCompnyNm LIKE " + SQLUtil.toSQL(fsValue + "%"));
        }
        System.out.println("Client Contact Search = " + lsSQL);
        loJSON = ShowDialogFX.Search(
                poGRider,
                lsSQL,
                fsValue,
                "ID»Contact Name»Contact Person»Mobile no",
                "sContctID»sCompnyNm»sCPerson1»sMobileNo",
                "a.sContctID»b.sCompnyNm»a.sCPerson1»a.sMobileNo",
                fbByCode ? 0 : 1);

        if (loJSON != null) {
            loJSON.put("result", "success");
            getModel().setContactId((String) loJSON.get("sContctID"));
        } else {
            loJSON.put("result", "success");
            loJSON.put("message", "No record selected.");
        }

        return loJSON;
    }

    public JSONObject CloseTransaction() throws SQLException, GuanzonException, CloneNotSupportedException {
        poJSON = new JSONObject();

        //initialize validator
        poJSON = isEntryOkay();
        if ("error".equals((String) poJSON.get("result"))) {
            return poJSON;
        }
        poGRider.beginTrans("UPDATE STATUS", "ConfirmTransaction", SOURCE_CODE, getModel().getTransactionNo());

        //if Accreditation 
        if (getModel().getAccountType().equals("0")) {
            
            //initialize AP_Client_Object
            AP_Client_Master loObject = getAPClientMaster(getModel().getClientId());

            //check editmode if new or update
            if (loObject.getEditMode() == EditMode.ADDNEW) {
                
                loObject.getModel().setClientId(poModel.getClientId());
                loObject.getModel().setAddressId(poModel.getAddressId());
                loObject.getModel().setContactId(poModel.getContactId());
                loObject.getModel().setCategoryCode(poModel.getCategoryCode());
                loObject.getModel().setBeginningDate(poGRider.getServerDate());
                
                //if blacklisting set  Inactive record 
                loObject.getModel().setRecordStatus(getModel().getTransactionType().equals("1") ? "0" : "1");
                
                poJSON = loObject.saveRecord();
                
                if ("error".equals((String) poJSON.get("result"))) {
                    return poJSON;
                }
            } else {
                
                //make sure its onready mode
                if (loObject.getEditMode() == EditMode.READY) {
                    
                    //enter to update
                    loObject.updateRecord();
                    loObject.getModel().setClientId(poModel.getClientId());
                    loObject.getModel().setAddressId(poModel.getAddressId());
                    loObject.getModel().setContactId(poModel.getContactId());
                    loObject.getModel().setCategoryCode(poModel.getCategoryCode());
                    loObject.getModel().setBeginningDate(poGRider.getServerDate());
                    
                    //if blacklisting set  Inactive record 
                    loObject.getModel().setRecordStatus(getModel().getTransactionType().equals("1") ? "0" : "1");
                    poJSON = loObject.saveRecord();
                    
                    if ("error".equals((String) poJSON.get("result"))) {
                        return poJSON;
                    }
                }

            }
        }//add here for other type

        String lsSQL = "UPDATE "
                + poModel.getTable()
                + " SET   cTranStat = " + SQLUtil.toSQL("1")
                + " WHERE sTransNox = " + SQLUtil.toSQL(getModel().getTransactionNo());

        Long lnResult = poGRider.executeQuery(lsSQL,
                getModel().getTable(),
                poGRider.getBranchCode(), "", "");
        if (lnResult <= 0L) {
            poGRider.rollbackTrans();

            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "Error updating the transaction status.");
            return poJSON;
        }

        poGRider.commitTrans();

        openRecord(getModel().getTransactionNo());
        
        poJSON = new JSONObject();
        poJSON.put("result", "success");
        poJSON.put("message", "Transaction confirmed successfully.");

        return poJSON;
    }

    public JSONObject VoidTransaction() throws SQLException, GuanzonException, CloneNotSupportedException {
        poJSON = new JSONObject();

        //initialize validator
        poJSON = isEntryOkay();
        if ("error".equals((String) poJSON.get("result"))) {
            return poJSON;
        }
        poGRider.beginTrans("UPDATE STATUS", "VoidTransaction", SOURCE_CODE, getModel().getTransactionNo());

        String lsSQL = "UPDATE "
                + poModel.getTable()
                + " SET cTranStat = " + SQLUtil.toSQL("4")
                + " WHERE sTransNox = " + SQLUtil.toSQL(getModel().getTransactionNo());

        Long lnResult = poGRider.executeQuery(lsSQL,
                getModel().getTable(),
                poGRider.getBranchCode(), "", "");
        if (lnResult <= 0L) {
            poGRider.rollbackTrans();

            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "Error updating the transaction status.");
            return poJSON;
        }

        poGRider.commitTrans();

        openRecord(getModel().getTransactionNo());
        poJSON = new JSONObject();
        poJSON.put("result", "success");
        poJSON.put("message", "Transaction voided successfully.");

        return poJSON;
    }

    public AP_Client_Master getAPClientMaster(String foClientID)
            throws GuanzonException, SQLException {
        AP_Client_Master loObject = new ClientControllers(poGRider, null).APClientMaster();
        poJSON = loObject.openRecord(foClientID);
        if ("error".equals((String) poJSON.get("result"))) {
            loObject.newRecord();
        }
        loObject.setWithParentClass(true);
        return loObject;
    }
}
