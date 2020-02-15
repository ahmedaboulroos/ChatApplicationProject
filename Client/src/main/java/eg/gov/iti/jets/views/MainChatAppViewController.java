package eg.gov.iti.jets.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainChatAppViewController implements Initializable {

    @FXML
    private BorderPane mainChatBp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LeftView.fxml"));
            Parent leftView = fxmlLoader.load();
            mainChatBp.setLeft(leftView);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
