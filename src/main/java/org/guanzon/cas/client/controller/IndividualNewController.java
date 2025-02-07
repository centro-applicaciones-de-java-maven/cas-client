package org.guanzon.cas.client.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import com.ibm.icu.impl.Assert;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.util.StringConverter;
import org.guanzon.appdriver.agent.ShowMessageFX;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.ClientType;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.client.Client;
import org.guanzon.cas.client.table.models.ModelAddress;
import org.guanzon.cas.client.table.models.ModelEmail;
import org.guanzon.cas.client.table.models.ModelMobile;
import org.guanzon.cas.client.table.models.ModelSocialMedia;
import org.guanzon.cas.parameter.Barangay;
import org.guanzon.cas.parameter.TownCity;
import org.guanzon.cas.parameter.Country;
import org.guanzon.cas.parameter.Province;
import org.json.simple.JSONObject;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.cas.client.Client_Master;

/**
 * FXML Controller class
 *
 * @author User
 */
public class IndividualNewController implements Initializable {

    private final String pxeModuleName = "Individual New";
    private GRider oApp;
    private Client oTrans;
    public int pnEditMode;
    public String lsID;

    static LogWrapper logWrapper;
    private String oTransnox = "";

    private boolean pbLoaded = false;

    private ObservableList<ModelAddress> address_data = FXCollections.observableArrayList();
    private ObservableList<ModelMobile> mobile_data = FXCollections.observableArrayList();
    private ObservableList<ModelEmail> email_data = FXCollections.observableArrayList();
    private ObservableList<ModelSocialMedia> socialmedia_data = FXCollections.observableArrayList();

    ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female", "Others");
    ObservableList<String> civilstatus = FXCollections.observableArrayList("Single", "Married", "Divorced", "Widowed");

    ObservableList<String> mobileOwn = ModelMobile.mobileOwn;
    ObservableList<String> mobileType = ModelMobile.mobileType;
    ObservableList<String> emailOwn = ModelEmail.emailOwn;
    ObservableList<String> socialTyp = ModelSocialMedia.socialTyp;

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
        try {
            if (oTrans.getAddressCount() >= 2) {
                oTrans.Address(pnAddress).getModel().setRecordStatus("0");
            }
        } catch (Exception e) {
        }
        try {
            oTrans.Address(pnAddress).getModel().setLatitude("0.0");
            oTrans.Address(pnAddress).getModel().setLongitude("0.0");
        } catch (Exception e) {
        }

        AddressField01.clear();
        AddressField02.clear();
        AddressField03.clear();
        AddressField04.clear();
        AddressField05.clear();

        cbAddress01.setSelected(false);
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
        try {
            if (oTrans.getMobileCount() >= 2) {
                oTrans.Mobile(pnMobile).getModel().setRecordStatus("0");
            }
        } catch (Exception e) {
        }
        txtMobile01.clear();
        cmbMobile01.setItems(mobileOwn);
        cmbMobile01.getSelectionModel().select(0);

        cmbMobile02.setItems(mobileType);
        cmbMobile02.getSelectionModel().select(0);

        cbMobileNo01.setSelected(false);
        cbMobileNo02.setSelected(false);

    }

    private void clearEmail() {
        mailFields01.clear();
        try {
            if (oTrans.getMailCount() >= 2) {
                oTrans.Mail(pnEmail).getModel().setRecordStatus("0");
            }
        } catch (Exception e) {

        }
        cmbEmail01.setItems(emailOwn);
        cmbEmail01.getSelectionModel().select(0);
        cbEmail01.setSelected(false);
        cbEmail02.setSelected(false);
    }

    private void clearSocMed() {
//        try {
//            if (oTrans.getSocMedCount() >= 1) {
//                oTrans.SocialMedia(pnSocialMedia).getModel().setRecordStatus("0");
//            }
//        } catch (Exception e) {
//
//        }

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
        JSONObject loJson = new JSONObject();
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
                    oTrans.Master().getModel().setCompanyName(oTrans.Master().getModel().getCompanyName());

                    loJSON = oTrans.Master().isEntryOkay();
                    if ("error".equals((String) loJSON.get("result"))) {
                        ShowMessageFX.Information((String) loJSON.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }
                    if (pnEditMode == EditMode.UPDATE) {
                        oTrans.Update();
                    }

                    loJSON = oTrans.Save();

                    if ("error".equals((String) loJSON.get("result"))) {
                        ShowMessageFX.Information((String) loJSON.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    } else {
                        ShowMessageFX.OkayCancel((String) loJSON.get("message"), "", "Successfully saved!");
                    }
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
                        Stage stage = (Stage) txtField01.getScene().getWindow();
                        stage.close();

                    }
                    break;
                case "btnAddAddress":
                    if (oTrans.getAddressCount() > 1) {
                        JSONObject addObj = oTrans.Address(pnAddress - 1).isEntryOkay();
                        if ("error".equals((String) addObj.get("result"))) {
                            ShowMessageFX.Information((String) addObj.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        } else {

                        }
                    }
                    JSONObject addObjAddress = oTrans.addAddress();
                    if ("error".equals((String) addObjAddress.get("result"))) {
                        ShowMessageFX.Information((String) addObjAddress.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    } else {
                        oTrans.Address(pnAddress).getModel().setClientId(oTrans.Master().getModel().getClientId());
                        pnAddress = oTrans.getAddressCount() - 1;
                        tblAddress.getSelectionModel().select(pnAddress + 1);
                        clearAddress();
                        loadRecordAddress();
                    }
                    break;
                case "btnAddEmail":
                    if (oTrans.getMailCount() > 1) {
                        JSONObject addObjMail = oTrans.Mail(pnEmail - 1).isEntryOkay();
                        if ("error".equals((String) addObjMail.get("result"))) {
                            ShowMessageFX.Information((String) addObjMail.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        } else {
                            try {
                                oTrans.Mail(pnEmail).getModel().setClientId(oTrans.Master().getModel().getClientId());
                            } catch (Exception e) {
                            }
                        }
                    }

                    JSONObject addObjMail = oTrans.addMail();
                    System.out.println((String) addObjMail.get("message"));
                    if ("error".equals((String) addObjMail.get("result"))) {
                        ShowMessageFX.Information((String) addObjMail.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    } else {
                        oTrans.Mail(pnEmail).getModel().setClientId(oTrans.Master().getModel().getClientId());
                        mailFields01.clear();
                        pnEmail = oTrans.getMailCount() - 1;
                        tblEmail.getSelectionModel().select(pnEmail + 1);
                        clearEmail();
                        loadRecordEmail();
                    }

                    break;
                case "btnAddMobile":
                    if (oTrans.getMobileCount() > 1) {
                        JSONObject addObjMobile = oTrans.Mobile(pnMobile - 1).isEntryOkay();
                        if ("error".equals((String) addObjMobile.get("result"))) {
                            ShowMessageFX.Information((String) addObjMobile.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                    }

                    JSONObject addObj = oTrans.addMobile();
                    System.out.println((String) addObj.get("message"));
                    if ("error".equals((String) addObj.get("result"))) {
                        ShowMessageFX.Information((String) addObj.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    } else {
                        oTrans.Mobile(pnMobile).getModel().setClientId(oTrans.Master().getModel().getClientId());
                        txtMobile01.clear();
                        pnMobile = oTrans.getMobileCount() - 1;
                        tblMobile.getSelectionModel().select(pnMobile + 1);
                        clearMobile();
                        loadRecordMobile();
                    }

                    break;
                case "btnAddSocMed":
                    if (oTrans.getSocMedCount() > 1) {
                        JSONObject addObjSocMed = oTrans.SocialMedia(pnSocialMedia - 1).isEntryOkay();
                        if ("error".equals((String) addObjSocMed.get("result"))) {
                            ShowMessageFX.Information((String) addObjSocMed.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                    }
                    JSONObject addSocMed = oTrans.addSocialMedia();
                    System.out.println((String) addSocMed.get("message"));
                    if ("error".equals((String) addSocMed.get("result"))) {
                        ShowMessageFX.Information((String) addSocMed.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    } else {
                        oTrans.SocialMedia(pnSocialMedia).getModel().setClientId(oTrans.Master().getModel().getClientId());
                        txtSocial01.clear();
                        txtSocial02.clear();
                        pnSocialMedia = oTrans.getSocMedCount() - 1;

                        tblSocMed.getSelectionModel().select(pnSocialMedia + 1);
                        clearSocMed();
                        loadRecordSocialMedia();

                    }
                    break;
                case "btnDelAddress":
                    if (oTrans.getAddressCount() == 0) {
                        loJson.put("result", "error");
                        loJson.put("message", "No Record.");
                        ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }
                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
                        loJson = oTrans.deleteAddress(pnAddress);
                        if ("error".equals((String) loJson.get("result"))) {
                            ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                        clearAddress();
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
                    if (oTrans.getMobileCount() == 0) {
                        loJson.put("result", "error");
                        loJson.put("message", "No Record.");
                        ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }

                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
                        loJson = oTrans.deleteMobile(pnMobile);
                        if ("error".equals((String) loJson.get("result"))) {
                            ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                        clearMobile();
                        if (oTrans.getMobileCount() <= 0) {
                            pnMobile = oTrans.getMobileCount();
                        } else {
                            //this is where should be getting table get selected then minus one if it is not equal to 
                            pnMobile = oTrans.getMobileCount() - 1;
                        }

                        loadRecordMobile();
                    }
                    break;
                case "btnDelEmail":
                    if (oTrans.getMailCount() == 0) {
                        loJson.put("result", "error");
                        loJson.put("message", "No Record.");
                        ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }
                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
                        loJson = oTrans.deleteEmail(pnEmail);
                        if ("error".equals((String) loJson.get("result"))) {
                            ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }
                        clearEmail();
                        if (oTrans.getMailCount() <= 0) {
                            pnEmail = oTrans.getMailCount();
                        } else {
                            pnEmail = oTrans.getMailCount() - 1;
                        }
                        loadRecordEmail();

                    }

                    break;
                case "btnDelSocMed":
                    if (oTrans.getSocMedCount() == 0) {
                        loJson.put("result", "error");
                        loJson.put("message", "No Record.");
                        ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }

                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
                        loJson = oTrans.deleteSocialMedia(pnSocialMedia);
                        if ("error".equals((String) loJson.get("result"))) {
                            ShowMessageFX.Information((String) loJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            break;
                        }

                        clearSocMed();
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

    private String autoCapitalize(String lsValue) {
        String[] words = lsValue.split("\\s+");
        StringBuilder lscapitalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                // Capitalize the first letter and convert the rest to lowercase
                lscapitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return lscapitalized.toString().trim();
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

//                        Country loCountry = new Country(oApp, true);
//                        loCountry.setRecordStatus("1");
//                        poJson = loCountry.searchRecord(lsValue, false);
                        poJson = oTrans.Master().searchCitizenship(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            personalinfo06.clear();
                        } else {
//                            personalinfo06.setText((String) loCountry.getModel().getNational());
//                            oTrans.Master().getModel().setCitizenshipId((String) loCountry.getModel().getCountryCode());
                            personalinfo06.setText((String) poJson.get("sNational"));
                            oTrans.Master().getModel().setCitizenshipId((String) poJson.get("sCntryCde"));

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
                        poJson = new JSONObject();
                        break;
                    case 11:
                        /*search spouse*/
                        Client_Master loclientMaster = new Client_Master();
                        loclientMaster.setApplicationDriver(oApp);
                        loclientMaster.setRecordStatus("1");
                        loclientMaster.initialize();
                        loclientMaster.setClientType(ClientType.INDIVIDUAL);
                        poJson = loclientMaster.searchRecordWithClientType(lsValue, true);

                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            personalinfo11.clear();
                        } else {
                            //loadspousename
                            oTrans.Master().getModel().setSpouseId(loclientMaster.getModel().getClientId());
                            personalinfo11.setText(loadSpouseName(loclientMaster.getModel().getClientId()));
                        }
                        poJson = new JSONObject();
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

        if (lsValue == null) {
            return;
        }
        if (!nv) {
            /*Lost Focus*/
            lsValue = lsValue.trim();
            switch (lnIndex) {
                case 2:
                    /*Last name*/

                    // Remove the trailing space and return the result
                    lsValue = autoCapitalize(lsValue);
                    oTrans.Master().getModel().setLastName(lsValue);
                    break;
                case 3:
                    /*First name*/
                    lsValue = autoCapitalize(lsValue);
                    oTrans.Master().getModel().setFirstName(lsValue);
                    break;
                case 4:
                    /*Middle name*/
                    lsValue = autoCapitalize(lsValue);
                    oTrans.Master().getModel().setMiddleName(lsValue);
                    break;
                case 5:
                    /*Suffix*/
                    oTrans.Master().getModel().setSuffixName(lsValue);
                    break;
                case 6:
                    /*Citizenship*/

                    break;
                case 7:
                    /*Birth Date*/ // DATE TIME
                    break;
                case 8:
                    /*Birth Place*/
                    oTrans.Master().getModel().setBirthPlaceId(lsValue);
                case 9:
                    /*Gender*/ //COMBOBOX
                    break;
                case 10:
                    /*Civil Status*/
                    break;
//                case 11:
//                    /*Spouse*/
//                    break;
                case 12:
                    /*Mother's Maiden Name*/
                    lsValue = autoCapitalize(lsValue);
                    oTrans.Master().getModel().setMothersMaidenName(lsValue);
                    break;
                case 13:
                    /*TIN No*/ //ADD VALIDATIONS
                    if (!lsValue.equals("")) {
                        lsValue = lsValue.replace("-", "");
                        if (lsValue.length() == 9 && lsValue.matches("\\d+")) {
                            String lsformattedValue = lsValue.substring(0, 3) + "-" + lsValue.substring(3, 5) + "-" + lsValue.substring(5, 9);
                            oTrans.Master().getModel().setTaxIdNumber(lsformattedValue);
                        } else {
                            // Input is invalid
                            oTrans.Master().getModel().setTaxIdNumber("");
                            ShowMessageFX.Information("Input must be 9 digits and contain numbers only.", "Computerized Acounting System", pxeModuleName);
                        }
                    } else {
                        oTrans.Master().getModel().setTaxIdNumber("");
                    }
                    break;
                case 14:
                    /*LTO ID*/

                    if (!lsValue.equals("")) {
                        lsValue = lsValue.replace("-", "");
                        if (lsValue.length() == 11 && lsValue.matches("\\d+")) {
                            // Format the input
                            String lsformattedValue = lsValue.substring(0, 3) + "-" + lsValue.substring(3, 5) + "-" + lsValue.substring(5, 11);
                            oTrans.Master().getModel().setLTOClientId(lsformattedValue);
                        } else {
                            // Input is invalid
                            oTrans.Master().getModel().setLTOClientId("");
                            ShowMessageFX.Information("Input must be 11 digits and contain numbers only.", "Computerized Acounting System", pxeModuleName);
                        }
                    } else {
                        oTrans.Master().getModel().setLTOClientId("");
                    }
                    break;
                case 15:
                    /*National ID */
                    if (!lsValue.equals("")) {
                        lsValue = lsValue.replace("-", "");
                        if (lsValue.length() == 11 && lsValue.matches("\\d+")) {
                            // Format the input
                            String lsformattedValue = lsValue.substring(0, 3) + "-" + lsValue.substring(3, 5) + "-" + lsValue.substring(5, 11);
                            oTrans.Master().getModel().setPhNationalId(lsformattedValue);

                        } else {
                            // Input is invalid
                            oTrans.Master().getModel().setPhNationalId("");
                            ShowMessageFX.Information("Input must be 11 digits and contain numbers only.", "Computerized Acounting System", pxeModuleName);
                        }
                    } else {
                        oTrans.Master().getModel().setPhNationalId("");
                    }
                    break;
            }
            loadMasterName();
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

                        Province loProvince = new Province();
                        loProvince.setApplicationDriver(oApp);
                        loProvince.setRecordStatus("1");
                        loProvince.initialize();

                        poJson = loProvince.searchRecord(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            AddressField03.clear();
                        } else {
                            AddressField03.setText((String) loProvince.getModel().getProvinceName());
                            oTrans.Address(pnAddress).getModel().Town().Province().setProvinceId(loProvince.getModel().getProvinceId());
                            oTrans.setProvinceID_temp(loProvince.getModel().getProvinceId());
                            oTrans.Address(pnAddress).getModel().setTownId("");
                            oTrans.Address(pnAddress).getModel().setBarangayId("");
                            AddressField04.setText("");
                            AddressField04.setText("");
                        }

                        break;
                    case 4:
                        /*search city*/
                        poJson = new JSONObject();
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
                        }
                        loadRecordAddress();
                        break;
                    case 5:
                        /*search barangay*/
                        poJson = new JSONObject();
                        Barangay loBarangay = new Barangay();
                        loBarangay.setApplicationDriver(oApp);
                        loBarangay.setRecordStatus("1");
                        loBarangay.initialize();

                        try {
                            if (!oTrans.Address(pnAddress).getModel().getTownId().equalsIgnoreCase("")) {
                                poJson = loBarangay.searchRecord("", false, oTrans.Address(pnAddress).getModel().getTownId());
                            } else {
                                poJson = loBarangay.searchRecord(lsValue, false);
                            }
                        } catch (Exception e) {
                            poJson = loBarangay.searchRecord(lsValue, false);
                        }

                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            AddressField05.clear();
                        } else {
                            AddressField05.setText(loBarangay.getModel().getBarangayName());
                            oTrans.Address(pnAddress).getModel().setBarangayId(loBarangay.getModel().getBarangayId());
                            oTrans.Address(pnAddress).getModel().setTownId(loBarangay.getModel().getTownId());

                        }
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

    public void loadMasterName() {
        String lsLastName = ((oTrans.Master().getModel().getLastName() instanceof String) && (!oTrans.Master().getModel().getLastName().equals("")) ? oTrans.Master().getModel().getLastName() : "");
        String lsFirstName = ((oTrans.Master().getModel().getFirstName() instanceof String) && (!oTrans.Master().getModel().getFirstName().equals("")) ? oTrans.Master().getModel().getFirstName() : "");
        String lsMiddleName = ((oTrans.Master().getModel().getMiddleName() instanceof String) && (!oTrans.Master().getModel().getMiddleName().equals("")) ? oTrans.Master().getModel().getMiddleName() : "");
        String lsSuffixName = ((oTrans.Master().getModel().getSuffixName() instanceof String) && (!oTrans.Master().getModel().getSuffixName().equals("")) ? oTrans.Master().getModel().getSuffixName() : "");

        StringBuilder fullName = new StringBuilder();
        if (lsLastName != null && !lsLastName.trim().isEmpty()) {
            fullName.append(lsLastName).append(", ");
        }
        if (lsFirstName != null && !lsFirstName.trim().isEmpty()) {
            fullName.append(lsFirstName).append(" ");
        }
        if (lsMiddleName != null && !lsMiddleName.trim().isEmpty()) {
            fullName.append(lsMiddleName).append(" ");
        }
        if (lsSuffixName != null && !lsSuffixName.trim().isEmpty()) {
            fullName.append(lsSuffixName);
        }

        String lsCompName = fullName.toString().trim();
        oTrans.Master().getModel().setCompanyName(lsCompName);
        txtField02.setText(oTrans.Master().getModel().getCompanyName());
    }

    public String loadSpouseName(String lsClientID) {
        StringBuilder fullName = new StringBuilder();
        String lsSpouseName = "";
        if (!lsClientID.equals("")) {

            oTrans.Master().openRecord(lsClientID);

            String lsLastName = ((oTrans.Master().getModel().getLastName() instanceof String) && (!oTrans.Master().getModel().getLastName().equals("")) ? oTrans.Master().getModel().getLastName() : "");
            String lsFirstName = ((oTrans.Master().getModel().getFirstName() instanceof String) && (!oTrans.Master().getModel().getFirstName().equals("")) ? oTrans.Master().getModel().getFirstName() : "");
            String lsMiddleName = ((oTrans.Master().getModel().getMiddleName() instanceof String) && (!oTrans.Master().getModel().getMiddleName().equals("")) ? oTrans.Master().getModel().getMiddleName() : "");
            String lsSuffixName = ((oTrans.Master().getModel().getSuffixName() instanceof String) && (!oTrans.Master().getModel().getSuffixName().equals("")) ? oTrans.Master().getModel().getSuffixName() : "");

            if (lsLastName != null && !lsLastName.trim().isEmpty()) {
                fullName.append(lsLastName).append(", ");
            }
            if (lsFirstName != null && !lsFirstName.trim().isEmpty()) {
                fullName.append(lsFirstName).append(" ");
            }
            if (lsMiddleName != null && !lsMiddleName.trim().isEmpty()) {
                fullName.append(lsMiddleName).append(" ");
            }
            if (lsSuffixName != null && !lsSuffixName.trim().isEmpty()) {
                fullName.append(lsSuffixName);
            }
            lsSpouseName = fullName.toString().trim();
        }
        return lsSpouseName;
    }

    public void loadMasterAddress() {
        boolean primaryAddressExists = false;
        for (int i = 0; i < oTrans.getAddressCount(); i++) {
            if (oTrans.Address(i).getModel().isPrimaryAddress()) {
                txtField03.setText(oTrans.getMasterAddress(i));
                primaryAddressExists = true; // Mark as found
                break; // Exit the loop since a primary address is found
            }
        }
        if (!primaryAddressExists) {
            txtField03.setText("");
        }
    }

    final ChangeListener<? super Boolean> address_Focus = (o, ov, nv) -> {
        JSONObject loJSON = new JSONObject();
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
            lsValue = lsValue.trim();
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
                    lsValue = autoCapitalize(lsValue);
                    loJSON = oTrans.Address(pnAddress).getModel().setAddress(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    loadRecordAddress();

                    break;
                case 3:
                    /*Province*/

                    //leave blank as it is searchable 
                    break;
                case 4:
                    /*City*/
                    //leave blank as it is searchable 
                    if (lsValue.equals("")) {
                        oTrans.Address(pnAddress).getModel().Town().Province().setProvinceId(lsValue);
                        oTrans.Address(pnAddress).getModel().setTownId(lsValue);
                        oTrans.Address(pnAddress).getModel().setBarangayId(lsValue);
                    }
                    loadRecordAddress();
                    break;
                case 5:
                    /*Barangay*/
                    //leave blank as it is searchable 
                    loadRecordAddress();
                    break;
                case 6:
                    /*Latitude*/
                    if (!lsValue.matches("-?\\d+\\.?\\d*")) {
                        oTrans.Address(pnAddress).getModel().setLatitude("0.0");
                        loJSON.put("error", lsValue);
                        loJSON.put("message", "Value must contain numbers only");
                        oTrans.Address(pnAddress).getModel().setLatitude("0.0");
                        loadRecordAddress();

                        ShowMessageFX.Information((String) loJSON.get("message"), "Computerized Acounting System", pxeModuleName);
                    } else {
                        loJSON = oTrans.Address(pnAddress).getModel().setLatitude(lsValue);
                        if ("error".equals((String) loJSON.get("result"))) {
                            Assert.fail((String) loJSON.get("message"));
                        }
                    }
                    loadRecordAddress();
                    break;
                case 7:
                    /*Longitud*/
                    if (!lsValue.matches("-?\\d+\\.?\\d*")) {
                        loJSON.put("error", lsValue);
                        loJSON.put("message", "Value must contain numbers only");
                        oTrans.Address(pnAddress).getModel().setLongitude("0.0");
                        ShowMessageFX.Information((String) loJSON.get("message"), "Computerized Acounting System", pxeModuleName);
                    } else {
                        loJSON = oTrans.Address(pnAddress).getModel().setLongitude(lsValue);
                        if ("error".equals((String) loJSON.get("result"))) {
                            Assert.fail((String) loJSON.get("message"));
                        }
                    }
                    loadRecordAddress();
                    break;
            }

        } else {
            // txtContact.selectAll();
        }
    };

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
            lsValue = lsValue.trim();
            switch (lnIndex) {
                case 1:
                    /*Email.*/
                    loJSON = oTrans.Mail(pnEmail).getModel().setMailAddress(lsValue);
                    if ("error".equalsIgnoreCase(loJSON.get("result").toString())) {
                        ShowMessageFX.Information((String) loJSON.get("message"), "Computerized Acounting System", pxeModuleName);
                        mailFields01.clear();
                    }

                    oTrans.Mail(pnEmail).getModel().setMailAddress(lsValue);
                    break;
            }
            loadRecordEmail();

        } else {
            // txtContact.selectAll();
        }
    };

    final ChangeListener<? super Boolean> socialmedia_Focus = (o, ov, nv) -> {
        JSONObject loJSON;
        if (!pbLoaded) {
            return;
        }

        TextField txtSocialMedia = (TextField) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtSocialMedia.getId().substring(txtSocialMedia.getId().length() - 2));
        String lsValue = (txtSocialMedia.getText() == null ? "" : txtSocialMedia.getText());
        JSONObject jsonObject = new JSONObject();

        if (lsValue == null) {
            return;
        }
        if (!nv) {
            /*Lost Focus*/
            lsValue = lsValue.trim();
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
            lsValue = lsValue.trim();
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
            lsValue = lsValue.trim();
            switch (lnIndex) {
                case 1:
                    /*Mobile No.*/
                    loJSON = oTrans.Mobile(pnMobile).getModel().setMobileNo(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        ShowMessageFX.Information((String) loJSON.get("message"), "Computerized Acounting System", pxeModuleName);
                    }
                    break;
            }
            oTrans.Mobile(pnMobile).getModel().setMobileNetwork(CommonUtils.classifyNetwork(lsValue));
            loadRecordMobile();
        } else {
            // txtContact.selectAll();
        }
    };

    private void InitSocialMediaTextFields() {
        /*MOBILE INFO FOCUSED PROPERTY*/
        txtSocial01.focusedProperty().addListener(socialmedia_Focus);
        txtSocial02.focusedProperty().addListener(txtAreaSocialMedia_Focus);
    }

    private void InitEmailTextFields() {
        /*MOBILE INFO FOCUSED PROPERTY*/
        mailFields01.focusedProperty().addListener(email_Focus);
    }

    private void InitMobileTextFields() {
        /*MOBILE INFO FOCUSED PROPERTY*/
        txtMobile01.focusedProperty().addListener(mobile_Focus);
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
//        personalinfo11.focusedProperty().addListener(personalinfo_Focus);
        personalinfo12.focusedProperty().addListener(personalinfo_Focus);
        personalinfo13.focusedProperty().addListener(personalinfo_Focus);
        personalinfo14.focusedProperty().addListener(personalinfo_Focus);
        personalinfo15.focusedProperty().addListener(personalinfo_Focus);

        personalinfo06.setOnKeyPressed(this::personalinfo_KeyPressed);
        personalinfo08.setOnKeyPressed(this::personalinfo_KeyPressed);
        personalinfo11.setOnKeyPressed(this::personalinfo_KeyPressed);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        personalinfo07.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {

                return (date != null) ? date.format(formatter) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
            }
        });

        personalinfo07.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Lost focus
                LocalDate selectedDate = personalinfo07.getValue();
                System.out.println("this is date " + selectedDate);
                LocalDate localbdate = LocalDate.parse(selectedDate.toString(), formatter);
                String formattedDate = formatter.format(selectedDate);
                oTrans.Master().getModel().setBirthDate((SQLUtil.toDate(formattedDate, "yyyy-MM-dd")));
                personalinfo07.setValue(localbdate);
            }
        });

    }

    private void loadRecordPersonalInfo() {
        personalinfo02.setText(oTrans.Master().getModel().getLastName());
        personalinfo03.setText(oTrans.Master().getModel().getFirstName());
        personalinfo04.setText(oTrans.Master().getModel().getMiddleName());
        personalinfo05.setText(oTrans.Master().getModel().getSuffixName());

        personalinfo06.setText(oTrans.Master().getModel().Citizenship().getNationality());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        personalinfo07.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? date.format(formatter) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
            }
        }
        );

        if (!oTrans.Master().getModel().getBirthDate().equals("")) {
            Object lobirthdate = oTrans.Master().getModel().getBirthDate();
            if (lobirthdate == null) {
                // If the object is null, set the DatePicker to the current date
                personalinfo07.setValue(LocalDate.now());
            } else if (lobirthdate instanceof Timestamp) {
                // If the object is a Timestamp, convert it to LocalDate
                Timestamp timestamp = (Timestamp) lobirthdate;
                LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
                personalinfo07.setValue(localDate);
            } else if (lobirthdate instanceof Date) {
                // If the object is a java.sql.Date, convert it to LocalDate
                Date sqlDate = (Date) lobirthdate;
                LocalDate localDate = sqlDate.toLocalDate();
                personalinfo07.setValue(localDate);
            } else {
            }
        }

        if (!oTrans.Master().getModel().getBirthPlaceId().equals("")) {

            personalinfo08.setText(oTrans.Master().getModel().BirthTown().getTownName() + ", " + oTrans.Master().getModel().BirthTown().Province().getProvinceName());
        }
        int lsGender = Integer.parseInt(oTrans.Master().getModel().getGender());
        personalinfo09.getSelectionModel().select(lsGender);

        int lsCivilStatus = Integer.parseInt(oTrans.Master().getModel().getCivilStatus());
        personalinfo10.getSelectionModel().select(lsCivilStatus);

        personalinfo11.setText(loadSpouseName(oTrans.Master().getModel().getSpouseId()));
        personalinfo12.setText(oTrans.Master().getModel().getMothersMaidenName());
        personalinfo13.setText(oTrans.Master().getModel().getTaxIdNumber());
        personalinfo14.setText(oTrans.Master().getModel().getLTOClientId());
        personalinfo15.setText(oTrans.Master().getModel().getPhNationalId());

    }

    private void loadRecordAddress() {
        loadMasterAddress();

        int lnCtr;
        int lnCtr2 = 0;
        address_data.clear();

        if (oTrans.getAddressCount() >= 0) {
            for (lnCtr = 0; lnCtr < oTrans.getAddressCount(); lnCtr++) {

                oTrans.Address(lnCtr2).getModel().Town().openRecord(oTrans.Address(lnCtr2).getModel().getTownId());

                try {
                    oTrans.Address(lnCtr2).getModel().Barangay().openRecord(oTrans.Address(lnCtr2).getModel().getBarangayId());
                } catch (Exception e) {
                }

                address_data.add(new ModelAddress(String.valueOf(lnCtr + 1),
                        (String) oTrans.Address(lnCtr2).getModel().getValue("sHouseNox"),
                        (String) oTrans.Address(lnCtr2).getModel().getValue("sAddressx"),
                        (String) oTrans.Address(lnCtr2).getModel().Town().getTownName(),
                        (String) oTrans.Address(lnCtr2).getModel().Barangay().getBarangayName()
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
                pnMobile = 0;
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
        int lsSocMedType = 0;
        if (oTrans.getSocMedCount() > 0) {
            lsSocMedType = Integer.parseInt(oTrans.SocialMedia(pnSocialMedia).getModel().getSocMedType());
            cmbSocMed01.getSelectionModel().select(lsSocMedType);

            txtSocial01.setText(oTrans.SocialMedia(pnSocialMedia).getModel().getAccount());
            txtSocial02.setText(oTrans.SocialMedia(pnSocialMedia).getModel().getRemarks());
            cbSocMed01.setSelected((String) oTrans.SocialMedia(pnSocialMedia).getModel().getRecordStatus() == "1" ? true : false);

        }
    }

    private void getSelectedAddress() {

        if (oTrans.getAddressCount() > 0) {
            AddressField01.setText(oTrans.Address(pnAddress).getModel().getHouseNo());
            AddressField02.setText(oTrans.Address(pnAddress).getModel().getAddress());

            AddressField03.setText(oTrans.Address(pnAddress).getModel().Town().Province().getProvinceName());

            AddressField04.setText(oTrans.Address(pnAddress).getModel().Town().getTownName());
            AddressField05.setText(oTrans.Address(pnAddress).getModel().Barangay().getBarangayName());
            AddressField06.setText(String.valueOf(oTrans.Address(pnAddress).getModel().getLatitude()));
            AddressField07.setText(String.valueOf(oTrans.Address(pnAddress).getModel().getLongitude()));

            cbAddress01.setSelected(((String) oTrans.Address(pnAddress).getModel().getRecordStatus() == "1") ? true : false);
            cbAddress02.setSelected(oTrans.Address(pnAddress).getModel().isPrimaryAddress());
            cbAddress03.setSelected(oTrans.Address(pnAddress).getModel().isOfficeAddress());
            cbAddress04.setSelected(oTrans.Address(pnAddress).getModel().isProvinceAddress());
            cbAddress05.setSelected(oTrans.Address(pnAddress).getModel().isBillingAddress());
            cbAddress06.setSelected(oTrans.Address(pnAddress).getModel().isShippingAddress());
            cbAddress07.setSelected(oTrans.Address(pnAddress).getModel().isCurrentAddress());
            cbAddress08.setSelected(oTrans.Address(pnAddress).getModel().isLTMSAddress());
        }
    }

    private void getSelectedEmail() {
        if (oTrans.getMailCount() > 0) {
            mailFields01.setText(oTrans.Mail(pnEmail).getModel().getMailAddress());

            int lsOwnerType = 0;
            lsOwnerType = Integer.parseInt(oTrans.Mail(pnEmail).getModel().getOwnershipType());
            cbEmail01.setSelected(oTrans.Mail(pnEmail).getModel().getRecordStatus() == "1" ? true : false);
            cbEmail02.setSelected(oTrans.Mail(pnEmail).getModel().isPrimaryEmail());
            cmbEmail01.getSelectionModel().select(lsOwnerType);

        }
    }

    private void getSelectedMobile() {
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
            cbMobileNo01.setSelected(((String) oTrans.Mobile(pnMobile).getModel().getRecordStatus() == "1") ? true : false);

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
                        case 1: //
                            System.out.println("Checkbox 01 selected");

                            loJSON = oTrans.Address(pnAddress).getModel().setRecordStatus(checkbox.isSelected() ? "1" : "0");
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            getSelectedAddress();
                            break;
                        case 2: // Primary Address || Restricted to 1 Primary Address
                            System.out.println("" + oTrans.Address(pnAddress).getModel().getAddressId());
                            boolean primaryAddressExists = false;
                            for (int in = 0; in < oTrans.getAddressCount(); in++) {
                                if (oTrans.Address(in).getModel().isPrimaryAddress()) {
                                    primaryAddressExists = true;
                                    if (oTrans.Address(in).getModel().getAddressId() == oTrans.Address(pnMobile).getModel().getAddressId()) {
                                        if (ShowMessageFX.YesNo("There will be no primary address, proceed? \n", "Computerized Acounting System", pxeModuleName)) {
                                            primaryAddressExists = false;
                                            oTrans.Address(in).getModel().isPrimaryAddress(false);
                                        }
                                    } else {
                                        if (ShowMessageFX.YesNo("Do you want to change the current primary address? \n", "Computerized Acounting System", pxeModuleName)) {
                                            primaryAddressExists = false;
                                            oTrans.Address(in).getModel().isPrimaryAddress(false);
                                        }

                                    }
                                    break;
                                }
                            }
                            if (!primaryAddressExists) {
                                loJSON = oTrans.Address(pnMobile).getModel().isPrimaryAddress(checkbox.isSelected());
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
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
                    loadRecordAddress();
                } catch (NumberFormatException e) {
                }

            }
            );
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
                        case 1:
                            loJSON = oTrans.Mail(pnEmail).getModel().setRecordStatus(checkbox.isSelected() ? "1" : "0");
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        case 2: // Primary Email
                            boolean primaryEmailExists = false;
                            for (int in = 0; in < oTrans.getMailCount(); in++) {
                                if (oTrans.Mail(in).getModel().isPrimaryEmail()) {
                                    primaryEmailExists = true;
                                    if (oTrans.Mail(in).getModel().getEmailId() == oTrans.Mail(pnEmail).getModel().getEmailId()) {
                                        if (ShowMessageFX.YesNo("There will be no primary email, proceed? \n", "Computerized Acounting System", pxeModuleName)) {
                                            primaryEmailExists = false;
                                            oTrans.Mail(in).getModel().isPrimaryEmail(false);
                                        }

                                    } else {
                                        if (ShowMessageFX.YesNo("Do you want to change the current primary email? \n", "Computerized Acounting System", pxeModuleName)) {
                                            primaryEmailExists = false;
                                            oTrans.Mail(in).getModel().isPrimaryEmail(false);
                                        }
                                    }
                                    break;
                                }
                            }
                            if (!primaryEmailExists) {
                                loJSON = oTrans.Mail(pnEmail).getModel().isPrimaryEmail(checkbox.isSelected());
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
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
                String id = checkbox.getId();

                String numberPart = id.substring(id.length() - 2);
                try {
                    int number = Integer.parseInt(numberPart);
                    switch (number) {
                        case 1:
                            System.out.println("Checkbox 01 selected");
                            oTrans.SocialMedia(pnSocialMedia).getModel().setRecordStatus(checkbox.isSelected() ? "1" : "0");
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
                        case 1:
                            loJSON = oTrans.Mobile(pnMobile).getModel().setRecordStatus(checkbox.isSelected() ? "1" : "0");
                            if ("error".equals((String) loJSON.get("result"))) {
                                Assert.fail((String) loJSON.get("message"));
                            }
                            break;
                        case 2: // Primary Mobile
                            boolean primaryMobileExists = false;
                            for (int in = 0; in < oTrans.getMobileCount(); in++) {
                                if (oTrans.Mobile(in).getModel().isPrimaryMobile()) {
                                    primaryMobileExists = true;
                                    if (oTrans.Mobile(in).getModel().getMobileId() == oTrans.Mobile(pnMobile).getModel().getMobileId()) {
                                        if (ShowMessageFX.YesNo("There will be no primary mobile, proceed? \n", "Computerized Acounting System", pxeModuleName)) {
                                            primaryMobileExists = false;
                                            oTrans.Mobile(in).getModel().isPrimaryMobile(false);
                                        }
                                    } else {
                                        if (ShowMessageFX.YesNo("Do you want to change the current primary mobile? \n", "Computerized Acounting System", pxeModuleName)) {
                                            primaryMobileExists = false;
                                            oTrans.Mobile(in).getModel().isPrimaryMobile(false);
                                        }
                                    }
                                    break;
                                }
                            }
                            if (!primaryMobileExists) {
                                loJSON = oTrans.Mobile(pnMobile).getModel().isPrimaryMobile(checkbox.isSelected());
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
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
            if (event.getClickCount() == 1) {
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
    public void initComboboxes() {
        // Personal info
        oTrans.Master().getModel().setGender("0");
        oTrans.Master().getModel().setCivilStatus("0");

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
        personalinfo09.getSelectionModel().select(0);
        personalinfo10.getSelectionModel().select(0);

        // Mobile
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

        // Email
        cmbEmail01.setItems(emailOwn);
        cmbEmail01.setOnAction(event -> {
            int selectedIndex = cmbEmail01.getSelectionModel().getSelectedIndex();
            oTrans.Mail(pnEmail).getModel().setOwnershipType(String.valueOf(selectedIndex));
            cmbEmail01.getSelectionModel().select(selectedIndex);
            loadRecordEmail();
        });

        // Social Media
        cmbSocMed01.setItems(socialTyp);
        cmbSocMed01.setOnAction(event -> {
            int selectedIndex = cmbSocMed01.getSelectionModel().getSelectedIndex();
            oTrans.SocialMedia(pnSocialMedia).getModel().setSocMedType(String.valueOf(selectedIndex));
            cmbSocMed01.getSelectionModel().select(selectedIndex);
            loadRecordSocialMedia();
        });

    }

    public void initTextFieldsAutoFormat() {
        TextField[] textFields = {personalinfo15, personalinfo14, personalinfo13};
        for (int i = 0; i < textFields.length; i++) {
            TextField textField = textFields[i];

            textField.setOnKeyTyped((KeyEvent e) -> {
                if (e.isControlDown() || e.isMetaDown()) {
                    return;
                }
                String text = textField.getText().replaceAll("[^\\d]", "");
                if (textField.getId().equals("personalinfo13")) {
                    if (text.length() >= 9) {
                        e.consume();  // Prevent additional input beyond 9 digits
                        text = text.substring(0, 9);
                    }
                } else {
                    if (text.length() >= 11) {
                        e.consume();  // Prevent additional input beyond 9 digits
                        text = text.substring(0, 11);
                    }
                }
                textField.setText(formatInput(text));  // Format and set the text
                textField.positionCaret(textField.getText().length()); // Move cursor to end
            });
        }
    }

    private String formatInput(String digits) {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            if (i == 3 || i == 5) {
                formatted.append("-");  // Insert dash after 3rd and 5th digits
            }
            formatted.append(digits.charAt(i));
        }
        return formatted.toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.setProperty("sys.default.path.temp", "D:/GGC_Maven_Systems/temp/");
        System.setProperty("sys.default.path.metadata", "D:/GGC_Maven_Systems/config/metadata/new/");
        boolean[] booleanArray = {true, true, true, true};
        oApp = MiscUtil.Connect();

        oTrans = new Client(oApp, "", null);

        //Reference in GUI_IndividualNew for new record or open & update record
        Boolean lbOption = true;
        if (pnEditMode == EditMode.UPDATE) {
            lbOption = false;
        }
        Boolean lbOption1 = true;

        if (lbOption) {
            // NEW RECORD
            oTrans.New();
            System.out.println(oTrans.Mobile(0).getModel().getRecordStatus());

            oTrans.Master().getModel().setClientType(ClientType.INDIVIDUAL);
            pnEditMode = EditMode.ADDNEW;
        } else {
            // OPEN RECORD
            oTrans.Master().openRecord(lsID);
            oTrans.OpenClientAddress(lsID);
            oTrans.OpenClientMobile(lsID);
            oTrans.OpenClientMail(lsID);
            oTrans.OpenClientSocialMedia(lsID);

            if (oTrans.getAddressCount() <= 0) {
                booleanArray[0] = false;
            }
            if (oTrans.getMobileCount() <= 0) {
                booleanArray[1] = false;
            }
            if (oTrans.getMailCount() <= 0) {
                booleanArray[2] = false;
            }
            if (oTrans.getSocMedCount() <= 0) {
                booleanArray[3] = false;
            }
        }

        initButton();
        InitPersonalInfoTextFields();

        InitAddressTextFields();
        initAddressCheckbox();

        InitMobileTextFields();
        initMobileCheckbox();

        InitEmailTextFields();
        initEmailCheckbox();

        InitSocialMediaTextFields();
        initSocialMediaCheckbox();

        initAddressGrid();
        initSocialMediaGrid();
        initEmailGrid();
        initMobileGrid();

        clearMaster();
        clearAddress();
        clearEmail();
        clearSocMed();
        clearMobile();

        initComboboxes();
        if (lbOption) {
            loadRecordMobile();
            loadRecordAddress();
            loadRecordSocialMedia();
            loadRecordEmail();
        } else {
            if (booleanArray[0]) {
                loadRecordMobile();
            }
            if (booleanArray[1]) {
                loadRecordAddress();
            }
            if (booleanArray[2]) {
                loadRecordSocialMedia();
            }
            if (booleanArray[3]) {
                loadRecordEmail();
            }
        }

        if (!lbOption) {
            // load when using open record
            loadMasterName();
            loadRecordPersonalInfo();
        }
        initTextFieldsAutoFormat();
        initTableOnClick();
        pbLoaded = true;

        String lsClientID = (String) oTrans.Master().getModel().getClientId();
        if (txtField01 != null) { // Check if txtField01 is not null before setting its text
            oTrans.Master().getModel().setClientId(lsClientID);
            txtField01.setText((String) oTrans.Master().getModel().getClientId());
        } else {
            ShowMessageFX.OkayCancel(oTransnox, lsClientID, "No Client ID");
        }

        personalinfo11.setText(loadSpouseName(oTrans.Master().getModel().getSpouseId()));

    }
}
