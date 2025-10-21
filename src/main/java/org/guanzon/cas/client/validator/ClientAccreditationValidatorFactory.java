/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.guanzon.cas.client.validator;

import org.guanzon.appdriver.iface.GValidator;

/**
 *
 * @author Guillier
 */
public class ClientAccreditationValidatorFactory {
    
    public static GValidator make(String industryId){
        
        switch (industryId) {
            case "01": //Mobile Phone
                return new ClientAccreditation_MP();
            case "02": //Motorcycle
                return new ClientAccreditation_MC();
            case "03": //Vehicle
                return new ClientAccreditation_Car();
            case "04": //Monarch
                return new ClientAccreditation_Monarch();
            case "05": //Los Pedritos
                return new ClientAccreditation_LP();
            case "06": //General
                return new ClientAccreditation_General();
            case "07": //Appliances
                return new ClientAccreditation_Appliance();

            case "": //Main Office
                return new ClientAccreditation_General();
            default:
                return null;
        }
    }
}
