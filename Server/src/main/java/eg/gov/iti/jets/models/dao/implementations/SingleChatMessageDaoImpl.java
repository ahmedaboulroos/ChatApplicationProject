package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.dao.interfaces.SingleChatMessageDao;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.network.implementations.ServerService;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class SingleChatMessageDaoImpl extends UnicastRemoteObject implements SingleChatMessageDao {
    private Connection connection = DBConnection.getConnection();

    private static SingleChatMessageDaoImpl instance;
    private ServerService serverService = ServerService.getInstance();
    protected SingleChatMessageDaoImpl() throws RemoteException {
    }

    public static SingleChatMessageDao getInstance() {
        if (instance == null) {
            try {
                instance = new SingleChatMessageDaoImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public boolean createSingleChatMessage(SingleChatMessage singleChatMessage) {
        PreparedStatement stmt = null;
        PreparedStatement stmt1 = null;
        try {
            String sql = "INSERT INTO SINGLE_CHAT_MESSAGE (SINGLE_CHAT_MESSAGE_ID, SINGLE_CHAT_ID,  USER_ID, CONTENT, MESSAGE_TIMESTAMP) VALUES (SEQ_SINGLE_CHAT_MESSAGE_ID.NEXTVAL,?,?,?,?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, singleChatMessage.getSingleChatId());
            stmt.setInt(2, singleChatMessage.getUserId());
            stmt.setString(3, singleChatMessage.getContent());
            stmt.setTimestamp(4, Timestamp.valueOf(singleChatMessage.getMessageTimestamp()));
            int affectedRow = stmt.executeUpdate();
            stmt1 = connection.prepareStatement("select SEQ_SINGLE_CHAT_MESSAGE_ID.currval from dual");
            ResultSet resultSet = stmt1.executeQuery();
            if (affectedRow != 0) {
                int singleChatMessageId = 0;
                while (resultSet.next()) {
                    singleChatMessageId = resultSet.getInt(1);
                }
                System.out.println(singleChatMessageId + " mn resultSet");
                SingleChatDao singleChatDao = SingleChatDaoImpl.getInstance();
                System.out.println(singleChatDao);
                SingleChat singleChat = singleChatDao.getSingleChat(singleChatMessage.getSingleChatId());
                System.out.println(singleChat);
                System.out.println(singleChat.getUserTwoId() + " ana usertwo");
                ClientInterface clientTwo = serverService.getClient(singleChat.getUserTwoId());
                System.out.println("gbt clientTwo");
                clientTwo.receiveNewSingleChatMessage(singleChatMessageId);
                System.out.println("estaqbl");
                ClientInterface clientOne = serverService.getClient(singleChat.getUserOneId());
                System.out.println("gbt clientOne");
                clientOne.receiveNewSingleChatMessage(singleChatMessageId);
                System.out.println("estaqbl");
                return true;
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                stmt1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    @Override
    public SingleChatMessage getSingleChatMessage(int singleChatMessageId) {

        SingleChatMessage singleChatMessageRef = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;
        try {


            String sql = "select SINGLE_CHAT_MESSAGE_ID, SINGLE_CHAT_ID,  USER_ID, CONTENT, MESSAGE_TIMESTAMP from SINGLE_CHAT_MESSAGE where SINGLE_CHAT_MESSAGE_ID = ? ";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, singleChatMessageId);
            resultset = statement.executeQuery();

            System.out.println(resultset + "ana fe getMessage");
            if (resultset.next()) {
                System.out.println(resultset + "ana fe if");
                //  System.out.println("inside" + singleChatMessageRef);
                System.out.println(resultset.getInt("SINGLE_CHAT_MESSAGE_ID") + " :singleChatMessageID");
                Timestamp timestamp = resultset.getTimestamp("MESSAGE_TIMESTAMP");
                //  int singleChatMessageId, int singleChatId, int userId, String content, LocalDateTime messageTimestamp
                singleChatMessageRef = new SingleChatMessage(resultset.getInt("SINGLE_CHAT_MESSAGE_ID"), resultset.getInt("SINGLE_CHAT_ID"), resultset.getInt("USER_ID"), resultset.getString("CONTENT"), timestamp.toLocalDateTime());
                System.out.println("inside" + singleChatMessageRef);

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
