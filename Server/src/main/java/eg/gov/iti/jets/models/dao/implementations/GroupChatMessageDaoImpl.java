package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.GroupChatMessageDao;
import eg.gov.iti.jets.models.entities.GroupChatMembership;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.network.implementations.ServerService;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.List;

public class GroupChatMessageDaoImpl extends UnicastRemoteObject implements GroupChatMessageDao {

    private static GroupChatMessageDaoImpl instance;
    private static Connection dbConnection;

    protected GroupChatMessageDaoImpl() throws RemoteException {
    }

    public static GroupChatMessageDaoImpl getInstance(Connection connection) {
        if (instance == null) {
            try {
                instance = new GroupChatMessageDaoImpl();
                dbConnection = connection;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;

    }

    @Override
    public int createGroupChatMessage(GroupChatMessage groupChatMessage) {
        System.out.println("inside GroupChatMessageDaoIMp groupChatMessage" + groupChatMessage);
        int id = -1;
        String[] key = {"ID"};
        String sql = "INSERT INTO  GROUP_CHAT_MESSAGES (ID, USER_ID, GROUP_CHAT_ID, CONTENT,MESSAGE_DATE_TIME) VALUES (ID_SEQ.NEXTVAL,?,?,?,?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, key)) {
            ps.setInt(1, groupChatMessage.getUserId());
            ps.setInt(2, groupChatMessage.getGroupChatId());
            ps.setString(3, groupChatMessage.getContent());
            ps.setTimestamp(4, Timestamp.valueOf(groupChatMessage.getMessageDateTime()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            List<GroupChatMembership> groupChatMemberships = GroupChatDaoImpl.getInstance(dbConnection).getGroupChatMemberships(groupChatMessage.getGroupChatId());
            System.out.println("inside GroupChatMessageDaoIMp groupChatMessage.getGroupChatId()" + groupChatMessage.getGroupChatId());
            for (GroupChatMembership m : groupChatMemberships) {
                ClientInterface client = ServerService.getClient(m.getUserId());
                if (client != null) {
                    System.out.println("inside groupChatMessageDao IMP message id" + id);
                    client.receiveNewGroupChatMessage(id);
                }
            }
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public GroupChatMessage getGroupChatMessage(int groupChatMessageId) {
        GroupChatMessage groupChatMessage = null;
        String sql = "select ID, USER_ID, GROUP_CHAT_ID, CONTENT, MESSAGE_DATE_TIME from GROUP_CHAT_MESSAGES  where ID=?";
        try (PreparedStatement statement = dbConnection.prepareStatement(sql)) {
            statement.setInt(1, groupChatMessageId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                groupChatMessage = new GroupChatMessage(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getTimestamp(5).toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupChatMessage;
    }

    @Override
    public void updateGroupChatMessage(GroupChatMessage groupChatMessage) {
        String sql = "UPDATE GROUP_CHAT_MESSAGES SET USER_ID=?, GROUP_CHAT_ID=? ,CONTENT=?, MESSAGE_DATE_TIME=? where ID=?";
        try (PreparedStatement statment = dbConnection.prepareStatement(sql)) {
            statment.setInt(1, groupChatMessage.getUserId());
            statment.setInt(2, groupChatMessage.getGroupChatId());
            statment.setString(3, groupChatMessage.getContent());
            statment.setTimestamp(4, Timestamp.valueOf(groupChatMessage.getMessageDateTime()));
            statment.setInt(5, groupChatMessage.getUserId());
            statment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGroupChatMessage(int groupChatMessageId) {
        String sql = "Delete from GROUP_CHAT_MESSAGES where ID=? ";
        try (PreparedStatement statement = dbConnection.prepareStatement(sql)) {
            statement.setInt(1, groupChatMessageId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

