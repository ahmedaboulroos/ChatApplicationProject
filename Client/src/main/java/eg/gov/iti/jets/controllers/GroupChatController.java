package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.views.ClientStageCoordinator;

import java.rmi.RemoteException;
import java.util.List;

public class GroupChatController {

    private UserDao userDao;
    private List<GroupChat> groupChats;

    public GroupChatController() {
        userDao = RMIConnection.getInstance().getUserDao();
    }

    ///getting all th user's groupchat from userdoa instance
    public List<GroupChat> getAllGroupChats() {
        {
            System.out.println(ClientStageCoordinator.getInstance().currentUser.getUserId());
            try {
                groupChats = userDao.getUserGroupChats(ClientStageCoordinator.getInstance().currentUser.getUserId());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return groupChats;
    }


}
