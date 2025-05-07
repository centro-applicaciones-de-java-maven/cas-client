package org.guanzon.cas.client;

import java.sql.SQLException;
import java.util.ArrayList;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.ClientType;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.UserRight;
import org.guanzon.cas.client.model.Model_Client_Address;
import org.guanzon.cas.client.model.Model_Client_Institution_Contact;
import org.guanzon.cas.client.model.Model_Client_Mail;
import org.guanzon.cas.client.model.Model_Client_Master;
import org.guanzon.cas.client.model.Model_Client_Mobile;
import org.guanzon.cas.client.model.Model_Client_Social_Media;
import org.guanzon.cas.client.services.ClientModels;
import org.guanzon.cas.parameter.Country;
import org.guanzon.cas.parameter.TownCity;
import org.guanzon.cas.parameter.services.ParamControllers;
import org.json.simple.JSONObject;

public class ClientInfo extends Parameter{
    Model_Client_Master poClient;
    Model_Client_Mobile poMobile;
    Model_Client_Address poAddress;
    Model_Client_Mail poMail;
    Model_Client_Social_Media poSocMed;
    Model_Client_Institution_Contact poContact;
    
    ArrayList<Model_Client_Mobile> paMobile;
    ArrayList<Model_Client_Address> paAddress;
    ArrayList<Model_Client_Mail> paMail;
    ArrayList<Model_Client_Social_Media> paSocMed;
    ArrayList<Model_Client_Institution_Contact> paContact;
    
    String psClientTp;
    
    @Override
    public void initialize() throws SQLException, GuanzonException{
        super.initialize();
        
        psRecdStat = Logical.YES;

        ClientModels model = new ClientModels(poGRider);
        poClient = model.ClientMaster();
        poMobile = model.ClientMobile();               
        poMail = model.ClientMail();
        poAddress = model.ClientAddress();
        poSocMed = model.ClientSocMed();
        poContact = model.ClientInstitutionContact();
        
        psClientTp = ClientType.INDIVIDUAL;
    }
        
    @Override
    public Model_Client_Master getModel() {
        return poClient;
    }
    
    public void setClientType(String clientType){
        psClientTp = clientType;
    }

    public Model_Client_Mobile Mobile(int row){
        if (row > paMobile.size() -1) return null;
        
        return paMobile.get(row);
    }
    
    public Model_Client_Address Address(int row){
        if (row > paAddress.size() -1) return null;
        
        return paAddress.get(row);
    }
    
    public Model_Client_Mail Mail(int row){
        if (row > paMail.size() -1) return null;
        
        return paMail.get(row);
    }
    
    public Model_Client_Social_Media SocMed(int row){
        if (row > paSocMed.size() -1) return null;
        
        return paSocMed.get(row);
    }
    
    public Model_Client_Institution_Contact InstiContact(int row){
        if (row > paContact.size() -1) return null;
        
        return paContact.get(row);
    }
        
    public int getMobileCount(){
        return paMobile.size();
    }
    
    public int getAddressCount(){
        return paAddress.size();
    }
    
    public int getMailCount(){
        return paMail.size();
    }
    
    public int getSocMedCount(){
        return paSocMed.size();
    }
    
    public int getInstiContactCount(){
        return paContact.size();
    }
    
    public JSONObject addMobile() throws CloneNotSupportedException{
        poJSON = new JSONObject();
        
        if (!pbInitRec){
            poJSON.put("result", "error");
            poJSON.put("message", "Object is not initialized.");
            return poJSON;
        }
        
        if (getEditMode() != EditMode.ADDNEW && getEditMode() != EditMode.UPDATE){
            poJSON.put("result", "error");
            poJSON.put("message", "Invalid edit mode. Unable to add mobile.");
            return poJSON;
        }
         
        if (paMobile.size() > 1){
            if (paMobile.get(getMobileCount() - 1).getMobileNo().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add new mobile record.\n\nLast record is still empty.");
                return poJSON;
            }
        }
        
        Model_Client_Mobile object = (Model_Client_Mobile) poMobile.clone();
        object.newRecord();

        paMobile.add(object);
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject addAddress() throws CloneNotSupportedException{
        poJSON = new JSONObject();
        
        if (!pbInitRec){
            poJSON.put("result", "error");
            poJSON.put("message", "Object is not initialized.");
            return poJSON;
        }
        
        if (getEditMode() != EditMode.ADDNEW && getEditMode() != EditMode.UPDATE){
            poJSON.put("result", "error");
            poJSON.put("message", "Invalid edit mode. Unable to add mobile.");
            return poJSON;
        }
         
        if (paAddress.size() > 1){
            if (paAddress.get(getAddressCount() - 1).getAddress().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add new address record.\n\nLast record's address is still empty.");
                return poJSON;
            }
            
            if (paAddress.get(getAddressCount() - 1).getTownId().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add new address record.\n\nLast record's town is still empty.");
                return poJSON;
            }
        }
        
        Model_Client_Address object = (Model_Client_Address) poAddress.clone();
        object.newRecord();

        paAddress.add(object);
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject addMail() throws CloneNotSupportedException{
        poJSON = new JSONObject();
        
        if (!pbInitRec){
            poJSON.put("result", "error");
            poJSON.put("message", "Object is not initialized.");
            return poJSON;
        }
        
        if (getEditMode() != EditMode.ADDNEW && getEditMode() != EditMode.UPDATE){
            poJSON.put("result", "error");
            poJSON.put("message", "Invalid edit mode. Unable to add mobile.");
            return poJSON;
        }
         
        if (paMail.size() > 1){
            if (paMail.get(getMobileCount() - 1).getMailAddress().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add new email record.\n\nLast record is still empty.");
                return poJSON;
            }
        }
        
        Model_Client_Mail object = (Model_Client_Mail) poMail.clone();
        object.newRecord();

        paMail.add(object);
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject addSocMed() throws CloneNotSupportedException{
        poJSON = new JSONObject();
        
        if (!pbInitRec){
            poJSON.put("result", "error");
            poJSON.put("message", "Object is not initialized.");
            return poJSON;
        }
        
        if (getEditMode() != EditMode.ADDNEW && getEditMode() != EditMode.UPDATE){
            poJSON.put("result", "error");
            poJSON.put("message", "Invalid edit mode. Unable to add mobile.");
            return poJSON;
        }
         
        if (paSocMed.size() > 1){
            if (paSocMed.get(getMobileCount() - 1).getAccount().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add new social media record.\n\nLast record is still empty.");
                return poJSON;
            }
        }
        
        Model_Client_Social_Media object = (Model_Client_Social_Media) poSocMed.clone();
        object.newRecord();

        paSocMed.add(object);
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject addInstiContact() throws CloneNotSupportedException{
        poJSON = new JSONObject();
        
        if (!pbInitRec){
            poJSON.put("result", "error");
            poJSON.put("message", "Object is not initialized.");
            return poJSON;
        }
        
        if (getEditMode() != EditMode.ADDNEW && getEditMode() != EditMode.UPDATE){
            poJSON.put("result", "error");
            poJSON.put("message", "Invalid edit mode. Unable to add mobile.");
            return poJSON;
        }
         
        if (paContact.size() > 1){
            if (paContact.get(getInstiContactCount()- 1).getContactPersonName().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add new contact person record.\n\nLast record's name is still empty.");
                return poJSON;
            }
            
            if (paContact.get(getInstiContactCount()- 1).getMobileNo().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add new contact person record.\n\nLast record's mobile number is still empty.");
                return poJSON;
            }
        }
        
        Model_Client_Institution_Contact object = (Model_Client_Institution_Contact) poContact.clone();
        object.newRecord();

        paContact.add(object);
        
        poJSON.put("result", "success");
        return poJSON;
    }

    public JSONObject searchBirthPlace(String lsValue) throws SQLException, GuanzonException{
        TownCity loParam = new ParamControllers(poGRider, logwrapr).TownCity();
        
        poJSON = loParam.searchRecord(lsValue, false);
        
        if ("success".equals((String) poJSON.get("result"))){
            poClient.setBirthPlaceId(loParam.getModel().getTownId());
        }
        
        return poJSON;
    }
    
    public JSONObject searchCitizenship(String lsValue) throws SQLException, GuanzonException{
        Country loParam = new ParamControllers(poGRider, logwrapr).Country();
        
        poJSON = loParam.searchRecord(lsValue, false);
        
        if ("success".equals((String) poJSON.get("result"))){
            poClient.setCitizenshipId(loParam.getModel().getCountryId());
        }
        
        return poJSON;
    }
    
    @Override
    public JSONObject isEntryOkay() throws SQLException, GuanzonException, CloneNotSupportedException{        
        if (poGRider.getUserLevel() < UserRight.SYSADMIN){
            poJSON.put("result", "error");
            poJSON.put("message", "User is not allowed to save record.");
            return poJSON;
        } else {
            poJSON = new JSONObject();
            
            //validate master                        
            if (getEditMode() == EditMode.ADDNEW){
                poJSON = poClient.setClientType(psClientTp);
                
                if (poClient.getClientType().equals(ClientType.INDIVIDUAL)){
                    if (poClient.getLastName().isEmpty()){
                        poJSON.put("result", "error");
                        poJSON.put("message", "Last name must not be empty.");
                        return poJSON;
                    }

                    if (poClient.getFirstName().isEmpty()){
                        poJSON.put("result", "error");
                        poJSON.put("message", "First name must not be empty.");
                        return poJSON;
                    }
                    
                    String lsName = poClient.getLastName() + ", " + poClient.getFirstName() + " " + poClient.getSuffixName() + " " + poClient.getMiddleName();
                    lsName = lsName.trim();
                    
                    poClient.setCompanyName(lsName);
                } else {
                    poClient.setLastName("");
                    poClient.setFirstName("");
                    poClient.setMiddleName("");
                    poClient.setSuffixName("");
                    
                    if (poClient.getCompanyName().isEmpty()){
                        poJSON.put("result", "error");
                        poJSON.put("message", "Company name must not be empty.");
                        return poJSON;
                    }
                }

                if (!"success".equals((String) poJSON.get("result"))) return poJSON;
                
                poClient.setModifyingId(poGRider.Encrypt(poGRider.getUserID()));
                poClient.setModifiedDate(poGRider.getServerDate());
            }
            
//            //validate mobile
//            if (paMobile.get(paMobile.size() - 1).getMobileNo().isEmpty()) paMobile.remove(paMobile.size() - 1);
//            
//            switch (paMobile.size()) {
//                case 0:
//                    addMobile();
//                case 1:
//                    if (paMobile.get(paMobile.size() - 1).getMobileNo().isEmpty()){
//                    poJSON.put("result", "error");
//                    poJSON.put("message", "Client must have a mobile number.");
//                    return poJSON;
//                }
//            }
//            
//            //validate address
//            if (paAddress.get(paAddress.size() - 1).getAddress().isEmpty() &&
//                    paAddress.get(paAddress.size() - 1).getTownId().isEmpty()) paAddress.remove(paAddress.size() - 1);
//            
//            switch (paAddress.size()) {
//                case 0:
//                    addAddress();
//                case 1:
//                    if (paAddress.get(paAddress.size() - 1).getAddress().isEmpty() &&
//                        paAddress.get(paAddress.size() - 1).getTownId().isEmpty()){
//                    poJSON.put("result", "error");
//                    poJSON.put("message", "Address and town must have a value.");
//                    return poJSON;
//                }
//            }
//            
//            //validate contact person
//            if (getModel().getClientType().equals(ClientType.INSTITUTION)){
//                if (paContact.get(paContact.size() - 1).getContactPersonName().isEmpty() &&
//                        paContact.get(paContact.size() - 1).getMobileNo().isEmpty()) paContact.remove(paContact.size() - 1);
//
//                switch (paContact.size()) {
//                    case 0:
//                        addInstiContact();
//                    case 1:
//                        if (paContact.get(paContact.size() - 1).getContactPersonName().isEmpty() &&
//                            paContact.get(paContact.size() - 1).getMobileNo().isEmpty()){
//                        poJSON.put("result", "error");
//                        poJSON.put("message", "Contact person name and mobile number must have a value.");
//                        return poJSON;
//                    }
//                }
//            }
        }
        
        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }
    
    @Override
    protected JSONObject initFields() throws SQLException, GuanzonException{        
        poMobile.initialize();
        poMobile.newRecord();
        
        poAddress.initialize();
        poAddress.newRecord();
        
        poMail.initialize();
        poMail.newRecord();
        
        poSocMed.initialize();
        poSocMed.newRecord();
        
        poContact.initialize();
        poContact.newRecord();
        
        paMobile = new ArrayList<>();
        paMobile.add(poMobile);
        
        paAddress = new ArrayList<>();
        paAddress.add(poAddress);
        
        paMail = new ArrayList<>();
        paMail.add(poMail);
        
        paSocMed = new ArrayList<>();
        paSocMed.add(poSocMed);
        
        paContact = new ArrayList<>();
        paContact.add(poContact);
        
        poJSON = new JSONObject();
        poJSON.put("result", "success");
        
        return poJSON;
    }
    
    @Override
    protected JSONObject willSave() throws SQLException, GuanzonException{        
        if (getEditMode() == EditMode.ADDNEW){
            //assign master client id
            poClient.setClientId(MiscUtil.getNextCode(poClient.getTable(), "sClientID", true, poGRider.getGConnection().getConnection(), poGRider.getBranchCode()));
            
            //assign client ids to details
            int lnCtr;

            Model_Client_Mobile loMobile;
            for (lnCtr = 0; lnCtr <= paMobile.size() - 1; lnCtr++){
                loMobile = paMobile.get(lnCtr);
                loMobile.setClientId(poClient.getClientId());
                loMobile.setMobileNetwork(CommonUtils.classifyNetwork(loMobile.getMobileNo()));
                
                if (paMobile.size() == 1) loMobile.isPrimaryMobile(true);
                if (loMobile.getEditMode() == EditMode.ADDNEW) loMobile.setModifiedDate(poGRider.getServerDate());
            }
            
            Model_Client_Address loAddress;
            for (lnCtr = 0; lnCtr <= paAddress.size() - 1; lnCtr++){
                loAddress = paAddress.get(lnCtr);
                
                if (loAddress.getAddress().isEmpty() && loAddress.getTownId().isEmpty()) {
                    paMail.remove(lnCtr);
                    break;
                }
                
                loAddress.setClientId(poClient.getClientId());
                if (paAddress.size() == 1) loAddress.isPrimaryAddress(true);
                if (loAddress.getEditMode() == EditMode.ADDNEW) loAddress.setModifiedDate(poGRider.getServerDate());
            }
            
            Model_Client_Mail loMail;
            for (lnCtr = 0; lnCtr <= paMail.size() - 1; lnCtr++){
                loMail = paMail.get(lnCtr);
                
                if (loMail.getMailAddress().isEmpty()) {
                    paMail.remove(lnCtr);
                    break;
                }
                
                loMail.setClientId(poClient.getClientId());
                if (paMail.size() == 1) loMail.isPrimaryEmail(true);
                if (loMail.getEditMode() == EditMode.ADDNEW) loMail.setModifiedDate(poGRider.getServerDate());
            }
            
            Model_Client_Social_Media loSocMed;
            for (lnCtr = 0; lnCtr <= paSocMed.size() - 1; lnCtr++){
                loSocMed = paSocMed.get(lnCtr);
                
                if (loSocMed.getAccount().isEmpty()) {
                    paSocMed.remove(lnCtr);
                    break;
                }
                
                loSocMed.setClientId(poClient.getClientId());                
                if (loSocMed.getEditMode() == EditMode.ADDNEW) loSocMed.setModifiedDate(poGRider.getServerDate());
            }
            
            if (getModel().getClientType().equals(ClientType.INSTITUTION)){
                Model_Client_Institution_Contact loContact;
                for (lnCtr = 0; lnCtr <= paContact.size() - 1; lnCtr++){
                    loContact = paContact.get(lnCtr);

                    if (loContact.getContactPersonName().isEmpty()) {
                        paSocMed.remove(lnCtr);
                        break;
                    }

                    loContact.setClientId(poClient.getClientId());
                    if (paContact.size() == 1) loContact.isPrimaryContactPersion(true);
                    if (loContact.getEditMode() == EditMode.ADDNEW) loContact.setModifiedDate(poGRider.getServerDate());
                }
            }
        }
        
        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }
    
    @Override
    protected JSONObject saveOthers() throws SQLException, GuanzonException{     
        Model_Client_Mobile loMobile;
        Model_Client_Address loAddress;
        Model_Client_Mail loMail;
        Model_Client_Social_Media loSocMed;
        Model_Client_Institution_Contact loContact;
        
        int lnCtr;
        
        if (!paMobile.isEmpty()){
            for (lnCtr = 0; lnCtr <= paMobile.size() - 1; lnCtr++){
                loMobile = paMobile.get(lnCtr);

                if (loMobile.getEditMode() == EditMode.ADDNEW){
                    poJSON = loMobile.saveRecord();
                    if (!"success".equals((String) poJSON.get("result"))) return poJSON;
                } else {
                    //validate if record is modified
                }
            }
        }
        
        if (!paAddress.isEmpty()){
            for (lnCtr = 0; lnCtr <= paAddress.size() - 1; lnCtr++){
                loAddress = paAddress.get(lnCtr);

                if (loAddress.getEditMode() == EditMode.ADDNEW){
                    poJSON = loAddress.saveRecord();
                    if (!"success".equals((String) poJSON.get("result"))) return poJSON;
                } else {
                    //validate if record is modified
                }
            }
        }
        
        if (!paMail.isEmpty()){
            for (lnCtr = 0; lnCtr <= paMail.size() - 1; lnCtr++){
                loMail = paMail.get(lnCtr);

                if (loMail.getEditMode() == EditMode.ADDNEW){
                    poJSON = loMail.saveRecord();
                    if (!"success".equals((String) poJSON.get("result"))) return poJSON;
                } else {
                    //validate if record is modified
                }
            }
        }
        
        if (!paSocMed.isEmpty()){
            for (lnCtr = 0; lnCtr <= paSocMed.size() - 1; lnCtr++){
                loSocMed = paSocMed.get(lnCtr);

                if (loSocMed.getEditMode() == EditMode.ADDNEW){
                    poJSON = loSocMed.saveRecord();
                    if (!"success".equals((String) poJSON.get("result"))) return poJSON;
                } else {
                    //validate if record is modified
                }
            }
        }
        
        if (getModel().getClientType().equals(ClientType.INSTITUTION)){
            if (!paContact.isEmpty()){
                for (lnCtr = 0; lnCtr <= paContact.size() - 1; lnCtr++){
                    loContact = paContact.get(lnCtr);

                    if (loContact.getEditMode() == EditMode.ADDNEW){
                        poJSON = loContact.saveRecord();
                        if (!"success".equals((String) poJSON.get("result"))) return poJSON;
                    } else {
                        //validate if record is modified
                    }
                }
            }
        }
        
        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }
    
    @Override
    public JSONObject searchRecord(String value, boolean byCode) throws SQLException, GuanzonException{
        String lsSQL = getSQ_Browse();
        
        poJSON = ShowDialogFX.Search(poGRider,
                lsSQL,
                value,
                "Birthday»Name»Mobile»Email",
                "dBirthDte»xFullName»xMobileNo»xEMailAdd",
                "a.dBirthDte»TRIM(CONCAT(a.sLastName, ', ', a.sFrstName, IF(a.sSuffixNm <> '', CONCAT(' ', a.sSuffixNm, ''), ''), ' ', a.sMiddName))»IFNULL(c.sMobileNo, '')»IFNULL(d.sEMailAdd, '')",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poClient.openRecord((String) poJSON.get("sClientID"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    @Override
    public String getSQ_Browse(){
        String lsCondition = "";

        if (psRecdStat.length() > 1) {
            for (int lnCtr = 0; lnCtr <= psRecdStat.length() - 1; lnCtr++) {
                lsCondition += ", " + SQLUtil.toSQL(Character.toString(psRecdStat.charAt(lnCtr)));
            }

            lsCondition = "a.cRecdStat IN (" + lsCondition.substring(2) + ")";
        } else {
            lsCondition = "a.cRecdStat = " + SQLUtil.toSQL(psRecdStat);
        }               
        
        String lsSQL = "SELECT" +
                            "  a.sClientID" +
                            ", a.cClientTp" +
                            ", a.sLastName" +
                            ", a.sFrstName" +
                            ", a.sMiddName" +
                            ", a.sSuffixNm" +
                            ", a.sMaidenNm" +
                            ", a.sCompnyNm" +
                            ", a.cGenderCd" +
                            ", a.cCvilStat" +
                            ", a.sCitizenx" +
                            ", a.dBirthDte" +
                            ", a.sBirthPlc" +
                            ", a.sAddlInfo" +
                            ", a.sSpouseID" +
                            ", a.sTaxIDNox" +
                            ", a.sLTOIDxxx" +
                            ", a.sPHBNIDxx" +
                            ", a.cLRClient" +
                            ", a.cMCClient" +
                            ", a.cSCClient" +
                            ", a.cSPClient" +
                            ", a.cCPClient" +
                            ", a.cRecdStat" +
                            ", a.sModified" +
                            ", a.dModified" +
                            ", TRIM(CONCAT(a.sLastName, ', ', a.sFrstName, IF(a.sSuffixNm <> '', CONCAT(' ', a.sSuffixNm, ''), ''), ' ', a.sMiddName)) xFullName " +
                            ", IFNULL(c.sMobileNo, '') xMobileNo" +
                            ", IFNULL(d.sEMailAdd, '') xEMailAdd" +
                        " FROM Client_Master a" +
                            " LEFT JOIN Client_Mobile c ON a.sClientID = c.sClientID" +
                                " AND c.cPrimaryx = '1'" +
                            " LEFT JOIN Client_eMail_Address d ON a.sClientID = d.sClientID" +
                                " AND d.cPrimaryx = '1'";
        
        lsSQL = MiscUtil.addCondition(lsSQL, lsCondition);
        
        if (psClientTp.length() > 1) 
            psClientTp = ClientType.INDIVIDUAL;
        
        lsCondition = "a.cClientTp = " + SQLUtil.toSQL(psClientTp);
        
        return MiscUtil.addCondition(lsSQL, lsCondition);
    }
}