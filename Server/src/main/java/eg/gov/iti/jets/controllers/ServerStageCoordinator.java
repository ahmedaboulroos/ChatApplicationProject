package eg.gov.iti.jets.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class ServerStageCoordinator {
    private static Properties serverProperties;
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

    public static Properties getServerProperties() {
        return serverProperties;
    }

    public static void setServerProperties(Properties properties) {
        serverProperties = properties;
    }

    public void startServerLoginScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
            Parent loginView = fxmlLoader.load();
            LoginViewController loginViewController = fxmlLoader.getController();
            loginViewController.setCoordinator(this);
            stage.setMaximized(false);
            stage.setScene(new Scene(loginView));
            stage.setTitle("Login - Admin Panel");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void updateClients() {
        Platform.runLater(() -> {
            mainServerViewController.getDashboardViewController().loadOnlineStatisticsData();
        });
    }

    public void startMainServerScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MainServerView.fxml"));
            Parent mainServerView = fxmlLoader.load();
            mainServerViewController = fxmlLoader.getController();
            stage.setScene(new Scene(mainServerView));
            stage.setTitle("Chat Application Server - Admin Panel");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
