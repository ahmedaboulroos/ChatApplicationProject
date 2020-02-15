package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDaoImplTest {
    static UserDaoImpl userDao;
    static Connection connection;
    private static ArrayList<String> testFileNames = new ArrayList<>();
    private String testFilesDirectoryPath = "TestResources/JavaConversionTestFiles";
    private int lastPhoneNumberUsed = 145;
    private int lastPasswordUsed = 556;

    @org.junit.jupiter.api.BeforeAll
    static void setUp() throws SQLException, RemoteException {
        testFileNames.add("resources/UserDaoImplTests");
        DBConnection.getInstance().initConnection();
        connection = DBConnection.getInstance().getConnection();
        /*PreparedStatement preparedStatement =
                        connection.prepareStatement("insert into APP_USER" +
                                " (USER_ID, PHONE_NUMBER, USERNAME, PASSWORD, EMAIL," +
                                " COUNTRY, BIO, BIRTH_DATE, USER_GENDER, USER_STATUS, CURRENTLY_LOGGED_IN)" +
                                " VALUES (SEQ_USER_ID.nextval,?,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1, "1234");
        preparedStatement.setString(2, "tunafish");
        preparedStatement.setString(3, "tunella");
        preparedStatement.setString(4, "123@def.com");
        preparedStatement.setString(5, "Egypt");
        preparedStatement.setString(6, "on fire");
        preparedStatement.setDate(7, birthDate);
        preparedStatement.setString(9, userGender);
*/
        userDao = new UserDaoImpl();
    }

    @org.junit.jupiter.api.AfterAll
    static void tearDown() {
        DBConnection.getInstance().stopConnection();
    }

    @org.junit.jupiter.api.Test
    void testCreateUser() throws SQLException {
        int userId;
        User user;
//        PreparedStatement preparedStatement;

        user = new User("0" + ++lastPhoneNumberUsed, "0" + ++lastPasswordUsed);
        assertTrue(userDao.createUser(user));

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

    }

    @org.junit.jupiter.api.Test
    void testGetUserBy() {

    }

    @org.junit.jupiter.api.Test
    void testGetUser() {
    }

    @org.junit.jupiter.api.Test
    void testGetUser1() {
    }

    @org.junit.jupiter.api.Test
    void getAllUsers() {
    }

    @org.junit.jupiter.api.Test
    void getUserRelationships() {
    }

    @org.junit.jupiter.api.Test
    void getUserSingleChats() {
    }

    @org.junit.jupiter.api.Test
    void getUserGroupChatsMembership() {
    }

    @org.junit.jupiter.api.Test
    void getUserGroupChats() {
    }

    @org.junit.jupiter.api.Test
    void getUserAnnouncementDeliveries() {
    }

    @org.junit.jupiter.api.Test
    void getUserGroups() {
    }

    @org.junit.jupiter.api.Test
    void updateUser() {
    }

    @org.junit.jupiter.api.Test
    void deleteUser() {
    }
}