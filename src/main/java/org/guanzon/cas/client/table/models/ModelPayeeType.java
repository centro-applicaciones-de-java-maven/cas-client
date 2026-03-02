/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.guanzon.cas.client.table.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public class ModelPayeeType {
    
    public SimpleStringProperty index01;
    public SimpleStringProperty index02;
    public SimpleStringProperty index03;
    public SimpleStringProperty index04;
    
    public static ObservableList<String> mobileType = FXCollections.observableArrayList("Accounts Payable", "Accounts Receivables");
    
    public ModelPayeeType(String index01,
            String index02,
            String index03){
        
        this.index01 = new SimpleStringProperty(index01);
        this.index02 = new SimpleStringProperty(index02);
        this.index03 = new SimpleStringProperty(index03);
    }

    public SimpleStringProperty getIndex01() {
        return index01;
    }

    public void setIndex01(SimpleStringProperty index01) {
        this.index01 = index01;
    }

    public SimpleStringProperty getIndex02() {
        return index02;
    }

    public void setIndex02(SimpleStringProperty index02) {
        this.index02 = index02;
    }

    public SimpleStringProperty getIndex03() {
        return index03;
    }

    public void setIndex03(SimpleStringProperty index03) {
        this.index03 = index03;
    }
    
    
}
