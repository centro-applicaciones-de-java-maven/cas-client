package org.guanzon.cas.client;

import java.sql.SQLException;
import org.guanzon.appdriver.agent.ShowDialogFX;
import org.guanzon.appdriver.base.CommonUtils;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.constant.ClientType;
import org.guanzon.cas.client.model.Model_Department_Client;
import org.guanzon.cas.parameter.Department;
import org.json.simple.JSONObject;

//Extends cas-parameter's Department with the head/supervisor/general-manager assignment
//screens - these live here (not in cas-parameter) so cas-parameter itself never needs to
//depend on cas-client. See Model_Department_Client for the matching model-layer split.
public class DepartmentAssignment extends Department {

    private Model_Department_Client poModel;

    @Override
    public void initialize() throws SQLException, GuanzonException {
        //Runs Department's own initialize() first (sets psRecdStat/pbInitRec and, as a side
        //effect, a plain Model_Department that this subclass never uses - see getModel() below).
        super.initialize();

        poModel = new Model_Department_Client();
        poModel.setApplicationDriver(poGRider);
        poModel.setXML("Model_Department");
        poModel.setTableName("Department");
        poModel.initialize();
    }

    @Override
    public Model_Department_Client getModel() {
        return poModel;
    }

    public JSONObject searchDepartmentHead(String fsValue, boolean fbByCode) throws SQLException, GuanzonException, Exception {

        if (fbByCode) {
            JSONObject loJSON = new JSONObject();
            if (fsValue.equals(getModel().DepartmentHeadAssign().getClientId())) {
                loJSON.put("result", "success");
                return loJSON;
            } else {
                loJSON.put("result", "error");
                loJSON.put("message", "Client not found.");
                return loJSON;
            }
        } else {
            JSONObject loJSON = null;
            if (!fsValue.isEmpty()) {

                String lsSQL = "SELECT"
                        + " sClientID"
                        + ", sCompnyNm"
                        + " FROM Client_Master"
                        + " WHERE cRecdStat= '1'";

                loJSON = ShowDialogFX.Search(poGRider,
                        lsSQL,
                        fsValue,
                        "Client ID»Client Name",
                        "sClientID»sCompnyNm",
                        "sClientID»sCompnyNm",
                        fbByCode ? 0 : 1);

                if (loJSON == null) {
                    return loJSON;
                } else {
                    if ("error".equals(loJSON.get("result"))) {
                        return loJSON;
                    }
                }
            }

            //initialize Client GUI
            ClientGUI loClient = new ClientGUI();

            loClient.setGRider(poGRider);
            loClient.setLogWrapper(null);
            loClient.setCategoryCode(poGRider.getCategory());

            //filter client type
            loClient.setClientType(ClientType.INDIVIDUAL);

            //searchRecord(fsValue,fbByCode) will run make sure to set client and bycode
            //bycode true client id
            //bycode false company
            //set search by code
            loClient.setByCode(fbByCode);

            if (loJSON != null) {
                getModel().setDeptHeadAssignedId(loJSON.get("sClientID").toString());

                //set client id
                loClient.setClientId(getModel().getDeptHeadAssignedId());

            } else {
                loClient.setClientId("");

                JSONObject loResult = new JSONObject();
                loResult.put("result", "error");
                return loResult;
            }

            //load record
            CommonUtils.showModal(loClient);

            //initialize new json for result
            JSONObject loResult = new JSONObject();

            //load if button
            if (!loClient.isCancelled()) {

                getModel().setDeptHeadAssignedId(loClient.getClient().getModel().getClientId() != null ? loClient.getClient().getModel().getClientId() : "");

            }
            loResult.put("result", "success");
            return loResult;
        }
    }

    public JSONObject searchHead(String fsValue, boolean fbByCode) throws SQLException, GuanzonException, Exception {

        if (fbByCode) {
            JSONObject loJSON = new JSONObject();
            if (fsValue.equals(getModel().HeadAssign().getClientId())) {
                loJSON.put("result", "success");
                return loJSON;
            } else {
                loJSON.put("result", "error");
                loJSON.put("message", "Client not found.");
                return loJSON;
            }
        } else {
            JSONObject loJSON = null;
            if (!fsValue.isEmpty()) {

                String lsSQL = "SELECT"
                        + " sClientID"
                        + ", sCompnyNm"
                        + " FROM Client_Master"
                        + " WHERE cRecdStat= '1'";

                loJSON = ShowDialogFX.Search(poGRider,
                        lsSQL,
                        fsValue,
                        "Client ID»Client Name",
                        "sClientID»sCompnyNm",
                        "sClientID»sCompnyNm",
                        fbByCode ? 0 : 1);

                if (loJSON == null) {
                    return loJSON;
                } else {
                    if ("error".equals(loJSON.get("result"))) {
                        return loJSON;
                    }
                }
            }

            //initialize Client GUI
            ClientGUI loClient = new ClientGUI();

            loClient.setGRider(poGRider);
            loClient.setLogWrapper(null);
            loClient.setCategoryCode(poGRider.getCategory());

            //filter client type
            loClient.setClientType(ClientType.INDIVIDUAL);

            //searchRecord(fsValue,fbByCode) will run make sure to set client and bycode
            //bycode true client id
            //bycode false company
            //set search by code
            loClient.setByCode(fbByCode);

            if (loJSON != null) {
                getModel().setDeptHeadId(loJSON.get("sClientID").toString());

                //set client id
                loClient.setClientId(getModel().getDeptHeadId());

            } else {
                loClient.setClientId("");
                JSONObject loResult = new JSONObject();
                loResult.put("result", "error");
                return loResult;
            }

            //load record
            CommonUtils.showModal(loClient);

            //initialize new json for result
            JSONObject loResult = new JSONObject();

            //load if button
            if (!loClient.isCancelled()) {

                getModel().setDeptHeadId(loClient.getClient().getModel().getClientId() != null ? loClient.getClient().getModel().getClientId() : "");

            }
            loResult.put("result", "success");
            return loResult;
        }
    }

    public JSONObject searchSupervisor(String fsValue, boolean fbByCode) throws SQLException, GuanzonException, Exception {

        if (fbByCode) {
            JSONObject loJSON = new JSONObject();
            if (fsValue.equals(getModel().SupervisorAssign().getClientId())) {
                loJSON.put("result", "success");
                return loJSON;
            } else {
                loJSON.put("result", "error");
                loJSON.put("message", "Client not found.");
                return loJSON;
            }
        } else {
            JSONObject loJSON = null;
            if (!fsValue.isEmpty()) {

                String lsSQL = "SELECT"
                        + " sClientID"
                        + ", sCompnyNm"
                        + " FROM Client_Master"
                        + " WHERE cRecdStat= '1'";

                loJSON = ShowDialogFX.Search(poGRider,
                        lsSQL,
                        fsValue,
                        "Client ID»Client Name",
                        "sClientID»sCompnyNm",
                        "sClientID»sCompnyNm",
                        fbByCode ? 0 : 1);

                if (loJSON == null) {
                    return loJSON;
                } else {
                    if ("error".equals(loJSON.get("result"))) {
                        return loJSON;
                    }
                }
            }

            //initialize Client GUI
            ClientGUI loClient = new ClientGUI();

            loClient.setGRider(poGRider);
            loClient.setLogWrapper(null);
            loClient.setCategoryCode(poGRider.getCategory());

            //filter client type
            loClient.setClientType(ClientType.INDIVIDUAL);

            //searchRecord(fsValue,fbByCode) will run make sure to set client and bycode
            //bycode true client id
            //bycode false company
            //set search by code
            loClient.setByCode(fbByCode);

            if (loJSON != null) {
                getModel().setDeptSupervisorAssignedId(loJSON.get("sClientID").toString());

                //set client id
                loClient.setClientId(getModel().getDeptSupervisorAssignedId());

            } else {
                loClient.setClientId("");
                JSONObject loResult = new JSONObject();
                loResult.put("result", "error");
                return loResult;
            }

            //load record
            CommonUtils.showModal(loClient);

            //initialize new json for result
            JSONObject loResult = new JSONObject();

            //load if button
            if (!loClient.isCancelled()) {

                getModel().setDeptSupervisorAssignedId(loClient.getClient().getModel().getClientId() != null ? loClient.getClient().getModel().getClientId() : "");

            }
            loResult.put("result", "success");
            return loResult;
        }
    }

    public JSONObject searchGeneral(String fsValue, boolean fbByCode) throws SQLException, GuanzonException, Exception {

        if (fbByCode) {
            JSONObject loJSON = new JSONObject();
            if (fsValue.equals(getModel().GeneralAssign().getClientId())) {
                loJSON.put("result", "success");
                return loJSON;
            } else {
                loJSON.put("result", "error");
                loJSON.put("message", "Client not found.");
                return loJSON;
            }
        } else {
            JSONObject loJSON = null;
            if (!fsValue.isEmpty()) {

                String lsSQL = "SELECT"
                        + " sClientID"
                        + ", sCompnyNm"
                        + " FROM Client_Master"
                        + " WHERE cRecdStat= '1'";

                loJSON = ShowDialogFX.Search(poGRider,
                        lsSQL,
                        fsValue,
                        "Client ID»Client Name",
                        "sClientID»sCompnyNm",
                        "sClientID»sCompnyNm",
                        fbByCode ? 0 : 1);

                if (loJSON == null) {
                    return loJSON;
                } else {
                    if ("error".equals(loJSON.get("result"))) {
                        return loJSON;
                    }
                }
            }

            //initialize Client GUI
            ClientGUI loClient = new ClientGUI();

            loClient.setGRider(poGRider);
            loClient.setLogWrapper(null);
            loClient.setCategoryCode(poGRider.getCategory());

            //filter client type
            loClient.setClientType(ClientType.INDIVIDUAL);

            //searchRecord(fsValue,fbByCode) will run make sure to set client and bycode
            //bycode true client id
            //bycode false company
            //set search by code
            loClient.setByCode(fbByCode);

            if (loJSON != null) {
                getModel().setGeneralAssignedId(loJSON.get("sClientID").toString());

                //set client id
                loClient.setClientId(getModel().getGeneralAssignedId());

            } else {
                loClient.setClientId("");
                JSONObject loResult = new JSONObject();
                loResult.put("result", "error");
                return loResult;
            }

            //load record
            CommonUtils.showModal(loClient);

            //initialize new json for result
            JSONObject loResult = new JSONObject();

            //load if button
            if (!loClient.isCancelled()) {

                getModel().setGeneralAssignedId(loClient.getClient().getModel().getClientId() != null ? loClient.getClient().getModel().getClientId() : "");

            }
            loResult.put("result", "success");
            return loResult;
        }
    }
}
