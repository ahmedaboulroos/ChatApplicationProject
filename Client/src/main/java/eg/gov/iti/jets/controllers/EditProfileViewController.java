package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXTextArea;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class EditProfileViewController implements Initializable {

    private Label label;
    @FXML
    private Circle Photo;
    @FXML
    private Button editPhotoBT;
    @FXML
    private TextField check;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField phoneTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField countryTF;
    @FXML
    private PasswordField newPassTF;
    @FXML
    private PasswordField confirmPassTF;
    @FXML
    private JFXTextArea biograpyTArea;
    private User user = ClientStageCoordinator.getInstance().currentUser;
    private UserDao userDao = RMIConnection.getInstance().getUserDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getUserForContentProfile();
    }

    private void getUserForContentProfile() {
        nameTF.setText(user.getUsername());
        phoneTF.setText(user.getPhoneNumber());
        emailTF.setText(user.getEmail());
        countryTF.setText(user.getCountry());
        newPassTF.setText(user.getPassword());
        confirmPassTF.setText(user.getPassword());
        biograpyTArea.setText(user.getBio());
        byte[] imageBytes = user.getProfileImage();

        try {
            Image image = ImageUtiles.fromBytesToImage(imageBytes);
            Photo.setFill(new ImagePattern(image));
        } catch (Exception e) {
            System.out.println("Chat Icon not loaded.");
        }

    }

    public void handelEditOnStatus(MouseEvent mouseEvent) {

        // user.setUserStatus();


    }


    public void updateProfileBT(ActionEvent actionEvent) {
        if (nameTF.getText() != null || phoneTF.getText() != null || emailTF.getText() != null || biograpyTArea.getText() != null
                || countryTF.getText() != null || (newPassTF.getText() != null && confirmPassTF.getText() != null)) {

            try {

                System.out.println("here");
                user.setUsername(nameTF.getText());
                user.setPhoneNumber(phoneTF.getText());
                user.setEmail(emailTF.getText());
                user.setBio(biograpyTArea.getText());
                user.setPassword(newPassTF.getText());
                user.setCountry(countryTF.getText());
                System.out.println(user.toString());
                System.out.println(userDao);
                userDao.updateUser(user);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void handelCancelBT(ActionEvent actionEvent) {
        nameTF.setText(user.getUsername());
        phoneTF.setText(user.getPhoneNumber());
        emailTF.setText(user.getEmail());
        biograpyTArea.setText(user.getBio());
        newPassTF.setText(user.getPassword());
        countryTF.setText(user.getCountry());
        confirmPassTF.setText(user.getPassword());


    }

    public void handelEditPhototBT(ActionEvent actionEvent) {


        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());

        Image image = new Image(file.toURI().toString());
        System.out.println("image loaded <<");

        Photo.setFill(new ImagePattern(image));


        byte[] imageBytes = ImageUtiles.fromImageToBytes(file.toURI().toString());

        user.setProfileImage(imageBytes);
//        try {
//            System.out.println(imageBytes);
//            userDao.updateUser(user);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }


    }

}


