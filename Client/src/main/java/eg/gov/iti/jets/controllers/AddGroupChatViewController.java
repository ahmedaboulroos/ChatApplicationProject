package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.dao.interfaces.GroupChatMembershipDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMembership;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class AddGroupChatViewController {

    GroupChatDao groupChatDao = RMIConnection.getGroupChatDao();
    GroupChatMembershipDao groupChatMembershipDao = RMIConnection.getGroupChatMembershipDao();
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
            GroupChat groupChat = new GroupChat(titleTf.getText(), descriptionTf.getText(), ImageUtiles.fromImageToBytes(file.getAbsolutePath()), LocalDateTime.now());
            int autoGenGroupChatID = groupChatDao.createGroupChat(groupChat);
            System.out.println("inside ====>> handleCreateGroupChat  isGroupChatCreated" + autoGenGroupChatID);
            System.out.println("inside -->> AddGroupChatViewController Current user id" + ClientStageCoordinator.getInstance().currentUser.getId());
            GroupChatMembership membership = new GroupChatMembership(ClientStageCoordinator.getInstance().currentUser.getId(), autoGenGroupChatID);
            System.out.println("========================");
            System.out.println(membership);
            int autoGenMembershipID = groupChatMembershipDao.createGroupChatMembership(membership);
            System.out.println("========================" + autoGenMembershipID);
            System.out.println("inside ===> add group chatView controller membershipDao.getUser" + groupChatMembershipDao.getUser(autoGenMembershipID));

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
