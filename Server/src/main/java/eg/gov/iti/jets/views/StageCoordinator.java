package eg.gov.iti.jets.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StageCoordinator {

    private Stage stage;
    boolean dbConnectionStarted;
    boolean rmiConnectionStarted;

    public StageCoordinator(Stage stage, boolean dbConnectionStarted, boolean rmiConnectionStarted) {
        this.stage = stage;
        this.dbConnectionStarted = dbConnectionStarted;
        this.rmiConnectionStarted = rmiConnectionStarted;
    }

    public void startMainServerScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MainServerView.fxml"));
        Parent mainServerView = fxmlLoader.load();
        MainServerViewController mainServerViewController = fxmlLoader.getController();
        stage.setScene(new Scene(mainServerView));
        stage.setTitle("Server Application");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public void startServerLoginScene() {

    }

}
