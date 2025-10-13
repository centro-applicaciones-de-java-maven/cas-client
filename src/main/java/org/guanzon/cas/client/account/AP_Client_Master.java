package org.guanzon.cas.client.account;

import java.sql.SQLException;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.cas.client.model.Model_AP_Client_Master;
import org.guanzon.cas.client.model.Model_Account_Client_Accreditation;
import org.guanzon.cas.client.services.ClientModels;
import org.json.simple.JSONObject;

public class AP_Client_Master extends Parameter{
    Model_AP_Client_Master poModel;
    
    @Override
    public void initialize() throws SQLException, GuanzonException {
        super.initialize();
        
        psRecdStat = TransactionStatus.STATE_OPEN;
        
        ClientModels model = new ClientModels(poGRider);
        poModel = model.APClientMaster();  
    }
    
    @Override
    public JSONObject isEntryOkay() throws SQLException, GuanzonException, CloneNotSupportedException{
        poJSON = new JSONObject();

        if (poModel.getClientId().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Client must not be empty.");
            return poJSON;
        }

        if (poModel.getAddressId().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Client address not be empty.");
            return poJSON;
        }
        
        if (poModel.getContactId().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Contact person not be empty.");
            return poJSON;
        }
        
        if (poModel.getCategoryCode().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Category not be empty.");
            return poJSON;
        }

        poModel.setModifyingId(poGRider.Encrypt(poGRider.getUserID()));
        poModel.setModifiedDate(poGRider.getServerDate());
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    @Override
    public Model_AP_Client_Master getModel() {
        return poModel;
    }
 
    @Override
    public JSONObject searchRecord(String value, boolean byCode) throws SQLException, GuanzonException{
        String lsSQL = getSQ_Browse();
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Client ID»Name»Address»Contact Person",
                "sClientID»sCompnyNm»xAddressx»xContactP",
                "a.sClientID»b.sCompnyNm»TRIM(CONCAT(c.sHouseNox, ', ', c.sAddressx, ', ', c.sBrgyIDxx, ', ', c.sTownIDxx))»d.sCPerson1",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sClientID"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    @Override
    public String getSQ_Browse(){
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
        
        if (!lsCondition.isEmpty()) lsSQL = MiscUtil.addCondition(lsSQL, lsCondition);
        
        return lsSQL;
    }
}