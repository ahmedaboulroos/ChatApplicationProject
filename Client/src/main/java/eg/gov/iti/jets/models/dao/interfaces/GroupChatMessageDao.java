package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.GroupChatMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GroupChatMessageDao extends Remote {
    // Create
    int createGroupChatMessage(GroupChatMessage groupChatMessage) throws RemoteException;

    // Read
    GroupChatMessage getGroupChatMessage(int groupChatMessageId) throws RemoteException;

    // Update
    void updateGroupChatMessage(GroupChatMessage groupChatMessage) throws RemoteException;

    // Delete
    void deleteGroupChatMessage(int groupChatMessageId) throws RemoteException;

}
