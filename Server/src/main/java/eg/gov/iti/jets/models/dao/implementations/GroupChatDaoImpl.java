package eg.gov.iti.jets.models.dao.implementations;


import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.Membership;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.persistence.DBConnection;

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

    private Connection connection = DBConnection.getConnection();
    private static GroupChatDaoImpl instance;

    protected GroupChatDaoImpl() throws RemoteException {
    }

    @Override
    public int createGroupChat(GroupChat groupChat) {

        int groupChatId = 0;
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("select SEQ_GROUP_CHAT_ID.nextval from DUAL");

            String groupChatTitle = groupChat.getTitle() == null ? "dummy Title" : groupChat.getTitle();
            String groupChatDescription = groupChat.getDescription() == null ? "default Groupchat Description" : groupChat.getDescription();
            InputStream imageInputStream;
            int imageByteArrayLength;
            if (groupChat.getGroupImageBytes().length != 0) {
                imageByteArrayLength = groupChat.getGroupImageBytes().length;
                imageInputStream = new ByteArrayInputStream(groupChat.getGroupImageBytes());

            } else {
                byte[] imageByteArray = ImageUtiles.fromImageToBytes("C:\\Users\\elnaggar\\IdeaProjects\\ChatApplicationProject\\Server\\src\\main\\resources\\images\\groupChatDefaultImage.jpg");
                imageInputStream = new ByteArrayInputStream(imageByteArray);
                imageByteArrayLength = imageByteArray.length;
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                groupChatId = resultSet.getInt(1);
                groupChat.setGroupChatId(groupChatId);
                System.out.println("inside group chatdao imple" + groupChatId);
                System.out.println("inside group chatdao imple" + groupChat.getGroupChatId());

                String sql = "INSERT INTO group_chat (group_chat_id, title, description,group_image,creation_timestamp) VALUES (?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, groupChatId);
                preparedStatement.setString(2, groupChatTitle);
                preparedStatement.setString(3, groupChatDescription);
                preparedStatement.setBinaryStream(4, imageInputStream, imageByteArrayLength);
                preparedStatement.setTimestamp(5, groupChat.getCreationTimestamp());
                preparedStatement.executeUpdate();

                resultSet.close();
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupChatId;
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
            byte[] imageAsBytes = ImageUtiles.fromBlobToBytes(blob);

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
        List<Membership> membershipList = getGroupChatMemberships(groupChatId);
        List<User> userList = new ArrayList<>();
        String sql = null;
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        for (int i = 0; i < membershipList.size(); i++) {
            sql = "USER_ID, PHONE_NUMBER, USERNAME, PASSWORD, EMAIL, COUNTRY, BIO, BIRTH_DATE, USER_GENDER, PROFILE_IMAGE, USER_STATUS, CURRENTLY_LOGGED_IN from APP_USER where USER_ID=?";
            int userId = membershipList.get(i).getUserId();
            try (PreparedStatement preparedStatement = stmt = connection.prepareStatement(sql)) {
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
            stmt.setBlob(3, ImageUtiles.fromBytesToBlob(groupChat.getGroupImageBytes()));
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
        boolean currentlyLoggedIn = getCurrentlyLoggedInFromString(resultSet.getString("currently_logged_in"));
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
                userStatus,
                currentlyLoggedIn);
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

    private boolean getCurrentlyLoggedInFromString(String string) {
        return string != null && string.equals("Online");
    }

}
