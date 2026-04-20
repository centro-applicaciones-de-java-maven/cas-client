/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.guanzon.cas.client.constants;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public class APPaymentConstants {
    
    public static final ObservableList<String> regstrList = FXCollections.observableArrayList(
        "Non Vatable",
        "Vat Registered",
        "BIR Registered to Supplier"
    );
    
    public static final ObservableList<String> paymentList = FXCollections.observableArrayList(
        "Check",
        "Deposit to Account",
        "Bank Transfer"
    );
    
}
