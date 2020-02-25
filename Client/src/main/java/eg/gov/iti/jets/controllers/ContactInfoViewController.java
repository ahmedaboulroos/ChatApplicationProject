package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactInfoViewController implements Initializable {


        @FXML
        private Circle imageCircle;

        @FXML
        private Label UserName;

        @FXML
        private FontIcon files;

        @FXML
        private JFXButton deleteChat;

        @FXML
        private JFXButton blockBtn;


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                System.out.println("el Contact Info view loaded");
        }
}
