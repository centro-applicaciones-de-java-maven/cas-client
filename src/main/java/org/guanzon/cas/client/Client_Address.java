package org.guanzon.cas.client;

import java.sql.SQLException;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.cas.client.model.Model_Client_Address;
import org.json.simple.JSONObject;

public class Client_Address  extends Parameter{
    Model_Client_Address poModel;
    
    @Override
    public void initialize() {
        psRecdStat = Logical.YES;
        
        poModel = new Model_Client_Address();
        poModel.setApplicationDriver(poGRider);
        poModel.setXML("Model_Client_Address");
        poModel.setTableName("Client_Address");
        poModel.initialize();
    }
    
    @Override
    public JSONObject isEntryOkay() throws SQLException, GuanzonException{
        poJSON = new JSONObject();

        if (poModel.getClientId().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Client must not be empty.");
            return poJSON;
        }

        if (poModel.getBarangayId().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Barangay must not be empty.");
            return poJSON;
        }
        
        if (poModel.getTownId().isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("message", "Town must not be empty.");
            return poJSON;
        }
        
        if (!poModel.getTownId().equals(poModel.Barangay().getTownId())){
            poJSON.put("result", "error");
            poJSON.put("message", "Barangay is not within the selected town.");
            return poJSON;
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    @Override
    public Model_Client_Address getModel() {
        return poModel;
    }
    
    @Override
    public JSONObject searchRecord(String value, boolean byCode) throws SQLException, GuanzonException{
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»Client Name»Address»Birthday»Primary",
                "sAddrssID»xFullName»xAddressx»dBirthDte»xPrimaryx",
                "a.sAddrssID»TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»CONCAT(IF(IFNull(a.sHouseNox, '') = '', '', CONCAT(a.sHouseNox, ' ')), a.sAddressx, IF(IFNull(e.sBrgyName, '') = '', '', CONCAT(' ', e.sBrgyName)), ', ', c.sTownName, ', ', d.sProvName, ' ', c.sZippCode)»b.dBirthDte»IF(a.cPrimaryx = '1', 'Yes', 'No')",
                byCode ? 0 : 2);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sAddrssID"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecordbyclient(String value, boolean byCode) throws SQLException, GuanzonException{
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»ID»Client Name»Address»Birthday»Primary",
                "sClientID»sAddrssID»xFullName»xAddressx»dBirthDte»xPrimaryx",
                "a.sClientID»a.sAddrssID»TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»CONCAT(IF(IFNull(a.sHouseNox, '') = '', '', CONCAT(a.sHouseNox, ' ')), a.sAddressx, IF(IFNull(e.sBrgyName, '') = '', '', CONCAT(' ', e.sBrgyName)), ', ', c.sTownName, ', ', d.sProvName, ' ', c.sZippCode)»b.dBirthDte»IF(a.cPrimaryx = '1', 'Yes', 'No')",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sAddrssID"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }

    public JSONObject searchRecordWithCondition(String value,  String clientId, boolean isPrimary) throws SQLException, GuanzonException{
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), "a.sClientID = " + SQLUtil.toSQL(clientId) +
                " AND a.cPrimaryx = "+ SQLUtil.toSQL(isPrimary == true ? "1" : "0"));
        System.out.println(lsSQL);
            poJSON = ShowDialogFX.Search(poGRider,
                    lsSQL,
                    value,
                    "ID»Client Name»Address»Birthday»Primary",
                    "sAddrssID»xFullName»xAddressx»dBirthDte»cPrimaryx",
                    "a.sAddrssID»TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»CONCAT(IF(IFNull(a.sHouseNox, '') = '', '', CONCAT(a.sHouseNox, ' ')), a.sAddressx, IF(IFNull(e.sBrgyName, '') = '', '', CONCAT(' ', e.sBrgyName)), ', ', c.sTownName, ', ', d.sProvName, ' ', c.sZippCode)»b.dBirthDte»IF(a.cPrimaryx = '1', 'Yes', 'No')",
                    isPrimary ? 0 : 2);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sAddrssID"));
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
                "ID»Client Name»Address»Birthday»Primary",
                "sAddrssID»xFullName»xAddressx»dBirthDte»xPrimaryx",
                "a.sAddrssID»TRIM(IF(b.cClientTp = '0', CONCAT(b.sLastName, ', ', b.sFrstName, IF(TRIM(IFNull(b.sSuffixNm, '')) = '', ' ', CONCAT(' ', b.sSuffixNm, ' ')), b.sMiddName), b.sCompnyNm))»CONCAT(IF(IFNull(a.sHouseNox, '') = '', '', CONCAT(a.sHouseNox, ' ')), a.sAddressx, IF(IFNull(e.sBrgyName, '') = '', '', CONCAT(' ', e.sBrgyName)), ', ', c.sTownName, ', ', d.sProvName, ' ', c.sZippCode)»b.dBirthDte»IF(a.cPrimaryx = '1', 'Yes', 'No')",
                byCode ? 0 : 2);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sAddrssID"));
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
        
        lsSQL = "SELECT "
                + "  a.sAddrssID, "
                + "  a.sClientID, "
                + "  a.sHouseNox, "
                + "  a.sAddressx, "
                + "  a.sBrgyIDxx, "
                + "  a.sTownIDxx, "
                + "  a.nLatitude, "
                + "  a.nLongitud, "
                + "  a.cPrimaryx, "
                + "  a.cOfficexx, "
                + "  a.cProvince, "
                + "  a.cBillingx, "
                + "  a.cShipping, "
                + "  a.cCurrentx, "
                + "  a.cLTMSAddx, "
                + "  a.sSourceCd, "
                + "  a.sReferNox, "
                + "  a.cRecdStat, "
                + "  CONCAT( "
                + "    IFNULL(NULLIF(a.sHouseNox, ''), ''), "
                + "    IF( "
                + "      a.sHouseNox IS NOT NULL AND a.sHouseNox != '', "
                + "      ' ', "
                + "      '' "
                + "    ), "
                + "    a.sAddressx, "
                + "    IFNULL(CONCAT(' ', e.sBrgyName), ''), "
                + "    ', ', "
                + "    c.sTownName, "
                + "    ', ', "
                + "    d.sProvName, "
                + "    ' ', "
                + "    c.sZippCode "
                + "  ) AS xAddressx, "
                + "  LTRIM(RTRIM( "
                + "    IF( "
                + "      b.cClientTp = '0', "
                + "      CONCAT( "
                + "        b.sLastName, ', ', b.sFrstName, "
                + "        IFNULL(CONCAT(' ', NULLIF(b.sSuffixNm, '')), ''), "
                + "        ' ', "
                + "        b.sMiddName "
                + "      ), "
                + "      b.sCompnyNm "
                + "    ) "
                + "  )) AS xFullName, "
                + "  b.dBirthDte, "
                + "  IF(a.cPrimaryx = '1', 'Yes', 'No') AS xPrimaryx "
                + "FROM Client_Address a "
                + "LEFT JOIN TownCity c ON a.sTownIDxx = c.sTownIDxx "
                + "LEFT JOIN Province d ON c.sProvIDxx = d.sProvIDxx "
                + "LEFT JOIN Barangay e ON a.sBrgyIDxx = e.sBrgyIDxx "
                + "JOIN Client_Master b ON a.sClientID = b.sClientID";


        
         System.out.println("get SQ BRowse  == " + lsSQL);
        return MiscUtil.addCondition(lsSQL, lsCondition);
    }
    
    
}