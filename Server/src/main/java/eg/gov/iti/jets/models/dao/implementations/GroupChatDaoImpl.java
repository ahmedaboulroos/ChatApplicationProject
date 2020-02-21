package eg.gov.iti.jets.models.dao.implementations;


import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.Membership;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroupChatDaoImpl extends UnicastRemoteObject implements GroupChatDao {

    private Connection connection = DBConnection.getConnection();
    private static GroupChatDaoImpl instance;

    protected GroupChatDaoImpl() throws RemoteException {
    }

    public static GroupChatDao getInstance() {
        if (instance == null) {
            try {
                instance = new GroupChatDaoImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public boolean createGroupChat(GroupChat groupChat) {

        boolean b = false;
        PreparedStatement stmt = null;
        try {
//            BLOB blob = BLOB.createTemporary(connection, false, BLOB.DURATION_SESSION);
//            blob.setBytes();
            String sql = "INSERT INTO group_chat (group_chat_id, title, description,group_image,creation_timestamp) VALUES (SEQ_GROUP_CHAT_ID.nextval,?,?,?,?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, groupChat.getTitle());
            stmt.setString(2, groupChat.getDescription());

            System.out.println(groupChat.getGroupImageBytes());
            System.out.println(groupChat.getGroupImageBytes().length);
            InputStream in = new ByteArrayInputStream(groupChat.getGroupImageBytes());
            stmt.setBinaryStream(3, in, groupChat.getGroupImageBytes().length);

            // stmt.setBlob(3, ImageUtiles.FromBytesToBlob(groupChat.getGroupImageBytes()));
            stmt.setTimestamp(4, groupChat.getCreationTimestamp());

            if (stmt.executeUpdate() != 0) {
                b = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    @Override
    public GroupChat getGroupChat(int groupChatId) {
        ResultSet rs = null;
        int id = 0;
        String tilte = null;
        String description = null;
        Timestamp timestamp = null;
        Blob blob = null;
        GroupChat groupChat = null;
        PreparedStatement stmt = null;
        try {
            String sql = "select group_chat_id, title, description, group_image, creation_timestamp from group_chat where group_chat_id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, groupChatId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("group_chat_id");
                tilte = rs.getString("title");
                description = rs.getString("description");
                blob = rs.getBlob("group_image");
                timestamp = rs.getTimestamp("creation_timestamp");
            }
            byte[] imageAsBytes = ImageUtiles.FromBlobToBytes(blob);

            groupChat = new GroupChat(id, tilte, description, imageAsBytes, timestamp);

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return groupChat;
    }

    @Override
    public List<Membership> getGroupChatMemberships(int groupChatId) {
        String sql = "select membership_id, user_id, group_chat_id, joined_timestamp from membership where group_chat_id=?";
        List<Membership> membershipList = new ArrayList<>();
        ResultSet rs = null;
        int membership_id = 0;
        int group_chat_id = 0;
        int user_id = 0;
        LocalDateTime joined_timestamp;
        Timestamp timestamp = null;
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, groupChatId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                membership_id = rs.getInt("membership_id");
                group_chat_id = rs.getInt("group_chat_id");
                user_id = rs.getInt("user_id");
                timestamp = rs.getTimestamp("joined_timestamp");
                joined_timestamp = timestamp.toLocalDateTime();
                membershipList.add(new Membership(membership_id, user_id, group_chat_id, joined_timestamp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return membershipList;
    }

    @Override
    public List<User> getGroupChatUsers(int groupChatId) {
        return null;
    }//m4 fahma

    @Override
    public List<GroupChatMessage> getGroupChatMessages(int groupChatId) {
        String sql = "select group_chat_message_id, user_id, content, message_timestamp from group_Chat_Message where group_chat_id=?";
        List<GroupChatMessage> groupChatMessageList = new ArrayList<>();
        ResultSet rs = null;
        int membership_id = 0;
        int group_chat_id = 0;
        int user_id = 0;
        LocalDateTime joined_timestamp;
        Timestamp timestamp = null;
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, groupChatId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                membership_id = rs.getInt("membership_id");
                group_chat_id = rs.getInt("group_chat_id");
                user_id = rs.getInt("user_id");
                timestamp = rs.getTimestamp("joined_timestamp");
                joined_timestamp = timestamp.toLocalDateTime();
                //groupChatMessageList.add(new Membership(membership_id, user_id, group_chat_id, joined_timestamp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupChatMessageList;
    }

    @Override
    public boolean updateGroupChat(GroupChat groupChat) {
        boolean b = false;
        PreparedStatement stmt = null;
        try {
            String sql = "update GROUP_CHAT set  title=?, description=?, group_image=?, creation_timestamp=? where group_chat_id=? ";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, groupChat.getTitle());
            stmt.setString(2, groupChat.getDescription());
            stmt.setBlob(3, ImageUtiles.FromBytesToBlob(groupChat.getGroupImageBytes()));
            stmt.setTimestamp(4, groupChat.getCreationTimestamp());
            stmt.setInt(5, groupChat.getGroupChatId());
            if (stmt.executeUpdate() != 0) {
                b = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return b;
    }

    @Override
    public boolean deleteGroupChat(int groupChatId) {

        boolean b = false;
        PreparedStatement stmt = null;
        try {
            String sql = "delete from GROUP_CHAT where group_chat_id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, groupChatId);
            if (stmt.executeUpdate() != 0) {
                b = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return b;
    }

}
