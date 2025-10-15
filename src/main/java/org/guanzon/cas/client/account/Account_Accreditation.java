package org.guanzon.cas.client.account;

import java.sql.SQLException;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.appdriver.constant.UserRight;
import org.guanzon.cas.client.model.Model_Account_Client_Accreditation;
import org.guanzon.cas.client.services.ClientModels;
import org.json.simple.JSONObject;

public class Account_Accreditation extends Parameter {

    Model_Account_Client_Accreditation poModel;

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

        if (poModel.getAccountType().isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("message", "Account type must not be empty.");
            return poJSON;
        }

        if (poModel.getClientId().isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("message", "Client must not be empty.");
            return poJSON;
        }

        if (poModel.getContactId().isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("message", "Contact must not be empty.");
            return poJSON;
        }

        poModel.setModifyingId(poGRider.Encrypt(poGRider.getUserID()));
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

    public JSONObject searchClient(String fsValue, boolean fbByCode) throws SQLException, GuanzonException {
        JSONObject loJSON;

        if (fbByCode) {
            if (fsValue.equals(getModel().getClientId())) {
                loJSON = new JSONObject();
                loJSON.put("result", "success");
                return loJSON;
            }
        } else {
            if (getModel().Client().getCompanyName() != null && !getModel().Client().getCompanyName().isEmpty()) {
                if (fsValue.equals(getModel().Client().getCompanyName())) {
                    loJSON = new JSONObject();
                    loJSON.put("result", "success");
                    return loJSON;
                }
            }
        }

        String lsSQL = "SELECT"
                + " a.sClientID"
                + " , a.sCompnyNm"
                + " , b.sAddrssID"
                + " , TRIM(CONCAT (IFNULL(b.sHouseNox,''),', ',IFNULL(b.sAddressx,''),', ',IFNULL(b.sBrgyIDxx,''),', ',IFNULL(b.sTownIDxx,''))) xAddressx"
                + "     FROM Client_Master a "
                + "      LEFT JOIN Client_Address b ON a.sClientID = b.sClientID"
                + "     WHERE a.cRecdStat = " + SQLUtil.toSQL(RecordStatus.ACTIVE)
                + "         AND a.cClientTp = " + SQLUtil.toSQL(Logical.YES);

        if (fbByCode) {
            lsSQL = MiscUtil.addCondition(lsSQL, "a.sClientID = " + SQLUtil.toSQL(fsValue));
        } else {
            lsSQL = MiscUtil.addCondition(lsSQL, "a.sCompnyNm LIKE " + SQLUtil.toSQL(fsValue + "%"));
        }
        System.out.println("ClientSearch = " + lsSQL);
        loJSON = ShowDialogFX.Search(
                poGRider,
                lsSQL,
                fsValue,
                "ID»Company»Address",
                "sClientID»sCompnyNm»xAddressx",
                "a.sClientID»a.sCompnyNm»TRIM(CONCAT(b.sHouseNox, ', ', b.sAddressx, ', ', b.sBrgyIDxx, ', ', b.sTownIDxx))",
                fbByCode ? 0 : 1);

        if (loJSON != null) {
            loJSON.put("result", "success");
            getModel().setClientId((String) loJSON.get("sClientID"));
            getModel().setAddressId(loJSON.get("sAddrssID") != null ? (String) loJSON.get("sAddrssID") : "");
        } else {
            loJSON.put("result", "success");
            loJSON.put("message", "No record selected.");
        }

        return loJSON;
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

        lsSQL = " SELECT " +
                    " a.sTransNox, " +
                    " a.cAcctType, " +
                    " a.sClientID, " +
                    " a.sAddrssID, " +
                    " a.sContctID, " +
                    " a.dTransact, " +
                    " a.cAcctType, " +
                    " a.sRemarksx, " +
                    " a.cTranType, " +
                    " a.sCategrCd, " +
                    " a.cTranStat, " +
                    " b.sCompnyNm, " +
                    " c.sAddrssID, " +
                    " d.sMobileNo, " +
                    " IFNULL(CONCAT(c.sHouseNox, ', ', c.sAddressx, ', ', c.sBrgyIDxx, ', ', c.sTownIDxx), '') xAddressx" +
                " FROM Account_Client_Accreditation a " +
                    " LEFT JOIN Client_Master b " +
                      " ON a.sClientID = b.sClientID " +
                    " LEFT JOIN Client_Address c " +
                      " ON a.sAddrssID = c.sAddrssID " +
                    " LEFT JOIN Client_Institution_Contact_Person d " +
                      " ON a.sContctID = d.sContctID " ;

        if (!lsCondition.isEmpty()) {
            lsSQL = MiscUtil.addCondition(lsSQL, lsCondition);
        }

        return lsSQL;
    }

    public JSONObject CloseTransaction() throws SQLException, GuanzonException, CloneNotSupportedException {
        poJSON = new JSONObject();

        if ("1".equals((String) poModel.getValue("cTranStat"))) {
            poJSON.put("result", "error");
            poJSON.put("message", "Transaction was already confirmed.");
            return poJSON;
        }
        if (poGRider.getUserLevel() <= UserRight.ENCODER) {
            poJSON = ShowDialogFX.getUserApproval(poGRider);
            if ("error".equals((String) poJSON.get("result"))) {
                return poJSON;
            } else {
                if (Integer.parseInt(poJSON.get("nUserLevl").toString()) <= UserRight.ENCODER) {
                    poJSON.put("result", "error");
                    poJSON.put("message", "User is not an authorized approving officer.");
                    return poJSON;
                }
            }
        }
        //validator
        poJSON = isEntryOkay();
        if ("error".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        poGRider.beginTrans("UPDATE STATUS", "ConfirmTransaction", SOURCE_CODE, getModel().getTransactionNo());

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

        if ("4".equals((String) poModel.getValue("cTranStat"))) {
            poJSON.put("result", "error");
            poJSON.put("message", "Transaction was already voided.");
            return poJSON;
        }else if ("3".equals((String) poModel.getValue("cTranStat"))) {
            poJSON.put("result", "error");
            poJSON.put("message", "Transaction was already cancelled.");
            return poJSON;
        }

        //validator
        poJSON = isEntryOkay();
        if ("error".equals((String) poJSON.get("result"))) {
            return poJSON;
        }
        poGRider.beginTrans("UPDATE STATUS", "VoidTransaction", SOURCE_CODE, getModel().getTransactionNo());

        String lsSQL = "UPDATE "
                + poModel.getTable()
                + " SET   cTranStat = " + SQLUtil.toSQL("4")
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

    public JSONObject CancelTransaction() throws SQLException, GuanzonException, CloneNotSupportedException {
        poJSON = new JSONObject();

        if ("3".equals((String) poModel.getValue("cTranStat"))) {
            poJSON.put("result", "error");
            poJSON.put("message", "Transaction was already cancelled.");
            return poJSON;
        }

        if (poGRider.getUserLevel() <= UserRight.ENCODER) {
            poJSON = ShowDialogFX.getUserApproval(poGRider);
            if ("error".equals((String) poJSON.get("result"))) {
                return poJSON;
            } else {
                if (Integer.parseInt(poJSON.get("nUserLevl").toString()) <= UserRight.ENCODER) {
                    poJSON.put("result", "error");
                    poJSON.put("message", "User is not an authorized approving officer.");
                    return poJSON;
                }
            }
        }
        //validator
        poJSON = isEntryOkay();
        if ("error".equals((String) poJSON.get("result"))) {
            return poJSON;
        }
        poGRider.beginTrans("UPDATE STATUS", "CancelTransaction", SOURCE_CODE, getModel().getTransactionNo());

        String lsSQL = "UPDATE "
                + poModel.getTable()
                + " SET   cTranStat = " + SQLUtil.toSQL("3")
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
        poJSON.put("message", "Transaction cancelled successfully.");

        return poJSON;
    }

}
