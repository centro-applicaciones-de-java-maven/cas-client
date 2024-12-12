package org.guanzon.cas.client;

import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.cas.client.model.Model_Client_Social_Media;
import org.json.simple.JSONObject;

public class Client_Social_Media  extends Parameter{
    Model_Client_Social_Media poModel;
    
    @Override
    public void initialize() {
        psRecdStat = Logical.YES;
        
        poModel = new Model_Client_Social_Media();
        poModel.setApplicationDriver(poGRider);
        poModel.setXML("Model_Client_Social_Media");
        poModel.setTableName("Client_Social_Media");
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

        if (poModel.getAccount().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Mobile number must not be empty.");
            return poJSON;
        }
        
        if (poModel.getSocMedType().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Social media platform must not be empty.");
            return poJSON;
        }
        
        return poJSON;
    }
    
    @Override
    public Model_Client_Social_Media getModel() {
        return poModel;
    }
    
    @Override
    public JSONObject searchRecord(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»Client Name»Account»Platform»Birthday",
                "sSocialID»xFullName»sAccountx»xSocialTp»dBirthDte",
                "a.sSocialID»TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»a.sAccountx»CASE a.cSocialTp WHEN '0' THEN 'Meta' WHEN '1' THEN 'Instagram' WHEN '2' THEN 'X' ELSE '' END»b.dBirthDte",
                byCode ? 0 : 2);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sSocialID"));
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
                "ID»Client Name»Account»Platform»Birthday",
                "sSocialID»xFullName»sAccountx»xSocialTp»dBirthDte",
                "a.sSocialID»TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»a.sAccountx»CASE a.cSocialTp WHEN '0' THEN 'Meta' WHEN '1' THEN 'Instagram' WHEN '2' THEN 'X' ELSE '' END»b.dBirthDte",
                byCode ? 0 : 2);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sSocialID"));
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
                    "  a.sSocialID" +
                    ", a.sClientID" +
                    ", a.sAccountx" +
                    ", a.cSocialTp" +
                    ", a.cRecdStat" +
                    ", TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm)) xFullName" +
                    ", CASE a.cSocialTp WHEN '0' THEN 'Meta' WHEN '1' THEN 'Instagram' WHEN '2' THEN 'X' ELSE '' END xSocialTp" +
                    ", b.dBirthDte" +
                " FROM Client_Social_Media a" +
                    ", Client_Master b" +
                " WHERE a.sClientID = b.sClientID";
        
        return MiscUtil.addCondition(lsSQL, lsCondition);
    }
}