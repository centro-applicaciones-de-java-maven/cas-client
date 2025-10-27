package ph.com.guanzongroup.cas.client;

import java.sql.SQLException;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.impl.Parameter;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.Logical;
import org.json.simple.JSONObject;
import ph.com.guanzongroup.cas.constants.Tables;
import ph.com.guanzongroup.cas.core.ObjectInitiator;
import ph.com.guanzongroup.cas.model.Model_Client_Mail;

public class Client_Mail  extends Parameter{
    Model_Client_Mail poModel;
    
    @Override
    public void initialize() throws SQLException, GuanzonException {
        psRecdStat = Logical.YES;
        
        poModel = ObjectInitiator.createModel(Model_Client_Mail.class, poGRider, Tables.CLIENT_MAIL);
        
        super.initialize();
    }
    
    @Override
    public JSONObject isEntryOkay() {
        poJSON = new JSONObject();

        if (poModel.getClientId().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Client must not be empty.");
            return poJSON;
        }

        if (poModel.getEmailId().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Email address must not be empty.");
            return poJSON;
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    @Override
    public Model_Client_Mail getModel() {
        return poModel;
    }
    
    @Override
    public JSONObject searchRecord(String value, boolean byCode) throws SQLException, GuanzonException{
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»Client Name»Mail Address»Ownership»Primary",
                "sEmailIDx»xFullName»sEMailAdd»xOwnerxxx»xPrimaryx",
                "a.sEmailIDx»TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»a.sEMailAdd»CASE a.cOwnerxxx WHEN '0' THEN 'Personal' WHEN '1' THEN 'COMPANY' ELSE 'Others' END»IF(a.cPrimaryx = '1', 'Yes', 'No')",
                byCode ? 0 : 2);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sEmailIDx"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecord(String value, boolean byCode, String clientId) throws SQLException, GuanzonException{
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), "a.sClientID = " + SQLUtil.toSQL(clientId));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "ID»Client Name»Mail Address»Ownership»Primary",
                "sEmailIDx»xFullName»sEMailAdd»xOwnerxxx»xPrimaryx",
                "a.sEmailIDx»TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»a.sEMailAdd»CASE a.cOwnerxxx WHEN '0' THEN 'Personal' WHEN '1' THEN 'COMPANY' ELSE 'Others' END»IF(a.cPrimaryx = '1', 'Yes', 'No')",
                byCode ? 0 : 2);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sEmailIDx"));
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
                    "  a.sEmailIDx" +
                    ", a.sClientID" +
                    ", a.sEMailAdd" +
                    ", a.cOwnerxxx" +
                    ", a.cPrimaryx" +
                    ", a.cRecdStat" +
                    ", TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm)) xFullName" +
                    ", CASE a.cOwnerxxx WHEN '0' THEN 'Personal' WHEN '1' THEN 'COMPANY' ELSE 'Others' END xOwnerxxx" +
                    ", IF(a.cPrimaryx = '1', 'Yes', 'No') xPrimaryx" +
                " FROM Client_eMail_Address a" +
                    ", Client_Master b" +
                " WHERE a.sClientID = b.sClientID";
        
        return MiscUtil.addCondition(lsSQL, lsCondition);
    }
}