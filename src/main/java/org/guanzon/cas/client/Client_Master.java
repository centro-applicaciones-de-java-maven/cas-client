package org.guanzon.cas.client;

import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.iface.GRecord;
import org.guanzon.cas.client.model.Model_Client_Master;
import org.json.simple.JSONObject;

public class Client_Master implements GRecord{
    GRider poGRider;
    boolean pbWthParent;
    String psRecdStat;
    String psClientTp;

    Model_Client_Master poModel;
    JSONObject poJSON;

    public Client_Master(GRider appDriver, boolean withParent) {
        poGRider = appDriver;
        pbWthParent = withParent;

        psClientTp = "10";
        psRecdStat = Logical.YES;
        poModel = new Model_Client_Master(appDriver);
    }
    
    public void setClientType(String clientType){
        psClientTp = clientType;
    }

    @Override
    public void setRecordStatus(String recordStatus) {
        psRecdStat = recordStatus;
    }

    @Override
    public int getEditMode() {
        return poModel.getEditMode();
    }

    @Override
    public JSONObject newRecord() {        
        return poModel.newRecord();
    }

    @Override
    public JSONObject openRecord(String barangayId) {
        return poModel.openRecord(barangayId);
    }

    @Override
    public JSONObject updateRecord() {
        return poModel.updateRecord();
    }

    @Override
    public JSONObject saveRecord() {
        if (!pbWthParent) {
            poGRider.beginTrans();
        }

        poJSON = poModel.saveRecord();

        if ("success".equals((String) poJSON.get("result"))) {
            if (!pbWthParent) {
                poGRider.commitTrans();
            }
        } else {
            if (!pbWthParent) {
                poGRider.rollbackTrans();
            }
        }

        return poJSON;
    }

    @Override
    public JSONObject deleteRecord() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject deactivateRecord() {
        poJSON = new JSONObject();
        
        if (poModel.getEditMode() != EditMode.READY ||
            poModel.getEditMode() != EditMode.UPDATE){
        
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
        }
        
        
        if (poModel.getEditMode() == EditMode.READY) {
            poJSON = updateRecord();
            if ("error".equals((String) poJSON.get("result"))) return poJSON;
        } 

        poJSON = poModel.setRecordStatus("0");

        if ("error".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        return poModel.saveRecord();
    }

    @Override
    public JSONObject activateRecord() {
        poJSON = new JSONObject();
        
        if (poModel.getEditMode() != EditMode.READY ||
            poModel.getEditMode() != EditMode.UPDATE){
        
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
        }
        
        
        if (poModel.getEditMode() == EditMode.READY) {
            poJSON = updateRecord();
            if ("error".equals((String) poJSON.get("result"))) return poJSON;
        } 

        poJSON = poModel.setRecordStatus("1");

        if ("error".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        return poModel.saveRecord();
    }

    @Override
    public JSONObject searchRecord(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»Name»Birthday»Birth Place",
                "sClientID»sCompnyNm»dBirthDte»xBirthPlc",
                "a.sBrgyIDxx»a.sCompnyNm»a.dBirthDte»CONCAT(IF(IFNULL(b.sTownName, '') = '', '', CONCAT(b.sTownName, ', ', c.sProvName))",
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
    public Model_Client_Master getModel() {
        return poModel;
    }   
    
    private String getSQ_Browse(){
        String lsSQL = "";
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
                    ", CONCAT(IF(IFNULL(b.sTownName, '') = '', '', CONCAT(b.sTownName, ', ', c.sProvName))) xBirthPlc" +
                " FROM Client_Master a" +
                    " LEFT JOIN TownCity b ON a.sBirthPlc = b.sTownIDxx" +
                    " LEFT JOIN Province c ON b.sProvIDxx = c.sProvIDxx";
        
        if (!psRecdStat.isEmpty()) lsSQL = MiscUtil.addCondition(lsSQL, lsRecdStat);
        if (!psClientTp.isEmpty()) lsSQL = MiscUtil.addCondition(lsSQL, lsClientTp);
        
        return lsSQL;
    }
}
