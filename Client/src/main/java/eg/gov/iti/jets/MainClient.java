package eg.gov.iti.jets;

import eg.gov.iti.jets.controllers.ClientStageCoordinator;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        RMIConnection.startConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Client start...");
        ClientStageCoordinator coordinator = ClientStageCoordinator.getInstance();
        coordinator.setStage(primaryStage);
        coordinator.startLoginScene();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

}
