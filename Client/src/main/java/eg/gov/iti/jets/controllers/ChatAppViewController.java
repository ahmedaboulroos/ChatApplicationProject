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

    @FXML
    private BorderPane chatAppBp;
    LeftViewController leftViewController;
    CenterViewController centerViewController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader leftViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/LeftView.fxml"));
            Parent leftView = leftViewFxmlLoader.load();
            leftViewController = leftViewFxmlLoader.getController();
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

    public void openSingleChat(int singleChatId) {
        centerViewController.addSingleChat(singleChatId);
    }

    public void openGroupChat(int groupChatId) {
        centerViewController.addGroupChat(groupChatId);
    }

    //TODO:REMOVE DTO FROM IMPLEMENTATION
    /*public void displayNewGroup(GroupDto groupDto) {
        leftViewController.addNewGroup(groupDto);
    }

    public void displayRelationship(UserDto user) {
        leftViewController.addContact(user);
    }

    public void displayMsg(String hi_hi) {
        System.out.println(hi_hi);
    }

    public void loggedIn(UserDto user) {
        leftViewController.addLoggedIn(user);
    }

    public void loggedOut(UserDto user) {
        leftViewController.removeLoggedOut(user);
    }
*/
}
