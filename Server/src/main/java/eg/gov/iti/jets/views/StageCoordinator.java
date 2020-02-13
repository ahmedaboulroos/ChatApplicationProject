package eg.gov.iti.jets.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageCoordinator {

    private Stage stage;
    boolean dbConnectionStarted;
    boolean rmiConnectionStarted;

    public StageCoordinator(Stage stage) {
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

    public void startServerLoginScene(boolean dbConnStarted, boolean rmiConnStarted) {

    }

}
