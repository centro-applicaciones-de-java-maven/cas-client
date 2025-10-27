package org.guanzon.cas.client.validator;

import ph.com.guanzongroup.cas.iface.GValidator;


/**
 *
 * @author Guillier
 */
public class APClientValidatorFactory {
    
    public static GValidator make(String industryId){
        
        switch (industryId) {
            case "01": //Mobile Phone
                return new APClient_MP();
            case "02": //Motorcycle
                return new APClient_MC();
            case "03": //Vehicle
                return new APClient_Car();
            case "04": //Monarch
                return new APClient_Monarch();
            case "05": //Los Pedritos
                return new APClient_LP();
            case "06": //General
                return new APClient_General();
            case "07": //Appliances
                return new APClient_Appliance();

            case "": //Main Office
                return new APClient_General();
            default:
                return null;
        }
    }
}
