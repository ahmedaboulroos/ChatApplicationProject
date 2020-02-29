package eg.gov.iti.jets.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerStageCoordinator {
    private static Stage stage;
    private static MainServerViewController mainServerViewController;

    public ServerStageCoordinator(Stage stage) {
        ServerStageCoordinator.stage = stage;
    }

    public static MainServerViewController getMainServerViewController() {
        return mainServerViewController;
    }

    public static void updateUsers() {
        Platform.runLater(() -> {
            mainServerViewController.getUsersViewController().updateUsersTable();
            mainServerViewController.getDashboardViewController().loadGenderStatisticsData();
            mainServerViewController.getDashboardViewController().loadCountryStatisticsData();
        });
    }

//    public void startServerLoginScene(boolean dbConnStarted, boolean rmiConnStarted) {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
//        Parent mainServerView = fxmlLoader.load();
//        MainServerViewController mainServerViewController = fxmlLoader.getController();
//        stage.setScene(new Scene(mainServerView));
//        stage.setTitle("Chat Application Server - Admin Panel");
//        stage.show();
//    }

    public static Stage getStage() {
        return stage;
    }

    public static void updateClients() {
        Platform.runLater(() -> {
            mainServerViewController.getDashboardViewController().loadOnlineStatisticsData();
        });
    }

    public void startMainServerScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MainServerView.fxml"));
        Parent mainServerView = fxmlLoader.load();
        mainServerViewController = fxmlLoader.getController();
        stage.setMaximized(true);
        stage.setScene(new Scene(mainServerView));
        stage.setTitle("Chat Application Server - Admin Panel");
        stage.show();
    }

}
