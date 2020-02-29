package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class EditProfileViewController implements Initializable {


    @FXML
    private Circle PhotoCIR;


    @FXML
    private JFXTextField nameJFT;
    @FXML
    private JFXTextField phoneJFT;
    @FXML
    private JFXTextField countryJFT;
    @FXML
    private JFXTextField emailJFT;
    @FXML
    private JFXPasswordField passJFXP;
    @FXML
    private JFXPasswordField confirumJFXP;
    @FXML
    private JFXTextArea biograpyTArea;
    @FXML
    private JFXButton updateJFB;


    private User user = ClientStageCoordinator.getInstance().currentUser;
    private UserDao userDao = RMIConnection.getInstance().getUserDao();
    private Image image;
    private File file;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        phoneJFT.setDisable(true);

        getUserForContentProfile();
    }

    private void getUserForContentProfile() {
        nameJFT.setText(user.getUsername());
        phoneJFT.setText(user.getPhoneNumber());
        countryJFT.setText(user.getCountry());
        emailJFT.setText(user.getEmail());

        passJFXP.setText(user.getPassword());
        confirumJFXP.setText(user.getPassword());
        biograpyTArea.setText(user.getBio());
        byte[] imageBytes = user.getProfileImage();

        try {
            Image image = ImageUtiles.fromBytesToImage(imageBytes);
            PhotoCIR.setFill(new ImagePattern(image));
        } catch (Exception e) {
            System.out.println("Chat Icon not loaded.");
        }

    }


    public void updateProfileBT(ActionEvent actionEvent) {

        try {


            user.setUsername(nameJFT.getText());
            user.setPhoneNumber(phoneJFT.getText());
            String email = emailJFT.getText();
            if (email.length() > 0 && !email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
                emailJFT.setStyle("-fx-background-color: #ffcccb");
            } else {
                emailJFT.setStyle("-fx-background-color: white");
                user.setEmail(emailJFT.getText());
            }

            user.setBio(biograpyTArea.getText());
            if (!passJFXP.getText().equals(confirumJFXP.getText())) {
                passJFXP.setStyle("-fx-background-color: #ffcccb");
                confirumJFXP.setStyle("-fx-background-color: #ffcccb");

            } else {
                user.setPassword(passJFXP.getText());

            }
            user.setCountry(countryJFT.getText());
            byte[] imageBytes = null;
            if (file != null)
                imageBytes = ImageUtiles.fromImageToBytes(file.getPath());
            else {
                URL res = getClass().getClassLoader().getResource("images/user.png");
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
            System.out.println("length" + imageBytes.length);
            user.setProfileImage(imageBytes);

            userDao.updateUser(user);


        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void getPhotoByByte(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif"));
        file = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());

        image = new Image(file.toURI().toString());


        System.out.println("image loaded <<");

        PhotoCIR.setFill(new ImagePattern(image));


    }

}


