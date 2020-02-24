package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.entities.User;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class ClientStageCoordinator {

    private static ClientStageCoordinator instance;
    public User currentUser;
    private Stage stage;
    private ChatAppViewController chatAppViewController;

    private ClientStageCoordinator() {
    }

    public static ClientStageCoordinator getInstance() {
        if (instance == null) {
            instance = new ClientStageCoordinator();
        }
        return instance;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void startMainChatAppScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ChatAppView.fxml"));
        Parent mainChatAppView = fxmlLoader.load();
        this.stage.setScene(new Scene(mainChatAppView));
        this.stage.setTitle("Chat Application");
        this.stage.show();
        this.stage.setMaximized(true);
    }

    public void startLoginScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
        Parent loginView = fxmlLoader.load();
        this.stage.setScene(new Scene(loginView));
        this.stage.setTitle("Login");
        this.stage.show();
    }

    public void startRegistrationScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/RegisterView.fxml"));
        Parent registrationView = fxmlLoader.load();
        this.stage.setScene(new Scene(registrationView));
        this.stage.setTitle("Registration");
        this.stage.show();
    }

    public void displayUserLoginNotification(int userId) {
        System.out.println(">> User Logged In :" + userId);
        Platform.runLater(() -> {
            Notifications announcement = Notifications.create()
                    .owner(this.stage)
                    .title("User Logged In")
                    .text("User Logged In : " + userId)
                    .position(Pos.BOTTOM_RIGHT)
                    .hideAfter(Duration.seconds(30));
            announcement.showInformation();
        });
    }

    public void displayUserLogoutNotification(int userId) {
        System.out.println(">> User Logged Out :" + userId);
        Platform.runLater(() -> {
            Notifications announcement = Notifications.create()
                    .owner(this.stage)
                    .title("User Logged Out")
                    .text(userId + " Logged Out")
                    .position(Pos.BOTTOM_RIGHT)
                    .hideAfter(Duration.seconds(30));
            announcement.showInformation();
        });
    }

    public void displayServerAnnouncement(int announcementId) {
        System.out.println(">> Server Announcement :" + announcementId);
        Platform.runLater(() -> {
            Notifications announcement = Notifications.create()
                    .owner(this.stage)
                    .title("Announcement")
                    .text(announcementId + " server annon")
                    .position(Pos.BOTTOM_RIGHT)
                    .hideAfter(Duration.seconds(30))
                    .onAction(event -> {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/AnnouncementView.fxml"));
                            Parent announcementView = fxmlLoader.load();
                            AnnouncementViewController announcementViewController = fxmlLoader.getController();
                            announcementViewController.setAnnouncementId(announcementId);
                            Stage stage = new Stage();
                            stage.setScene(new Scene(announcementView));
                            stage.setTitle("Server Announcement");
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            announcement.showWarning();
        });
    }

    public void openNewSingleChat(int singleChatId) {
        chatAppViewController.openSingleChat(singleChatId);
    }

    public void openNewGroupChat(int groupChatId) {
        chatAppViewController.openGroupChat(groupChatId);
    }
}
