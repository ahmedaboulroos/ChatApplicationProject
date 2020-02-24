package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMembership;
import eg.gov.iti.jets.models.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GroupChatMembershipDao extends Remote {
    // Create
    int createGroupChatMembership(GroupChatMembership groupChatMembership) throws RemoteException;

    // Read
    GroupChatMembership getGroupChatMembership(int groupChatMembershipId) throws RemoteException;

    User getUser(int groupChatMembershipId) throws RemoteException;

    GroupChat getGroupChat(int groupChatMembershipId) throws RemoteException;

    // Update
    void updateGroupChatMembership(GroupChatMembership groupChatMembership) throws RemoteException;

    // Delete
    void deleteGroupChatMembership(int groupChatMembershipId) throws RemoteException;

}
