package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.dao.interfaces.GroupChatMembershipDao;
import eg.gov.iti.jets.models.dao.interfaces.GroupChatMessageDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMembership;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class GroupInfoViewController implements Initializable {
    private static GroupInfoViewController groupInfoViewController;
    AddMembershipGroupController addMembershipGroupController;
    List<GroupChatMembership> groupChatMemberships = null;

    GroupChat groupChat;
    protected ListProperty<GroupChatMembership> listProperty = new SimpleListProperty<>();
    @FXML
    private Text gname;
    @FXML
    private Text desc;
    @FXML
    private Text createdate;
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
        GroupInfoViewController.groupInfoViewController = groupInfoViewController;
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
            stage.setMaxWidth(557);
            stage.setMaxHeight(346);
            //  addMembershipGroupController.setGroupInfoViewController(this);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteContactGroup(ActionEvent event) {
        if (membershipListView.getSelectionModel().getSelectedItem() != null) {
            membershipListView.getSelectionModel().getSelectedItem();
            try {
                RMIConnection.getGroupChatMembershipDao().deleteGroupChatMembership(membershipListView.getSelectionModel().getSelectedItem().getId());
                CenterViewController.getInstance().centerViewBp.setCenter(null);
                RightViewController.getInstance().rightViewBp.setCenter(null);
                //ClientStageCoordinator.getInstance().openNewGroupChat(groupChatId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setGroupInfo(int groupChatId) {
        try {
            // System.out.println(groupChatId);
            groupChat = groupChatDao.getGroupChat(groupChatId);
            if (groupChat.getTitle() != null) {
                gname.setText(groupChat.getTitle());
            }
            if (groupChat.getDescription() != null) {
                desc.setText(groupChat.getDescription());
            }
            if (groupChat.getCreationDateTime() != null) {
                createdate.setText(groupChat.getCreationDateTime().toString());
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
            // System.out.println(groupChatMemberships + "nour membership");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (groupChatMemberships != null) {
            membershipListView.setItems(FXCollections.observableList(groupChatMemberships));

            // listProperty.set(FXCollections.observableArrayList(groupChatMemberships));
            //   membershipListView.itemsProperty().bindBidirectional(listProperty);
            //     membershipListView.setItems(listProperty);
            membershipListView.setCellFactory(groupChatsLv -> new ListCell<GroupChatMembership>() {
                @Override
                public void updateItem(GroupChatMembership item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        //    System.out.println(item.getUserId());
                        try {
                            User user = userDao.getUser(item.getUserId());
                            //   System.out.println("name is "+"    "+item.getUserId()+"   "+user.getUsername());
                            setText(user.getUsername());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        //   System.out.println("inside Right view cell ");

                        Circle imageCircle = new Circle();
                        try {
                            User user = userDao.getUser(item.getUserId());
                            Image imageForTasting = null;
                            byte[] image = user.getProfileImage();

                            if (image == null) {
                                File file = null;
                                URL res = getClass().getClassLoader().getResource("images/user.png");
                                try {
                                    file = Paths.get(res.toURI()).toFile();
                                    String filePath = file.getAbsolutePath();
                                    image = ImageUtiles.fromImageToBytes(filePath);
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                            }
                            imageForTasting = ImageUtiles.fromBytesToImage(image);
                            imageCircle.setFill(new ImagePattern(imageForTasting));
                            imageCircle.setRadius(20);
                            if (user.getUserStatus() == UserStatus.AVAILABLE) {
                                imageCircle.setStroke(Color.GREEN);

                            }
                            if (user.getUserStatus() == UserStatus.AWAY) {
                                imageCircle.setStroke(Color.RED);

                            }
                            if (user.getUserStatus() == UserStatus.BUSY) {
                                imageCircle.setStroke(Color.ORANGE);

                            }
                            imageCircle.setStrokeWidth(3);
                            setGraphic(imageCircle);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Membership Icon not loaded.");
                        }


                    }
                }
            });
        } else {
            System.out.println("No Group chats for this user");
        }
    }
}



