/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.guanzon.cas.client.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.guanzon.appdriver.agent.ShowMessageFX;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.constant.ClientType;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.client.ClientInfo;
import org.guanzon.cas.client.Client_Role;
import org.guanzon.cas.client.services.ClientControllers;
import org.json.simple.JSONObject;

/**
 * FXML Controller class
 *
 * @author User
 */

public class ClientRoleController implements Initializable {
    
    private final String MODULE = "Client Controller";
    
    private GRiderCAS poGRider;
    private LogWrapper poWrapper;
    
    private Client_Role poRole;
    
    private String psRoleIDxx;
    private JSONObject poJSON = new JSONObject();
    
    private int pnEditMode;
    private boolean pbCancelled;

    public void setGRider(GRiderCAS griderCAS){
        poGRider = griderCAS;
    }
    
    public void setLogWrapper(LogWrapper wrapper){
        poWrapper = wrapper;
    }
    
    public void setRoleIDxx(String fsRoleIDxx){
        psRoleIDxx = fsRoleIDxx;
    }
    
    public boolean isCancelled(){
        return pbCancelled;
    }
    
    public String getRoleID(){
        return psRoleIDxx;
    }
    
    @FXML
    AnchorPane apMain;
    
    @FXML
    Label lblRoleStatus;
    
    @FXML
    TextField tfRoleIDxx;
    
    @FXML
    TextField tfDescript;
    
    @FXML
    CheckBox chckActive;
    
    @FXML
    Button btnSave;
    
    @FXML
    Button btnCancel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            if (poGRider == null) {
                ShowMessageFX.Warning(getStage(), "Application driver is not set.", "Warning", MODULE);
                System.exit(1);
            }

            poRole = new ClientControllers(poGRider, poWrapper).ClientRole();
            
            initFields();
            loadRecord();
            
        } catch (SQLException | GuanzonException e) {
            ShowMessageFX.Error(getStage(), e.getMessage(), "Error", MODULE);
            System.exit(1);
        }
        
    }
    
    private void cmdButton_Click(ActionEvent event){
        try {
            switch (((Button) event.getSource()).getId()) {
                case "btnCancel":
                    psRoleIDxx = "";
                    pbCancelled = true;
                    
                    getStage().close();
                    break;
                case "btnSave":
                    
                    if (pnEditMode == EditMode.ADDNEW || pnEditMode == EditMode.UPDATE) {
                        
                        poJSON = poRole.saveRecord();
                        if (!"success".equals((String) poJSON.get("result"))) {
                            ShowMessageFX.Warning(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                            break;
                        }else{
                            psRoleIDxx = poRole.getModel().getRoleIDxx();
                            pbCancelled = false;
                            
                            getStage().close();
                        }
                    }
                    break;

            }
        } catch (SQLException | GuanzonException | CloneNotSupportedException e) {
            ShowMessageFX.Error(getStage(), e.getMessage(), "Error", MODULE);
            System.exit(1);
        }
    }
    
    private void initFields(){
        btnCancel.setOnAction(this::cmdButton_Click);
        btnSave.setOnAction(this::cmdButton_Click);
        
    }
    
    private void loadRecord() throws SQLException, GuanzonException{
        
        if (psRoleIDxx == null || psRoleIDxx.isEmpty()) {
            
            poJSON = poRole.newRecord();
            if (poJSON.get("result").toString().equalsIgnoreCase("success")) {
                lblRoleStatus.setText("***New Role***");
            }else{
                disableFields(true);
                return;
            }
        }else{
            poJSON = poRole.openRecord(psRoleIDxx);
            if (poJSON.get("result").toString().equalsIgnoreCase("success")) {
                
                lblRoleStatus.setText("***Repeat Role***");
                
                poRole.updateRecord();
                if (poJSON.get("result").toString().equalsIgnoreCase("error")) {
                    btnSave.setDisable(true);
                }
            }else{
                disableFields(true);
                return;
            }
        }
        
        //initialize edit mode
        pnEditMode = poRole.getEditMode();
        System.out.print("this is the edit mode " + pnEditMode);
        
        //display details
        tfRoleIDxx.setText(poRole.getModel().getRoleIDxx() == null ? "" : poRole.getModel().getRoleIDxx());
        tfDescript.setText(poRole.getModel().getsRoleDesc() == null ? "" : poRole.getModel().getsRoleDesc());
        chckActive.setSelected((poRole.getModel().getcRecdStat() == null ? "0" : poRole.getModel().getcRecdStat()).equalsIgnoreCase("1"));
    }
    
    private void disableFields(boolean fbDisable){
        btnSave.setDisable(fbDisable);
        chckActive.setDisable(fbDisable);
        tfDescript.setDisable(fbDisable);
    }
    
    public Stage getStage() {
        return (Stage) apMain.getScene().getWindow();
    }    
    
}
