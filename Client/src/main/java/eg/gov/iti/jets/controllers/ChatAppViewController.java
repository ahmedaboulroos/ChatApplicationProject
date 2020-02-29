package eg.gov.iti.jets.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatAppViewController implements Initializable {
    private static ChatAppViewController instance;
    private static ChatAppViewController chatAppViewController;

    public static ChatAppViewController getInstance() {
        return chatAppViewController;
    }


    @FXML
    private BorderPane chatAppBp;
    LeftViewController leftViewController;
    CenterViewController centerViewController;
    RightViewController rightViewController;
    //=CenterViewController.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader leftViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/LeftView.fxml"));
            Parent leftView = leftViewFxmlLoader.load();
            leftViewController = leftViewFxmlLoader.getController();
            leftViewController.setController(leftViewController);
            chatAppBp.setLeft(leftView);

            FXMLLoader centerViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/CenterView.fxml"));
            Parent centerView = centerViewFxmlLoader.load();
            centerViewController = centerViewFxmlLoader.getController();
            centerViewController.setController(centerViewController);
            chatAppBp.setCenter(centerView);

            FXMLLoader rightViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/RightView.fxml"));
            Parent rightView = rightViewFxmlLoader.load();
            rightViewController = rightViewFxmlLoader.getController();
            rightViewController.setController(rightViewController);
            chatAppBp.setRight(rightView);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openSingleChat(int singleChatId) {
        centerViewController.addSingleChat(singleChatId);
    }

    public void openGroupChat(int groupChatId) {
        centerViewController.displayGroupChat(groupChatId);
    }

    public void setController(ChatAppViewController chatAppViewController) {
        ChatAppViewController.chatAppViewController = chatAppViewController;
    }
}