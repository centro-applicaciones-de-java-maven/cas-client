package ph.com.guanzongroup.cas.client.services;

import java.sql.SQLException;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import ph.com.guanzongroup.cas.client.Client;
import ph.com.guanzongroup.cas.client.ClientInfo;
import ph.com.guanzongroup.cas.client.AP_Client_Master;
import ph.com.guanzongroup.cas.client.Account_Accreditation;

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
            poLogWrapper.severe("ClientControllers.ClientInfo: Application driver is not set.");
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
    
    public Account_Accreditation AccountAccreditation() throws SQLException, GuanzonException{
        if (poGRider == null){
            poLogWrapper.severe("ClientControllers.AccountAccreditation: Application driver is not set.");
            return null;
        }
        
        if (poAccountAccreditation != null) return poAccountAccreditation;
        
        poAccountAccreditation = new Account_Accreditation();
        poAccountAccreditation.setApplicationDriver(poGRider);
        poAccountAccreditation.setWithParentClass(false);
        poAccountAccreditation.setLogWrapper(poLogWrapper);
        poAccountAccreditation.initialize();
//        poAccountAccreditation.newRecord();
        
        return poAccountAccreditation;       
    }
    
    public AP_Client_Master APClientMaster() throws SQLException, GuanzonException{
        if (poGRider == null){
            poLogWrapper.severe("ClientControllers.APClientMaster: Application driver is not set.");
            return null;
        }
        
        if (poAPClientMaster != null) return poAPClientMaster;
        
        poAPClientMaster = new AP_Client_Master();
        poAPClientMaster.setApplicationDriver(poGRider);
        poAPClientMaster.setWithParentClass(false);
        poAPClientMaster.setLogWrapper(poLogWrapper);
        poAPClientMaster.initialize();
//        poAPClientMaster.newRecord();
        
        return poAPClientMaster;       
    }

    private final GRiderCAS poGRider;
    private final LogWrapper poLogWrapper;

    private Client poClient;
    private ClientInfo poClientx;
    private Account_Accreditation poAccountAccreditation;
    private AP_Client_Master poAPClientMaster;
}
