package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.implementations.ServerService;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SingleChatDaoImpl extends UnicastRemoteObject implements SingleChatDao {
    private Connection connection = DBConnection.getConnection();

    private static SingleChatDaoImpl instance;
    private ServerService serverService = ServerService.getInstance();
    protected SingleChatDaoImpl() throws RemoteException {
    }

    public static SingleChatDao getInstance() {
        if (instance == null) {
            try {
                instance = new SingleChatDaoImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

//    public static void main(String[] args) {
//        DBConnection.getInstance().initConnection();
//        connection = DBConnection.getInstance().getConnection();
//        SingleChatDaoImpl obj = new SingleChatDaoImpl();
//        //SingleChat singleChat = new SingleChat(1, 123, 124);
//       // SingleChat.createSingleChat(singleChat);
//       // SingleChat.deleteSingleChat(3);
////        SingleChat ss= obj.getSingleChat(1);
////        System.out.println(ss.getUserOneId());
////        SingleChat ob = new SingleChat(1, 123, 125);
////        boolean falg = obj .updateSingleChat(ob);
////        System.out.println(falg);
//        List<SingleChat> singlechats = new ArrayList<>();
//        singlechats=obj.getSingleChatMessages(1);
//
//        System.out.println("First element of the ArrayList: "+singlechats.get(0));
//    }

    @Override
    public boolean createSingleChat(SingleChat singleChat) {
        boolean flag = false;
        PreparedStatement stmt1 = null;
        PreparedStatement preparedStatement = null;
        try {
            System.out.println(connection);
            connection.setAutoCommit(false);
            String sql = "INSERT INTO SINGLE_CHAT (SINGLE_CHAT_ID,  USER_ONE_ID, USER_TWO_ID) VALUES (SEQ_SINGLE_CHAT_ID.NEXTVAL,?,?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, singleChat.getUserOneId());
            preparedStatement.setInt(2, singleChat.getUserTwoId());


            int affectedRow = preparedStatement.executeUpdate();
            stmt1 = connection.prepareStatement("select SEQ_SINGLE_CHAT_ID.currval from dual");
            ResultSet resultSet = stmt1.executeQuery();
            System.out.println(affectedRow + "affectedRow");
            connection.commit();
            if (affectedRow > 0) {
                int singleChatId = 0;
                while (resultSet.next()) {
                    singleChatId = resultSet.getInt(1);
                }
                System.out.println(singleChatId);
                ClientInterface clientTwo = serverService.getClient(singleChat.getUserTwoId());
                System.out.println("gbt clientTwo");
                clientTwo.receiveNewSingleChat(singleChatId);
                System.out.println("estaqbl");
                ClientInterface clientOne = serverService.getClient(singleChat.getUserOneId());
                System.out.println("gbt clientOne");
                clientOne.receiveNewSingleChat(singleChatId);
                System.out.println("estaqbl");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                stmt1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

        return null;


    }

    @Override
    public List<SingleChatMessage> getSingleChatMessages(int singleChatId) {
        String sql = "select * from SINGLE_CHAT_MESSAGE where SINGLE_CHAT_ID = ?";
        List<SingleChatMessage> singleChatMessages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, singleChatId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                singleChatMessages.add(new SingleChatMessage(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getTimestamp(4).toLocalDateTime()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return singleChatMessages;
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
