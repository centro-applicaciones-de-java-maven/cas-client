package org.guanzon.cas.client;

import java.util.List;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.constant.EditMode;
import org.json.simple.JSONObject;

public class Client {
    GRider poGRider;
    String psParent;
    
    JSONObject poJSON;
    
    Client_Master poClient;
    List<Client_Mobile> poMobile;
    List<Client_Address> poAddress;
    List<Client_Mail> poMail;
    List<Client_Social_Media> poSocMed;
    List<Client_Institution_Contact> poInsContact;
    
    public Client(GRider applicationDriver,
                    String parentClass){
        
        poGRider = applicationDriver;
        psParent = parentClass;
        
        poClient = new Client_Master();
        poClient.setApplicationDriver(poGRider);
        poClient.setWithParentClass(true);
        poClient.initialize();
    }
        
    public Client_Master Master(){
        return poClient;
    }
    
    public Client_Mobile Mobile(int row){
        return poMobile.get(row);
    }
    
    public int getMobileCount(){
        return poMobile.size();
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
                poJSON.put("result", "Unable to add mobile. Last row mobile number is still empty.");
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
        
        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }
    
    public JSONObject Save(){
        int lnCtr;
        
        if (psParent.isEmpty()) poGRider.beginTrans();
        
        //save client master
        poJSON = poClient.saveRecord();
        
        if (!"success".equals((String) poJSON.get("result"))){
            if (psParent.isEmpty()) poGRider.rollbackTrans();
            return poJSON;
        }
        
        if (!poMobile.isEmpty()){            
            for(lnCtr = 0; lnCtr <= poMobile.size()-1; lnCtr++){
                if (poMobile.get(lnCtr).getEditMode() == EditMode.ADDNEW ||
                    poMobile.get(lnCtr).getEditMode() == EditMode.UPDATE){

                    if (poMobile.get(lnCtr).getEditMode() == EditMode.ADDNEW){
                        poMobile.get(0).getModel().setClientId(poClient.getModel().getClientId());
                    }
                    
                    //save mobile
                    poJSON = poMobile.get(0).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))){
                        if (psParent.isEmpty()) poGRider.rollbackTrans();
                        return poJSON;
                    }
                }
            }
        }
        
        if (psParent.isEmpty()) poGRider.commitTrans();
        
        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;        
    }

    private Client_Mobile mobile(){
        Client_Mobile object = new Client_Mobile();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
        object.initialize();
        return object;
    }
    
    private Client_Address address(){
        Client_Address object = new Client_Address();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
        object.initialize();
        return object;
    }
    
    private Client_Mail email(){
        Client_Mail object = new Client_Mail();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
        object.initialize();
        return object;
    }
    
    private Client_Social_Media socmed(){
        Client_Social_Media object = new Client_Social_Media();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
        object.initialize();
        return object;
    }
    
    private Client_Institution_Contact insCPerson(){
        Client_Institution_Contact object = new Client_Institution_Contact();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
        object.initialize();
        return object;
    }
}
