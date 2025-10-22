package ph.com.guanzongroup.cas.client.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelSocialMedia {
    public SimpleStringProperty index01;
    public SimpleStringProperty index02;
    public SimpleStringProperty index03;
    public SimpleStringProperty index04; 
    public SimpleStringProperty index05;
    
    public static ObservableList<String> socialTyp = FXCollections.observableArrayList("Facebook", "Instagram", "X","Others");
    
    public ModelSocialMedia(String index01,
               String index02,
               String index03,
               String index04){
        
        this.index01 = new SimpleStringProperty(index01);
        this.index02 = new SimpleStringProperty(index02);
        this.index03 = new SimpleStringProperty(index03);
        this.index04 = new SimpleStringProperty(index04);
    }

    public String getIndex01(){return index01.get();}
    public void setIndex01(String index01){this.index01.set(index01);}
    
    public String getIndex02(){return index02.get();}
    public void setIndex02(String index02){this.index02.set(index02);}
    
    public String getIndex03(){return socialTyp.get(Integer.parseInt(index03.get()));}
    public void setIndex03(String index03){this.index03.set(index03);}
    
    public String getIndex04(){return index04.get();}
    public void setIndex04(String index04){this.index04.set(index04);}
}
