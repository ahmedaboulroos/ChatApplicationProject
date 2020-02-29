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
    byte[] imageBytes;
    File file;
    @FXML
    private JFXDatePicker birthDateDp;
    @FXML
    private Circle circleImage;

    private User initUser() {
        String phone = phoneNoTf.getText();
        String pass = passwordTf.getText();
        String userName = usernameTf.getText();
        LocalDate dateOfBirth = birthDateDp.getValue();
        String bio = bioTa.getText();
        String email = emailTf.getText();
        String country = countryTf.getText();
        User user = new User(phone, userName, pass, email, country, bio, dateOfBirth, UserGender.MALE, imageBytes, UserStatus.AVAILABLE);
        return user;
    }

    @FXML
    void handleSignUpBtn(ActionEvent event) {
        boolean valid = validateSignUp();
        if (valid) {
            try {
                User temp = initUser();
                System.out.println("inside sign up controller handle signup btn " + temp);
                int userId = RMIConnection.getUserDao().createUser(temp);
                temp.setId(userId);
                ClientStageCoordinator coordinator = ClientStageCoordinator.getInstance();
                coordinator.currentUser = temp;
                coordinator.loggedInTime = LocalDateTime.now();
                System.out.println("Singn up time stamp " + coordinator.loggedInTime);
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

    @FXML
    public void closeBtnHandler(MouseEvent Event) {
        Platform.exit();
    }


}
