package eg.gov.iti.jets;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class MainClient extends Application {

    public static void main(String[] args) {
        System.out.println("Client main...");
        launch(args);
    }

    @Override
    public void init() throws Exception {
        System.out.println("Client init...");
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Client start...");
//


        stage.setTitle("Client Application");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Client stop...");

    }

}
