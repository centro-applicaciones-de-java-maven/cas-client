package org.guanzon.cas.client.services;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.cas.client.account.AP_Client_Ledger;
import org.guanzon.cas.client.account.AP_Client_Master;
import org.guanzon.cas.client.account.AR_Client_Ledger;
import org.guanzon.cas.client.account.AR_Client_Master;

public class CasClientControllers {
    public CasClientControllers(GRider applicationDriver, LogWrapper logWrapper) {
        poGRider = applicationDriver;
        poLogWrapper = logWrapper;
    }
    
    public AP_Client_Master APClientMaster() {
        if (poGRider == null) {
            poLogWrapper.severe("CasClients.AP_Client_Master: Application driver is not set.");
            return null;
        }

        if (poApClientMaster != null) {
            return poApClientMaster;
        }

        poApClientMaster = new AP_Client_Master();
        poApClientMaster.setApplicationDriver(poGRider);
        poApClientMaster.setWithParentClass(true);
        poApClientMaster.setLogWrapper(poLogWrapper);
        poApClientMaster.initialize();
        poApClientMaster.newRecord();
        return poApClientMaster;
    }
    
    public AP_Client_Ledger APClientLedger() {
        if (poGRider == null) {
            poLogWrapper.severe("CasClients.AP_Client_Ledger: Application driver is not set.");
            return null;
        }

        if (poApClientMaster != null) {
            return poApClientLedger;
        }

        poApClientLedger = new AP_Client_Ledger();
        poApClientLedger.setApplicationDriver(poGRider);
        poApClientLedger.setWithParentClass(true);
        poApClientLedger.setLogWrapper(poLogWrapper);
        poApClientLedger.initialize();
        poApClientLedger.newRecord();
        return poApClientLedger;
    }
    
    public AR_Client_Master ARClientMaster() {
        if (poGRider == null) {
            poLogWrapper.severe("CasClients.AR_Client_Master: Application driver is not set.");
            return null;
        }

        if (poArClientMaster != null) {
            return poArClientMaster;
        }

        poArClientMaster = new AR_Client_Master();
        poArClientMaster.setApplicationDriver(poGRider);
        poArClientMaster.setWithParentClass(true);
        poArClientMaster.setLogWrapper(poLogWrapper);
        poArClientMaster.initialize();
        poArClientMaster.newRecord();
        return poArClientMaster;
    }
    
    public AR_Client_Ledger ARClientLedger() {
        if (poGRider == null) {
            poLogWrapper.severe("CasClients.AR_Client_Ledger: Application driver is not set.");
            return null;
        }

        if (poApClientMaster != null) {
            return poArClientLedger;
        }

        poArClientLedger = new AR_Client_Ledger();
        poArClientLedger.setApplicationDriver(poGRider);
        poArClientLedger.setWithParentClass(true);
        poArClientLedger.setLogWrapper(poLogWrapper);
        poArClientLedger.initialize();
        poArClientLedger.newRecord();
        return poArClientLedger;
    }


    private final GRider poGRider;
    private final LogWrapper poLogWrapper;

    private AP_Client_Master poApClientMaster;
    private AP_Client_Ledger poApClientLedger;
    private AR_Client_Master poArClientMaster;
    private AR_Client_Ledger poArClientLedger;
}
