/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.guanzon.cas.client.model;

import java.sql.SQLException;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class Model_Contact_Role extends Model{

    @Override
    public void initialize() {
        
        try{
            
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());
            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);
            
            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);
            
            pnEditMode = EditMode.UNKNOWN;
            
        }catch(Exception e){
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }
    
    public JSONObject setRoleIDxx(String sRoleIDxx){
        return setValue("sRoleIDxx", sRoleIDxx);
    }

    public String getRoleIDxx(){
        return (String) getValue("sRoleIDxx");
    }
    
    public JSONObject setsRoleDesc(String sRoleDesc){
        return setValue("sRoleDesc", sRoleDesc);
    }

    public String getsRoleDesc(){
        return (String) getValue("sRoleDesc");
    }
    
    public JSONObject setcRecdStat(String cRecdStat){
        return setValue("cRecdStat", cRecdStat);
    }

    public String getcRecdStat(){
        return (String) getValue("cRecdStat");
    }

    @Override
    public String getNextCode() {
        return super.getNextCode();
    }
    
    private String GET_SQ_BROWSE = 
                       "SELECT " +
                        "sRoleIDxx, " +
                        "sRoleDesc " +
                       "FROM " +
                        "Corporate_Role " +
                       "WHERE " +
                        "cRecdStat = '1' ";
    
    public JSONObject searchRecord(String value, boolean byCode) throws SQLException, GuanzonException{
        
        if (value == null) value = "";
        
        String lsSearch = 
                        GET_SQ_BROWSE +
                       "AND " +
                        "((sRoleDesc " +
                       "LIKE " +
                        "%'" + value + "'&) " +
                       "OR " +
                        "(sRoleIDxx = '" + value + "'))";
        
        //show dialog (search by description), open record (search by code)
        String lsRoleIDxx;
        if (!byCode) {
            
            poJSON = ShowDialogFX.Search(poGRider,
                            lsSearch,
                            value,
                            "ID»Role",
                            "sRoleIDxx»sRoleDesc",
                            "sRoleIDxx»sRoleDesc",
                            byCode ? 0 : 1);
            
            lsRoleIDxx = ((String) poJSON.get("sRoleIDxx")) == null ? "" : ((String) poJSON.get("sRoleIDxx"));
        }else{
            lsRoleIDxx = value;
        }
        
        if (poJSON != null) {
            return openRecord(lsRoleIDxx);
        } else {
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", "No record loaded.");
            return poJSON;
        }
    }
    
    
}
