/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test_IndividualNew extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/guanzon/cas/client/views/IndividualNew.fxml"));

        // Set the controller to the FXMLLoader
        loader.setController(getClass().getResource("/org/guanzon/cas/client/controller/IndividualNewController.java"));

        // Load the FXML content
        Parent root = loader.load();

        // Set up the scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application
    }
}
