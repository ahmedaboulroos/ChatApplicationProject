package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.*;

import java.util.List;

public interface UserDao {

    // Create
    boolean createUser(User user);

    // Read
    User getUser(int userId);
    User getUser(String phoneNumber);
    User getUser(String phoneNumber, String password);
    List<User> getAllUsers();
    List<Relationship> getUserRelationships(int userId);
    List<SingleChat> getUserSingleChats(int userId);
    List<Membership> getUserGroupChatsMembership(int userId);
    List<GroupChat> getUserGroupChats(int userId);
    List<AnnouncementDelivery> getUserAnnouncementDeliveries(int userId);
    List<Group> getUserGroups(int userId);

    // Update
    boolean updateUser(User user);

    // Delete
    boolean deleteUser(int userId);

}
