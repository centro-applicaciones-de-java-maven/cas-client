package org.guanzon.cas.client.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import com.ibm.icu.impl.Assert;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
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
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.ClientType;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.client.Client;
import org.guanzon.cas.client.Client_Mail;
import org.guanzon.cas.client.Client_Master;
import org.guanzon.cas.client.Client_Mobile;
import org.guanzon.cas.client.models.ModelAddress;
import org.guanzon.cas.client.models.ModelEmail;
import org.guanzon.cas.client.models.ModelMobile;
import org.guanzon.cas.client.models.ModelSocialMedia;
import org.guanzon.cas.controller.ScreenInterface;
import org.guanzon.cas.parameter.TownCity;
import org.guanzon.cas.parameters.Barangay;
import org.guanzon.cas.parameters.Country;
import org.guanzon.cas.parameters.Province;
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
    private int pnEditMode;
    static LogWrapper logWrapper;
    private String oTransnox = "";

    private boolean state = false;
    private boolean pbLoaded = false;

    private ObservableList<ModelAddress> address_data = FXCollections.observableArrayList();
    private ObservableList<ModelMobile> mobile_data = FXCollections.observableArrayList();
    private ObservableList<ModelEmail> email_data = FXCollections.observableArrayList();
    private ObservableList<ModelSocialMedia> socialmedia_data = FXCollections.observableArrayList();

    ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female", "Others");
    ObservableList<String> civilstatus = FXCollections.observableArrayList("Single", "Married", "Divorced", "Widowed");
    ObservableList<String> mobileOwn = FXCollections.observableArrayList("Personal", "Office", "Others");
    ObservableList<String> mobileType = FXCollections.observableArrayList("Mobile No", "Tel No", "Fax No");
    ObservableList<String> emailOwn = FXCollections.observableArrayList("Personal", "Office", "Others");
    ObservableList<String> socialTyp = FXCollections.observableArrayList("Facebook", "Instagram", "Twitter");

    int pnPersonalInfo = 0;
    int pnAddress = 0;
    int pnMobile = 0;
    int pnEmail = 0;
    int pnSocialMedia = 0;

    @FXML
    private AnchorPane AnchorMain;

    @FXML
    private TextField txtField02;

    @FXML
    private TextField txtField01;

    @FXML
    private TextArea txtField03;

    @FXML
    private TabPane TabPane;

    @FXML
    private Tab PersonalInfo;

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
    private TextField AddressField01;

    @FXML
    private TextField AddressField02;

    @FXML
    private TextField AddressField04;

    @FXML
    private TextField AddressField05;

    @FXML
    private TextField AddressField06;

    @FXML
    private TextField AddressField07;

    @FXML
    private CheckBox cbAddress03;

    @FXML
    private CheckBox cbAddress05;

    @FXML
    private CheckBox cbAddress06;

    @FXML
    private CheckBox cbAddress04;

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
    private TextField AddressField03;

    @FXML
    private Tab Mobile;

    @FXML
    private ComboBox cmbMobile01;

    @FXML
    private ComboBox cmbMobile02;

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
    private Tab Email;

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
    private AnchorPane draggablePane;

    @FXML
    private Button btnExit;

    @FXML
    private FontAwesomeIconView glyphExit;
//
//
//     @Override
//    public void setGRider(GRider foValue) {
//        oApp = foValue;
//    }

    private void closeForm(javafx.event.ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    private void clearAddress() {
        // Arrays of TextFields grouped by sections
//        TextField[][] allFields = {
//            {AddressField01, AddressField02, AddressField03, AddressField04,
//                AddressField05, AddressField06}
//        };
//
//        // Loop through each array of TextFields and clear them
//        for (TextField[] fields : allFields) {
//            for (TextField field : fields) {
//                field.clear();
//            }
//        }
        AddressField01.clear();
        AddressField02.clear();
        AddressField03.clear();
        AddressField04.clear();
        AddressField05.clear();
        AddressField06.clear();
        cbAddress01.setSelected(true);
        cbAddress02.setSelected(false);
        cbAddress03.setSelected(false);
        cbAddress04.setSelected(false);
        cbAddress05.setSelected(false);
        cbAddress06.setSelected(false);
        cbAddress07.setSelected(false);
        cbAddress08.setSelected(false);

    }

    private void clearMaster() {
        txtField01.clear();
        txtField02.clear();
        txtField03.clear();
    }

    private void clearMobile() {
        txtMobile01.clear();
        cmbMobile01.setItems(mobileOwn);
        cmbMobile01.getSelectionModel().select(0);

        cmbMobile02.setItems(mobileType);
        cmbMobile02.getSelectionModel().select(0);

        cbMobileNo01.setSelected(true);
        cbMobileNo02.setSelected(false);

    }

    private void clearEmail() {
        mailFields01.clear();
        cmbEmail01.setItems(emailOwn);
        cmbEmail01.getSelectionModel().select(0);
        cbEmail01.setSelected(true);
        cbEmail02.setSelected(false);

    }

    private void clearSocMed() {
        txtSocial01.clear();
        txtSocial02.clear();
        cmbSocMed01.setItems(socialTyp);
        cmbSocMed01.getSelectionModel().select(0);
        cbSocMed01.setSelected(true);
    }

    private void initButton() {
        btnSave.setOnAction(this::handleButtonAction);
        btnCancel.setOnAction(this::handleButtonAction);
        btnExit.setOnAction(this::closeForm);

        btnAddAddress.setOnAction(this::handleButtonAction);
        btnDelAddress.setOnAction(this::handleButtonAction);

        btnAddEmail.setOnAction(this::handleButtonAction);
        btnDelEmail.setOnAction(this::handleButtonAction);

        btnAddMobile.setOnAction(this::handleButtonAction);
        btnDelMobile.setOnAction(this::handleButtonAction);

        btnAddSocMed.setOnAction(this::handleButtonAction);
        btnDelSocMed.setOnAction(this::handleButtonAction);

    }

    private void handleButtonAction(javafx.event.ActionEvent event) {
        String tabText = "";
        Tab selectedTab = TabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            tabText = selectedTab.getId();
        }
        Object source = event.getSource();
        if (source instanceof Button) {
            Button clickedButton = (Button) source;
            switch (clickedButton.getId()) {

                case "btnSave":

                    JSONObject loJSON;
                    System.out.println("data files " + oTrans.Master().getModel().getLastName() + " ||"
                            + oTrans.Master().getModel().getFirstName() + " ||"
                            + oTrans.Master().getModel().getMiddleName() + " ||"
                            + oTrans.Master().getModel().getSuffixName() + " ||"
                            + oTrans.Master().getModel().getCitizenshipId() + " ||"
                            + oTrans.Master().getModel().getBirthDate() + " ||"
                            + oTrans.Master().getModel().getBirthPlaceId() + " ||"
                            + "gender: " + oTrans.Master().getModel().getGender() + " ||"
                            + oTrans.Master().getModel().getCivilStatus() + " ||"
                            + oTrans.Master().getModel().getSpouseId() + " ||"
                            + oTrans.Master().getModel().getMothersMaidenName() + " ||"
                            + oTrans.Master().getModel().getTaxIdNumber() + " ||"
                            + oTrans.Master().getModel().getLTOClientId() + " ||"
                            + oTrans.Master().getModel().getPhNationalId() + " ||"
                    );

                    loJSON = oTrans.Master().isEntryOkay();
                    if ("error".equals((String) loJSON.get("result"))) {
                        ShowMessageFX.Information((String) loJSON.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }

                    loJSON = oTrans.Save();
                    if ("error".equals((String) loJSON.get("result"))) {
                        ShowMessageFX.Information((String) loJSON.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    } else {
                        ShowMessageFX.OkayCancel(null, "", "Successfully saved!");
                    }
//                    switch (tabText) {
//                        
//                        case "PersonalInfo":
//
//                            break;
//                        case "Address":
//                            break;
//                        case "Mobile":
//                            break;
//                        case "Email":
//                            break;
//                    }
//                    break;
//                case "btnClose":
//                    switch (tabText) {
//                        case "PersonalInfo":
//                            break;
//                        case "Address":
//                            break;
//                        case "Mobile":
//                            break;
//                        case "Email":
//                            break;
//                    }
                    break;

                case "btnCancel":
                    if (ShowMessageFX.YesNo("Do you really want to cancel this record? \nAny data collected will not be kept.", "Computerized Acounting System", pxeModuleName)) {
                        clearMaster();
                        clearAddress();
                        clearEmail();
                        clearMobile();
                        clearSocMed();

                        // Initialize the Client_Master transaction
                        oTrans.Master().getModel().setClientType(ClientType.INDIVIDUAL);
                        pnEditMode = EditMode.UNKNOWN;
                    }

                    break;
                case "btnAddAddress":
                    if (oTrans.getAddressCount() > 0) {
                        JSONObject addObjMail = oTrans.Address(pnAddress).isEntryOkay();
                        if ("error".equals((String) addObjMail.get("result"))) {
                            ShowMessageFX.Information((String) addObjMail.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                    }
                    clearAddress();
                    JSONObject addObjAddress = oTrans.addAddress();
                     oTrans.Address(pnAddress).getModel().setRecordStatus("0");
                    if ("error".equals((String) addObjAddress.get("result"))) {
                        ShowMessageFX.Information((String) addObjAddress.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }
                    oTrans.Address(pnAddress).getModel().setClientId(oTrans.Master().getModel().getClientId());
                    pnAddress = oTrans.getAddressCount() - 1;
                    tblAddress.getSelectionModel().select(pnAddress + 1);
                    loadRecordAddress();
//                       initAddressGrid();
                    break;

                case "btnAddEmail":
                    //    oTrans.Mail(pnEmail).getModel().setEmailId("");

                    if (oTrans.getMailCount() > 0) {
                        //  oTrans.Mail(pnEmail).getModel().setEmailId("");
                        JSONObject addObjMail = oTrans.Mail(pnEmail).isEntryOkay();
                        if ("error".equals((String) addObjMail.get("result"))) {
                            ShowMessageFX.Information((String) addObjMail.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                    }
                    clearEmail();
                    JSONObject addObjMail = oTrans.addMail();
                    oTrans.Mail(pnEmail).getModel().setRecordStatus("0");
                    oTrans.Mail(pnEmail).getModel().setEmailId("");
                    oTrans.Mail(pnEmail).getModel().setClientId(oTrans.Master().getModel().getClientId());
                    System.out.println((String) addObjMail.get("message"));
                    if ("error".equals((String) addObjMail.get("result"))) {
                        ShowMessageFX.Information((String) addObjMail.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }

                    mailFields01.clear();
                    pnEmail = oTrans.getMailCount() - 1;
                    tblEmail.getSelectionModel().select(pnEmail + 1);
                    loadRecordEmail();
                    break;
                case "btnAddMobile":
                    if (oTrans.getMobileCount() > 0) {
                        JSONObject addObjMobile = oTrans.Mobile(pnMobile).isEntryOkay();
                        if ("error".equals((String) addObjMobile.get("result"))) {
                            ShowMessageFX.Information((String) addObjMobile.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                    }
                    clearMobile();
                    System.out.println(oTrans.getMobileCount());
                    JSONObject addObj = oTrans.addMobile();
                    oTrans.Mobile(pnMobile).getModel().setRecordStatus("0");
                    oTrans.Mobile(pnMobile).getModel().setClientId(oTrans.Master().getModel().getClientId());
                    System.out.println((String) addObj.get("message"));
                    if ("error".equals((String) addObj.get("result"))) {
                        ShowMessageFX.Information((String) addObj.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }
                    txtMobile01.clear();
                    pnMobile = oTrans.getMobileCount() - 1;
                    tblMobile.getSelectionModel().select(pnMobile + 1);
                    loadRecordMobile();
                    break;
                case "btnAddSocMed":
                    if (oTrans.getSocMedCount() > 0) {
                        JSONObject addObjSocMed = oTrans.SocialMedia(pnSocialMedia).isEntryOkay();
                        if ("error".equals((String) addObjSocMed.get("result"))) {
                            ShowMessageFX.Information((String) addObjSocMed.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                    }
                    clearSocMed();
                    JSONObject addSocMed = oTrans.addSocialMedia();
                    oTrans.SocialMedia(pnSocialMedia).getModel().setRecordStatus("0");
                    oTrans.SocialMedia(pnSocialMedia).getModel().setClientId(oTrans.Master().getModel().getClientId());
                    System.out.println((String) addSocMed.get("message"));
                    if ("error".equals((String) addSocMed.get("result"))) {
                        ShowMessageFX.Information((String) addSocMed.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }
                    txtSocial01.clear();
                    txtSocial02.clear();
                    pnSocialMedia = oTrans.getSocMedCount() - 1;

                    tblSocMed.getSelectionModel().select(pnSocialMedia + 1);
                    loadRecordSocialMedia();
                    break;

                case "btnDelAddress":
                    clearAddress();
                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
//                        oTrans.Address(pnAddress).deleteRecord();
                        JSONObject loJson = oTrans.deleteAddress(pnAddress);
                        if ("error".equals((String) loJson.get("result"))) {
                            ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                        if (oTrans.getAddressCount() <= 0) {
                            pnAddress = oTrans.getAddressCount();
                        } else {
                            pnAddress = oTrans.getAddressCount() - 1;
                        }
                        loadRecordAddress();
                        AddressField01.requestFocus();
                    }
                    break;
                case "btnDelMobile":
                    clearMobile();
                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
                        JSONObject loJson = oTrans.deleteMobile(pnMobile);
                        if ("error".equals((String) loJson.get("result"))) {
                            ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                        if (oTrans.getMobileCount() <= 0) {
                            pnMobile = oTrans.getMobileCount();
                        } else {
                            pnMobile = oTrans.getMobileCount() - 1;
                        }

                        loadRecordMobile();
                    }
                    break;
                case "btnDelEmail":
                    clearEmail();
                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
                        JSONObject loJson = oTrans.deleteEmail(pnEmail);
                        if ("error".equals((String) loJson.get("result"))) {
                            ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }

                        if (oTrans.getMailCount() <= 0) {
                            pnEmail = oTrans.getMailCount();
                        } else {
                            pnEmail = oTrans.getMailCount() - 1;
                        }
                        loadRecordEmail();
                    }
                    break;
                case "btnDelSocMed":
                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
                        JSONObject loJson = oTrans.deleteSocialMedia(pnSocialMedia);
                        if ("error".equals((String) loJson.get("result"))) {
                            ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                        if (oTrans.getSocMedCount() <= 0) {
                            pnSocialMedia = oTrans.getSocMedCount();
                        } else {
                            pnSocialMedia = oTrans.getSocMedCount() - 1;
                        }

                        loadRecordSocialMedia();
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
                        /*search country for citizenship*/
                        poJson = new JSONObject();
//                        poJson = oTrans.Master().searchCitizenship(lsValue, false);

                        Country loCountry = new Country(oApp, true);
                        loCountry.setRecordStatus("1");
                        poJson = loCountry.searchRecord(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            personalinfo06.clear();
                        } else {
                            personalinfo06.setText((String) loCountry.getModel().getNational());
                            oTrans.Master().getModel().setCitizenshipId((String) loCountry.getModel().getCountryCode());
                        }
                        break;
                    case 8:
                        /*search birthplace*/
                        poJson = new JSONObject();
                        poJson = oTrans.Master().searchBirthPlace(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            personalinfo08.clear();
                        } else {
                            personalinfo08.setText((String) poJson.get("xBrthPlce"));
                            oTrans.Master().getModel().setBirthPlaceId((String) poJson.get("sTownIDxx"));
                        }
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
        int lnIndex = Integer.parseInt(txtPersonalInfo.getId().substring(12, 14));
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
                    oTrans.Master().getModel().setMiddleName(lsValue);
                    break;
                case 5:
                    /*Suffix*/
                    oTrans.Master().getModel().setSuffixName(lsValue);
                    break;
                case 6:
                    /*Citizenship*/
//                    oTrans.Master().getModel().setCitizenshipId(lsValue);
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
                    oTrans.Master().getModel().setTaxIdNumber(lsValue);
                    break;
                case 14:
                    /*LTO ID*/
                    oTrans.Master().getModel().setLTOClientId(lsValue);
                    break;
                case 15:
                    /*National ID */
                    oTrans.Master().getModel().setPhNationalId(lsValue);
                    break;

            }
            // loadContctPerson();
            loadRecordPersonalInfo();

        } else {
            // txtContact.selectAll();
        }
    };

    private void address_KeyPressed(KeyEvent event) {
        TextField address = (TextField) event.getSource();
        int lnIndex = Integer.parseInt(((TextField) event.getSource()).getId().substring(12, 14));
        String lsValue = (address.getText() == null ? "" : address.getText());
        String lsProvince = "";
        JSONObject poJson;
        switch (event.getCode()) {
            case F3:
                switch (lnIndex) {
                    case 3:
                        /*search province*/
                        poJson = new JSONObject();
                        Province loProvince = new Province(oApp, true);
                        loProvince.setRecordStatus("1");
                        poJson = loProvince.searchRecord(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            AddressField03.clear();
                        } else {
                            AddressField03.setText((String) loProvince.getModel().getProvinceName());

                            oTrans.Address(pnAddress).getModel().Town().Province().setProvinceId(loProvince.getModel().getProvinceID());
                            oTrans.Address(pnAddress).getModel().Town().Province().getProvinceId();

                            oTrans.setProvinceID_temp(loProvince.getModel().getProvinceID());
                        }

                        break;
                    case 4:
                        /*search city*/
                        poJson = new JSONObject();
                        //       poJson = oTrans.Master().SearchTownAddress(pnAddress, lsValue, false);
                        //locity
                        TownCity loTownCity = new TownCity();
                        loTownCity.setApplicationDriver(oApp);
                        loTownCity.setRecordStatus("1");
                        loTownCity.initialize();
                        try {
                            if (!oTrans.getProvinceID_temp().equalsIgnoreCase("")) {
                                poJson = loTownCity.searchRecord(lsValue, false, oTrans.getProvinceID_temp());
                            } else {
                                poJson = loTownCity.searchRecord(lsValue, false);
                            }
                        } catch (Exception e) {
                            poJson = loTownCity.searchRecord(lsValue, false);
                        }

                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            AddressField04.clear();
                        } else {
//                            loadRecordAddress
                            AddressField04.setText((String) loTownCity.getModel().getTownName());
                            oTrans.Address(pnAddress).getModel().setTownId(loTownCity.getModel().getTownId());
                            loadRecordAddress();

                        }
                        break;
                    case 5:
                        /*search barangay*/

//                        if (!oTrans.Address(pnAddress).getModel().getTownId().equals("")) {
//                            JSONObject loJSON = new JSONObject();
//                            loJSON.put("result", "error");
//                            loJSON.put("message", "Kindly choose the town or city first.");
//                            ShowMessageFX.Information((String) loJSON.get("message"), "Computerized Acounting System", pxeModuleName);
//                            break;
//                        }
                        poJson = new JSONObject();
                        Barangay loBarangay = new Barangay(oApp, true);
                        loBarangay.setRecordStatus("1");
                        poJson = loBarangay.searchRecord(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            AddressField05.clear();
                        }
                        AddressField05.setText(loBarangay.getModel().getBarangayName());
                        oTrans.Address(pnAddress).getModel().setBarangayId(loBarangay.getModel().getBarangayID());
                        oTrans.Address(pnAddress).getModel().setTownId(loBarangay.getModel().getTownID());
                        loadRecordAddress();
                        break;
                }
        }
        switch (event.getCode()) {
            case ENTER:
                CommonUtils.SetNextFocus(address);
            case DOWN:
                CommonUtils.SetNextFocus(address);
                break;
            case UP:
                CommonUtils.SetPreviousFocus(address);
        }
    }

    final ChangeListener<? super Boolean> master_Focus = (o, ov, nv) -> {
        JSONObject loJSON;
        if (!pbLoaded) {
            return;
        }
        TextField txtMaster = (TextField) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtMaster.getId().substring(txtMaster.getId().length() - 2));
        String lsValue = (txtMaster.getText() == null ? "" : txtMaster.getText());
        JSONObject jsonObject = new JSONObject();
        if (lsValue == null) {
            return;
        }
        if (!nv) {
            /*Lost Focus*/
            switch (lnIndex) {
                case 2:
                    /*Client Name should be company name*/
                    loJSON = oTrans.Master().getModel().setCompanyName(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    break;
                case 3:
                    /*Client Name should be company name*/
//                    loJSON = oTrans.Master().getModel().BirthTown().
//                    if ("error".equals((String) loJSON.get("result"))) {
//                        Assert.fail((String) loJSON.get("message"));
//                    }
                    break;
            }
            loadRecordMaster();

        } else {
            // txtContact.selectAll();
        }
    };

    final ChangeListener<? super Boolean> address_Focus = (o, ov, nv) -> {
        JSONObject loJSON;
        if (!pbLoaded) {
            return;
        }
        TextField txtPersonalInfo = (TextField) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtPersonalInfo.getId().substring(12, 14));
        String lsValue = (txtPersonalInfo.getText() == null ? "" : txtPersonalInfo.getText());
        JSONObject jsonObject = new JSONObject();
        if (lsValue == null) {
            return;
        }
        if (!nv) {
            /*Lost Focus*/
            switch (lnIndex) {
                case 1:
                    /*House No.*/
                    loJSON = oTrans.Address(pnAddress).getModel().setHouseNo(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    loadRecordAddress();
                    break;
                case 2:
                    /*Address*/
                    loJSON = oTrans.Address(pnAddress).getModel().setAddress(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    loadRecordAddress();
                    break;
                case 3:
                    /*Province*/
                    //leave blank as it is searchable 
//                    loJSON =  oTrans.Address(pnAddress).getModel().Town().setProvinceId(lsValue);
//                    if ("error".equals((String) loJSON.get("result"))) {
//                        Assert.fail((String) loJSON.get("message"));
//                    }
                    break;
                case 4:
                    /*City*/
//                    loJSON = oTrans.Address(pnAddress).getModel().setTownId(lsValue);
//                    if ("error".equals((String) loJSON.get("result"))) {
//                        Assert.fail((String) loJSON.get("message"));
//                    }
                    break;
                case 5:
                    /*Barangay*/
//                    loJSON = oTrans.Address(pnAddress).getModel().setBarangayId(lsValue);
//                    if ("error".equals((String) loJSON.get("result"))) {
//                        Assert.fail((String) loJSON.get("message"));
//                    }
                    break;
                case 6:
                    /*Latitude*/
                    loJSON = oTrans.Address(pnAddress).getModel().setLatitude(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    loadRecordAddress();
                    break;
                case 7:
                    /*Longitud*/
                    loJSON = oTrans.Address(pnAddress).getModel().setLongitude(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    loadRecordAddress();
                    break;

            }

        } else {
            // txtContact.selectAll();
        }
    };

    private void email_KeyPressed(KeyEvent event) {
        TextField address = (TextField) event.getSource();
        int lnIndex = Integer.parseInt(((TextField) event.getSource()).getId().substring(address.getId().length() - 2));
        String lsValue = (address.getText() == null ? "" : address.getText());
        JSONObject poJson;
        switch (event.getCode()) {
            case F3:
                switch (lnIndex) {
                    case 1:
                        /*search mobileNo*/
                        poJson = new JSONObject();
                        Client_Mail loclient_email = new Client_Mail();
                        loclient_email.setApplicationDriver(oApp);
                        loclient_email.setRecordStatus("1");
                        loclient_email.initialize();

                        poJson = loclient_email.searchRecord(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            mailFields01.clear();
                        }
                        mailFields01.setText((String) loclient_email.getModel().getMailAddress());
                        oTrans.Mail(pnEmail).getModel().setEmailId((String) loclient_email.getModel().getEmailId());
                        oTrans.Mail(pnEmail).getModel().setMailAddress((String) loclient_email.getModel().getMailAddress());
                        break;
                }
                loadRecordEmail();

        }
        switch (event.getCode()) {
            case ENTER:
                CommonUtils.SetNextFocus(address);
            case DOWN:
                CommonUtils.SetNextFocus(address);
                break;
            case UP:
                CommonUtils.SetPreviousFocus(address);
        }
    }

    private void mobile_KeyPressed(KeyEvent event) {
        TextField address = (TextField) event.getSource();
        int lnIndex = Integer.parseInt(((TextField) event.getSource()).getId().substring(9, 11));
        String lsValue = (address.getText() == null ? "" : address.getText());
        JSONObject poJson;
        switch (event.getCode()) {
            case F3:
                switch (lnIndex) {
                    case 1:
                        /*search mobileNo*/
                        poJson = new JSONObject();
                        Client_Mobile loclient_mobile = new Client_Mobile();
                        loclient_mobile.setApplicationDriver(oApp);
                        loclient_mobile.setRecordStatus("1");
                        loclient_mobile.initialize();
                        poJson = loclient_mobile.searchRecord(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            txtMobile01.clear();
                        }
                        txtMobile01.setText((String) loclient_mobile.getModel().getMobileNo());
                        oTrans.Mobile(pnMobile).getModel().setMobileNo((String) loclient_mobile.getModel().getMobileNo());
                        oTrans.Mobile(pnMobile).getModel().setMobileType((String) loclient_mobile.getModel().getMobileType());
                        oTrans.Mobile(pnMobile).getModel().setMobileNetwork((String) loclient_mobile.getModel().getMobileNetwork());
                        break;
                }
                loadRecordMobile();

        }
        switch (event.getCode()) {
            case ENTER:
                CommonUtils.SetNextFocus(address);
            case DOWN:
                CommonUtils.SetNextFocus(address);
                break;
            case UP:
                CommonUtils.SetPreviousFocus(address);
        }
    }

    final ChangeListener<? super Boolean> email_Focus = (o, ov, nv) -> {
        JSONObject loJSON;
        if (!pbLoaded) {
            return;
        }
        TextField txtEmail = (TextField) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtEmail.getId().substring(txtEmail.getId().length() - 2));
        String lsValue = (txtEmail.getText() == null ? "" : txtEmail.getText());
        JSONObject jsonObject = new JSONObject();
        if (lsValue == null) {
            return;
        }
        if (!nv) {
            /*Lost Focus*/
            switch (lnIndex) {
                case 1:
                    /*Email.*/
//                    loJSON = oTrans.Mail(pnEmail).getModel().setMailAddress(lsValue);
//                    if ("error".equals((String) loJSON.get("result"))) {
//                        Assert.fail((String) loJSON.get("message"));
//                    }

                    break;
            }
            // loadRecordEmail();

        } else {
            // txtContact.selectAll();
        }
    };

    final ChangeListener<? super Boolean> socialmedia_Focus = (o, ov, nv) -> {
        JSONObject loJSON;
        if (!pbLoaded) {
            return;
        }
        TextField txtEmail = (TextField) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtEmail.getId().substring(txtEmail.getId().length() - 2));
        String lsValue = (txtEmail.getText() == null ? "" : txtEmail.getText());
        JSONObject jsonObject = new JSONObject();
        if (lsValue == null) {
            return;
        }
        if (!nv) {
            /*Lost Focus*/
            switch (lnIndex) {
                case 1:
                    /*Account.*/
                    loJSON = oTrans.SocialMedia(pnSocialMedia).getModel().setAccount(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    break;
            }
            loadRecordSocialMedia();

        } else {
            // txtContact.selectAll();
        }
    };

    final ChangeListener<? super Boolean> txtAreaMaster_Focus = (o, ov, nv) -> {
        if (!pbLoaded) {
            return;
        }

        TextArea txtField = (TextArea) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(txtField.getId().length() - 2));
        String lsValue = txtField.getText();

        if (lsValue == null) {
            return;
        }
        JSONObject loJSON;
        if (!nv) {
            /*Lost Focus*/
            switch (lnIndex) {

                case 3://Address
//                    loJSON = oTrans.SocialMedia(pnSocialMedia).getModel().setRemarks(lsValue);
//                    if ("error".equals((String) loJSON.get("result"))) {
//                        System.err.println((String) loJSON.get("message"));
//                        ShowMessageFX.Information(null, pxeModuleName, (String) loJSON.get("message"));
//                        return;
//                    }
                    break;
            }
        } else {
            txtField.selectAll();
        }
    };

    final ChangeListener<? super Boolean> txtAreaSocialMedia_Focus = (o, ov, nv) -> {
        if (!pbLoaded) {
            return;
        }

        TextArea txtField = (TextArea) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(txtField.getId().length() - 2));
        String lsValue = txtField.getText();

        if (lsValue == null) {
            return;
        }
        JSONObject loJSON;
        if (!nv) {
            /*Lost Focus*/
            switch (lnIndex) {

                case 2://Remarks
                    loJSON = oTrans.SocialMedia(pnSocialMedia).getModel().setRemarks(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        System.err.println((String) loJSON.get("message"));
                        ShowMessageFX.Information(null, pxeModuleName, (String) loJSON.get("message"));
                        return;
                    }
                    break;
            }
            loadRecordSocialMedia();
        } else {
            txtField.selectAll();
        }
    };

    final ChangeListener<? super Boolean> mobile_Focus = (o, ov, nv) -> {
        JSONObject loJSON;
        if (!pbLoaded) {
            return;
        }
        TextField txtMobile = (TextField) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtMobile.getId().substring(txtMobile.getId().length() - 2));
        String lsValue = (txtMobile.getText() == null ? "" : txtMobile.getText());
        JSONObject jsonObject = new JSONObject();
        if (lsValue == null) {
            return;
        }
        if (!nv) {
            /*Lost Focus*/
            switch (lnIndex) {
                case 1:
                    /*Mobile No.*/ // searchable MobileNo
//                    loJSON = oTrans.Mobile(pnMobile).getModel().setMobileNo(lsValue);
//                    if ("error".equals((String) loJSON.get("result"))) {
//                        Assert.fail((String) loJSON.get("message"));
//                    }
                    break;
            }
            // loadRecordMobile();

        } else {
            // txtContact.selectAll();
        }
    };

    private void InitSocialMediaTextFields() {
        /*MOBILE INFO FOCUSED PROPERTY*/
        txtSocial01.focusedProperty().addListener(socialmedia_Focus);
        txtSocial02.focusedProperty().addListener(txtAreaSocialMedia_Focus);
    }

    private void InitMasterTextFields() {
        /*MOBILE INFO FOCUSED PROPERTY*/
        txtField02.focusedProperty().addListener(master_Focus);
        txtField03.focusedProperty().addListener(txtAreaMaster_Focus);
    }

    private void InitEmailTextFields() {
        /*MOBILE INFO FOCUSED PROPERTY*/
//        mailFields01.focusedProperty().addListener(email_Focus);
        mailFields01.setOnKeyPressed(this::email_KeyPressed);
    }

    private void InitMobileTextFields() {
        /*MOBILE INFO FOCUSED PROPERTY*/
        txtMobile01.focusedProperty().addListener(mobile_Focus);
        txtMobile01.setOnKeyPressed(this::mobile_KeyPressed);
    }

    private void InitAddressTextFields() {
        /*MOBILE INFO FOCUSED PROPERTY*/
        AddressField01.focusedProperty().addListener(address_Focus);
        AddressField02.focusedProperty().addListener(address_Focus);
        AddressField03.focusedProperty().addListener(address_Focus);
        AddressField04.focusedProperty().addListener(address_Focus);
        AddressField05.focusedProperty().addListener(address_Focus);
        AddressField06.focusedProperty().addListener(address_Focus);
        AddressField07.focusedProperty().addListener(address_Focus);

        AddressField03.setOnKeyPressed(this::address_KeyPressed);
        AddressField04.setOnKeyPressed(this::address_KeyPressed);
        AddressField05.setOnKeyPressed(this::address_KeyPressed);

    }

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
        personalinfo11.focusedProperty().addListener(personalinfo_Focus);
        personalinfo12.focusedProperty().addListener(personalinfo_Focus);
        personalinfo13.focusedProperty().addListener(personalinfo_Focus);
        personalinfo14.focusedProperty().addListener(personalinfo_Focus);
        personalinfo15.focusedProperty().addListener(personalinfo_Focus);

        personalinfo06.setOnKeyPressed(this::personalinfo_KeyPressed);
        personalinfo08.setOnKeyPressed(this::personalinfo_KeyPressed);

        personalinfo07.setOnAction(event -> {
            // Get the selected date
            LocalDate selectedDate = personalinfo07.getValue();
            if (selectedDate != null) {
//                ShowMessageFX.OkayCancel(null, "", "Selected Date: " + selectedDate);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = formatter.format(selectedDate);
                oTrans.Master().getModel().setBirthDate((SQLUtil.toDate(formattedDate, "yyyy-MM-dd")));

                Date dateString = oTrans.Master().getModel().getBirthDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateString);

                LocalDate defaultDate = LocalDate.of(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1, // Months are 0-based
                        calendar.get(Calendar.DAY_OF_MONTH));

                personalinfo07.setValue(defaultDate);

            } else {
                System.out.println("No date selected!");
                ShowMessageFX.OkayCancel(null, "", "No date selected!");
            }
        });

    }

    private void loadRecordMaster() {
        txtField02.setText(oTrans.Master().getModel().getCompanyName());
        try {
            ShowMessageFX.OkayCancel((String) oTrans.Master().getModel().getValue("sAddressx"), oTransnox, oTransnox);
            //   oTrans.Master().getModel().getValue("sAddressx");
            // oTrans.Master().getModel().getClientId();  
        } catch (Exception e) {
            ShowMessageFX.OkayCancel("error occured", oTransnox, oTransnox);

        }
    }

    private void loadRecordPersonalInfo() {
        personalinfo02.setText(oTrans.Master().getModel().getLastName());
        personalinfo03.setText(oTrans.Master().getModel().getFirstName());
        personalinfo04.setText(oTrans.Master().getModel().getMiddleName());
        personalinfo05.setText(oTrans.Master().getModel().getSuffixName());
//        personalinfo06.setText(oTrans.Master().getModel().getCitizenshipId());
//        personalinfo07.setText(oTransnox);
//        personalinfo08.setText(oTrans.Master().getModel().getBirthPlaceId());
//        personalinfo09.setText(oTransnox);
//        personalinfo10.setText(oTransnox);
        personalinfo11.setText(oTrans.Master().getModel().getSpouseId());
        personalinfo12.setText(oTrans.Master().getModel().getMothersMaidenName());
        personalinfo13.setText(oTrans.Master().getModel().getTaxIdNumber());
        personalinfo14.setText(oTrans.Master().getModel().getLTOClientId());
        personalinfo15.setText(oTrans.Master().getModel().getPhNationalId());

    }

    private void loadRecordAddress() {

        int lnCtr;
        int lnCtr2 = 0;
        address_data.clear();
//        oTrans.getAddress(pnAddress).list();

        if (oTrans.getAddressCount() >= 0) {
            for (lnCtr = 0; lnCtr < oTrans.getAddressCount(); lnCtr++) {
                TownCity loTownCity = new TownCity();
                loTownCity.setApplicationDriver(oApp);
                loTownCity.setRecordStatus("1");
                loTownCity.initialize();
                loTownCity.openRecord(oTrans.Address(lnCtr2).getModel().getTownId());

                Barangay loBarangay = new Barangay(oApp, true);
                loBarangay.setRecordStatus("1");
                loBarangay.openRecord(oTrans.Address(lnCtr2).getModel().getBarangayId());

//                String lsTown = (String) oTrans.getAddress(lnCtr, 20) + ", " + (String) oTrans.getAddress(lnCtr, 22);
                address_data.add(new ModelAddress(String.valueOf(lnCtr + 1),
                        (String) oTrans.Address(lnCtr2).getModel().getValue("sHouseNox"),
                        (String) oTrans.Address(lnCtr2).getModel().getValue("sAddressx"),
                        (String) loTownCity.getModel().getTownName(),
                        (String) loBarangay.getModel().getBarangayName()
                ));
                lnCtr2 += 1;

            }
        }

        if (pnAddress < 0 || pnAddress
                >= address_data.size()) {
            if (!address_data.isEmpty()) {
                /* FOCUS ON FIRST ROW */
                tblAddress.getSelectionModel().select(0);
                tblAddress.getFocusModel().focus(0);
                pnAddress = tblAddress.getSelectionModel().getSelectedIndex();
                getSelectedAddress();
            }
        } else {
            /* FOCUS ON THE ROW THAT pnRowDetail POINTS TO */
            tblAddress.getSelectionModel().select(pnAddress);
            tblAddress.getFocusModel().focus(pnAddress);
            getSelectedAddress();
        }

    }

    private void loadRecordMobile() {

        int lnCtr2 = 0;
        mobile_data.clear();

        if (oTrans.getMobileCount() >= 0) {

            for (int lnCtr = 0; lnCtr < oTrans.getMobileCount(); lnCtr++) {
                mobile_data.add(new ModelMobile(String.valueOf(lnCtr + 1),
                        oTrans.Mobile(lnCtr2).getModel().getValue("sMobileNo").toString(),
                        oTrans.Mobile(lnCtr2).getModel().getValue("cOwnerxxx").toString(),
                        oTrans.Mobile(lnCtr2).getModel().getValue("cMobileTp").toString()
                ));
                lnCtr2 += 1;
            }
        }

        if (pnMobile < 0 || pnMobile
                >= mobile_data.size()) {
            if (!mobile_data.isEmpty()) {
                /* FOCUS ON FIRST ROW */
                tblMobile.getSelectionModel().select(0);
                tblMobile.getFocusModel().focus(0);
                pnMobile = tblMobile.getSelectionModel().getSelectedIndex();
            }
            getSelectedMobile();
        } else {
            /* FOCUS ON THE ROW THAT pnRowDetail POINTS TO */
            tblMobile.getSelectionModel().select(pnMobile);
            tblMobile.getFocusModel().focus(pnMobile);
            getSelectedMobile();
        }
    }

    private void loadRecordEmail() {

        int lnCtr2 = 0;
        email_data.clear();
        if (oTrans.getMailCount() >= 0) {
            for (int lnCtr = 0; lnCtr < oTrans.getMailCount(); lnCtr++) {
                email_data.add(new ModelEmail(String.valueOf(lnCtr + 1),
                        oTrans.Mail(lnCtr2).getModel().getValue("cOwnerxxx").toString(),
                        oTrans.Mail(lnCtr2).getModel().getValue("sEMailAdd").toString()
                ));
                lnCtr2 += 1;
            }
        }
        if (pnEmail < 0 || pnEmail
                >= email_data.size()) {
            if (!email_data.isEmpty()) {
                /* FOCUS ON FIRST ROW */
                tblEmail.getSelectionModel().select(0);
                tblEmail.getFocusModel().focus(0);
                pnEmail = tblEmail.getSelectionModel().getSelectedIndex();

            }
            getSelectedEmail();
        } else {
            /* FOCUS ON THE ROW THAT pnRowDetail POINTS TO */
            tblEmail.getSelectionModel().select(pnEmail);
            tblEmail.getFocusModel().focus(pnEmail);
            getSelectedEmail();
        }
    }

    private void loadRecordSocialMedia() {
        try {
            String lsActive = oTrans.SocialMedia(pnSocialMedia).getModel().getRecordStatus();
            switch (lsActive) {
                case "0":
                    lblSocMedStat.setText("OPEN");
                    break;
                case "1":
                    lblSocMedStat.setText("CLOSED");
                    break;
                case "2":
                    lblSocMedStat.setText("POSTED");
                    break;
                case "3":
                    lblSocMedStat.setText("CANCELLED");
                    break;
                default:
                    lblSocMedStat.setText("UNKNOWN");
                    break;
            }
        } catch (Exception e) {

        }

        int lnCtr2 = 0;
        socialmedia_data.clear();
        if (oTrans.getSocMedCount() >= 0) {
            for (int lnCtr = 0; lnCtr < oTrans.getSocMedCount(); lnCtr++) {
                socialmedia_data.add(new ModelSocialMedia(String.valueOf(lnCtr + 1),
                        oTrans.SocialMedia(lnCtr2).getModel().getAccount(),
                        oTrans.SocialMedia(lnCtr2).getModel().getSocMedType(),
                        oTrans.SocialMedia(lnCtr2).getModel().getRemarks()
                ));
                lnCtr2 += 1;
            }
        }
        if (pnSocialMedia < 0 || pnSocialMedia
                >= socialmedia_data.size()) {
            if (!socialmedia_data.isEmpty()) {
                /* FOCUS ON FIRST ROW */
                tblSocMed.getSelectionModel().select(0);
                tblSocMed.getFocusModel().focus(0);
                pnSocialMedia = tblSocMed.getSelectionModel().getSelectedIndex();
            }
            getSelectedSocialMedia();
        } else {
            /* FOCUS ON THE ROW THAT pnRowDetail POINTS TO */
            tblSocMed.getSelectionModel().select(pnSocialMedia);
            tblSocMed.getFocusModel().focus(pnSocialMedia);
            getSelectedSocialMedia();

        }
    }

    private void getSelectedSocialMedia() {
        try {
            String lsActive = oTrans.SocialMedia(pnSocialMedia).getModel().getRecordStatus();
            switch (lsActive) {
                case "0":
                    lblSocMedStat.setText("OPEN");
                    break;
                case "1":
                    lblSocMedStat.setText("CLOSED");
                    break;
                case "2":
                    lblSocMedStat.setText("POSTED");
                    break;
                case "3":
                    lblSocMedStat.setText("CANCELLED");
                    break;
                default:
                    lblSocMedStat.setText("UNKNOWN");
                    break;
            }
        } catch (Exception e) {

        }

        int lsSocMedType = 0;
        if (oTrans.getSocMedCount() > 0) {
            lsSocMedType = Integer.parseInt(oTrans.SocialMedia(pnSocialMedia).getModel().getSocMedType());
            cmbSocMed01.getSelectionModel().select(lsSocMedType);

            txtSocial01.setText(oTrans.SocialMedia(pnSocialMedia).getModel().getAccount());
            txtSocial02.setText(oTrans.SocialMedia(pnSocialMedia).getModel().getRemarks());

            cbSocMed01.setSelected(oTrans.SocialMedia(pnSocialMedia).getModel().getRecordStatus() == "0" ? true : false);
        }

    }

    private void getSelectedAddress() {
        clearAddress();
//        TextField[] fields = {AddressField01, AddressField02, AddressField03, AddressField04,
//            AddressField05, AddressField06, AddressField07};
//        for (TextField field : fields) {
//            field.clear();
//        }
//
//        CheckBox[] checkboxs = {cbAddress01, cbAddress02, cbAddress03,
//            cbAddress04, cbAddress05, cbAddress06, cbAddress07, cbAddress08};
//
//        // Loop through each array of TextFields and clear them
//        for (CheckBox checkbox : checkboxs) {
//            checkbox.setSelected(false);
//        }

        if (oTrans.getAddressCount() > 0) {
            AddressField01.setText(oTrans.Address(pnAddress).getModel().getHouseNo());
            AddressField02.setText(oTrans.Address(pnAddress).getModel().getAddress());

            Province loProvince = new Province(oApp, true);
            loProvince.setRecordStatus("1");
            loProvince.openRecord(oTrans.Address(pnAddress).getModel().Town().Province().getProvinceId());

            AddressField03.setText(loProvince.getModel().getProvinceName());

            TownCity loTownCity = new TownCity();
            loTownCity.setApplicationDriver(oApp);
            loTownCity.setRecordStatus("1");
            loTownCity.initialize();
            loTownCity.openRecord(oTrans.Address(pnAddress).getModel().getTownId());

            Barangay loBarangay = new Barangay(oApp, true);
            loBarangay.setRecordStatus("1");
            loBarangay.openRecord(oTrans.Address(pnAddress).getModel().getBarangayId());

            AddressField04.setText((String) loTownCity.getModel().getTownName());
            AddressField05.setText(loBarangay.getModel().getBarangayName());
            AddressField06.setText(String.valueOf(oTrans.Address(pnAddress).getModel().getLatitude()));
            AddressField07.setText(String.valueOf(oTrans.Address(pnAddress).getModel().getLongitude()));

            cbAddress01.setSelected(((String) oTrans.Address(pnAddress).getModel().getRecordStatus() == "0") ? true : false);
            cbAddress02.setSelected(oTrans.Address(pnAddress).getModel().isPrimaryAddress());
            cbAddress03.setSelected(oTrans.Address(pnAddress).getModel().isOfficeAddress());
            cbAddress04.setSelected(oTrans.Address(pnAddress).getModel().isPrimaryAddress());
            cbAddress05.setSelected(oTrans.Address(pnAddress).getModel().isBillingAddress());
            cbAddress06.setSelected(oTrans.Address(pnAddress).getModel().isShippingAddress());
            cbAddress07.setSelected(oTrans.Address(pnAddress).getModel().isCurrentAddress());
            cbAddress08.setSelected(oTrans.Address(pnAddress).getModel().isLTMSAddress());
        }
    }

    private void getSelectedEmail() {
        try {
            String lsActive = oTrans.Mail(pnEmail).getModel().getRecordStatus();
            switch (lsActive) {
                case "0":
                    lblEmailStat.setText("OPEN");
                    break;
                case "1":
                    lblEmailStat.setText("CLOSED");
                    break;
                case "2":
                    lblEmailStat.setText("POSTED");
                    break;
                case "3":
                    lblEmailStat.setText("CANCELLED");
                    break;
                default:
                    lblEmailStat.setText("UNKNOWN");
                    break;
            }
        } catch (Exception e) {

        }

        if (oTrans.getMailCount() > 0) {
            mailFields01.setText(oTrans.Mail(pnEmail).getModel().getMailAddress()); // ask if searchable

            int lsOwnerType = 0;
            lsOwnerType = Integer.parseInt(oTrans.Mail(pnEmail).getModel().getOwnershipType());
            cbEmail01.setSelected(oTrans.Mail(pnEmail).getModel().getRecordStatus() == "0" ? true : false);
            cbEmail02.setSelected(oTrans.Mail(pnEmail).getModel().isPrimaryEmail());

            cmbEmail01.getSelectionModel().select(lsOwnerType);

//        Client_Mobile loclient_mobile = new Client_Mobile();
//        loclient_mobile.setApplicationDriver(oApp);
//        loclient_mobile.setRecordStatus("1");
//        loclient_mobile.initialize();
//        loclient_mobile.openRecord(oTrans.Mail(pnEmail).getModel().getEmailId());
        }

    }

    private void getSelectedMobile() {
        try {
            String lsActive = oTrans.Mobile(pnMobile).getModel().getRecordStatus();
            switch (lsActive) {
                case "0":
                    lblMobileStat.setText("OPEN");
                    break;
                case "1":
                    lblMobileStat.setText("CLOSED");
                    break;
                case "2":
                    lblMobileStat.setText("POSTED");
                    break;
                case "3":
                    lblMobileStat.setText("CANCELLED");
                    break;
                default:
                    lblMobileStat.setText("UNKNOWN");
                    break;
            }
        } catch (Exception e) {
        }

        if (oTrans.getMobileCount() > 0) {
            txtMobile01.setText(oTrans.Mobile(pnMobile).getModel().getMobileNo());
            int lsOwnerType = 0;
            int lsMobileType = 0;
            try {
                lsOwnerType = Integer.parseInt(oTrans.Mobile(pnMobile).getModel().getOwnershipType());
                lsMobileType = Integer.parseInt(oTrans.Mobile(pnMobile).getModel().getMobileType());
            } catch (Exception e) {

            }
            try {
                cmbMobile01.getSelectionModel().select(lsOwnerType);
                cmbMobile02.getSelectionModel().select(lsMobileType);
            } catch (Exception e) {
            }
            cbMobileNo01.setSelected(((String) oTrans.Mobile(pnMobile).getModel().getRecordStatus() == "0") ? true : false);
            cbMobileNo02.setSelected(oTrans.Mobile(pnMobile).getModel().isPrimaryMobile());

        }

    }

    private void initAddressCheckbox() {
        CheckBox[] cbAddressCheckboxes = {cbAddress01, cbAddress02, cbAddress03, cbAddress04, cbAddress05, cbAddress06, cbAddress07, cbAddress08};

        for (int i = 0; i < cbAddressCheckboxes.length; i++) {
            final CheckBox checkbox = cbAddressCheckboxes[i]; // Capture the current checkbox
            checkbox.setOnMouseClicked(event -> {
                JSONObject loJSON;
                String id = checkbox.getId();
                String numberPart = id.substring(id.length() - 2);
                try {
                    int number = Integer.parseInt(numberPart);
                    switch (number) {
                        case 1: // ask if this is crecordstat // isActive
                            System.out.println("Checkbox 01 selected");
                            loJSON = oTrans.Address(pnAddress).getModel().setRecordStatus((checkbox.isSelected()) ? "0" : "1");
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }

                            getSelectedAddress();
                            break;
                        case 2: // Primary Address
                            loJSON = oTrans.Address(pnAddress).getModel().isPrimaryAddress(checkbox.isSelected());
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        case 3: //Office
                            loJSON = oTrans.Address(pnAddress).getModel().isOfficeAddress(checkbox.isSelected());
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        case 4: // Province
                            loJSON = oTrans.Address(pnAddress).getModel().isProvinceAddress(checkbox.isSelected());
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        case 5: // Billing
                            loJSON = oTrans.Address(pnAddress).getModel().isBillingAddress(checkbox.isSelected());
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        case 6: // Shipping
                            loJSON = oTrans.Address(pnAddress).getModel().isShippingAddress(checkbox.isSelected());
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        case 7: // Current
                            loJSON = oTrans.Address(pnAddress).getModel().isCurrentAddress(checkbox.isSelected());
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        case 8: // LTMS
                            loJSON = oTrans.Address(pnAddress).getModel().isLTMSAddress(checkbox.isSelected());
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        default:
                            System.out.println("Unknown checkbox selected");
                            break;
                    }
                } catch (NumberFormatException e) {
                }

            });
        }
    }

    private void initEmailCheckbox() {
        CheckBox[] cbEmailCheckboxes = {cbEmail01, cbEmail02};

        for (int i = 0; i < cbEmailCheckboxes.length; i++) {
            final CheckBox checkbox = cbEmailCheckboxes[i]; // Capture the current checkbox
            checkbox.setOnMouseClicked(event -> {
                JSONObject loJSON;

                String id = checkbox.getId();
                String numberPart = id.substring(id.length() - 2);
                try {
                    int number = Integer.parseInt(numberPart);
                    switch (number) {
                        case 1: // ask if this is crecordstat // isActive
                            System.out.println("Checkbox 01 selected");

                            loJSON = oTrans.Mail(pnEmail).getModel().setRecordStatus(checkbox.isSelected() ? "0" : "1");
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }

                            break;
                        case 2: // Primary Address
                            loJSON = oTrans.Mail(pnEmail).getModel().isPrimaryEmail(checkbox.isSelected());
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        default:
                            System.out.println("Unknown checkbox selected");
                            break;

                    }
                    getSelectedEmail();
                } catch (NumberFormatException e) {
                }

            });
        }
    }

    private void initSocialMediaCheckbox() {
        CheckBox[] cbSocMedCheckboxes = {cbSocMed01};

        for (int i = 0; i < cbSocMedCheckboxes.length; i++) {
            final CheckBox checkbox = cbSocMedCheckboxes[i]; // Capture the current checkbox
            checkbox.setOnMouseClicked(event -> {
                JSONObject loJSON;
                String id = checkbox.getId();
                String numberPart = id.substring(id.length() - 2);
                try {
                    int number = Integer.parseInt(numberPart);
                    switch (number) {
                        case 1: // ask if this is crecordstat // isActive
                            System.out.println("Checkbox 01 selected");
                            oTrans.SocialMedia(pnSocialMedia).getModel().setRecordStatus(checkbox.isSelected() ? "0" : "1");
                            getSelectedSocialMedia();
                            break;
                        default:
                            System.out.println("Unknown checkbox selected");
                            break;
                    }
                } catch (NumberFormatException e) {
                }

            });
        }
    }

    private void initMobileCheckbox() {
        CheckBox[] cbMobileCheckboxes = {cbMobileNo01, cbMobileNo02};

        for (int i = 0; i < cbMobileCheckboxes.length; i++) {
            final CheckBox checkbox = cbMobileCheckboxes[i]; // Capture the current checkbox
            checkbox.setOnMouseClicked(event -> {
                JSONObject loJSON;
                String id = checkbox.getId();
                String numberPart = id.substring(id.length() - 2);
                try {
                    int number = Integer.parseInt(numberPart);
                    switch (number) {
                        case 1: // ask if this is crecordstat // isActive
                            System.out.println("Checkbox 01 selected");
                            loJSON = oTrans.Mobile(pnMobile).getModel().setRecordStatus(checkbox.isSelected() ? "0" : "1");
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        case 2: // Primary Address
                            loJSON = oTrans.Mobile(pnMobile).getModel().isPrimaryMobile(checkbox.isSelected());
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        default:
                            System.out.println("Unknown checkbox selected");
                            break;
                    }
                    getSelectedMobile();

                } catch (NumberFormatException e) {
                }
            });
        }
    }

    public void initTableOnClick() {
        tblAddress.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // Detect single click (or use another condition for double click)
                pnAddress = tblAddress.getSelectionModel().getSelectedIndex();
                if (pnAddress >= 0) {
                    getSelectedAddress();
                }
            }
        });

        tblMobile.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // Detect single click (or use another condition for double click)
                pnMobile = tblMobile.getSelectionModel().getSelectedIndex();
                getSelectedMobile();
            }
        });
        tblEmail.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // Detect single click (or use another condition for double click)
                pnEmail = tblEmail.getSelectionModel().getSelectedIndex();
                getSelectedEmail();
            }
        });

        tblSocMed.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // Detect single click (or use another condition for double click)
                pnSocialMedia = tblSocMed.getSelectionModel().getSelectedIndex();
                getSelectedSocialMedia();
            }
        });

    }

    public void initAddressGrid() {
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
        tblAddress.setItems(address_data);
        tblAddress.getSelectionModel().select(pnAddress + 1);
        tblAddress.autosize();

    }

    private void initMobileGrid() {
        indexMobileNo01.setStyle("-fx-alignment: CENTER;");
        indexMobileNo02.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");
        indexMobileNo03.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");
        indexMobileNo04.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");

        indexMobileNo01.setCellValueFactory(new PropertyValueFactory<>("index01"));
        indexMobileNo02.setCellValueFactory(new PropertyValueFactory<>("index02"));
        indexMobileNo03.setCellValueFactory(new PropertyValueFactory<>("index03"));
        indexMobileNo04.setCellValueFactory(new PropertyValueFactory<>("index04"));
        tblMobile.widthProperty().addListener((ObservableValue<? extends Number> source, Number oldWidth, Number newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) tblMobile.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                header.setReordering(false);
            });
        });
        tblMobile.setItems(mobile_data);

        tblMobile.autosize();
    }

    private void initEmailGrid() {
        indexEmail01.setStyle("-fx-alignment: CENTER;");
        indexEmail02.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");
        indexEmail03.setStyle("-fx-alignment: CENTER-LEFT;-fx-padding: 0 0 0 5;");
//        
        indexEmail01.setCellValueFactory(new PropertyValueFactory<>("index01"));
        indexEmail02.setCellValueFactory(new PropertyValueFactory<>("index02"));
        indexEmail03.setCellValueFactory(new PropertyValueFactory<>("index03"));
        tblEmail.widthProperty().addListener((ObservableValue<? extends Number> source, Number oldWidth, Number newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) tblEmail.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                header.setReordering(false);
            });
        });
        tblEmail.setItems(email_data);
        tblEmail.autosize();
    }

    private void initSocialMediaGrid() {
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
        tblSocMed.setItems(socialmedia_data);
        tblMobile.getSelectionModel().select(pnSocialMedia + 1);
        tblSocMed.autosize();
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

        if (oTransnox == null || oTransnox.isEmpty()) { // Check if oTransnox is null or empty
            pnEditMode = EditMode.ADDNEW;
//            initButton(pnEditMode);
        }

        oTrans = new Client(oApp, "", null);
        oTrans.New();
        oTrans.deleteAddress(0);
        oTrans.deleteMobile(0);
        oTrans.deleteEmail(0);
        oTrans.deleteSocialMedia(0);

        oTrans.Master().getModel().setClientType(ClientType.INDIVIDUAL);

        initButton();
        InitPersonalInfoTextFields();

        personalinfo09.setItems(gender);

        personalinfo09.setOnAction(event -> {
            String selectedItem = String.valueOf(personalinfo09.getSelectionModel().getSelectedIndex());
            oTrans.Master().getModel().setGender(selectedItem);

            personalinfo09.setValue(personalinfo09.getValue());
        });
        personalinfo10.setItems(civilstatus);
        personalinfo10.setOnAction(event -> {
            String selectedItem = String.valueOf(personalinfo10.getSelectionModel().getSelectedIndex());
            oTrans.Master().getModel().setCivilStatus(selectedItem);
            personalinfo10.setValue(personalinfo10.getValue());
        });

        InitAddressTextFields();
        initAddressCheckbox();

        InitMobileTextFields();
        initMobileCheckbox();

        cmbMobile01.setItems(mobileOwn);
        cmbMobile01.setOnAction(event -> {
            int selectedIndex = cmbMobile01.getSelectionModel().getSelectedIndex();
            oTrans.Mobile(pnMobile).getModel().setOwnershipType(String.valueOf(selectedIndex));
            cmbMobile01.getSelectionModel().select(selectedIndex);

            loadRecordMobile();
        });

        cmbMobile02.setItems(mobileType);
        cmbMobile02.setOnAction(event -> {
            int selectedIndex = cmbMobile02.getSelectionModel().getSelectedIndex();
            String selectedItem = (String) cmbMobile02.getValue();
            oTrans.Mobile(pnMobile).getModel().setMobileType(String.valueOf(selectedIndex));
            cmbMobile02.getSelectionModel().select(selectedIndex);

            loadRecordMobile();
        });

        InitEmailTextFields();
        initEmailCheckbox();

        cmbEmail01.setItems(emailOwn);
        cmbEmail01.setOnAction(event -> {
            int selectedIndex = cmbEmail01.getSelectionModel().getSelectedIndex();
            oTrans.Mail(pnEmail).getModel().setOwnershipType(String.valueOf(selectedIndex));
            cmbEmail01.getSelectionModel().select(selectedIndex);

            loadRecordEmail();
        });

        InitSocialMediaTextFields();
        initSocialMediaCheckbox();

        cmbSocMed01.setItems(socialTyp);
        cmbSocMed01.setOnAction(event -> {
            int selectedIndex = cmbSocMed01.getSelectionModel().getSelectedIndex();
            oTrans.SocialMedia(pnSocialMedia).getModel().setSocMedType(String.valueOf(selectedIndex));
            cmbSocMed01.getSelectionModel().select(selectedIndex);

            loadRecordSocialMedia();
        });

        initAddressGrid();
        initSocialMediaGrid();
        initEmailGrid();
        initMobileGrid();

        clearMaster();
        clearAddress();
        clearEmail();
        clearSocMed();
        clearMobile();

        InitMasterTextFields();

        loadRecordMobile();
        loadRecordAddress();
        loadRecordSocialMedia();
        loadRecordEmail();

        initTableOnClick();

        pbLoaded = true;

//        oTrans.New();
        String lsClientID = (String) oTrans.Master().getModel().getClientId();
        if (txtField01 != null) { // Check if txtField01 is not null before setting its text
            oTrans.Master().getModel().setClientId(lsClientID);
            txtField01.setText((String) oTrans.Master().getModel().getClientId());
        } else {
            // Handle the case where txtField01 is null
            ShowMessageFX.OkayCancel(oTransnox, lsClientID, "No Client ID");
        }

    }

}
