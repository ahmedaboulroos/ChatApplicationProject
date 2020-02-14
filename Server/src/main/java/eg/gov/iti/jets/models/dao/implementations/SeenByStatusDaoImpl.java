package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.SeenByStatusDao;
import eg.gov.iti.jets.models.entities.SeenByStatus;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeenByStatusDaoImpl extends UnicastRemoteObject implements SeenByStatusDao {
    private static Connection connection;

    protected SeenByStatusDaoImpl() throws RemoteException {
    }

    private static SeenByStatusDaoImpl instance;

    public static SeenByStatusDao getInstance() {
        if (instance == null) {
            try {
                instance = new SeenByStatusDaoImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /*
        public static void main(String[] args) {
            DBConnection.getInstance().initConnection();
            // connection = DBConnection.getInstance().getConnection();
            try {
                SeenByStatusDaoImpl obj = new SeenByStatusDaoImpl();
                SeenByStatus seen = new SeenByStatus(2, 12, 2);
                boolean d = obj.createSeenByStatus(seen);
                System.out.println(d);


            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }
    */
    @Override
    public boolean createSeenByStatus(SeenByStatus seenByStatus) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {

            String sql = "INSERT INTO Seen_By_Status (SEEN_BY_STATUs_ID, GROUP_CHAT_MESSAGE_ID, USER_ID) VALUES (SEQ_SEEN_BY_STATUS_ID.NEXTVAL,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, seenByStatus.getGroupChatMessageId());
            preparedStatement.setInt(2, seenByStatus.getUserId());

            if (preparedStatement.executeUpdate() != 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public SeenByStatus getSeenByStatus(int seenByStatusId) {
        SeenByStatus seenByStatus = null;
        try {
            PreparedStatement statement;
            String sql = "select * from Seen_By_Status where SEEN_BY_STATUs_ID= ? ";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, seenByStatusId);
            ResultSet resultset = statement.executeQuery();
            System.out.println(resultset);
            while (resultset.next()) {

                seenByStatus = new SeenByStatus(resultset.getInt("SEEN_BY_STATUs_ID"), resultset.getInt("GROUP_CHAT_MESSAGE_ID"), resultset.getInt("USER_ID"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seenByStatus;
    }

    @Override
    public boolean updateSeenByStatus(SeenByStatus seenByStatus) {
        String sql = "UPDATE  Seen_By_Status SET GROUP_CHAT_MESSAGE_ID= ?  , USER_ID= ?" +
                " where SEEN_BY_STATUs_ID= ? ";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, seenByStatus.getGroupChatMessageId());
            preparedStatement.setInt(2, seenByStatus.getUserId());

            preparedStatement.setInt(3, seenByStatus.getSeenByStatusId());
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
    public boolean deleteSeenByStatus(int seenByStatusId) {
        try {
            String sql = "delete from Seen_By_Status where SEEN_BY_STATUs_ID= ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, seenByStatusId);


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
