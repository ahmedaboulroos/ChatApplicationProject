package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatMessageDao;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class SingleChatMessageDaoImpl implements SingleChatMessageDao {
    private static Connection connection;

    public static void main(String[] args) {

        DBConnection.getInstance().initConnection();
        connection = DBConnection.getInstance().getConnection();


        SingleChatMessageDaoImpl ss = new SingleChatMessageDaoImpl();

        LocalDateTime datetime1 = LocalDateTime.of(2017, 1, 14, 10, 34);
        SingleChatMessage ob = new SingleChatMessage(1, 124,
                "rrrr", datetime1);
        boolean falg = ss.updateSingleChatMessage(ob);
        System.out.println(falg);

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

        SingleChatMessage singleChatMessageRef = null;
        try {
            PreparedStatement statement;
            String sql = "select * from SINGLE_CHAT_MESSAGE where SINGLE_CHAT_MESSAGE_ID = ? ";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, singleChatMessageId);
            ResultSet resultset = statement.executeQuery();

            System.out.println(resultset);
            while (resultset.next()) {
                System.out.println("inside" + singleChatMessageRef);
                System.out.println(resultset.getInt("SINGLE_CHAT_MESSAGE_ID"));
                singleChatMessageRef = new SingleChatMessage(resultset.getInt("SINGLE_CHAT_MESSAGE_ID"), resultset.getString("CONTENT"));

            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return singleChatMessageRef;
    }

    @Override
    public boolean updateSingleChatMessage(SingleChatMessage singleChatMessage) {
        String sql = "UPDATE  SINGLE_CHAT_MESSAGE SET CONTENT= ? " +
                " where SINGLE_CHAT_MESSAGE_ID=? and USER_ID = ? ";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, singleChatMessage.getContent());
            preparedStatement.setInt(2, singleChatMessage.getSingleChatMessageId());

            preparedStatement.setInt(3, singleChatMessage.getUserId());
            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected != 0) {
                System.out.println("true");
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

            String sql = "delete from SINGLE_CHAT_MESSAGE where SINGLE_CHAT_MESSAGE_ID = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, singleChatMessageId);

            System.out.println(singleChatMessageId);
            int affectedRow = stmt.executeUpdate();
            if (affectedRow != 0) {
                return true;
            }


        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return false;
    }

}
