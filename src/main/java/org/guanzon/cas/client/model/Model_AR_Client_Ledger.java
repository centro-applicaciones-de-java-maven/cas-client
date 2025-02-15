package org.guanzon.cas.client.model;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.parameter.model.Model_Branch;
import org.guanzon.cas.parameter.model.Model_Warehouse;
import org.guanzon.cas.parameter.services.ParamModels;
import org.json.simple.JSONObject;

public class Model_AR_Client_Ledger extends Model {

    //reference objects
    Model_Branch poBranch;
    Model_Warehouse poWarehouse;
//    Model_Inventory poInventory;

    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());

            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);

            //assign default values
            //end - assign default values
            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

            ID = "sClientID";
            ID2 = "dTransact";
            ID3 = "sSourceCd";
            ID4 = "sSourceNo";
            //initialize reference objects
            ParamModels model = new ParamModels(poGRider);
            poBranch = model.Branch();
            poWarehouse = model.Warehouse();

//            poInventory = new InvModels(poGRider).Inventory();
            //end - initialize reference objects
            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }

    public JSONObject setClientId(String clientId) {
        return setValue("sClientID", clientId);
    }

    public String getClientId() {
        return (String) getValue("sClientID");
    }

    public JSONObject setLedgerNo(String ledgerNo) {
        return setValue("nLedgerNo", ledgerNo);
    }

    public String getLedgerNo() {
        return (String) getValue("nLedgerNo");
    }

    public JSONObject setTransactionDate(Date transactionDate) {
        return setValue("dTransact", transactionDate);
    }

    public Date getTransactionDate() {
        return (Date) getValue("dTransact");
    }

    public JSONObject setSourceCode(String sourceCode) {
        return setValue("sSourceCd", sourceCode);
    }

    public String getSourceCode() {
        return (String) getValue("sSourceCd");
    }

    public JSONObject setSourceNo(String sourceNo) {
        return setValue("sSourceNo", sourceNo);
    }

    public String getSourceNo() {
        return (String) getValue("sSourceNo");
    }

//    public JSONObject setAmountIn(Double anmountIn){
//        return setValue("nAmountIn", anmountIn);
//    }
//    
//    public Double getAmountIn(){
//        return (Double) getValue("nAmountIn");
//    }   
    public Double getAmountIn() {
        Object value = getValue("nAmountIn");
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).doubleValue(); // ✅ Convert BigDecimal to Double
        } else if (value instanceof Double) {
            return (Double) value; // ✅ Already Double, return as is
        } else {
            return 0.0; // ✅ Default value if null or unexpected type
        }
    }

    public JSONObject setAmountIn(Object amountIn) {
        if (amountIn instanceof BigDecimal) {
            return setValue("nAmountIn", ((BigDecimal) amountIn).doubleValue()); // Convert to Double
        } else if (amountIn instanceof Double) {
            return setValue("nAmountIn", amountIn);
        } else {
            return setValue("nAmountIn", 0.0); // Default value
        }
    }

    public Double getAmountOt() {
        Object value = getValue("nAmountOt");
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).doubleValue(); // ✅ Convert BigDecimal to Double
        } else if (value instanceof Double) {
            return (Double) value; // ✅ Already Double, return as is
        } else {
            return 0.0; // ✅ Default value if null or unexpected type
        }
    }

    public JSONObject setAmountOt(Object amountIn) {
        if (amountIn instanceof BigDecimal) {
            return setValue("nAmountOt", ((BigDecimal) amountIn).doubleValue()); // Convert to Double
        } else if (amountIn instanceof Double) {
            return setValue("nAmountOt", amountIn);
        } else {
            return setValue("nAmountOt", 0.0); // Default value
        }
    }

//    public JSONObject setAmountOt(Double anmountOt){
//        return setValue("nAmountOt", anmountOt);
//    }
//    
//    public Double getAmountOt(){
//        return (Double) getValue("nAmountOt");
//    }
    public JSONObject setDatePosted(Date datePosted) {
        return setValue("dPostedxx", datePosted);
    }

    public Date getDatePosted() {
        return (Date) getValue("dPostedxx");
    }

    public JSONObject setAccountBalance(Double accountBalance) {
        return setValue("nABalance", accountBalance);
    }

    public Double getAccountBalance() {
        return (Double) getValue("nABalance");
    }

    public JSONObject setModifiedDate(Date modifiedDate) {
        return setValue("dModified", modifiedDate);
    }

    public Date getModifiedDate() {
        return (Date) getValue("dModified");
    }

    @Override
    public String getNextCode() {
        return "";
    }

    @Override
    public JSONObject openRecord(String id) {
        JSONObject loJSON = new JSONObject();
        loJSON.put("result", "error");
        loJSON.put("message", "This feature is not supported.");
        return loJSON;
    }

    @Override
    public JSONObject openRecord(String Id1, Object Id2) {
        JSONObject loJSON = new JSONObject();
        loJSON.put("result", "error");
        loJSON.put("message", "This feature is not supported.");
        return loJSON;
    }

    @Override
    public JSONObject openRecord(String Id1, Object Id2, Object Id3) {
        JSONObject loJSON = new JSONObject();
        loJSON.put("result", "error");
        loJSON.put("message", "This feature is not supported.");
        return loJSON;
    }

//    @Override
//    public JSONObject openRecord(String Id1, Object Id2, Object Id3, Object Id4) {
//        JSONObject loJSON = new JSONObject();
//        loJSON.put("result", "error");
//        loJSON.put("message", "This feature is not supported.");
//        return loJSON;
//    }
    //reference object models
    public Model_Branch Branch() {
        if (!"".equals((String) getValue("sBranchCd"))) {
            if (poBranch.getEditMode() == EditMode.READY
                    && poBranch.getBranchCode().equals((String) getValue("sBranchCd"))) {
                return poBranch;
            } else {
                poJSON = poBranch.openRecord((String) getValue("sBranchCd"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poBranch;
                } else {
                    poBranch.initialize();
                    return poBranch;
                }
            }
        } else {
            poBranch.initialize();
            return poBranch;
        }
    }

}

//    public Model_Inventory Inventory() {
//        if (!"".equals((String) getValue("sStockIDx"))) {
//            if (poInventory.getEditMode() == EditMode.READY
//                    && poInventory.getStockId().equals((String) getValue("sStockIDx"))) {
//                return poInventory;
//            } else {
//                poJSON = poInventory.openRecord((String) getValue("sStockIDx"));
//
//                if ("success".equals((String) poJSON.get("result"))) {
//                    return poInventory;
//                } else {
//                    poInventory.initialize();
//                    return poInventory;
//                }
//            }
//        } else {
//            poInventory.initialize();
//            return poInventory;
//        }
//    }
//end - reference object models

