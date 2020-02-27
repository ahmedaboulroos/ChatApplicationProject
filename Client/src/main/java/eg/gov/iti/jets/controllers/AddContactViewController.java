package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.RelationshipDao;
import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.rmi.RemoteException;

public class AddContactViewController {
    @FXML
    private JFXButton addContactBtn;

    @FXML
    private JFXTextField phoneNumberTf;

    @FXML
    private Label errorsLbl;

    @FXML
    void handleAddContactBtn(ActionEvent event) {
        try {
            User user = RMIConnection.getUserDao().getUser(phoneNumberTf.getText());
            if (user != null) {
                RelationshipDao relationshipDao = RMIConnection.getInstance().getRelationshipDao();
                // Relationship relationship = new Relationship(ClientStageCoordinator.getInstance().currentUser.getId(), user.getId());
                Relationship relationship = relationshipDao.getRelationshipBetween(ClientStageCoordinator.getInstance().currentUser.getId(), user.getId());
                System.out.println(ClientStageCoordinator.getInstance().currentUser.getId());
                System.out.println(user.getId());
                if (relationship != null) {
                    System.out.println("Relationship Status: " + relationship.getStatus().toString());
                    if (relationship.getStatus().equals(RelationshipStatus.ACCEPTED)) {
                        errorsLbl.setText("You are already friends with that user.");
                    } else if (relationship.getStatus().equals(RelationshipStatus.PENDING)) {
                        errorsLbl.setText("Friend Request is pending.");
                    } else if (relationship.getStatus().equals(RelationshipStatus.BLOCKED)) {
                        errorsLbl.setText("User not found");
                    }
                } else {
                    relationship = new Relationship(ClientStageCoordinator.getInstance().currentUser.getId(), user.getId());
                    RMIConnection.getRelationshipDao().createRelationship(relationship);
                    System.out.println("relation ship status" + relationship.getStatus());
                    phoneNumberTf.clear();
                }

            } else {
                errorsLbl.setText("User not found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}

