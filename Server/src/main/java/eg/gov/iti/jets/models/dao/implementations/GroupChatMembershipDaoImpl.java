package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.GroupChatMembershipDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMembership;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.implementations.ServerService;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.List;

public class GroupChatMembershipDaoImpl extends UnicastRemoteObject implements GroupChatMembershipDao {

    private static GroupChatMembershipDaoImpl instance;
    private static Connection dbConnection;

    protected GroupChatMembershipDaoImpl() throws RemoteException {
    }

    public static GroupChatMembershipDao getInstance(Connection connection) {
        if (instance == null) {
            try {
                instance = new GroupChatMembershipDaoImpl();
                dbConnection = connection;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public int createGroupChatMembership(GroupChatMembership groupChatMembership) {
        int id = -1;
        String[] key = {"ID"};
        String sql = "INSERT INTO GROUP_CHAT_MEMBERSHIPS (ID, USER_ID, GROUP_CHAT_ID, JOINED_DATE_TIME) VALUES (ID_SEQ.NEXTVAL,?,?,?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, key)) {
            ps.setInt(1, groupChatMembership.getUserId());
            ps.setInt(2, groupChatMembership.getGroupChatId());
            ps.setTimestamp(3, Timestamp.valueOf(groupChatMembership.getJoinedDateTime()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            List<GroupChatMembership> groupChatMemberships = GroupChatDaoImpl.getInstance(dbConnection).getGroupChatMemberships(groupChatMembership.getGroupChatId());
            for (GroupChatMembership m : groupChatMemberships) {
                ClientInterface client = ServerService.getClient(m.getUserId());
                if (client != null) {
                    client.receiveNewGroupChatMembership(id);
                }
            }
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public GroupChatMembership getGroupChatMembership(int groupChatMembershipId) {
        GroupChatMembership groupChatMembership = null;
        String sql = "select ID, USER_ID, GROUP_CHAT_ID, JOINED_DATE_TIME from GROUP_CHAT_MEMBERSHIPS where ID = ? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, groupChatMembershipId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                groupChatMembership = new GroupChatMembership(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4).toLocalDateTime());
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return groupChatMembership;
    }

    @Override
    public User getUser(int groupChatMembershipId) {
        try {
            System.out.println("inside groupchatmembership  imple  groupChatMembershipId ==>> " + groupChatMembershipId);
            GroupChatMembership groupChatMembership = getGroupChatMembership(groupChatMembershipId);
            System.out.println("groupchat memberships dao impl ==> getUser GroupChatMembership groupChatMembership " + groupChatMembership);
            return UserDaoImpl.getInstance(dbConnection).getUser(groupChatMembership.getUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public GroupChat getGroupChat(int groupChatMembershipId) {
        try {
            GroupChatMembership groupChatMembership = getGroupChatMembership(groupChatMembershipId);
            return GroupChatDaoImpl.getInstance(dbConnection).getGroupChat(groupChatMembership.getGroupChatId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateGroupChatMembership(GroupChatMembership groupChatMembership) {
        String sql = "UPDATE GROUP_CHAT_MEMBERSHIPS SET USER_ID = ?, GROUP_CHAT_ID = ?, JOINED_DATE_TIME = ? where ID =?";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setInt(1, groupChatMembership.getUserId());
            ps.setInt(2, groupChatMembership.getGroupChatId());
            ps.setTimestamp(3, Timestamp.valueOf(groupChatMembership.getJoinedDateTime()));
            ps.setInt(4, groupChatMembership.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGroupChatMembership(int groupChatMembershipId) {
        try {
            GroupChatMembership groupChatMembership = GroupChatMembershipDaoImpl.getInstance(dbConnection)
                    .getGroupChatMembership(groupChatMembershipId);
            String sql = "delete from GROUP_CHAT_MEMBERSHIPS where ID = ?";
            try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
                ps.setInt(1, groupChatMembershipId);
                ps.executeUpdate();
                List<GroupChatMembership> groupChatMemberships = GroupChatDaoImpl.getInstance(dbConnection)
                        .getGroupChatMemberships(groupChatMembership.getGroupChatId());

                ClientInterface deletedClient = ServerService.getClient(groupChatMembership.getUserId());
                deletedClient.receiveNewGroupChatMembership(groupChatMembershipId);

                for (GroupChatMembership m : groupChatMemberships) {
                    ClientInterface client = ServerService.getClient(m.getUserId());
                    if (client != null) {
                        client.receiveNewGroupChatMembership(groupChatMembershipId);
                    }
                }
            } catch (SQLException | RemoteException sqe) {
                sqe.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
