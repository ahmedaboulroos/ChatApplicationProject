package eg.gov.iti.jets.views;

import eg.gov.iti.jets.models.dto.GroupDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatAppViewController implements Initializable {

    @FXML
    private BorderPane chatAppBp;

    /*private UserController userController;
    private SingleChatMessageController singleChatMessageController;
    private SingleChatController singleChatController;
    private SeenByStatusController seenByStatusController;
    private RelationshipController relationshipController;
    private MembershipController membershipController;
    private GroupController groupController;
    private GroupContactController groupContactController;
    private GroupChatMessageController groupChatMessageController;
    private GroupChatController groupChatController;
    private AnnouncementDeliveryController announcementDeliveryController;
    private AnnouncementController announcementController;*/

    LeftViewController leftViewController;
    CenterViewController centerViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader leftViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/LeftView.fxml"));
            Parent leftView = leftViewFxmlLoader.load();
            leftViewController = leftViewFxmlLoader.getController();
            chatAppBp.setLeft(leftView);

            FXMLLoader centerViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/CenterView.fxml"));
            Parent centerView = centerViewFxmlLoader.load();
            centerViewController = centerViewFxmlLoader.getController();
            chatAppBp.setCenter(centerView);

            FXMLLoader rightViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/RightView.fxml"));
            Parent rightView = rightViewFxmlLoader.load();
            chatAppBp.setRight(rightView);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openSingleChat(int singleChatId) {
        centerViewController.addSingleChat(singleChatId);
    }

    public void openGroupChat(int groupChatId) {
        centerViewController.addGroupChat(groupChatId);
    }

    public void displayNewGroup(GroupDto groupDto) {
        leftViewController.addNewGroup(groupDto);
    }

    public void displayMsg(String hi_hi) {
        System.out.println(hi_hi);
    }

/*
    public void setControllers(AnnouncementController announcementController, AnnouncementDeliveryController announcementDeliveryController, GroupChatController groupChatController, GroupChatMessageController groupChatMessageController, GroupContactController groupContactController, GroupController groupController, MembershipController membershipController, RelationshipController relationshipController, SeenByStatusController seenByStatusController, SingleChatController singleChatController, SingleChatMessageController singleChatMessageController, UserController userController) {
        this.announcementController = announcementController;
        this.announcementDeliveryController = announcementDeliveryController;
        this.groupChatController = groupChatController;
        this.groupChatMessageController = groupChatMessageController;
        this.groupContactController = groupContactController;
        this.groupController = groupController;
        this.membershipController = membershipController;
        this.relationshipController = relationshipController;
        this.seenByStatusController = seenByStatusController;
        this.singleChatController = singleChatController;
        this.singleChatMessageController = singleChatMessageController;
        this.userController = userController;
    }
*/
}
