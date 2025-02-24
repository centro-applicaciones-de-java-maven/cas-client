package org.guanzon.cas.client.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.client.model.Model_AP_Client_Ledger;
import org.guanzon.cas.client.model.Model_AR_Client_Ledger;
import org.guanzon.cas.client.services.CasClientControllers;
import org.guanzon.cas.client.services.CasClientModel;
import org.json.simple.JSONObject;

public class ARClients {

    GRider poGRider;
    String psParent;

    JSONObject poJSON;
    LogWrapper poLogWrapper;

    AR_Client_Master poARClientMaster;
    List<Model_AR_Client_Ledger> poARClientLedger;

    public ARClients(GRider applicationDriver,
            String parentClass,
            LogWrapper logWrapper) {

        poGRider = applicationDriver;
        psParent = parentClass;
        poLogWrapper = logWrapper;

        poARClientMaster = new CasClientControllers(applicationDriver, logWrapper).ARClientMaster();
        poARClientLedger = new ArrayList<>();
    }

    public AR_Client_Master ARClientMaster() {
        return poARClientMaster;
    }

    public Model_AR_Client_Ledger ARClientLedger(int row) {
        return poARClientLedger.get(row);
    }
       
    public int getLedgerCount() {
        return poARClientLedger.size();
    }
    
    public JSONObject addARClientLedger() {
        poJSON = new JSONObject();
        if (poARClientLedger.isEmpty()){
            poARClientLedger.add(aRClientLedger());            
        } else {
            if (!poARClientLedger.get(poARClientLedger.size()-1).getClientId().isEmpty()){
                poARClientLedger.add(aRClientLedger());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add Ledger.");
                return poJSON;
            }
        }

        poJSON.put("result", "success");
        return poJSON;
    }
    public JSONObject delARClientLedger(int row) {
        poJSON = new JSONObject();

        if (poARClientLedger.isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete ledger. Mobile list is empty.");
            return poJSON;
        }

        if (row >= poARClientLedger.size()) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete ledger. Row is more than the mobile list.");
            return poJSON;
        }

        if (poARClientLedger.get(row).getEditMode() != EditMode.ADDNEW) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete old mobile. You can deactivate the record instead.");
            return poJSON;
        }

        poARClientLedger.remove(row);
        poJSON.put("result", "success");
        return poJSON;
    }

   

    public JSONObject New() {
        poJSON = poARClientMaster.newRecord();
        if (!"success".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        poARClientLedger.clear();
        poJSON = addARClientLedger();
        if (!"success".equals((String) poJSON.get("result"))) {
            return poJSON;
        }
        
        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }

    public JSONObject Save() {
        int lnCtr;

        if (psParent.isEmpty()) {
            poGRider.beginTrans();
        }

        //assign modified info
        poARClientMaster.getModel().setModifiedDate(poGRider.getServerDate());

        //save client master
        poJSON = poARClientMaster.saveRecord();

        if (!"success".equals((String) poJSON.get("result"))) {
            if (psParent.isEmpty()) {
                poGRider.rollbackTrans();
            }
            return poJSON;
        }

        //save inv ledger
        if (!poARClientLedger.isEmpty()) {
            for (lnCtr = 0; lnCtr <= poARClientLedger.size() - 1; lnCtr++) {
                if ((poARClientLedger.get(lnCtr).getEditMode() == EditMode.ADDNEW
                        || poARClientLedger.get(lnCtr).getEditMode() == EditMode.UPDATE)
                        && !poARClientLedger.get(lnCtr).getClientId().isEmpty()) {

                    if (poARClientLedger.get(lnCtr).getEditMode() == EditMode.ADDNEW) {
                        poARClientLedger.get(lnCtr).setClientId(poARClientMaster.getModel().getClientId());
                    }

                    poARClientLedger.get(lnCtr).setModifiedDate(poARClientMaster.getModel().getModifiedDate());

                    //save
                    poJSON = poARClientLedger.get(lnCtr).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))) {
                        if (psParent.isEmpty()) {
                            poGRider.rollbackTrans();
                        }
                        return poJSON;
                    }
                }
            }
        }
       
        if (psParent.isEmpty()) {
            poGRider.commitTrans();
        }

        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }

    private Model_AR_Client_Ledger aRClientLedger() {
        return new CasClientModel(poGRider).ARClientLedger();
    }
 
   
    private Model_AR_Client_Ledger aR_Client_Ledger(String clientid,String date, String sourceCd, String sourceNo) {
        Model_AR_Client_Ledger object = new CasClientModel(poGRider).ARClientLedger();

        JSONObject loJSON = object.openRecord(clientid,date,sourceCd,sourceNo);

        if ("success".equals((String) loJSON.get("result"))) {
            return object;
        } else {
            return new CasClientModel(poGRider).ARClientLedger();
        }
    }
    
    

    public JSONObject OpenLedger(String fsclientID ) {
        StringBuilder lsSQL = new StringBuilder("SELECT" +
                        "  sClientID" +
                        ", nLedgerNo" +
                        ", dTransact" +
                        ", sSourceCd" +
                        ", sSourceNo" +
                        ", nAmountIn" +
                        ", nAmountOt" +
                        ", dPostedxx" +
                        ", nABalance" +
                        ", dModified" +
                        " FROM AR_Client_Ledger");

        // Use SQLUtil.toSQL for handling the dates
        String condition = "sClientID = " + SQLUtil.toSQL(fsclientID);
        lsSQL.append(MiscUtil.addCondition(" ", condition));
        lsSQL.append(" ORDER BY nLedgerNo ASC");

        System.out.println("Executing SQL: " + lsSQL.toString());

        ResultSet loRS = poGRider.executeQuery(lsSQL.toString());
        JSONObject poJSON = new JSONObject();

        try {
            int lnctr = 0;

            if (MiscUtil.RecordCount(loRS) >= 0) {
                poARClientLedger = new ArrayList<>();
                while (loRS.next()) {
                    // Print the result set

//                    System.out.println("dTransact: " + loRS.getString("dTransact"));
                    System.out.println("sClientID: " + loRS.getString("sClientID"));
                    System.out.println("nLedgerNo: " + loRS.getInt("nLedgerNo"));
                    System.out.println("dTransact: " + loRS.getDate("dTransact"));
                    System.out.println("sSourceCd: " + loRS.getString("sSourceCd"));
                    System.out.println("sSourceNo: " + loRS.getString("sSourceNo"));
                    System.out.println("nAmountIn: " + loRS.getString("nAmountIn"));
                    System.out.println("nAmountOt: " + loRS.getString("nAmountOt"));
                    System.out.println("------------------------------------------------------------------------------");

                    poARClientLedger.add(aR_Client_Ledger(loRS.getString("sClientID"),loRS.getString("dTransact"),loRS.getString("sSourceCd"),loRS.getString("sSourceNo")));
                    poARClientLedger.get(poARClientLedger.size() - 1)
                            .openRecord(loRS.getString("sClientID"),loRS.getString("dTransact"),loRS.getString("sSourceCd"),loRS.getString("sSourceNo"));
                    lnctr++;
                }

                System.out.println("Records found: " + lnctr);
                poJSON.put("result", "success");
                poJSON.put("message", "Record loaded successfully.");

            } else {
                poARClientLedger = new ArrayList<>();
                addARClientLedger();
                poJSON.put("result", "error");
                poJSON.put("continue", true);
                poJSON.put("message", "No record found .");
            }
            MiscUtil.close(loRS);
        } catch (SQLException e) {
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
        }
        return poJSON;
    }
//
//    public JSONObject OpenInvSerialLedger(String fsStockID, String fsUnitType ) {
//        StringBuilder lsSQL = new StringBuilder("SELECT" +
//               "   a.sSerialID" +
//               " , a.sBranchCd" +
//               " , a.sSerial01" +
//               " , a.sSerial02" +
//               " , a.nUnitPrce" +
//               " , a.sStockIDx" +
//               " , a.cLocation" +
//               " , a.cSoldStat" +
//               " , a.cUnitType" +
//               " , a.sCompnyID" +
//               " , a.sWarranty" +
//               " , a.dModified" +
//               " , b.sBrandIDx" +
//               " , b.sBarCodex AS xBarCodex" + 
//               " , b.sDescript AS xDescript" + 
//               " FROM Inv_Serial a" +
//               " LEFT JOIN Inventory b ON a.sStockIDx = b.sStockIDx");
//
//        // Use SQLUtil.toSQL for handling the dates
//        String condition = "a.sStockIDx = " + SQLUtil.toSQL(fsStockID)
//                + " AND a.cUnitType like " + SQLUtil.toSQL(fsUnitType);
//        lsSQL.append(MiscUtil.addCondition("", condition));
////        lsSQL.append(" ORDER BY a.nLedgerNo ASC");
//
//        System.out.println("Executing SQL: " + lsSQL.toString());
//
//        ResultSet loRS = poGRider.executeQuery(lsSQL.toString());
//        JSONObject poJSON = new JSONObject();
//
//        try {
//            int lnctr = 0;
//
//            if (MiscUtil.RecordCount(loRS) >= 0) {
//                poSerialLedger = new ArrayList<>();
//                while (loRS.next()) {
//                    // Print the result set
//
//                    System.out.println("sSerialID: " + loRS.getString("sSerialID"));
//                    System.out.println("sSerial01: " + loRS.getString("sSerial01"));
//                    System.out.println("sSerial02: " + loRS.getString("sSerial02"));
//                    System.out.println("------------------------------------------------------------------------------");
//
//                    poSerialLedger.add(inventorySerial(loRS.getString("sSerialID"),loRS.getString("sBranchCd")));
//                    poSerialLedger.get(poSerialLedger.size() - 1)
//                            .openRecord(loRS.getString("sSerialID"),loRS.getString("sBranchCd"));
//                    lnctr++;
//                }
//
//                System.out.println("Records found: " + lnctr);
//                poJSON.put("result", "success");
//                poJSON.put("message", "Record loaded successfully.");
//
//            } else {
//                poSerialLedger = new ArrayList<>();
//                addInvLedger();
//                poJSON.put("result", "error");
//                poJSON.put("continue", true);
//                poJSON.put("message", "No record found .");
//            }
//            MiscUtil.close(loRS);
//        } catch (SQLException e) {
//            poJSON.put("result", "error");
//            poJSON.put("message", e.getMessage());
//        }
//        System.out.println("RESULT == " + poJSON);
//        return poJSON;
//    }
    
    
}
