package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.implementations.ServerService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SingleChatDaoImpl extends UnicastRemoteObject implements SingleChatDao {

    private static SingleChatDaoImpl instance;
    private static Connection dbConnection;

    protected SingleChatDaoImpl() throws RemoteException {
    }

    public static SingleChatDao getInstance(Connection connection) {
        if (instance == null) {
            try {
                instance = new SingleChatDaoImpl();
                dbConnection = connection;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public int createSingleChat(SingleChat singleChat) {
        int id = -1;
        String[] key = {"ID"};
        String sql = "INSERT INTO SINGLE_CHATS (ID,  USER_ONE_ID, USER_TWO_ID) VALUES (ID_SEQ.NEXTVAL,?,?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, key)) {
            ps.setInt(1, singleChat.getUserOneId());
            ps.setInt(2, singleChat.getUserTwoId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            ServerService.getClient(singleChat.getUserOneId()).receiveNewSingleChat(id);
            ServerService.getClient(singleChat.getUserTwoId()).receiveNewSingleChat(id);
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public SingleChat getSingleChat(int singleChatId) {
        SingleChat singleChat = null;
        String sql = "select ID, USER_ONE_ID, USER_TWO_ID from SINGLE_CHATS where ID= ? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, singleChatId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                singleChat = new SingleChat(rs.getInt(1), rs.getInt(2), rs.getInt(3));
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return singleChat;
    }

    @Override
    public List<User> getSingleChatTwoUsers(int singleChatId) {
        List<User> users = new ArrayList<>();
        String sql = "select USER_ONE_ID, USER_TWO_ID from SINGLE_CHATS where ID= ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, singleChatId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                users.add(UserDaoImpl.getInstance(dbConnection).getUser(rs.getInt(1)));
                users.add(UserDaoImpl.getInstance(dbConnection).getUser(rs.getInt(2)));
            }
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<SingleChatMessage> getSingleChatMessages(int singleChatId) {
        String sql = "select ID, USER_ID, CONTENT, MESSAGE_DATE_TIME, SINGLE_CHAT_ID from SINGLE_CHAT_MESSAGES where SINGLE_CHAT_ID = ?";
        List<SingleChatMessage> singleChatMessages = new ArrayList<>();
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setInt(1, singleChatId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                singleChatMessages.add(new SingleChatMessage(rs.getInt(1), rs.getInt(5), rs.getInt(2), rs.getString(3), rs.getTimestamp(4).toLocalDateTime()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return singleChatMessages;
    }

    @Override
    public void updateSingleChat(SingleChat singleChat) {
        String sql = "UPDATE SINGLE_CHATS SET USER_ONE_ID = ? , USER_TWO_ID = ? where ID= ? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setInt(1, singleChat.getUserOneId());
            ps.setInt(2, singleChat.getUserTwoId());
            ps.setInt(3, singleChat.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSingleChat(int singleChatId) {
        String sql = "delete from SINGLE_CHATS where ID= ?";
        try (PreparedStatement stmt = dbConnection.prepareStatement(sql)) {
            stmt.setInt(1, singleChatId);
            stmt.executeUpdate();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }


}
