package eg.gov.iti.jets.views;

import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    private StageCoordinator stageCoordinator;

    public void setStageCoordinator(StageCoordinator stageCoordinator) {
        this.stageCoordinator = stageCoordinator;
    }

    User user = stageCoordinator.currentUser;

    private HashMap<Integer, ObservableList<Node>> accordionLists;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int userId = user.getUserId();
        try {
            List<Relationship> userRelationships = userDao.getUserRelationships(userId);
            TitledPane allContactsTPane = new TitledPane();
            allContactsTPane.setText("All Contacts");
            accordionLists.put(0, allContactsTPane.getChildrenUnmodifiable());
            groupsAccordion.getChildrenUnmodifiable().add(allContactsTPane);
            List<Integer> friendIds = new ArrayList<>();
            for(Relationship r: userRelationships) {
                if(r.getRelationshipStatus()== RelationshipStatus.ACCEPTED) {
                    int id = r.getFirstUserId();
                    if (id == user.getUserId())
                        friendIds.add(id);
                    else
                        friendIds.add(r.getSecondUserId());
                }
            }
            List<User> friends = new ArrayList<>();
            for(Integer i: friendIds)
                friends.add(userDao.getUser(i));
            List<Label> names = new ArrayList<>();
            friends.stream()
                    .map(f->f.getUsername()==null?f.getUsername():f.getPhoneNumber())
                    .forEach(s->names.add(new Label(s)));
            allContactsTPane.getChildrenUnmodifiable().addAll(names);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        loadSingleChats();
        loadGroupChats();
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
