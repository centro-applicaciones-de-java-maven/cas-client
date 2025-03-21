package org.guanzon.cas.client;

import java.sql.SQLException;
import java.util.ArrayList;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Parameter;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.UserRight;
import org.guanzon.cas.client.model.Model_Client_Address;
import org.guanzon.cas.client.model.Model_Client_Mail;
import org.guanzon.cas.client.model.Model_Client_Master;
import org.guanzon.cas.client.model.Model_Client_Mobile;
import org.guanzon.cas.client.model.Model_Client_Social_Media;
import org.guanzon.cas.client.services.ClientModels;
import org.json.simple.JSONObject;

public class ClientInfo extends Parameter{
    Model_Client_Master poClient;
    ArrayList<Model_Client_Mobile> paMobile;
    ArrayList<Model_Client_Address> paAddress;
    ArrayList<Model_Client_Mail> paMail;
    ArrayList<Model_Client_Social_Media> paSocMed;
    
    ClientModels poModels;
    
    @Override
    public void initialize() {
        psRecdStat = Logical.YES;

        poModels = new ClientModels(poGRider);
        poClient = poModels.ClientMaster();
    }
    
    @Override
    public JSONObject isEntryOkay() throws SQLException, GuanzonException{
        poJSON = new JSONObject();
        
        if (poGRider.getUserLevel() < UserRight.SYSADMIN){
            poJSON.put("result", "error");
            poJSON.put("message", "User is not allowed to save record.");
            return poJSON;
        } else {
            poJSON = new JSONObject();
            
            if (poClient.getClientId().isEmpty()){
                poJSON.put("result", "error");
                poJSON.put("message", "Client ID must not be empty.");
                return poJSON;
            }
            
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
            
            if (getEditMode() == EditMode.ADDNEW){
                poClient.setModifyingId(poGRider.Encrypt(poGRider.getUserID()));
                poClient.setModifiedDate(poGRider.getServerDate());
            }
            
            
//            if (poClient.getBirthDate() == SQLUtil.toDate("1900-01-01", SQLUtil.FORMAT_SHORT_DATE)){
//                poJSON.put("result", "error");
//                poJSON.put("message", "Invalid birth date must not be empty.");
//                return poJSON;
//            }
        }
        
        poJSON.put("result", "success");
        return poJSON;
    }
    
    @Override
    public Model_Client_Master getModel() {
        return poClient;
    }
    
    @Override
    public JSONObject searchRecord(String value, boolean byCode) throws SQLException, GuanzonException{
        poJSON = ShowDialogFX.Search(poGRider,
                getSQ_Browse(),
                value,
                "Serial ID»Description»Serial 01»Serial 02",
                "sSerialID»xDescript»sSerial01»sSerial02",
                "a.sSerialID»b.sDescript»a.sSerial01»a.sSerial02",
                byCode ? 0 : 1);

        if (poJSON != null) {
            return poClient.openRecord((String) poJSON.get("sSerialID"));
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
        
    public Model_Client_Mobile Mobile(int row){
        if (row > paMobile.size() -1) return null;
        
        return paMobile.get(row);
    }
    
    public int getMobileCount(){
        return paMobile.size();
    } 
}