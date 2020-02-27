package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.dao.interfaces.GroupChatMembershipDao;
import eg.gov.iti.jets.models.dao.interfaces.GroupChatMessageDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMembership;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class GroupInfoViewController implements Initializable {
    private static GroupInfoViewController groupInfoViewController;
    AddMembershipGroupController addMembershipGroupController;
    List<GroupChatMembership> groupChatMemberships = null;
    GroupChat groupChat;
    @FXML
    private Label gname;
    @FXML
    private Label desc;
    @FXML
    private Label createdate;
    @FXML
    private Label whocreted;
    private GroupChatDao groupChatDao = RMIConnection.getGroupChatDao();
    private UserDao userDao = RMIConnection.getUserDao();
    private int groupChatId;
    private ClientStageCoordinator clientStageCoordinator;
    private GroupChatMessageDao GroupChatMessageDao = RMIConnection.getGroupChatMessageDao();
    private GroupChatMembershipDao groupChatMembershipDao = RMIConnection.getGroupChatMembershipDao();
    @FXML
    private ListView<GroupChatMembership> membershipListView;

    public static GroupInfoViewController getInstance() {
        return groupInfoViewController;
    }

    public void setGroupChatId(int groupChatId) {
        this.groupChatId = groupChatId;
    }

    public void setController(GroupInfoViewController groupInfoViewController) {
        this.groupInfoViewController = groupInfoViewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientStageCoordinator = ClientStageCoordinator.getInstance();

    }

    @FXML
    void handleAddContactGroup(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/AddContactViewGroup.fxml"));
            Parent addContactView = fxmlLoader.load();
            addMembershipGroupController = fxmlLoader.getController();
            addMembershipGroupController.setController(addMembershipGroupController);
            addMembershipGroupController.setGroupChatId(groupChatId);
            Stage stage = new Stage();
            Scene scene = new Scene(addContactView);
            stage.setScene(scene);
            stage.setTitle("Add Contact");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteContactGroup(ActionEvent event) {

         /*   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/DeleteContactViewGroup.fxml"));
            Parent addContactView = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(addContactView);
            stage.setScene(scene);
            stage.setTitle("Delete Contact");
            stage.setMaxHeight(302);
            stage.setMaxWidth(556);
            stage.show();*/
        if (membershipListView.getSelectionModel().getSelectedItem() != null) {
            membershipListView.getSelectionModel().getSelectedItem();
            try {
                RMIConnection.getGroupChatMembershipDao().deleteGroupChatMembership(membershipListView.getSelectionModel().getSelectedItem().getId());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setGroupInfo(int groupChatId) {
        try {
            System.out.println(groupChatId);
            groupChat = groupChatDao.getGroupChat(groupChatId);
            if (groupChat.getTitle() != null) {
                gname.setText(groupChat.getTitle());
            }
            if (groupChat.getDescription() != null) {
                desc.setText(groupChat.getDescription());
            }
            if (groupChat.getCreationDateTime() != null) {
                createdate.setText(groupChat.getCreationDateTime().toString());
            }
            if (groupChat.getId() != 0) {
                whocreted.setText(String.valueOf(groupChat.getId()));
                membershipList();
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void membershipList() {
        try {


            // System.out.println(groupChat.getId()+"groupchat"+"group is here"+groupChatId);
            groupChatMemberships = groupChatDao.getGroupChatMemberships(groupChatId);
            System.out.println(groupChatMemberships + "nour membership");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (groupChatMemberships != null) {

            membershipListView.setItems(FXCollections.observableList(groupChatMemberships));
            membershipListView.setCellFactory(groupChatsLv -> new ListCell<GroupChatMembership>() {
                @Override
                public void updateItem(GroupChatMembership item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        System.out.println(item.getUserId());
                        try {
                            User user = userDao.getUser(item.getUserId());
                            //   System.out.println("name is "+"    "+item.getUserId()+"   "+user.getUsername());
                            setText(user.getUsername());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        //   System.out.println("inside Right view cell ");
                        //  Image imageForTasting = ImageUtiles.fromBytesToImage(item.getGroupImageBytes());
                        Image imageForTasting = new Image("images/chat-circle-blue-512.png");
                        if (imageForTasting != null) {
                            Circle imageCircle = new Circle();
                            imageCircle.setFill(new ImagePattern(imageForTasting));
                            imageCircle.setRadius(20);
                            imageCircle.setStroke(Color.GREEN);
                            imageCircle.setStrokeWidth(3);
                            setGraphic(imageCircle);
                        }
                    }
                }
            });
        } else {
            System.out.println("No Group chats for this user");
        }
    }


}