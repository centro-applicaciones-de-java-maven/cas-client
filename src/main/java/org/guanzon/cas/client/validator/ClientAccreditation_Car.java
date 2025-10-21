/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.guanzon.cas.client.validator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.constant.UserRight;
import org.guanzon.appdriver.iface.GValidator;
import org.guanzon.cas.client.constants.AccountAccreditationStatus;
import org.guanzon.cas.client.model.Model_Account_Client_Accreditation;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class ClientAccreditation_Car implements GValidator{

    private GRiderCAS poGRider;
    private JSONObject poJSON;
    private boolean isRequiredApproval;
    private String psTranStat;

    Model_Account_Client_Accreditation poMaster;

    @Override
    public void setApplicationDriver(Object applicationDriver) {
        poGRider = (GRiderCAS) applicationDriver;
    }

    @Override
    public void setTransactionStatus(String transactionStatus) {
        psTranStat = transactionStatus;
    }

    @Override
    public void setMaster(Object value) {
        poMaster = (Model_Account_Client_Accreditation) value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setDetail(ArrayList<Object> value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setOthers(ArrayList<Object> value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject validate() {
        
        try {
            
            //main validation for all status
        
            isRequiredApproval = false;
            poJSON = new JSONObject();

            if (poMaster.getDateTransact()== null) {
                poJSON.put("result", "error");
                poJSON.put("message", "Invalid Transaction Date.");
                return poJSON;
            }

            //validate industry
            if (poGRider.getIndustry()== null || poGRider.getIndustry().isEmpty()) {
                poJSON.put("result", "error");
                poJSON.put("message", "Company is not set.");
                return poJSON;
            }

            //validate account type
            if (poMaster.getAccountType() == null || poMaster.getAccountType().isEmpty()) {
                poJSON.put("result", "error");
                poJSON.put("message", "Company is not set.");
                return poJSON;
            }

            //validate cliend id
            if (poMaster.getClientId() == null || poMaster.getClientId().isEmpty()) {
                poJSON.put("result", "error");
                poJSON.put("message", "Client must not be empty.");
                return poJSON;
            }

            //validate contact id
            if (poMaster.getContactId() == null || poMaster.getContactId().isEmpty()) {
                poJSON.put("result", "error");
                poJSON.put("message", "Contact must not be empty.");
                return poJSON;
            }
            
            //change transaction date 
            if (poMaster.getDateTransact().after((Date) poGRider.getServerDate())
                    && poMaster.getDateTransact().before((Date) poGRider.getServerDate())) {
                poJSON.put("message", "Change of transaction date are not allowed.! Approval is Required");
                isRequiredApproval = true;
            }
            
            switch(psTranStat){
                case AccountAccreditationStatus.CONFIRMED:
                    return validateConfirmed();
                    
                case AccountAccreditationStatus.VOID:
                    return validateVoid();
            }

            poJSON.put("result", "success");
            poJSON.put("isRequiredApproval", isRequiredApproval);

            return poJSON;

        } catch (Exception e) {
            
            Logger.getLogger(ClientAccreditation_Car.class.getName()).log(Level.SEVERE, null, e);
            
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
            
            return poJSON;
        }
    }
    
    private JSONObject validateConfirmed(){
        
        poJSON = new JSONObject();
        
        //if already confirmed
        if (poMaster.getRecordStatus().equals(AccountAccreditationStatus.CONFIRMED)) {
            poJSON.put("result", "error");
            poJSON.put("message", "Transaction already confirmed!");
            return poJSON;
        }

        //if modified by enocder below, ask for approval
        if (poGRider.getUserLevel() <= UserRight.ENCODER) {
            isRequiredApproval = true;
        }
        
        //if date approved is empty
        if (poMaster.getDateApproved()== null) {
            poJSON.put("result", "error");
            poJSON.put("message", "Invalid date approved");
            return poJSON;
        }
        
        //if user approved is empty
        if (poMaster.getApproved() == null || poMaster.getApproved().isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("message", "IApprover is empty");
            return poJSON;
        }
        
        poJSON.put("result", "success");
        poJSON.put("isRequiredApproval", isRequiredApproval);
        
        return poJSON;
    }
    
    private JSONObject validateVoid(){
        
        poJSON = new JSONObject();
        
        //if already confirmed
        if (poMaster.getRecordStatus().equals(AccountAccreditationStatus.CONFIRMED)) {
            poJSON.put("result", "error");
            poJSON.put("message", "Transaction already confirmed!");
            return poJSON;
        }
        //if already voided
        if (poMaster.getRecordStatus().equals(AccountAccreditationStatus.VOID)) {
            poJSON.put("result", "error");
            poJSON.put("message", "Transaction already voided!");
            return poJSON;
        }

        //if modified by enocder below, ask for approval
        if (poGRider.getUserLevel() <= UserRight.ENCODER) {
            isRequiredApproval = true;
        }
        
        poJSON.put("result", "success");
        poJSON.put("isRequiredApproval", isRequiredApproval);
        
        return poJSON;
    }
    
}
