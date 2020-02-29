package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    ServerStageCoordinator serverStageCoordinator;

    @FXML
    private Button loginBtn;
    @FXML
    private TextField usernameTf;
    @FXML
    private PasswordField passwordPf;
    @FXML
    private Label validationLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginBtn.requestFocus();
    }

    @FXML
    void handleLoginBtn(ActionEvent event) {
        if (validCredentials()) {
            validationLbl.setText("Loading...");
            RMIConnection.getInstance().initConnection();
            serverStageCoordinator.startMainServerScene();
        } else {
            validationLbl.setText("Invalid Credentials");
        }
    }

    boolean validCredentials() {
        Properties serverProperties = ServerStageCoordinator.getServerProperties();
        return usernameTf.getText().equals(serverProperties.getProperty("serverUsername")) && passwordPf.getText().equals(serverProperties.getProperty("serverPassword"));
    }

    public void setCoordinator(ServerStageCoordinator serverStageCoordinator) {
        this.serverStageCoordinator = serverStageCoordinator;
    }
}
