package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.rmi.RemoteException;

public class RegisterViewController {

    @FXML
    private JFXTextField phoneNoTf;

    @FXML
    private JFXTextField birthDateTf;

    @FXML
    private JFXTextField usernameTf;

    @FXML
    private JFXTextField passwordTf;

    @FXML
    private JFXTextField userGenderTf;

    @FXML
    private JFXTextField emailTf;

    @FXML
    private JFXTextField countryTf;

    @FXML
    private JFXTextField bioTf;

    @FXML
    private JFXButton registerBtn;

    @FXML
    private JFXButton signInBtn;

    @FXML
    void handleBackToSignInBtn(ActionEvent event) {
        try {
            ClientStageCoordinator.getInstance().startLoginScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSignUpBtn(ActionEvent event) {
        try {
            User user = new User(phoneNoTf.getText(), passwordTf.getText());
            RMIConnection.getUserDao().createUser(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
