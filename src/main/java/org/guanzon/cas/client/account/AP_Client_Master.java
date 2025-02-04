package org.guanzon.cas.client.account;

import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.UserRight;
import org.guanzon.cas.client.Client;
import org.guanzon.cas.client.model.Model_AP_Client_Master;
import org.guanzon.cas.parameter.Branch;
import org.guanzon.cas.parameter.InvLocation;
import org.guanzon.cas.parameter.services.ParamControllers;
import org.guanzon.cas.parameter.Warehouse;
import org.json.simple.JSONObject;

public class AP_Client_Master extends Parameter{
    //object model
    Model_AP_Client_Master poModel;
    Client poClients;
    
    //reference objects
    ParamControllers poParams;
//    InvControllers poInv;
    
    Branch poBranch;
    InvLocation poLocation;
//    Inventory poInventory;
    Warehouse poWarehouse;
    //end - reference objects
    
    //optional only
    String psBranchCd;
    public void setBranchCode(String branchCode){
        psBranchCd = branchCode;
    }
    
    //return reference objects
    public Branch Branch(){
        return poBranch;
    }
    
    public InvLocation InvLocation(){
        return poLocation;
    }
    
//    public Inventory Inventory(){
//        return poInventory;
//    }
    
    public Warehouse Warehouse(){
        return poWarehouse;
    }
    //end - return reference objects
    
    @Override
    public void initialize() {
        psRecdStat = Logical.YES;
        
        poClients = new Client(poGRider,"", logwrapr);
        
        poModel = new Model_AP_Client_Master();
        poModel.setApplicationDriver(poGRider);
        poModel.setXML("Model_AP_Client_Master");
        poModel.setTableName("AP_Client_Master");
        poModel.initialize();
        
        psBranchCd = poGRider.getBranchCode();
        
        //initialize reference objects
        poParams = new ParamControllers(poGRider, logwrapr);
        poBranch = poParams.Branch();
        poLocation = poParams.InventoryLocation();
        poWarehouse = poParams.Warehouse();
        
//        poInv = new InvControllers(poGRider, logwrapr);
//        poInventory = poInv.Inventory();
        //end - initialize reference objects
    }
    public Client Client() {
        return poClients;
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
            
//            if (poModel.getClientId().isEmpty()){
//                poJSON.put("result", "error");
//                poJSON.put("message", "client must not be empty.");
//                return poJSON;
//            }
            
            if (poModel.getAddressId().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Address must not be empty.");
                return poJSON;
            }
            
            if (poModel.getContactId().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Contact must not be empty.");
                return poJSON;
            }
            
            if (poModel.getCategoryCode().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Category must not be empty.");
                return poJSON;
            }
            
//            if (poModel.getBeginningDate().isEmpty()){
//                poJSON.put("result", "error");
//                poJSON.put("message", "Bin must not be empty.");
//                return poJSON;
//            }
            
            //todo:
            //  more validations/use of validators per category
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    @Override
    public Model_AP_Client_Master getModel() {
        return poModel;
    }
    
    @Override
    public JSONObject searchRecord(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "ID»Name»Contact Person»",
                "sClientID»xClientNm»xCPerson1",
                "a.sClientID»b.sCompnyNm»d.sCPerson1",
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
    
    public JSONObject searchRecord(String value, 
                                    boolean byCode, 
                                    String inventoryTypeId) {
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Bar Code»Description»Brand»Model»Color»Selling Price»ID",
                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        return openRecord(poJSON);
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

        return openRecord(poJSON);
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

        return openRecord(poJSON);
    }
    
    public JSONObject searchRecordAttributes(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "Brand»Model»Color»Selling Price»ID",
                "xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
                "IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        return openRecord(poJSON);
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

        return openRecord(poJSON);
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

        return openRecord(poJSON);
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

        return openRecord(poJSON);
    }
    
    public JSONObject searchRecordWithMeasurement(String value, boolean byCode) {
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "Bar Code»Description»Brand»Model»Color»Measurement»Selling Price»ID",
                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»xMeasurNm»nSelPrice»sStockIDx",
                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»IFNULL(e.sMeasurNm, '')»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        return openRecord(poJSON);
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

        return openRecord(poJSON);
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

        return openRecord(poJSON);
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

        return openRecord(poJSON);
    }
    
    @Override
    public String getSQ_Browse(){
        String lsSQL;
        String lsRecdStat = "";

        if (psRecdStat.length() > 1) {
            for (int lnCtr = 0; lnCtr <= psRecdStat.length() - 1; lnCtr++) {
                lsRecdStat += ", " + SQLUtil.toSQL(Character.toString(psRecdStat.charAt(lnCtr)));
            }

            lsRecdStat = "a.cRecdStat IN (" + lsRecdStat.substring(2) + ")";
        } else {
            lsRecdStat = "a.cRecdStat = " + SQLUtil.toSQL(psRecdStat);
        }
        
        lsSQL = "SELECT" +
                        "  a.sClientID" +
                        ", a.sAddrssID" +
                        ", a.sContctID" +
                        ", a.sCategrCd" +
                        ", a.dCltSince" +
                        ", a.dBegDatex" +
                        ", a.nBegBalxx" +
                        ", a.sTermIDxx" +
                        ", a.nDiscount" +
                        ", a.nCredLimt" +
                        ", a.nABalance" +
                        ", a.nOBalance" +
                        ", a.nLedgerNo" +
                        ", a.cVatablex" +
                        ", a.cRecdStat" +
                        ", a.sModified" +
                        ", a.dModified" +
                        ", b.sCompnyNm xClientNm" +
                        ", LTRIM(RTRIM(CONCAT(c.sHouseNox,' ',c.sAddressx,', ',g.sBrgyName,' ',h.sTownName,', ',i.sProvName))) xAddressx" +
                        ", d.sCPerson1 xCPerson1" +
                        ", d.sCPPosit1 xCPPosit1" +
                        ", e.sDescript xCategrNm" +
                        ", f.sDescript xTermName" +
                        ", b.sTaxIDNox xTaxIDNox" +
                        ", d.sMobileNo xMobileNo" +
                    " FROM AP_Client_Master a" +
                        " LEFT JOIN Client_Master b ON a.sClientID = b.sClientID" +
                        " LEFT JOIN Client_Address c" + 
                            " LEFT JOIN Barangay  g ON c.sBrgyIDxx = g.sBrgyIDxx" +
                            " LEFT JOIN TownCity h ON c.sTownIDxx = h.sTownIDxx" +
                            " LEFT JOIN Province i ON h.sProvIDxx = i.sProvIDxx" +
                        " ON a.sAddrssID = c.sAddrssID" +
                        " LEFT JOIN Client_Institution_Contact_Person d ON a.sContctID = d.sContctID" +
                        " LEFT JOIN Category e ON a.sCategrCd = e.sCategrCd" +
                        " LEFT JOIN Term f ON a.sTermIDxx = f.sTermCode";
        
        
        if (!psRecdStat.isEmpty()) lsSQL = MiscUtil.addCondition(lsSQL, lsRecdStat);

        System.out.println("select == " + lsSQL);
        return lsSQL;
    }
     private JSONObject openRecord(JSONObject json){
        if (json != null) {
            System.out.println("(String) poJSON.get(\"sBranchCd\") == " + psBranchCd);
            poJSON = poModel.openRecord((String) poJSON.get("sClientID"));
            
            if (!"success".equals((String) poJSON.get("result"))) return poJSON;
            
           
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
        
        return poJSON;
    }
        public JSONObject searchRecordwithBarrcode(String value, boolean byCode) {
            
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "BarCode»Description»Selling Price»ID",
                "sBarCodex»sDescript»nSelPrice»sStockIDx",
                "a.sBarCodex»a.sDescript»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);

        return openRecord(poJSON);
    }
    public JSONObject searchRecordwithBarrcode(String value, 
                                    boolean byCode, 
                                    String stockID) {
        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
                                                    "a.sStockIDx = " + SQLUtil.toSQL(stockID));
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                  "BarCode»Description»Selling Price»ID",
                "sBarCodex»sDescript»nSelPrice»sStockIDx",
                "a.sBarCodex»a.sDescript»a.nSelPrice»a.sStockIDx",
                byCode ? 0 : 1);
        System.out.println("poJSON = " + poJSON);
        return openRecord(poJSON);
    }
}
        