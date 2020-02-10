package eg.gov.iti.jets.models.dao.implementation;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.User;

import java.util.List;

public class SingleChatDaoImpl implements SingleChatDao {
    @Override
    public boolean createSingleChat(SingleChat singleChat) {
        return false;
    }

    @Override
    public SingleChat getSingleChat(int singleChatId) {
        return null;
    }

    @Override
    public List<User> getSingleChatTwoUsers(int singleChatId) {
        return null;
    }

    @Override
    public List<SingleChat> getSingleChatMessages(int singleChatId) {
        return null;
    }

    @Override
    public boolean updateSingleChat(SingleChat singleChat) {
        return false;
    }

    @Override
    public boolean addSingleChatMessage(int singleChatMessageId) {
        return false;
    }

    @Override
    public boolean deleteSingleChat(int singleChatId) {
        return false;
    }
}
