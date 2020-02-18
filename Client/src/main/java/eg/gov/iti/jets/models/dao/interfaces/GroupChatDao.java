package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.Membership;
import eg.gov.iti.jets.models.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GroupChatDao extends Remote {
    // Create
    boolean createGroupChat(GroupChat groupChat) throws RemoteException;

    // Read
    GroupChat getGroupChat(int groupChatId) throws RemoteException;

    List<Membership> getGroupChatMemberships(int groupChatId) throws RemoteException;

    List<User> getGroupChatUsers(int groupChatId) throws RemoteException;

    List<GroupChatMessage> getGroupChatMessages(int groupChatId) throws RemoteException;

    // Update
    boolean updateGroupChat(GroupChat groupChat) throws RemoteException;

    // Delete
    boolean deleteGroupChat(int groupChatId) throws RemoteException;

}
