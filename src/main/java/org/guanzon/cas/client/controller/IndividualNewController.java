package org.guanzon.cas.client.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.guanzon.appdriver.agent.ShowMessageFX;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.client.ClientInfo;
import org.guanzon.cas.client.services.ClientControllers;

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
    private DatePicker txtPersonal07;
    @FXML
    private TextField txtPersonal08;
    @FXML
    private ComboBox cmbPersonal09;
    @FXML
    private ComboBox cmbPersonal10;
    @FXML
    private TextField personalinfo11;
    @FXML
    private TextField personalinfo12;
    @FXML
    private TextField txtPersonal13;
    @FXML
    private TextField txtPersonal15;
    @FXML
    private TextField personalinfo14;
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
    private ComboBox<?> cmbEmail01;
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
    
    private boolean pbLoaded;
    
    public void setGRider(GRiderCAS griderCAS){
        poGRider = griderCAS;
    }
    
    public void setLogWrapper(LogWrapper wrapper){
        poWrapper = wrapper;
    }
    
    public  void setClientId(String clientId){
        psClientID = clientId;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        try {
            if (poGRider == null){
                ShowMessageFX.Warning("Application driver is not set.", "Warning", MODULE);
                System.exit(1);
            }
                        
            txtPersonal02.focusedProperty().addListener(txtPersonal_Focus);
            txtPersonal03.focusedProperty().addListener(txtPersonal_Focus);
            txtPersonal04.focusedProperty().addListener(txtPersonal_Focus);
            txtPersonal05.focusedProperty().addListener(txtPersonal_Focus);
            txtPersonal06.focusedProperty().addListener(txtPersonal_Focus);
            txtPersonal07.focusedProperty().addListener(txtPersonal_Focus);
            txtPersonal08.focusedProperty().addListener(txtPersonal_Focus);
            txtPersonal08.focusedProperty().addListener(txtPersonal_Focus);
            txtPersonal13.focusedProperty().addListener(txtPersonal_Focus);
            txtPersonal15.focusedProperty().addListener(txtPersonal_Focus);
            
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
            
            tblAddress.setOnMouseClicked(this::address_Clicked);
                        
            poClient = new ClientControllers(poGRider, poWrapper).ClientInfo();
            
            pnEditMode = psClientID.isEmpty() ? EditMode.ADDNEW : EditMode.UPDATE;
            
            pbLoaded = true;
        } catch (SQLException | GuanzonException e) {
            ShowMessageFX.Warning(e.getMessage(), "Error", MODULE);
            System.exit(1);
        }
    }        
    
    private void cmdButton_Click(ActionEvent event) {
        
    }
    
    private void address_Clicked(MouseEvent event) {
        System.err.println("address clicked.");
        pnAddress = tblAddress.getSelectionModel().getSelectedIndex();
    }
    
    final ChangeListener<? super Boolean> txtPersonal_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(11, 13));
        
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){//lost focus
            switch(lnIndex){
            
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
}