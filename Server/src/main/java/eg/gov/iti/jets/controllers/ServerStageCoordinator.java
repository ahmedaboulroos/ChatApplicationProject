package eg.gov.iti.jets.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerStageCoordinator {
    private Stage stage;

    public ServerStageCoordinator(Stage stage) {
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

    public void startErrorScene(String error) {
        System.out.println(">> ERROR IN : " + error);
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
