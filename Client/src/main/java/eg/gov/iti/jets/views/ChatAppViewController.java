package eg.gov.iti.jets.views;

import eg.gov.iti.jets.controllers.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatAppViewController implements Initializable {

    @FXML
    private BorderPane chatAppBp;
    private MainController mainController;

    CenterViewController centerViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader leftViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/LeftView.fxml"));
            Parent leftView = leftViewFxmlLoader.load();
            chatAppBp.setLeft(leftView);

            FXMLLoader centerViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/CenterView.fxml"));
            Parent centerView = centerViewFxmlLoader.load();
            centerViewController = centerViewFxmlLoader.getController();
            chatAppBp.setCenter(centerView);

            FXMLLoader rightViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/RightView.fxml"));
            Parent rightView = rightViewFxmlLoader.load();
            chatAppBp.setRight(rightView);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setController(MainController mainController) {
        this.mainController = mainController;
    }

    public void displayMsg(String hi) {
        System.out.println(hi);
        mainController.sendMsg("HI HI");
    }

    public void openSingleChat(int singleChatId) {
        centerViewController.addSingleChat(singleChatId);
    }

    public void openGroupChat(int groupChatId) {
        centerViewController.addGroupChat(groupChatId);
    }
}
