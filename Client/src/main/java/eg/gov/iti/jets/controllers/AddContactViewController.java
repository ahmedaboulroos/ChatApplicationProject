package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

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
                System.out.println("relation ship status" + relationship.getStatus());
                phoneNumberTf.clear();
                //
//notification(relationship);

            } else {
                errorsLbl.setText("user not found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }





    }

