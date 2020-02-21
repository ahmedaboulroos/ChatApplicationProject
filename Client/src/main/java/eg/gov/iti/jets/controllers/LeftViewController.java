package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.GroupDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.dto.GroupDto;
import eg.gov.iti.jets.models.dto.UserDto;
import eg.gov.iti.jets.models.entities.*;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
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
    Circle imageCircle = new Circle();

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
        int userId = user.getUserId();
        try {
            List<Group> userGroups = userDao.getUserGroups(userId);
            if (userGroups != null) {
                TitledPane allContactsTPane = new TitledPane();
                allContactsTPane.setText("All Contacts");
                //accordionLists.put(0, allContactsTPane.getContent());
                List<Integer> groupIds = new ArrayList<>();
                for (Group g : userGroups) {
                    TitledPane titledPane = new TitledPane();
                    titledPane.setText(g.getGroupName());
                    List<String> groupUserNames = getGroupUsersNames(g);
                    VBox vBox = new VBox();
                    groupUserNames.forEach(s -> vBox.getChildren().add(new Label(s)));
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

    private List<String> getGroupUsersNames(Group g) throws RemoteException {
        GroupDao groupDao = RMIConnection.getGroupDao();
        //get ids of users in group
        List<Integer> groupUsersIds = new ArrayList<>();
        List<GroupContact> groupContacts = groupDao.getGroupContacts(g.getGroupId());
        groupContacts.stream()
                .map(GroupContact::getUserId)
                .forEach(groupUsersIds::add);

        List<String> groupUserNames = new ArrayList<>();
        if (groupUsersIds.isEmpty())
            groupUsersIds = null;
        else {
            for (Integer id : groupUsersIds) {
                User user = null;
                user = userDao.getUser(id);
                groupUserNames.add(user.getUsername() == null ?
                        user.getPhoneNumber() : user.getUsername());
            }
        }

        return groupUserNames;
    }

    List<Integer> friendIds;

    private void loadContacts() {
        User user = clientStageCoordinator.currentUser;
        int userId = user.getUserId();
        try {
            List<Relationship> userRelationships = userDao.getUserRelationships(userId);
            if (userRelationships != null) {
                TitledPane allContactsTPane = new TitledPane();
                allContactsTPane.setText("All Contacts");
                //  List<Integer> friendIds = new ArrayList<>();
                friendIds = new ArrayList<>();
                for (Relationship r : userRelationships) {
                    if (r.getRelationshipStatus() == RelationshipStatus.ACCEPTED) {
                        int id = r.getFirstUserId();
                        if (id != user.getUserId())
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
            List<SingleChat> singleChats = userDao.getUserSingleChats(ClientStageCoordinator.getInstance().currentUser.getUserId());
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
            groupChats = userDao.getUserGroupChats(ClientStageCoordinator.getInstance().currentUser.getUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println(groupChats);

        if (groupChats != null) {
            groupChatsLv.setItems(FXCollections.observableList(groupChats));
            System.out.println("55555555555555555");
            groupChatsLv.setCellFactory(groupChatsLv -> new ListCell<GroupChat>() {

                @Override
                public void updateItem(GroupChat item, boolean empty) {
                    super.updateItem(item, empty);

                    //  for(int i =0;i<groupChatsLv.getItems().size();i++) {
                    //  setText(groupChatsLv.getItems().get(i).getTitle());
                    //for elnaggar you need to add image of user and handle the status of user ;
                    if (item != null) {
                        setText(groupChatsLv.getItems().get(0).getTitle());

                        Image imageForTasting = new Image("images/chat-circle-blue-512.png");

                        imageCircle.setFill(new ImagePattern(imageForTasting));
                        imageCircle.setRadius(20);
                        imageCircle.setStroke(Color.GREEN);
                        imageCircle.setStrokeWidth(3);
                        setGraphic(imageCircle);
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
            System.out.println(singleChat.getSingleChatId());
            ClientStageCoordinator.getInstance().openNewSingleChat(singleChat.getSingleChatId());
        }
    }

    @FXML
    void handleGroupChatSelection(MouseEvent event) {
        GroupChat groupChat = groupChatsLv.getSelectionModel().getSelectedItem();

        ////groupchatmessages controller to get the messages list from the DB
        /*GroupChatMessageController groupChatMessageController = new GroupChatMessageController();
        List<GroupChatMessage> groupChatMessageList = groupChatMessageController.getAllGroupChatMessages(groupChat.getGroupChatId());
        ///printing message list*/
        List<GroupChatMessage> groupChatMessageList = null;
        try {
            groupChatMessageList = RMIConnection.getGroupChatDao().getGroupChatMessages(groupChat.getGroupChatId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(groupChatMessageList);

        if (groupChat != null) {
            System.out.println(groupChat.getGroupChatId());
            ClientStageCoordinator.getInstance().openNewGroupChat(groupChat.getGroupChatId());
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

    public void addNewGroup(GroupDto groupDto) {
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
    }
}

