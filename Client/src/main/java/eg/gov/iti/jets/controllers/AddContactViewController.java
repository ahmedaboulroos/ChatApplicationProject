package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.User;
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
                Relationship relationship = new Relationship(ClientStageCoordinator.getInstance().currentUser.getId(), user.getId());
                RMIConnection.getRelationshipDao().createRelationship(relationship);
                phoneNumberTf.clear();
            } else {
                errorsLbl.setText("user not found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
