package eg.gov.iti.jets.views;

import eg.gov.iti.jets.controllers.MainController;
import eg.gov.iti.jets.models.entities.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientStageCoordinator {

    private static ClientStageCoordinator instance;
    public User currentUser;
    private Stage stage;

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
        MainChatAppViewController mainChatAppViewController = fxmlLoader.getController();
        MainController mainController = new MainController(mainChatAppViewController);
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

}
