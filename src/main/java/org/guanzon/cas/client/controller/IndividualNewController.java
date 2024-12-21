package org.guanzon.cas.client.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import com.ibm.icu.impl.Assert;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.guanzon.appdriver.agent.ShowMessageFX;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.ClientType;
import org.guanzon.cas.client.Client;
import org.guanzon.cas.client.Client_Master;
import org.json.simple.JSONObject;

/**
 * FXML Controller class
 *
 * @author User
 */
public class IndividualNewController implements Initializable {

    private final String pxeModuleName = "Individual New";
    private GRider oApp;
    private Client oTrans;
//    private JSONObject poJSON;
    private int pnEditMode;

    static LogWrapper logWrapper;

    // private ObservableList<ModelInstitutionalContactPerson> contact_data = FXCollections.observableArrayList();
    private int pnContact = 0;
    private String oTransnox = "";

    private boolean state = false;
    private boolean pbLoaded = false;
    private double offsetX;
    private double offsetY;

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
    private Tab SocialMedia;
    @FXML
    private Tab PersonalInfo;
    @FXML
    private Tab Mobile;
    @FXML
    private Tab Email;
    @FXML
    private TabPane TabPane;

    private void closeForm(javafx.event.ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    private void initButton() {
        btnSave.setOnAction(this::handleButtonAction);
        btnCancel.setOnAction(this::handleButtonAction);
        btnExit.setOnAction(this::closeForm);
    }

    private void handleButtonAction(javafx.event.ActionEvent event) {
        String tabText = "";
        Tab selectedTab = TabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            tabText = selectedTab.getText();
            ShowMessageFX.OkayCancel(null, "", tabText);
        }
        Object source = event.getSource();
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            switch (clickedButton.getId()) {

                case "btnSave":
                    switch (tabText) {
                        case "PersonalInfo":
                            break;
                        case "Address":
                            break;
                        case "Mobile":
                            break;
                        case "Email":
                            break;
                    }

                    break;
                case "btnClose":

                    switch (tabText) {
                        case "PersonalInfo":
                            break;
                        case "Address":
                            break;
                        case "Mobile":
                            break;
                        case "Email":
                            break;
                    }

                    break;
                default:
                    break;
            }
        }
    }

    private void personalinfo_KeyPressed(KeyEvent event) {
        TextField personalinfo = (TextField) event.getSource();
        int lnIndex = Integer.parseInt(((TextField) event.getSource()).getId().substring(12, 14));
        String lsValue = (personalinfo.getText() == null ? "" : personalinfo.getText());
        JSONObject poJson;
        switch (event.getCode()) {
            case F3:
                switch (lnIndex) {
                    case 6:
                        /*search town*/
                        poJson = new JSONObject();
                        poJson = oTrans.Master().searchCitizenship(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            personalinfo06.clear();
                        }
                        personalinfo06.setText((String) poJson.get("sNational"));
                        break;
                    case 8:
                        poJson = new JSONObject();
                        poJson = oTrans.Master().searchBirthPlace(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            personalinfo08.clear();
                        }
                        personalinfo08.setText((String) poJson.get("xBrthPlce"));
                        oTrans.Master().getModel().setBirthPlaceId(lsValue);
                        break;
                }
        }
        switch (event.getCode()) {
            case ENTER:
                CommonUtils.SetNextFocus(personalinfo);
            case DOWN:
                CommonUtils.SetNextFocus(personalinfo);
                break;
            case UP:
                CommonUtils.SetPreviousFocus(personalinfo);
        }
    }

    final ChangeListener<? super Boolean> personalinfo_Focus = (o, ov, nv) -> {
        if (!pbLoaded) {
            return;
        }
        TextField txtPersonalInfo = (TextField) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtPersonalInfo.getId().substring(10, 12));
        String lsValue = (txtPersonalInfo.getText() == null ? "" : txtPersonalInfo.getText());
        JSONObject jsonObject = new JSONObject();
        if (lsValue == null) {
            return;
        }
        if (!nv) {
            /*Lost Focus*/
            switch (lnIndex) {
                case 2:
                    /*Last name*/
                    oTrans.Master().getModel().setLastName(lsValue);
                    break;
                case 3:
                    /*First name*/
                  oTrans.Master().getModel().setFirstName(lsValue);
                    break;
                case 4:
                    /*Middle name*/
                   oTrans.Master().getModel().setFirstName(lsValue);
                    break;
                case 5:
                    /*Suffix*/
                    oTrans.Master().getModel().setSuffixName(lsValue);
                    break;
                case 6:
                    /*Citizenship*/
                    oTrans.Master().getModel().setCitizenshipId(lsValue);
                    break;
                case 7:
                    /*Birth Date*/ // DATE TIME
                    // oTrans.getModel().setBirthDate(lsValue);
//                    if (lsValue.matches("\\d{11}")) {
//                        oTrans.setInsContact(pnContact, "sMobileNo", lsValue);
//                    } else {
//                        ShowMessageFX.OkayCancel(null, pxeModuleName, "Contact number must be exactly 11 digits.");
//                        txtContact.requestFocus();
//                        break;
//                    }
                    break;
                case 8:
                    /*Birth Place*/
                    oTrans.Master().getModel().setBirthPlaceId(lsValue);
                case 9:
                    /*Gender*/ //COMBOBOX
                    //oTrans.getModel().setGender(lsValue);
                    break;
                case 10:
                    /*Civil Status*/ //NO CIVIL STATUS
                    //  oTrans.getModel().
                    break;
                case 11:
                    /*Spouse*/
                   oTrans.Master().getModel().setSpouseId(lsValue);
                    break;
                case 12:
                    /*Mother's Maiden Name*/
                    oTrans.Master().getModel().setMothersMaidenName(lsValue);
                    break;
                case 13:
                    /*TIN No*/
                    //oTrans.getModel().setT
                    break;
                case 14:
                    /*LTO ID*/
                    //  oTrans.getModel()
                    break;
                case 15:
                    /*National ID */
                    //oTrans.setInsContact(pnContact, "sEMailAdd", lsValue);
                    break;
            }
            // loadContctPerson();
        } else {
            // txtContact.selectAll();
        }
    };

    private void InitPersonalInfoTextFields() {
        /*MOBILE INFO FOCUSED PROPERTY*/
        personalinfo02.focusedProperty().addListener(personalinfo_Focus);
        personalinfo03.focusedProperty().addListener(personalinfo_Focus);
        personalinfo04.focusedProperty().addListener(personalinfo_Focus);
        personalinfo05.focusedProperty().addListener(personalinfo_Focus);
        // personalinfo06.focusedProperty().addListener(personalinfo_Focus);
        //   personalinfo07.focusedProperty().addListener(personalinfo_Focus);
        //  personalinfo08.focusedProperty().addListener(personalinfo_Focus);
        //   personalinfo09.focusedProperty().addListener(personalinfo_Focus);
        //    personalinfo10.focusedProperty().addListener(personalinfo_Focus);
        personalinfo12.focusedProperty().addListener(personalinfo_Focus);
        personalinfo13.focusedProperty().addListener(personalinfo_Focus);
        personalinfo14.focusedProperty().addListener(personalinfo_Focus);
        personalinfo15.focusedProperty().addListener(personalinfo_Focus);

        personalinfo06.setOnKeyPressed(this::personalinfo_KeyPressed);
        personalinfo08.setOnKeyPressed(this::personalinfo_KeyPressed);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.setProperty("sys.default.path.temp", "D:/GGC_Maven_Systems/temp/");
        System.setProperty("sys.default.path.metadata", "D:/GGC_Maven_Systems/config/metadata/new/");
        // ShowMessageFX.OkayCancel(null, "", "samp");
//        JSONObject poJson = new JSONObject();
//        poJson = oTrans.getModel().setClientType(ClientType.INDIVIDUAL);
//        if ("error".equals((String) poJson.get("result"))) {
//            Assert.fail((String) poJson.get("message"));
//        }  
        oApp = MiscUtil.Connect();
        oTrans = new Client(oApp, "", null);
        
        
         oTrans.Master().getModel().setClientType(ClientType.INDIVIDUAL);
     

      
        
        initButton();
        InitPersonalInfoTextFields();

    }

}
