package eg.gov.iti.jets.models.dao.implementation;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatMessageDao;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.persistence.DBConnection;
import oracle.net.aso.f;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;

public class SingleChatMessageDaoImpl implements SingleChatMessageDao {
     Connection connection ;
    public static void main(String[] args) {


        SingleChatMessageDaoImpl SingleChatMessage1 = new SingleChatMessageDaoImpl();
        SingleChatMessage1. deleteSingleChatMessage(21);
       // SingleChatMessage obj = SingleChatMessage1.getSingleChatMessage(21);
//        System.out.println(obj.getUserId());

    }

    public SingleChatMessageDaoImpl(){
        DBConnection.getInstance().initConnection();
        connection = DBConnection.getInstance().getConnection();
        System.out.println(connection);
    }

    @Override
    public boolean createSingleChatMessage(SingleChatMessage singleChatMessage) {


        try {

            connection.setAutoCommit(false);
            String sql = "INSERT INTO SINGLE_CHAT_MESSAGE (SINGLE_CHAT_MESSAGE_ID,  USER_ID, CONTENT, MESSAGE_TIMESTAMP) VALUES (SEQ_SINGLE_CHAT_MESSAGE_ID.NEXTVAL,?,?,?)";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, singleChatMessage.getUserId());
            stmt.setString(2, singleChatMessage.getContent());


            stmt.setTimestamp(3, Timestamp.valueOf(singleChatMessage.getMessageTimestamp()));
            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public SingleChatMessage getSingleChatMessage(int singleChatMessageId) {
        System.out.println(singleChatMessageId);
        SingleChatMessage singleChatMessageObj = null;
        try {
            PreparedStatement statement;
            String sql="select * from SINGLE_CHAT_MESSAGE where SINGLE_CHAT_MESSAGE_ID = ? ";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, singleChatMessageId);
            ResultSet resultset = statement.executeQuery();

            System.out.println(resultset);
                while (resultset.next()) {
                    System.out.println("inside");
                    System.out.println(resultset.getInt("SINGLE_CHAT_MESSAGE_ID"));
                    singleChatMessageObj.setSingleChatMessageId(resultset.getInt("SINGLE_CHAT_MESSAGE_ID"));
                    singleChatMessageObj.setContent(resultset.getString("CONTENT"));
                }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return singleChatMessageObj;
    }

    @Override
    public boolean updateSingleChatMessage(SingleChatMessage singleChatMessage) {
        String sql = "UPDATE  SINGLE_CHAT_MESSAGE SET SINGLE_CHAT_MESSAGE_ID= SEQ_SINGLE_CHAT_MESSAGE_ID.NEXTVAL," +
                "USER_ID = ?,CONTENT = ?,MESSAGE_TIMESTAMP= ?  " +
                " WHERE SINGLE_CHAT_MESSAGE_ID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, singleChatMessage.getUserId());
            preparedStatement.setString(2, singleChatMessage.getContent());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(singleChatMessage.getMessageTimestamp()));
            preparedStatement.setLong(4, singleChatMessage.getSingleChatMessageId());
            if (preparedStatement.executeUpdate() == 1) {
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSingleChatMessage(int singleChatMessageId) {
        try {

            String sql = "delete from SINGLE_CHAT_MESSAGE where  SINGLE_CHAT_MESSAGE_ID=?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, singleChatMessageId);
            if (stmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException sqe) {


            sqe.printStackTrace();
        }
        return false;
    }

    private void closeResultSetAndPreparedStatement(ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
