package ph.com.guanzongroup.cas.client.validator;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.guanzon.appdriver.base.GRiderCAS;
import org.json.simple.JSONObject;
import ph.com.guanzongroup.cas.iface.GValidator;
import ph.com.guanzongroup.cas.model.Model_AP_Client_Master;

/**
 *
 * @author User
 */
public class APClient_Appliance implements GValidator{

    private GRiderCAS poGRider;
    private JSONObject poJSON;

    Model_AP_Client_Master poMaster;

    @Override
    public void setApplicationDriver(Object applicationDriver) {
        poGRider = (GRiderCAS) applicationDriver;
    }

    @Override
    public void setTransactionStatus(String transactionStatus) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setMaster(Object value) {
        poMaster = (Model_AP_Client_Master) value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setDetail(ArrayList<Object> value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setOthers(ArrayList<Object> value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject validate() {
        
        try {
            
            poJSON = new JSONObject();
            
            //validate industry
            if (poGRider.getIndustry()== null || poGRider.getIndustry().isEmpty()) {
                poJSON.put("result", "error");
                poJSON.put("message", "Company is not set.");
                return poJSON;
            }
        
            //validate client id
            if (poMaster.getClientId() == null || poMaster.getClientId().isEmpty()) {
                poJSON.put("result", "error");
                poJSON.put("message", "Client must not be empty.");
                return poJSON;
            }

            //validate contact id
            if (poMaster.getContactId() == null || poMaster.getContactId().isEmpty()) {
                poJSON.put("result", "error");
                poJSON.put("message", "Contact person not be empty.");
                return poJSON;
            }

            //validate category code
            if (poMaster.getCategoryCode() == null || poMaster.getCategoryCode().isEmpty()) {
                poJSON.put("result", "error");
                poJSON.put("message", "Category not be empty.");
                return poJSON;
            }
            
            poJSON.put("result", "success");

            return poJSON;

        } catch (Exception e) {
            
            Logger.getLogger(APClient_MC.class.getName()).log(Level.SEVERE, null, e);
            
            poJSON = new JSONObject();
            poJSON.put("result", "error");
            poJSON.put("message", e.getMessage());
            
            return poJSON;
        }
    }
    
}
