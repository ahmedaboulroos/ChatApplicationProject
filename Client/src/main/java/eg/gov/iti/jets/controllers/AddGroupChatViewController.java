package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class AddGroupChatViewController {

    GroupChatDao groupChatDao = RMIConnection.getInstance().getGroupChatDao();
    File file;
    @FXML
    private JFXTextField titleTf;
    @FXML
    private JFXTextField descriptionTf;
    @FXML
    private Label pathLbl;

    @FXML
    void handleSelectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null);
    }

    @FXML
    void handleCreateGroupChat(ActionEvent event) {
        try {
            GroupChat groupChat = new GroupChat(titleTf.getText(), descriptionTf.getText(), new Image(file.toURI().toURL().toString()));
            groupChatDao.createGroupChat(groupChat);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
