package org.guanzon.cas.client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.cas.client.controller.IndividualNewController;

public class ClientGUI extends Application {
    private double xOffset = 0; 
    private double yOffset = 0;
    
    private static GRiderCAS poGRider;
    private static LogWrapper poWrapper;
    private static ClientInfo poClient;
    
    private static String psClientId;
    private static boolean pbCancelled;
    
    public void setGRider(GRiderCAS griderCAS){
        poGRider = griderCAS;
    }
    
    public void setLogWrapper(LogWrapper wrapper){
        poWrapper = wrapper;
    }
    
    public void setClientId(String clientId){
        psClientId = clientId;
    }
    
    public ClientInfo getClient(){
        return poClient;
    }
    
    public boolean isCancelled(){
        return pbCancelled;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/guanzon/cas/client/views/IndividualNew.fxml"));

        IndividualNewController controller = new IndividualNewController();
        controller.setGRider(poGRider);
        controller.setLogWrapper(poWrapper);
        controller.setClientId(psClientId);
        
        loader.setController(controller);

        Parent parent = loader.load();
        
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Client Info");
        primaryStage.showAndWait();
        
        pbCancelled = controller.isCancelled();
        if (!pbCancelled) poClient = controller.getClient();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
