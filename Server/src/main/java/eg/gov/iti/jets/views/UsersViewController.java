package eg.gov.iti.jets.views;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersViewController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private TableColumn<?, ?> phoneNoCol;

    @FXML
    private TableColumn<?, ?> userNameCol;

    @FXML
    private TableColumn<?, ?> passCol;

    @FXML
    private TableColumn<?, ?> EmailCol;

    @FXML
    private JFXTextField phoneNo;

    @FXML
    private JFXTextField userName;

    @FXML
    private JFXTextField Password;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField country;

    @FXML
    private JFXTextField bio;

    @FXML
    private JFXTextField birthDate;

    @FXML
    private JFXTextField userGender;

    @FXML
    private JFXTextField profileImage;

    @FXML
    private JFXTextField userStatus;

    @FXML
    private JFXTextField currentlyLoggedIn;


}
