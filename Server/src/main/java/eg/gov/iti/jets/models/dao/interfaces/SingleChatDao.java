package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.User;

import java.util.List;

public interface SingleChatDao {

    // Create
    boolean createSingleChat(SingleChat singleChat);

    // Read
    SingleChat getSingleChat(int singleChatId);
    List<User> getSingleChatTwoUsers(int singleChatId);
    List<SingleChat> getSingleChatMessages(int singleChatId);

    // Update
    boolean updateSingleChat(SingleChat singleChat);
    boolean addSingleChatMessage(int singleChatMessageId);

    // Delete
    boolean deleteSingleChat(int singleChatId);

}
