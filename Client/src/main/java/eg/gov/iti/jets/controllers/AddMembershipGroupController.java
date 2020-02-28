package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.entities.GroupChatMembership;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.rmi.RemoteException;
import java.util.List;

public class AddMembershipGroupController {
    private static AddMembershipGroupController addMembershipGroupController;
    @FXML
    private JFXButton addContactBtn;
    @FXML
    private JFXTextField phoneNumberTf;
    @FXML
    private Label errorsLbl;
    private int groupChatId;
    private ListView<GroupChatMembership> membershipListView;
    User user;
    GroupInfoViewController groupInfoViewController;
    private List<GroupChatMembership> groupChatMembershipList;

    public boolean checkmembership(int groupChatId) {

        List<GroupChatMembership> groupChatMembershipList = null;
        try {
            groupChatMembershipList = RMIConnection.getGroupChatDao().getGroupChatMemberships(groupChatId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (int index = 0; index < groupChatMembershipList.size(); index++) {
            if (user.getId() == groupChatMembershipList.get(index).getUserId()) {
                return true;
            }

        }
        return false;
    }

    @FXML
    void handleAddContactBtn(ActionEvent event) {
        try {

            user = RMIConnection.getUserDao().getUser(phoneNumberTf.getText());
            System.out.println(user + "user number");

            if (user != null) {
                GroupChatMembership groupChatMembership = new GroupChatMembership(user.getId(), groupChatId);
                if (!checkmembership(groupChatId)) {
                    RMIConnection.getGroupChatMembershipDao().createGroupChatMembership(groupChatMembership);
                    System.out.println(groupChatMembership + "user   groupChatMembership  number");
                    errorsLbl.setText("user added");
                    phoneNumberTf.clear();
                    // groupInfoViewController.addInList(groupChatMembership);
                    ((Node) (event.getSource())).getScene().getWindow().hide();

                } else {
                    errorsLbl.setText(("user already in group"));
                    phoneNumberTf.clear();
                }
            } else {
                errorsLbl.setText("user not found");
                phoneNumberTf.clear();
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

    public void setGroupInfoViewController(GroupInfoViewController groupInfoViewController) {
        this.groupInfoViewController = groupInfoViewController;
    }

    public void setRefresh(ListView<GroupChatMembership> membershipListView) {
        this.membershipListView = membershipListView;

    }

    public void setClear(List<GroupChatMembership> groupChatMembershipList) {
        this.groupChatMembershipList = groupChatMembershipList;
    }


}
