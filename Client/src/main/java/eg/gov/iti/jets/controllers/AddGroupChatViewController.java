package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.dao.interfaces.MembershipDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.Membership;
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
    MembershipDao membershipDao = RMIConnection.getMembershipDao();
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
            GroupChat groupChat = new GroupChat(titleTf.getText(), descriptionTf.getText(), ImageUtiles.fromImageToBytes(file.getAbsolutePath()));
            int autoGenGroupChatID = groupChatDao.createGroupChat(groupChat);
            System.out.println("inside ====>> handleCreateGroupChat  isGroupChatCreated" + autoGenGroupChatID);
            System.out.println("inside -->> AddGroupChatViewController Current user id" + ClientStageCoordinator.getInstance().currentUser.getUserId());
            Membership membership = new Membership(ClientStageCoordinator.getInstance().currentUser.getUserId(), autoGenGroupChatID);
            System.out.println(ImageUtiles.fromImageToBytes(file.getAbsolutePath()));
            int autoGenMembershipID = membershipDao.createMembership(membership);
            System.out.println("inside ===> add group chatView controller membershipDao.getUser" + membershipDao.getUser(autoGenMembershipID));

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
