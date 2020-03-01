package eg.gov.iti.jets.controllers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatAppViewController implements Initializable {
    private static ChatAppViewController instance;
    private static ChatAppViewController chatAppViewController;

    public static ChatAppViewController getInstance() {
        return chatAppViewController;
    }

    private static double xOffset = 0;
    private static double yOffset = 0;

    @FXML
    private BorderPane chatAppBp;
    LeftViewController leftViewController;
    CenterViewController centerViewController;
    RightViewController rightViewController;

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

            chatAppBp.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            chatAppBp.setOnMouseDragged(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void close(MouseEvent Event) {
        Platform.exit();
    }

    @FXML
    public void minimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void maximize(MouseEvent mouseEvent) {

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        boolean isMaximized = stage.isMaximized();
        if (!isMaximized) {
            stage.setMaximized(true);
        } else {
            stage.setMaximized(false);
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