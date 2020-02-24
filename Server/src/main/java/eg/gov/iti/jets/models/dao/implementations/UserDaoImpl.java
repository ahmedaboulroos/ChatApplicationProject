package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.*;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.implementations.ServerService;
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

    private File defaultImage;
    private Connection connection = DBConnection.getConnection();

    private static UserDaoImpl instance;
    private static Connection dbConnection;

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
    public boolean createUser(User user) {
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select SEQ_USER_ID.nextval from DUAL");
            Date birthDate = user.getBirthDate() == null ?
                    null : Date.valueOf(user.getBirthDate());
            String userGender = user.getUserGender() == null ?
                    null : user.getUserGender().toString();
            byte[] profileImage = user.getProfileImage();
            String userStatus = user.getUserStatus() == null ?
                    null : user.getUserStatus().toString();
            String currentlyLoggedIn = user.isCurrentlyLoggedIn() ? "ONLINE" : "OFFLINE";
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt(1);
                user.setUserId(userId);
                preparedStatement = connection.prepareStatement(
                        "insert into APP_USER (user_id, phone_number, username, " +
                                "password, email, country, bio, birth_date, user_gender, " +
                                "user_status, currently_logged_in)" +
                                " values (?,?,?,?,?,?,?,?,?,?,?,?)");
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, user.getPhoneNumber());
                preparedStatement.setString(3, user.getUsername());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setString(5, user.getEmail());
                preparedStatement.setString(6, user.getCountry());
                preparedStatement.setString(7, user.getBio());
                preparedStatement.setDate(8, birthDate);
                preparedStatement.setString(9, userGender);
                InputStream in = new ByteArrayInputStream(profileImage);
                preparedStatement.setBinaryStream(10, in, profileImage.length);
                //preparedStatement.setBlob(10, profileImage);
                preparedStatement.setString(11, userStatus);
                preparedStatement.setString(12, currentlyLoggedIn);
                result = preparedStatement.executeUpdate();
    public int createUser(User user) {
        Date birthDate = user.getBirthDate() == null ? null : Date.valueOf(user.getBirthDate());
        String userGender = user.getUserGender() == null ? null : user.getUserGender().toString();
        Blob profileImage = ImageUtiles.fromBytesToBlob(user.getProfileImage());
        String userStatus = user.getUserStatus() == null ? null : user.getUserStatus().toString();
        int id = -1;
        String key[] = {"ID"};
        String sql = "insert into USERS (id, phone_number, username, password, email, country, bio, birth_date, user_gender, user_status) values (ID_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, key)) {
            ps.setString(1, user.getPhoneNumber());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getCountry());
            ps.setString(6, user.getBio());
            ps.setDate(7, birthDate);
            ps.setString(8, userGender);
            ps.setString(9, userStatus);
//                ps.setBlob(9, profileImage);
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
        if (!membershipList.isEmpty()) {
            for (int i = 0; i < membershipList.size(); i++) {
                sql = "select group_chat_id, title, description, group_image, creation_timestamp from GROUP_CHAT where group_chat_id=?";
                int groupChatId = membershipList.get(i).getGroupChatId();
                System.out.println("inside --->> userdaoimpl.getUserGroupChats groupChatId " + groupChatId);
                try (PreparedStatement preparedStatement = stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, groupChatId);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        id = rs.getInt("group_chat_id");
                        System.out.println("inside-->> userdoaimpl group_chat_id" + id);
                        tilte = rs.getString("title");
                        System.out.println("inside-->> userdoaimpl group_chat_title" + tilte);
                        description = rs.getString("description");
                        System.out.println("inside-->> userdoaimpl group_chat_description" + description);
                        imageBytes = rs.getBytes("group_image");
                        System.out.println("inside-->> userdoaimpl imageBytes.length" + imageBytes.length);
                        //blob = rs.getBlob("group_image");
                        timestamp = rs.getTimestamp("creation_timestamp");
                    }
//                    if (imageBytes.length==0 ) {
//                        imageBytes = ImageUtiles.fromImageToBytes("C:\\Users\\elnaggar\\IdeaProjects\\ChatApplicationProject\\Server\\src\\main\\resources\\images\\groupChatDefaultImage.png");
//                    }
                    groupChatList.add(new GroupChat(id, tilte, description, imageBytes, timestamp));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } else {
            System.out.println("inside-->> userdoaimpl usermemberShiList is empty ");
        }

        System.out.println("inside-->> userdoaimpl   groupChatList.size()" + groupChatList.size());
        return groupChatList;
    }//alaa

    @Override
    public List<AnnouncementDelivery> getUserAnnouncementDeliveries(int userId) {
        List<AnnouncementDelivery> announcementDeliveries = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from ANNOUNCEMENT_DELIVERY where USER_ID = " + userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            announcementDeliveries = getAnnouncementDeliveriesFromResultSet(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return announcementDeliveries;
    }

    @Override
    public List<Group> getUserGroups(int userId) {
        List<Group> groups = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from APP_USER_GROUP where USER_ID = " + userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            groups = getGroupsFromResultSet(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public List<GroupContact> getUserContacts(int userId) throws RemoteException {
        List<GroupContact> groupContacts = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from GROUP_CONTACT where USER_ID = " + userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            groupContacts = getGroupContactsFromResultSet(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupContacts;
    }

    @Override
    public boolean updateUser(User user) {
        int result = 0;
        try {
            Date birthDate = user.getBirthDate() == null ?
                    null : Date.valueOf(user.getBirthDate());
            String userGender = user.getUserGender() == null ?
                    null : user.getUserGender().toString();
            //InputStream inputStream = getInputStreamFromImage(user.getProfileImage());
            String userStatus = user.getUserStatus() == null ?
                    null : user.getUserStatus().toString();
            String currentlyLoggedIn = user.isCurrentlyLoggedIn() ? "ONLINE" : "OFFLINE";
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update APP_USER set" +
                            " PHONE_NUMBER = ?," +
                            " USERNAME = ?," +
                            " PASSWORD = ?," +
                            " EMAIL = ?," +
                            " COUNTRY = ?," +
                            " BIO = ?," +
                            " BIRTH_DATE = ?," +
                            " USER_GENDER = ?," +
                            " USER_STATUS = ?," +
                            " CURRENTLY_LOGGED_IN = ?," +
                            //TODO: handle sending images to database (convert to blob)
                            ", PROFILE_IMAGE = ?" +
                            " where USER_ID = " + user.getUserId());
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getCountry());
            preparedStatement.setString(6, user.getBio());
            preparedStatement.setDate(7, birthDate);
            preparedStatement.setString(8, userGender);
            preparedStatement.setString(9, userStatus);
            preparedStatement.setString(10, currentlyLoggedIn);
            preparedStatement.setBlob(11, ImageUtiles.fromBytesToBlob(user.getProfileImage()));
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
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
