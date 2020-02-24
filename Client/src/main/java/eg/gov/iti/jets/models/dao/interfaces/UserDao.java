package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.*;
import eg.gov.iti.jets.models.entities.enums.UserStatus;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserDao extends Remote {

    // Create
    int createUser(User user) throws RemoteException;

    // Read
    User getUser(int userId) throws RemoteException;

    User getUser(String phoneNumber) throws RemoteException;

    User getUser(String phoneNumber, String password) throws RemoteException;

    List<User> getAllUsers() throws RemoteException;

    List<Relationship> getUserRelationships(int userId) throws RemoteException;

    List<SingleChat> getUserSingleChats(int userId) throws RemoteException;

    List<GroupChat> getUserGroupChats(int userId) throws RemoteException;

    List<GroupChatMembership> getUserGroupChatsMembership(int userId) throws RemoteException;

    List<ContactsGroup> getUserContactsGroups(int userId) throws RemoteException;

    List<ContactsGroupMembership> getUserContactsGroupMemberships(int userId) throws RemoteException;

    // Update
    void updateUser(User user) throws RemoteException;

    void updateUserStatus(int userId, UserStatus userStatus) throws RemoteException;

    // Delete
    void deleteUser(int userId) throws RemoteException;

}
