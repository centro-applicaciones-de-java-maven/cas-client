package ph.com.guanzongroup.cas.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.impl.Parameter;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.cas.client.validator.APClientValidatorFactory;
import org.json.simple.JSONObject;
import ph.com.guanzongroup.cas.constants.Tables;
import ph.com.guanzongroup.cas.core.ObjectInitiator;
import ph.com.guanzongroup.cas.iface.GValidator;
import ph.com.guanzongroup.cas.model.Model_AP_Client_Ledger;
import ph.com.guanzongroup.cas.model.Model_AP_Client_Master;

public class AP_Client_Master extends Parameter {
    private Model_AP_Client_Master poModel;
    private List<Model_AP_Client_Ledger> paLedger;

    @SuppressWarnings("unchecked")
    public List<Model_AP_Client_Ledger> getLedgerList() {
        return (List<Model_AP_Client_Ledger>) (List<?>) paLedger;
    }

    @Override
    public void initialize() throws SQLException, GuanzonException {
        psRecdStat = TransactionStatus.STATE_OPEN;
        paLedger = new ArrayList<>();

        poModel = ObjectInitiator.createModel(Model_AP_Client_Master.class, poGRider, Tables.AP_CLIENT_MASTER);
        
        super.initialize();
    }

    @Override
    public JSONObject isEntryOkay() throws SQLException, GuanzonException, CloneNotSupportedException {
        poJSON = new JSONObject();

        //initialize validator
        GValidator loValidator = APClientValidatorFactory.make(poGRider.getIndustry());
        
        //initialize params for app validator
        loValidator.setApplicationDriver(poGRider);
        loValidator.setMaster(poModel);
        
        //validate
        poJSON = loValidator.validate();
        
        //if validation not success
        if ("error".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        //set modified date and id
        poModel.setModifyingId(poGRider.getUserID());
        poModel.setModifiedDate(poGRider.getServerDate());

        poJSON.put("result", "success");
        return poJSON;
    }

    @Override
    public Model_AP_Client_Master getModel() {
        return poModel;
    }

    @Override
    public JSONObject searchRecord(String value, boolean byCode) throws SQLException, GuanzonException {
        String lsSQL = getSQ_Browse();

        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Client ID»Name»Address»Contact Person",
                "sClientID»sCompnyNm»xAddressx»xContactP",
                "a.sClientID»b.sCompnyNm»TRIM(CONCAT(c.sHouseNox, ', ', c.sAddressx, ', ', c.sBrgyIDxx, ', ', c.sTownIDxx))»d.sCPerson1",
                byCode ? 0 : 1);

        if (poJSON != null) {
            //clear retrieve ledger
            paLedger.clear();

            return poModel.openRecord((String) poJSON.get("sClientID"));
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

            lsCondition = "a.cRecdStat IN (" + lsCondition.substring(2) + ")";
        } else {
            lsCondition = "a.cRecdStat = " + SQLUtil.toSQL(psRecdStat);
        }

        lsSQL = "SELECT" +
                    "  a.sClientID" +
                    ", a.sAddrssID" +
                    ", a.sContctID" +
                    ", a.sCategrCd" +
                    ", a.dCltSince" +
                    ", a.dBegDatex" +
                    ", a.nBegBalxx" +
                    ", a.sTermIDxx" +
                    ", a.nDiscount" +
                    ", a.nCredLimt" +
                    ", a.nABalance" +
                    ", a.nOBalance" +
                    ", a.nLedgerNo" +
                    ", a.cVatablex" +
                    ", a.cHoldAcct" +
                    ", a.cAutoHold" +
                    ", a.cRecdStat" +
                    ", b.sCompnyNm" +
                    ", c.sAddrssID" +
                    ", d.sMobileNo" +
                    ", TRIM(CONCAT(c.sHouseNox, ', ', c.sAddressx, ', ', c.sBrgyIDxx, ', ', c.sTownIDxx)) xAddressx" +
                    ", d.sCPerson1 xContactP" +
                " FROM AP_Client_Master a" +
                    " LEFT JOIN Client_Master b" +
                        " ON a.sClientID = b.sClientID" +
                    " LEFT JOIN Client_Address c" +
                        " ON a.sAddrssID = c.sAddrssID" +
                    " LEFT JOIN Client_Institution_Contact_Person d" +
                        " ON a.sContctID = d.sContctID";

        if (!lsCondition.isEmpty()) {
            lsSQL = MiscUtil.addCondition(lsSQL, lsCondition);
        }

        return lsSQL;
    }
//
//    public JSONObject searchClient(String fsValue, boolean fbByCode) throws SQLException, GuanzonException {
//        JSONObject loJSON;
//
//        if (fbByCode) {
//            if (fsValue.equals(getModel().getClientId())) {
//                loJSON = new JSONObject();
//                loJSON.put("result", "success");
//                return loJSON;
//            }
//        } else {
//            if (getModel().Client().getCompanyName() != null && !getModel().Client().getCompanyName().isEmpty()) {
//                if (fsValue.equals(getModel().Client().getCompanyName())) {
//                    loJSON = new JSONObject();
//                    loJSON.put("result", "success");
//                    return loJSON;
//                }
//            }
//        }
//
//        String lsSQL = "SELECT"
//                + " a.sClientID"
//                + " , a.sCompnyNm"
//                + " , b.sAddrssID"
//                + " , TRIM(CONCAT (IFNULL(b.sHouseNox,''),', ',IFNULL(b.sAddressx,''),', ',IFNULL(b.sBrgyIDxx,''),', ',IFNULL(b.sTownIDxx,''))) xAddressx"
//                + "     FROM Client_Master a "
//                + "      LEFT JOIN Client_Address b ON a.sClientID = b.sClientID"
//                + "     WHERE a.cRecdStat = " + SQLUtil.toSQL(RecordStatus.ACTIVE)
//                + "         AND a.cClientTp = " + SQLUtil.toSQL(Logical.YES);
//
//        if (fbByCode) {
//            lsSQL = MiscUtil.addCondition(lsSQL, "a.sClientID = " + SQLUtil.toSQL(fsValue));
//        } else {
//            lsSQL = MiscUtil.addCondition(lsSQL, "a.sCompnyNm LIKE " + SQLUtil.toSQL(fsValue + "%"));
//        }
//        System.out.println("ClientSearch = " + lsSQL);
//        loJSON = ShowDialogFX.Search(
//                poGRider,
//                lsSQL,
//                fsValue,
//                "ID»Company»Address",
//                "sClientID»sCompnyNm»xAddressx",
//                "a.sClientID»a.sCompnyNm»TRIM(CONCAT(b.sHouseNox, ', ', b.sAddressx, ', ', b.sBrgyIDxx, ', ', b.sTownIDxx))",
//                fbByCode ? 0 : 1);
//
//        if (loJSON != null) {
//            loJSON.put("result", "success");
//            getModel().setClientId((String) loJSON.get("sClientID"));
//            getModel().setAddressId(loJSON.get("sAddrssID") != null ? (String) loJSON.get("sAddrssID") : "");
//        } else {
//            loJSON.put("result", "success");
//            loJSON.put("message", "No record selected.");
//        }
//
//        return loJSON;
//    }

    public JSONObject searchTerm(String fsValue, boolean fbByCode) throws SQLException, GuanzonException {
        JSONObject loJSON;

        String lsSQL = "SELECT"
                + " sTermCode"
                + " , sDescript"
                + " FROM Term WHERE cRecdStat = '1'";

        if (fbByCode) {
            lsSQL = MiscUtil.addCondition(lsSQL, "sTermCode = " + SQLUtil.toSQL(fsValue));
        } else {
            lsSQL = MiscUtil.addCondition(lsSQL, "sDescript LIKE " + SQLUtil.toSQL(fsValue + "%"));
        }

        loJSON = ShowDialogFX.Search(
                poGRider,
                lsSQL,
                fsValue,
                "Code»Description",
                "sTermCode»sDescript",
                "sTermCode»sDescript",
                fbByCode ? 0 : 1);

        if (loJSON != null) {
            loJSON.put("result", "success");
            getModel().setTermId((String) loJSON.get("sTermCode"));
        } else {
            loJSON.put("result", "success");
            loJSON.put("message", "No record selected.");
        }

        return loJSON;
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

    public JSONObject loadLedgerList()
            throws SQLException, GuanzonException, CloneNotSupportedException {

        if (getModel().getClientId() == null
                || getModel().getClientId().isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("message", "No record Loaded. Please load Client");
            return poJSON;
        }
        paLedger.clear();
        String lsSQL = "SELECT "
                + " a.sClientID"
                + ", b.nLedgerNo"
                + ", b.dTransact"
                + " FROM AP_Client_Master a "
                + " LEFT JOIN AP_Client_Ledger b ON a.sClientID = b.sClientID "
                + " ORDER BY b.nLedgerNo";

        lsSQL = MiscUtil.addCondition(lsSQL, "a.sClientID=" + SQLUtil.toSQL(getModel().getClientId()));
        ResultSet loRS = poGRider.executeQuery(lsSQL);
        System.out.println("Load list query is " + lsSQL);

        if (MiscUtil.RecordCount(loRS)
                <= 0) {
            poJSON.put("result", "error");
            poJSON.put("message", "No record found.");
            return poJSON;
        }

        while (loRS.next()) {
            Model_AP_Client_Ledger loLedger = ObjectInitiator.createModel(Model_AP_Client_Ledger.class, poGRider, Tables.AP_CLIENT_LEDGER);
            poJSON = loLedger.openRecord(loRS.getString("sClientID"), loRS.getString("nLedgerNo"));

            if ("success".equals((String) poJSON.get("result"))) {
                paLedger.add(loLedger);
            } else {
                return poJSON;
            }
        }

        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }
}
