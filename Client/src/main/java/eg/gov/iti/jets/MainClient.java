package eg.gov.iti.jets;

import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.views.StageCoordinator;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        System.out.println("Client init...");
        RMIConnection.getInstance().initConnection();
        RMIConnection.getInstance().startConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Client start...");
        StageCoordinator coordinator = new StageCoordinator(primaryStage);
        coordinator.startLoginScene();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Client stop...");
        RMIConnection.getInstance().stopConnection();
    }

}
