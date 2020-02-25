package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.ContactsGroupDao;
import eg.gov.iti.jets.models.dao.interfaces.ContactsGroupMembershipDao;
import eg.gov.iti.jets.models.dao.interfaces.RelationshipDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.ContactsGroup;
import eg.gov.iti.jets.models.entities.ContactsGroupMembership;
import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.ByteArrayInputStream;
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

    private String getUserDisplayName(User user) {
        return user.getUsername() == null ? user.getPhoneNumber() : user.getUsername();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        availableGroupsLv.setCellFactory(listViewListCellCallback -> new ListCell<>() {
            @Override
            protected void updateItem(ContactsGroup group, boolean empty) {
                super.updateItem(group, empty);
                if (group != null) {
                    setText(group.getGroupName());
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
        availableGroupContactsLv.setCellFactory(listViewListCellCallback -> new ListCell<>() {
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
        availableContactsLv.setCellFactory(listViewListCellCallback -> new ListCell<>() {
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
                    setText(null);
                    setGraphic(null);
                }
            }
        });
        try {
            List<ContactsGroup> groups = userDao.getUserContactsGroups(user.getId());
            if (groups != null) {
                availableGroupsLv.setItems(FXCollections.observableList(groups));
            }
            List<ContactsGroupMembership> memberships = userDao.getUserContactsGroupMemberships(user.getId());
            if (memberships != null) {
                availableGroupContactsLv.setItems(FXCollections.observableList(memberships));
            }
            List<Relationship> relationships = userDao.getUserRelationships(user.getId());
            if (relationships != null) {
                List<User> contacts = new ArrayList<>();
                for (Relationship relationship : relationships) {
                    if (relationship.getFirstUserId() == ClientStageCoordinator.getInstance().currentUser.getId())
                        contacts.add(userDao.getUser(relationship.getSecondUserId()));
                    else
                        contacts.add(userDao.getUser(relationship.getFirstUserId()));
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
        ContactsGroup group = availableGroupsLv.getSelectionModel().getSelectedItem();
        if (group != null) {
            try {
                List<ContactsGroupMembership> groupContacts = contactsGroupDao.getContactsGroupMemberships(group.getId());
                if (groupContacts != null) {
                    availableGroupContactsLv.setItems(FXCollections.observableList(groupContacts));
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
