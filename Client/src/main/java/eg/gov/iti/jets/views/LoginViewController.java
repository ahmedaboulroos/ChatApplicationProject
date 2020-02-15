package eg.gov.iti.jets.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML
    private JFXTextField phoneNumberTf;

    @FXML
    private JFXPasswordField passwordPf;

    @FXML
    private JFXCheckBox rememberMeCb;

    @FXML
    private JFXButton signInBtn;

    @FXML
    private JFXButton signUpBtn;

    @FXML
    private Label errorLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleSignInBtn(ActionEvent event) {
        try {
            UserDao userDao = RMIConnection.getInstance().getUserDao();
            User user = userDao.getUser(phoneNumberTf.getText(), passwordPf.getText());
            if (user != null) {
                StageCoordinator.getInstance().currentUser = user;
                StageCoordinator.getInstance().startMainChatAppScene();
            } else {
                errorLbl.setText("Invalid PhoneNumber or Password");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSignUpBtn(ActionEvent event) {
        try {
            StageCoordinator.getInstance().startRegistrationScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

