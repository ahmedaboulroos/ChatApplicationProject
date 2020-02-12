package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class UserDaoImpl extends UnicastRemoteObject implements UserDao {

    protected UserDaoImpl() throws RemoteException {
    }

    @Override
    public boolean createUser(User user) {
        return false;
    }

    @Override
    public User getUser(int userId) {
        return null;
    }

    @Override
    public User getUser(String phoneNumber) {
        return null;
    }

    @Override
    public User getUser(String phoneNumber, String password) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public List<Relationship> getUserRelationships(int userId) {
        return null;
    }

    @Override
    public List<SingleChat> getUserSingleChats(int userId) {
        return null;
    }

    @Override
    public List<Membership> getUserGroupChatsMembership(int userId) {
        return null;
    }

    @Override
    public List<GroupChat> getUserGroupChats(int userId) {
        return null;
    }

    @Override
    public List<AnnouncementDelivery> getUserAnnouncementDeliveries(int userId) {
        return null;
    }

    @Override
    public List<Group> getUserGroups(int userId) {
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        return false;
    }
}
