//package org.guanzon.cas.client.account;
//
//import org.guanzon.appdriver.agent.ShowDialogFX;
//import org.guanzon.appdriver.agent.services.Parameter;
//import org.guanzon.appdriver.base.MiscUtil;
//import org.guanzon.appdriver.base.SQLUtil;
//import org.guanzon.appdriver.constant.Logical;
//import org.guanzon.appdriver.constant.UserRight;
//import org.guanzon.cas.client.model.Model_AP_Client_Ledger;
//import org.guanzon.cas.client.model.Model_AP_Client_Master;
//import org.guanzon.cas.parameter.Branch;
//import org.guanzon.cas.parameter.InvLocation;
//import org.guanzon.cas.parameter.services.ParamControllers;
//import org.guanzon.cas.parameter.Warehouse;
//import org.json.simple.JSONObject;
//
//public class AP_Client_Ledger extends Parameter{
//    //object model
//    Model_AP_Client_Ledger poModel;
//    
//    //reference objects
//    ParamControllers poParams;
////    InvControllers poInv;
//    
//    Branch poBranch;
//    InvLocation poLocation;
////    Inventory poInventory;
//    Warehouse poWarehouse;
//    //end - reference objects
//    
//    //optional only
//    String psBranchCd;
//    public void setBranchCode(String branchCode){
//        psBranchCd = branchCode;
//    }
//    
//    //return reference objects
//    public Branch Branch(){
//        return poBranch;
//    }
//    
//    public InvLocation InvLocation(){
//        return poLocation;
//    }
//    
////    public Inventory Inventory(){
////        return poInventory;
////    }
//    
//    public Warehouse Warehouse(){
//        return poWarehouse;
//    }
//    //end - return reference objects
//    
//    @Override
//    public void initialize() {
//        psRecdStat = Logical.YES;
//        
//        poModel = new Model_AP_Client_Ledger();
//        poModel.setApplicationDriver(poGRider);
//        poModel.setXML("Model_AP_Client_Ledger");
//        poModel.setTableName("AP_Client_Ledger");
//        poModel.initialize();
//        
//        psBranchCd = poGRider.getBranchCode();
//        
//        //initialize reference objects
//        poParams = new ParamControllers(poGRider, logwrapr);
//        poBranch = poParams.Branch();
//        poLocation = poParams.InventoryLocation();
//        poWarehouse = poParams.Warehouse();
//        
////        poInv = new InvControllers(poGRider, logwrapr);
////        poInventory = poInv.Inventory();
//        //end - initialize reference objects
//    }
//    
//    @Override
//    public JSONObject isEntryOkay() {
//        poJSON = new JSONObject();
//        
//        if (poGRider.getUserLevel() < UserRight.SYSADMIN){
//            poJSON.put("result", "error");
//            poJSON.put("message", "User is not allowed to save record.");
//            return poJSON;
//        } else {
//            poJSON = new JSONObject();
//            
////            if (poModel.getClientId().isEmpty()){
////                poJSON.put("result", "error");
////                poJSON.put("message", "client must not be empty.");
////                return poJSON;
////            }
//            
////            if (poModel.getAddressId().isEmpty()){
////                poJSON.put("result", "error");
////                poJSON.put("message", "Address must not be empty.");
////                return poJSON;
////            }
////            
////            if (poModel.getContactId().isEmpty()){
////                poJSON.put("result", "error");
////                poJSON.put("message", "Contact must not be empty.");
////                return poJSON;
////            }
////            
////            if (poModel.getCategoryCode().isEmpty()){
////                poJSON.put("result", "error");
////                poJSON.put("message", "Category must not be empty.");
////                return poJSON;
////            }
//            
////            if (poModel.getBeginningDate().isEmpty()){
////                poJSON.put("result", "error");
////                poJSON.put("message", "Bin must not be empty.");
////                return poJSON;
////            }
//            
//            //todo:
//            //  more validations/use of validators per category
//        }
//        
//        poJSON.put("result", "success");
//        return poJSON;
//    }
//    
//    @Override
//    public Model_AP_Client_Ledger getModel() {
//        return poModel;
//    }
//    
//    @Override
//    public JSONObject searchRecord(String value, boolean byCode) {
//        poJSON = ShowDialogFX.Search(poGRider,
//                getSQ_Browse(),
//                value,
//                "ID»Bar Code»Description»Brand»Color»On Hand»Selling Price",
//                "sStockIDx»sBarCodex»sDescript»xModelNme»xColorNme»nQtyOnHnd»nSelPrice",
//                "a.sStockIDx»a.sBarCodex»a.sDescript»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»k.nQtyOnHnd»a.nSelPrice",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    public JSONObject searchRecord(String value, 
//                                    boolean byCode, 
//                                    String inventoryTypeId) {
//        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
//                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId));
//        
//        poJSON = ShowDialogFX.Search(poGRider,
//                lsSQL,
//                value,
//                "Bar Code»Description»Brand»Model»Color»Selling Price»ID",
//                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
//                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    public JSONObject searchRecord(String value, 
//                                    boolean byCode, 
//                                    String inventoryTypeId,
//                                    String categoryIdLevel1) {
//        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
//                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
//                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1));
//        
//        poJSON = ShowDialogFX.Search(poGRider,
//                lsSQL,
//                value,
//                "Bar Code»Description»Brand»Model»Color»Selling Price»ID",
//                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
//                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    public JSONObject searchRecord(String value, 
//                                    boolean byCode, 
//                                    String inventoryTypeId,
//                                    String categoryIdLevel1,
//                                    String categoryIdLevel2) {
//        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
//                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
//                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1) +
//                                                    " AND a.sCategCd2 = " + SQLUtil.toSQL(categoryIdLevel2));
//        
//        poJSON = ShowDialogFX.Search(poGRider,
//                lsSQL,
//                value,
//                "Bar Code»Description»Brand»Model»Color»Selling Price»ID",
//                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
//                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    public JSONObject searchRecordAttributes(String value, boolean byCode) {
//        poJSON = ShowDialogFX.Search(poGRider,
//                getSQ_Browse(),
//                value,
//                "Brand»Model»Color»Selling Price»ID",
//                "xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
//                "IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    public JSONObject searchRecordAttributes(String value, 
//                                    boolean byCode, 
//                                    String inventoryTypeId) {
//        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
//                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId));
//        
//        poJSON = ShowDialogFX.Search(poGRider,
//                lsSQL,
//                value,
//                "Brand»Model»Color»Selling Price»ID",
//                "xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
//                "IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    public JSONObject searchRecordAttributes(String value, 
//                                    boolean byCode, 
//                                    String inventoryTypeId,
//                                    String categoryIdLevel1) {
//        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
//                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
//                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1));
//        
//        poJSON = ShowDialogFX.Search(poGRider,
//                lsSQL,
//                value,
//                "Brand»Model»Color»Selling Price»ID",
//                "xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
//                "IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    public JSONObject searchRecordAttributes(String value, 
//                                    boolean byCode, 
//                                    String inventoryTypeId,
//                                    String categoryIdLevel1,
//                                    String categoryIdLevel2) {
//        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
//                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
//                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1) +
//                                                    " AND a.sCategCd2 = " + SQLUtil.toSQL(categoryIdLevel2));
//        
//        poJSON = ShowDialogFX.Search(poGRider,
//                lsSQL,
//                value,
//                "Brand»Model»Color»Selling Price»ID",
//                "xBrandNme»xModelNme»xColorNme»nSelPrice»sStockIDx",
//                "IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    public JSONObject searchRecordWithMeasurement(String value, boolean byCode) {
//        poJSON = ShowDialogFX.Search(poGRider,
//                getSQ_Browse(),
//                value,
//                "Bar Code»Description»Brand»Model»Color»Measurement»Selling Price»ID",
//                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»xMeasurNm»nSelPrice»sStockIDx",
//                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»IFNULL(e.sMeasurNm, '')»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    public JSONObject searchRecordWithMeasurement(String value, 
//                                    boolean byCode, 
//                                    String inventoryTypeId) {
//        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
//                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId));
//        
//        poJSON = ShowDialogFX.Search(poGRider,
//                lsSQL,
//                value,
//                "Bar Code»Description»Brand»Model»Color»Measurement»Selling Price»ID",
//                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»xMeasurNm»nSelPrice»sStockIDx",
//                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»IFNULL(e.sMeasurNm, '')»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    public JSONObject searchRecordWithMeasurement(String value, 
//                                    boolean byCode, 
//                                    String inventoryTypeId,
//                                    String categoryIdLevel1) {
//        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
//                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
//                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1));
//        
//        poJSON = ShowDialogFX.Search(poGRider,
//                lsSQL,
//                value,
//                "Bar Code»Description»Brand»Model»Color»Measurement»Selling Price»ID",
//                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»xMeasurNm»nSelPrice»sStockIDx",
//                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»IFNULL(e.sMeasurNm, '')»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    public JSONObject searchRecordWithMeasurement(String value, 
//                                    boolean byCode, 
//                                    String inventoryTypeId,
//                                    String categoryIdLevel1,
//                                    String categoryIdLevel2) {
//        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
//                                                "a.sInvTypCd = " + SQLUtil.toSQL(inventoryTypeId) +
//                                                    " AND a.sCategCd1 = " + SQLUtil.toSQL(categoryIdLevel1) +
//                                                    " AND a.sCategCd2 = " + SQLUtil.toSQL(categoryIdLevel2));
//        
//        poJSON = ShowDialogFX.Search(poGRider,
//                lsSQL,
//                value,
//                "Bar Code»Description»Brand»Model»Color»Measurement»Selling Price»ID",
//                "sBarCodex»sDescript»xBrandNme»xModelNme»xColorNme»xMeasurNm»nSelPrice»sStockIDx",
//                "a.sBarCodex»a.sDescript»IFNULL(b.sDescript, '')»IF(IFNULL(c.sDescript, '') = '', '', CONCAT(c.sDescript, '(', c.sModelCde, ')'))»IFNULL(d.sDescript, '')»IFNULL(e.sMeasurNm, '')»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    
//    @Override
//    public String getSQ_Browse(){
//        String lsSQL;
//        String lsRecdStat = "";
//
//        if (psRecdStat.length() > 1) {
//            for (int lnCtr = 0; lnCtr <= psRecdStat.length() - 1; lnCtr++) {
//                lsRecdStat += ", " + SQLUtil.toSQL(Character.toString(psRecdStat.charAt(lnCtr)));
//            }
//
//            lsRecdStat = "a.cRecdStat IN (" + lsRecdStat.substring(2) + ")";
//        } else {
//            lsRecdStat = "a.cRecdStat = " + SQLUtil.toSQL(psRecdStat);
//        }
//        
//        lsSQL = "SELECT" +
//                        "  sClientID" +
//                        ", nLedgerNo" +
//                        ", dTransact" +
//                        ", sSourceCd" +
//                        ", sSourceNo" +
//                        ", nAmountIn" +
//                        ", nAmountOt" +
//                        ", dPostedxx" +
//                        ", nABalance" +
//                        ", dModified" +
//                        " FROM AP_Client_Ledger";
//        
//        if (!psRecdStat.isEmpty()) lsSQL = MiscUtil.addCondition(lsSQL, lsRecdStat);
//        
//        return lsSQL;
//    }
//     private JSONObject openRecord(JSONObject json){
//        if (json != null) {
//            System.out.println("(String) poJSON.get(\"sBranchCd\") == " + psBranchCd);
//            poJSON = poModel.openRecord((String) poJSON.get("sStockIDx"), psBranchCd);
//            
//            if (!"success".equals((String) poJSON.get("result"))) return poJSON;
//            
//            //load reference records
////            poInventory.openRecord("sStockIDx");
//            poBranch.openRecord("sBranchCd");
//            poWarehouse.openRecord("sWHouseID");
//            poLocation.openRecord("sLocatnID");
//            //end -load reference records
//        } else {
//            poJSON = new JSONObject();
//            poJSON.put("result", "error");
//            poJSON.put("message", "No record loaded.");
//            return poJSON;
//        }
//        
//        return poJSON;
//    }
////    private JSONObject openRecord(JSONObject json){
////        if (json != null) {
////             System.out.println("stockid == " + (String) poJSON.get("sStockIDx"));
////             String lsStockID = (String) poJSON.get("sStockIDx");
////           poJSON = poModel.openRecord((String) poJSON.get("sStockIDx"), (String) poJSON.get("sBranchCd"));
////           if ("error".equals((String) poJSON.get("result"))){
////               if ((String) poJSON.get("sBranchCd") == null){
////                ShowMessageFX.Information("No Inventory found in your warehouse. Please save the record to create.", "Computerized Acounting System", "Inventory Detail");
////                   System.out.println("stockid == " + lsStockID);
////                poJSON = poInventory.openRecord((String) poJSON.get("sStockIDx"));
//////                EditMode.ADDNEW;    
////                } 
////                return poJSON;
////            }
////            
////            
////            
////            else {
////                poJSON = poModel.openRecord((String) poJSON.get("sStockIDx"), (String) poJSON.get("sBranchCd"));
////            }
////            
//////            if ("error".equals((String)poJSON.get("result"))){
//////                ShowMessageFX.Information("No Inventory found in your warehouse. Please save the record to create.", "Computerized Acounting System", "Inventory Detail");
//////                 poJSON = newRecord();
//////            
//////            }
////            
////            //load reference records
////            poInventory.openRecord("sStockIDx");
////            poBranch.openRecord("sBranchCd");
////            poWarehouse.openRecord("sWHouseID");
////            poLocation.openRecord("sLocatnID");
////            //end -load reference records
////        } else {
////            poJSON = new JSONObject();
////            poJSON.put("result", "error");
////            poJSON.put("message", "No record loaded.");
////            return poJSON;
////        }
////        
////        return poJSON;
////    }
//        public JSONObject searchRecordwithBarrcode(String value, boolean byCode) {
//            
//        poJSON = ShowDialogFX.Search(poGRider,
//                getSQ_Browse(),
//                value,
//                "BarCode»Description»Selling Price»ID",
//                "sBarCodex»sDescript»nSelPrice»sStockIDx",
//                "a.sBarCodex»a.sDescript»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//
//        return openRecord(poJSON);
//    }
//    public JSONObject searchRecordwithBarrcode(String value, 
//                                    boolean byCode, 
//                                    String stockID) {
//        String lsSQL = MiscUtil.addCondition(getSQ_Browse(), 
//                                                    "a.sStockIDx = " + SQLUtil.toSQL(stockID));
//        
//        poJSON = ShowDialogFX.Search(poGRider,
//                lsSQL,
//                value,
//                  "BarCode»Description»Selling Price»ID",
//                "sBarCodex»sDescript»nSelPrice»sStockIDx",
//                "a.sBarCodex»a.sDescript»a.nSelPrice»a.sStockIDx",
//                byCode ? 0 : 1);
//        System.out.println("poJSON = " + poJSON);
//        return openRecord(poJSON);
//    }
//}
//        