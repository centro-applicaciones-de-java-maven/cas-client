package org.guanzon.cas.client.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.F3;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.guanzon.appdriver.agent.ShowMessageFX;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.ClientType;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.client.ClientInfo;
import org.guanzon.cas.client.services.ClientControllers;
import org.json.simple.JSONObject;

public class IndividualNewController implements Initializable {
    @FXML
    private AnchorPane AnchorMain;
    @FXML
    private AnchorPane draggablePane;
    @FXML
    private Button btnExit;
    @FXML
    private FontAwesomeIconView glyphExit;
    @FXML
    private HBox hbButtons;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtField01;
    @FXML
    private TextField txtField02;
    @FXML
    private TextArea txtField03;
    @FXML
    private TabPane TabPane;
    @FXML
    private Tab PersonalInfo;
    @FXML
    private TextField txtPersonal02;
    @FXML
    private TextField txtPersonal03;
    @FXML
    private TextField txtPersonal04;
    @FXML
    private TextField txtPersonal05;
    @FXML
    private TextField txtPersonal06;
    @FXML
    private TextField txtPersonal07;
    @FXML
    private TextField txtPersonal08;
    @FXML
    private ComboBox cmbPersonal09;
    @FXML
    private ComboBox cmbPersonal10;
    @FXML
    private TextField txtPersonal11;
    @FXML
    private TextField txtPersonal12;
    @FXML
    private TextField txtPersonal13;
    @FXML
    private TextField txtPersonal15;
    @FXML
    private TextField txtPersonal14;
    @FXML
    private Tab Address;
    @FXML
    private AnchorPane anchorAddress;
    @FXML
    private TableView tblAddress;
    @FXML
    private TableColumn indexAddress01;
    @FXML
    private TableColumn indexAddress02;
    @FXML
    private TableColumn indexAddress03;
    @FXML
    private TableColumn indexAddress04;
    @FXML
    private TableColumn indexAddress05;
    @FXML
    private TextField txtAddress03;
    @FXML
    private TextField txtAddress04;
    @FXML
    private TextField txtAddress05;
    @FXML
    private TextField txtAddress01;
    @FXML
    private TextField txtAddress02;
    @FXML
    private TextField txtAddress06;
    @FXML
    private TextField txtAddress07;
    @FXML
    private CheckBox cbAddress01;
    @FXML
    private CheckBox cbAddress02;
    @FXML
    private CheckBox cbAddress03;
    @FXML
    private CheckBox cbAddress04;
    @FXML
    private CheckBox cbAddress05;
    @FXML
    private CheckBox cbAddress06;
    @FXML
    private CheckBox cbAddress07;
    @FXML
    private CheckBox cbAddress08;
    @FXML
    private Button btnAddAddress;
    @FXML
    private Button btnDelAddress;
    @FXML
    private Tab Mobile;
    @FXML
    private ComboBox cmbMobile01;
    @FXML
    private ComboBox  cmbMobile02;
    @FXML
    private TextField txtMobile01;
    @FXML
    private CheckBox cbMobileNo01;
    @FXML
    private CheckBox cbMobileNo02;
    @FXML
    private Button btnAddMobile;
    @FXML
    private Button btnDelMobile;
    @FXML
    private TableView tblMobile;
    @FXML
    private TableColumn indexMobileNo01;
    @FXML
    private TableColumn indexMobileNo02;
    @FXML
    private TableColumn indexMobileNo03;
    @FXML
    private TableColumn indexMobileNo04;
    @FXML
    private Tab Email;
    @FXML
    private ComboBox cmbEmail01;
    @FXML
    private TextField txtEmail01;
    @FXML
    private CheckBox cbEmail01;
    @FXML
    private CheckBox cbEmail02;
    @FXML
    private Button btnAddEmail;
    @FXML
    private Button btnDelEmail;
    @FXML
    private TableView tblEmail;
    @FXML
    private TableColumn indexEmail01;
    @FXML
    private TableColumn indexEmail02;
    @FXML
    private TableColumn indexEmail03;
    @FXML
    private Tab SocialMedia;
    @FXML
    private ComboBox cmbSocMed01;
    @FXML
    private TextField txtSocial01;
    @FXML
    private TextArea txtSocial02;
    @FXML
    private CheckBox cbSocMed01;
    @FXML
    private Button btnAddSocMed;
    @FXML
    private Button btnDelSocMed;
    @FXML
    private TableView tblSocMed;
    @FXML
    private TableColumn indexSocMed01;
    @FXML
    private TableColumn indexSocMed02;
    @FXML
    private TableColumn indexSocMed03;
    @FXML
    private TableColumn indexSocMed04;
    
    private final String MODULE = "Client Controller";
    private GRiderCAS poGRider;
    private LogWrapper poWrapper;
    
    private ClientInfo poClient;
    private String psClientID;

    private int pnEditMode;
    private int pnAddress;
    private int pnMobile;
    private int pnEmail;
    private int pnSocmed;
    
    private JSONObject poJSON;
    private boolean pbLoaded;
    private boolean pbCancelled;
    
    ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female");
    ObservableList<String> civilstatus = FXCollections.observableArrayList("Single", "Married", "Separated", "Widowed", "Single Parent", "Single Parent w/ Live-in Partner");
    
    public void setGRider(GRiderCAS griderCAS){
        poGRider = griderCAS;
    }
    
    public void setLogWrapper(LogWrapper wrapper){
        poWrapper = wrapper;
    }
    
    public void setClientId(String clientId){
        psClientID = clientId;
    }
    
    public ClientInfo getClient(){
        return poClient;
    }
    
    public boolean isCancelled(){
        return pbCancelled;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        try {
            if (poGRider == null){
                ShowMessageFX.Warning(getStage(), "Application driver is not set.", "Warning", MODULE);
                System.exit(1);
            }
                        
            initFields();
 
            poClient = new ClientControllers(poGRider, poWrapper).ClientInfo();
            poClient.setClientType(ClientType.INDIVIDUAL);
            poClient.setRecordStatus("1");
            loadRecord();
            
            pbLoaded = true;
        } catch (SQLException | GuanzonException e) {
            ShowMessageFX.Error(getStage(), e.getMessage(), "Error", MODULE);
            System.exit(1);
        }
    }        
    
    private void cmdButton_Click(ActionEvent event) {
        try {
            switch (((Button)event.getSource()).getId()){
                case "btnExit":
                case "btnCancel":
                    psClientID = "";
                    pbCancelled = true;
                    getStage().close();
                    break;
                case "btnSave":
                    poJSON = poClient.saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))){
                        ShowMessageFX.Warning(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                        break;
                    }

                    psClientID = poClient.getModel().getClientId();
                    pbCancelled = false;
                    getStage().close();
                    break;
            }
        } catch (SQLException | GuanzonException | CloneNotSupportedException e) {
            ShowMessageFX.Error(getStage(), e.getMessage(), "Error", MODULE);
            System.exit(1);
        }
        
    }
    
    private void address_Clicked(MouseEvent event) {
        pnAddress = tblAddress.getSelectionModel().getSelectedIndex();
    }
    
    private void mobile_Clicked(MouseEvent event) {
        pnMobile = tblMobile.getSelectionModel().getSelectedIndex();
    }
    
    private void email_Clicked(MouseEvent event) {
        pnEmail = tblEmail.getSelectionModel().getSelectedIndex();
    }
    
    private void socmed_Clicked(MouseEvent event) {
        pnSocmed = tblSocMed.getSelectionModel().getSelectedIndex();
    }
    
    private void txtPersonal_KeyPressed(KeyEvent event){
        TextField txtField = (TextField)event.getSource();
        int lnIndex = Integer.parseInt(txtField.getId().substring(11, 13));
        
        String lsValue = txtField.getText();
        
        try {
            if (event.getCode() == F3 || event.getCode() == ENTER){
                switch (lnIndex){
                    case 6:
                        poJSON = poClient.searchCitizenship(lsValue);
                        
                        if ("success".equals((String) poJSON.get("result"))){
                            txtField.setText(poClient.getModel().Citizenship().getDescription());
                        }

                        break;
                    case 8:
                        poJSON = poClient.searchBirthPlace(lsValue);
                        
                        if ("success".equals((String) poJSON.get("result"))){
                            txtField.setText(poClient.getModel().BirthTown().getDescription());
                        }
                        
                        break;
                }
            }
        } catch (SQLException | GuanzonException e) {
            ShowMessageFX.Error(getStage(), e.getMessage(), "Error", MODULE);
            System.exit(1);
        }
        

        switch (event.getCode()){
            case ENTER:
            case DOWN:
                CommonUtils.SetNextFocus(txtField);
                break;
            case UP:
                CommonUtils.SetPreviousFocus(txtField);
        }
    }
    
    final ChangeListener<? super Boolean> txtPersonal_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(11, 13));
        
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){//lost focus
            switch(lnIndex){
                case 2:
                    poJSON = poClient.getModel().setLastName(lsValue);
                    
                    if (!"success".equals((String) poJSON.get("result"))){
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }
                    
                    txtField.setText(poClient.getModel().getLastName());
                    txtField02.setText((poClient.getModel().getLastName() + ", " + poClient.getModel().getFirstName() + " " + poClient.getModel().getSuffixName() + " " + poClient.getModel().getMiddleName()).trim());                    
                    break;
                case 3:
                    poJSON = poClient.getModel().setFirstName(lsValue);
                    
                    if (!"success".equals((String) poJSON.get("result"))){
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }
                    
                    txtField.setText(poClient.getModel().getFirstName());
                    txtField02.setText((poClient.getModel().getLastName() + ", " + poClient.getModel().getFirstName() + " " + poClient.getModel().getSuffixName() + " " + poClient.getModel().getMiddleName()).trim());                    
                    break;
                case 4:
                    poJSON = poClient.getModel().setMiddleName(lsValue);
                    
                    if (!"success".equals((String) poJSON.get("result"))){
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }
                    
                    txtField.setText(poClient.getModel().getMiddleName());
                    txtField02.setText((poClient.getModel().getLastName() + ", " + poClient.getModel().getFirstName() + " " + poClient.getModel().getSuffixName() + " " + poClient.getModel().getMiddleName()).trim());                    
                    break;
                case 5:    
                    poJSON = poClient.getModel().setSuffixName(lsValue);
                    
                    if (!"success".equals((String) poJSON.get("result"))){
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }
                    
                    txtField.setText(poClient.getModel().getSuffixName());
                    txtField02.setText((poClient.getModel().getLastName() + ", " + poClient.getModel().getFirstName() + " " + poClient.getModel().getSuffixName() + " " + poClient.getModel().getMiddleName()).trim());                    
                    break;
                case 7:
                    if (!CommonUtils.isDate(lsValue, SQLUtil.FORMAT_SHORT_DATE)){
                        ShowMessageFX.Error(getStage(), "Invalid date input. Please follow yyyy-mm-dd format.", "Warning", MODULE);
                        
                        try {
                            lsValue = SQLUtil.dateFormat(poGRider.getServerDate(), SQLUtil.FORMAT_SHORT_DATE);
                        } catch (SQLException e) {
                            lsValue = "1900-01-01";
                        }
                    }
                    
                    poJSON = poClient.getModel().setBirthDate(SQLUtil.toDate(lsValue, SQLUtil.FORMAT_SHORT_DATE));
                    
                    if (!"success".equals((String) poJSON.get("result"))){
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }
                    
                    txtField.setText(SQLUtil.dateFormat(poClient.getModel().getBirthDate(), SQLUtil.FORMAT_SHORT_DATE));
                    break;
                case 12:
                    poJSON = poClient.getModel().setMothersMaidenName(lsValue);
                    
                    if (!"success".equals((String) poJSON.get("result"))){
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }
                    
                    txtField.setText(poClient.getModel().getMothersMaidenName());
                    break;
                case 13:
                    poJSON = poClient.getModel().setTaxIdNumber(lsValue);
                    
                    if (!"success".equals((String) poJSON.get("result"))){
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }
                    
                    txtField.setText(poClient.getModel().getTaxIdNumber());
                    break;
                case 14:
                    break;
                case 15:
                    break;
            }
        } else{//got focus
            txtField.selectAll();
        }
    };
    
    final ChangeListener<? super Boolean> txtAddress_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(10, 12));
        
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){//lost focus
            switch(lnIndex){
            
            }
        } else{//got focus
            txtField.selectAll();
        }
    };
    
    final ChangeListener<? super Boolean> txtMobile_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(9, 11));
        
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){//lost focus
            switch(lnIndex){
            
            }
        } else{//got focus
            txtField.selectAll();
        }
    };
    
    final ChangeListener<? super Boolean> txtEmail_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){//lost focus
            switch(lnIndex){
            
            }
        } else{//got focus
            txtField.selectAll();
        }
    };
    
    final ChangeListener<? super Boolean> txtSocmed_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(9, 11));
        
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){//lost focus
            switch(lnIndex){
            
            }
        } else{//got focus
            txtField.selectAll();
        }
    };
    
    private void initFields(){
        txtPersonal02.focusedProperty().addListener(txtPersonal_Focus);
        txtPersonal03.focusedProperty().addListener(txtPersonal_Focus);
        txtPersonal04.focusedProperty().addListener(txtPersonal_Focus);
        txtPersonal05.focusedProperty().addListener(txtPersonal_Focus);
        txtPersonal06.focusedProperty().addListener(txtPersonal_Focus);
        txtPersonal07.focusedProperty().addListener(txtPersonal_Focus);
        txtPersonal08.focusedProperty().addListener(txtPersonal_Focus);
        txtPersonal11.focusedProperty().addListener(txtPersonal_Focus);
        txtPersonal12.focusedProperty().addListener(txtPersonal_Focus);
        txtPersonal13.focusedProperty().addListener(txtPersonal_Focus);
        txtPersonal14.focusedProperty().addListener(txtPersonal_Focus);
        txtPersonal15.focusedProperty().addListener(txtPersonal_Focus);

        txtPersonal02.setOnKeyPressed(this::txtPersonal_KeyPressed);
        txtPersonal03.setOnKeyPressed(this::txtPersonal_KeyPressed);
        txtPersonal04.setOnKeyPressed(this::txtPersonal_KeyPressed);
        txtPersonal05.setOnKeyPressed(this::txtPersonal_KeyPressed);
        txtPersonal06.setOnKeyPressed(this::txtPersonal_KeyPressed);
        txtPersonal07.setOnKeyPressed(this::txtPersonal_KeyPressed);
        txtPersonal08.setOnKeyPressed(this::txtPersonal_KeyPressed);            
        txtPersonal11.setOnKeyPressed(this::txtPersonal_KeyPressed);
        txtPersonal12.setOnKeyPressed(this::txtPersonal_KeyPressed);
        txtPersonal13.setOnKeyPressed(this::txtPersonal_KeyPressed);
        txtPersonal14.setOnKeyPressed(this::txtPersonal_KeyPressed);
        txtPersonal15.setOnKeyPressed(this::txtPersonal_KeyPressed);

        txtAddress01.focusedProperty().addListener(txtAddress_Focus);
        txtAddress02.focusedProperty().addListener(txtAddress_Focus);
        txtAddress03.focusedProperty().addListener(txtAddress_Focus);
        txtAddress04.focusedProperty().addListener(txtAddress_Focus);
        txtAddress05.focusedProperty().addListener(txtAddress_Focus);
        txtAddress06.focusedProperty().addListener(txtAddress_Focus);
        txtAddress07.focusedProperty().addListener(txtAddress_Focus);

        txtMobile01.focusedProperty().addListener(txtMobile_Focus);
        txtEmail01.focusedProperty().addListener(txtEmail_Focus);
        txtSocial01.focusedProperty().addListener(txtSocmed_Focus);
        txtSocial02.focusedProperty().addListener(txtSocmed_Focus);

        btnExit.setOnAction(this::cmdButton_Click);
        btnCancel.setOnAction(this::cmdButton_Click);
        btnSave.setOnAction(this::cmdButton_Click);

        tblAddress.setOnMouseClicked(this::address_Clicked);
        tblMobile.setOnMouseClicked(this::mobile_Clicked);
        tblEmail.setOnMouseClicked(this::email_Clicked);
        tblSocMed.setOnMouseClicked(this::socmed_Clicked);  
        
        cmbPersonal09.setItems(gender);
        cmbPersonal10.setItems(civilstatus);
        
        cmbPersonal09.setOnAction(event -> {            
            poJSON = poClient.getModel().setGender(String.valueOf(cmbPersonal09.getSelectionModel().getSelectedIndex()));
            
            if(!"success".equals((String) poJSON.get("result"))){
                ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
            }
        });

        cmbPersonal10.setOnAction(event -> {            
            poJSON = poClient.getModel().setCivilStatus(String.valueOf(cmbPersonal10.getSelectionModel().getSelectedIndex()));
            
            if(!"success".equals((String) poJSON.get("result"))){
                ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
            }
        });
    }
    
    private void loadRecord(){
        try {
            pnEditMode = psClientID.isEmpty() ? EditMode.ADDNEW : EditMode.UPDATE;
        
            if (pnEditMode == EditMode.ADDNEW)
                poJSON =  poClient.newRecord();
            else
                poJSON = poClient.openRecord(psClientID);

            if (!"success".equals((String) poJSON.get("result"))){
                ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Error", MODULE);
                System.exit(1);
            }
            
            txtField01.setText(poClient.getModel().getClientId());            
            txtField02.setText(poClient.getModel().getCompanyName());
            txtField03.setText("Set primary adress here");      
            
            if (pnEditMode == EditMode.ADDNEW){
                cmbPersonal09.getSelectionModel().selectFirst();
                poClient.getModel().setGender(String.valueOf(cmbPersonal09.getSelectionModel().getSelectedIndex()));
                
                cmbPersonal10.getSelectionModel().selectFirst();
                poClient.getModel().setCivilStatus(String.valueOf(cmbPersonal10.getSelectionModel().getSelectedIndex()));
            } else {
                cmbPersonal09.getSelectionModel().select(Integer.parseInt(poClient.getModel().getGender()));
                cmbPersonal10.getSelectionModel().select(Integer.parseInt(poClient.getModel().getCivilStatus()));
            }
            
            txtPersonal02.requestFocus();
            txtPersonal02.selectAll();
        } catch (SQLException | GuanzonException e) {
            ShowMessageFX.Error(getStage(), e.getMessage(), "Error", MODULE);
            System.exit(1);
        }       
    }
    
    public Stage getStage() {
        return (Stage) AnchorMain.getScene().getWindow();        
    }
}