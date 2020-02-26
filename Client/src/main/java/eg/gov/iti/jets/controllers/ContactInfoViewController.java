package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ContactInfoViewController implements Initializable {
    private static ContactInfoViewController contactInfoViewController;
    private UserDao userDao = RMIConnection.getUserDao();
    private SingleChatDao singleChatDao = RMIConnection.getSingleChatDao();
    @FXML
    private Circle imageCircle;

    @FXML
    private Label UserName;

    @FXML
    private FontIcon files;

    @FXML
    private JFXButton deleteChat;

    @FXML
    private JFXButton blockBtn;
    private int singleChatId;

    public static ContactInfoViewController getInstance() {
        return contactInfoViewController;
    }

    public void setSingleChatId(int singleChatId) {
        this.singleChatId = singleChatId;
    }

    public void setController(ContactInfoViewController contactInfoViewController) {
        this.contactInfoViewController = contactInfoViewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // System.out.println("el Contact Info view loaded");

    }

    @FXML
    void handleOnBlockBtn(ActionEvent event) {

    }

    @FXML
    void handleOnDeleteChat(ActionEvent event) {

    }

    public void setContactInfo(int singleChatId) {
        try {
            System.out.println(singleChatId);
            SingleChat singleChat = singleChatDao.getSingleChat(singleChatId);
            User userTwo = userDao.getUser(singleChat.getUserTwoId());
            System.out.println(singleChat.getUserTwoId());
            if (userTwo.getUsername() != null) {
                UserName.setText(userTwo.getUsername());
            } else {
                UserName.setText(userTwo.getPhoneNumber());
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
