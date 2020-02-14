package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.GroupChatMessageDao;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.SeenByStatus;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroupChatMessageDaoImpl extends UnicastRemoteObject implements GroupChatMessageDao {

    private static GroupChatMessageDaoImpl instance;

    protected GroupChatMessageDaoImpl() throws RemoteException {
    }

    public static GroupChatMessageDaoImpl getInstance() {
        if (instance == null) {
            try {
                instance = new GroupChatMessageDaoImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;

    }

    @Override
    public boolean createGroupChatMessage(GroupChatMessage groupChatMessage) {

        Connection connection = DBConnection.getInstance().getConnection();
        boolean flag = false;
        String sql = "INSERT INTO  GROUP_CHAT_MESSAGE (GROUP_CHAT_MESSAGE_ID, USER_ID, CONTENT,MESSAGE_TIMESTAMP) VALUES (SEQ_GROUP_CHAT_MESSAGE_ID.NEXTVAL,?,?,?)";
        try {
            PreparedStatement insertpre = connection.prepareStatement(sql);

            insertpre.setInt(1, groupChatMessage.getUserId());
            insertpre.setString(2, groupChatMessage.getContent());

            insertpre.setTimestamp(3, Timestamp.valueOf(groupChatMessage.getMessageTimestamp()));
            System.out.println("here");
            int statments = insertpre.executeUpdate();
            System.out.println("before if" + statments);
            if (statments != -1) {
                System.out.println("in if");
                flag = true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(flag);
        return flag;
    }

    @Override
    public GroupChatMessage getGroupChatMessage(int groupChatMessageId) {
        Connection connection = DBConnection.getInstance().getConnection();

        GroupChatMessage groupChatMessage;
        int groupChatMessageID = 0;
        int userId = 0;
        int groupChatID = 0;
        String content = null;

        Timestamp timestamp = null;
        LocalDateTime creation_time_stamp;
        try {

            PreparedStatement statement = connection.prepareStatement("select * from GROUP_CHAT_MESSAGE  where GROUP_CHAT_MESSAGE_ID=?");
            statement.setInt(1, groupChatMessageId);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("before if ");
            while (resultSet.next()) {
                groupChatMessageID = resultSet.getInt(1);
                userId = resultSet.getInt(2);
                groupChatID = resultSet.getInt(3);
                content = resultSet.getString(4);
                timestamp = resultSet.getTimestamp(5);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert timestamp != null;
        creation_time_stamp = timestamp.toLocalDateTime();
        groupChatMessage = new GroupChatMessage(groupChatMessageID, userId, groupChatID, content, creation_time_stamp);

        return groupChatMessage;
    }

    @Override
    public List<SeenByStatus> getSeenByStatus(int groupChatMessageId) {
        List<SeenByStatus> list = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try {

            PreparedStatement statement = connection.prepareStatement("select * from GROUP_CHAT_MESSAGE  where GROUP_CHAT_MESSAGE_ID=?");
            statement.setInt(1, groupChatMessageId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int seenByStatusId = resultSet.getInt(1);
                int groupMessageId = resultSet.getInt(2);
                int userId = resultSet.getInt(3);

                SeenByStatus seen = new SeenByStatus(seenByStatusId, groupMessageId, userId);
                list.add(seen);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateGroupChatMessage(GroupChatMessage groupChatMessage) {
        Connection connection = DBConnection.getInstance().getConnection();
        boolean flag = false;
        try {
            String sql = "UPDATE GROUP_CHAT_MESSAGE SET USER_ID=? ,CONTENT=?, MESSAGE_TIMESTAMP=? where  GROUP_CHAT_MESSAGE_ID=?";

            PreparedStatement statment = connection.prepareStatement(sql);
            statment.setInt(1, groupChatMessage.getUserId());
            statment.setString(2, groupChatMessage.getContent());
            statment.setTimestamp(3, Timestamp.valueOf(groupChatMessage.getMessageTimestamp()));
            int row = statment.executeUpdate();
            System.out.println("Database updated successfully");
            if (row != 0) {
                flag = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteGroupChatMessage(int groupChatMessageId) {
        Connection connection = DBConnection.getInstance().getConnection();

        boolean flag = false;
        try {

            String sql = "Delete from GROUP_CHAT_MESSAGE where GROUP_CHAT_MESSAGE_ID=? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            int row = statement.executeUpdate();
            if (row != 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}

