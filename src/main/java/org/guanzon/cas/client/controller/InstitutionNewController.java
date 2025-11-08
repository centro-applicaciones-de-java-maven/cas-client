package org.guanzon.cas.client.controller;

import com.ibm.icu.impl.Assert;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.F3;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.guanzon.appdriver.agent.ShowMessageFX;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.constant.ClientType;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.client.ClientInfo;
import org.guanzon.cas.client.services.ClientControllers;
import org.guanzon.cas.client.table.models.ModelAddress;
import org.json.simple.JSONObject;
import org.guanzon.cas.client.table.models.ModelContactPerson;

public class InstitutionNewController implements Initializable {

    @FXML
    private AnchorPane AnchorMain;

    @FXML
    private TextField txtField01;

    @FXML
    private TextField txtField02;

    @FXML
    private TextArea txtField03;

    @FXML
    private TabPane TabPane;

    @FXML
    private Tab Company;

    @FXML
    private AnchorPane anchorAddress;
    @FXML
    private GridPane gridAddress;

    @FXML
    private TableView<ModelAddress> tblAddress;

    @FXML
    private TableColumn<ModelAddress, String> indexAddress01;

    @FXML
    private TableColumn<ModelAddress, String> indexAddress02;

    @FXML
    private TableColumn<ModelAddress, String> indexAddress03;

    @FXML
    private TableColumn<ModelAddress, String> indexAddress04;

    @FXML
    private TableColumn<ModelAddress, String> indexAddress05;

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
    private CheckBox cbAddress01;

    @FXML
    private CheckBox cbAddress02;

    @FXML
    private CheckBox cbAddress08;

    @FXML
    private Button btnAddAddress;

    @FXML
    private Button btnDelAddress;

    @FXML
    private Label lblClientStatus;
    @FXML
    private TextField txtAddress00;

    @FXML
    private Tab Contact;

    @FXML
    private AnchorPane anchorSocMed;
    @FXML
    private GridPane gridSocMed;

    @FXML
    private TextField txtContact01;

    @FXML
    private CheckBox cbContact01;
    
    @FXML
    private CheckBox cbContact02;

    @FXML
    private Button btnAddContact;

    @FXML
    private Button btnDelContact;

    @FXML
    private TextField txtContact00;

    @FXML
    private TextField txtContact02;

    @FXML
    private TextField txtContact03;

    @FXML
    private TextField txtContact04;

    @FXML
    private TextField txtContact05;

    @FXML
    private TableView<ModelContactPerson> tblSocMed;

    @FXML
    private TableColumn<ModelContactPerson, String> indexSocMed01;

    @FXML
    private TableColumn<ModelContactPerson, String> indexSocMed02;

    @FXML
    private TableColumn<ModelContactPerson, String> indexSocMed03;

    @FXML
    private TableColumn<ModelContactPerson, String> indexSocMed04;

    @FXML
    private HBox hbButtons;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private AnchorPane draggablePane;

    @FXML
    private Button btnExit;

    @FXML
    private FontAwesomeIconView glyphExit;

    private final String MODULE = "Institution Controller";
    private GRiderCAS poGRider;
    private LogWrapper poWrapper;

    private ClientInfo poClient;
    private String psClientID;

    private int pnEditMode;
    private int pnCompany;
    private int pnContactPerson;

    private ObservableList<ModelAddress> company_data = FXCollections.observableArrayList();
    private ObservableList<ModelContactPerson> contactPerson_data = FXCollections.observableArrayList();

    private JSONObject poJSON;
    private boolean pbLoaded;
    private boolean pbCancelled;
    private boolean pbLoadingData;

    public void setGRider(GRiderCAS griderCAS) {
        poGRider = griderCAS;
    }

    public void setLogWrapper(LogWrapper wrapper) {
        poWrapper = wrapper;
    }

    public void setClientId(String clientId) {
        psClientID = clientId;
    }

    public ClientInfo getClient() {
        return poClient;
    }

    public boolean isCancelled() {
        return pbCancelled;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (poGRider == null) {
                ShowMessageFX.Warning(getStage(), "Application driver is not set.", "Warning", MODULE);
                System.exit(1);
            }

            initFields();

            poClient = new ClientControllers(poGRider, poWrapper).ClientInfo();
            poClient.setClientType(ClientType.INSTITUTION);
            poClient.setRecordStatus("1");
            
            loadRecord();

            pbLoaded = true;
        } catch (SQLException | GuanzonException e) {
            ShowMessageFX.Error(getStage(), e.getMessage(), "Error", MODULE);
            System.exit(1);
        }
    }

    private void cmdButton_Click(ActionEvent event){
        try {
            switch (((Button) event.getSource()).getId()) {
                case "btnExit":
                case "btnCancel":
                    psClientID = "";
                    pbCancelled = true;
                    getStage().close();
                    break;
                case "btnSave":
                    if (pnEditMode == EditMode.ADDNEW || pnEditMode == EditMode.UPDATE) {
                        poJSON = poClient.saveRecord();

                        if (!"success".equals((String) poJSON.get("result"))) {
                            ShowMessageFX.Warning(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                            break;
                        }else{
                            psClientID = poClient.getModel().getClientId();
                            pbCancelled = false;
                            getStage().close();
                        }
                    }
                    break;
                case "btnDelAddress":
                    break;
                case "btnAddAddress":
                    
                    JSONObject addObjAddress = poClient.addAddress();
                    
                    if ("error".equals((String) addObjAddress.get("result"))) {
                        ShowMessageFX.Information(getStage(), (String) addObjAddress.get("message"), "Computerized Acounting System", MODULE);
                        break;
                    } else {
                        poClient.Address(pnCompany).setClientId(poClient.getModel().getClientId());
                        pnCompany = poClient.getAddressCount() - 1;
                        tblAddress.getSelectionModel().select(pnCompany + 1);
                        
                        loadRecordAddress();

                        txtAddress03.requestFocus();
                    }
                    break;
                case "btnDelMobile":
                    break;
                case "btnAddContact":
                    JSONObject addObj = poClient.addInstiContact();
                    if ("error".equals((String) addObj.get("result"))) {
                        ShowMessageFX.Information(getStage(), (String) addObj.get("message"), "Computerized Acounting System", MODULE);
                        break;
                    } else {
                        poClient.InstiContact(pnContactPerson).setClientId(poClient.getModel().getClientId());
                        pnContactPerson = poClient.getInstiContactCount()- 1;
                        
                        tblSocMed.getSelectionModel().select(pnContactPerson + 1);
                        
                        loadContactPerson();

                        txtContact00.requestFocus();
                    }
                    break;
                case "btnDelContact":
                    break;

            }
        } catch (SQLException | GuanzonException | CloneNotSupportedException e) {
            ShowMessageFX.Error(getStage(), e.getMessage(), "Error", MODULE);
            System.exit(1);
        }
    }

    private void company_Clicked(MouseEvent event) {
        pnCompany = tblAddress.getSelectionModel().getSelectedIndex();

        if (pnCompany >= 0) {
            getSelectedAddress();
        }
    }

    private void contactPerson_Clicked(MouseEvent event) {
        pnContactPerson = tblSocMed.getSelectionModel().getSelectedIndex();

        if (pnContactPerson >= 0) {
            getSelectedContactPerson();
        }
    }

    private void txtAddress_KeyPressed(KeyEvent event) {
        TextField txtField = (TextField) event.getSource();
        int lnIndex = Integer.parseInt(txtField.getId().substring(10, 12));

        String lsValue = txtField.getText();

        try {
            if (event.getCode() == F3 || event.getCode() == ENTER) {
                switch (lnIndex) {

                    case 3: //province
                        poJSON = poClient.searchProvince(pnCompany, lsValue, false);

                        if ("success".equals((String) poJSON.get("result"))) {
                            txtField.setText(poClient.Address(pnCompany).Town().Province().getDescription());
                            CommonUtils.SetNextFocus(txtField);
                            event.consume();
                        }
                        break;
                    case 4: //town
                        poJSON = poClient.searchTown(pnCompany, lsValue, false);

                        if ("success".equals((String) poJSON.get("result"))) {
                            txtField.setText(poClient.Address(pnCompany).Town().getDescription());
                            txtAddress03.setText(poClient.Address(pnCompany).Town().Province().getDescription());
                            CommonUtils.SetNextFocus(txtField);
                            event.consume();
                        }
                        break;
                    case 5: //barangay
                        poJSON = poClient.searchBarangay(pnCompany, lsValue, false);

                        if ("success".equals((String) poJSON.get("result"))) {
                            txtField.setText(poClient.Address(pnCompany).Barangay().getBarangayName());
                            txtAddress04.setText(poClient.Address(pnCompany).Town().getDescription());
                            txtAddress03.setText(poClient.Address(pnCompany).Town().Province().getDescription());
                            CommonUtils.SetNextFocus(txtField);
                            event.consume();
                        }
                        break;
                }
            }
        } catch (SQLException | GuanzonException e) {
            ShowMessageFX.Error(getStage(), e.getMessage(), "Error", MODULE);
            System.exit(1);
        }

        switch (event.getCode()) {
            case ENTER:
            case DOWN:
                CommonUtils.SetNextFocus(txtField);
                break;
            case UP:
                CommonUtils.SetPreviousFocus(txtField);
        }
    }

    private void txtContactPerson_KeyPressed(KeyEvent event) {
        TextField txtField = (TextField) event.getSource();

        switch (event.getCode()) {
            case ENTER:
            case DOWN:
                CommonUtils.SetNextFocus(txtField);
                break;
            case UP:
                CommonUtils.SetPreviousFocus(txtField);
        }
    }

    final ChangeListener<? super Boolean> txtAddress_Focus = (o, ov, nv) -> {
        if (!pbLoaded) {
            return;
        }

        TextField txtField = (TextField) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(10, 12));

        String lsValue = txtField.getText();

        if (lsValue == null) {
            return;
        }

        if (!nv) {//lost focus
            switch (lnIndex) {
                case 0: //company name
                    poJSON = poClient.getModel().setCompanyName(lsValue);

                    if (!"success".equals((String) poJSON.get("result"))) {
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                        return;
                    }

                    txtField.setText(poClient.getModel().getCompanyName());
                    txtField02.setText(poClient.getModel().getCompanyName());
                    break;
                case 1: //house no
                    poJSON = poClient.Address(pnCompany).setHouseNo(lsValue);

                    if (!"success".equals((String) poJSON.get("result"))) {
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                        return;
                    }

                    txtField.setText(poClient.Address(pnCompany).getHouseNo());
                    break;
                case 2: //address
                    poJSON = poClient.Address(pnCompany).setAddress(lsValue);

                    if (!"success".equals((String) poJSON.get("result"))) {
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                        return;
                    }

                    txtField.setText(poClient.Address(pnCompany).getAddress());
                    break;
                    
                case 6: //tax id number
                    poJSON = poClient.getModel().setTaxIdNumber(lsValue);
                    
                    if (!"success".equals((String) poJSON.get("result"))) {
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                        return;
                    }
                    txtField.setText(poClient.getModel().getTaxIdNumber());
                    break;

            }

            loadRecordAddress();
        } else {//got focus
            txtField.selectAll();
        }
    };

    final ChangeListener<? super Boolean> txtContactPerson_Focus = (o, ov, nv) -> {
        if (!pbLoaded) {
            return;
        }

        TextField txtField = (TextField) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(10, 12));

        String lsValue = txtField.getText();

        if (lsValue == null) {
            return;
        }

        if (!nv) {//lost focus
            switch (lnIndex) {
                case 0: //Full Name
                    poJSON = poClient.InstiContact(pnContactPerson).setContactPersonName(lsValue);
                    if (!"success".equals((String) poJSON.get("result"))) {
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }

                    txtField.setText(poClient.InstiContact(pnContactPerson).getContactPersonName());
                    break;
                case 1: //Position
                    poJSON = poClient.InstiContact(pnContactPerson).setContactPersonPosition(lsValue);

                    if (!"success".equals((String) poJSON.get("result"))) {
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }

                    txtField.setText(poClient.InstiContact(pnContactPerson).getContactPersonPosition());
                    break;
                case 2: //Mobile No
                    poJSON = poClient.InstiContact(pnContactPerson).setMobileNo(lsValue);

                    if (!"success".equals((String) poJSON.get("result"))) {
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }

                    txtField.setText(poClient.InstiContact(pnContactPerson).getMobileNo());
                    break;
                case 3: //LandLine
                    poJSON = poClient.InstiContact(pnContactPerson).setLandlineNo(lsValue);

                    if (!"success".equals((String) poJSON.get("result"))) {
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }

                    txtField.setText(poClient.InstiContact(pnContactPerson).getLandlineNo());
                    break;
                case 4: //Fax No
                    poJSON = poClient.InstiContact(pnContactPerson).setFaxNo(lsValue);

                    if (!"success".equals((String) poJSON.get("result"))) {
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }

                    txtField.setText(poClient.InstiContact(pnContactPerson).getFaxNo());
                    break;
                case 5: //Email Address
                    poJSON = poClient.InstiContact(pnContactPerson).setMailAddress(lsValue);

                    if (!"success".equals((String) poJSON.get("result"))) {
                        ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Warning", MODULE);
                    }

                    txtField.setText(poClient.InstiContact(pnContactPerson).getMailAddress());
                    break;

            }

            loadContactPerson();
        } else {//got focus
            txtField.selectAll();
        }
    };

    public void initCompanyGrid() {
        company_data.clear();

        indexAddress01.setStyle("-fx-alignment: CENTER;");
        indexAddress02.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");
        indexAddress03.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");
        indexAddress04.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");
        indexAddress05.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");

        indexAddress01.setCellValueFactory(new PropertyValueFactory<>("index01"));
        indexAddress02.setCellValueFactory(new PropertyValueFactory<>("index02"));
        indexAddress03.setCellValueFactory(new PropertyValueFactory<>("index03"));
        indexAddress04.setCellValueFactory(new PropertyValueFactory<>("index04"));
        indexAddress05.setCellValueFactory(new PropertyValueFactory<>("index05"));

        tblAddress.widthProperty().addListener((ObservableValue<? extends Number> source, Number oldWidth, Number newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) tblAddress.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                header.setReordering(false);
            });
        });

        tblAddress.setItems(company_data);
        tblAddress.getSelectionModel().select(pnCompany + 1);
        tblAddress.autosize();
    }

    private void initContactPersonGrid() {
        contactPerson_data.clear();

        indexSocMed01.setStyle("-fx-alignment: CENTER;");
        indexSocMed02.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");
        indexSocMed03.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");
        indexSocMed04.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");

        indexSocMed01.setCellValueFactory(new PropertyValueFactory<>("index01"));
        indexSocMed02.setCellValueFactory(new PropertyValueFactory<>("index02"));
        indexSocMed03.setCellValueFactory(new PropertyValueFactory<>("index03"));
        indexSocMed04.setCellValueFactory(new PropertyValueFactory<>("index04"));

        tblSocMed.widthProperty().addListener((ObservableValue<? extends Number> source, Number oldWidth, Number newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) tblSocMed.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                header.setReordering(false);
            });
        });
        tblSocMed.setItems(contactPerson_data);
        tblSocMed.autosize();
    }

    private void initFields() {

        txtAddress00.focusedProperty().addListener(txtAddress_Focus);
        txtAddress01.focusedProperty().addListener(txtAddress_Focus);
        txtAddress02.focusedProperty().addListener(txtAddress_Focus);
        txtAddress03.focusedProperty().addListener(txtAddress_Focus);
        txtAddress04.focusedProperty().addListener(txtAddress_Focus);
        txtAddress05.focusedProperty().addListener(txtAddress_Focus);
        txtAddress06.focusedProperty().addListener(txtAddress_Focus);

        txtAddress01.setOnKeyPressed(this::txtAddress_KeyPressed);
        txtAddress02.setOnKeyPressed(this::txtAddress_KeyPressed);
        txtAddress03.setOnKeyPressed(this::txtAddress_KeyPressed);
        txtAddress04.setOnKeyPressed(this::txtAddress_KeyPressed);
        txtAddress05.setOnKeyPressed(this::txtAddress_KeyPressed);

        txtContact00.focusedProperty().addListener(txtContactPerson_Focus);
        txtContact01.focusedProperty().addListener(txtContactPerson_Focus);
        txtContact02.focusedProperty().addListener(txtContactPerson_Focus);
        txtContact03.focusedProperty().addListener(txtContactPerson_Focus);
        txtContact04.focusedProperty().addListener(txtContactPerson_Focus);

        txtContact05.setOnKeyPressed(this::txtContactPerson_KeyPressed);
        txtContact01.setOnKeyPressed(this::txtContactPerson_KeyPressed);
        txtContact02.setOnKeyPressed(this::txtContactPerson_KeyPressed);
        txtContact03.setOnKeyPressed(this::txtContactPerson_KeyPressed);
        txtContact04.setOnKeyPressed(this::txtContactPerson_KeyPressed);
        txtContact05.setOnKeyPressed(this::txtContactPerson_KeyPressed);

        btnExit.setOnAction(this::cmdButton_Click);
        btnCancel.setOnAction(this::cmdButton_Click);
        btnSave.setOnAction(this::cmdButton_Click);
        btnAddAddress.setOnAction(this::cmdButton_Click);
        btnDelAddress.setOnAction(this::cmdButton_Click);
        btnAddContact.setOnAction(this::cmdButton_Click);
        btnDelContact.setOnAction(this::cmdButton_Click);

        tblAddress.setOnMouseClicked(this::company_Clicked);
        tblSocMed.setOnMouseClicked(this::contactPerson_Clicked);

        initCompanyCheckbox();
        initContactPersonCheckbox();

        clearfields();
    }

    private void clearfields() {
        txtField01.setText("");
        txtField02.setText("");
        txtField03.setText("");

        txtAddress00.setText("");
        txtAddress01.setText("");
        txtAddress02.setText("");
        txtAddress03.setText("");
        txtAddress04.setText("");
        txtAddress05.setText("");
        txtAddress06.setText("");

        cbAddress01.selectedProperty().set(false);
        cbAddress02.selectedProperty().set(false);
        cbAddress08.selectedProperty().set(false);

        txtContact00.setText("");
        txtContact01.setText("");
        txtContact02.setText("");
        txtContact03.setText("");
        txtContact04.setText("");
        txtContact05.setText("");

        cbContact01.selectedProperty().set(false);
        cbContact02.selectedProperty().set(false);

        pnCompany = 0;
        pnContactPerson = 0;

        initCompanyGrid();
        initContactPersonGrid();
    }

    private void loadRecord() {
        try {

            if (psClientID.isEmpty()) {
                poJSON = poClient.newRecord();

                anchorAddress.setDisable(false);
                gridAddress.setDisable(false);
                anchorSocMed.setDisable(false);
                gridSocMed.setDisable(false);
                
                poClient.getModel().setCompanyName(psClientID);
                txtField02.setText(psClientID);
                txtAddress00.setText(psClientID);
                lblClientStatus.setText("***NEW INSTITUTION");
            } else {
                poJSON = poClient.openClientRecord(psClientID);
                poJSON = poClient.updateRecord();
                
                pnEditMode = poClient.getEditMode();
                
                if(pnEditMode == EditMode.ADDNEW || pnEditMode == EditMode.UPDATE){
                    
                    //allow to modify contact on update
                    anchorSocMed.setDisable(false);
                    gridSocMed.setDisable(false);
                    
                    //allow to modify address on update
                    anchorAddress.setDisable(false);
                    gridAddress.setDisable(false);
                }else{
                    anchorSocMed.setDisable(true);
                    gridSocMed.setDisable(true);
                    
                    anchorAddress.setDisable(true);
                    gridAddress.setDisable(true);
                }

                lblClientStatus.setText("***REPEAT INSTITUTION");
            }

            if (!"success".equals((String) poJSON.get("result"))) {
                ShowMessageFX.Error(getStage(), (String) poJSON.get("message"), "Error", MODULE);
                System.exit(1);
            }

            txtField01.setText(poClient.getModel().getClientId());
            txtField02.setText(poClient.getModel().getCompanyName());
            txtAddress00.setText(poClient.getModel().getCompanyName());
            txtAddress06.setText(poClient.getModel().getTaxIdNumber());
            txtField03.setText(""); //todo: put full address here      

            loadRecordAddress();
            loadContactPerson();

            if (pnEditMode == EditMode.ADDNEW || pnEditMode == EditMode.UPDATE) {
                txtAddress00.requestFocus();
                txtAddress00.selectAll();
            } else {
                btnSave.requestFocus();
            }
        } catch (SQLException | GuanzonException | CloneNotSupportedException e) {
            e.printStackTrace();
            ShowMessageFX.Error(getStage(), e.getMessage(), "Error", MODULE);
            System.exit(1);
        }
    }

    private void loadRecordAddress() {
        try {

            int lnCtr;
            company_data.clear();

            if (poClient.getAddressCount() >= 0) {
                
                for (lnCtr = 0; lnCtr < poClient.getAddressCount(); lnCtr++) {
                    
                    company_data.add(new ModelAddress(String.valueOf(lnCtr + 1),
                            (String) poClient.Address(lnCtr).getHouseNo(),
                            (String) poClient.Address(lnCtr).getAddress(),
                            (String) poClient.Address(lnCtr).Town().getDescription(),
                            (String) poClient.Address(lnCtr).Barangay().getBarangayName()));
                    
                    //set primary address the first item, only if size is 1
                    if(poClient.getAddressCount() == 1 && lnCtr == 0){
                        poClient.Address(0).isPrimaryAddress(true);
                    }
                }
            }
            loadMasterAddress();

            if (pnCompany < 0 || pnCompany >= company_data.size()) {
                
                if (!company_data.isEmpty()) {
                    
                    /* FOCUS ON FIRST ROW */
                    tblAddress.getSelectionModel().select(0);
                    tblAddress.getFocusModel().focus(0);
                    pnCompany = tblAddress.getSelectionModel().getSelectedIndex();
                    
                    getSelectedAddress();
                }
            } else {
                
                /* FOCUS ON THE ROW THAT pnRowDetail POINTS TO */
                tblAddress.getSelectionModel().select(pnCompany);
                tblAddress.getFocusModel().focus(pnCompany);
                
                getSelectedAddress();
            }
        } catch (SQLException | GuanzonException e) {
            e.printStackTrace();
        }
    }

    public void loadMasterAddress() throws SQLException, GuanzonException {
        String address;
        boolean primaryAddressExists = false;

        for (int i = 0; i < poClient.getAddressCount(); i++) {
            
            if (poClient.Address(i).isPrimaryAddress()) {
                
                address = poClient.Address(i).getHouseNo() + " "
                        + poClient.Address(i).getAddress() + " "
                        + poClient.Address(i).Barangay().getBarangayName() + " "
                        + poClient.Address(i).Town().getDescription() + ", "
                        + poClient.Address(i).Town().Province().getDescription();

                txtField03.setText(address.trim());
                
                primaryAddressExists = true; // Mark as found
                break; // Exit the loop since a primary address is found
            }
        }

        if (!primaryAddressExists) {
            txtField03.setText("");
        }
    }

    private void getSelectedAddress() {
        pbLoadingData = true;
        try {
            if (poClient.getAddressCount() > 0) {
                txtAddress01.setText(poClient.Address(pnCompany).getHouseNo());
                txtAddress02.setText(poClient.Address(pnCompany).getAddress());
                txtAddress03.setText(poClient.Address(pnCompany).Town().Province().getDescription());
                txtAddress04.setText(poClient.Address(pnCompany).Town().getDescription());
                txtAddress05.setText(poClient.Address(pnCompany).Barangay().getBarangayName());

                cbAddress01.setSelected(("1".equals((String) poClient.Address(pnCompany).getRecordStatus())));
                cbAddress02.setSelected(poClient.Address(pnCompany).isPrimaryAddress());
                cbAddress08.setSelected(poClient.Address(pnCompany).isLTMSAddress());
            }
        } catch (SQLException | GuanzonException e) {
            e.printStackTrace();
        }
        pbLoadingData = false;
    }

    private void loadContactPerson() {
        contactPerson_data.clear();
        if (poClient.getInstiContactCount() >= 0) {
            for (int lnCtr = 0; lnCtr < poClient.getInstiContactCount(); lnCtr++) {
                
                contactPerson_data.add(new ModelContactPerson(String.valueOf(lnCtr + 1),
                        poClient.InstiContact(lnCtr).getContactPersonName(),
                        poClient.InstiContact(lnCtr).getContactPersonPosition(),
                        poClient.InstiContact(lnCtr).getMailAddress()
                ));
                
                //set primary address the first item, only if size is 1
                if(poClient.getInstiContactCount()== 1 && lnCtr == 0){
                    poClient.InstiContact(0).isPrimaryContactPersion(true);
                }
            }
        }
        if (pnContactPerson < 0 || pnContactPerson
                >= contactPerson_data.size()) {
            if (!contactPerson_data.isEmpty()) {
                /* FOCUS ON FIRST ROW */
                tblSocMed.getSelectionModel().select(pnContactPerson);
                tblSocMed.getFocusModel().focus(pnContactPerson);
                pnContactPerson = tblSocMed.getSelectionModel().getSelectedIndex();
            }
            getSelectedContactPerson();
        } else {
            /* FOCUS ON THE ROW THAT pnRowDetail POINTS TO */
            tblSocMed.getSelectionModel().select(pnContactPerson);
            tblSocMed.getFocusModel().focus(pnContactPerson);
            getSelectedContactPerson();
        }
    }

    private void getSelectedContactPerson() {
        
        pbLoadingData = true;
        
        if (poClient.getInstiContactCount() > 0) {
            txtContact00.setText(poClient.InstiContact(pnContactPerson).getContactPersonName());
            txtContact01.setText(poClient.InstiContact(pnContactPerson).getContactPersonPosition());
            txtContact02.setText(poClient.InstiContact(pnContactPerson).getMobileNo());
            txtContact03.setText(poClient.InstiContact(pnContactPerson).getLandlineNo());
            txtContact04.setText(poClient.InstiContact(pnContactPerson).getFaxNo());
            txtContact05.setText(poClient.InstiContact(pnContactPerson).getMailAddress());

            cbContact01.setSelected(("1".equals(poClient.InstiContact(pnContactPerson).getRecordStatus())));
            cbContact02.setSelected(poClient.InstiContact(pnContactPerson).isPrimaryContactPersion());

        }
        pbLoadingData = false;
    }

    private void initCompanyCheckbox() {
        CheckBox[] cbAddressCheckboxes = {cbAddress01, cbAddress02, cbAddress08};

        for (CheckBox checkbox : cbAddressCheckboxes) {
            // Capture the current checkbox
            checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!pbLoaded) {
                        return;
                    }

                    JSONObject loJSON;
                    String id = checkbox.getId();
                    String numberPart = id.substring(id.length() - 2);

                    try {
                        int number = Integer.parseInt(numberPart);
                        switch (number) {
                            case 1: //
                                loJSON = poClient.Address(pnCompany).setRecordStatus(newValue ? "1" : "0");
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
                                getSelectedAddress();
                                break;
                            case 2: // Primary Address || Restricted to 1 Primary Address
                                poClient.Address(pnCompany).isPrimaryAddress(newValue);
                                
                                //initialize full primary address to client master
                                String lshouseno = poClient.Address(pnCompany).getHouseNo() == null || poClient.Address(pnCompany).getHouseNo().isEmpty() ? "" : poClient.Address(pnCompany).getHouseNo() + " ";
                                String lsaddress = poClient.Address(pnCompany).getAddress() == null || poClient.Address(pnCompany).getAddress().isEmpty() ? "" : poClient.Address(pnCompany).getAddress();
                                String lsbrgy = poClient.Address(pnCompany).Barangay().getBarangayName() == null || poClient.Address(pnCompany).Barangay().getBarangayName().isEmpty() ? "" : ", " + poClient.Address(pnCompany).Barangay().getBarangayName();
                                String lscity = poClient.Address(pnCompany).Town().getDescription() == null || poClient.Address(pnCompany).Town().getDescription().isEmpty() ? " " : ", " + poClient.Address(pnCompany).Town().getDescription();
                                String lsprovince = poClient.Address(pnCompany).Town().Province().getDescription() == null || poClient.Address(pnCompany).Town().Province().getDescription().isEmpty() ? " " : " " + poClient.Address(pnCompany).Town().Province().getDescription();
            
                                poClient.getModel().setAdditionalInfo(lshouseno + lsaddress + lsbrgy + lscity + lsprovince);

                                if (!pbLoadingData) {
                                    for (int in = 0; in < poClient.getAddressCount(); in++) {
                                        if (in != pnCompany) {
                                            poClient.Address(in).isPrimaryAddress(false);
                                        }
                                    }
                                }
                                break;

                            case 8: // LTMS
                                loJSON = poClient.Address(pnCompany).isLTMSAddress(newValue);
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
                                break;
                            default:
                                System.out.println("Unknown checkbox selected");
                                break;
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(InstitutionNewController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GuanzonException ex) {
                        Logger.getLogger(InstitutionNewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    private void initContactPersonCheckbox() {
        
        CheckBox[] cbMobileCheckboxes = {cbContact01, cbContact02};
        
        for (CheckBox checkbox : cbMobileCheckboxes) {
            
            // Capture the current checkbox
            checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!pbLoaded) {
                        return;
                    }

                    JSONObject loJSON;
                    String id = checkbox.getId();
                    String numberPart = id.substring(id.length() - 2);

                    try {
                        int number = Integer.parseInt(numberPart);
                        switch (number) {
                            case 1: // Active
                                loJSON = poClient.InstiContact(pnContactPerson).setRecordStatus(checkbox.isSelected() ? "1" : "0");
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
                                break;
                                
                            case 2: // Primary
                                loJSON = poClient.InstiContact(pnContactPerson).isPrimaryContactPersion(checkbox.isSelected() ? true : false);
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
                                
                                if (!pbLoadingData) {
                                    for (int in = 0; in < poClient.getInstiContactCount(); in++) {
                                        if (in != pnContactPerson) {
                                            poClient.InstiContact(in).isPrimaryContactPersion(false);
                                        }
                                    }
                                }
                                break;

                            default:
                                System.out.println("Unknown checkbox selected");
                                break;
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            });
        }
    }
   
    public Stage getStage() {
        return (Stage) AnchorMain.getScene().getWindow();
    }
}
