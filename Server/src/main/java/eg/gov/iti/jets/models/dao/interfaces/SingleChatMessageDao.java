package eg.gov.iti.jets.models.dao.interfaces;


import eg.gov.iti.jets.models.entities.SingleChatMessage;

public interface SingleChatMessageDao {

    // Create
    boolean createSingleChatMessage(SingleChatMessage singleChatMessage);

    // Read
    SingleChatMessage getSingleChatMessage(int singleChatMessageId);

    // Update
    boolean updateSingleChatMessage(SingleChatMessage singleChatMessage);

    // Delete
    boolean deleteSingleChatMessage(int singleChatMessageId);
}
