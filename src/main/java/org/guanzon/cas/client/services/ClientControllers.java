package org.guanzon.cas.client.services;

import java.sql.SQLException;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.cas.client.Client;
import org.guanzon.cas.client.ClientInfo;

public class ClientControllers {
    public ClientControllers(GRiderCAS applicationDriver, LogWrapper logWrapper) {
        poGRider = applicationDriver;
        poLogWrapper = logWrapper;
    }

    public Client Client() throws SQLException, GuanzonException{
        if (poGRider == null) {
            poLogWrapper.severe("ClientControllers.Client: Application driver is not set.");
            return null;
        }

        if (poClient != null) {
            return poClient;
        }

        poClient = new Client(poGRider,"", poLogWrapper);
        return poClient;
    }
    
    public ClientInfo ClientInfo() throws SQLException, GuanzonException{
        if (poGRider == null){
            poLogWrapper.severe("GLControllers.AccountChart: Application driver is not set.");
            return null;
        }
        
        if (poClientx != null) return poClientx;
        
        poClientx = new ClientInfo();
        poClientx.setApplicationDriver(poGRider);
        poClientx.setWithParentClass(false);
        poClientx.setLogWrapper(poLogWrapper);
        poClientx.initialize();
        poClientx.newRecord();
        return poClientx;       
    }

    private final GRiderCAS poGRider;
    private final LogWrapper poLogWrapper;

    private Client poClient;
    private ClientInfo poClientx;
}