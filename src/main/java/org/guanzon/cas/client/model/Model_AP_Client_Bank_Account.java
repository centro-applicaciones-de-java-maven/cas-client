/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.guanzon.cas.client.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.appdriver.agent.services.ReferenceCache;
import org.guanzon.cas.parameter.model.Model_Banks;
import org.json.simple.JSONObject;

/**
 *
 * @author kalyptus
 */
public class Model_AP_Client_Bank_Account extends Model  {
    private Model_Banks poBanks;
    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());

            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);

            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

            //assign default values  
            //poEntity.updateString("sAPBnkIDx", "");
            poEntity.updateString("sClientID", "");
            poEntity.updateString("sActNumbr", "");
            poEntity.updateString("sActNamex", "");
            poEntity.updateString("sBankIDxx", "");
            poEntity.updateString("cRecdStat", RecordStatus.ACTIVE);
            poEntity.updateString("sModified", poGRider.getUserID());
            poEntity.updateObject("dModified", poGRider.getServerDate());
            //end - assign default values

            ID = "sAPBnkIDx";

            //poBanks is intentionally NOT constructed here - see Banks() below, which builds it
            //lazily on first access so opening this record never touches the Banks table.

            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }
    
    public JSONObject setAPClientBankID(String bankId) {
        return setValue("sAPBnkIDx", bankId);
    }
    public String getAPClientBankID() {
        return (String)getValue("sAPBnkIDx");
    }
    
    public JSONObject setClientID(String value) {
        return setValue("sClientID", value);
    }
    public String getClientID() {
        return (String)getValue("sClientID");
    }
    
    public JSONObject setAccountNumber(String value) {
        return setValue("sActNumbr", value);
    }
    public String getAccountNumber() {
        return (String)getValue("sActNumbr");
    }

    public JSONObject setAccountName(String value) {
        return setValue("sActNamex", value);
    }
    public String getAccountName() {
        return (String)getValue("sActNamex");
    }
    
    public JSONObject setBankID(String value) {
        return setValue("sBankIDxx", value);
    }
    public String getBankID() {
        return (String)getValue("sBankIDxx");
    }
    
    public Model_Banks Banks() throws SQLException, GuanzonException{
        if (poBanks == null) {
            poBanks = new Model_Banks();
            poBanks.setApplicationDriver(poGRider);
            poBanks.setXML("Model_Banks");
            poBanks.setTableName("Banks");
            poBanks.initialize();
        }

        String bankId = (String) getValue("sBankIDxx");

        if (!"".equals(bankId)){
            if (poBanks.getEditMode() == EditMode.READY &&
                poBanks.getBankID().equals(bankId))
                return poBanks;
            else{
                if (ReferenceCache.tryLoad("Banks", bankId, poBanks)) {
                    return poBanks;
                }

                poJSON = poBanks.openRecord(bankId);

                if ("success".equals((String) poJSON.get("result"))){
                    ReferenceCache.store("Banks", bankId, poBanks);
                    return poBanks;
                }
                else {
                    poBanks.initialize();
                    return poBanks;
                }
            }
        } else {
            poBanks.initialize();
            return poBanks;
        }
    }
    
    public JSONObject setRecordStatus(String recordStatus) {
        return setValue("cRecdStat", recordStatus);
    }

    public String getRecordStatus() {
        return (String) getValue("cRecdStat");
    }

    public JSONObject setModifyingId(String ModifyingId) {
        return setValue("sModified", ModifyingId);
    }
    
    public String getModifyingId() {
        return (String) getValue("sModified");
    }
    
    public JSONObject setModifiedDate(Date modifiedDate) {
        return setValue("dModified", modifiedDate);
    }

    public Date getModifiedDate() {
        return (Date) getValue("dModified");
    }
    
    @Override
    public String getNextCode(){
        return MiscUtil.getNextCode(getTable(), ID, true, poGRider.getGConnection().getConnection(), poGRider.getBranchCode()); 
    }
}
