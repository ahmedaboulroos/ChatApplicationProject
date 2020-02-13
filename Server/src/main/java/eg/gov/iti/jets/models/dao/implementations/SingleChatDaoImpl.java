package eg.gov.iti.jets.models.dao.implementation;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class SingleChatDaoImpl implements SingleChatDao {
    Connection connection = DBConnection.getInstance().getConnection();
    public static void main(String[] args) {

        Connection  connection = DBConnection.getInstance().getConnection();
        SingleChatDaoImpl SingleChat = new SingleChatDaoImpl();
        SingleChat singleChat=new SingleChat(1,20,22);
        SingleChat. createSingleChat(singleChat);
        // SingleChatMessage obj = SingleChatMessage1.getSingleChatMessage(21);
//        System.out.println(obj.getUserId());

    }
    @Override
    public boolean createSingleChat(SingleChat singleChat) {
        boolean flag = false;

        try {

            connection.setAutoCommit(false);
            String sql = "INSERT INTO SINGLE_CHAT (SINGLE_CHAT_ID,  USER_ONE_ID, USER_TWO_ID,) VALUES (SEQ_SINGLE_CHAT_ID.NEXTVAL,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, singleChat.getUserOneId());
            preparedStatement.setInt(2, singleChat.getUserTwoId());


            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


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
