package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatMessageDao;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.network.implementations.ServerService;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class SingleChatMessageDaoImpl extends UnicastRemoteObject implements SingleChatMessageDao {
    private static Connection connection = DBConnection.getInstance().getConnection();

    private static SingleChatMessageDaoImpl instance;
    private static ServerService serverService = ServerService.getInstance();
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

        try {
            String sql = "INSERT INTO SINGLE_CHAT_MESSAGE (SINGLE_CHAT_MESSAGE_ID, SINGLE_CHAT_ID,  USER_ID, CONTENT, MESSAGE_TIMESTAMP) VALUES (SEQ_SINGLE_CHAT_MESSAGE_ID.NEXTVAL,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, singleChatMessage.getSingleChatId());
            stmt.setInt(2, singleChatMessage.getUserId());
            stmt.setString(3, singleChatMessage.getContent());
            stmt.setTimestamp(4, Timestamp.valueOf(singleChatMessage.getMessageTimestamp()));
            int affectedRow = stmt.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
// a call recieve function mn elclient receiveNewSingleChatMessage();
        serverService.recieveSingleChatMessage(singleChatMessage.getUserId(), singleChatMessage.getSingleChatMessageId());
        return false;
    }

    @Override
    public SingleChatMessage getSingleChatMessage(int singleChatMessageId) {

        SingleChatMessage singleChatMessageRef = null;
        try {
            PreparedStatement statement;
            String sql = "select * from SINGLE_CHAT_MESSAGE where SINGLE_CHAT_MESSAGE_ID = ? ";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, singleChatMessageId);
            ResultSet resultset = statement.executeQuery();

            System.out.println(resultset);
            while (resultset.next()) {
                System.out.println("inside" + singleChatMessageRef);
                System.out.println(resultset.getInt("SINGLE_CHAT_MESSAGE_ID"));
                singleChatMessageRef = new SingleChatMessage(resultset.getInt("SINGLE_CHAT_MESSAGE_ID"), resultset.getInt("SINGLE_CHAT_ID"), resultset.getString("CONTENT"));
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
