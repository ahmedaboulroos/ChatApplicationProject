package eg.gov.iti.jets.views;

import eg.gov.iti.jets.models.entities.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageCoordinator {

    private static StageCoordinator instance;

    private StageCoordinator() {
    }

    public static StageCoordinator getInstance() {
        if (instance == null) {
            instance = new StageCoordinator();
        }
        return instance;
    }

    public User currentUser;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void startMainChatAppScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MainChatAppView.fxml"));
        Parent mainChatAppView = fxmlLoader.load();
        MainChatAppViewController mainChatAppViewController = fxmlLoader.getController();
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

    public void startChatScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ChatView.fxml"));
        Parent chatView = fxmlLoader.load();
        this.stage.setScene(new Scene(chatView));
        this.stage.setTitle("Chat");
        this.stage.show();
    }

    public void startRegistrationScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/RegisterView.fxml"));
        Parent registrationView = fxmlLoader.load();
        this.stage.setScene(new Scene(registrationView));
        this.stage.setTitle("Registration");
        this.stage.show();
    }

//    public void startLeftScene() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LeftView.fxml"));
//        Parent leftView = fxmlLoader.load();
//        LeftViewController leftViewController = fxmlLoader.getController();
//        this.stage.setScene(new Scene(leftView));
//        this.stage.setTitle("Left View");
//        this.stage.show();
//    }

    public void startRightScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/RightView.fxml"));
        Parent rightView = fxmlLoader.load();
        this.stage.setScene(new Scene(rightView));
        this.stage.setTitle("Left View");
        this.stage.show();
    }

}
