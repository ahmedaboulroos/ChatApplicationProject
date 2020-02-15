package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {
    static UserDaoImpl userDao;
    static Connection connection;
    private static ArrayList<String> testFileNames = new ArrayList<>();
    private String testFilesDirectoryPath = "TestResources/JavaConversionTestFiles";


    @org.junit.jupiter.api.BeforeAll
    static void setUp() throws SQLException, RemoteException {
        testFileNames.add("resources/UserDaoImplTests");
        DBConnection.getInstance().initConnection();
        connection = DBConnection.getInstance().getConnection();
        userDao = new UserDaoImpl();
    }

    @org.junit.jupiter.api.AfterAll
    static void tearDown() {
        DBConnection.getInstance().stopConnection();
    }

    @org.junit.jupiter.api.Test
    void testCreateUser() throws SQLException {
        int lastPhoneNumberUsed = 111;
        int lastPasswordUsed = 11;
        User user;

        user = new User("0" + ++lastPhoneNumberUsed, "0" + ++lastPasswordUsed);
        assertTrue(userDao.createUser(user));
        System.out.println(user);

        user = new User(0, "0" + ++lastPhoneNumberUsed, "ahmed", "ahmed1",
                "222@def.com", null, null, null, null,
                null, null, false);
        assertTrue(userDao.createUser(user));

        user = new User(0, "0" + ++lastPhoneNumberUsed, "alaa", "ahmed1",
                "222@def.com", null, null, null, null,
                null, null, false);
        assertTrue(userDao.createUser(user));

        user = new User(0, "0" + ++lastPhoneNumberUsed, "alaa", "alaa1",
                "222@def.com", "Egypt", null, LocalDate.of(1996, 2, 3), null,
                null, UserStatus.AWAY, false);
        assertTrue(userDao.createUser(user));

        user = new User(0, "0" + ++lastPhoneNumberUsed, "alaa", "alaa1",
                null, "Egypt", "go away", LocalDate.of(1996, 2, 3), null,
                null, UserStatus.BUSY, true);
        assertTrue(userDao.createUser(user));

        PreparedStatement preparedStatement = connection.prepareStatement("delete from APP_USER" +
                " where PHONE_NUMBER = ?");
        for (int i = 0; i < 5; i++) {
            preparedStatement.setString(1, "0" + lastPhoneNumberUsed--);
            preparedStatement.executeUpdate();
        }
    }

    @org.junit.jupiter.api.Test
    void testGetUserByPhoneNumber() throws SQLException {
        //delete user from database
        PreparedStatement preparedStatement = connection.prepareStatement("delete from APP_USER" +
                " where PHONE_NUMBER = 345");
        preparedStatement.executeUpdate();
        //create user by phone number
        User user = new User(0, "345", "ahmed", "ahmed1",
                "222@def.com", null, null, null, null,
                null, null, false);
        userDao.createUser(user);
        String expectedUserString = user.toString();
        //get user id
        preparedStatement = connection.prepareStatement("select " +
                "SEQ_USER_ID.currval from DUAL");
        ResultSet resultSet = preparedStatement.executeQuery();
        assertTrue(resultSet.next());
        int userId = resultSet.getInt(1);
        //get User
        user = userDao.getUser("345");
        assertArrayEquals(user.toString().toCharArray(), expectedUserString.toCharArray());
    }

    @org.junit.jupiter.api.Test
    void testGetUserByPhoneNumberAndPassword() {

    }

    @org.junit.jupiter.api.Test
    void testGetUserById() {
    }

    @org.junit.jupiter.api.Test
    void testGetAllUsers() {
    }

    @org.junit.jupiter.api.Test
    void testGetUserRelationships() {
    }

    @org.junit.jupiter.api.Test
    void testGetUserSingleChats() {
    }

    @org.junit.jupiter.api.Test
    void testGetUserGroupChatsMembership() {
    }

    @org.junit.jupiter.api.Test
    void testGetUserGroupChats() {
    }

    @org.junit.jupiter.api.Test
    void testGetUserAnnouncementDeliveries() {
    }

    @org.junit.jupiter.api.Test
    void testGetUserGroups() {
    }

    @org.junit.jupiter.api.Test
    void testUpdateUser() {

    }

    @org.junit.jupiter.api.Test
    void testDeleteUser() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from APP_USER" +
                " where PHONE_NUMBER = 345");
        preparedStatement.executeUpdate();
        //create user by phone number
        User user = new User(0, "345", "ahmed", "ahmed1",
                "222@def.com", null, null, null, null,
                null, null, false);
        userDao.createUser(user);
        String expectedUserString = user.toString();
        //get user id
        preparedStatement = connection.prepareStatement("select " +
                "SEQ_USER_ID.currval from DUAL");
        ResultSet resultSet = preparedStatement.executeQuery();
        assertTrue(resultSet.next());
        int userId = resultSet.getInt(1);

        assertTrue(userDao.deleteUser(userId));
        assertNull(userDao.getUser(userId));
    }
}


