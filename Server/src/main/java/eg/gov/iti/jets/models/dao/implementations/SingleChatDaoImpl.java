package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SingleChatDaoImpl implements SingleChatDao {
    private static Connection connection;

    public static void main(String[] args) {
        DBConnection.getInstance().initConnection();
        connection = DBConnection.getInstance().getConnection();
        SingleChatDaoImpl obj = new SingleChatDaoImpl();
        //SingleChat singleChat = new SingleChat(1, 123, 124);
       // SingleChat.createSingleChat(singleChat);
       // SingleChat.deleteSingleChat(3);
//        SingleChat ss= obj.getSingleChat(1);
//        System.out.println(ss.getUserOneId());
//        SingleChat ob = new SingleChat(1, 123, 125);
//        boolean falg = obj .updateSingleChat(ob);
//        System.out.println(falg);
        List<SingleChat> singlechats = new ArrayList<>();
        singlechats=obj.getSingleChatMessages(1);

        System.out.println("First element of the ArrayList: "+singlechats.get(0));
    }

    @Override
    public boolean createSingleChat(SingleChat singleChat) {
        boolean flag = false;

        try {
            System.out.println(connection);
            connection.setAutoCommit(false);
            String sql = "INSERT INTO SINGLE_CHAT (SINGLE_CHAT_ID,  USER_ONE_ID, USER_TWO_ID) VALUES (SEQ_SINGLE_CHAT_ID.NEXTVAL,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, singleChat.getUserOneId());
            preparedStatement.setInt(2, singleChat.getUserTwoId());


            int affectedRow = preparedStatement.executeUpdate();
            System.out.println(affectedRow);
            connection.commit();
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

        SingleChat singleChatRef = null;
        try {
            PreparedStatement statement;
            String sql = "select * from SINGLE_CHAT where SINGLE_CHAT_ID= ? ";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, singleChatId);
            ResultSet resultset = statement.executeQuery();

            System.out.println(resultset);
            while (resultset.next()) {

                singleChatRef = new SingleChat(resultset.getInt("SINGLE_CHAT_ID"), resultset.getInt("USER_ONE_ID"), resultset.getInt("USER_TWO_ID"));

            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return singleChatRef;


    }

    @Override
    public List<User> getSingleChatTwoUsers(int singleChatId) {
//        List<User> users = new ArrayList<>();
//        User userRef=null;
//        try {
//            String sql = " select  * FROM  SINGLE_CHAT  WHERE SINGLE_CHAT_ID  = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setInt(1,singleChatId );
//            ResultSet  resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                userRef=new User(resultSet.getInt(""));
//                users.add();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return users;
//
//
           return null;
        }


    @Override
    public List<SingleChat> getSingleChatMessages(int singleChatId) {
        SingleChat singleChatRef;
        List<SingleChat> singlechats = new ArrayList<>();
        try {
            String sql = " select  * FROM  SINGLE_CHAT  WHERE SINGLE_CHAT_ID  = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, singleChatId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                singleChatRef = new SingleChat(resultSet.getInt("USER_ONE_ID"), resultSet.getInt("USER_TWO_ID"));
                singlechats.add(singleChatRef);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateSingleChat(SingleChat singleChat) {
        String sql = "UPDATE  SINGLE_CHAT SET USER_ONE_ID= ?  , USER_TWO_ID= ?" +
                " where SINGLE_CHAT_ID= ? ";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, singleChat.getUserOneId());
            preparedStatement.setInt(2, singleChat.getUserTwoId());

            preparedStatement.setInt(3, singleChat.getSingleChatId());
            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected != 0) {

                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean addSingleChatMessage(int singleChatMessageId) {

        return false;
    }

    @Override
    public boolean deleteSingleChat(int singleChatId) {
        try {
            String sql = "delete from SINGLE_CHAT where SINGLE_CHAT_ID= ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, singleChatId);


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
