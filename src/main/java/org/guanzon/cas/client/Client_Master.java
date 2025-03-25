package org.guanzon.cas.client;

import java.sql.SQLException;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.cas.client.model.Model_Client_Master;
import org.json.simple.JSONObject;

public class Client_Master extends Parameter {

    Model_Client_Master poModel;
    String psClientTp;
    int pnEditMode;

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
    public JSONObject isEntryOkay() {
        poJSON = new JSONObject();

        if (poModel.getClientId().isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("message", "Client must not be empty.");
            return poJSON;
        }

        if (poModel.getClientType().isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("message", "Client type not be empty.");
            return poJSON;
        }

        if (poModel.getClientType().equals(ClientTypes.INDIVIDUAL)) {
            if (poModel.getLastName().isEmpty()) {
                poJSON.put("result", "error");
                poJSON.put("message", "Last name must not be empty.");
                return poJSON;
            }

            if (poModel.getFirstName().isEmpty()) {
                poJSON.put("result", "error");
                poJSON.put("message", "Last name must not be empty.");
                return poJSON;
            }
        } else {
            if (poModel.getCompanyName().isEmpty()) {
                poJSON.put("result", "error");
                poJSON.put("message", "Company name must not be empty.");
                return poJSON;
            }
        }

        poJSON.put("result", "success");
        return poJSON;
    }

    @Override
    public Model_Client_Master getModel() {
        return poModel;
    }

    @Override
    public JSONObject searchRecord(String value, boolean byCode) throws SQLException, GuanzonException{
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»Name»Birthday»Birth Place",
                "sClientID»sCompnyNm»dBirthDte»xBirthPlc",
                "a.sBrgyIDxx»TRIM(IF(a.cClientTp = '0', CONCAT(a.sLastName, ', ', a.sFrstName, IF(TRIM(IFNull(a.sSuffixNm, '')) = '', ' ', CONCAT(' ', a.sSuffixNm, ' ')), a.sMiddName), a.sCompnyNm))»a.dBirthDte»CONCAT(IF(IFNULL(b.sTownName, '') = '', '', CONCAT(b.sTownName, ', ', c.sDescript, ' ', b.sZippCode)))",
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
    public JSONObject searchRecordClientType(String value, boolean byCode) throws SQLException, GuanzonException{
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»Name»Birthday»Birth Place",
                "sClientID»sCompnyNm»dBirthDte»xBirthPlc",
                "a.sBrgyIDxx»TRIM(IF(a.cClientTp = '0', CONCAT(a.sLastName, ', ', a.sFrstName, IF(TRIM(IFNull(a.sSuffixNm, '')) = '', ' ', CONCAT(' ', a.sSuffixNm, ' ')), a.sMiddName), a.sCompnyNm))»a.dBirthDte»CONCAT(IF(IFNULL(b.sTownName, '') = '', '', CONCAT(b.sTownName, ', ', c.sDescript, ' ', b.sZippCode)))",
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
    public JSONObject searchRecordSpouse(String value, boolean byCode) throws SQLException, GuanzonException{
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»Name",
                "sClientID»sCompnyNm",
                "a.sClientID»a.sCompnyNm",
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

    public JSONObject searchRecordWithClientType(String value, boolean byCode) throws SQLException, GuanzonException{
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»Name»Birthday»Birth Place»Client Type",
                "sClientID»sCompnyNm»dBirthDte»xBirthPlc»cClientTp",
                "a.sBrgyIDxx»TRIM(IF(a.cClientTp = '0', CONCAT(a.sLastName, ', ', a.sFrstName, IF(TRIM(IFNull(a.sSuffixNm, '')) = '', ' ', CONCAT(' ', a.sSuffixNm, ' ')), a.sMiddName), a.sCompnyNm))»a.dBirthDte»CONCAT(IF(IFNULL(b.sTownName, '') = '', '', CONCAT(b.sTownName, ', ', c.sDescript, ' ', b.sZippCode)))»IF(a.cClientTp = 0, 'Individual', 'Corporate')",
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

    public JSONObject searchBirthPlace(String fsValue, boolean fbByCode) {
        JSONObject loJSON;
        if (fbByCode) {
            if (fsValue.equals(getMaster(27))) {
                loJSON = new JSONObject();
                loJSON.put("result", "success");
                loJSON.put("message", "Search birth place success.");
                return loJSON;
            }
        } else {
            if (getMaster(27) != null && !getMaster(27).toString().trim().isEmpty()) {
                if (fsValue.equals(getMaster(27))) {
                    loJSON = new JSONObject();
                    loJSON.put("result", "success");
                    loJSON.put("message", "Search birth place success.");
                    return loJSON;
                }
            }
        }

        String lsSQL = "SELECT"
                + "  a.sTownIDxx"
                + ", CONCAT(a.sTownName, ', ', b.sProvName) AS xBrthPlce"
                + " FROM TownCity a "
                + " INNER JOIN Province b "
                + "   ON a.sProvIDxx = b.sProvIDxx "
                + " WHERE a.cRecdStat = 1 ";
        if (fbByCode) {
            lsSQL = MiscUtil.addCondition(lsSQL, "a.sTownIDxx = " + SQLUtil.toSQL(fsValue));
        } else {
            lsSQL = MiscUtil.addCondition(lsSQL, "a.sTownName LIKE " + SQLUtil.toSQL(fsValue + "%"));
        }

        loJSON = ShowDialogFX.Search(
                poGRider,
                lsSQL,
                fsValue,
                "Code»Birth Place",
                "a.sTownIDxx»xBrthPlce",
                "a.sTownIDxx»CONCAT(a.sTownName, ', ', b.sProvName)",
                fbByCode ? 0 : 1);

        if (loJSON != null) {
//            setMaster(11, (String) loJSON.get("sTownIDxx"));
//            setMaster(13, (String) loJSON.get("xBrthPlce"));
            loJSON.put("result", "success");
            loJSON.put("message", "Search birth place success.");
            return loJSON;
        } else {
            loJSON.put("result", "success");
            loJSON.put("message", "No record selected.");
            return loJSON;
        }
    }
    public JSONObject searchCitizenship(String fsValue, boolean fbByCode) {

        JSONObject loJSON;
        if (fbByCode) {
            if (fsValue.equals(getMaster(28))) {
                loJSON = new JSONObject();
                loJSON.put("result", "success");
                loJSON.put("message", "Search town success.");
                return loJSON;
            }
        } else {
            if (getMaster(28) != null && !getMaster(28).toString().trim().isEmpty()) {
                if (fsValue.equals(getMaster(28))) {
                    loJSON = new JSONObject();
                    loJSON.put("result", "success");
                    loJSON.put("message", "Search town success.");
                    return loJSON;
                }
            }
        }
        String lsSQL = "SELECT"
                + "  sCntryCde"
                + ", sNational"
                + " FROM Country "
                + " WHERE cRecdStat = '1' "
                + " AND (sNational IS NOT NULL AND sNational != '') ";

        if (fbByCode) {
            lsSQL = MiscUtil.addCondition(lsSQL, "sCntryCde = " + SQLUtil.toSQL(fsValue));
        } else {
            lsSQL = MiscUtil.addCondition(lsSQL, "sNational LIKE " + SQLUtil.toSQL(fsValue + "%"));
        }

        System.out.println(lsSQL);
        loJSON = ShowDialogFX.Search(
                poGRider,
                lsSQL,
                fsValue,
                "Code»Nationality",
                "sCntryCde»sNational",
                "sCntryCde»sNational",
                fbByCode ? 0 : 1);

        if (loJSON != null) {
//            loJSON.get("sCntryCde");
//            loJSON.get("sNational");
            loJSON.put("result", "success");
            loJSON.put("message", "Search citizenship success.");
            return loJSON;
        } else {
            loJSON.put("result", "success");
            loJSON.put("message", "No record selected.");
            return loJSON;
        }
    }
    
    
    public JSONObject setMaster(String fsCol, Object foData) {
        return setMaster(poModel.getColumn(fsCol), foData);
    }

    public JSONObject setMaster(int fnCol, Object foData) {

        JSONObject obj = new JSONObject();
        obj.put("pnEditMode", pnEditMode);
        if (pnEditMode != EditMode.UNKNOWN) {
            // Don't allow specific fields to assign values
            if (!(fnCol == poModel.getColumn("sClientID")
                    || fnCol == poModel.getColumn("cRecdStat")
                    || fnCol == poModel.getColumn("sModified")
                    || fnCol == poModel.getColumn("dModified"))) {
                poModel.setValue(fnCol, foData);
                poModel.getColumn(fnCol);
                obj.put(fnCol, pnEditMode);
            }
        }
        return obj;
    }

    public Object getMaster(int fnCol) {
        if (pnEditMode == EditMode.UNKNOWN) {
            return null;
        } else {
            return poModel.getValue(fnCol);
        }
    }

    public Object getMaster(String fsCol) {
        return getMaster(poModel.getColumn(fsCol));
    }

    @Override
    public String getSQ_Browse() {
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

        lsSQL = "SELECT"
                + "  a.sClientID"
                + ", a.cClientTp"
                + ", a.sLastName"
                + ", a.sFrstName"
                + ", a.sMiddName"
                + ", a.sSuffixNm"
                + ", a.sMaidenNm"
                + ", a.sCompnyNm"
                + ", a.cGenderCd"
                + ", a.cCvilStat"
                + ", a.sCitizenx"
                + ", a.dBirthDte"
                + ", a.sBirthPlc"
                + ", a.sAddlInfo"
                + ", a.sSpouseID"
                + ", a.sTaxIDNox"
                + ", a.sLTOIDxxx"
                + ", a.sPHBNIDxx"
                + ", a.cLRClient"
                + ", a.cMCClient"
                + ", a.cSCClient"
                + ", a.cSPClient"
                + ", a.cCPClient"
                + ", a.cRecdStat"
                + ", IF(a.cClientTp = '0',CONCAT(a.sLastName, ', ', a.sFrstName,IF(LTRIM(RTRIM(IFNULL(a.sSuffixNm, ''))) = '',' ',CONCAT(' ', LTRIM(RTRIM(a.sSuffixNm)), ' ')),a.sMiddName),a.sCompnyNm) AS xFullName"
                + ", CONCAT(IF(IFNULL(b.sTownName, '') = '','',CONCAT(b.sTownName, ', ', c.sDescript, ' ', b.sZippCode))) AS xBirthPlc"
                + " FROM Client_Master a"
                + " LEFT JOIN TownCity b ON a.sBirthPlc = b.sTownIDxx"
                + " LEFT JOIN Province c ON b.sProvIDxx = c.sProvIDxx";
        

        if (!psRecdStat.isEmpty()) {
            lsSQL = MiscUtil.addCondition(lsSQL, lsRecdStat);
        }
        if (!psClientTp.isEmpty()) {
            lsSQL = MiscUtil.addCondition(lsSQL, lsClientTp);
        }
        
        return lsSQL;
    }
}
