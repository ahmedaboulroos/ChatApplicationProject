package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.ContactsGroupDao;
import eg.gov.iti.jets.models.dao.interfaces.ContactsGroupMembershipDao;
import eg.gov.iti.jets.models.dao.interfaces.RelationshipDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.ContactsGroup;
import eg.gov.iti.jets.models.entities.ContactsGroupMembership;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddContactGroupViewController implements Initializable {

    User user = ClientStageCoordinator.getInstance().currentUser;
    ContactsGroupDao contactsGroupDao = RMIConnection.getContactsGroupDao();
    ContactsGroupMembershipDao contactsGroupMembershipDao = RMIConnection.getContactsGroupMembershipDao();
    UserDao userDao = RMIConnection.getUserDao();
    RelationshipDao relationshipDao = RMIConnection.getRelationshipDao();

    @FXML
    private JFXButton createGroupBtn;
    @FXML
    private JFXTextField groupNameTf;
    @FXML
    private ListView<ContactsGroup> availableGroupsLv;
    @FXML
    private ListView<User> availableContactsLv;
    @FXML
    private ListView<ContactsGroupMembership> availableGroupContactsLv;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<ContactsGroup> groups = userDao.getUserContactsGroups(user.getId());
            if (groups != null) {
                availableGroupsLv.setItems(FXCollections.observableList(groups));
            }

            List<ContactsGroupMembership> memberships = userDao.getUserContactsGroupMemberships(user.getId());

            if (memberships != null) {
                List<User> contacts = new ArrayList<>();
                for (ContactsGroupMembership membership : memberships) {
                    contacts.add(userDao.getUser(membership.getUserId()));
                }
                availableContactsLv.setItems(FXCollections.observableList(contacts));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCreateGroupBtn(ActionEvent event) {
        try {
            ContactsGroup group = new ContactsGroup(user.getId(), groupNameTf.getText());
            contactsGroupDao.createContactsGroup(group);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddContactToGroup(ActionEvent event) {

    }

    @FXML
    void handleSelectionMc(MouseEvent event) {
//        ContactsGroup group = availableGroupsLv.getSelectionModel().getSelectedItem();
//        if (group != null) {
//            try {
//                List<GroupContact> groupContacts = contactsGroupDao.getGroupContacts(group.getGroupId());
//                if (groupContacts != null) {
//                    availableGroupContactsLv.setItems(FXCollections.observableList(groupContacts));
//                }
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
    }


}
