package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.rmi.RemoteException;

public class AddGroupChatViewController {

    GroupChatDao groupChatDao = RMIConnection.getGroupChatDao();
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
            GroupChat groupChat = new GroupChat(titleTf.getText(), descriptionTf.getText(), ImageUtiles.convertImageToBytes(file.getAbsolutePath()));
            System.out.println(ImageUtiles.convertImageToBytes(file.getAbsolutePath()));
            System.out.println("55555555555555555555555");
            System.out.println(groupChat.getCreationTimestamp());
            groupChatDao.createGroupChat(groupChat);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
