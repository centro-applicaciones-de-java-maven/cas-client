package org.guanzon.cas.client.services;

import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.cas.client.model.Model_AP_Client_Ledger;
import org.guanzon.cas.client.model.Model_AP_Client_Master;
import org.guanzon.cas.client.model.Model_Account_Client_Accreditation;
import org.guanzon.cas.client.model.Model_Client_Address;
import org.guanzon.cas.client.model.Model_Client_Institution_Contact;
import org.guanzon.cas.client.model.Model_Client_Mail;
import org.guanzon.cas.client.model.Model_Client_Master;
import org.guanzon.cas.client.model.Model_Client_Mobile;
import org.guanzon.cas.client.model.Model_Client_Social_Media;
public class ClientModels {
    public ClientModels(GRiderCAS applicationDriver) {
        poGRider = applicationDriver;
    }

    public Model_Client_Master ClientMaster() {
        if (poGRider == null) {
            System.err.println("ClientModel.ClientMaster: Application driver is not set.");
            return null;
        }

        if (poClientMaster == null) {
            poClientMaster = new Model_Client_Master();
            poClientMaster.setApplicationDriver(poGRider);
            poClientMaster.setXML("Model_Client_Master");
            poClientMaster.setTableName("Client_Master");
            poClientMaster.initialize();
        }

        return poClientMaster;
    }
    
    public Model_Client_Address ClientAddress() {
        if (poGRider == null) {
            System.err.println("ClientModel.ClientAddress: Application driver is not set.");
            return null;
        }

        if (poClientAddress == null) {
            poClientAddress = new Model_Client_Address();
            poClientAddress.setApplicationDriver(poGRider);
            poClientAddress.setXML("Model_Client_Address");
            poClientAddress.setTableName("Client_Address");
            poClientAddress.initialize();
        }

        return poClientAddress;
    }

    public Model_Client_Institution_Contact ClientInstitutionContact() {
        if (poGRider == null) {
            System.err.println("ClientModel.ClientInstitutionContact: Application driver is not set.");
            return null;
        }

        if (poClientInstitutionContact == null) {
            poClientInstitutionContact = new Model_Client_Institution_Contact();
            poClientInstitutionContact.setApplicationDriver(poGRider);
            poClientInstitutionContact.setXML("Model_Client_Institution_Contact_Person");
            poClientInstitutionContact.setTableName("Client_Institution_Contact_Person");
            poClientInstitutionContact.initialize();
        }

        return poClientInstitutionContact;
    }
        
    public Model_Client_Mobile ClientMobile() {
        if (poGRider == null) {
            System.err.println("ClientModel.ClientMobile: Application driver is not set.");
            return null;
        }

        if (poClientMobile == null) {
            poClientMobile = new Model_Client_Mobile();
            poClientMobile.setApplicationDriver(poGRider);
            poClientMobile.setXML("Model_Client_Mobile");
            poClientMobile.setTableName("Client_Mobile");
            poClientMobile.initialize();
        }

        return poClientMobile;
    }
        
    public Model_Client_Mail ClientMail() {
        if (poGRider == null) {
            System.err.println("ClientModel.ClientMail: Application driver is not set.");
            return null;
        }

        if (poClientMail == null) {
            poClientMail = new Model_Client_Mail();
            poClientMail.setApplicationDriver(poGRider);
            poClientMail.setXML("Model_Client_eMail_Address");
            poClientMail.setTableName("Client_eMail_Address");
            poClientMail.initialize();
        }

        return poClientMail;
    }
        
    public Model_Client_Social_Media ClientSocMed() {
        if (poGRider == null) {
            System.err.println("ClientModel.ClientSocialMedia: Application driver is not set.");
            return null;
        }

        if (poClientSocmed == null) {
            poClientSocmed = new Model_Client_Social_Media();
            poClientSocmed.setApplicationDriver(poGRider);
            poClientSocmed.setXML("Model_Client_Social_Media");
            poClientSocmed.setTableName("Client_Social_Media");
            poClientSocmed.initialize();
        }

        return poClientSocmed;
    }    
    
    public Model_Account_Client_Accreditation ClientAccreditation() {
        if (poGRider == null) {
            System.err.println("ClientModel.ClientAccreditation: Application driver is not set.");
            return null;
        }

        if (poClientAccreditation == null) {
            poClientAccreditation = new Model_Account_Client_Accreditation();
            poClientAccreditation.setApplicationDriver(poGRider);
            poClientAccreditation.setXML("Model_Account_Client_Accreditation");
            poClientAccreditation.setTableName("Account_Client_Accreditation");
            poClientAccreditation.initialize();
        }

        return poClientAccreditation;
    }   
    
    public Model_AP_Client_Master APClientMaster() {
        if (poGRider == null) {
            System.err.println("ClientModel.APClientMaster: Application driver is not set.");
            return null;
        }

        if (poAPClientMaster == null) {
            poAPClientMaster = new Model_AP_Client_Master();
            poAPClientMaster.setApplicationDriver(poGRider);
            poAPClientMaster.setXML("Model_AP_Client_Master");
            poAPClientMaster.setTableName("AP_Client_Master");
            poAPClientMaster.initialize();
        }

        return poAPClientMaster;
    }
    
    public Model_AP_Client_Ledger APClientLedger() {
        if (poGRider == null) {
            System.err.println("ClientModel.APClientLedger: Application driver is not set.");
            return null;
        }

        if (poAPClientLedger == null) {
            poAPClientLedger = new Model_AP_Client_Ledger();
            poAPClientLedger.setApplicationDriver(poGRider);
            poAPClientLedger.setXML("Model_AP_Client_Ledger");
            poAPClientLedger.setTableName("AP_Client_Ledger");
            poAPClientLedger.initialize();
        }

        return poAPClientLedger;
    }
        
    private final GRiderCAS poGRider;

    private Model_Client_Master poClientMaster;
    private Model_Client_Address poClientAddress;
    private Model_Client_Mobile poClientMobile;
    private Model_Client_Mail poClientMail;
    private Model_Client_Social_Media poClientSocmed;
    private Model_Client_Institution_Contact poClientInstitutionContact;
    private Model_Account_Client_Accreditation poClientAccreditation;
    private Model_AP_Client_Master poAPClientMaster;
    private Model_AP_Client_Ledger poAPClientLedger;
}
