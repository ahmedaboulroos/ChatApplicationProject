package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.network.RMIConnection;

import java.rmi.RemoteException;
import java.util.List;

public class GroupChatMessageController {

    GroupChatDao groupChatDao;
    List<GroupChatMessage> groupChatMessages;

    public GroupChatMessageController() {
        groupChatDao = RMIConnection.getInstance().getGroupChatDao();
    }

    public List<GroupChatMessage> getAllGroupChatMessages(int groupChatID) {

        try {
            groupChatMessages = groupChatDao.getGroupChatMessages(groupChatID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return groupChatMessages;
    }
}
