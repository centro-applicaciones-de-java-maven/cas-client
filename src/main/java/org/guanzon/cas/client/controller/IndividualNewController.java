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
import org.guanzon.cas.client.Client;
import org.guanzon.cas.client.Client_Master;
import org.guanzon.cas.client.models.ModelAddress;
import org.guanzon.cas.client.models.ModelEmail;
import org.guanzon.cas.client.models.ModelMobile;
import org.guanzon.cas.client.models.ModelSocialMedia;
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
    private TextField AddressField07;
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

    private void clearAddress() {
        // Arrays of TextFields grouped by sections
        TextField[][] allFields = {
            {AddressField01, AddressField02, AddressField03, AddressField04,
                AddressField05, AddressField06}
        };

        // Loop through each array of TextFields and clear them
        for (TextField[] fields : allFields) {
            for (TextField field : fields) {
                field.clear();
            }
        }
    }

    private void clearMobile() {
        txtMobile01.clear();
        cmbMobile01.setItems(mobileOwn);
        cmbMobile01.getSelectionModel().select(0);

        cmbMobile02.setItems(mobileType);
        cmbMobile02.getSelectionModel().select(0);
    }

    private void clearEmail() {
        mailFields01.clear();
        cmbEmail01.setItems(emailOwn);
        cmbEmail01.getSelectionModel().select(0);
        cbEmail02.setSelected(true);
        cbEmail01.setSelected(false);

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
                    switch (tabText) {
                        case "PersonalInfo":
                            ShowMessageFX.OkayCancel(null, "", "Successfully saved!");
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
//                            loJSON = oTrans.Save();
//                            if ("error".equals((String) loJSON.get("result"))) {
//                                Assert.fail((String) loJSON.get("message"));
//                            }
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
                case "btnAddAddress":
                    JSONObject addObjAddress = oTrans.addAddress();
                    System.out.println((String) addObjAddress.get("message"));
                    if ("error".equals((String) addObjAddress.get("result"))) {
                        ShowMessageFX.Information((String) addObjAddress.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }
                    AddressField01.clear();
                    AddressField02.clear();
                    AddressField03.clear();
                    AddressField04.clear();
                    AddressField05.clear();
                    AddressField06.clear();
                    cbAddress01.setSelected(false);
                    cbAddress02.setSelected(false);
                    cbAddress03.setSelected(false);
                    cbAddress04.setSelected(false);
                    cbAddress05.setSelected(false);
                    cbAddress06.setSelected(false);
                    cbAddress07.setSelected(false);
                    cbAddress08.setSelected(false);
                    loadRecordAddress();

                    pnAddress = oTrans.getAddressCount() - 1;
                    tblAddress.getSelectionModel().select(pnAddress + 1);
//                       initAddressGrid();
                    break;

                case "btnAddEmail":
                    JSONObject addObjMail = oTrans.addMail();

                    System.out.println((String) addObjMail.get("message"));
                    if ("error".equals((String) addObjMail.get("result"))) {
                        ShowMessageFX.Information((String) addObjMail.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }
                    mailFields01.clear();
                    loadRecordEmail();
                    pnEmail = oTrans.getMailCount() - 1;
                    tblEmail.getSelectionModel().select(pnEmail + 1);
                    break;
                case "btnAddMobile":
                    JSONObject addObj = oTrans.addMobile();

                    System.out.println((String) addObj.get("message"));
                    if ("error".equals((String) addObj.get("result"))) {
                        ShowMessageFX.Information((String) addObj.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }
                    txtMobile01.clear();
                    loadRecordMobile();
                    pnMobile = oTrans.getMobileCount() - 1;

                    tblMobile.getSelectionModel().select(pnMobile + 1);
                    break;
                case "btnAddSocMed":
                    JSONObject addSocMed = oTrans.addSocialMedia();

                    System.out.println((String) addSocMed.get("message"));
                    if ("error".equals((String) addSocMed.get("result"))) {
                        ShowMessageFX.Information((String) addSocMed.get("message"), "Computerized Acounting System", pxeModuleName);
                        break;
                    }
                    txtSocial01.clear();
                    txtSocial02.clear();
                    pnSocialMedia = oTrans.getSocMedCount() - 1;

                    loadRecordSocialMedia();
                    tblSocMed.getSelectionModel().select(pnSocialMedia + 1);
                    break;

                case "btnDelAddress":
                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
                        oTrans.Address(pnAddress).deleteRecord();
                        if (oTrans.getAddressCount() <= 0) {
                            oTrans.addAddress();
                        }

                        pnAddress = oTrans.getAddressCount() - 1;
                        loadRecordAddress();
                        clearAddress();
                        AddressField01.requestFocus();
                    }
                    break;
                case "btnDelMobile":
                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
                        oTrans.Mobile(pnMobile).deleteRecord();
                        if (oTrans.getMobileCount() <= 0) {
                            oTrans.addMobile();
                        }

                        pnMobile = oTrans.getMobileCount() - 1;
                        loadRecordMobile();
                        clearMobile();
                    }
                    break;
                case "btnDelEmail":
                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
                        oTrans.Mobile(pnEmail).deleteRecord();
                        if (oTrans.getMailCount() <= 0) {
                            oTrans.addMail();
                        }

                        pnEmail = oTrans.getMailCount() - 1;
                        loadRecordEmail();
                        clearEmail();
                    }
                    break;
                case "btnDelSocMed":
                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove these details? ") == true) {
                        oTrans.SocialMedia(pnSocialMedia).deleteRecord();
                        if (oTrans.getSocMedCount() <= 0) {
                            oTrans.addSocialMedia();
                        }

                        pnSocialMedia = oTrans.getSocMedCount() - 1;
                        loadRecordSocialMedia();
                        clearSocMed();
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
                        /*search town for citizenship*/
                        poJson = new JSONObject();
                        poJson = oTrans.Master().searchCitizenship(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            personalinfo06.clear();
                        }
                        personalinfo06.setText((String) poJson.get("sNational"));
                        oTrans.Master().getModel().setCitizenshipId((String) poJson.get("sCntryCde"));

                        break;
                    case 8:
                        /*search birthplace*/
                        poJson = new JSONObject();
                        poJson = oTrans.Master().searchBirthPlace(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            personalinfo08.clear();
                        }
                        personalinfo08.setText((String) poJson.get("xBrthPlce"));
                        oTrans.Master().getModel().setBirthPlaceId((String) poJson.get("sTownIDxx"));
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
        JSONObject poJson;
        switch (event.getCode()) {
            case F3:
                switch (lnIndex) {
                    case 3:
                        /*search province*/
                        poJson = new JSONObject();
                        poJson = oTrans.Master().searchBirthPlace(lsValue, false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            AddressField03.clear();
                        }
                        AddressField03.setText((String) poJson.get("sNational"));
                        oTrans.Master().getModel().setCitizenshipId((String) poJson.get("sCntryCde"));

                        break;
                    case 4:
                        /*search city*/
                        poJson = new JSONObject();
                        poJson = oTrans.Master().SearchTownAddress(pnAddress, lsValue, false);
                        System.out.println("poJson = " + poJson.toJSONString());
                        if("error".equalsIgnoreCase(poJson.get("result").toString())){
                             ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                             AddressField03.clear();
                        }
                        //                setTownID((String) loJSON.get("sTownIDxx"));
//                setTownName((String) loJSON.get("sTownName"));
//                setProvinceName((String) loJSON.get("sProvName"));
//                        AddressField03.setText((String)oTrans.getAddress(pnAddress, 20) + ", " + (String) oTrans.getAddress(pnAddress, 22));
//                        AddressField04.setText((String)oTrans.getAddress(pnAddress, 21));
                        oTrans.Address(pnAddress).getModel().setTownId(lsValue);
//                        oTrans.Address(pnAddress).getModel()
                        break;
                    case 5:
                        /*search barangay*/
                        poJson = new JSONObject();
                        poJson = oTrans.Master().SearchBarangayAddress(1, "", false);
                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
                            AddressField05.clear();
                        }
                        AddressField05.setText((String) poJson.get("xBrthPlce"));
                        oTrans.Master().getModel().setBirthPlaceId((String) poJson.get("sTownIDxx"));
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
                    loJSON = oTrans.Address(0).getModel().setHouseNo(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    break;
                case 2:
                    /*Address*/
                    loJSON = oTrans.Address(0).getModel().setAddress(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    break;
                case 3:
                    /*Province*/
//                   loJSON = oTrans.Address(0).getModel().setHouseNo(lsValue);
//                    if ("error".equals((String) loJSON.get("result"))) {
//                        Assert.fail((String) loJSON.get("message"));
//                    }
                    break;
                case 4:
                    /*City*/
                    loJSON = oTrans.Address(0).getModel().setTownId(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    break;
                case 5:
                    /*Barangay*/
                    loJSON = oTrans.Address(0).getModel().setBarangayId(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    break;
                case 6:
                    /*Latitude*/
                    loJSON = oTrans.Address(0).getModel().setLatitude(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    break;
                case 7:
                    /*Longitud*/
                    loJSON = oTrans.Address(0).getModel().setLongitude(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    break;

            }
            loadRecordAddress();

        } else {
            // txtContact.selectAll();
        }
    };

    private void mobile_KeyPressed(KeyEvent event) {
        TextField address = (TextField) event.getSource();
        int lnIndex = Integer.parseInt(((TextField) event.getSource()).getId().substring(12, 14));
        String lsValue = (address.getText() == null ? "" : address.getText());
        JSONObject poJson;
        switch (event.getCode()) {
            case F3:
                switch (lnIndex) {
                    case 1:
                        /*search mobileNo*/
                        poJson = new JSONObject();
//                        poJson = oTrans.Master().searchBirthPlace(lsValue, false);
//                        if ("error".equalsIgnoreCase(poJson.get("result").toString())) {
//                            ShowMessageFX.Information((String) poJson.get("message"), "Computerized Acounting System", pxeModuleName);
//                            AddressField03.clear();
//                        }
//                        AddressField03.setText((String) poJson.get("sNational"));
//                        oTrans.Master().getModel().setCitizenshipId((String) poJson.get("sCntryCde"));

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

    final ChangeListener<? super Boolean> email_Focus = (o, ov, nv) -> {
        JSONObject loJSON;
        if (!pbLoaded) {
            return;
        }
        TextField txtEmail = (TextField) ((ReadOnlyBooleanPropertyBase) o).getBean();
        int lnIndex = Integer.parseInt(txtEmail.getId().substring(12, 14));
        String lsValue = (txtEmail.getText() == null ? "" : txtEmail.getText());
        JSONObject jsonObject = new JSONObject();
        if (lsValue == null) {
            return;
        }
        if (!nv) {
            /*Lost Focus*/
            switch (lnIndex) {
                case 1:
                    /*House No.*/
                    loJSON = oTrans.Mobile(0).getModel().setMobileNo(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    break;
            }
            loadRecordEmail();

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
            switch (lnIndex) {

                case 2://Remarks
                    loJSON = oTrans.SocialMedia(0).getModel().setRemarks(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        System.err.println((String) loJSON.get("message"));
                        ShowMessageFX.Information(null, pxeModuleName, (String) loJSON.get("message"));
                        return;
                    }
                    break;
            }
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
        int lnIndex = Integer.parseInt(txtMobile.getId().substring(12, 14));
        String lsValue = (txtMobile.getText() == null ? "" : txtMobile.getText());
        JSONObject jsonObject = new JSONObject();
        if (lsValue == null) {
            return;
        }
        if (!nv) {
            /*Lost Focus*/
            switch (lnIndex) {
                case 1:
                    /*House No.*/
                    loJSON = oTrans.Mobile(0).getModel().setMobileNo(lsValue);
                    if ("error".equals((String) loJSON.get("result"))) {
                        Assert.fail((String) loJSON.get("message"));
                    }
                    break;
            }
            loadRecordMobile();

        } else {
            // txtContact.selectAll();
        }
    };

    private void InitSocialMediaTextFields() {
        /*MOBILE INFO FOCUSED PROPERTY*/
        txtSocial01.focusedProperty().addListener(mobile_Focus);
        txtSocial02.focusedProperty().addListener(txtAreaSocialMedia_Focus);
    }

    private void InitEmailTextFields() {
        /*MOBILE INFO FOCUSED PROPERTY*/
        mailFields01.focusedProperty().addListener(mobile_Focus);
        mailFields01.setOnKeyPressed(this::mobile_KeyPressed);
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

    private void loadRecordMobile() {
        mobile_data.clear();
        for (int lnCtr = 0; lnCtr < oTrans.getMobileCount(); lnCtr++) {
            mobile_data.add(new ModelMobile(String.valueOf(lnCtr + 1),
                    oTrans.Mobile(pnMobile).getModel().getValue("sMobileNo").toString(),
                    oTrans.Mobile(pnMobile).getModel().getValue("cOwnerxxx").toString(),
                    oTrans.Mobile(pnMobile).getModel().getValue("cMobileTp").toString()
            ));
        }
    }

    private void loadRecordEmail() {
        email_data.clear();
        for (int lnCtr = 0; lnCtr < oTrans.getMailCount(); lnCtr++) {
            email_data.add(new ModelEmail(String.valueOf(lnCtr + 1),
                    oTrans.Mail(pnEmail).getModel().getValue("cOwnerxxx").toString(),
                    oTrans.Mail(pnEmail).getModel().getValue("sEMailAdd").toString()
            ));
        }
    }

    private void loadRecordSocialMedia() {
        socialmedia_data.clear();
        for (int lnCtr = 0; lnCtr < oTrans.getSocMedCount(); lnCtr++) {
            socialmedia_data.add(new ModelSocialMedia(String.valueOf(lnCtr + 1),
                    oTrans.SocialMedia(pnSocialMedia).getModel().getValue("sAccountx").toString(),
                    oTrans.SocialMedia(pnSocialMedia).getModel().getSocMedType(),
                    oTrans.SocialMedia(pnSocialMedia).getModel().getValue("sRemarksx").toString()
            ));
        }
    }

    private void loadRecordAddress() {
        int lnCtr;
        address_data.clear();
//        oTrans.getAddress(pnAddress).list();
        if (oTrans.getAddressCount() >= 0) {
            for (lnCtr = 0; lnCtr < oTrans.getAddressCount(); lnCtr++) {
//                String lsTown = (String) oTrans.getAddress(lnCtr, 20) + ", " + (String) oTrans.getAddress(lnCtr, 22);
                address_data.add(new ModelAddress(String.valueOf(lnCtr + 1),
                        (String) oTrans.Address(pnAddress).getModel().getValue("sHouseNox"),
                        (String) oTrans.Address(pnAddress).getModel().getValue("sAddressx"),
                        (String) oTrans.Address(pnAddress).getModel().getTownId(),
                        (String) oTrans.Address(pnAddress).getModel().getBarangayId()
                ));

            }
        }

    }

    private void getSelectedSocialMedia() {
        txtSocial01.setText(oTrans.SocialMedia(pnSocialMedia).getModel().getAccount());
        txtSocial02.setText(oTrans.SocialMedia(pnSocialMedia).getModel().getRemarks());
    }

    private void getSelectedAddress() {
        TextField[] fields = {AddressField01, AddressField02, AddressField03, AddressField04,
            AddressField05, AddressField06, AddressField07};
        for (TextField field : fields) {
            field.clear();
        }

        CheckBox[] checkboxs = {cbAddress01, cbAddress02, cbAddress03,
            cbAddress04, cbAddress05, cbAddress06, cbAddress07, cbAddress08};

        // Loop through each array of TextFields and clear them
        for (CheckBox checkbox : checkboxs) {
            checkbox.setSelected(false);
        }

        if (oTrans.getAddressCount() > 0) {
            AddressField01.setText(oTrans.Address(pnAddress).getModel().getHouseNo());
            AddressField02.setText(oTrans.Address(pnAddress).getModel().getAddress());
//          AddressField03.setText(oTrans.Address(0).getModel().get());
//            AddressField03.setText((String) oTrans.getAddress(pnAddress, 20) + ", " + (String) oTrans.getAddress(pnAddress, 22));
            AddressField04.setText(oTrans.Address(pnAddress).getModel().getTownId());
            AddressField05.setText(oTrans.Address(pnAddress).getModel().getBarangayId());
            AddressField06.setText(String.valueOf(oTrans.Address(pnAddress).getModel().getLatitude()));
            AddressField07.setText(String.valueOf(oTrans.Address(pnAddress).getModel().getLongitude()));

            cbAddress01.setSelected(((String) oTrans.Address(pnAddress).getModel().getRecordStatus() == "1") ? true : false);
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
        mailFields01.setText(oTrans.Mail(0).getModel().getEmailId());
    }

    private void getSelectedMobile() {
        txtMobile01.setText(oTrans.Address(0).getModel().getHouseNo());
    }

    private void initAddressCheckbox() {
        CheckBox[] cbAddressCheckboxes = {cbAddress01, cbAddress02, cbAddress03, cbAddress04, cbAddress05, cbAddress06, cbAddress07, cbAddress08};

        for (int i = 0; i < cbAddressCheckboxes.length; i++) {
            final CheckBox checkbox = cbAddressCheckboxes[i]; // Capture the current checkbox
            checkbox.setOnMouseClicked(event -> {
                JSONObject loJSON;
                if (checkbox.isSelected()) {
                    String id = checkbox.getId();
                    String numberPart = id.substring(10, 12);
                    try {
                        int number = Integer.parseInt(numberPart);
                        switch (number) {
                            case 1: // ask if this is crecordstat // isActive
                                System.out.println("Checkbox 01 selected");
                                break;
                            case 2: // Primary Address
                                loJSON = oTrans.Address(0).getModel().isPrimaryAddress(true);
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
                                break;
                            case 3: //Office
                                loJSON = oTrans.Address(0).getModel().isOfficeAddress(true);
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
                                break;
                            case 4: // Province
                                loJSON = oTrans.Address(0).getModel().isProvinceAddress(true);
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
                                break;
                            case 5: // Billing
                                loJSON = oTrans.Address(0).getModel().isBillingAddress(true);
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
                                break;
                            case 6: // Shipping
                                loJSON = oTrans.Address(0).getModel().isShippingAddress(true);
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
                                break;
                            case 7: // Current
                                loJSON = oTrans.Address(0).getModel().isCurrentAddress(true);
                                if ("error".equals((String) loJSON.get("result"))) {
                                    Assert.fail((String) loJSON.get("message"));
                                }
                                break;
                            case 8: // LTMS
                                loJSON = oTrans.Address(0).getModel().isLTMSAddress(true);
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

                }
            });
        }
    }

    private void initEmailCheckbox() {
        CheckBox[] cbEmailCheckboxes = {cbMobileNo01, cbMobileNo02};

        for (int i = 0; i < cbEmailCheckboxes.length; i++) {
            final CheckBox checkbox = cbEmailCheckboxes[i]; // Capture the current checkbox
            checkbox.setOnMouseClicked(event -> {
                JSONObject loJSON;
                if (checkbox.isSelected()) {
                    String id = checkbox.getId();
                    String numberPart = id.substring(id.length() - 2);
                    try {
                        int number = Integer.parseInt(numberPart);
                        switch (number) {
                            case 1: // ask if this is crecordstat // isActive
                                System.out.println("Checkbox 01 selected");
                                break;
                            case 2: // Primary Address
                                loJSON = oTrans.Mail(0).getModel().isPrimaryEmail(true);
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
                if (checkbox.isSelected()) {
                    String id = checkbox.getId();
                    String numberPart = id.substring(id.length() - 2);
                    try {
                        int number = Integer.parseInt(numberPart);
                        switch (number) {
                            case 1: // ask if this is crecordstat // isActive
                                System.out.println("Checkbox 01 selected");
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

    private void initMobileCheckbox() {
        CheckBox[] cbMobileCheckboxes = {cbMobileNo01, cbMobileNo02};

        for (int i = 0; i < cbMobileCheckboxes.length; i++) {
            final CheckBox checkbox = cbMobileCheckboxes[i]; // Capture the current checkbox
            checkbox.setOnMouseClicked(event -> {
                JSONObject loJSON;
                if (checkbox.isSelected()) {
                    String id = checkbox.getId();
                    String numberPart = id.substring(id.length() - 2);
                    try {
                        int number = Integer.parseInt(numberPart);
                        switch (number) {
                            case 1: // ask if this is crecordstat // isActive
                                System.out.println("Checkbox 01 selected");
                                break;
                            case 2: // Primary Address
                                loJSON = oTrans.Mobile(0).getModel().isPrimaryMobile(true);
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
                pnAddress = tblAddress.getSelectionModel().getSelectedIndex();
                getSelectedMobile();
            }
        });
        tblEmail.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // Detect single click (or use another condition for double click)
                pnAddress = tblAddress.getSelectionModel().getSelectedIndex();
                getSelectedEmail();
            }
        });

        tblSocMed.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // Detect single click (or use another condition for double click)
                pnAddress = tblAddress.getSelectionModel().getSelectedIndex();
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
        oTrans = new Client(oApp, "", null);

        oTrans.Master().getModel().setClientType(ClientType.INDIVIDUAL);

        initButton();
        InitPersonalInfoTextFields();
        pbLoaded = true;

        personalinfo09.setItems(gender);

        personalinfo09.setOnAction(event -> {
            String selectedItem = String.valueOf(personalinfo09.getSelectionModel().getSelectedIndex());
            ShowMessageFX.OkayCancel(oTransnox, selectedItem, selectedItem);
            switch (selectedItem) {
                case "0":
                    oTrans.Master().getModel().setGender("0");
                    break;
                case "1":
                    oTrans.Master().getModel().setGender("1");
                    break;
                case "2":
                    oTrans.Master().getModel().setGender("2");
                    break;
            }
            personalinfo09.setValue(personalinfo09.getValue());
        });

        personalinfo10.setItems(civilstatus);
        personalinfo10.setOnAction(event -> {
            String selectedItem = String.valueOf(personalinfo10.getSelectionModel().getSelectedIndex());
            switch (selectedItem) {
                case "0":
                    oTrans.Master().getModel().setCivilStatus("0");
                    break;
                case "1":
                    oTrans.Master().getModel().setCivilStatus("1");
                    break;
                case "2":
                    oTrans.Master().getModel().setCivilStatus("2");
                    break;
                case "3":
                    oTrans.Master().getModel().setCivilStatus("3");
                    break;
            }
            personalinfo10.setValue(personalinfo10.getValue());

        });

        InitAddressTextFields();
        initAddressCheckbox();

        InitMobileTextFields();
        initMobileCheckbox();

        cmbMobile01.setItems(mobileOwn);
        cmbMobile01.setOnAction(event -> {
            String selectedItem = (String) cmbMobile01.getValue();
            oTrans.Mobile(0).getModel().setClientId(selectedItem);
            cmbMobile01.setValue(selectedItem);
        });

        cmbMobile02.setItems(mobileType);
        cmbMobile02.setOnAction(event -> {
            String selectedItem = (String) cmbMobile02.getValue();
            oTrans.Mobile(0).getModel().setMobileType(selectedItem);
            cmbMobile02.setValue(selectedItem);
        });

        InitEmailTextFields();
        initEmailCheckbox();

        cmbEmail01.setItems(emailOwn);
        cmbEmail01.setOnAction(event -> {
            String selectedItem = (String) cmbEmail01.getValue();
            oTrans.Mail(0).getModel().setClientId(selectedItem);
            cmbEmail01.setValue(selectedItem);
        });

        InitSocialMediaTextFields();
        initSocialMediaCheckbox();

        cmbSocMed01.setItems(socialTyp);
        cmbSocMed01.setOnAction(event -> {
            String selectedItem = (String) cmbSocMed01.getValue();
            oTrans.SocialMedia(0).getModel().setClientId(selectedItem);
            cmbSocMed01.setValue(selectedItem);
        });

        initAddressGrid();
        initSocialMediaGrid();
        initEmailGrid();
        initMobileGrid();

    }

}
