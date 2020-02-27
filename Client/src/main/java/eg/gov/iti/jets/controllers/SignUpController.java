package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private JFXTextField phoneNoTf;

    @FXML
    private JFXTextField birthDateTf;

    @FXML
    private JFXTextField usernameTf;

    @FXML
    private JFXPasswordField passwordTf;

    @FXML
    private JFXTextField emailTf;

    @FXML
    private JFXTextField countryTf;

    @FXML
    private JFXTextArea bioTa;

    @FXML
    private JFXButton signUpBtn;

    @FXML
    private JFXButton signInBtn;

    @FXML
    void handleSignUpBtn(ActionEvent event) {
        boolean valid = validateSignUp();
        if (valid) {
            try {
                User user = new User(phoneNoTf.getText(), passwordTf.getText());
                RMIConnection.getUserDao().createUser(user);
                ClientStageCoordinator coordinator = ClientStageCoordinator.getInstance();
                coordinator.currentUser = user;
                coordinator.startMainChatAppScene();

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateSignUp() {
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
