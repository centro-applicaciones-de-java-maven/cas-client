package org.guanzon.cas.client;

import java.util.ArrayList;
import java.util.List;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.constant.EditMode;
import org.json.simple.JSONObject;

public class Client {    
    GRider poGRider;
    String psParent;
    
    JSONObject poJSON;
    LogWrapper poLogWrapper;
    
    Client_Master poClient;
    List<Client_Mobile> poMobile;
    List<Client_Address> poAddress;
    List<Client_Mail> poMail;
    List<Client_Social_Media> poSocMed;
    List<Client_Institution_Contact> poInsContact;
    
    public Client(GRider applicationDriver,
                    String parentClass,
                    LogWrapper logWrapper){
        
        poGRider = applicationDriver;
        psParent = parentClass;
        poLogWrapper = logWrapper;
        
        poClient = new Client_Master();
        poClient.setApplicationDriver(poGRider);
        poClient.setWithParentClass(true);
        poClient.setLogWrapper(poLogWrapper);
        poClient.initialize();
        
        poMobile = new ArrayList<>();
        poAddress = new ArrayList<>();
        poMail = new ArrayList<>();
        poSocMed = new ArrayList<>();
        poInsContact = new ArrayList<>();
    }
        
    public Client_Master Master(){
        return poClient;
    }
    
    public Client_Mobile Mobile(int row){
        return poMobile.get(row);
    }
    
    public Client_Address Address(int row){
        return poAddress.get(row);
    }
    
    public Client_Mail Mail(int row){
        return poMail.get(row);
    }
    
    public Client_Social_Media SocialMedia(int row){
        return poSocMed.get(row);
    }
    
    public Client_Institution_Contact InstitutionContactPerson(int row){
        return poInsContact.get(row);
    }
    
    public int getMobileCount(){
        return poMobile.size();
    }    
    
    public int getAddressCount(){
        return poAddress.size();
    }
    
    public int getMailCount(){
        return poMail.size();
    }
    
    public int getSocMedCount(){
        return poSocMed.size();
    }
    
    public int getInstitutionContactPCount(){
        return poSocMed.size();
    }
    
    public JSONObject addMobile(){
        poJSON = new JSONObject();
        
        if (poMobile.isEmpty()){
            poMobile.add(mobile());            
        } else {
            if (!poMobile.get(poMobile.size()-1).getModel().getMobileNo().isEmpty()){
                poMobile.add(mobile());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add mobile. Last row mobile number is still empty.");
                return poJSON;
            }
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject addAddress(){
        poJSON = new JSONObject();
        
        if (poAddress.isEmpty()){
            poAddress.add(address());            
        } else {
            if (!poAddress.get(poAddress.size()-1).getModel().getAddress().isEmpty()){
                poAddress.add(address());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add mobile. Last row address is still empty.");
                return poJSON;
            }
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject addMail(){
        poJSON = new JSONObject();
        
        if (poMail.isEmpty()){
            poMail.add(email());            
        } else {
            if (!poMail.get(poMail.size()-1).getModel().getMailAddress().isEmpty()){
                poMail.add(email());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add mobile. Last row email address is still empty.");
                return poJSON;
            }
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject addSocialMedia(){
        poJSON = new JSONObject();
        
        if (poSocMed.isEmpty()){
            poSocMed.add(socmed());            
        } else {
            if (!poSocMed.get(poSocMed.size()-1).getModel().getAccount().isEmpty()){
                poSocMed.add(socmed());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add mobile. Last row social media account is still empty.");
                return poJSON;
            }
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject addInsContactPerson(){
        poJSON = new JSONObject();
        
        if (poInsContact.isEmpty()){
            poInsContact.add(insCPerson());            
        } else {
            if (!poInsContact.get(poInsContact.size()-1).getModel().getContactPersonName().isEmpty()){
                poInsContact.add(insCPerson());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add mobile. Last row social media account is still empty.");
                return poJSON;
            }
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject deleteMobile(int row){
        poJSON = new JSONObject();
        
        if (poMobile.isEmpty()){
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete mobile. Mobile list is empty.");
            return poJSON;
        }
        
        if (row >= poMobile.size()){
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete mobile. Row is more than the mobile list.");
            return poJSON;
        }
        
        if (poMobile.get(row).getEditMode() != EditMode.ADDNEW){
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete old mobile. You can deactivate the record instead.");
            return poJSON;
        }
        
        poMobile.remove(row);
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject New(){
        poJSON = poClient.newRecord();
        if (!"success".equals((String) poJSON.get("result"))) return poJSON;
        
        poMobile.clear();
        poJSON = addMobile();
        if (!"success".equals((String) poJSON.get("result"))) return poJSON;
        
        poAddress.clear();
        poJSON = addAddress();
        if (!"success".equals((String) poJSON.get("result"))) return poJSON;
        
        poMail.clear();
        poJSON = addMail();
        if (!"success".equals((String) poJSON.get("result"))) return poJSON;
        
        poSocMed.clear();
        poJSON = addSocialMedia();
        if (!"success".equals((String) poJSON.get("result"))) return poJSON;
        
        poInsContact.clear();
        poJSON = addInsContactPerson();
        if (!"success".equals((String) poJSON.get("result"))) return poJSON;
        
        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject Save(){
        int lnCtr;
        
        if (psParent.isEmpty()) 
            poGRider.beginTrans();
        
        //assign modified info
        poClient.getModel().setModifyingId(poGRider.getUserID());
        poClient.getModel().setModifiedDate(poGRider.getServerDate());
        
        //save client master
        poJSON = poClient.saveRecord();
        
        if (!"success".equals((String) poJSON.get("result"))){
            if (psParent.isEmpty()) poGRider.rollbackTrans();
            return poJSON;
        }
        
        //save mobile
        if (!poMobile.isEmpty()){            
            for(lnCtr = 0; lnCtr <= poMobile.size()-1; lnCtr++){
                if ((poMobile.get(lnCtr).getEditMode() == EditMode.ADDNEW ||
                        poMobile.get(lnCtr).getEditMode() == EditMode.UPDATE) &&
                            !poMobile.get(lnCtr).getModel().getMobileNo().isEmpty()){

                    if (poMobile.get(lnCtr).getEditMode() == EditMode.ADDNEW){
                        poMobile.get(lnCtr).getModel().setClientId(poClient.getModel().getClientId());
                    }
                    
                    poMobile.get(lnCtr).getModel().setModifiedDate(poClient.getModel().getModifiedDate());
                    
                    //save
                    poJSON = poMobile.get(lnCtr).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))){
                        if (psParent.isEmpty()) poGRider.rollbackTrans();
                        return poJSON;
                    }
                }
            }
        }
        
        //save address
        if (!poAddress.isEmpty()){            
            for(lnCtr = 0; lnCtr <= poAddress.size()-1; lnCtr++){
                if ((poAddress.get(lnCtr).getEditMode() == EditMode.ADDNEW ||
                        poAddress.get(lnCtr).getEditMode() == EditMode.UPDATE) &&
                        (!poAddress.get(lnCtr).getModel().getAddress().isEmpty() ||
                            !poAddress.get(lnCtr).getModel().getBarangayId().isEmpty())){

                    if (poAddress.get(lnCtr).getEditMode() == EditMode.ADDNEW){
                        poAddress.get(lnCtr).getModel().setClientId(poClient.getModel().getClientId());
                    }
                    
                    poAddress.get(lnCtr).getModel().setModifiedDate(poClient.getModel().getModifiedDate());
                    
                    //save
                    poJSON = poAddress.get(lnCtr).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))){
                        if (psParent.isEmpty()) poGRider.rollbackTrans();
                        return poJSON;
                    }
                }
            }
        }
        
        //save email
        if (!poMail.isEmpty()){            
            for(lnCtr = 0; lnCtr <= poMail.size()-1; lnCtr++){
                if ((poMail.get(lnCtr).getEditMode() == EditMode.ADDNEW ||
                        poMail.get(lnCtr).getEditMode() == EditMode.UPDATE) &&
                        !poMail.get(lnCtr).getModel().getMailAddress().isEmpty()){

                    if (poMail.get(lnCtr).getEditMode() == EditMode.ADDNEW){
                        poMail.get(lnCtr).getModel().setClientId(poClient.getModel().getClientId());
                    }
                    
                    poMail.get(lnCtr).getModel().setModifiedDate(poClient.getModel().getModifiedDate());
                    
                    //save
                    poJSON = poMail.get(lnCtr).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))){
                        if (psParent.isEmpty()) poGRider.rollbackTrans();
                        return poJSON;
                    }
                }
            }
        }
        
        //save socmed
        if (!poSocMed.isEmpty()){            
            for(lnCtr = 0; lnCtr <= poSocMed.size()-1; lnCtr++){
                if ((poSocMed.get(lnCtr).getEditMode() == EditMode.ADDNEW ||
                        poSocMed.get(lnCtr).getEditMode() == EditMode.UPDATE) &&
                        !poSocMed.get(lnCtr).getModel().getAccount().isEmpty()){

                    if (poSocMed.get(lnCtr).getEditMode() == EditMode.ADDNEW){
                        poSocMed.get(lnCtr).getModel().setClientId(poClient.getModel().getClientId());
                    }
                    
                    poSocMed.get(lnCtr).getModel().setModifiedDate(poClient.getModel().getModifiedDate());
                    
                    //save
                    poJSON = poSocMed.get(lnCtr).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))){
                        if (psParent.isEmpty()) poGRider.rollbackTrans();
                        return poJSON;
                    }
                }
            }
        }
        
        //save contact person
        if (!poInsContact.isEmpty()){            
            for(lnCtr = 0; lnCtr <= poInsContact.size()-1; lnCtr++){
                if ((poInsContact.get(lnCtr).getEditMode() == EditMode.ADDNEW ||
                        poInsContact.get(lnCtr).getEditMode() == EditMode.UPDATE) &&
                        !poInsContact.get(lnCtr).getModel().getContactPersonName().isEmpty()){

                    if (poInsContact.get(lnCtr).getEditMode() == EditMode.ADDNEW){
                        poInsContact.get(lnCtr).getModel().setClientId(poClient.getModel().getClientId());
                    }
                    
                    poInsContact.get(lnCtr).getModel().setModifiedDate(poClient.getModel().getModifiedDate());
                    
                    //save
                    poJSON = poInsContact.get(lnCtr).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))){
                        if (psParent.isEmpty()) poGRider.rollbackTrans();
                        return poJSON;
                    }
                }
            }
        }
        
        if (psParent.isEmpty()) 
            poGRider.commitTrans();
        
        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;        
    }

    private Client_Mobile mobile(){
        Client_Mobile object = new Client_Mobile();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
        object.setLogWrapper(poLogWrapper);
        object.initialize();
        object.newRecord();
        return object;
    }
    
    private Client_Address address(){
        Client_Address object = new Client_Address();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
        object.setLogWrapper(poLogWrapper);
        object.initialize();
        object.newRecord();
        return object;
    }
    
    private Client_Mail email(){
        Client_Mail object = new Client_Mail();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
        object.setLogWrapper(poLogWrapper);
        object.initialize();
        object.newRecord();
        return object;
    }
    
    private Client_Social_Media socmed(){
        Client_Social_Media object = new Client_Social_Media();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
        object.setLogWrapper(poLogWrapper);
        object.initialize();
        object.newRecord();
        return object;
    }
    
    private Client_Institution_Contact insCPerson(){
        Client_Institution_Contact object = new Client_Institution_Contact();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
        object.setLogWrapper(poLogWrapper);
        object.initialize();
        object.newRecord();
        return object;
    }
}
