package org.guanzon.cas.client.account;

import java.sql.SQLException;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.cas.client.model.Model_Account_Client_Accreditation;
import org.guanzon.cas.client.services.ClientModels;
import org.json.simple.JSONObject;

public class Account_Accreditation extends Parameter{
    Model_Account_Client_Accreditation poModel;
    
    @Override
    public void initialize() throws SQLException, GuanzonException {
        super.initialize();
        
        psRecdStat = TransactionStatus.STATE_OPEN;
        
        ClientModels model = new ClientModels(poGRider);
        poModel = model.ClientAccreditation();  
    }
    
    @Override
    public JSONObject isEntryOkay() throws SQLException, GuanzonException, CloneNotSupportedException{
        poJSON = new JSONObject();

        if (poModel.getAccountType().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Account type must not be empty.");
            return poJSON;
        }

        if (poModel.getClientId().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Client must not be empty.");
            return poJSON;
        }

        if (poModel.getContactId().isEmpty()){
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
    public JSONObject searchRecord(String value, boolean byCode) throws SQLException, GuanzonException{
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
    public String getSQ_Browse(){
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
        
        if (!lsCondition.isEmpty()) lsSQL = MiscUtil.addCondition(lsSQL, lsCondition);
        
        return lsSQL;
    }
    public JSONObject ConfirmTransaction() {
        poJSON = new JSONObject();
        return poJSON;
    }

    public JSONObject VoidTransaction() {
        poJSON = new JSONObject();
        return poJSON;
    }
}