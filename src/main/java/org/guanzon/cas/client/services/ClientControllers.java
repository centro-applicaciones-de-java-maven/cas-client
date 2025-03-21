package org.guanzon.cas.client.services;

import java.sql.SQLException;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.cas.client.ClientInfo;

public class ClientControllers {
    public ClientControllers(GRiderCAS applicationDriver, LogWrapper logWrapper) {
        poGRider = applicationDriver;
        poLogWrapper = logWrapper;
    }

    public ClientInfo Client() throws SQLException, GuanzonException{
        if (poGRider == null) {
            poLogWrapper.severe("InvControllers.Inventory: Application driver is not set.");
            return null;
        }

        if (poClient != null) {
            return poClient;
        }

        poClient = new ClientInfo();
        poClient.setApplicationDriver(poGRider);
        poClient.setWithParentClass(false);
        poClient.setLogWrapper(poLogWrapper);
        poClient.initialize();
        poClient.newRecord();
        return poClient;
    }

    private final GRiderCAS poGRider;
    private final LogWrapper poLogWrapper;

    private ClientInfo poClient;
}