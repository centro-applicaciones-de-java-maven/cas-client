//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.layout.Pane;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
//import org.guanzon.appdriver.base.GRider;
//import org.guanzon.cas.client.controller.ClientMasterTransactionCompanyController;
//import org.guanzon.cas.client.controller.ClientMasterParameterController;
//import org.guanzon.cas.client.controller.ClientMasterTransactionIndividualController;
//import org.guanzon.cas.client.controller.NewCustomerController;
//
//public class testClient extends Application {
//
//    GRider instance;
//
//    Object[] classArray = new Object[]{
//        new ClientMasterParameterController(),
//        new ClientMasterTransactionCompanyController(),
//        new ClientMasterTransactionIndividualController(),
//        new NewCustomerController()
//    };
//
//    @Override
//    public void start(Stage primaryStage) {
//        try {
//            String path;
//            if (System.getProperty("os.name").toLowerCase().contains("win")) {
//                path = "D:/GGC_Maven_Systems";
//            } else {
//                path = "/srv/GGC_Maven_Systems";
//            }
//            System.setProperty("sys.default.path.config", path);
//
//            instance = new GRider("gRider");
//
//            if (!instance.logUser("gRider", "M001000001")) {
//                System.err.println(instance.getErrMsg());
//                System.exit(1);
//            }
//            System.out.println("sBranch code = " + instance.getBranchCode());
//            System.out.println("Connected");
//            System.setProperty("sys.default.path.metadata", "D:/GGC_Maven_Systems/config/metadata/");
//            
//            //Buttons for different actions
//            Button openDialogButton = new Button("ClientMasterParameter");
//            openDialogButton.setOnAction(event -> openDialog(primaryStage, "ClientMasterParameter"));
//
//            Button openAnotherDialogButton = new Button("ClientMasterTransactionCompany");
//            openAnotherDialogButton.setOnAction(event -> openDialog(primaryStage, "ClientMasterTransactionCompany"));
//
//            Button showMessageButton = new Button("ClientMasterTransactionIndividual");
//            showMessageButton.setOnAction(event -> openDialog(primaryStage, "ClientMasterTransactionIndividual"));
//
//            Button closeAppButton = new Button("NewCustomer");
//            closeAppButton.setOnAction(event -> openDialog(primaryStage, "NewCustomer"));
//
//            StackPane mainPane = new StackPane();
//            mainPane.getChildren().addAll(openDialogButton, openAnotherDialogButton, showMessageButton, closeAppButton);
//
//            // StackPane allows multiple nodes; adjusting button positions manually
//            openDialogButton.setTranslateY(-100);
//            openAnotherDialogButton.setTranslateY(-50);
//            showMessageButton.setTranslateY(0);
//            closeAppButton.setTranslateY(50);
//
//            primaryStage.setTitle("Main Window");
//            primaryStage.setScene(new Scene(mainPane, 400, 300));
//            primaryStage.show();
//
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void openDialog(Stage ownerStage, String module_name) {
//
//        try {
//            int num = 0;
//            String lsfxml = "";
//            switch (module_name) {
//                case "ClientMasterParameter":
//                    num = 0;
//                    lsfxml = "ClientMasterParameter.fxml";
//                    ((ClientMasterParameterController) classArray[num]).setGRider(instance);
//                    break;
//                case "ClientMasterTransactionCompany":
//                    num = 1;
//                    lsfxml = "ClientMasterTransactionCompany.fxml";
//                    ((ClientMasterTransactionCompanyController) classArray[num]).setGRider(instance);
//                    break;
//                case "ClientMasterTransactionIndividual":
//                    num = 2;
//                    lsfxml = "ClientMasterTransactionIndividual.fxml";
//                    ((ClientMasterTransactionIndividualController) classArray[num]).setGRider(instance);
//                    break;
//                case "NewCustomer":
//                    num = 3;
//                    lsfxml = "NewCustomer.fxml";
//                    ((NewCustomerController) classArray[num]).setGRider(instance);
//                    break;
//            }
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/guanzon/cas/views/" + lsfxml));
//            loader.setController(classArray[num]);
//            Pane dialogPane = loader.load();
//            Scene dialogScene = new Scene(dialogPane);
//
//            String cssPath = getClass().getResource("/org/guanzon/cas/css/MainPanelStyle.css").toExternalForm();
//            String cssPath2 = getClass().getResource("/org/guanzon/cas/css/StyleSheet.css").toExternalForm();
//            dialogScene.getStylesheets().add(cssPath);
//            dialogScene.getStylesheets().add(cssPath2);
//
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle(lsfxml);
//            dialogStage.setScene(dialogScene);
//
//            dialogStage.initOwner(ownerStage); // Tie dialog to main window
//            dialogStage.initModality(Modality.NONE); // Non-blocking
//            dialogStage.setAlwaysOnTop(true); // Ensure dialog stays on top
//
//            dialogStage.show();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
