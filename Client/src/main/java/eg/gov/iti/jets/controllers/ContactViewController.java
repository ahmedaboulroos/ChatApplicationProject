package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class ContactViewController {
    @FXML
    private Circle contactImageCircle;

    @FXML
    private Label contactNameLbl;
    @FXML
    private FontIcon buttonIcon;
    @FXML
    private Tooltip buttonTip;
    @FXML
    private JFXButton button;

    public FontIcon getButtonIcon() {
        return buttonIcon;
    }

    public Tooltip getButtonTip() {
        return buttonTip;
    }

    public JFXButton getButton() {
        return button;
    }

    public void displayUser(User user) {
        if (user != null) {
            Image image = null;
            byte[] imageBytes = user.getProfileImage();
            try {
                if (imageBytes == null) {
                    File file = null;
                    URL res = getClass().getClassLoader().getResource("images/user.png");
                    try {
                        file = Paths.get(res.toURI()).toFile();
                        String filePath = file.getAbsolutePath();
                        imageBytes = ImageUtiles.fromImageToBytes(filePath);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                image = ImageUtiles.fromBytesToImage(imageBytes);
                contactImageCircle.setFill(new ImagePattern(image));
            } catch (Exception e) {
                System.out.println("cannot load");
            }
            contactNameLbl.setText(getUserDisplayName(user));
        }
    }

    private String getUserDisplayName(User user) {
        return user.getUsername() == null ? user.getPhoneNumber() : user.getUsername();
    }
}
