package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMembership;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GroupChatDao extends Remote {
    // Create
    int createGroupChat(GroupChat groupChat) throws RemoteException;

    // Read
    GroupChat getGroupChat(int groupChatId) throws RemoteException;

    List<GroupChatMembership> getGroupChatMemberships(int groupChatId) throws RemoteException;

    List<User> getGroupChatUsers(int groupChatId) throws RemoteException;

    List<GroupChatMessage> getGroupChatMessages(int groupChatId) throws RemoteException;

    // Update
    void updateGroupChat(GroupChat groupChat) throws RemoteException;

    // Delete
    void deleteGroupChat(int groupChatId) throws RemoteException;

}
