package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.*;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.implementations.ServerService;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends UnicastRemoteObject implements UserDao {

    private static UserDaoImpl instance;
    private static Connection dbConnection;
    private File defaultImage;
    private Connection connection = DBConnection.getConnection();

    protected UserDaoImpl() throws RemoteException {
    }

    public static UserDao getInstance(Connection connection) {
        if (instance == null) {
            try {
                instance = new UserDaoImpl();
                dbConnection = connection;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public int createUser(User user) {
        Date birthDate = user.getBirthDate() == null ? null : Date.valueOf(user.getBirthDate());
        String userGender = user.getUserGender() == null ? null : user.getUserGender().toString();

        InputStream in = new ByteArrayInputStream(user.getProfileImage());

        String userStatus = user.getUserStatus() == null ? null : user.getUserStatus().toString();
        int id = -1;
        String key[] = {"ID"};
        String sql = "insert into USERS (id, phone_number, username, password, email, country, bio, birth_date, user_gender,profile_image, user_status) values (ID_SEQ.nextval,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, key)) {
            ps.setString(1, user.getPhoneNumber());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getCountry());
            ps.setString(6, user.getBio());
            ps.setDate(7, birthDate);
            ps.setString(8, userGender);
            ps.setBinaryStream(9, in, user.getProfileImage().length);
            ps.setString(10, userStatus);
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
    public User getUser(int userId) {
        User user = null;
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(
                    "select * from USERS where ID = " + userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getUserFromResultSet(resultSet);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUser(String phoneNumber) {
        User user = null;
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(
                    "select * from USERS where PHONE_NUMBER = '" + phoneNumber + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getUserFromResultSet(resultSet);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUser(String phoneNumber, String password) {
        User user = null;
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(
                    "select * from USERS where PHONE_NUMBER = '" + phoneNumber + "' and PASSWORD = '" + password + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getUserFromResultSet(resultSet);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(
                    "select * from USERS");
            ResultSet resultSet = preparedStatement.executeQuery();
            users = getUsersListFromResultSet(resultSet);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<Relationship> getUserRelationships(int userId) {
        List<Relationship> relationships = null;
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(
                    "select * from RELATIONSHIPS where FIRST_USER_ID = " + userId
                            + " or SECOND_USER_ID = " + userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            relationships = getRelationshipsFromResultSet(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relationships;
    }

    @Override
    public List<SingleChat> getUserSingleChats(int userId) {
        List<SingleChat> singleChats = null;
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(
                    "select * from SINGLE_CHATS where USER_ONE_ID = '" + userId
                            + "' or USER_TWO_ID = '" + userId + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            singleChats = getSingleChatsFromResultSet(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return singleChats;
    }

    @Override
    public List<GroupChatMembership> getUserGroupChatsMembership(int userId) {

        String sql = "select id, user_id, group_chat_id, joined_Date_time from group_chat_memberships where user_id=?";
        List<GroupChatMembership> groupChatMembershipList = new ArrayList<>();
        ResultSet rs = null;
        int membership_id = 0;
        int group_chat_id = 0;
        int user_id = 0;
        LocalDateTime joined_timestamp = null;
        Timestamp timestamp = null;
        PreparedStatement stmt = null;
        try {
            stmt = dbConnection.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                membership_id = rs.getInt("id");
                group_chat_id = rs.getInt("group_chat_id");
                user_id = rs.getInt("user_id");
                timestamp = rs.getTimestamp("joined_date_time");
                if (timestamp != null)
                    joined_timestamp = timestamp.toLocalDateTime();
                groupChatMembershipList.add(new GroupChatMembership(membership_id, user_id, group_chat_id, joined_timestamp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return groupChatMembershipList;
    }

    @Override
    public List<GroupChat> getUserGroupChats(int userId) {
        List<GroupChatMembership> groupChatMembershipList = getUserGroupChatsMembership(userId);
        List<GroupChat> groupChatList = new ArrayList<>();
        // int groupChatId=0;
        String sql = null;
        ResultSet rs = null;
        int id = 0;
        String tilte = null;
        String description = null;
        byte[] group_image;
        Timestamp timestamp = null;
        InputStream in;
        Blob blob = null;
        BufferedImage imagen;
        PreparedStatement stmt = null;
        for (int i = 0; i < groupChatMembershipList.size(); i++) {
            sql = "select id, title, description, group_image, creation_date_time from GROUP_CHATS where id=?";
            int groupChatId = groupChatMembershipList.get(i).getGroupChatId();
            try (PreparedStatement preparedStatement = stmt = dbConnection.prepareStatement(sql)) {
                stmt.setInt(1, groupChatId);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("id");
                    tilte = rs.getString("title");
                    description = rs.getString("description");
                    blob = rs.getBlob("group_image");
                    timestamp = rs.getTimestamp("creation_date_time");
                }
                if (blob != null) {
                    group_image = ImageUtiles.fromBlobToBytes(blob);
                } else
                    group_image = null;

                groupChatList.add(new GroupChat(id, tilte, description, group_image, timestamp.toLocalDateTime()));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return groupChatList;
    }//alaa

    @Override
    public List<ContactsGroup> getUserContactsGroups(int userId) {
        List<ContactsGroup> contactsGroups = null;
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(
                    "select * from CONTACTS_GROUPS where USER_ID = " + userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            contactsGroups = getGroupsFromResultSet(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsGroups;
    }

    @Override
    public List<ContactsGroupMembership> getUserContactsGroupMemberships(int userId) throws RemoteException {
        List<ContactsGroupMembership> contactsGroupMemberships = null;
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(
                    "select * from CONTACTS_GROUP_MEMBERSHIPS where USER_ID = " + userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            contactsGroupMemberships = getGroupContactsFromResultSet(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsGroupMemberships;
    }

    @Override
    public void updateUser(User user) {
        Date birthDate = user.getBirthDate() == null ? null : Date.valueOf(user.getBirthDate());
        String userGender = user.getUserGender() == null ? null : user.getUserGender().toString();
        String sql = "update USERS set PHONE_NUMBER = ?, USERNAME = ?, PASSWORD = ?, EMAIL = ?, COUNTRY = ?, BIO = ?, BIRTH_DATE = ?, USER_GENDER = ?, USER_STATUS = ?, PROFILE_IMAGE = ? where ID = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getCountry());
            preparedStatement.setString(6, user.getBio());
            preparedStatement.setDate(7, birthDate);
            preparedStatement.setString(8, userGender);
            preparedStatement.setString(9, user.getUserStatus().toString());
            preparedStatement.setBlob(10, ImageUtiles.fromBytesToBlob(user.getProfileImage()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserStatus(int userId, UserStatus userStatus) throws RemoteException {
        String sql = "UPDATE USERS SET USER_STATUS = ? WHERE ID = ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setString(1, userStatus.toString());
            ps.setInt(2, userId);
            ps.executeUpdate();
            List<Relationship> userRelationships = getUserRelationships(userId);
            if (userRelationships != null) {
                for (Relationship r : userRelationships) {
                    if (r.getFirstUserId() == userId) {
                        ClientInterface client = ServerService.getClient(r.getSecondUserId());
                        if (client != null) {
                            client.receiveUserStatusChanged(userId);
                        }
                    } else {
                        ClientInterface client = ServerService.getClient(r.getFirstUserId());
                        if (client != null) {
                            client.receiveUserStatusChanged(userId);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private InputStream getInputStreamFromImage(Image profileImage) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(profileImage, null);
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }

    @Override
    public void deleteUser(int userId) {
        String sql = "delete from USERS where ID = ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setInt(1, userId);
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

    private List<User> getUsersListFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        User user;
        while (resultSet.next()) {
            user = getUserFromRecord(resultSet);
            users.add(user);
        }
        if (!users.isEmpty())
            return users;
        else
            return null;
    }

    private User getUserFromRecord(ResultSet resultSet) throws SQLException {
        LocalDate birthDate = getLocalDateFromDate(resultSet.getDate("birth_date"));
        UserGender userGender = getUserGenderFromString(resultSet.getString("user_gender"));
        byte[] profileImage = ImageUtiles.fromBlobToBytes(resultSet.getBlob("profile_image"));
        UserStatus userStatus = getUserStatusFromString(resultSet.getString("user_status"));
        return new User(resultSet.getInt("id"),
                resultSet.getString("phone_number"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getString("country"),
                resultSet.getString("bio"),
                birthDate,
                userGender,
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

    private List<Relationship> getRelationshipsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Relationship> relationships = new ArrayList<>();
        Relationship relationship;
        while (resultSet.next()) {
            RelationshipStatus relationshipStatus =
                    RelationshipStatus.valueOf(resultSet.getString(4).toUpperCase());
            relationship = new Relationship(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    relationshipStatus);
            relationships.add(relationship);
        }
        if (!relationships.isEmpty())
            return relationships;
        else
            return null;
    }

    private List<SingleChat> getSingleChatsFromResultSet(ResultSet resultSet) throws SQLException {
        List<SingleChat> singleChats = new ArrayList<>();
        SingleChat singleChat;
        while (resultSet.next()) {
            singleChat = new SingleChat(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3));
            singleChats.add(singleChat);
        }
        if (!singleChats.isEmpty())
            return singleChats;
        else
            return null;
    }

    private List<ContactsGroup> getGroupsFromResultSet(ResultSet resultSet) throws SQLException {
        List<ContactsGroup> contactsGroups = new ArrayList<>();
        ContactsGroup contactsGroup;
        while (resultSet.next()) {
            contactsGroup = new ContactsGroup(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getString(3));
            contactsGroups.add(contactsGroup);
        }
        if (!contactsGroups.isEmpty())
            return contactsGroups;
        else
            return null;
    }

    private List<ContactsGroupMembership> getGroupContactsFromResultSet(ResultSet resultSet) throws SQLException {
        List<ContactsGroupMembership> contactsGroupMemberships = new ArrayList<>();
        ContactsGroupMembership contactsGroupMembership;
        while (resultSet.next()) {
            contactsGroupMembership = new ContactsGroupMembership(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3));
            contactsGroupMemberships.add(contactsGroupMembership);
        }
        if (!contactsGroupMemberships.isEmpty())
            return contactsGroupMemberships;
        else
            return null;
    }

}
