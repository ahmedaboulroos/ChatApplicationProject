package eg.gov.iti.jets.views;

import eg.gov.iti.jets.models.dao.interfaces.GroupDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.*;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

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

    private UserDao userDao = RMIConnection.getInstance().getUserDao();

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
        int userId = user.getUserId();
        try {
            List<Group> userGroups = userDao.getUserGroups(userId);
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
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private List<String> getGroupUsersNames(Group g) throws RemoteException {
        GroupDao groupDao = RMIConnection.getInstance().getGroupDao();
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

    private void loadContacts() {
        User user = clientStageCoordinator.currentUser;
        int userId = user.getUserId();
        try {
            List<Relationship> userRelationships = userDao.getUserRelationships(userId);
            TitledPane allContactsTPane = new TitledPane();
            allContactsTPane.setText("All Contacts");
            //accordionLists.put(0, allContactsTPane.getContent());
            List<Integer> friendIds = new ArrayList<>();
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
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void loadSingleChats() {
        try {
            List<SingleChat> singleChats = userDao.getUserSingleChats(ClientStageCoordinator.getInstance().currentUser.getUserId());
            System.out.println(singleChats);
            singleChatsLv.setItems(FXCollections.observableList(singleChats));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void loadGroupChats() {
        try {
            List<GroupChat> groupChats = userDao.getUserGroupChats(ClientStageCoordinator.getInstance().currentUser.getUserId());
            System.out.println(groupChats);
            groupChatsLv.setItems(FXCollections.observableList(groupChats));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
