package org.guanzon.cas.client;

import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.cas.client.model.Model_Client_Mobile;
import org.json.simple.JSONObject;

public class Client_Mobile  extends Parameter{
    Model_Client_Mobile poModel;
    
    @Override
    public void initialize() {
        psRecdStat = Logical.YES;
        
        poModel = new Model_Client_Mobile();
        poModel.setApplicationDriver(poGRider);
        poModel.setXML("Model_Client_Mobile");
        poModel.setTableName("Client_Mobile");
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

        if (poModel.getMobileNo().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Mobile number must not be empty.");
            return poJSON;
        }
        
        return poJSON;
    }
    
    @Override
    public Model_Client_Mobile getModel() {
        return poModel;
    }
    
    @Override
    public JSONObject searchRecord(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "Type»Client Name»Mobile No»Ownership»Primary»ID",
                "xMobileTp»xFullName»sMobileNo»xOwnerxxx»xPrimaryx»sMobileID",
                "CASE a.cMobileTp WHEN '0' THEN 'Mobile No' WHEN '1' THEN 'Landline' WHEN '2' THEN 'Fax No' ELSE '' END»TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»a.sMobileNo»CASE a.cOwnerxxx WHEN '0' THEN 'Personal' WHEN '1' THEN 'Company' WHEN '2' THEN 'Others' ELSE '' END»IF(a.cPrimaryx = '1', 'Yes', 'No')»a.sMobileID",
                byCode ? 5 : 2);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sMobileID"));
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
                "Type»Client Name»Mobile No»Ownership»Primary»ID",
                "xMobileTp»xFullName»sMobileNo»xOwnerxxx»xPrimaryx»sMobileID",
                "CASE a.cMobileTp WHEN '0' THEN 'Mobile No' WHEN '1' THEN 'Landline' WHEN '2' THEN 'Fax No' ELSE '' END»TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»a.sMobileNo»CASE a.cOwnerxxx WHEN '0' THEN 'Personal' WHEN '1' THEN 'Company' WHEN '2' THEN 'Others' ELSE '' END»IF(a.cPrimaryx = '1', 'Yes', 'No')»a.sMobileID",
                byCode ? 5 : 2);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sMobileID"));
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
                    "  a.sMobileID" +
                    ", a.sClientID" +
                    ", a.sMobileNo" +
                    ", a.cMobileTp" +
                    ", a.cOwnerxxx" +
                    ", a.cPrimaryx" +
                    ", a.cRecdStat" +
                    ", TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm)) xFullName" +
                    ", CASE a.cMobileTp WHEN '0' THEN 'Mobile Phone' WHEN '1' THEN 'Landline' WHEN '2' THEN 'Fax No' ELSE '' END xMobileTp" +
                    ", CASE a.cOwnerxxx WHEN '0' THEN 'Personal' WHEN '1' THEN 'Company' WHEN '2' THEN 'Others' ELSE '' END xOwnerxxx" +
                    ", IF(a.cPrimaryx = '1', 'Yes', 'No') xPrimaryx" +
                " FROM Client_Mobile a" +
                    ", Client_Master b" +
                " WHERE a.sClientID = b.sClientID";
        
        return MiscUtil.addCondition(lsSQL, lsCondition);
    }
}