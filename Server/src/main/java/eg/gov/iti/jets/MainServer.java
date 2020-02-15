package eg.gov.iti.jets;

import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.persistence.DBConnection;
import eg.gov.iti.jets.views.ServerStageCoordinator;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainServer extends Application {

    private boolean rmiConnStarted = false;
    private boolean dbConnStarted = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        this.dbConnStarted = DBConnection.getInstance().initConnection();
        this.rmiConnStarted = RMIConnection.getInstance().initConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ServerStageCoordinator coordinator = ServerStageCoordinator.getInstance();
        coordinator.setStage(primaryStage);
        coordinator.startMainServerScene();
    }

    @Override
    public void stop() throws Exception {
        RMIConnection.getInstance().stopConnection();
        DBConnection.getInstance().stopConnection();
        System.exit(0);
    }
}
