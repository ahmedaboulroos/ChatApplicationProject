package eg.gov.iti.jets;

import eg.gov.iti.jets.controllers.ClientStageCoordinator;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.network.implementations.ClientService;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientStageCoordinator coordinator = ClientStageCoordinator.getInstance();
        coordinator.setStage(primaryStage);
        coordinator.startLoginScene();
    }

    @Override
    public void stop() throws Exception {
        if (RMIConnection.isConnectionEstablished()) {
            if (ClientStageCoordinator.getInstance().currentUser != null)
                RMIConnection.getServerService().logout(ClientStageCoordinator.getInstance().currentUser.getId(), ClientService.getInstance());
        }
        System.exit(0);
    }

}
