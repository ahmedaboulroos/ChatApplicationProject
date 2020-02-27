package eg.gov.iti.jets.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    private static LoginViewController instance;
    @FXML
    private VBox vbox;
    private Parent fxml;
    @FXML
    private Button signInBtn;
    @FXML
    private Button signUpBtn;

    public static LoginViewController getInstance() {
        return instance;
    }

    public void setController(LoginViewController loginViewController) {
        instance = loginViewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition translate = new TranslateTransition(Duration.seconds(1), vbox);
        System.out.println(vbox);
        translate.setToX(0);
        translate.play();
        translate.setOnFinished((e) -> {
            try {
                fxml = FXMLLoader.load(getClass().getResource("/views/Intro.fxml"));

                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    private void openSignIn(ActionEvent event) {
        TranslateTransition translate = new TranslateTransition(Duration.seconds(1), vbox);
        translate.setToX(vbox.getLayoutX() * 1);
        translate.play();
        translate.setOnFinished((e) -> {
            try {
                fxml = FXMLLoader.load(getClass().getResource("/views/SignIn.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    @FXML
    private void openSignUp(ActionEvent event) {
        TranslateTransition translate = new TranslateTransition(Duration.seconds(1), vbox);
        translate.setToX(vbox.getLayoutX() * -1);
        translate.play();
        translate.setOnFinished((e) -> {
            try {
                fxml = FXMLLoader.load(getClass().getResource("/views/SignUp.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    public void disable() {
        signInBtn.setDisable(true);
        signUpBtn.setDisable(true);
    }

    public void enable() {
        signInBtn.setDisable(false);
        signUpBtn.setDisable(false);
    }
}
