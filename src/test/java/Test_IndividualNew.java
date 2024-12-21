/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.guanzon.cas.client.controller.IndividualNewController;

public class Test_IndividualNew extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/guanzon/cas/client/views/IndividualNew.fxml"));

        // Set the controller to the FXMLLoader (Optional, depending on how the FXML is set up)
       //loader.setController(getClass().getResource("/org/guanzon/cas/client/controller/IndividualNewController.java"));
      //  IndividualNewController controller = new IndividualNewController(); // create the controller instance
     //   loader.setController(controller); // set the controller instance here
        
     
     IndividualNewController controller = new IndividualNewController();
     loader.setController(controller);
        if (controller != null) {
            System.out.println("Controller loaded successfully: " + controller.getClass().getName());
        } else {
            System.out.println("Controller is null!");
        }
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/org/guanzon/cas/client/css/Tables.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/org/guanzon/cas/client/css/StyleSheet.css").toExternalForm());

       // primaryStage.initStyle(StageStyle.UNDECORATED); // Or StageStyle.UNDECORATED for complete removal of window borders
        primaryStage.setTitle("JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application        launch(args); // Launch JavaFX application

    }
}
