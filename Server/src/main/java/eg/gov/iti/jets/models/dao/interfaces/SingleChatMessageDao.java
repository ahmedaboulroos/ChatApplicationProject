package eg.gov.iti.jets.models.dao.interfaces;


import eg.gov.iti.jets.models.entities.SingleChatMessage;

import java.sql.SQLException;

public interface SingleChatMessageDao {

    // Create
    boolean createSingleChatMessage(SingleChatMessage singleChatMessage) throws SQLException;

    // Read
    SingleChatMessage getSingleChatMessage(int singleChatMessageId);

    // Update
    boolean updateSingleChatMessage(SingleChatMessage singleChatMessage);

    // Delete
    boolean deleteSingleChatMessage(int singleChatMessageId);
}
