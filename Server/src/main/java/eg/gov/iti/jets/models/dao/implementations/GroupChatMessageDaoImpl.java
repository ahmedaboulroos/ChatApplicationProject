package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.GroupChatMessageDao;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.SeenByStatus;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class GroupChatMessageDaoImpl extends UnicastRemoteObject implements GroupChatMessageDao {

    protected GroupChatMessageDaoImpl() throws RemoteException {
    }

    @Override
    public boolean createGroupChatMessage(GroupChatMessage groupChatMessage) {
        return false;
    }

    @Override
    public GroupChatMessage getGroupChatMessage(int groupChatMessageId) {
        return null;
    }

    @Override
    public List<SeenByStatus> getSeenByStatus(int groupChatMessageId) {
        return null;
    }

    @Override
    public boolean updateGroupChatMessage(GroupChatMessage groupChatMessage) {
        return false;
    }

    @Override
    public boolean deleteGroupChatMessage(int groupChatMessageId) {
        return false;
    }
}
