package org.guanzon.cas.client.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.guanzon.appdriver.agent.ShowMessageFX;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.cas.client.Client;
import org.guanzon.cas.client.Client_Master;
import org.guanzon.cas.client.account.GlobalVariables;
import org.json.simple.JSONObject;

public class IndividualNewController {

    private final String pxeModuleName = "Individual New";
    private GRider oApp;
    private Client_Master oTrans;
//    private JSONObject poJSON;
    private int pnEditMode;

    static GRider instance;
    static Client record;
    static LogWrapper logWrapper;

    // private ObservableList<ModelInstitutionalContactPerson> contact_data = FXCollections.observableArrayList();
    private int pnContact = 0;
    private String oTransnox = "";

    private boolean state = false;
    private boolean pbLoaded = false;

    @FXML
    private AnchorPane AnchorMain;

    @FXML
    private TextField txtField02;

    @FXML
    private TextField txtField01;

    @FXML
    private TextArea txtField03;

    @FXML
    private TextField personalinfo02;

    @FXML
    private TextField personalinfo03;

    @FXML
    private TextField personalinfo04;

    @FXML
    private TextField personalinfo05;

    @FXML
    private TextField personalinfo12;

    @FXML
    private ComboBox personalinfo09;

    @FXML
    private ComboBox personalinfo10;

    @FXML
    private TextField personalinfo06;

    @FXML
    private DatePicker personalinfo07;

    @FXML
    private TextField personalinfo08;

    @FXML
    private TextField personalinfo11;

    @FXML
    private TextField personalinfo13;

    @FXML
    private TextField personalinfo14;

    @FXML
    private TextField personalinfo15;

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
    private TextField AddressField01;

    @FXML
    private TextField AddressField02;

    @FXML
    private TextField AddressField03;

    @FXML
    private TextField AddressField04;

    @FXML
    private TextField AddressField05;

    @FXML
    private TextField AddressField06;

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
    private CheckBox cbAddress01;

    @FXML
    private CheckBox cbAddress02;

    @FXML
    private Button btnAddAddress;

    @FXML
    private Button btnDelAddress;

    @FXML
    private TextField AddressField031;

    @FXML
    private ComboBox cmbMobile01;

    @FXML
    private ComboBox cmbMobile02;

    @FXML
    private TextField txtMobile01;

    @FXML
    private CheckBox cbMobileNo02;

    @FXML
    private CheckBox cbMobileNo01;

    @FXML
    private Button btnAddMobile;

    @FXML
    private Button btnDelMobile;

    @FXML
    private Label lblMobileStat;

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
    private ComboBox cmbEmail01;

    @FXML
    private TextField mailFields01;

    @FXML
    private CheckBox cbEmail02;

    @FXML
    private CheckBox cbEmail01;

    @FXML
    private Button btnAddEmail;

    @FXML
    private Button btnDelEmail;

    @FXML
    private Label lblEmailStat;

    @FXML
    private TableView tblEmail;

    @FXML
    private TableColumn indexEmail01;

    @FXML
    private TableColumn indexEmail02;

    @FXML
    private TableColumn indexEmail03;

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
    private Label lblSocMedStat;

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

    @FXML
    private HBox hbButtons;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnExit;

    @FXML
    private FontAwesomeIconView glyphExit;

    @FXML
    void CheckMailStatus_Clicked(MouseEvent event) {

    }

    @FXML
    void CheckPrimaryEmail_Clicked(MouseEvent event) {

    }

    @FXML
    void CheckPrimary_Clicked(MouseEvent event) {

    }

    @FXML
    void CheckSocMedtatus_Clicked(MouseEvent event) {

    }

    @FXML
    void CheckStatus_Clicked(MouseEvent event) {

    }

    @FXML
    void cbAddress01_Clicked(MouseEvent event) {

    }

    @FXML
    void cbAddress02_Clicked(MouseEvent event) {

    }

    @FXML
    void cbAddress03_Clicked(MouseEvent event) {

    }

    @FXML
    void cbAddress05_Clicked(MouseEvent event) {

    }

    @FXML
    void cbAddress06_Clicked(MouseEvent event) {

    }

    @FXML
    void cbAddress07_Clicked(MouseEvent event) {

    }

    @FXML
    void cbAddress08_Clicked(MouseEvent event) {

    }

//    @FXML
//    void closeForm(ActionEvent event) {
//
//    }
    private void closeForm() {
        btnExit.setOnAction(event -> closeForm());
    }

    @FXML
    void tblAddress_Clicked(MouseEvent event) {

    }

    @FXML
    void tblMobile_Clicked(MouseEvent event) {

    }

    @FXML
    void tblSocMed_Clicked(MouseEvent event) {

    }

    private void initButton() {
        btnSave.setOnAction(this::handleButtonAction);
        btnCancel.setOnAction(this::handleButtonAction);
        btnExit.setOnAction(event -> closeForm());

    }

    private void handleButtonAction(javafx.event.ActionEvent event) {
        Object source = event.getSource();
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            switch (clickedButton.getId()) {
                case "btnClose":
                    break;
                case "btnSave":
                    break;
                default:
                    break;
            }
        }
    }

    public void initialize(URL location, ResourceBundle resources) {

//               System.setProperty("sys.default.path.temp", "D:/GGC_Maven_Systems/temp/");
//        System.setProperty("sys.default.path.metadata", "D:/GGC_Maven_Systems/config/metadata/new/");
//
//        instance = MiscUtil.Connect();
//        logWrapper = new LogWrapper("CAS", System.getProperty("sys.default.path.temp") + "cas-error.log");
//        
//        record = new Client(instance, "", null);
//        
//        
//        if (oTransnox == null || oTransnox.isEmpty()) { // Check if oTransnox is null or empty
//            pnEditMode = EditMode.ADDNEW;
//        }
//        
//        initButton();
    }

    public void setGRider(GRider foValue) {
        oApp = foValue;
    }

}
