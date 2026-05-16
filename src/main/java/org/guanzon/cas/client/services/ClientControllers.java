package org.guanzon.cas.client.services;

import java.sql.SQLException;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.cas.client.Client;
import org.guanzon.cas.client.ClientInfo;
import org.guanzon.cas.client.Client_Role;
import org.guanzon.cas.client.account.AP_Client_Master;
import org.guanzon.cas.client.account.Account_Accreditation;
import org.guanzon.cas.client.account.Accounts_Accreditation_Transaction;
import ph.com.guanzongroup.cas.cashflow.Payee;

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
    
    public Client_Role ClientRole() throws SQLException, GuanzonException{
        if (poGRider == null){
            poLogWrapper.severe("ClientControllers.ClientInfo: Application driver is not set.");
            return null;
        }
        
        if (poClientRole != null) return poClientRole;
        
        poClientRole = new Client_Role();
        poClientRole.setApplicationDriver(poGRider);
        poClientRole.setWithParentClass(false);
        poClientRole.setLogWrapper(poLogWrapper);
        poClientRole.initialize();
        
        return poClientRole;       
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
        
        return poAccountAccreditation;       
    }
    
    public Accounts_Accreditation_Transaction AccountAccreditationTransaction() throws SQLException, GuanzonException, CloneNotSupportedException{
        if (poGRider == null){
            poLogWrapper.severe("ClientControllers.AccountAccreditation: Application driver is not set.");
            return null;
        }
        
        if (poAccountAccreditationTransaction != null) return poAccountAccreditationTransaction;
        
        poAccountAccreditationTransaction = new Accounts_Accreditation_Transaction();
        poAccountAccreditationTransaction.setApplicationDriver(poGRider);
        poAccountAccreditationTransaction.setWithParent(false);
        poAccountAccreditationTransaction.setLogWrapper(poLogWrapper);
        
        return poAccountAccreditationTransaction;       
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
    
    public Payee Payee() throws SQLException, GuanzonException {
        if (poGRider == null) {
            poLogWrapper.severe("CashFlowcontrollers.Payee: Application driver is not set.");
            return null;
        }

        if (poPayee != null) {
            return poPayee;
        }

        poPayee = new Payee();
        poPayee.setApplicationDriver(poGRider);
        poPayee.setWithParentClass(false);
        poPayee.setLogWrapper(poLogWrapper);
        poPayee.initialize();
        poPayee.newRecord();
        return poPayee;
    }

    private final GRiderCAS poGRider;
    private final LogWrapper poLogWrapper;

    private Client poClient;
    private ClientInfo poClientx;
    private Client_Role poClientRole;
    private Account_Accreditation poAccountAccreditation;
    private Accounts_Accreditation_Transaction poAccountAccreditationTransaction;
    private AP_Client_Master poAPClientMaster;
    private Payee poPayee;
}
