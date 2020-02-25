package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import eg.gov.iti.jets.models.dao.interfaces.ContactsGroupDao;
import eg.gov.iti.jets.models.dao.interfaces.RelationshipDao;
import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.*;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LeftViewController implements Initializable {
    private static LeftViewController leftViewController;

    private AddContactGroupViewController addContactGroupViewController;
    @FXML
    private Tab contactsTab;

    @FXML
    private Tab groupChatTab;

    @FXML
    private ListView<GroupChat> groupChatsLv;

    @FXML
    private ListView<SingleChat> singleChatsLv;

    @FXML
    private Tab singleChatTab;

    @FXML
    private Accordion groupsAccordion;

    @FXML
    private JFXButton userProfileBtn;

    @FXML
    private JFXComboBox<UserStatus> userStatusCb;

    private UserDao userDao = RMIConnection.getUserDao();
    private SingleChatDao singleChatDao = RMIConnection.getSingleChatDao();
    private ContactsGroupDao contactsGroupDao = RMIConnection.getContactsGroupDao();

    private ClientStageCoordinator clientStageCoordinator;

    AddSingleChatViewController addSingleChatViewController;

    public static LeftViewController getInstance() {
        return leftViewController;
    }

    private JFXListView<User> allContactsLv;

    public void setController(LeftViewController leftViewController) {
        LeftViewController.leftViewController = leftViewController;
    }

    public AddContactGroupViewController getAddContactGroupViewController() {
        return addContactGroupViewController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientStageCoordinator = ClientStageCoordinator.getInstance();
        loadContacts();
        loadGroups();
        loadSingleChats();
        loadGroupChats();
        loadUserStatus();
    }

    private void loadUserStatus() {
        userStatusCb.getItems().addAll(UserStatus.AVAILABLE, UserStatus.BUSY, UserStatus.AWAY);
    }

    private void loadContacts() {
        User user = clientStageCoordinator.currentUser;
        allContactsLv = new JFXListView<>();
        allContactsLv.setCellFactory(listViewListCellCallback -> new JFXListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (user != null) {
                    Image image = null;
                    if (user.getProfileImage() != null) {
                        image = new Image(new ByteArrayInputStream(user.getProfileImage()));
                    } else {
                        //image = new Image(getClass().getClassLoader().getResource("images/chat-circle-blue-512.png").toExternalForm());
                    }
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);

                    HBox box = new HBox();
                    box.getChildren().addAll(imageView, new Label(getUserDisplayName(user)));
                    setGraphic(box);
                } else {
                    setGraphic(null);
                }
                setText(null);
            }
        });

        try {
            List<Relationship> userRelationships = userDao.getUserRelationships(user.getId());
            TitledPane allContactsTPane = new TitledPane();
            allContactsTPane.setText("All Contacts");
            allContactsTPane.setContent(allContactsLv);
            groupsAccordion.getPanes().add(allContactsTPane);

            if (userRelationships != null) {
                //GET ALL USER FRIENDS IDS
                List<Integer> friendIds = new ArrayList<>();
                for (Relationship r : userRelationships) {
                    if (r.getStatus() == RelationshipStatus.ACCEPTED) {
                        int id = r.getFirstUserId();
                        if (id != user.getId())
                            friendIds.add(id);
                        else
                            friendIds.add(r.getSecondUserId());
                    }
                }
                for (Integer i : friendIds)
                    allContactsLv.getItems().add(userDao.getUser(i));
            } else {
                System.out.println("No Relationships");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void loadGroups() {
        User user = clientStageCoordinator.currentUser;
        int userId = user.getId();
        try {
            List<ContactsGroup> userGroups = userDao.getUserContactsGroups(userId);
            if (userGroups != null) {
                for (ContactsGroup g : userGroups) {
                    ListView<ContactsGroupMembership> contactsGroupLv =
                            createContactGroupLv(g);

                    List<ContactsGroupMembership> memberships =
                            contactsGroupDao.getContactsGroupMemberships(g.getId());
                    if (memberships != null) {
                        for (ContactsGroupMembership m : memberships)
                            contactsGroupLv.getItems().add(m);
                    }

                }
            } else {
                System.out.println("No user groups");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ListView<ContactsGroupMembership> createContactGroupLv(ContactsGroup g) {
        //Set group cell factory
        JFXListView<ContactsGroupMembership> contactsGroupLv = new JFXListView<>();
        contactsGroupLv.setCellFactory(listViewListCellCallback -> new JFXListCell<>() {
            @Override
            protected void updateItem(ContactsGroupMembership membership, boolean empty) {
                super.updateItem(membership, empty);
                if (membership != null) {
                    User user = null;
                    try {
                        user = userDao.getUser(membership.getUserId());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    if (user != null) {
                        Image image = null;
                        if (user.getProfileImage() != null) {
                            image = new Image(new ByteArrayInputStream(user.getProfileImage()));
                        } else {
                            //image = new Image(getClass().getClassLoader().getResource("images/chat-circle-blue-512.png").toString());
                        }
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);

                        HBox box = new HBox();
                        box.getChildren().addAll(imageView, new Label(getUserDisplayName(user)));
                        setGraphic(box);
                    } else {
                        setGraphic(null);
                    }
                    setText(null);
                }
            }
        });
        TitledPane groupTpane = new TitledPane();
        groupTpane.setText(g.getGroupName());
        groupTpane.setContent(contactsGroupLv);
        Platform.runLater(() -> groupsAccordion.getPanes().add(groupTpane));
        return contactsGroupLv;
    }

    private String getUserDisplayName(User user) {
        return user.getUsername() == null ? user.getPhoneNumber() : user.getUsername();
    }

    public void displayNewSingleChat(int singleChatId) {

        try {
            SingleChat singleChat = singleChatDao.getSingleChat(singleChatId);

            if (singleChat != null) {
                singleChatsLv.getItems().add(singleChat);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        loadSingleChats();
    }

    private void loadSingleChats() {
        try {
            List<SingleChat> singleChats = userDao.getUserSingleChats(ClientStageCoordinator.getInstance().currentUser.getId());
            System.out.println(singleChats);
            if (singleChats != null) {
                singleChatsLv.setItems(FXCollections.observableList(singleChats));
                singleChatsLv.setCellFactory(singleChatsLv -> new ListCell<SingleChat>() {


                    @Override
                    public void updateItem(SingleChat item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {

                            try {
                                    int idTwo = item.getUserTwoId();
                                User user = userDao.getUser(idTwo);
                                System.out.println(user.getUsername());
                                HBox hBox = new HBox();
                                hBox.setStyle("-fx-background-color: transparent  ;" +
                                        "-fx-padding: 1;" + "-fx-border-style: solid inside;"
                                        + "-fx-border-width: 3;" + "-fx-border-insets: 1;"
                                        + "-fx-border-radius: 2;" + "-fx-border-color: white;");
                                Circle imageCircle = new Circle();
                                Image imageForTasting = new Image("images/chat-circle-blue-512.png");
                                imageCircle.setFill(new ImagePattern(imageForTasting));
                                imageCircle.setRadius(20);
                                imageCircle.setStroke(Color.NAVY);
                                imageCircle.setStrokeWidth(1);
                                StackPane stackPane = new StackPane();
                                Region selectedBar = new Region();
                                selectedBar.setMinWidth(Region.USE_PREF_SIZE);
                                selectedBar.setMaxHeight(Region.USE_PREF_SIZE);
                                selectedBar.setMaxWidth(Double.MAX_VALUE);
                                StackPane.setAlignment(selectedBar, Pos.BOTTOM_CENTER);
                                stackPane.getChildren().addAll(imageCircle, selectedBar);
                                String userInfo;
                                if (user.getUsername() == null) {
                                    userInfo = user.getPhoneNumber();
                                } else {
                                    userInfo = user.getUsername();
                                }
                                Text text = new Text(userInfo);
                                text.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 20));//FontWeight.BOLD
                                text.setFill(Color.NAVY);
                                Label label = new Label();
                                label.setMinWidth(20);
                                hBox.getChildren().addAll(stackPane, label, text);
                                hBox.setAlignment(Pos.CENTER_LEFT);
                                setPrefWidth(200);
                                setPrefHeight(60);
                                hBox.setMaxWidth(200);
                                hBox.setMinWidth(200);
                                setGraphic(hBox);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


            } else {
                System.out.println("No Single chats for this user");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void loadGroupChats() {
        List<GroupChat> groupChats = null;
        try {
            groupChats = userDao.getUserGroupChats(ClientStageCoordinator.getInstance().currentUser.getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (groupChats != null) {
            System.out.println("inside leftView controller -->> preparing list view cells ....");
            groupChatsLv.setItems(FXCollections.observableList(groupChats));

            groupChatsLv.setCellFactory(groupChatsLv -> new ListCell<GroupChat>() {
                @Override
                public void updateItem(GroupChat item, boolean empty) {
                    // super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getTitle());
                        System.out.println("inside left view cell fact groupChat.getTitle() " + item.getTitle());
                        Image imageForTasting = ImageUtiles.fromBytesToImage(item.getGroupImageBytes());
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

    @FXML
    void handleSingleChatSelection(MouseEvent event) {
        SingleChat singleChat = singleChatsLv.getSelectionModel().getSelectedItem();

        if (singleChat != null) {
            System.out.println(singleChat.getId());
            ClientStageCoordinator.getInstance().openNewSingleChat(singleChat.getId());
        }

    }

    @FXML
    void handleGroupChatSelection(MouseEvent event) {
        GroupChat groupChat = groupChatsLv.getSelectionModel().getSelectedItem();
        //System.out.println("inside leftViewController groupChat.getGroupChatId() "+groupChat.getGroupChatId());
        ////groupchatmessages controller to get the messages list from the DB
        /*GroupChatMessageController groupChatMessageController = new GroupChatMessageController();
        List<GroupChatMessage> groupChatMessageList = groupChatMessageController.getAllGroupChatMessages(groupChat.getGroupChatId());
        ///printing message list*/
        List<GroupChatMessage> groupChatMessageList = null;
        try {
            groupChatMessageList = RMIConnection.getGroupChatDao().getGroupChatMessages(groupChat.getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(groupChatMessageList);

        if (groupChat != null) {
            System.out.println(groupChat.getId());
            ClientStageCoordinator.getInstance().openNewGroupChat(groupChat.getId());
        }
    }

    @FXML
    void handleAddContact(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/AddContactView.fxml"));
            Parent addContactView = fxmlLoader.load();
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
    void handleAddContactGroup(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/AddContactGroupView.fxml"));
            Parent addContactGroupView = fxmlLoader.load();
            addContactGroupViewController = fxmlLoader.getController();
            AddContactGroupViewController.setController(addContactGroupViewController);
            Stage stage = new Stage();
            Scene scene = new Scene(addContactGroupView);
            stage.setScene(scene);
            stage.setTitle("Add Contact Group");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddGroupChat(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/AddGroupChatView.fxml"));
            Parent addGroupChatView = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(addGroupChatView);
            stage.setScene(scene);
            stage.setTitle("Add Group Chat");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddSingleChat(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/AddSingleChatView.fxml"));
            Parent addSingleChatView = fxmlLoader.load();
            addSingleChatViewController = fxmlLoader.getController();
            addSingleChatViewController.setController(addSingleChatViewController);
            Stage stage = new Stage();
            Scene scene = new Scene(addSingleChatView);
            stage.setScene(scene);
            stage.setTitle("Add Single Chat");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleUserProfileBtnClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/UserProfileView.fxml"));
            Parent addGroupChatView = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(addGroupChatView));
            stage.setTitle("Edit Profile");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleChangedStatus(ActionEvent event) {
        try {
            switch (userStatusCb.getSelectionModel().getSelectedItem()) {
                case AVAILABLE:
                    RMIConnection.getUserDao().updateUserStatus(ClientStageCoordinator.getInstance().currentUser.getId(), UserStatus.AVAILABLE);
                    userStatusCb.setStyle("-fx-background-color: lightgreen");
                    break;
                case BUSY:
                    RMIConnection.getUserDao().updateUserStatus(ClientStageCoordinator.getInstance().currentUser.getId(), UserStatus.BUSY);
                    userStatusCb.setStyle("-fx-background-color: pink");
                    break;
                case AWAY:
                    RMIConnection.getUserDao().updateUserStatus(ClientStageCoordinator.getInstance().currentUser.getId(), UserStatus.AWAY);
                    userStatusCb.setStyle("-fx-background-color: lightyellow");
                    break;
                default:
                    System.out.println("WT!?");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addGroup(int groupId) {
        try {
            createContactGroupLv(contactsGroupDao.getContactsGroup(groupId));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //
    public void getRelationship(int relationshipId) throws RemoteException {
        RelationshipDao relationshipDao = RMIConnection.getInstance().getRelationshipDao();
        Relationship relationship = relationshipDao.getRelationship(relationshipId);
        System.out.println(relationship.getFirstUserId());
        if (relationship.getStatus() == RelationshipStatus.ACCEPTED) {
            int id = relationship.getFirstUserId();

            UserDao userDao = RMIConnection.getInstance().getUserDao();

            User user = userDao.getUser(id);
            //User user = clientStageCoordinator.currentUser;
            ObservableList<User> userObservableList = FXCollections.observableArrayList();
            if (id != user.getId()) {
                System.out.println("kkk" + id);

                userObservableList.add(user);
                allContactsLv.setItems(userObservableList);
            } else {

                userObservableList.add(user);
                allContactsLv.setItems(userObservableList);
                System.out.println("SecondUserId" + relationship.getSecondUserId());

            }

        } else if (relationship.getStatus() == RelationshipStatus.PENDING) {
            int id = relationship.getFirstUserId();

            UserDao userDao = RMIConnection.getInstance().getUserDao();

            User user = userDao.getUser(id);
            Stage stage = ClientStageCoordinator.getInstance().getStage();
            if (id != user.getId()) {
                Platform.runLater(() -> {
                    Notifications announcement = Notifications.create()
                            .owner(stage)
                            .title("User notification")
                            .text(user.getPhoneNumber() + " notification")
                            .position(Pos.BOTTOM_LEFT)
                            .hideAfter(Duration.seconds(30));
                    announcement.showInformation();
                });

            } else {
                ///relationship.getSecondUserId();
                Platform.runLater(() -> {
                    Notifications announcement = Notifications.create()
                            .owner(stage)
                            .title("secondUser notification")
                            .text(relationship.getSecondUserId() + " secondUsernotification")
                            .position(Pos.BOTTOM_LEFT)
                            .hideAfter(Duration.seconds(30));
                    announcement.showInformation();
                });
            }
        }
    }
}

