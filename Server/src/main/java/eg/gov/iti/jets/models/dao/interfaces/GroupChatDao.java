package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.Membership;
import eg.gov.iti.jets.models.entities.User;

import java.util.List;

public interface GroupChatDao {
    // Create
    boolean createGroupChat(GroupChat groupChat);

    // Read
    GroupChat getGroupChat(int groupChatId);
    List<Membership> getGroupChatMemberships(int groupChatId);
    List<User> getGroupChatUsers(int groupChatId);
    List<GroupChatMessage> getGroupChatMessages(int groupChatId);

    // Update
    boolean updateGroupChat(GroupChat groupChat);
    boolean addGroupChatMessage(int groupMessageId);
    boolean addGroupChatUser(int userId);

    // Delete
    boolean deleteGroupChat(int groupChatId);

}
