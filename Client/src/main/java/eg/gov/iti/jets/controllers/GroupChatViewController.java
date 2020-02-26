package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.GroupChatMessageDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.web.HTMLEditor;

import java.rmi.RemoteException;
import java.util.List;

public class GroupChatViewController {
    private GroupChatMessageDao groupChatMessageDao = RMIConnection.getGroupChatMessageDao();
    private UserDao userDao = RMIConnection.getUserDao();
    private static GroupChatViewController groupChatViewController;
    private static GroupChatViewController instance;

    public static GroupChatViewController getInstance() {
        return groupChatViewController;
    }

    public void setController(GroupChatViewController groupChatViewController) {
        this.groupChatViewController = groupChatViewController;
    }

    @FXML
    private ListView<GroupChatMessage> groupChatMessagesLv;

    @FXML
    private HTMLEditor groupChatMessageHtml;
    private int groupChatId;
    private User currentUser = ClientStageCoordinator.getInstance().currentUser;

    public void setGroupChatMessages(int groupChatId) {
        try {
            this.groupChatId = groupChatId;
            updateGroupChat();
            List<GroupChatMessage> groupChatMessages = RMIConnection.getGroupChatDao().getGroupChatMessages(groupChatId);
            groupChatMessagesLv.setItems(FXCollections.observableList(groupChatMessages));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSendBtn(ActionEvent event) {
        try {
            String msg = groupChatMessageHtml.getHtmlText();
            System.out.println(msg);
            GroupChatMessageDao groupChatDao = RMIConnection.getGroupChatMessageDao();
            GroupChatMessage GroupChatMessage = new GroupChatMessage(this.groupChatId, currentUser.getId(), msg);
            groupChatDao.createGroupChatMessage(GroupChatMessage);
        } catch (
                RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateGroupChat() {
        try {
            groupChatMessagesLv.getItems().clear();
            List<GroupChatMessage> groupChatMessages = RMIConnection.getGroupChatDao().getGroupChatMessages(groupChatId);
            if (groupChatMessages != null) {
                groupChatMessagesLv.setItems(FXCollections.observableList(groupChatMessages));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addGroupChatMessage(GroupChatMessage groupChatMessage) {
        Platform.runLater(() -> groupChatMessagesLv.getItems().add(groupChatMessage));
    }

    public void displayNewGroupChatMessage(int groupChatMessageId) throws RemoteException {
        GroupChatMessage groupChatMessage = null;
        groupChatMessage = groupChatMessageDao.getGroupChatMessage(groupChatMessageId);

        User user = userDao.getUser(groupChatMessage.getUserId());
        System.out.println(
                " groupChatMessageId : " + groupChatMessage.getGroupChatId()
                        + "\n userId: " + groupChatMessage.getUserId()
                        + "\n  Id " + groupChatMessage.getId()
                        + "\n content " + groupChatMessage.getContent()
                        + "\n TimeStamp " + groupChatMessage.getMessageDateTime());
        System.out.println("name : " + user.getUsername() + "Image : " + user.getProfileImage());
    }
    }



