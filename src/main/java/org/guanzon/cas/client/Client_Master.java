package org.guanzon.cas.client;

import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.cas.client.model.Model_Client_Master;
import org.json.simple.JSONObject;

public class Client_Master  extends Parameter{
    Model_Client_Master poModel;
    String psClientTp;

    public void setClientType(String clientType) {
        psClientTp = clientType;
    }
    
    @Override
    public void initialize() {
        psRecdStat = Logical.YES;
        
        poModel = new Model_Client_Master();
        poModel.setApplicationDriver(poGRider);
        poModel.setXML("Model_Client_Master");
        poModel.setTableName("Client_Master");
        poModel.initialize();
        
        psClientTp = "10";
    }
        
    @Override
    public Model_Client_Master getModel() {
        return poModel;
    }
    
    @Override
    public JSONObject searchRecord(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»Name»Birthday»Birth Place",
                "sClientID»sCompnyNm»dBirthDte»xBirthPlc",
                "a.sBrgyIDxx»TRIM(IF(a.cClientTp = '0', CONCAT(a.sLastName, ', ', a.sFrstName, IF(TRIM(IFNull(a.sSuffixNm, '')) = '', ' ', CONCAT(' ', a.sSuffixNm, ' ')), a.sMiddName), a.sCompnyNm))»a.dBirthDte»CONCAT(IF(IFNULL(b.sTownName, '') = '', '', CONCAT(b.sTownName, ', ', c.sProvName, ' ', b.sZippCode)))",
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
    
    public JSONObject searchRecordWithClientType(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»Name»Birthday»Birth Place»Client Type",
                "sClientID»sCompnyNm»dBirthDte»xBirthPlc»xClientTp",
                "a.sBrgyIDxx»TRIM(IF(a.cClientTp = '0', CONCAT(a.sLastName, ', ', a.sFrstName, IF(TRIM(IFNull(a.sSuffixNm, '')) = '', ' ', CONCAT(' ', a.sSuffixNm, ' ')), a.sMiddName), a.sCompnyNm))»a.dBirthDte»CONCAT(IF(IFNULL(b.sTownName, '') = '', '', CONCAT(b.sTownName, ', ', c.sProvName, ' ', b.sZippCode)))»IF(a.cClientTp = 0, 'Individual', 'Corporate')",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sClientID"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded to update.");
            return poJSON;
        }
    }
    
    @Override
    public String getSQ_Browse(){
        String lsSQL;
        String lsRecdStat = "";
        String lsClientTp = "";

        if (psRecdStat.length() > 1) {
            for (int lnCtr = 0; lnCtr <= psRecdStat.length() - 1; lnCtr++) {
                lsRecdStat += ", " + SQLUtil.toSQL(Character.toString(psRecdStat.charAt(lnCtr)));
            }

            lsRecdStat = "a.cRecdStat IN (" + lsRecdStat.substring(2) + ")";
        } else {
            lsRecdStat = "a.cRecdStat = " + SQLUtil.toSQL(psRecdStat);
        }
        
        if (psClientTp.length() > 1) {
            for (int lnCtr = 0; lnCtr <= psClientTp.length() - 1; lnCtr++) {
                lsClientTp += ", " + SQLUtil.toSQL(Character.toString(psClientTp.charAt(lnCtr)));
            }

            lsClientTp = "a.cClientTp IN (" + lsClientTp.substring(2) + ")";
        } else {
            lsClientTp = "a.cClientTp = " + SQLUtil.toSQL(psClientTp);
        }
        
        lsSQL = "SELECT" +
                    "  a.sClientID" +
                    ", a.cClientTp" +
                    ", a.sLastName" +
                    ", a.sFrstName" +
                    ", a.sMiddName" +
                    ", a.sSuffixNm" +
                    ", a.sMaidenNm" +
                    ", a.sCompnyNm" +
                    ", a.cGenderCd" +
                    ", a.cCvilStat" +
                    ", a.sCitizenx" +
                    ", a.dBirthDte" +
                    ", a.sBirthPlc" +
                    ", a.sAddlInfo" +
                    ", a.sSpouseID" +
                    ", a.sTaxIDNox" +
                    ", a.sLTOIDxxx" +
                    ", a.sPHBNIDxx" +
                    ", a.cLRClient" +
                    ", a.cMCClient" +
                    ", a.cSCClient" +
                    ", a.cSPClient" +
                    ", a.cCPClient" +
                    ", a.cRecdStat" + 
                    ", TRIM(IF(a.cClientTp = '0', CONCAT(a.sLastName, ', ', a.sFrstName, IF(TRIM(IFNull(a.sSuffixNm, '')) = '', ' ', CONCAT(' ', a.sSuffixNm, ' ')), a.sMiddName), a.sCompnyNm)) xFullName" +
                    ", CONCAT(IF(IFNULL(b.sTownName, '') = '', '', CONCAT(b.sTownName, ', ', c.sProvName, ' ', b.sZippCode))) xBirthPlc" +
                    ", IF(a.cClientTp = '0', 'Individual', 'Corporate') xClientTp" +
                " FROM Client_Master a" +
                    " LEFT JOIN TownCity b ON a.sBirthPlc = b.sTownIDxx" +
                    " LEFT JOIN Province c ON b.sProvIDxx = c.sProvIDxx";
        
        if (!psRecdStat.isEmpty()) lsSQL = MiscUtil.addCondition(lsSQL, lsRecdStat);
        if (!psClientTp.isEmpty()) lsSQL = MiscUtil.addCondition(lsSQL, lsClientTp);
        
        return lsSQL;
    }
}