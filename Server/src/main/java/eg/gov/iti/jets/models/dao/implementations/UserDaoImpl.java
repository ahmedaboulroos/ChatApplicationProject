package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.*;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class UserDaoImpl extends UnicastRemoteObject implements UserDao {

    protected UserDaoImpl() throws RemoteException {
    }

    public static void main(String[] args) throws RemoteException {
        UserDaoImpl userDao = new UserDaoImpl();
        DBConnection.getInstance().initConnection();
        //System.out.println(userDao.createUser(new User("0123","555")));
        //System.out.println(userDao.getUser("0122"));
        System.out.println(userDao.getAllUsers());
        DBConnection.getInstance().stopConnection();
    }

    @Override
    public boolean createUser(User user) {
        Connection connection = DBConnection.getInstance().getConnection();
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into APP_USER (USER_ID, PHONE_NUMBER, PASSWORD)" +
                            " values (SEQ_USER_ID.nextval,?,?)");
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getPassword());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public User getUser(int userId) {
        Connection connection = DBConnection.getInstance().getConnection();
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
        Connection connection = DBConnection.getInstance().getConnection();
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from APP_USER where PHONE_NUMBER = " + phoneNumber);
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
        Connection connection = DBConnection.getInstance().getConnection();
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from APP_USER where PHONE_NUMBER = " + phoneNumber + " and PASSWORD = " + password);
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
        Connection connection = DBConnection.getInstance().getConnection();
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
        return null;
    }

    @Override
    public List<SingleChat> getUserSingleChats(int userId) {
        return null;
    }

    @Override
    public List<Membership> getUserGroupChatsMembership(int userId) {
        return null;
    }

    @Override
    public List<GroupChat> getUserGroupChats(int userId) {
        return null;
    }

    @Override
    public List<AnnouncementDelivery> getUserAnnouncementDeliveries(int userId) {
        return null;
    }

    @Override
    public List<Group> getUserGroups(int userId) {
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        return false;
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
}
