package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.*;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private JFXTextField phoneNoTf;

    Image userImage;

    @FXML
    private JFXTextField usernameTf;

    byte[] imageBytes;
    File file;

    @FXML
    private JFXTextField emailTf;
    boolean valid;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        User user = new User(phone, userName, pass, email, country, bio, dateOfBirth, UserGender.MALE, imageBytes, UserStatus.AVAILABLE);
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
        //validateEmail();
    }

    /*private void validateEmail() {
        String email = emailTf.getText();
        if(email.length()>0 && !email.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")){
            emailTf.setStyle("-fx-background-color: #ffcccb");
            emailErrLbl.setText("Invalid Email Address");
            valid = false;
        }
        else{
            emailTf.setStyle("-fx-background-color: white");
            emailErrLbl.setText("");
        }
    }*/

    private void validatePassword() {
        if (passwordPf.getText().length() == 0) {
            passwordPf.setStyle("-fx-background-color: #ffcccb");
            confirmPasswordPf.setStyle("-fx-background-color: #ffcccb");
            passwordErrLbl.setText("Must Enter Password");
            valid = false;
            System.out.println("TEXT FEL IF BT3T el pass");
        } else if (!passwordPf.getText().equals(confirmPasswordPf.getText())) {
            passwordPf.setStyle("-fx-background-color: #ffcccb");
            confirmPasswordPf.setStyle("-fx-background-color: #ffcccb");
            passwordErrLbl.setText("Password and Confirm Password do not match");
            valid = false;
            System.out.println("TEXT FEL IF BT3T el pass");
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
            System.out.println("TEXT FEL ELSE");
            phoneNoTf.setStyle("-fx-background-color: white");
            phoneErrLbl.setText("");
        }
    }
}
