package eg.gov.iti.jets.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageCoordinator {

    private Stage stage;

    public StageCoordinator(Stage stage) {
        this.stage = stage;
    }

    public void startMainChatAppScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MainChatAppView.fxml"));
        Parent mainChatAppView = fxmlLoader.load();
        MainChatAppViewController mainChatAppViewController = fxmlLoader.getController();
        mainChatAppViewController.setStageCoordinator(this);
        this.stage.setScene(new Scene(mainChatAppView));
        this.stage.setTitle("Chat Application");
        this.stage.show();
    }

    public void startLoginScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
        Parent loginView = fxmlLoader.load();
        LoginViewController loginViewController = fxmlLoader.getController();
        loginViewController.setStageCoordinator(this);
        this.stage.setScene(new Scene(loginView));
        this.stage.setTitle("Login");
        this.stage.show();
    }

    public void startRegistrationScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/RegisterView.fxml"));
        Parent registrationView = fxmlLoader.load();
        RegisterViewController registerViewController = fxmlLoader.getController();
        registerViewController.setStageCoordinator(this);
        this.stage.setScene(new Scene(registrationView));
        this.stage.setTitle("Registration");
        this.stage.show();
    }

}
