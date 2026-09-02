package org.guanzon.cas.client.model;

import java.sql.SQLException;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.client.services.ClientModels;
import org.guanzon.cas.parameter.model.Model_Department;

//Extends the cas-parameter Model_Department with the Client_Master-typed head/supervisor/
//general-manager assignment accessors - these live here (not in cas-parameter) so cas-parameter
//itself never needs to depend on cas-client.
public class Model_Department_Client extends Model_Department {

    private Model_Client_Master poDeptHead;
    private Model_Client_Master poHead;
    private Model_Client_Master poSupervisor;
    private Model_Client_Master poGeneral;

    @Override
    public void initialize() {
        super.initialize();

        poDeptHead = new ClientModels(poGRider).ClientMaster();
        poHead = new ClientModels(poGRider).ClientMaster();
        poSupervisor = new ClientModels(poGRider).ClientMaster();
        poGeneral = new ClientModels(poGRider).ClientMaster();
    }

    public Model_Client_Master DepartmentHeadAssign() throws SQLException, GuanzonException {
        if (!"".equals((String) getValue("sDeptHead"))) {
            if (poDeptHead.getEditMode() == EditMode.READY
                    && poDeptHead.getClientId().equals((String) getValue("sDeptHead"))) {
                return poDeptHead;
            } else {
                poJSON = poDeptHead.openRecord((String) getValue("sDeptHead"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poDeptHead;
                } else {
                    poDeptHead.initialize();
                    return poDeptHead;
                }
            }
        } else {
            poDeptHead.initialize();
            return poDeptHead;
        }
    }

    public Model_Client_Master HeadAssign() throws SQLException, GuanzonException {
        if (!"".equals((String) getValue("sHAssgnID"))) {
            if (poHead.getEditMode() == EditMode.READY
                    && poHead.getClientId().equals((String) getValue("sHAssgnID"))) {
                return poHead;
            } else {
                poJSON = poHead.openRecord((String) getValue("sHAssgnID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poHead;
                } else {
                    poHead.initialize();
                    return poHead;
                }
            }
        } else {
            poHead.initialize();
            return poHead;
        }
    }

    public Model_Client_Master SupervisorAssign() throws SQLException, GuanzonException {
        if (!"".equals((String) getValue("sSAssgnID"))) {
            if (poSupervisor.getEditMode() == EditMode.READY
                    && poSupervisor.getClientId().equals((String) getValue("sSAssgnID"))) {
                return poSupervisor;
            } else {
                poJSON = poSupervisor.openRecord((String) getValue("sSAssgnID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poSupervisor;
                } else {
                    poSupervisor.initialize();
                    return poSupervisor;
                }
            }
        } else {
            poSupervisor.initialize();
            return poSupervisor;
        }
    }

    public Model_Client_Master GeneralAssign() throws SQLException, GuanzonException {
        if (!"".equals((String) getValue("sGenMgrID"))) {
            if (poGeneral.getEditMode() == EditMode.READY
                    && poGeneral.getClientId().equals((String) getValue("sGenMgrID"))) {
                return poGeneral;
            } else {
                poJSON = poGeneral.openRecord((String) getValue("sGenMgrID"));

                if ("success".equals((String) poJSON.get("result"))) {
                    return poGeneral;
                } else {
                    poGeneral.initialize();
                    return poGeneral;
                }
            }
        } else {
            poGeneral.initialize();
            return poGeneral;
        }
    }
}
