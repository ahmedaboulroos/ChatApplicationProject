package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.*;
import eg.gov.iti.jets.models.entities.enums.AnnouncementDeliveryStatus;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends UnicastRemoteObject implements UserDao {

    private Connection connection = DBConnection.getConnection();

    private static UserDaoImpl instance;

    public UserDaoImpl() throws RemoteException {
    }

    public static UserDao getInstance() {
        if (instance == null) {
            try {
                instance = new UserDaoImpl();
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
            //InputStream inputStream = getInputStreamFromImage(user.getProfileImage());
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
                                " values (?,?,?,?,?,?,?,?,?,?,?)");
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, user.getPhoneNumber());
                preparedStatement.setString(3, user.getUsername());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setString(5, user.getEmail());
                preparedStatement.setString(6, user.getCountry());
                preparedStatement.setString(7, user.getBio());
                preparedStatement.setDate(8, birthDate);
                preparedStatement.setString(9, userGender);
                //TODO: handle sending images to database (convert to blob)
                //preparedStatement.setBlob(10, null);
                preparedStatement.setString(10, userStatus);
                preparedStatement.setString(11, currentlyLoggedIn);
                result = preparedStatement.executeUpdate();
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public User getUser(int userId) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from APP_USER where USER_ID = " + userId);
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
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from APP_USER where PHONE_NUMBER = '" + phoneNumber + "'");
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
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from APP_USER where PHONE_NUMBER = '" + phoneNumber + "' and PASSWORD = '" + password + "'");
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
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from APP_USER");
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
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from RELATIONSHIP where FIRST_USER_ID = " + userId
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
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from SINGLE_CHAT where USER_ONE_ID = '" + userId
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
    public List<Membership> getUserGroupChatsMembership(int userId) {

        String sql = "select membership_id, user_id, group_chat_id, joined_timestamp from membership where user_id=?";
        List<Membership> membershipList = new ArrayList<>();
        ResultSet rs = null;
        int membership_id = 0;
        int group_chat_id = 0;
        int user_id = 0;
        LocalDateTime joined_timestamp = null;
        Timestamp timestamp = null;
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                membership_id = rs.getInt("membership_id");
                group_chat_id = rs.getInt("group_chat_id");
                user_id = rs.getInt("user_id");
                timestamp = rs.getTimestamp("joined_timestamp");
                if (timestamp != null)
                    joined_timestamp = timestamp.toLocalDateTime();
                membershipList.add(new Membership(membership_id, user_id, group_chat_id, joined_timestamp));
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
        return membershipList;
    }

    @Override
    public List<GroupChat> getUserGroupChats(int userId) {
        List<Membership> membershipList = getUserGroupChatsMembership(userId);
        List<GroupChat> groupChatList = new ArrayList<>();
        // int groupChatId=0;
        String sql = null;
        ResultSet rs = null;
        int id = 0;
        String tilte = null;
        String description = null;
        String group_image;
        LocalDateTime creation_time_stamp;
        Timestamp timestamp = null;
        InputStream in;
        Blob blob = null;
        BufferedImage imagen;
        PreparedStatement stmt = null;
        for (int i = 0; i < membershipList.size(); i++) {
            sql = "select group_chat_id, title, description, group_image, creation_timestamp from GROUP_CHAT where group_chat_id=?";
            int groupChatId = membershipList.get(i).getGroupChatId();
            try (PreparedStatement preparedStatement = stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, groupChatId);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("group_chat_id");
                    tilte = rs.getString("title");
                    description = rs.getString("description");
                    blob = rs.getBlob("group_image");
                    timestamp = rs.getTimestamp("creation_timestamp");
                }
                if (blob != null) {
                    group_image = ImageUtiles.FromBlobToString(blob);
                } else
                    group_image = null;

                if (timestamp != null) {
                    creation_time_stamp = timestamp.toLocalDateTime();
                } else
                    creation_time_stamp = null;
                groupChatList.add(new GroupChat(id, tilte, description, group_image, creation_time_stamp));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
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
                            " CURRENTLY_LOGGED_IN = ?" +
                            //TODO: handle sending images to database (convert to blob)
                            //", PROFILE_IMAGE = ?" +
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
            /*preparedStatement.setBinaryStream(11, (InputStream) inputStream,
                    (int)(inputStream.length()));*/
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
    public boolean deleteUser(int userId) {
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from APP_USER where USER_ID = " + userId);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
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
        LocalDate birthDate = getLocalDateFromDate(resultSet.getDate(8));
        UserGender userGender = getUserGenderFromString(resultSet.getString(9));
        //UserGender userGender = UserGender.valueOf(resultSet.getString(9));
        Image profileImage = getImageFromBlob(resultSet.getBlob(10));
        UserStatus userStatus = getUserStatusFromString(resultSet.getString(11));
        boolean currentlyLoggedIn = getCurrentlyLoggedInFromString(resultSet.getString(12));
        return new User(resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7),
                birthDate,
                userGender,
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

    private Image getImageFromBlob(Blob blob) {
        if (blob != null) {
            try {
                InputStream in = blob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(in);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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

    private List<Group> getGroupsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Group> groups = new ArrayList<>();
        Group group;
        while (resultSet.next()) {
            group = new Group(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getString(3));
            groups.add(group);
        }
        if (!groups.isEmpty())
            return groups;
        else
            return null;
    }

    private List<GroupContact> getGroupContactsFromResultSet(ResultSet resultSet) throws SQLException {
        List<GroupContact> groupContacts = new ArrayList<>();
        GroupContact groupContact;
        while (resultSet.next()) {
            groupContact = new GroupContact(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3));
            groupContacts.add(groupContact);
        }
        if (!groupContacts.isEmpty())
            return groupContacts;
        else
            return null;
    }

    private List<AnnouncementDelivery> getAnnouncementDeliveriesFromResultSet(ResultSet resultSet) throws SQLException {
        List<AnnouncementDelivery> announcementDeliveries = new ArrayList<>();
        AnnouncementDelivery announcementDelivery;
        while (resultSet.next()) {
            AnnouncementDeliveryStatus announcementDeliveryStatus =
                    AnnouncementDeliveryStatus.valueOf(resultSet.getString(4));
            announcementDelivery = new AnnouncementDelivery(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    announcementDeliveryStatus);
            announcementDeliveries.add(announcementDelivery);
        }
        if (!announcementDeliveries.isEmpty())
            return announcementDeliveries;
        else
            return null;
    }

}
