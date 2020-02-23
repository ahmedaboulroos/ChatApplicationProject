package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.MembershipDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.Membership;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class MembershipDaoImpl extends UnicastRemoteObject implements MembershipDao {

    private Connection connection = DBConnection.getConnection();

    private static MembershipDaoImpl instance;

    protected MembershipDaoImpl() throws RemoteException {
    }

    public static MembershipDao getInstance() {
        if (instance == null) {
            try {
                instance = new MembershipDaoImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public int createMembership(Membership membership) {
        int autoGenMemberShipID = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select SEQ_MEMBERSHIP_ID.nextval from DUAL");

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                autoGenMemberShipID = resultSet.getInt(1);
                String sql = "INSERT INTO MEMBERSHIP (MEMBERSHIP_ID,  USER_ID, GROUP_CHAT_ID, JOINED_TIMESTAMP) VALUES (?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, autoGenMemberShipID);
                preparedStatement.setInt(2, membership.getUserId());
                preparedStatement.setInt(3, membership.getGroupChatId());
                preparedStatement.setTimestamp(4, Timestamp.valueOf(membership.getJoinedTimestamp()));
            }
            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow != -1) {
                System.out.println("inside --->> memebershipdao membership created");
                return autoGenMemberShipID;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autoGenMemberShipID;
    }

    @Override
    public Membership getMembership(int membershipId) {

        Membership membership = null;
        try {
            PreparedStatement statement;
            String sql = "select * from MEMBERSHIP where MEMBERSHIP_ID = ? ";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, membershipId);

            ResultSet resultset = statement.executeQuery();

            System.out.println(resultset);
            while (resultset.next()) {
                membership = new Membership(resultset.getInt("USER_ID"), resultset.getInt("GROUP_CHAT_ID"));
            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return membership;


    }

    @Override
    public User getUser(int membershipId) {
        try {
            Membership membership = getMembership(membershipId);
            return UserDaoImpl.getInstance().getUser(membership.getUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public GroupChat getGroupChat(int membershipId) {
        try {
            Membership membership = getMembership(membershipId);
            return GroupChatDaoImpl.getInstance().getGroupChat(membership.getGroupChatId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateMembership(Membership membership) {

        String sql = "UPDATE MEMBERSHIP SET JOINED_TIMESTAMP= ? " +
                " where MEMBERSHIP_ID =? and USER_ID = ?  and GROUP_CHAT_ID =?";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(membership.getJoinedTimestamp()));
            preparedStatement.setInt(2, membership.getMembershipId());

            preparedStatement.setInt(3, membership.getGroupChatId());
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
    public boolean deleteMembership(int membershipId) {

        try {

            String sql = "delete from MEMBERSHIP where MEMBERSHIP_ID = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, membershipId);


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
