package org.guanzon.cas.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.client.model.Model_Client_Address;
import org.guanzon.cas.parameter.Barangay;
import org.guanzon.cas.parameter.TownCity;
import org.guanzon.cas.parameters.Province;
import org.json.simple.JSONObject;

public class Client {

    GRider poGRider;
    String psParent;
    String psProvinceTemp;
    JSONObject poJSON;
    LogWrapper poLogWrapper;

    Client_Master poClient;
    List<Client_Mobile> poMobile;
    List<Client_Address> poAddress;
    List<Client_Mail> poMail;
    List<Client_Social_Media> poSocMed;
    List<Client_Institution_Contact> poInsContact;

    public Client(GRider applicationDriver,
            String parentClass,
            LogWrapper logWrapper) {

        poGRider = applicationDriver;
        psParent = parentClass;
        poLogWrapper = logWrapper;

        poClient = new Client_Master();
        poClient.setApplicationDriver(poGRider);
        poClient.setWithParentClass(true);
//        poClient.setLogWrapper(poLogWrapper);
        poClient.initialize();

        poMobile = new ArrayList<>();
        poAddress = new ArrayList<>();
        poMail = new ArrayList<>();
        poSocMed = new ArrayList<>();
        poInsContact = new ArrayList<>();
    }

    public String getMasterAddress(int lnRow) {
       String lsTownId = "";
        String lsHouseNo = "";
                lsTownId = poAddress.get(lnRow).getModel().getTownId();
                lsHouseNo = poAddress.get(lnRow).getModel().getHouseNo();

                TownCity loTownCity = new TownCity();
                try {
                    loTownCity.setApplicationDriver(poGRider);
                    loTownCity.setRecordStatus("1");
                    loTownCity.initialize();
                    loTownCity.openRecord(poAddress.get(lnRow).getModel().getTownId());
                } catch (Exception e) {
                    System.out.println("error");
                }
                Barangay loBarangay = new Barangay();
                try {
                    loBarangay.setApplicationDriver(poGRider);
                    loBarangay.setRecordStatus("1");
                    loBarangay.initialize();
                    loBarangay.openRecord(poAddress.get(lnRow).getModel().getBarangayId());
                } catch (Exception e) {
                    System.out.println("error");

                }

                Province loProvince = new Province(poGRider, true);
                try {
                    loProvince.setRecordStatus("1");
                    loProvince.openRecord(loTownCity.getModel().getProvinceId());
                } catch (Exception e) {
                    System.out.println("error");

                }

                return (poAddress.get(lnRow).getModel().getHouseNo().equals("") ? "" : poAddress.get(lnRow).getModel().getHouseNo())
                        + ((loBarangay.getModel().getBarangayName() instanceof String) && (!loBarangay.getModel().getBarangayName().equals("")) ?  ", " + loBarangay.getModel().getBarangayName() : "")
                        + ((loTownCity.getModel().getTownName() instanceof String) && (!loTownCity.getModel().getTownName().equals(""))  ? ", " + loTownCity.getModel().getTownName() : "")
                        + ((loProvince.getModel().getProvinceName() instanceof String)  && (!loProvince.getModel().getProvinceName().equals("")) ? ", " + loProvince.getModel().getProvinceName() : "")
                        + ((loTownCity.getModel().getZipCode() instanceof String)  && (!loTownCity.getModel().getZipCode().equals("")) ? " " + loTownCity.getModel().getZipCode() : "");
        
    }

    public void setProvinceID_temp(String lsValue) {
        psProvinceTemp = lsValue;
    }

    public String getProvinceID_temp() {
        return psProvinceTemp;
    }

    public Client_Master Master() {
        return poClient;
    }

    public Client_Mobile Mobile(int row) {
        return poMobile.get(row);
    }

    public Client_Address Address(int row) {
        return poAddress.get(row);
    }

    public Client_Mail Mail(int row) {
        return poMail.get(row);
    }

    public Client_Social_Media SocialMedia(int row) {
        return poSocMed.get(row);
    }

    public Client_Institution_Contact InstitutionContactPerson(int row) {
        return poInsContact.get(row);
    }

    public int getMobileCount() {
        return poMobile.size();
    }

    public int getAddressCount() {
        return poAddress.size();
    }

    public int getMailCount() {
        return poMail.size();
    }

    public int getSocMedCount() {
        return poSocMed.size();
    }

    public int getInstitutionContactPCount() {
        return poSocMed.size();
    }

    public JSONObject addMobile() {
        poJSON = new JSONObject();

        if (poMobile.isEmpty()) {
            poMobile.add(mobile());
        } else {
            if (!poMobile.get(poMobile.size() - 1).getModel().getMobileNo().isEmpty()) {
                poMobile.add(mobile());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add mobile. Last row mobile number is still empty.");
                return poJSON;
            }
        }

        poJSON.put("result", "success");
        return poJSON;
    }

    
    
    
       public JSONObject OpenClientAddress(String fsValue){
        String lsSQL = "SELECT " +
                        " a.sAddrssID" +
                        ", a.sClientID" +
                        ", a.sHouseNox" +
                        ", a.sAddressx" +
                        ", a.sBrgyIDxx" +
                        ", a.sTownIDxx" +
                        ", a.nLatitude" +
                        ", a.nLongitud" +
                        ", a.cPrimaryx" +
                        ", a.cRecdStat" +
                        ", a.dModified" +
                        ", b.sTownName xTownName" +
                        ", d.sBrgyName xBrgyName" +
                        ", c.sProvName xProvName" +
                " FROM Client_Address a" + 
                 " LEFT JOIN TownCity b ON a.sTownIDxx = b.sTownIDxx" +
                            " LEFT JOIN Province c ON b.sProvIDxx = c.sProvIDxx" +
                            " LEFT JOIN Barangay d ON a.sBrgyIDxx = d.sBrgyIDxx";
        lsSQL = MiscUtil.addCondition(lsSQL, "a.sClientID = " + SQLUtil.toSQL(fsValue) + " GROUP BY sAddrssID");
        System.out.println(lsSQL);
        ResultSet loRS = poGRider.executeQuery(lsSQL);

        try {
            int lnctr = 0;
            if (MiscUtil.RecordCount(loRS) > 0) {
                poAddress = new ArrayList<>();
                while(loRS.next()){
                        poAddress.add(address());
                        poAddress.get(poAddress.size() - 1).openRecord(loRS.getString("sAddrssID"));
                        lnctr++;
                        try{
                            poJSON.put("result", "success");
                            poJSON.put("message", "Record loaded successfully.");       
                        }catch(Exception e){
                        }

                    } 
                
                System.out.println("lnctr = " + lnctr);
                
            }else{
                poAddress = new ArrayList<>();
                addAddress();
                poJSON.put("result", "error");
                poJSON.put("continue", true);
                poJSON.put("message", "No record selected.");
            }
            
            MiscUtil.close(loRS);
        } catch (SQLException e) {
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
        }
        return poJSON;
    }
        public JSONObject OpenClientMobile(String fsValue){
        String lsSQL = "SELECT" +
                    "  sMobileID" +
                    ", sClientID" +
                        " FROM Client_Mobile" ;
        lsSQL = MiscUtil.addCondition(lsSQL, "sClientID = " + SQLUtil.toSQL(fsValue));
        ResultSet loRS = poGRider.executeQuery(lsSQL);
        
        System.out.println(lsSQL);
       try {
            int lnctr = 0;
            if (MiscUtil.RecordCount(loRS) > 0) {
                poMobile = new ArrayList<>();
                while(loRS.next()){
                        poMobile.add(mobile());
                        poMobile.get(poMobile.size() - 1).openRecord(loRS.getString("sMobileID"));
                        
//                        pnEditMode = EditMode.UPDATE;
                        lnctr++;
                        try{
                            poJSON.put("result", "success");
                            poJSON.put("message", "Record loaded successfully.");               
                        }catch(Exception e){
                            
                        }
 
                    } 
                
                System.out.println("lnctr = " + lnctr);
            }else{
                poMobile = new ArrayList<>();
                addMobile();
                poJSON.put("result", "error");
                poJSON.put("continue", true);
                poJSON.put("message", "No record found .");
            }
            MiscUtil.close(loRS);
        } catch (SQLException e) {
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
        }
        return poJSON;
    }
    public JSONObject OpenClientMail(String fsValue){
        String lsSQL = "SELECT" +
                    "  sEmailIDx" +
                    ", sClientID" +
                        " FROM Client_eMail_Address" ;
        lsSQL = MiscUtil.addCondition(lsSQL, "sClientID = " + SQLUtil.toSQL(fsValue));
        ResultSet loRS = poGRider.executeQuery(lsSQL);
        
        System.out.println(lsSQL);
       try {
            int lnctr = 0;
            if (MiscUtil.RecordCount(loRS) > 0) {
                poMail = new ArrayList<>();
                while(loRS.next()){
                        poMail.add(email());
                        poMail.get(poMail.size() - 1).openRecord(loRS.getString("sEmailIDx"));
                        
//                        pnEditMode = EditMode.UPDATE;
                        lnctr++;
                        try{
                            poJSON.put("result", "success");
                            poJSON.put("message", "Record loaded successfully.");
                        }catch(Exception e){
                            
                        }
     
                    } 
                
                System.out.println("lnctr = " + lnctr);
            }else{
                poMail = new ArrayList<>();
                addMail();
                poJSON.put("result", "error");
                poJSON.put("continue", true);
                poJSON.put("message", "No record selected.");
            }
            MiscUtil.close(loRS);
        } catch (SQLException e) {
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
        }
        return poJSON;
    }
    
    public JSONObject OpenClientSocialMedia(String fsValue){
        String lsSQL = "SELECT" +
                    "  sSocialID" +
                    ", sClientID" +
                        " FROM Client_Social_Media" ;
        lsSQL = MiscUtil.addCondition(lsSQL, "sClientID = " + SQLUtil.toSQL(fsValue));
        ResultSet loRS = poGRider.executeQuery(lsSQL);
        
        System.out.println(lsSQL);
       try {
            int lnctr = 0;
            if (MiscUtil.RecordCount(loRS) > 0) {
                poSocMed = new ArrayList<>();
                while(loRS.next()){
                        poSocMed.add(socmed());
                        poSocMed.get(poSocMed.size() - 1).openRecord(loRS.getString("sSocialID"));
                        
//                        pnEditMode = EditMode.UPDATE;
                        lnctr++;
                        try{
                            poJSON.put("result", "success");
                            poJSON.put("message", "Record loaded successfully.");
                        }catch(Exception e){
                        }
                    } 
                
                System.out.println("lnctr = " + lnctr);
            }else{
                poSocMed = new ArrayList<>();
                addSocialMedia();
                poJSON.put("result", "error");
                poJSON.put("continue", true);
                poJSON.put("message", "No record selected.");
            }
            MiscUtil.close(loRS);
        } catch (SQLException e) {
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
        }
        return poJSON;
    }
    
    public JSONObject addAddress() {
        poJSON = new JSONObject();

        if (poAddress.isEmpty()) {
            poAddress.add(address());
        } else {
            if (!poAddress.get(poAddress.size() - 1).getModel().getAddress().isEmpty()) {
                poAddress.add(address());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add address. Last row address is still empty.");
                return poJSON;
            }
        }

        poJSON.put("result", "success");
        return poJSON;
    }

    public JSONObject addMail() {
        poJSON = new JSONObject();

        if (poMail.isEmpty()) {
            poMail.add(email());
        } else {
            if (!poMail.get(poMail.size() - 1).getModel().getMailAddress().isEmpty()) {
                poMail.add(email());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add mail. Last row email address is still empty.");
                return poJSON;
            }
        }

        poJSON.put("result", "success");
        return poJSON;
    }

    public JSONObject addSocialMedia() {
        poJSON = new JSONObject();

        if (poSocMed.isEmpty()) {
            poSocMed.add(socmed());
        } else {
            if (!poSocMed.get(poSocMed.size() - 1).getModel().getAccount().isEmpty()) {
                poSocMed.add(socmed());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add social. Last row social media account is still empty.");
                return poJSON;
            }
        }

        poJSON.put("result", "success");
        return poJSON;
    }

    public JSONObject addInsContactPerson() {
        poJSON = new JSONObject();

        if (poInsContact.isEmpty()) {
            poInsContact.add(insCPerson());
        } else {
            if (!poInsContact.get(poInsContact.size() - 1).getModel().getContactPersonName().isEmpty()) {
                poInsContact.add(insCPerson());
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "Unable to add mobile. Last row social media account is still empty.");
                return poJSON;
            }
        }

        poJSON.put("result", "success");
        return poJSON;
    }

    public JSONObject deleteMobile(int row) {
        poJSON = new JSONObject();

        if (poMobile.isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete mobile. Mobile list is empty.");
            return poJSON;
        }

        if (row >= poMobile.size()) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete mobile. Row is more than the mobile list.");
            return poJSON;
        }

        if (poMobile.get(row).getEditMode() != EditMode.ADDNEW) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete old mobile. You can deactivate the record instead.");
            return poJSON;
        }

        poMobile.remove(row);
        poJSON.put("result", "success");
        return poJSON;
    }

    public JSONObject deleteSocialMedia(int row) {
        poJSON = new JSONObject();

        if (poSocMed.isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete mobile. Mobile list is empty.");
            return poJSON;
        }

        if (row >= poSocMed.size()) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete mobile. Row is more than the mobile list.");
            return poJSON;
        }

        if (poSocMed.get(row).getEditMode() != EditMode.ADDNEW) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete old mobile. You can deactivate the record instead.");
            return poJSON;
        }

        poSocMed.remove(row);
        poJSON.put("result", "success");
        return poJSON;
    }

    public JSONObject deleteEmail(int row) {
        poJSON = new JSONObject();

        if (poMail.isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete mobile. Mobile list is empty.");
            return poJSON;
        }

        if (row >= poMail.size()) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete mobile. Row is more than the mobile list.");
            return poJSON;
        }

        if (poMail.get(row).getEditMode() != EditMode.ADDNEW) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete old mobile. You can deactivate the record instead.");
            return poJSON;
        }

        poMail.remove(row);
        poJSON.put("result", "success");
        return poJSON;
    }

    public JSONObject deleteAddress(int row) {
        poJSON = new JSONObject();

        if (poAddress.isEmpty()) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete mobile. Mobile list is empty.");
            return poJSON;
        }

        if (row >= poAddress.size()) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete mobile. Row is more than the mobile list.");
            return poJSON;
        }

        if (poAddress.get(row).getEditMode() != EditMode.ADDNEW) {
            poJSON.put("result", "error");
            poJSON.put("result", "Unable to delete old mobile. You can deactivate the record instead.");
            return poJSON;
        }

        poAddress.remove(row);
        poJSON.put("result", "success");
        return poJSON;
    }

    public JSONObject New() {
        poJSON = poClient.newRecord();
        if (!"success".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        poMobile.clear();
        poJSON = addMobile();
        if (!"success".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        poAddress.clear();
        poJSON = addAddress();
        if (!"success".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        poMail.clear();
        poJSON = addMail();
        if (!"success".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        poSocMed.clear();
        poJSON = addSocialMedia();
        if (!"success".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        poInsContact.clear();
        poJSON = addInsContactPerson();
        if (!"success".equals((String) poJSON.get("result"))) {
            return poJSON;
        }

        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }

    public JSONObject Save() {
        int lnCtr;

        if (psParent.isEmpty()) {
            poGRider.beginTrans();
        }

        //assign modified info
        poClient.getModel().setModifyingId(poGRider.getUserID());
        poClient.getModel().setModifiedDate(poGRider.getServerDate());

        //save client master
        poJSON = poClient.saveRecord();

        if (!"success".equals((String) poJSON.get("result"))) {
            if (psParent.isEmpty()) {
                poGRider.rollbackTrans();
            }
            return poJSON;
        }

        //save mobile
        if (!poMobile.isEmpty()) {
            for (lnCtr = 0; lnCtr <= poMobile.size() - 1; lnCtr++) {
                if ((poMobile.get(lnCtr).getEditMode() == EditMode.ADDNEW
                        || poMobile.get(lnCtr).getEditMode() == EditMode.UPDATE)
                        && !poMobile.get(lnCtr).getModel().getMobileNo().isEmpty()) {

                    if (poMobile.get(lnCtr).getEditMode() == EditMode.ADDNEW) {
                        poMobile.get(lnCtr).getModel().setClientId(poClient.getModel().getClientId());
                    }

                    poMobile.get(lnCtr).getModel().setModifiedDate(poClient.getModel().getModifiedDate());

                    //save
                    poJSON = poMobile.get(lnCtr).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))) {
                        if (psParent.isEmpty()) {
                            poGRider.rollbackTrans();
                        }
                        return poJSON;
                    }
                }
            }
        }

        //save address
        if (!poAddress.isEmpty()) {
            for (lnCtr = 0; lnCtr <= poAddress.size() - 1; lnCtr++) {
                if ((poAddress.get(lnCtr).getEditMode() == EditMode.ADDNEW
                        || poAddress.get(lnCtr).getEditMode() == EditMode.UPDATE)
                        && (!poAddress.get(lnCtr).getModel().getAddress().isEmpty()
                        || !poAddress.get(lnCtr).getModel().getBarangayId().isEmpty())) {

                    if (poAddress.get(lnCtr).getEditMode() == EditMode.ADDNEW) {
                        poAddress.get(lnCtr).getModel().setClientId(poClient.getModel().getClientId());
                    }

                    poAddress.get(lnCtr).getModel().setModifiedDate(poClient.getModel().getModifiedDate());

                    //save
                    poJSON = poAddress.get(lnCtr).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))) {
                        if (psParent.isEmpty()) {
                            poGRider.rollbackTrans();
                        }
                        return poJSON;
                    }
                }
            }
        }

        //save email
        if (!poMail.isEmpty()) {
            for (lnCtr = 0; lnCtr <= poMail.size() - 1; lnCtr++) {
                if ((poMail.get(lnCtr).getEditMode() == EditMode.ADDNEW
                        || poMail.get(lnCtr).getEditMode() == EditMode.UPDATE)
                        && !poMail.get(lnCtr).getModel().getMailAddress().isEmpty()) {

                    if (poMail.get(lnCtr).getEditMode() == EditMode.ADDNEW) {
                        poMail.get(lnCtr).getModel().setClientId(poClient.getModel().getClientId());
                    }

                    poMail.get(lnCtr).getModel().setModifiedDate(poClient.getModel().getModifiedDate());

                    //save
                    poJSON = poMail.get(lnCtr).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))) {
                        if (psParent.isEmpty()) {
                            poGRider.rollbackTrans();
                        }
                        return poJSON;
                    }
                }
            }
        }

        //save socmed
        if (!poSocMed.isEmpty()) {
            for (lnCtr = 0; lnCtr <= poSocMed.size() - 1; lnCtr++) {
                if ((poSocMed.get(lnCtr).getEditMode() == EditMode.ADDNEW
                        || poSocMed.get(lnCtr).getEditMode() == EditMode.UPDATE)
                        && !poSocMed.get(lnCtr).getModel().getAccount().isEmpty()) {

                    if (poSocMed.get(lnCtr).getEditMode() == EditMode.ADDNEW) {
                        poSocMed.get(lnCtr).getModel().setClientId(poClient.getModel().getClientId());
                    }

                    poSocMed.get(lnCtr).getModel().setModifiedDate(poClient.getModel().getModifiedDate());

                    //save
                    poJSON = poSocMed.get(lnCtr).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))) {
                        if (psParent.isEmpty()) {
                            poGRider.rollbackTrans();
                        }
                        return poJSON;
                    }
                }
            }
        }

        //save contact person
        if (!poInsContact.isEmpty()) {
            for (lnCtr = 0; lnCtr <= poInsContact.size() - 1; lnCtr++) {
                if ((poInsContact.get(lnCtr).getEditMode() == EditMode.ADDNEW
                        || poInsContact.get(lnCtr).getEditMode() == EditMode.UPDATE)
                        && !poInsContact.get(lnCtr).getModel().getContactPersonName().isEmpty()) {

                    if (poInsContact.get(lnCtr).getEditMode() == EditMode.ADDNEW) {
                        poInsContact.get(lnCtr).getModel().setClientId(poClient.getModel().getClientId());
                    }

                    poInsContact.get(lnCtr).getModel().setModifiedDate(poClient.getModel().getModifiedDate());

                    //save
                    poJSON = poInsContact.get(lnCtr).saveRecord();

                    if (!"success".equals((String) poJSON.get("result"))) {
                        if (psParent.isEmpty()) {
                            poGRider.rollbackTrans();
                        }
                        return poJSON;
                    }
                }
            }
        }

        if (psParent.isEmpty()) {
            poGRider.commitTrans();
        }

        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }

    private Client_Mobile mobile() {
        Client_Mobile object = new Client_Mobile();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
//        object.setLogWrapper(poLogWrapper);
        object.initialize();
        object.newRecord();
        return object;
    }

    private Client_Address address() {
        Client_Address object = new Client_Address();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
//        object.setLogWrapper(poLogWrapper);
        object.initialize();
        object.newRecord();
        return object;
    }

    private Client_Mail email() {
        Client_Mail object = new Client_Mail();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
//        object.setLogWrapper(poLogWrapper);
        object.initialize();
        object.newRecord();
        return object;
    }

    private Client_Social_Media socmed() {
        Client_Social_Media object = new Client_Social_Media();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
//        object.setLogWrapper(poLogWrapper);
        object.initialize();
        object.newRecord();
        return object;
    }

    private Client_Institution_Contact insCPerson() {
        Client_Institution_Contact object = new Client_Institution_Contact();
        object.setApplicationDriver(poGRider);
        object.setWithParentClass(true);
//        object.setLogWrapper(poLogWrapper);
        object.initialize();
        object.newRecord();
        return object;
    }
}
