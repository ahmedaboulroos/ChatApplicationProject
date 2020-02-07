package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.SeenByStatus;

import java.util.List;

public interface GroupChatMessageDao {
    // Create
    boolean createGroupChatMessage(GroupChatMessage groupChatMessage);

    // Read
    GroupChatMessage getGroupChatMessage(int groupChatMessageId);
    List<SeenByStatus> getSeenByStatus(int groupChatMessageId);

    // Update
    boolean updateGroupChatMessage(GroupChatMessage groupChatMessage);

    // Delete
    boolean deleteGroupChatMessage(int groupChatMessageId);

}
