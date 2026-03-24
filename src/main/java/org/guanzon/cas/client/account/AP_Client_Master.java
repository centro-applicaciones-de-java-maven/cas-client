package org.guanzon.cas.client.account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.agent.systables.SysTableContollers;
import org.guanzon.appdriver.agent.systables.TransactionAttachment;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscReplUtil;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.base.WebFile;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.appdriver.iface.GValidator;
import org.guanzon.appdriver.token.RequestAccess;
import org.guanzon.cas.client.model.Model_AP_Client_Ledger;
import org.guanzon.cas.client.model.Model_AP_Client_Master;
import org.guanzon.cas.client.services.ClientModels;
import org.guanzon.cas.client.validator.APClientValidatorFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AP_Client_Master extends Parameter {
    
    private final String SOURCE_CODE = "APCL";
    
    private static JSONObject token = null;

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
    protected JSONObject willSave() throws SQLException, GuanzonException {

        try {
            
            for (int lnCtr = 0; lnCtr <= getTransactionAttachmentCount()- 1; lnCtr++) {
                
                //assign other info on attachment
                getAttachmentList().get(lnCtr).getModel().setSourceNo(poModel.getClientId());
                getAttachmentList().get(lnCtr).getModel().setSourceCode(SOURCE_CODE);
                getAttachmentList().get(lnCtr).getModel().setBranchCode(poGRider.getBranchCode());
                getAttachmentList().get(lnCtr).getModel().setImagePath(System.getProperty("sys.default.path.temp.attachments"));

                //store original filename
                String lsOriginalFileName = getAttachmentList().get(lnCtr).getModel().getFileName();
                
                //Check record existence on database
                if(EditMode.ADDNEW == getAttachmentList().get(lnCtr).getModel().getEditMode()){
                    int lnCopies = 0;
                    
                    //concatenate image path with filename
                    String fsFilePath = getAttachmentList().get(lnCtr).getModel().getImagePath() + "/" + getAttachmentList().get(lnCtr).getModel().getFileName();
                    String lsNewFileName = getAttachmentList().get(lnCtr).getModel().getFileName();
                    
                    //check file existence on server, rename file as copied to identify duplicate files
                    while ("error".equals((String) checkExistingFileName(lsNewFileName).get("result"))) {
                        lnCopies++;
                        
                        //Rename the file
                        int dotIndex = getAttachmentList().get(lnCtr).getModel().getFileName().lastIndexOf(".");
                        if (dotIndex == -1) {
                            lsNewFileName = getAttachmentList().get(lnCtr).getModel().getFileName() +"_"+lnCopies;
                        } else {
                            lsNewFileName = getAttachmentList().get(lnCtr).getModel().getFileName().substring(0, dotIndex) +"_"+ lnCopies +getAttachmentList().get(lnCtr).getModel().getFileName().substring(dotIndex);
                        }
                    }

                    //copy duplicate filenames
                    if(lnCopies > 0){
                        Path source = Paths.get(fsFilePath);
                        
                        try {
                            // Copy file into the target directory with a new name
                            Path target = Paths.get(System.getProperty("sys.default.path.temp") + "/attachments").resolve(lsNewFileName);
                            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                            
                            //check if file is existing
                            int lnChecker = 0;
                            
                            //copy to target directory
                            File file = new File(getAttachmentList().get(lnCtr).getModel().getImagePath() + "/" + lsNewFileName);
                            while(!file.exists() && lnChecker < 5){
                                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);  
                                System.out.println("Re-Copying... " + lnChecker);
                                lnChecker++;
                            }
                            
                            //rename file with new filename
                            getAttachmentList().get(lnCtr).getModel().setFileName(lsNewFileName);
                            System.out.println("File copied successfully as " + lsNewFileName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                //upload unsent file attachments
                if("0".equals(getAttachmentList().get(lnCtr).getModel().getSendStatus())){
                    
                    //upload to file server
                    poJSON = uploadCASAttachments(poGRider, System.getProperty("sys.default.access.token"), lnCtr,lsOriginalFileName);
                    if ("error".equals((String) poJSON.get("result"))) {
                        return poJSON;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AP_Client_Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }

    @Override
    protected JSONObject saveOthers() throws SQLException, GuanzonException {
        try {
            
            //Save Attachments
            for (int lnCtr = 0; lnCtr <= getTransactionAttachmentCount() - 1; lnCtr++) {
                
                if (paAttachments.get(lnCtr).getEditMode() == EditMode.ADDNEW || paAttachments.get(lnCtr).getEditMode() == EditMode.UPDATE) {
                    
                    paAttachments.get(lnCtr).getModel().setModifyingId(poGRider.Encrypt(poGRider.getUserID()));
                    paAttachments.get(lnCtr).getModel().setModifiedDate(poGRider.getServerDate());
                    paAttachments.get(lnCtr).setWithParentClass(true);
                    
                    poJSON = paAttachments.get(lnCtr).saveRecord();
                    
                    if ("error".equals((String) poJSON.get("result"))) {
                        return poJSON;
                    }
                }
            }
            
        } catch (SQLException | GuanzonException | CloneNotSupportedException  ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MiscUtil.getException(ex), ex);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MiscUtil.getException(ex), ex);
        }
        
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
    
    public JSONObject removeAttachment(int fnRow) throws GuanzonException, SQLException{
        poJSON = new JSONObject();
        
        if(getTransactionAttachmentCount() <= 0){
            poJSON.put("result", "error");
            poJSON.put("message", "No transaction attachment to be removed.");
            return poJSON;
        }
        
        if(paAttachments.get(fnRow).getEditMode() == EditMode.ADDNEW){
            paAttachments.remove(fnRow);
            System.out.println("Attachment :"+ fnRow+" Removed");
        } else {
            paAttachments.get(fnRow).getModel().setRecordStatus(RecordStatus.INACTIVE);
            System.out.println("Attachment :"+ fnRow+" Deactivate");
        }
        
        poJSON.put("result", "success");
        return poJSON;
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
    
    public JSONObject checkExistingFileName(String fsFileName) throws SQLException, GuanzonException{
        poJSON = new JSONObject();
        
        String lsSQL = MiscUtil.addCondition(MiscUtil.makeSelect(TransactionAttachment().getModel()), 
                                                                    " sFileName = " + SQLUtil.toSQL(fsFileName)
                                                                    );
        System.out.println("Executing SQL: " + lsSQL);
        ResultSet loRS = poGRider.executeQuery(lsSQL);
        try {
            if (MiscUtil.RecordCount(loRS) > 0) {
                if(loRS.next()){
                    if(loRS.getString("sFileName") != null && !"".equals(loRS.getString("sFileName"))){
                        poJSON.put("result", "error");
                        poJSON.put("message", "File name already exist in database.\nTry changing the file name to upload.");
                    }
                }
            }
            MiscUtil.close(loRS);
        } catch (SQLException e) {
            System.out.println("No record loaded.");
        }
        return poJSON;
    }
    
    public JSONObject uploadCASAttachments(GRiderCAS instance, String access, int fnRow, String fsOriginalFileName) throws Exception{       
        
        poJSON = new JSONObject();
        System.out.println("Uploading... : fsOriginalFileName : " + fsOriginalFileName);
        System.out.println("New File Name... : " + paAttachments.get(fnRow).getModel().getFileName());
        
        String hash;
        String lsFile = paAttachments.get(fnRow).getModel().getFileName();
        
        //check if new file is existing
        File file = new File(paAttachments.get(fnRow).getModel().getImagePath() + "/" + lsFile);
        if(!file.exists()){
            //check if original file is existing
            lsFile = fsOriginalFileName;
            file = new File(paAttachments.get(fnRow).getModel().getImagePath() + "/" + lsFile);
            if(!file.exists()){
                poJSON.put("result", "error");
                poJSON.put("message", "Cannot locate file in " + paAttachments.get(fnRow).getModel().getImagePath() + "/" + lsFile
                                        + ".\nContact system administrator for assistance.");
                return poJSON;  
            }
        }

        //check if file hash is not empty
        hash = paAttachments.get(fnRow).getModel().getMD5Hash();
        if(paAttachments.get(fnRow).getModel().getMD5Hash() == null || "".equals(paAttachments.get(fnRow).getModel().getMD5Hash())){
            hash = MiscReplUtil.md5Hash(paAttachments.get(fnRow).getModel().getImagePath() + "/" + lsFile);
        }

        JSONObject result = WebFile.UploadFile(getAccessToken(access)
                                , "0032"
                                , ""
                                , paAttachments.get(fnRow).getModel().getFileName()
                                , instance.getBranchCode()
                                , hash
                                , encodeFileToBase64Binary(file)
                                , paAttachments.get(fnRow).getModel().getSourceCode()
                                , paAttachments.get(fnRow).getModel().getSourceNo()
                                , "");

        if("error".equalsIgnoreCase((String) result.get("result"))){
            System.out.println("Upload Error : " + result.toJSONString());
            System.out.println("Upload Error : " + paAttachments.get(fnRow).getModel().getFileName());
            poJSON.put("result", "error");
            poJSON.put("message", "System error while uploading file "+ paAttachments.get(fnRow).getModel().getFileName()
                                    + ".\nContact system administrator for assistance.");
            return poJSON;
        }
        paAttachments.get(fnRow).getModel().setMD5Hash(hash);
        paAttachments.get(fnRow).getModel().setSendStatus("1");
        System.out.println("Upload Success : " + paAttachments.get(fnRow).getModel().getFileName());
        poJSON.put("result", "success");
        return poJSON;
    }
    
    private static String getAccessToken(String access){
        try {
            JSONParser oParser = new JSONParser();
            if(token == null){
                token = (JSONObject)oParser.parse(new FileReader(access));
            }
            
            Calendar current_date = Calendar.getInstance();
            current_date.add(Calendar.MINUTE, -25);
            Calendar date_created = Calendar.getInstance();
            date_created.setTime(SQLUtil.toDate((String) token.get("created") , SQLUtil.FORMAT_TIMESTAMP));
            
            //Check if token is still valid within the time frame
            //Request new access token if not in the current period range
            if(current_date.after(date_created)){
                String[] xargs = new String[] {(String) token.get("parent"), access};
                RequestAccess.main(xargs);
                token = (JSONObject)oParser.parse(new FileReader(access));
            }
            
            return (String)token.get("access_key");
        } catch (IOException ex) {
            return null;
        } catch (ParseException ex) {
            return null;
        }
    }
    
    private static String encodeFileToBase64Binary(File file) throws Exception{
         FileInputStream fileInputStreamReader = new FileInputStream(file);
         byte[] bytes = new byte[(int)file.length()];
         fileInputStreamReader.read(bytes);
         return new String(Base64.encodeBase64(bytes), "UTF-8");
     }    
}
