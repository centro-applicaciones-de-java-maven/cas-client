package org.guanzon.cas.client.services;

import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.cas.client.model.Model_AP_Client_Ledger;
import org.guanzon.cas.client.model.Model_AP_Client_Master;
import org.guanzon.cas.client.model.Model_AR_Client_Ledger;
import org.guanzon.cas.client.model.Model_AR_Client_Master;
public class CasClientModel {

    public CasClientModel(GRiderCAS applicationDriver) {
        poGRider = applicationDriver;
    }

    public Model_AP_Client_Master APClientMaster() {
        if (poGRider == null) {
            System.err.println("InvModels.Inventory: Application driver is not set.");
            return null;
        }

        if (poApClientMaster == null) {
            poApClientMaster = new Model_AP_Client_Master();
            poApClientMaster.setApplicationDriver(poGRider);
            poApClientMaster.setXML("Model_AP_Client_Master");
            poApClientMaster.setTableName("AP_Client_Master");
            poApClientMaster.initialize();
        }

        return poApClientMaster;
    }
    
    public Model_AP_Client_Ledger APClientLedger() {
        if (poGRider == null) {
            System.err.println("InvModels.InventoryLedger: Application driver is not set.");
            return null;
        }

        if (poApClientLedger == null) {
            poApClientLedger = new Model_AP_Client_Ledger();
            poApClientLedger.setApplicationDriver(poGRider);
            poApClientLedger.setXML("Model_AP_Client_Ledger");
            poApClientLedger.setTableName("AP_Client_Ledger");
            poApClientLedger.initialize();
        }

        return poApClientLedger;
    }
    
    public Model_AR_Client_Master ARClientMaster() {
        if (poGRider == null) {
            System.err.println("InvModels.Inventory: Application driver is not set.");
            return null;
        }

        if (poArClientMaster == null) {
            poArClientMaster = new Model_AR_Client_Master();
            poArClientMaster.setApplicationDriver(poGRider);
            poArClientMaster.setXML("Model_AR_Client_Master");
            poArClientMaster.setTableName("AR_Client_Master");
            poArClientMaster.initialize();
        }

        return poArClientMaster;
    }
    
    public Model_AR_Client_Ledger ARClientLedger() {
        if (poGRider == null) {
            System.err.println("InvModels.InventoryLedger: Application driver is not set.");
            return null;
        }

        if (poArClientLedger == null) {
            poArClientLedger = new Model_AR_Client_Ledger();
            poArClientLedger.setApplicationDriver(poGRider);
            poArClientLedger.setXML("Model_AR_Client_Ledger");
            poArClientLedger.setTableName("AR_Client_Ledger");
            poArClientLedger.initialize();
        }

        return poArClientLedger;
    }

    private final GRiderCAS poGRider;

    private Model_AP_Client_Master poApClientMaster;
    private Model_AP_Client_Ledger poApClientLedger;
    private Model_AR_Client_Master poArClientMaster;
    private Model_AR_Client_Ledger poArClientLedger;

}
