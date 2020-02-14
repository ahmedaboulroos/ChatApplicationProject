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
    private static Connection connection;

    protected MembershipDaoImpl() throws RemoteException {
    }

    public static void main(String[] args) throws RemoteException {

        DBConnection.getInstance().initConnection();
        connection = DBConnection.getInstance().getConnection();
        MembershipDaoImpl obj = new MembershipDaoImpl();
        Membership ref = new Membership(125, 1);
        Membership refff = new Membership(125, 1);
        boolean flag = obj.createMembership(ref);


    }

    @Override
    public boolean createMembership(Membership membership) {

        try {


            String sql = "INSERT INTO MEMBERSHIP (MEMBERSHIP_ID,  USER_ID, GROUP_CHAT_ID, JOINED_TIMESTAMP) VALUES (SEQ_MEMBERSHIP_ID.NEXTVAL,?,?,?)";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, membership.getUserId());
            stmt.setInt(2, membership.getGroupChatId());
            stmt.setTimestamp(3, Timestamp.valueOf(membership.getJoinedTimestamp()));


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
        return null;
    }

    @Override
    public GroupChat getGroupChat(int membershipId) {
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
