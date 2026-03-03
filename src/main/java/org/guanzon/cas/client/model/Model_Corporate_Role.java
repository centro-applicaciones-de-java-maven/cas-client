/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.guanzon.cas.client.model;

import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class Model_Corporate_Role extends Model{

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
    
    public JSONObject setsModified(String sModified){
        return setValue("sModified", sModified);
    }

    public String getsModified(){
        return (String) getValue("sModified");
    }
    
    public JSONObject setdModified(Date dModified){
        return setValue("dModified", dModified);
    }

    public Date getdModified(){
        return (Date) getValue("dModified");
    }

    @Override
    public String getNextCode() {
        return MiscUtil.getNextCode(getTable(), ID, true, poGRider.getGConnection().getConnection(), poGRider.getBranchCode()); 
    }
}
