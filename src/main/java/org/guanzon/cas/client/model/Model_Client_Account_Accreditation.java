package org.guanzon.cas.client.model;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import javax.sql.rowset.CachedRowSet;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GRider;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.Logical;
import org.guanzon.appdriver.constant.TransactionStatus;
import org.guanzon.appdriver.iface.GEntity;
import org.json.simple.JSONObject;

/**
 *
 * @author Maynard
 */
public class Model_Client_Account_Accreditation implements GEntity {

    final String XML = "Model_Account_Client_Accreditation.xml";

    GRider poGRider;                //application driver
    CachedRowSet poEntity;          //rowset
    JSONObject poJSON;              //json container
    int pnEditMode;                 //edit mode

    /**
     * Entity constructor
     *
     * @param foValue - GhostRider Application Driver
     */
    public Model_Client_Account_Accreditation(GRider foValue) {
        if (foValue == null) {
            System.err.println("Application Driver is not set.");
            System.exit(1);
        }

        poGRider = foValue;

        initialize();
    }

    /**
     * Gets edit mode of the record
     *
     * @return edit mode
     */
    @Override
    public int getEditMode() {
        return pnEditMode;
    }

    /**
     * Gets the column index name.
     *
     * @param fnValue - column index number
     * @return column index name
     */
    @Override
    public String getColumn(int fnValue) {
        try {
            return poEntity.getMetaData().getColumnLabel(fnValue);
        } catch (SQLException e) {
        }
        return "";
    }

    /**
     * Gets the column index number.
     *
     * @param fsValue - column index name
     * @return column index number
     */
    @Override
    public int getColumn(String fsValue) {
        try {
            return MiscUtil.getColumnIndex(poEntity, fsValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets the total number of column.
     *
     * @return total number of column
     */
    @Override
    public int getColumnCount() {
        try {
            return poEntity.getMetaData().getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Gets the table name.
     *
     * @return table name
     */
    @Override
    public String getTable() {
        return "Account_Client_Accreditation";
    }

    /**
     * Gets the value of a column index number.
     *
     * @param fnColumn - column index number
     * @return object value
     */
    @Override
    public Object getValue(int fnColumn) {
        try {
            return poEntity.getObject(fnColumn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the value of a column index name.
     *
     * @param fsColumn - column index name
     * @return object value
     */
    @Override
    public Object getValue(String fsColumn) {
        try {
            return poEntity.getObject(MiscUtil.getColumnIndex(poEntity, fsColumn));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sets column value.
     *
     * @param fnColumn - column index number
     * @param foValue - value
     * @return result as success/failed
     */
    @Override
    public JSONObject setValue(int fnColumn, Object foValue) {
        try {
//            poJSON = MiscUtil.validateColumnValue(System.getProperty("sys.default.path.metadata") + XML, MiscUtil.getColumnLabel(poEntity, fnColumn), foValue);
//            if ("error".equals((String) poJSON.get("result"))) {
//                return poJSON;
//            }

            poEntity.updateObject(fnColumn, foValue);
            poEntity.updateRow();

            poJSON = new JSONObject();
            poJSON.put("result", "success");
            poJSON.put(MiscUtil.getColumnLabel(poEntity, fnColumn), getValue(fnColumn));
        } catch (SQLException e) {
            e.printStackTrace();
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
        }

        return poJSON;
    }

    /**
     * Sets column value.
     *
     * @param fsColumn - column index name
     * @param foValue - value
     * @return result as success/failed
     */
    @Override
    public JSONObject setValue(String fsColumn, Object foValue) {
        poJSON = new JSONObject();

        try {
            return setValue(MiscUtil.getColumnIndex(poEntity, fsColumn), foValue);
        } catch (SQLException e) {
            e.printStackTrace();
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
        }
        return poJSON;
    }

    /**
     * Set the edit mode of the entity to new.
     *
     * @return result as success/failed
     */
    @Override
    public JSONObject newRecord() {
        pnEditMode = EditMode.ADDNEW;

        //replace with the primary key column info
        setTransactionNo(MiscUtil.getNextCode(getTable(), "sTransNox", true, poGRider.getConnection(), poGRider.getBranchCode()));

        poJSON = new JSONObject();
        poJSON.put("result", "success");
        return poJSON;
    }

    /**
     * Opens a record.
     *
     * @param fsCondition - filter values
     * @return result as success/failed
     */
    @Override
    public JSONObject openRecord(String fsValue) {
        poJSON = new JSONObject();

        String lsSQL = getSQL();
        lsSQL = MiscUtil.addCondition(getSQL(), "a.sTransNox = " + SQLUtil.toSQL(fsValue));
        System.out.println("lsSQL open = " + lsSQL);
        ResultSet loRS = poGRider.executeQuery(lsSQL);

        try {
            if (loRS.next()){
                for (int lnCtr = 1; lnCtr <= loRS.getMetaData().getColumnCount(); lnCtr++){
                    setValue(lnCtr, loRS.getObject(lnCtr));
                    System.out.println(loRS.getMetaData().getColumnLabel(lnCtr) + " = " + loRS.getString(lnCtr));
                }

                pnEditMode = EditMode.UPDATE;

                poJSON.put("result", "success");
                poJSON.put("message", "Record loaded successfully.");
            } else {
                poJSON.put("result", "error");
                poJSON.put("message", "No record to load.");
            }
            MiscUtil.close(loRS);
        } catch (SQLException e) {
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
        }

        return poJSON;
    }

    /**
     * Save the entity.
     *
     * @return result as success/failed
     */
    @Override
    public JSONObject saveRecord() {
        poJSON = new JSONObject();

        if (pnEditMode == EditMode.ADDNEW || pnEditMode == EditMode.UPDATE) {
            String lsSQL;
            if (pnEditMode == EditMode.ADDNEW) {
                //replace with the primary key column info
                setTransactionNo(MiscUtil.getNextCode(getTable(), "sTransNox", true, poGRider.getConnection(), poGRider.getBranchCode()));
                setModifiedDate(poGRider.getServerDate());
                setModifiedBy(poGRider.getUserID());
                lsSQL = makeSQL();

                if (!lsSQL.isEmpty()) {
                    if (poGRider.executeQuery(lsSQL, getTable(), poGRider.getBranchCode(), "") > 0) {
                        poJSON.put("result", "success");
                        poJSON.put("message", "Record saved successfully.");
                    } else {
                        poJSON.put("result", "error");
                        poJSON.put("message", poGRider.getErrMsg());
                    }
                } else {
                    poJSON.put("result", "error");
                    poJSON.put("message", "No record to save.");
                }
            } else {
                Model_Client_Account_Accreditation loOldEntity = new Model_Client_Account_Accreditation(poGRider);

                //replace with the primary key column info
                
                System.out.println("TransactionNo = " + this.getTransactionNo());
                JSONObject loJSON = loOldEntity.openRecord(getValue("sTransNox").toString());
                
                setModifiedDate(poGRider.getServerDate());
                setModifiedBy(poGRider.getUserID());
                System.out.println("loJSON = " + loJSON);
                if ("success".equals((String) loJSON.get("result"))) {
                    //replace the condition based on the primary key column of the record
                    lsSQL = MiscUtil.makeSQL(this, loOldEntity, "sTransNox = " + SQLUtil.toSQL(this.getTransactionNo()), "xCompnyNm»xCPerson1»xCPPosit1»xMobileNo»sEMailAdd»xCategrNm");
                    System.out.println("lsSQL = " + lsSQL);
                    if (!lsSQL.isEmpty()) {
                        if (poGRider.executeQuery(lsSQL, getTable(), poGRider.getBranchCode(), "") > 0) {
                            poJSON.put("result", "success");
                            poJSON.put("message", "Record saved successfully.");
                        } else {
                            poJSON.put("result", "error");
                            poJSON.put("message", poGRider.getErrMsg());
                        }
                    } else {
                        poJSON.put("result", "success");
                        poJSON.put("message", "No updates has been made.");
                    }
                } else {
                    poJSON.put("result", "error");
                    poJSON.put("message", "Record discrepancy. Unable to save record.");
                }
            }
        } else {
            poJSON.put("result", "error");
            poJSON.put("message", "Invalid update mode. Unable to save record.");
            return poJSON;
        }

        return poJSON;
    }

    /**
     * Prints all the public methods used<br>
     * and prints the column names of this entity.
     */
    @Override
    public void list() {
        Method[] methods = this.getClass().getMethods();

        System.out.println("--------------------------------------------------------------------");
        System.out.println("LIST OF PUBLIC METHODS FOR " + this.getClass().getName() + ":");
        System.out.println("--------------------------------------------------------------------");
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        try {
            int lnRow = poEntity.getMetaData().getColumnCount();

            System.out.println("--------------------------------------------------------------------");
            System.out.println("ENTITY COLUMN INFO");
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Total number of columns: " + lnRow);
            System.out.println("--------------------------------------------------------------------");

            for (int lnCtr = 1; lnCtr <= lnRow; lnCtr++) {
                System.out.println("Column index: " + (lnCtr) + " --> Label: " + poEntity.getMetaData().getColumnLabel(lnCtr));
                if (poEntity.getMetaData().getColumnType(lnCtr) == Types.CHAR
                        || poEntity.getMetaData().getColumnType(lnCtr) == Types.VARCHAR) {

                    System.out.println("Column index: " + (lnCtr) + " --> Size: " + poEntity.getMetaData().getColumnDisplaySize(lnCtr));
                }
            }
        } catch (SQLException e) {
        }

    }

    /**
     * Description: Sets the ClientID of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setTransactionNo(String fsValue) {
        return setValue("sTransNox", fsValue);
    }

    /**
     * @return The sTransNox of this record.
     */
    public String getTransactionNo() {
        return (String) getValue("sTransNox");
    }

    /**
     * Description: Sets the dTransact of this record.
     *
     * @param fdValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setTransDate(Date fdValue) {
        return setValue("dTransact", fdValue);
    }

    /**
     * @return The dTransact of this record.
     */
    public Date getTransDate() {
        return (Date) getValue("dTransact");
    }
    /**
     * Description: Sets the cAcctType of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setAcctType(String fsValue) {
        return setValue("cAcctType", fsValue);
    }

    /**
     * @return The cAcctType of this record.
     */
    public String getAcctType() {
        return (String) getValue("cAcctType");
    }


    /**
     * Description: Sets the sClientID of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setClientID(String fsValue) {
        return setValue("sClientID", fsValue);
    }

    /**
     * @return The sClientID of this record.
     */
    public String getClientID() {
        return (String) getValue("sClientID");
    }

    /**
     * Description: Sets the sContctID of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setContactID(String fsValue) {
        return setValue("sContctID", fsValue);
    }

    /**
     * @return The sContctID of this record.
     */
    public String getContactID() {
        return (String) getValue("sContctID");
    }

    /**
     * Description: Sets the sRemarksx of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setRemarks(String fsValue) {
        return setValue("sRemarksx", fsValue);
    }

    /**
     * @return The sRemarksx of this record.
     */
    public String getRemarks() {
        return (String) getValue("sRemarksx");
    }

    /**
     * Description: Sets the cTranType of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setTranType(String fsValue) {
        return setValue("cTranType", fsValue);
    }

    /**
     * @return The cTranType of this record.
     */
    public String getTranType() {
        return (String) getValue("cTranType");
    }
    


    /**
     * Description: Sets the sCategrCd of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setCategoryCode(String fsValue) {
        return setValue("sCategrCd", fsValue);
    }

    /**
     * @return The sCategrCd of this record.
     */
    public String getCategoryCode() {
        return (String) getValue("sCategrCd");
    }
    
    /**
     * Description: Sets the cTranStat of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setTranStatus(String fsValue) {
        return setValue("cTranStat", fsValue);
    }

    /**
     * @return The cTranStat of this record.
     */
    public String getTranStatus() {
        return (String) getValue("cTranStat");
    }
    
    /**
     * Description: Sets the sApproved of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setApproveBy(String fsValue) {
        return setValue("sApproved", fsValue);
    }

    /**
     * @return The sApproved of this record.
     */
    public String getApproveBy() {
        return (String) getValue("sApproved");
    }

    /**
     * Description: Sets the dApproved of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setDateApproved(Date fsValue) {
        return setValue("dApproved", fsValue);
    }

    /**
     * @return The dApproved of this record.
     */
    public Date getDateApproved() {
        return (Date) getValue("dApproved");
    }
    /**
     * Description: Sets the sModified of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setModifiedBy(String fsValue) {
        return setValue("sModified", fsValue);
    }

    /**
     * @return The sModified of this record.
     */
    public String getModifiedBy() {
        return (String) getValue("sModified");
    }

    /**
     * Description: Sets the dModified of this record.
     *
     * @param fsValue
     * @return True if the record assignment is successful.
     */
    public JSONObject setModifiedDate(Date fdValue) {
        return setValue("dModified", fdValue);
    }

    /**
     * @return The dModified of this record.
     */
    public Date getModifiedDate() {
        return (Date) getValue("dModified");
    }
    /**
     * Gets the SQL statement for this entity.
     * 
     * @return SQL Statement
     */
    public String makeSQL() {
        return MiscUtil.makeSQL(this, "xCompnyNm»xCPerson1»xCPPosit1»xMobileNo»xEMailAdd»xCategrNm");
    }
    public String getSQL(){
        return "SELECT " +
                "  a.sTransNox, " +
                "  a.dTransact, " +
                "  a.cAcctType, " +
                "  a.sClientID, " +
                "  a.sContctID, " +
                "  a.sRemarksx, " +
                "  a.cTranType, " +
                "  a.sCategrCd, " +
                "  a.cTranStat, " +
                "  a.sApproved, " +
                "  a.dApproved, " +
                "  a.sModified, " +
                "  a.dModified, " +
                "FROM " +
                "  Account_Client_Acccreditation a " ;
    }
    private void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());

            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);
            poEntity.updateObject("cTranStat", TransactionStatus.STATE_OPEN);
            poEntity.updateObject("cAcctType", Logical.NO);
            poEntity.updateObject("cTranType", Logical.NO);
            poEntity.updateObject("dTransact", poGRider.getServerDate());
            poEntity.updateObject("dApproved", "0000-00-00");
            
            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public JSONObject openRecord(String string, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public JSONObject openRecord(String string, Object o, Object o1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public JSONObject openRecord(String string, Object o, Object o1, Object o2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public JSONObject openRecord(String string, Object o, Object o1, Object o2, Object o3) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public JSONObject updateRecord() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
