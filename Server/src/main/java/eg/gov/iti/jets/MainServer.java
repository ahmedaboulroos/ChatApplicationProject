package eg.gov.iti.jets;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainServer extends Application {

    public static void main(String[] args) {
        System.out.println("Server main...");
        launch(args);
    }

    @Override
    public void init() throws Exception {
        System.out.println("Server init...");
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Server start...");


        stage.setTitle("Server Application");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Server stop...");

    }

}
