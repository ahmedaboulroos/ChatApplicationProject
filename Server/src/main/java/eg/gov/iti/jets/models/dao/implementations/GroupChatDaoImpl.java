package eg.gov.iti.jets.models.dao.implementations;

// TODO revisit for possible errors

import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMembership;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GroupChatDaoImpl extends UnicastRemoteObject implements GroupChatDao {

    private static GroupChatDaoImpl instance;
    private static Connection dbConnection;

    protected GroupChatDaoImpl() throws RemoteException {
    }

    public static GroupChatDao getInstance(Connection connection) {
        if (instance == null) {
            try {
                instance = new GroupChatDaoImpl();
                dbConnection = connection;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public int createGroupChat(GroupChat groupChat) {
        int id = -1;
        String[] key = {"ID"};
        String sql = "INSERT INTO group_chats (id, title, description, group_image, creation_date_time) VALUES (ID_SEQ.nextval,?,?,?,?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, key)) {
            ps.setString(1, groupChat.getTitle());
            ps.setString(2, groupChat.getDescription());
            InputStream in = new ByteArrayInputStream(groupChat.getGroupImageBytes());
            ps.setBinaryStream(3, in, groupChat.getGroupImageBytes().length);
            ps.setTimestamp(4, Timestamp.valueOf(groupChat.getCreationDateTime()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public GroupChat getGroupChat(int groupChatId) {
        GroupChat groupChat = null;
        String sql = "select id, title, description, group_image, creation_date_time from group_chats where id=?";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, groupChatId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                groupChat = new GroupChat(rs.getInt(1), rs.getString(2), rs.getString(3), ImageUtiles.fromBlobToBytes(rs.getBlob(4)), rs.getTimestamp(5).toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return groupChat;
    }

    @Override
    public List<GroupChatMembership> getGroupChatMemberships(int groupChatId) {
        List<GroupChatMembership> groupChatMembershipList = new ArrayList<>();
        ResultSet rs = null;
        int membership_id = 0;
        int group_chat_id = 0;
        int user_id = 0;
        LocalDateTime joined_timestamp;
        Timestamp timestamp = null;
        PreparedStatement stmt = null;
        try {
            String sql = "select GROUP_CHAT_MEMBERSHIPS.id, GROUP_CHAT_MEMBERSHIPS.user_id, GROUP_CHAT_MEMBERSHIPS.group_chat_id, GROUP_CHAT_MEMBERSHIPS.JOINED_DATE_TIME from GROUP_CHAT_MEMBERSHIPS where GROUP_CHAT_MEMBERSHIPS.group_chat_id=?";
            stmt = dbConnection.prepareStatement(sql);
            stmt.setInt(1, groupChatId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                membership_id = rs.getInt("id");
                group_chat_id = rs.getInt("group_chat_id");
                user_id = rs.getInt("user_id");
                timestamp = rs.getTimestamp("joined_date_time");
                joined_timestamp = timestamp.toLocalDateTime();
                groupChatMembershipList.add(new GroupChatMembership(membership_id, user_id, group_chat_id, joined_timestamp));
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
        return groupChatMembershipList;
    }

    @Override
    public List<User> getGroupChatUsers(int groupChatId) {
        List<GroupChatMembership> groupChatMembershipList = getGroupChatMemberships(groupChatId);
        List<User> userList = new ArrayList<>();
        String sql = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        for (int i = 0; i < groupChatMembershipList.size(); i++) {
            sql = "USER_ID, PHONE_NUMBER, USERNAME, PASSWORD, EMAIL, COUNTRY, BIO, BIRTH_DATE, USER_GENDER, PROFILE_IMAGE, USER_STATUS, CURRENTLY_LOGGED_IN from APP_USER where USER_ID=?";
            int userId = groupChatMembershipList.get(i).getUserId();
            try (PreparedStatement preparedStatement = stmt = dbConnection.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                resultSet = stmt.executeQuery();
                User user = getUserFromResultSet(resultSet);
                userList.add(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }

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
            stmt = dbConnection.prepareStatement(sql);
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
    public void updateGroupChat(GroupChat groupChat) {
        String sql = "update GROUP_CHATS set  title=?, description=?, group_image=?, creation_date_time=? where id=? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setString(1, groupChat.getTitle());
            ps.setString(2, groupChat.getDescription());
            ps.setBlob(3, ImageUtiles.fromBytesToBlob(groupChat.getGroupImageBytes()));
            ps.setTimestamp(4, Timestamp.valueOf(groupChat.getCreationDateTime()));
            ps.setInt(5, groupChat.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGroupChat(int groupChatId) {
        String sql = "delete from GROUP_CHATS where ID=?";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setInt(1, groupChatId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = null;
        if (resultSet.next()) {
            user = getUserFromRecord(resultSet);
        }
        return user;
    }

    private User getUserFromRecord(ResultSet resultSet) throws SQLException {
        LocalDate birthDate = getLocalDateFromDate(resultSet.getDate("birth_date"));
        UserGender userGender = getUserGenderFromString(resultSet.getString("user_gender"));
        byte[] profileImage = ImageUtiles.fromBlobToBytes(resultSet.getBlob("profile_image"));
        UserStatus userStatus = getUserStatusFromString(resultSet.getString("user_status"));
        return new User(resultSet.getInt("user_id"),
                resultSet.getString("phone_number"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getString("country"),
                resultSet.getString("bio"),
                birthDate,
                userGender,
                //TODO: handle receiving images from database (convert to image)
                profileImage,
                userStatus);
    }

    private UserStatus getUserStatusFromString(String string) {
        return string == null ? null : UserStatus.valueOf(string);
    }

    private UserGender getUserGenderFromString(String string) {
        return string == null ? null : UserGender.valueOf(string);
    }

    private LocalDate getLocalDateFromDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }


}
