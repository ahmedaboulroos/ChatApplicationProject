package eg.gov.iti.jets;

import eg.gov.iti.jets.controllers.ServerStageCoordinator;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainServer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        RMIConnection.getInstance().initConnection();
        ServerStageCoordinator coordinator = new ServerStageCoordinator(primaryStage);
        coordinator.startMainServerScene();
    }

    @Override
    public void stop() throws Exception {
        RMIConnection.getInstance().stopConnection();
        DBConnection.stopConnection();
        System.exit(0);
    }

}
