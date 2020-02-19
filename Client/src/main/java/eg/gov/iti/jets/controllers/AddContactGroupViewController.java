package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.GroupContactDao;
import eg.gov.iti.jets.models.dao.interfaces.GroupDao;
import eg.gov.iti.jets.models.dao.interfaces.RelationshipDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.Group;
import eg.gov.iti.jets.models.entities.GroupContact;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class AddContactGroupViewController implements Initializable {

    User user = ClientStageCoordinator.getInstance().currentUser;
    GroupDao groupDao = RMIConnection.getInstance().getGroupDao();
    GroupContactDao groupContactDao = RMIConnection.getInstance().getGroupContactDao();
    UserDao userDao = RMIConnection.getInstance().getUserDao();
    RelationshipDao relationshipDao = RMIConnection.getInstance().getRelationshipDao();

    @FXML
    private JFXButton createGroupBtn;

    @FXML
    private JFXTextField groupNameTf;
    @FXML
    private ListView<Group> availableGroupsLv;
    @FXML
    private ListView<GroupContact> availableContactsLv;
    @FXML
    private ListView<GroupContact> availableGroupContactsLv;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Group> groups = userDao.getUserGroups(user.getUserId());
            if (groups != null) {
                availableGroupsLv.setItems(FXCollections.observableList(groups));
            }

            List<GroupContact> contacts = userDao.getUserContacts(user.getUserId());
            if (contacts != null) {
                availableContactsLv.setItems(FXCollections.observableList(contacts));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCreateGroupBtn(ActionEvent event) {
        try {
            Group group = new Group(user.getUserId(), groupNameTf.getText());
            groupDao.createGroup(group);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddContactToGroup(ActionEvent event) {

    }

    @FXML
    void handleSelectionMc(MouseEvent event) {
        Group group = availableGroupsLv.getSelectionModel().getSelectedItem();
        if (group != null) {
            try {
                List<GroupContact> groupContacts = groupDao.getGroupContacts(group.getGroupId());
                if (groupContacts != null) {
                    availableGroupContactsLv.setItems(FXCollections.observableList(groupContacts));
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


}
