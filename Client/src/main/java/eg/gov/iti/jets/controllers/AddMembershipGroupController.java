package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.entities.GroupChatMembership;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.rmi.RemoteException;

public class AddMembershipGroupController {
    private static AddMembershipGroupController addMembershipGroupController;
    @FXML
    private JFXButton addContactBtn;
    @FXML
    private JFXTextField phoneNumberTf;
    @FXML
    private Label errorsLbl;
    private int groupChatId;

    @FXML
    void handleAddContactBtn(ActionEvent event) {
        try {
            User user = RMIConnection.getUserDao().getUser(phoneNumberTf.getText());
            System.out.println(user + "user number");
            if (user != null) {
                GroupChatMembership groupChatMembership = new GroupChatMembership(user.getId(), groupChatId);

                RMIConnection.getGroupChatMembershipDao().createGroupChatMembership(groupChatMembership);
                System.out.println(groupChatMembership + "user   groupChatMembership  number");
                errorsLbl.setText("user added");
                phoneNumberTf.clear();
            } else {
                errorsLbl.setText("user not found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setGroupChatId(int groupChatId) {
        this.groupChatId = groupChatId;
        System.out.println("groupChatId is " + groupChatId);
    }

    public void setController(AddMembershipGroupController addMembershipGroupController) {
        this.addMembershipGroupController = addMembershipGroupController;
    }

}
