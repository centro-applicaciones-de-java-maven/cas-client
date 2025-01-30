package org.guanzon.cas.client.services;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.cas.client.account.AP_Client_Master;

public class CasClientControllers {
    public CasClientControllers(GRider applicationDriver, LogWrapper logWrapper) {
        poGRider = applicationDriver;
        poLogWrapper = logWrapper;
    }

//    public Inventory Inventory() {
//        if (poGRider == null) {
//            poLogWrapper.severe("InvControllers.Inventory: Application driver is not set.");
//            return null;
//        }
//
//        if (poInventory != null) {
//            return poInventory;
//        }
//
//        poInventory = new Inventory();
//        poInventory.setApplicationDriver(poGRider);
//        poInventory.setWithParentClass(true);
//        poInventory.setLogWrapper(poLogWrapper);
//        poInventory.initialize();
//        poInventory.newRecord();
//        return poInventory;
//    }

    public AP_Client_Master APClientMaster() {
        if (poGRider == null) {
            poLogWrapper.severe("InvControllers.InventoryMaster: Application driver is not set.");
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
    
//    public InvSerial InventorySerial() {
//        if (poGRider == null) {
//            poLogWrapper.severe("InvControllers.InventoryMaster: Application driver is not set.");
//            return null;
//        }
//
//        if (poInventorySerial != null) {
//            return poInventorySerial;
//        }
//
//        poInventorySerial = new InvSerial();
//        poInventorySerial.setApplicationDriver(poGRider);
//        poInventorySerial.setWithParentClass(true);
//        poInventorySerial.setLogWrapper(poLogWrapper);
//        poInventorySerial.initialize();
//        poInventorySerial.newRecord();
//        return poInventorySerial;
//    }
    
    

    private final GRider poGRider;
    private final LogWrapper poLogWrapper;

    private AP_Client_Master poApClientMaster;
//    private Inv_Master poInvMaster;
//    private InvSerial poInventorySerial;
}
