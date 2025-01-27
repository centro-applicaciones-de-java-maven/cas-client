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
public class ModelMobile {

    public SimpleStringProperty index01;
    public SimpleStringProperty index02;
    public SimpleStringProperty index03;
    public SimpleStringProperty index04;
    public SimpleStringProperty index05;
    public SimpleStringProperty index06;
    public SimpleStringProperty index07;
    public SimpleStringProperty index08;
    public SimpleStringProperty index09;
    public SimpleStringProperty index10;
    public SimpleStringProperty index11;
    public SimpleStringProperty index12;
    public SimpleStringProperty index13;
    public SimpleStringProperty index14;
    public SimpleStringProperty index15;
    public SimpleStringProperty index16;
    public SimpleStringProperty index17;
    public SimpleStringProperty index18;
    public SimpleStringProperty index19;
    public SimpleStringProperty index20;

    public static ObservableList<String> mobileType = FXCollections.observableArrayList("Mobile No", "Tel No", "Fax No");
    public static ObservableList<String> mobileOwn = FXCollections.observableArrayList("Personal", "Office", "Others");

    public ModelMobile(String index01,
            String index02,
            String index03,
            String index04) {

        this.index01 = new SimpleStringProperty(index01);
        this.index02 = new SimpleStringProperty(index02);
        this.index03 = new SimpleStringProperty(index03);
        this.index04 = new SimpleStringProperty(index04);

    }

    public String getIndex01() {
        return index01.get();
    }

    public void setIndex01(String index01) {
        this.index01.set(index01);
    }

    public String getIndex02() {
        return index02.get();
    }

    public void setIndex02(String index02) {
        this.index02.set(index02);
    }

    public String getIndex03() {
        return mobileOwn.get(Integer.parseInt(index03.get()));
    }

    public void setIndex03(String index03) {
        this.index03.set(index03);
    }

    public String getIndex04() {
        return mobileType.get(Integer.parseInt(index04.get()));
    }

    public void setIndex04(String index04) {
        this.index04.set(index04);
    }

}
