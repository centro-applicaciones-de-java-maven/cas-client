package org.guanzon.cas.client.services;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.cas.client.model.Model_AP_Client_Ledger;
import org.guanzon.cas.client.model.Model_Client_Address;
import org.guanzon.cas.client.model.Model_Client_Institution_Contact;
import org.guanzon.cas.client.model.Model_Client_Master;
public class ClientModel {

    public ClientModel(GRider applicationDriver) {
        poGRider = applicationDriver;
    }

    public Model_Client_Master ClientMaster() {
        if (poGRider == null) {
            System.err.println("InvModels.Inventory: Application driver is not set.");
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
            System.err.println("InvModels.InventoryLedger: Application driver is not set.");
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
            System.err.println("InvModels.InventoryLedger: Application driver is not set.");
            return null;
        }

        if (poClientInstitutionContact == null) {
            poClientInstitutionContact = new Model_Client_Institution_Contact();
            poClientInstitutionContact.setApplicationDriver(poGRider);
            poClientInstitutionContact.setXML("Model_Client_Institution_Contact");
            poClientInstitutionContact.setTableName("Client_Institution_Contact_Person");
            poClientInstitutionContact.initialize();
        }

        return poClientInstitutionContact;
    }
        
    private final GRider poGRider;

    private Model_Client_Master poClientMaster;
    private Model_Client_Address poClientAddress;
    private Model_Client_Institution_Contact poClientInstitutionContact;
    

}
