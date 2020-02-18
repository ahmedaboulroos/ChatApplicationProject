package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserDao extends Remote {

    // Create
    boolean createUser(User user) throws RemoteException;

    // Read
    User getUser(int userId) throws RemoteException;

    User getUser(String phoneNumber) throws RemoteException;

    User getUser(String phoneNumber, String password) throws RemoteException;

    List<User> getAllUsers() throws RemoteException;

    List<Relationship> getUserRelationships(int userId) throws RemoteException;

    List<SingleChat> getUserSingleChats(int userId) throws RemoteException;

    List<Membership> getUserGroupChatsMembership(int userId) throws RemoteException;

    List<GroupChat> getUserGroupChats(int userId) throws RemoteException;

    List<AnnouncementDelivery> getUserAnnouncementDeliveries(int userId) throws RemoteException;

    List<Group> getUserGroups(int userId) throws RemoteException;

    List<GroupContact> getUserContacts(int userId) throws RemoteException;

    // Update
    boolean updateUser(User user) throws RemoteException;

    // Delete
    boolean deleteUser(int userId) throws RemoteException;

}
