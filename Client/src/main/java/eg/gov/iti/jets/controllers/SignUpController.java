package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.*;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    Image userImage;
    byte[] imageBytes = null;
    File file;
    boolean valid;
    ToggleGroup tg = new ToggleGroup();

    @FXML
    private JFXTextField phoneNoTf;
    @FXML
    private JFXTextField usernameTf;
    @FXML
    private JFXTextField emailTf;
    @FXML
    private JFXTextArea bioTa;
    @FXML
    private JFXButton signUpBtn;
    @FXML
    private JFXDatePicker birthDateDp;

    @FXML
    private Circle circleImage;
    @FXML
    private JFXPasswordField passwordPf;
    @FXML
    private JFXPasswordField confirmPasswordPf;
    @FXML
    private JFXComboBox<String> countryCbox;
    @FXML
    private Label passwordErrLbl;
    @FXML
    private Label phoneErrLbl;
    @FXML
    private Label emailErrLbl;
    @FXML
    private JFXRadioButton maleRadioBtn;
    @FXML
    private JFXRadioButton femaleRadioBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maleRadioBtn.setToggleGroup(tg);
        femaleRadioBtn.setToggleGroup(tg);
        maleRadioBtn.setSelected(true);

        countryCbox.getItems().addAll(
                "Egypt",
                "Saudi Arabia",
                "Yemen",
                "Jordan",
                "Syria",
                "Other"
        );

    }

    private User initUser() {
        String phone = phoneNoTf.getText();
        String pass = passwordPf.getText();
        String userName = usernameTf.getText();
        LocalDate dateOfBirth = birthDateDp.getValue();
        String bio = bioTa.getText();
        String email = emailTf.getText();
        String country = countryCbox.getSelectionModel().getSelectedItem();
        if (imageBytes == null) {
            URL res = getClass().getClassLoader().getResource("images/user.png");
            File file = null;
            try {
                file = Paths.get(res.toURI()).toFile();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String absolutePath = file.getAbsolutePath();
            System.out.println(">> imageBytes == null");
            imageBytes = ImageUtiles.fromImageToBytes(absolutePath);
            System.out.println(imageBytes);
        }
        UserGender userGender = UserGender.MALE;
        RadioButton rd = (RadioButton) tg.getSelectedToggle();
        if (rd == femaleRadioBtn)
            userGender = UserGender.FEMALE;
        User user = new User(phone, userName, pass, email, country, bio, dateOfBirth, userGender, imageBytes, UserStatus.AVAILABLE);
        return user;
    }

    @FXML
    void handleSignUpBtn(ActionEvent event) {
        validateSignUp();
        if (valid) {
            try {
                User temp = initUser();
                System.out.println("inside sign up controller handle signup btn " + temp);
                int userId = RMIConnection.getUserDao().createUser(temp);
                temp.setId(userId);
                ClientStageCoordinator coordinator = ClientStageCoordinator.getInstance();
                coordinator.currentUser = temp;
                coordinator.loggedInTime = LocalDateTime.now();
                System.out.println("Sign up time stamp " + coordinator.loggedInTime);
                coordinator.startMainChatAppScene();

            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void closeBtnHandler(MouseEvent Event) {
        Platform.exit();
    }

    @FXML
    private void chooseImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif"));
        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            imageBytes = ImageUtiles.fromImageToBytes(file.getAbsolutePath());
            System.out.println("iside SignUPController CircleHandler  imageBytes len " + imageBytes.length);
            try {
                userImage = new Image(file.toURI().toURL().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            System.out.println("image loaded <<");
        } else {
            userImage = new Image("/images/defaultUserImage.jpg");
        }
        circleImage.setFill(new ImagePattern(userImage));
    }

    private void validateSignUp() {
        valid = true;
        validatePhoneNumber();
        validatePassword();
        validateEmail();
    }

    private void validateEmail() {
        String email = emailTf.getText();
        if (email.length() > 0 && !email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            emailTf.setStyle("-fx-background-color: #ffcccb");
            emailErrLbl.setText("Invalid Email Address");
            valid = false;
        } else {
            emailTf.setStyle("-fx-background-color: white");
            emailErrLbl.setText("");
        }
    }

    private void validatePassword() {
        if (passwordPf.getText().length() == 0) {
            passwordPf.setStyle("-fx-background-color: #ffcccb");
            confirmPasswordPf.setStyle("-fx-background-color: #ffcccb");
            passwordErrLbl.setText("Must Enter Password");
            valid = false;
        } else if (!passwordPf.getText().equals(confirmPasswordPf.getText())) {
            passwordPf.setStyle("-fx-background-color: #ffcccb");
            confirmPasswordPf.setStyle("-fx-background-color: #ffcccb");
            passwordErrLbl.setText("Password and Confirm Password do not match");
            valid = false;
        } else {
            passwordPf.setStyle("-fx-background-color: white");
            passwordErrLbl.setText("");
        }
    }

    private void validatePhoneNumber() {
        if (!phoneNoTf.getText().matches("01(0|1|2|5)\\d{8}")) {
            phoneNoTf.setStyle("-fx-background-color: #ffcccb");
            phoneErrLbl.setText("Invalid Phone Number");
            valid = false;
        } else {
            phoneNoTf.setStyle("-fx-background-color: white");
            phoneErrLbl.setText("");
        }
    }
}
