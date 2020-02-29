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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddGroupChatViewController implements Initializable {

    GroupChatDao groupChatDao = RMIConnection.getGroupChatDao();
    GroupChatMembershipDao groupChatMembershipDao = RMIConnection.getGroupChatMembershipDao();
    File file;
    @FXML
    private JFXTextField titleTf;
    @FXML
    private JFXTextField descriptionTf;
    @FXML
    private Label pathLbl;
    Image defultImage = new Image("images/user.png");
    @FXML
    private ImageView imageView;
    public String tileString = "";
    public String descString = "";

    @FXML
    void handleSelectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else {
            imageView.setImage(defultImage);
        }
    }

    @FXML
    void handleCreateGroupChat(ActionEvent event) {
        try {
            tileString = titleTf.getText();
            String newTitle = tileString.replaceAll("\\s+", "");
            descString = descriptionTf.getText();
            String newDesc = descString.replaceAll("\\s+", "");
            if (newTitle.equals("")) {
                pathLbl.setText("Please Enter Title");
            } else if (newDesc.equals("")) {
                pathLbl.setText("Please Enter Description");
            } else if (newDesc.equals("") && newTitle.equals("")) {
                pathLbl.setText("Please Enter Description&Title");
            } else {
                titleTf.getText().trim();
                descriptionTf.getText().trim();
                byte[] image = null;
                if (file == null) {
                    URL res = getClass().getClassLoader().getResource("images/user.png");
                    try {
                        file = Paths.get(res.toURI()).toFile();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                String filePath = file.getAbsolutePath();
                image = ImageUtiles.fromImageToBytes(filePath);
            GroupChat groupChat = new GroupChat(titleTf.getText(), descriptionTf.getText(), image, LocalDateTime.now());

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
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setImage(defultImage);
    }
}
