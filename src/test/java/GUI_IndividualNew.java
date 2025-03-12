import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.client.controller.IndividualNewController;

public class GUI_IndividualNew extends Application {
    private double xOffset = 0; 
    private double yOffset = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/guanzon/cas/client/views/IndividualNew.fxml"));

        IndividualNewController controller = new IndividualNewController();
        loader.setController(controller);
        if (controller != null) {
            System.out.println("Controller loaded successfully: " + controller.getClass().getName());
        } else {
            System.out.println("Controller is null!");
        }
        
        //ADDNEW OR UPDATE
        controller.pnEditMode= EditMode.ADDNEW;
        controller.lsID ="M00125000003";
        
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
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application  

    }
}
