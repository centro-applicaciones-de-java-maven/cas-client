package org.guanzon.cas.client.services;

import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.cas.client.Client;
import org.guanzon.cas.client.account.AP_Client_Master;

public class ClientControllers {
    public ClientControllers(GRider applicationDriver, LogWrapper logWrapper) {
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

    public Client Client() {
        if (poGRider == null) {
            poLogWrapper.severe("InvControllers.InventoryMaster: Application driver is not set.");
            return null;
        }

        if (poClient != null) {
            return poClient;
        }

        poClient = new Client(poGRider,"",poLogWrapper);
        return poClient;
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

    private Client poClient;
//    private Inv_Master poInvMaster;
//    private InvSerial poInventorySerial;
}
