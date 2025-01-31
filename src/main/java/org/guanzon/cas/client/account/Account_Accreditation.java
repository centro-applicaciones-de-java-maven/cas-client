package org.guanzon.cas.client.account;

import javax.sound.midi.SysexMessage;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.appdriver.constant.UserRight;
import org.guanzon.cas.client.Client;
import org.guanzon.cas.client.model.Model_Account_Client_Accreditation;
import org.json.simple.JSONObject;

public class Account_Accreditation extends Parameter{
    Model_Account_Client_Accreditation poModel;
    Client poClients;
    String psTranStatus;
    @Override
    public void initialize() {
        psRecdStat = Logical.YES;
        
        poModel = new Model_Account_Client_Accreditation();
        poModel.setApplicationDriver(poGRider);
        poModel.setXML("Model_Account_Client_Accreditation");
        poModel.setTableName("Account_Client_Accreditation");
        poModel.initialize();
        
         poClients = new Client(poGRider,"", logwrapr);
    }
    public Client Client() {
        return poClients;
    }
    
    public void setTransactionStatus(String string) {
       psTranStatus = string;
    }
        
    @Override
    public JSONObject isEntryOkay() {
        poJSON = new JSONObject();
        
        if (poGRider.getUserLevel() < UserRight.SYSADMIN){
            poJSON.put("result", "error");
            poJSON.put("message", "User is not allowed to save record.");
            return poJSON;
        } else {
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
            
            if (poModel.getContatId().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Contact must not be empty.");
                return poJSON;
            }
            
            //todo:
            //  more validations/use of validators per category
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    @Override
    public Model_Account_Client_Accreditation getModel() {
        return poModel;
    }
 
    

    
    @Override
    public JSONObject searchRecord(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
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
    
    public JSONObject searchRecordbyClient(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "Transaction No»Client»Date»Name",
                "sTransNox»sClientID»dTransact»sCompnyNm",
                "sTransNox»a.sClientID»dTransact»sCompnyNm",
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
    
    public JSONObject searchRecord(String value, 
                                    boolean byCode, 
                                    boolean openStat) {
        String lsCondition = "";
        if (openStat == true){
            lsCondition =  "LEFT (sTransNox, 4) = '" + poGRider.getBranchCode() + "' AND  a.cTranStat = 0";
        }else{
            lsCondition =  "LEFT (sTransNox, 4) = '" + poGRider.getBranchCode() + "' AND  a.cTranStat <> 0";
        }

        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
                                                 lsCondition);
        System.out.println("lsSQL == " + lsSQL);
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
    
    public JSONObject searchRecord(String value, 
                                    boolean byCode, 
                                    String inventoryTypeId,
                                    String categoryIdLevel1) {
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Bar Code»Description»Brand»Model»Color»Selling Price»ID",
                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sStockIDx"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecord(String value, 
                                    boolean byCode, 
                                    String inventoryTypeId,
                                    String categoryIdLevel1,
                                    String categoryIdLevel2) {
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1) +
                                                    " AND a.sCategCd2 = " + SQLUtil.toSQL(categoryIdLevel2));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Bar Code»Description»Brand»Model»Color»Selling Price»ID",
                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sStockIDx"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecordAttributes(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "Brand»Model»Color»Selling Price»ID",
                "xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
                "IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sStockIDx"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecordAttributes(String value, 
                                    boolean byCode, 
                                    String inventoryTypeId) {
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Brand»Model»Color»Selling Price»ID",
                "xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
                "IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sStockIDx"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecordAttributes(String value, 
                                    boolean byCode, 
                                    String inventoryTypeId,
                                    String categoryIdLevel1) {
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Brand»Model»Color»Selling Price»ID",
                "xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
                "IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sStockIDx"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecordAttributes(String value, 
                                    boolean byCode, 
                                    String inventoryTypeId,
                                    String categoryIdLevel1,
                                    String categoryIdLevel2) {
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1) +
                                                    " AND a.sCategCd2 = " + SQLUtil.toSQL(categoryIdLevel2));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Brand»Model»Color»Selling Price»ID",
                "xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
                "IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sStockIDx"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecordWithMeasurement(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "Bar Code»Description»Brand»Model»Color»Measurement»Selling Price»ID",
                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»xMeasurNm»nSelPrice»sStockIDx",
                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»IFNULL(e.sMeasurNm, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sStockIDx"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecordWithMeasurement(String value, 
                                    boolean byCode, 
                                    String inventoryTypeId) {
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Bar Code»Description»Brand»Model»Color»Measurement»Selling Price»ID",
                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»xMeasurNm»nSelPrice»sStockIDx",
                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»IFNULL(e.sMeasurNm, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sStockIDx"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecordWithMeasurement(String value, 
                                    boolean byCode, 
                                    String inventoryTypeId,
                                    String categoryIdLevel1) {
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Bar Code»Description»Brand»Model»Color»Measurement»Selling Price»ID",
                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»xMeasurNm»nSelPrice»sStockIDx",
                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»IFNULL(e.sMeasurNm, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sStockIDx"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    public JSONObject searchRecordWithMeasurement(String value, 
                                    boolean byCode, 
                                    String inventoryTypeId,
                                    String categoryIdLevel1,
                                    String categoryIdLevel2) {
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1) +
                                                    " AND a.sCategCd2 = " + SQLUtil.toSQL(categoryIdLevel2));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Bar Code»Description»Brand»Model»Color»Measurement»Selling Price»ID",
                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»xMeasurNm»nSelPrice»sStockIDx",
                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»IFNULL(e.sMeasurNm, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poModel.openRecord((String) poJSON.get("sStockIDx"));
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
        String lsRecdStat = "";

//        if (psRecdStat.length() > 1) {
//            for (int lnCtr = 0; lnCtr <= psRecdStat.length() - 1; lnCtr++) {
//                lsRecdStat += ", " + SQLUtil.toSQL(Character.toString(psRecdStat.charAt(lnCtr)));
//            }
//
//            lsRecdStat = "a.cTranStat IN (" + lsRecdStat.substring(2) + ")";
//        } else {
//            lsRecdStat = "a.cTranStat = " + SQLUtil.toSQL(psRecdStat);
//        }
        
        lsSQL = " SELECT " +
                        " a.sTransNox, " +
                        " a.cAcctType, " +
                        " a.sClientID, " +
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
                        " CONCAT(c.sHouseNox, ', ', c.sAddressx, ', ', c.sBrgyIDxx, ', ', c.sTownIDxx) AS sFllAddrs " +
                      " FROM Account_Client_Accreditation a " +
                        " LEFT JOIN Client_Master b " +
                          " on a.sClientID = b.sClientID " +
                        " LEFT JOIN client_address c " +
                          " on a.sClientID = c.sClientID " +
                        " LEFT JOIN Client_Institution_Contact_Person d " +
                          " on d.sClientID = a.sClientID " ;
        
        
        System.out.println("query natin to = = " + lsSQL );
        
//        if (!psRecdStat.isEmpty()) lsSQL = MiscUtil.addCondition(lsSQL, lsRecdStat);
        
        return lsSQL;
    }
    
    public JSONObject postTransaction() {
        poJSON = new JSONObject();
            if (poModel.getTransactionNo() != null || !poModel.getTransactionNo().isEmpty()){
                poJSON = poModel.updateRecord();
                    if ("success".equals((String) poJSON.get("result"))){
                        poJSON = poModel.setRecordStatus("1");
                        poJSON = poModel.setApproved(poGRider.getUserID());
                        poJSON = poModel.setModifyingId(poGRider.getUserID());
                        poJSON = poModel.setDateApproved(poGRider.getServerDate());
                        poJSON = poModel.setModifiedDate(poGRider.getServerDate());
                        poJSON = poModel.saveRecord();
                    }
                return poJSON;
            }else{
                poJSON.put("result", "error");
                poJSON.put("message", "No record loaded.");
            }
        return poJSON;
    }
    
    public JSONObject voidTransaction() {
        poJSON = new JSONObject();
            if (poModel.getTransactionNo() != null || !poModel.getTransactionNo().isEmpty()){
                poJSON = poModel.updateRecord();
                    if ("success".equals((String) poJSON.get("result"))){
                        poJSON = poModel.setRecordStatus("3");
                        poJSON = poModel.setApproved(poGRider.getUserID());
                        poJSON = poModel.setModifyingId(poGRider.getUserID());
                        poJSON = poModel.setDateApproved(poGRider.getServerDate());
                        poJSON = poModel.setModifiedDate(poGRider.getServerDate());
                        poJSON = poModel.saveRecord();
                    }
                return poJSON;
            }else{
                poJSON.put("result", "error");
                poJSON.put("message", "No record loaded.");
            }
        return poJSON;
    }
}