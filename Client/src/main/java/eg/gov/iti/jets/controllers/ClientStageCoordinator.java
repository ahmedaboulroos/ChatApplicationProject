package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.network.implementations.ClientService;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;
import eg.gov.iti.jets.models.network.interfaces.ServerInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void startMainChatAppScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ChatAppView.fxml"));
        Parent mainChatAppView = fxmlLoader.load();
        this.stage.setScene(new Scene(mainChatAppView));
        this.stage.setTitle("Chat Application");
        this.stage.show();
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

    public void openNewSingleChat(int singleChatId) {
        chatAppViewController.openSingleChat(singleChatId);
    }

    public void openNewGroupChat(int groupChatId) {
        chatAppViewController.openGroupChat(groupChatId);
    }
}
