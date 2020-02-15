package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.SeenByStatus;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GroupChatMessageDao extends Remote {
    // Create
    boolean createGroupChatMessage(GroupChatMessage groupChatMessage) throws RemoteException;

    // Read
    GroupChatMessage getGroupChatMessage(int groupChatMessageId) throws RemoteException;

    List<SeenByStatus> getSeenByStatus(int groupChatMessageId) throws RemoteException;

    // Update
    boolean updateGroupChatMessage(GroupChatMessage groupChatMessage) throws RemoteException;

    // Delete
    boolean deleteGroupChatMessage(int groupChatMessageId) throws RemoteException;

}
