package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXComboBox;
import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddSingleChatViewController implements Initializable {
    SingleChatDao singleChatDao = RMIConnection.getSingleChatDao();
    private User currentUser = ClientStageCoordinator.getInstance().currentUser;
    private int userTwoId;
    private ClientStageCoordinator clientStageCoordinator = ClientStageCoordinator.getInstance();
    private UserDao userDao = RMIConnection.getUserDao();
    @FXML
    private JFXComboBox<eg.gov.iti.jets.models.entities.User> usersCompoBox;

    @FXML
    void handleContactsCB(ActionEvent event) {
        System.out.println(usersCompoBox.getSelectionModel().getSelectedItem());
        User selectedItem = usersCompoBox.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
        userTwoId = selectedItem.getId();

        System.out.println(userTwoId);
    }

    @FXML
    void handleCreateButton(ActionEvent event) {
        try {
            SingleChat singleChat = new SingleChat(this.currentUser.getId(), userTwoId);
            System.out.println(singleChat + "my singleChat");
            singleChatDao.createSingleChat(singleChat);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersCompoBox.setPromptText("Choose Contact");
        usersCompoBox.setEditable(false);
        List<eg.gov.iti.jets.models.entities.User> contacts = loadContacts();
        ObservableList<eg.gov.iti.jets.models.entities.User> options = FXCollections.observableList(contacts);
        usersCompoBox.setItems(options);
    }

    private List<User> loadContacts() {
        User user = clientStageCoordinator.currentUser;
        List<User> friends = null;
        int userId = user.getId();
        try {
            List<Relationship> userRelationships = userDao.getUserRelationships(userId);
            if (userRelationships != null) {
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
                friends = new ArrayList<>();
                for (Integer i : friendIds)
                    friends.add(userDao.getUser(i));
            } else {
                System.out.println("No Relationships");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return friends;
    }


}


