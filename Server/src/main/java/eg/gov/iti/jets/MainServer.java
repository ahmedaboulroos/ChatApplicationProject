package eg.gov.iti.jets;

import eg.gov.iti.jets.controllers.ServerStageCoordinator;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MainServer extends Application {

    private boolean rmiConnStarted = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        this.rmiConnStarted = RMIConnection.getInstance().initConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ServerStageCoordinator coordinator = new ServerStageCoordinator(primaryStage);
        if (!rmiConnStarted) {
            coordinator.startErrorScene("RMI");
            Platform.exit();
        } else {
            coordinator.startMainServerScene();
        }
    }

    @Override
    public void stop() throws Exception {
        RMIConnection.getInstance().stopConnection();
        DBConnection.stopConnection();
        System.exit(0);
    }

}
