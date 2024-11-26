/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.guanzon.cas.client.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.guanzon.appdriver.base.GRider;

/**
 * FXML Controller class
 *
 * @author User
 */
public class GClientsController implements Initializable {


    private double xOffset = 0;
    private double yOffset = 0;

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    
    @FXML
    private AnchorPane draggablePane;
    @FXML
    private Button btnClose;
    @FXML
    private StackPane placeholder;
    @FXML
    private Label text;
    Object[] classArray = new Object[]{
        new ClientMasterParameterController(),
        new ClientMasterTransactionCompanyController(),
        new ClientMasterTransactionIndividualController(),
        new NewCustomerController()
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        draggablePane.setOnMousePressed(this::handleMousePressed);
        draggablePane.setOnMouseDragged(this::handleMouseDragged);

        // Close button functionality
        btnClose.setOnAction(event -> {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
        // TODO
    }

    private void handleMousePressed(MouseEvent event) {
        // Capture the initial offset between the mouse and the stage
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        // Get the current stage and update its position
        Stage stage = (Stage) draggablePane.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    // Example method to load FXML into placeholder
    public void loadContent(String fxmlFile, int num, GRider instance) {
        switch (num) {
            case 0:
                ((ClientMasterParameterController) classArray[num]).setGRider(instance);
                break;
            case 1:
                ((ClientMasterTransactionCompanyController) classArray[num]).setGRider(instance);
                break;
            case 2:
                ((ClientMasterTransactionIndividualController) classArray[num]).setGRider(instance);
                break;
            case 3:
                ((NewCustomerController) classArray[num]).setGRider(instance);
                break;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            loader.setController(classArray[num]);
            Parent childContent = loader.load();
            ClientMasterParameterController controller = new ClientMasterParameterController();
            loader.setController(controller);

            placeholder.getChildren().clear();
            placeholder.getChildren().add(childContent);
        } catch (Exception e) {
            System.out.println("");
            e.printStackTrace();
        }
    }

    public void closeDialog(ActionEvent event) {
        // Close the dialog window
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
