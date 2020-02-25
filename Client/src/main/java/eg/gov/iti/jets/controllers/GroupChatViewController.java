package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.GroupChatMessageDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.web.HTMLEditor;

import java.rmi.RemoteException;
import java.util.List;

public class GroupChatViewController {
    private GroupChatMessageDao groupChatMessageDao = RMIConnection.getGroupChatMessageDao();
    private UserDao userDao = RMIConnection.getUserDao();

    private static GroupChatViewController instance;

    public static GroupChatViewController getInstance() {
        if (instance == null) {
            instance = new GroupChatViewController();
        }
        return instance;
    }
    @FXML
    private ListView<GroupChatMessage> groupChatMessagesLv;

    @FXML
    private HTMLEditor groupChatMessageHtml;

    public void setGroupChatMessages(int groupChatId) {
        try {
            List<GroupChatMessage> groupChatMessages = RMIConnection.getGroupChatDao().getGroupChatMessages(groupChatId);
            groupChatMessagesLv.setItems(FXCollections.observableList(groupChatMessages));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void displayNewGroupChatMessage(int groupChatMessageId) throws RemoteException {
        ////////////////////

        GroupChatMessage groupChatMessage = null;
            groupChatMessage = groupChatMessageDao.getGroupChatMessage(groupChatMessageId);

        User user = userDao.getUser(groupChatMessage.getUserId());
        System.out.println(
                " groupChatMessageId : " + groupChatMessage.getGroupChatId()
                        + "\n userId: " + groupChatMessage.getUserId()
                        +"\n  Id "+groupChatMessage.getId()
                        + "\n content " + groupChatMessage.getContent()
                        + "\n TimeStamp " + groupChatMessage.getMessageDateTime());
        System.out.println("name : " + user.getUsername() + "Image : " + user.getProfileImage());
    }
    }



