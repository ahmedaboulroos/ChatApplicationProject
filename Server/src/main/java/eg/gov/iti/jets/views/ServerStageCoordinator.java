package eg.gov.iti.jets.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerStageCoordinator {
    private static ServerStageCoordinator instance;
    private Stage stage;

    private ServerStageCoordinator() {
    }

    public static ServerStageCoordinator getInstance() {
        if (instance == null) {
            instance = new ServerStageCoordinator();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void startMainServerScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MainServerView.fxml"));
        Parent mainServerView = fxmlLoader.load();
        MainServerViewController mainServerViewController = fxmlLoader.getController();
        stage.setScene(new Scene(mainServerView));
        stage.setTitle("Chat Application Server - Admin Panel");
        stage.show();
    }

//    public void startServerLoginScene(boolean dbConnStarted, boolean rmiConnStarted) {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
//        Parent mainServerView = fxmlLoader.load();
//        MainServerViewController mainServerViewController = fxmlLoader.getController();
//        stage.setScene(new Scene(mainServerView));
//        stage.setTitle("Chat Application Server - Admin Panel");
//        stage.show();
//    }

}
