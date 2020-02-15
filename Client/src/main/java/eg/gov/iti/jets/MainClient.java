package eg.gov.iti.jets;

import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        System.out.println("Client init...");
        RMIConnection.getInstance().initConnection();
        RMIConnection.getInstance().startConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Client start...");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/RegisterView.fxml"));
        Parent mainServerView = fxmlLoader.load();
        stage.setScene(new Scene(mainServerView));
        stage.setTitle("Chat Application Server - Admin Panel");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Client stop...");
        RMIConnection.getInstance().stopConnection();
    }

}
