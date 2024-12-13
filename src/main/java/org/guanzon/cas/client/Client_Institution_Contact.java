package org.guanzon.cas.client;

import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.cas.client.model.Model_Client_Institution_Contact;
import org.json.simple.JSONObject;

public class Client_Institution_Contact  extends Parameter{
    Model_Client_Institution_Contact poModel;
    
    @Override
    public void initialize() {
        psRecdStat = Logical.YES;
        
        poModel = new Model_Client_Institution_Contact();
        poModel.setApplicationDriver(poGRider);
        poModel.setXML("Model_Client_Institution_Contact_Person");
        poModel.setTableName("Client_Institution_Contact_Person");
        poModel.initialize();
    }
    
    @Override
    public JSONObject isEntryOkay() {
        poJSON = new JSONObject();

        if (poModel.getClientId().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Client must not be empty.");
            return poJSON;
        }

        if (poModel.getContactPersonName().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Contact person must not be empty.");
            return poJSON;
        }
        
        if (poModel.getContactPersonPosition().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Contact person position must not be empty.");
            return poJSON;
        }
        
        if (poModel.getMobileNo().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Contact person mobile must not be empty.");
            return poJSON;
        }
        
        if (poModel.getMailAddress().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Contact person email address must not be empty.");
            return poJSON;
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    @Override
    public Model_Client_Institution_Contact getModel() {
        return poModel;
    }
    
    @Override
    public JSONObject searchRecord(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "Company Name»Contact Person»Mobile Phone»Mail Address»Primary»ID",
                "xFullName»sCPerson1»sMobileNo»sEMailAdd»xPrimaryx»sContctID",
                "TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»a.sCPerson1»a.sMobileNo»a.sEMailAdd»IF(a.cPrimaryx = '1', 'Yes', 'No')»a.sContctID",
                byCode ? 5 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sContctID"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecord(String value, boolean byCode, String clientId) {
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), "a.sClientID = " + SQLUtil.toSQL(clientId));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Company Name»Contact Person»Mobile Phone»Mail Address»Primary»ID",
                "xFullName»sCPerson1»sMobileNo»sEMailAdd»xPrimaryx»sContctID",
                "TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»a.sCPerson1»a.sMobileNo»a.sEMailAdd»IF(a.cPrimaryx = '1', 'Yes', 'No')»a.sContctID",
                byCode ? 5 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sContctID"));
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
                    "  a.sContctID" +
                    ", a.sClientID" +
                    ", a.sCPerson1" +
                    ", a.sCPPosit1" +
                    ", a.sMobileNo" +
                    ", a.sTelNoxxx" +
                    ", a.sFaxNoxxx" +
                    ", a.sEMailAdd" +
                    ", a.sAccount1" +
                    ", a.sAccount2" +
                    ", a.sAccount3" +
                    ", a.sRemarksx" +
                    ", a.cPrimaryx" +
                    ", a.cRecdStat" +
                    ", TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm)) xFullName" +
                    ", IF(a.cPrimaryx = '1', 'Yes', 'No') xPrimaryx" +
                " FROM Client_Institution_Contact_Person a" +
                    ", Client_Master b" +
                " WHERE a.sClientID = b.sClientID";
        
        return MiscUtil.addCondition(lsSQL, lsCondition);
    }
}