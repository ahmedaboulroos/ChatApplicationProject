package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.*;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

public class LeftViewController implements Initializable {

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

    private UserDao userDao = RMIConnection.getUserDao();

    private Map<Integer, ObservableList<Node>> accordionLists = new HashMap<>();

    private ClientStageCoordinator clientStageCoordinator;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientStageCoordinator = ClientStageCoordinator.getInstance();
        loadContacts();
        loadGroups();
        loadSingleChats();
        loadGroupChats();
    }

    private void loadGroups() {
        User user = clientStageCoordinator.currentUser;
        int userId = user.getId();
        try {
            List<ContactsGroup> userGroups = userDao.getUserContactsGroups(userId);
            if (userGroups != null) {
                TitledPane allContactsTPane = new TitledPane();
                allContactsTPane.setText("All Contacts");
                //accordionLists.put(0, allContactsTPane.getContent());
                List<Integer> groupIds = new ArrayList<>();
                for (ContactsGroup g : userGroups) {
                    TitledPane titledPane = new TitledPane();
                    titledPane.setText(g.getGroupName());
                    List<String> groupUserNames = getGroupUsersNames(g);
                    VBox vBox = new VBox();
                    if (groupUserNames != null) {
                        groupUserNames.forEach(s -> vBox.getChildren().add(new Label(s)));
                    }
                    titledPane.setContent(vBox);
                    groupsAccordion.getPanes().add(titledPane);
                }
            } else {
                System.out.println("No user groups");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private List<String> getGroupUsersNames(ContactsGroup g) throws RemoteException {
//        ContactsGroupDao groupDao = RMIConnection.getContactsGroupDao();
//        //get ids of users in group
//        List<Integer> groupUsersIds = new ArrayList<>();
//        List<GroupContact> groupContacts = groupDao.getGroupContacts(g.getGroupId());
//        groupContacts.stream()
//                .map(GroupContact::getUserId)
//                .forEach(groupUsersIds::add);
//
//        List<String> groupUserNames = new ArrayList<>();
//        if (groupUsersIds.isEmpty())
//            groupUsersIds = null;
//        else {
//            for (Integer id : groupUsersIds) {
//                User user = null;
//                user = userDao.getUser(id);
//                groupUserNames.add(user.getUsername() == null ?
//                        user.getPhoneNumber() : user.getUsername());
//            }
//        }
//
//        return groupUserNames;
        return null;
    }

    List<Integer> friendIds;

    private void loadContacts() {
        User user = clientStageCoordinator.currentUser;
        int userId = user.getId();
        try {
            List<Relationship> userRelationships = userDao.getUserRelationships(userId);
            if (userRelationships != null) {
                TitledPane allContactsTPane = new TitledPane();
                allContactsTPane.setText("All Contacts");
                //  List<Integer> friendIds = new ArrayList<>();
                friendIds = new ArrayList<>();
                for (Relationship r : userRelationships) {
                    if (r.getStatus() == RelationshipStatus.ACCEPTED) {
                        int id = r.getFirstUserId();
                        if (id != user.getId())
                            friendIds.add(id);
                        else
                            friendIds.add(r.getSecondUserId());
                    }
                }
                List<User> friends = new ArrayList<>();
                for (Integer i : friendIds)
                    friends.add(userDao.getUser(i));
                List<Label> names = new ArrayList<>();
                friends.stream()
                        .map(f -> f.getUsername() == null ? f.getPhoneNumber() : f.getUsername())
                        .forEach(s -> names.add(new Label(s)));
                VBox vBox = new VBox();
                vBox.getChildren().addAll(names);
                allContactsTPane.setContent(vBox);
                groupsAccordion.getPanes().add(allContactsTPane);
            } else {
                System.out.println("No Relationships");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
                        System.out.println(groupChatsLv.getFixedCellSize());
                        System.out.println(groupChatsLv.getItems());
                        //  for(int i =0;i<groupChatsLv.getItems().size();i++) {
                        //  setText(groupChatsLv.getItems().get(i).getTitle());
                        //for alaa you need to return image and name for userid 2 and set it in text

                        if (item != null) {

                            setText(singleChatsLv.getItems().get(0).toString());
                            System.out.println(groupChatsLv.getFixedCellSize());
                            System.out.println(groupChatsLv.getItems());
                            Image imageForTasting = new Image("images/chat-circle-blue-512.png");
                            Circle imageCircle = new Circle();
                            imageCircle.setFill(new ImagePattern(imageForTasting));
                            imageCircle.setRadius(20);
                            imageCircle.setStroke(Color.GREEN);
                            imageCircle.setStrokeWidth(3);
                            setGraphic(imageCircle);
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
            Stage stage = new Stage();
            Scene scene = new Scene(addSingleChatView);
            stage.setScene(scene);
            stage.setTitle("Add Single Chat");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO:REMOVE DTO FROM IMPLEMENTATION
    /*public void addNewGroup(GroupDto groupDto) {
        TitledPane titledPane = new TitledPane();
        titledPane.setText(groupDto.getGroupName());
        //TODO: maintain photo
        groupDto.getUsers().forEach(userDto -> titledPane.getChildrenUnmodifiable().add(new Label(userDto.getUsername())));
        groupsAccordion.getPanes().add(titledPane);
    }

    public void addContact(UserDto user) {
        System.out.println(user);
    }

    public void addLoggedIn(UserDto user) {
        System.out.println(user);
    }

    public void removeLoggedOut(UserDto user) {
        System.out.println(user);
    }*/
}

