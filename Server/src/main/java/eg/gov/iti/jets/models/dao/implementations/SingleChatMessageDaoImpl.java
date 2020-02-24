package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatMessageDao;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.network.implementations.ServerService;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class SingleChatMessageDaoImpl extends UnicastRemoteObject implements SingleChatMessageDao {

    private static SingleChatMessageDaoImpl instance;
    private static Connection dbConnection;

    private ServerService serverService = ServerService.getInstance();

    protected SingleChatMessageDaoImpl() throws RemoteException {
    }

    public static SingleChatMessageDao getInstance(Connection connection) {
        if (instance == null) {
            try {
                instance = new SingleChatMessageDaoImpl();
                dbConnection = connection;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public int createSingleChatMessage(SingleChatMessage singleChatMessage) {
        int id = -1;
        String[] key = {"ID"};
        String sql = "INSERT INTO SINGLE_CHAT_MESSAGES (ID, USER_ID, CONTENT, MESSAGE_DATE_TIME, SINGLE_CHAT_ID) VALUES (ID_SEQ.NEXTVAL,?,?,?,?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, key)) {
            ps.setInt(1, singleChatMessage.getUserId());
            ps.setString(2, singleChatMessage.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(singleChatMessage.getMessageDateTime()));
            ps.setInt(4, singleChatMessage.getSingleChatId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            ClientInterface clientOne = ServerService.getClient(SingleChatDaoImpl.getInstance(dbConnection).getSingleChat(singleChatMessage.getSingleChatId()).getUserOneId());
            if (clientOne != null) {
                clientOne.receiveNewSingleChatMessage(id);
            }
            ClientInterface clientTwo = ServerService.getClient(SingleChatDaoImpl.getInstance(dbConnection).getSingleChat(singleChatMessage.getSingleChatId()).getUserTwoId());
            if (clientTwo != null) {
                clientTwo.receiveNewSingleChatMessage(id);
            }
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public SingleChatMessage getSingleChatMessage(int singleChatMessageId) {
        SingleChatMessage singleChatMessage = null;
        String sql = "select ID, USER_ID, CONTENT, MESSAGE_DATE_TIME, SINGLE_CHAT_ID from SINGLE_CHAT_MESSAGES where SINGLE_CHAT_MESSAGE_ID = ? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, singleChatMessageId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                singleChatMessage = new SingleChatMessage(rs.getInt(1), rs.getInt(5), rs.getInt(2), rs.getString(3), rs.getTimestamp(4).toLocalDateTime());
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return singleChatMessage;
    }

    @Override
    public void updateSingleChatMessage(SingleChatMessage singleChatMessage) {
        String sql = "UPDATE  SINGLE_CHAT_MESSAGES SET USER_ID = ?, CONTENT= ?, MESSAGE_DATE_TIME=?, SINGLE_CHAT_ID=? where ID=?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
            preparedStatement.setInt(1, singleChatMessage.getUserId());
            preparedStatement.setString(2, singleChatMessage.getContent());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(singleChatMessage.getMessageDateTime()));
            preparedStatement.setInt(4, singleChatMessage.getSingleChatId());
            preparedStatement.setInt(5, singleChatMessage.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSingleChatMessage(int singleChatMessageId) {
        String sql = "delete from SINGLE_CHAT_MESSAGES where ID = ?";
        try (PreparedStatement stmt = dbConnection.prepareStatement(sql)) {
            stmt.setInt(1, singleChatMessageId);
            stmt.executeUpdate();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }

}
