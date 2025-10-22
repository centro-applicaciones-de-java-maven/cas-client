import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.LogWrapper;
import ph.com.guanzongroup.cas.client.controller.IndividualNewController;

public class GUI_IndividualNew extends Application {
    private double xOffset = 0; 
    private double yOffset = 0;
    
    private GRiderCAS poGRider;
    private LogWrapper poWrapper;
    
    public void setGRider(GRiderCAS griderCAS){
        poGRider = griderCAS;
    }
    
    public void setLogWrapper(LogWrapper wrapper){
        poWrapper = wrapper;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/guanzon/cas/client/views/IndividualNew.fxml"));

        IndividualNewController controller = new IndividualNewController();
        controller.setGRider(poGRider);
        controller.setLogWrapper(poWrapper);
        
        loader.setController(controller);

        Parent root = loader.load();
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED); 
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application  
    }
}
