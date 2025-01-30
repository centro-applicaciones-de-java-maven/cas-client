package org.guanzon.cas.client.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.cas.parameter.model.Model_Branch;
import org.guanzon.cas.parameter.model.Model_Category;
import org.guanzon.cas.parameter.model.Model_Inv_Location;
import org.guanzon.cas.parameter.model.Model_Term;
import org.guanzon.cas.parameter.model.Model_Warehouse;
import org.guanzon.cas.parameter.services.ParamModels;
import org.json.simple.JSONObject;

public class Model_AP_Client_Master extends Model{      
    //reference objects
    Model_Branch poBranch;
    Model_Warehouse poWarehouse;
//    Model_Inventory poInventory;
    Model_Inv_Location poLocation;
    Model_Category poCategory;
    
    Model_Client_Master poClientMaster;
    Model_Client_Address poClientAddress;
    Model_Client_Institution_Contact poClientInstitutionContact;
    Model_Client_Mobile poClientMobile;
    Model_Term poTerm;
    
    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());
            
            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);
            
            //assign default values
            
            Object currentDate = SQLUtil.toDate(poGRider.getServerDate().toString(), SQLUtil.FORMAT_SHORT_DATE);
//            
//            poEntity.updateObject("dCltSince", poGRider.getSysDate());
//            poEntity.updateObject("dBegDatex", poGRider.getSysDate());
            poEntity.updateObject("dCltSince", "1900-01-01");
            poEntity.updateObject("dBegDatex", "1900-01-01");
            poEntity.updateObject("nDiscount", 0.00);
            poEntity.updateObject("nCredLimt", 0.00);
            poEntity.updateObject("nABalance", 0.00);
            poEntity.updateObject("nOBalance", 0.00);
            poEntity.updateObject("nBegBalxx", 0.00);
//            poEntity.updateObject("nQtyOnHnd", 0);
//            poEntity.updateObject("nLedgerNo", 0);
//            poEntity.updateObject("nMinLevel", 0);
//            poEntity.updateObject("nMaxLevel", 0);
//            poEntity.updateObject("nAvgMonSl", 0);
//            poEntity.updateObject("nAvgCostx", 0.00);
//            poEntity.updateString("cClassify", InventoryClassification.NEW_ITEMS);
//            poEntity.updateObject("nBackOrdr", 0);
//            poEntity.updateObject("nResvOrdr", 0);
//            poEntity.updateObject("nFloatQty", 0);
//            poEntity.updateObject("dLastTran", "0000-00-00");
//            poEntity.updateString("cPrimaryx", Logical.NO);
            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);
            //end - assign default values

            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

            ID = "sClientID";
//            ID2 = "sBranchCd";
            
            //initialize reference objects
            ParamModels model = new ParamModels(poGRider);
            poCategory = model.Category();
            poBranch = model.Branch();
            poWarehouse = model.Warehouse();
            poLocation = model.InventoryLocation();
            
            
            poClientMaster = new Model_Client_Master();
            poClientMaster.setApplicationDriver(poGRider);
            poClientMaster.setXML("Model_Client_Master");
            poClientMaster.setTableName("Client_Master");
            poClientMaster.initialize();
            
            poClientAddress = new Model_Client_Address();
            poClientAddress.setApplicationDriver(poGRider);
            poClientAddress.setXML("Model_Client_Address");
            poClientAddress.setTableName("Client_Address");
            poClientAddress.initialize();
//            poInventory = new InvModels(poGRider).Inventory();
            //end - initialize reference objects
            
            poClientInstitutionContact = new Model_Client_Institution_Contact();
            poClientInstitutionContact.setApplicationDriver(poGRider);
            poClientInstitutionContact.setXML("Model_Client_Institution_Contact_Person");
            poClientInstitutionContact.setTableName("Client_Institution_Contact_Person");
            poClientInstitutionContact.initialize();
            
            poClientMobile = new Model_Client_Mobile();
            poClientMobile.setApplicationDriver(poGRider);
            poClientMobile.setXML("Model_Client_Mobile");
            poClientMobile.setTableName("Client_Mobile");
            poClientMobile.initialize();
            
            poTerm = new Model_Term();
            poTerm.setApplicationDriver(poGRider);
            poTerm.setXML("Model_Term");
            poTerm.setTableName("Term");
            poTerm.initialize();
            
            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }
    
    public JSONObject setClientId(String clientId){
        return setValue("sClientID", clientId);
    }
    
    public String getClientId(){
        return (String) getValue("sClientID");
    }
    
    public JSONObject setAddressId(String addressId){
        return setValue("sAddrssID", addressId);
    }
    
    public String getAddressId(){
        return (String) getValue("sAddrssID");
    }
    
    public JSONObject setContactId(String contactId){
        return setValue("sContctID", contactId);
    }
    
    public String getContactId(){
        return (String) getValue("sContctID");
    }
    
    public JSONObject setCategoryCode(String categoryCode){
        return setValue("sCategrCd", categoryCode);
    }
    
    public String getCategoryCode(){
        return (String) getValue("sCategrCd");
    }
    
    public JSONObject setdateClientSince(Date dateClientSince){
        return setValue("dCltSince", dateClientSince);
    }
    

    public Date getdateClientSince(){
        Date date = null;
        if(!getValue("dCltSince").toString().isEmpty()){
            date = CommonUtils.toDate(getValue("dCltSince").toString());
        }
        return date;
    }
    
//    public Date getdateClientSince(){
//        return (Date) getValue("dCltSince");
//    }
    
    public JSONObject setBeginningDate(Date beginningDate){
        return setValue("dBegDatex", beginningDate);
    }
    
    public Date getBeginningDate(){
        return (Date) getValue("dBegDatex");
    }
    
    public JSONObject setBeginningBalance(Number beginningBalancce){
        return setValue("nBegBalxx", beginningBalancce);
    }
    
    public Number getBeginningBalance(){
        return (Number) getValue("nBegBalxx");
    }   
    
    public JSONObject setTermId(String termId){
        return setValue("sTermIDxx", termId );
    }
    
    public String getTermId(){
        return (String) getValue("sTermIDxx");
    }
    
    public JSONObject setDiscount(Number discount) {
        return setValue("nDiscount", discount);
    }

    public Number getDiscount() {
        return (Number) getValue("nDiscount");
    }
    
    public JSONObject setCreditLimit(Number creditLimit) {
        return setValue("nCredLimt", creditLimit);
    }

    public Number getCreditLimit() {
        return (Number) getValue("nCredLimt");
    }
    
    public JSONObject setAccountBalance(Number accountBalance) {
        return setValue("nABalance", accountBalance);
    }

    public Number getAccountBalance() {
        return (Number) getValue("nABalance");
    }

    public JSONObject setOBalance(Number oBalance) {
        return setValue("nOBalance", oBalance);
    }

    public Number getOBalance() {
        return (Number) getValue("nOBalance");
    }

    public JSONObject setLedgerNo(int ledgerNo){
        return setValue("nLedgerNo", ledgerNo);
    }
    
    public int getLedgerNo(){
        return (int) getValue("nLedgerNo");
    }  
    
    public JSONObject setVatable(String vatable){
        return setValue("cVatablex", vatable);
    }
    
    public String getVatable(){
        return (String) getValue("cVatablex");
    }
    
    public JSONObject setRecordStatus(String recordStatus){
        return setValue("cRecdStat", recordStatus);
    }
    
    public String getRecordStatus(){
        return (String) getValue("cRecdStat");
    }
    
    public JSONObject setModifyingId(String modifyingId){
        return setValue("sModified", modifyingId);
    }
    
    public String getModifyingId(){
        return (String) getValue("sModified");
    }
    
    public JSONObject setModifiedDate(Date modifiedDate){
        return setValue("dModified", modifiedDate);
    }
    
    public Date getModifiedDate(){
        return (Date) getValue("dModified");
    }
    
    @Override
    public String getNextCode() {
        return MiscUtil.getNextCode(getTable(), ID, true, poGRider.getConnection(), poGRider.getBranchCode());
    }
    
//    @Override
//    public JSONObject openRecord(String Id1) {
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
    
    public Model_Warehouse Warehouse() {
        if (!"".equals((String) getValue("sWHouseID"))) {
            if (poWarehouse.getEditMode() == EditMode.READY
                    && poWarehouse.getWarehouseId().equals((String) getValue("sWHouseID"))) {
                return poWarehouse;
            } else {
                poJSON = poWarehouse.openRecord((String) getValue("sWHouseID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poWarehouse;
                } else {
                    poWarehouse.initialize();
                    return poWarehouse;
                }
            }
        } else {
            poWarehouse.initialize();
            return poWarehouse;
        }
    }
    
    public Model_Inv_Location Location() {
        if (!"".equals((String) getValue("sLocatnID"))) {
            if (poLocation.getEditMode() == EditMode.READY
                    && poLocation.getLocationId().equals((String) getValue("sLocatnID"))) {
                return poLocation;
            } else {
                poJSON = poLocation.openRecord((String) getValue("sLocatnID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poLocation;
                } else {
                    poLocation.initialize();
                    return poLocation;
                }
            }
        } else {
            poLocation.initialize();
            return poLocation;
        }
    }
    
        public Model_Category Category() {
        if (!"".equals((String) getValue("sCategrCd"))) {
            if (poCategory.getEditMode() == EditMode.READY
                    && poCategory.getCategoryId().equals((String) getValue("sCategrCd"))) {
                return poCategory;
            } else {
                poJSON = poCategory.openRecord((String) getValue("sCategrCd"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poCategory;
                } else {
                    poCategory.initialize();
                    return poCategory;
                }
            }
        } else {
            poCategory.initialize();
            return poCategory;
        }
    }
        
    public Model_Client_Master ClientMaster() {

        
        if (!"".equals((String) getValue("sClientID"))) {
            if (poClientMaster.getEditMode() == EditMode.READY
                    && poClientMaster.getClientId().equals((String) getValue("sClientID"))) {
                return poClientMaster;
            } else {
                poJSON = poClientMaster.openRecord((String) getValue("sClientID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poClientMaster;
                } else {
                    poClientMaster.initialize();
                    return poClientMaster;
                }
            }
        } else {
            poClientMaster.initialize();
            return poClientMaster;
        }
    }
    
    public Model_Client_Address ClientAddress() {
        System.out.println("Model_Client_Address == " + (String) getValue("sClientID"));
        System.out.println("poClientAddress == " + (String) getValue("sAddrssID"));
        
        if (!"".equals((String) getValue("sClientID"))) {
            if (poClientAddress.getEditMode() == EditMode.READY
                    && poClientAddress.getClientId().equals((String) getValue("sAddrssID"))) {
                return poClientAddress;
            } else {
                poJSON = poClientAddress.openRecord((String) getValue("sAddrssID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poClientAddress;
                } else {
                    poClientAddress.initialize();
                    return poClientAddress;
                }
            }
        } else {
            poClientAddress.initialize();
            return poClientAddress;
        }
    }
    
    public Model_Client_Institution_Contact ClientInstitutionContact() {
        
            System.out.println("Client_Institution_Contact == " + (String) getValue("sClientID"));
        if (!"".equals((String) getValue("sClientID"))) {
            if (poClientInstitutionContact.getEditMode() == EditMode.READY
                    && poClientInstitutionContact.getClientId().equals((String) getValue("sContctID"))) {
                return poClientInstitutionContact;
            } else {
                poJSON = poClientInstitutionContact.openRecord((String) getValue("sContctID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poClientInstitutionContact;
                } else {
                    poClientInstitutionContact.initialize();
                    return poClientInstitutionContact;
                }
            }
        } else {
            poClientInstitutionContact.initialize();
            return poClientInstitutionContact;
        }
    }
    
        public Model_Client_Mobile ClientMobile() {
            System.out.println("mobile == " + (String) getValue("sClientID"));
        if (!"".equals((String) getValue("sClientID"))) {
            if (poClientMobile.getEditMode() == EditMode.READY
                    && poClientMobile.getClientId().equals((String) getValue("sClientID"))) {
                return poClientMobile;
            } else {
                poJSON = poClientMobile.openRecord((String) getValue("sClientID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poClientMobile;
                } else {
                    poClientMobile.initialize();
                    return poClientMobile;
                }
            }
        } else {
            poClientMobile.initialize();
            return poClientMobile;
        }
    }
        
        public Model_Term Term() {
            System.out.println("term == " + (String) getValue("sTermIDxx"));
        if (!"".equals((String) getValue("sTermIDxx"))) {
            if (poTerm.getEditMode() == EditMode.READY
                    && poTerm.getTermCode().equals((String) getValue("sTermIDxx"))) {
                return poTerm;
            } else {
                poJSON = poTerm.openRecord((String) getValue("sTermIDxx"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poTerm;
                } else {
                    poTerm.initialize();
                    return poTerm;
                }
            }
        } else {
            poTerm.initialize();
            return poTerm;
        }
    }
}