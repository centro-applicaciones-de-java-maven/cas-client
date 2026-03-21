package org.guanzon.cas.client.account;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.agent.systables.SysTableContollers;
import org.guanzon.appdriver.agent.systables.TransactionAttachment;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.base.WebFile;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.appdriver.iface.GValidator;
import org.guanzon.cas.client.model.Model_AP_Client_Ledger;
import org.guanzon.cas.client.model.Model_AP_Client_Master;
import org.guanzon.cas.client.services.ClientModels;
import org.guanzon.cas.client.validator.APClientValidatorFactory;
import org.json.simple.JSONObject;

public class AP_Client_Master extends Parameter {

    private Model_AP_Client_Master poModel;
    
    private List<Model_AP_Client_Ledger> paLedger;
    
    private List<TransactionAttachment> paAttachments;
    
    private TransactionAttachment TransactionAttachment() throws SQLException, GuanzonException {
        return new SysTableContollers(poGRider, logwrapr).TransactionAttachment();
    }

    @SuppressWarnings("unchecked")
    public List<Model_AP_Client_Ledger> getLedgerList() {
        return (List<Model_AP_Client_Ledger>) (List<?>) paLedger;
    }
    
    public List<TransactionAttachment> getAttachmentList() throws SQLException, GuanzonException {
        return paAttachments;
    }
    
    public int getTransactionAttachmentCount() {
        if (paAttachments == null) {
            paAttachments = new ArrayList<>();
        }
        return paAttachments.size();
    }

    @Override
    public void initialize() throws SQLException, GuanzonException {
        super.initialize();

        psRecdStat = TransactionStatus.STATE_OPEN;
        paLedger = new ArrayList<Model_AP_Client_Ledger>();

        ClientModels model = new ClientModels(poGRider);
        poModel = model.APClientMaster();
        
        paAttachments = new ArrayList<>();
    }

    @Override
    public JSONObject isEntryOkay() throws SQLException, GuanzonException, CloneNotSupportedException {
        poJSON = new JSONObject();

        //initialize validator
        GValidator loValidator = APClientValidatorFactory.make(poGRider.getIndustry());
        
        //initialize params for app validator
        loValidator.setApplicationDriver(poGRider);
        loValidator.setMaster(poModel);
        
        //validate
        poJSON = loValidator.validate();
        
        //if validation not success
        if ("error".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        //set modified date and id
        poModel.setModifyingId(poGRider.getUserID());
        poModel.setModifiedDate(poGRider.getServerDate());

        poJSON.put("result", "success");
        return poJSON;
    }

    @Override
    public Model_AP_Client_Master getModel() {
        return poModel;
    }

    @Override
    public JSONObject searchRecord(String value, boolean byCode) throws SQLException, GuanzonException {
        String lsSQL = getSQ_Browse();

        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Client ID»Name»Address»Contact Person",
                "sClientID»sCompnyNm»xAddressx»xContactP",
                "a.sClientID»b.sCompnyNm»TRIM(CONCAT(c.sHouseNox, ', ', c.sAddressx, ', ', c.sBrgyIDxx, ', ', c.sTownIDxx))»d.sCPerson1",
                byCode ? 0 : 1);

        if (poJSON != null) {
            //clear retrieve ledger
            paLedger.clear();

            return poModel.openRecord((String) poJSON.get("sClientID"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }

    @Override
    public String getSQ_Browse() {
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
                    ", a.cHoldAcct" +
                    ", a.cAutoHold" +
                    ", a.cRecdStat" +
                    ", b.sCompnyNm" +
                    ", c.sAddrssID" +
                    ", d.sMobileNo" +
                    ", TRIM(CONCAT(c.sHouseNox, ', ', c.sAddressx, ', ', c.sBrgyIDxx, ', ', c.sTownIDxx)) xAddressx" +
                    ", d.sCPerson1 xContactP" +
                " FROM AP_Client_Master a" +
                    " LEFT JOIN Client_Master b" +
                        " ON a.sClientID = b.sClientID" +
                    " LEFT JOIN Client_Address c" +
                        " ON a.sAddrssID = c.sAddrssID" +
                    " LEFT JOIN Client_Institution_Contact_Person d" +
                        " ON a.sContctID = d.sContctID";

        if (!lsCondition.isEmpty()) {
            lsSQL = MiscUtil.addCondition(lsSQL, lsCondition);
        }

        return lsSQL;
    }

    public JSONObject addAttachment() throws SQLException, GuanzonException {
        poJSON = new JSONObject();

        if (paAttachments.isEmpty()) {
            
            paAttachments.add(TransactionAttachment());
            System.out.print("add attachment size is " + paAttachments.size());
            poJSON = paAttachments.get(getTransactionAttachmentCount() - 1).newRecord();
            System.out.print("add attachment result is " + poJSON);
        } else {
            
            if (!paAttachments.get(paAttachments.size() - 1).getModel().getTransactionNo().isEmpty()) {
                paAttachments.add(TransactionAttachment());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add transaction attachment.");
                return poJSON;
            }
        }
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public int addAttachment(String fFileName) throws SQLException, GuanzonException{
        
        for(int lnCtr = 0;lnCtr <= getTransactionAttachmentCount() - 1;lnCtr++){
            
            if(fFileName.equals(paAttachments.get(lnCtr).getModel().getFileName())
                && RecordStatus.INACTIVE.equals(paAttachments.get(lnCtr).getModel().getRecordStatus())){
                
                paAttachments.get(lnCtr).getModel().setRecordStatus(RecordStatus.ACTIVE);
                System.out.println("Attachment :"+ lnCtr+" Activate");
                return lnCtr;
            }
        }
        addAttachment();
        
        paAttachments.get(getTransactionAttachmentCount() - 1).getModel().setFileName(fFileName);
        paAttachments.get(getTransactionAttachmentCount() - 1).getModel().setSourceNo(poModel.getClientId());
        paAttachments.get(getTransactionAttachmentCount() - 1).getModel().setRecordStatus(RecordStatus.ACTIVE);
        
        return getTransactionAttachmentCount() - 1;
    }
    
    public void copyFile(String fsPath){
        Path source = Paths.get(fsPath);
        Path targetDir = Paths.get(System.getProperty("sys.default.path.temp") + "/attachments");

        try {
            // Ensure target directory exists
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            // Copy file into the target directory
            Files.copy(source, targetDir.resolve(source.getFileName()),
                       StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File copied successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject searchTerm(String fsValue, boolean fbByCode) throws SQLException, GuanzonException {
        JSONObject loJSON;

        String lsSQL = "SELECT"
                + " sTermCode"
                + " , sDescript"
                + " FROM Term WHERE cRecdStat = '1'";

        if (fbByCode) {
            lsSQL = MiscUtil.addCondition(lsSQL, "sTermCode = " + SQLUtil.toSQL(fsValue));
        } else {
            lsSQL = MiscUtil.addCondition(lsSQL, "sDescript LIKE " + SQLUtil.toSQL(fsValue + "%"));
        }

        loJSON = ShowDialogFX.Search(
                poGRider,
                lsSQL,
                fsValue,
                "Code»Description",
                "sTermCode»sDescript",
                "sTermCode»sDescript",
                fbByCode ? 0 : 1);

        if (loJSON != null) {
            loJSON.put("result", "success");
            getModel().setTermId((String) loJSON.get("sTermCode"));
        } else {
            loJSON.put("result", "success");
            loJSON.put("message", "No record selected.");
        }

        return loJSON;
    }

    public JSONObject searchCategory(String fsValue, boolean fbByCode) throws SQLException, GuanzonException {
        JSONObject loJSON;

        String lsSQL = "SELECT"
                + " sCategrCd"
                + ", sDescript"
                + " FROM Category  WHERE cRecdStat = '1'";

        if (fbByCode) {
            lsSQL = MiscUtil.addCondition(lsSQL, "sCategrCd = " + SQLUtil.toSQL(fsValue));
        } else {
            lsSQL = MiscUtil.addCondition(lsSQL, "sDescript LIKE " + SQLUtil.toSQL(fsValue + "%"));
        }
        System.out.println("Category Query = " + lsSQL);
        loJSON = ShowDialogFX.Search(
                poGRider,
                lsSQL,
                fsValue,
                "Code»Description",
                "sCategrCd»sDescript",
                "sCategrCd»sDescript",
                fbByCode ? 0 : 1);

        if (loJSON != null) {
            loJSON.put("result", "success");
            getModel().setCategoryCode((String) loJSON.get("sCategrCd"));
        } else {
            loJSON.put("result", "success");
            loJSON.put("message", "No record selected.");
        }

        return loJSON;
    }

    public JSONObject searchClientContact(String fsValue, boolean fbByCode) throws SQLException, GuanzonException {
        JSONObject loJSON;

        String lsSQL = "SELECT"
                + " a.sContctID"
                + " ,a.sCPerson1"
                + " ,a.sMobileNo"
                + " , b.sClientID"
                + " , b.sCompnyNm"
                + " FROM Client_Institution_Contact_Person a "
                + " LEFT JOIN Client_Master b ON a.sClientID = b.sClientID"
                + " WHERE a.cRecdStat = " + SQLUtil.toSQL(RecordStatus.ACTIVE)
                + " AND b.cClientTp = " + SQLUtil.toSQL(Logical.NO);

        if (fbByCode) {
            lsSQL = MiscUtil.addCondition(lsSQL, "a.sContctID = " + SQLUtil.toSQL(fsValue));
        } else {
            lsSQL = MiscUtil.addCondition(lsSQL, "b.sCompnyNm LIKE " + SQLUtil.toSQL(fsValue + "%"));
        }
        System.out.println("Client Contact Search = " + lsSQL);
        loJSON = ShowDialogFX.Search(
                poGRider,
                lsSQL,
                fsValue,
                "ID»Contact Name»Contact Person»Mobile no",
                "sContctID»sCompnyNm»sCPerson1»sMobileNo",
                "a.sContctID»b.sCompnyNm»a.sCPerson1»a.sMobileNo",
                fbByCode ? 0 : 1);

        if (loJSON != null) {
            loJSON.put("result", "success");
            getModel().setContactId((String) loJSON.get("sContctID"));
        } else {
            loJSON.put("result", "success");
            loJSON.put("message", "No record selected.");
        }

        return loJSON;
    }

    public JSONObject loadLedgerList() throws SQLException, GuanzonException, CloneNotSupportedException {

        if (getModel().getClientId() == null
                || getModel().getClientId().isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("message", "No record Loaded. Please load Client");
            return poJSON;
        }
        paLedger.clear();
        String lsSQL = "SELECT "
                + " a.sClientID"
                + ", b.nLedgerNo"
                + ", b.dTransact"
                + " FROM AP_Client_Master a "
                + " LEFT JOIN AP_Client_Ledger b ON a.sClientID = b.sClientID "
                + " ORDER BY b.nLedgerNo";

        lsSQL = MiscUtil.addCondition(lsSQL, "a.sClientID=" + SQLUtil.toSQL(getModel().getClientId()));
        ResultSet loRS = poGRider.executeQuery(lsSQL);
        System.out.println("Load list query is " + lsSQL);

        if (MiscUtil.RecordCount(loRS)
                <= 0) {
            poJSON.put("result", "error");
            poJSON.put("message", "No record found.");
            return poJSON;
        }

        while (loRS.next()) {
            Model_AP_Client_Ledger loLedger = new ClientModels(poGRider).APClientLedger();
            poJSON = loLedger.openRecord(loRS.getString("sClientID"), loRS.getString("nLedgerNo"));

            if ("success".equals((String) poJSON.get("result"))) {
                paLedger.add(loLedger);
            } else {
                return poJSON;
            }
        }

        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject loadAttachments() throws SQLException, GuanzonException {
        
        poJSON = new JSONObject();
        paAttachments = new ArrayList<>();

        TransactionAttachment loAttachment = new SysTableContollers(poGRider, null).TransactionAttachment();
        List loList = loAttachment.getAttachments(SOURCE_CODE, poModel.getClientId());
        
        for (int lnCtr = 0; lnCtr <= loList.size() - 1; lnCtr++) {
            
            paAttachments.add(TransactionAttachment());
            poJSON = paAttachments.get(getTransactionAttachmentCount() - 1).openRecord((String) loList.get(lnCtr));
            
            if ("success".equals((String) poJSON.get("result"))) {
                if(poModel.getEditMode() == EditMode.UPDATE){
                   poJSON = paAttachments.get(getTransactionAttachmentCount() - 1).updateRecord();
                }
                System.out.println(paAttachments.get(getTransactionAttachmentCount() - 1).getModel().getTransactionNo());
                System.out.println(paAttachments.get(getTransactionAttachmentCount() - 1).getModel().getSourceNo());
                System.out.println(paAttachments.get(getTransactionAttachmentCount() - 1).getModel().getSourceCode());
                System.out.println(paAttachments.get(getTransactionAttachmentCount() - 1).getModel().getFileName());
            }
            
            //Download Attachments
            poJSON = WebFile.DownloadFile(WebFile.getAccessToken(System.getProperty("sys.default.access.token"))
                    , "0032" //Constant
                    , "" //Empty
                    , paAttachments.get(getTransactionAttachmentCount() - 1).getModel().getFileName()
                    , SOURCE_CODE
                    , paAttachments.get(getTransactionAttachmentCount() - 1).getModel().getSourceNo()
                    , "");
            
            if ("success".equals((String) poJSON.get("result"))) {
                
                poJSON = (JSONObject) poJSON.get("payload");
                if(WebFile.Base64ToFile((String) poJSON.get("data")
                        , (String) poJSON.get("hash")
                        , System.getProperty("sys.default.path.temp.attachments") + "/"
                        , (String) poJSON.get("filename"))){
                    
                    System.out.println("poJSON success: " +  poJSON.toJSONString());
                    System.out.println("File downloaded succesfully.");
                } else {
                    
                    poJSON = (JSONObject) poJSON.get("error");
                    poJSON.put("result", "error");
                    
                    System.out.println("ERROR WebFile.DownloadFile: " + poJSON.get("message"));
                    System.out.println("poJSON error WebFile.DownloadFile: " + poJSON.toJSONString());
                }
                
            } else {
                System.out.println("poJSON error WebFile.DownloadFile: " + poJSON.toJSONString());
            }
        }
        return poJSON;
    }
}
