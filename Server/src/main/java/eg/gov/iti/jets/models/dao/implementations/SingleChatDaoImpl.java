package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SingleChatDaoImpl implements SingleChatDao {
    private static Connection connection;

    public static void main(String[] args) {
        DBConnection.getInstance().initConnection();
        connection = DBConnection.getInstance().getConnection();
        SingleChatDaoImpl obj = new SingleChatDaoImpl();
        //SingleChat singleChat = new SingleChat(1, 123, 124);
       // SingleChat.createSingleChat(singleChat);
       // SingleChat.deleteSingleChat(3);
//        SingleChat ss= obj.getSingleChat(1);
//        System.out.println(ss.getUserOneId());
//        SingleChat ob = new SingleChat(1, 123, 125);
//        boolean falg = obj .updateSingleChat(ob);
//        System.out.println(falg);
        List<SingleChat> singlechats = new ArrayList<>();
        singlechats=obj.getSingleChatMessages(1);

        System.out.println("First element of the ArrayList: "+singlechats.get(0));
    }

    @Override
    public boolean createSingleChat(SingleChat singleChat) {
        boolean flag = false;

        try {
            System.out.println(connection);
            connection.setAutoCommit(false);
            String sql = "INSERT INTO SINGLE_CHAT (SINGLE_CHAT_ID,  USER_ONE_ID, USER_TWO_ID) VALUES (SEQ_SINGLE_CHAT_ID.NEXTVAL,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, singleChat.getUserOneId());
            preparedStatement.setInt(2, singleChat.getUserTwoId());


            int affectedRow = preparedStatement.executeUpdate();
            System.out.println(affectedRow);
            connection.commit();
            if (affectedRow > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public SingleChat getSingleChat(int singleChatId) {

        SingleChat singleChatRef = null;
        try {
            PreparedStatement statement;
            String sql = "select * from SINGLE_CHAT where SINGLE_CHAT_ID= ? ";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, singleChatId);
            ResultSet resultset = statement.executeQuery();

            System.out.println(resultset);
            while (resultset.next()) {

                singleChatRef = new SingleChat(resultset.getInt("SINGLE_CHAT_ID"), resultset.getInt("USER_ONE_ID"), resultset.getInt("USER_TWO_ID"));

            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return singleChatRef;


    }

    @Override
    public List<User> getSingleChatTwoUsers(int singleChatId) {
        Connection connection = DBConnection.getInstance().getConnection();
        List<User> userList = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * SINGLE_CHAT  from  where SINGLE_CHAT_ID= ?");
            preparedStatement.setInt(1, singleChatId);
            ResultSet resultSet = preparedStatement.executeQuery();
            userList = getUsersListFromResultSet(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;


    }


    @Override
    public List<SingleChat> getSingleChatMessages(int singleChatId) {

        List<SingleChat> singleChats = null;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    "select * from SINGLE_CHAT where
//                            );
//            ResultSet resultSet = preparedStatement.executeQuery();
//            singleChats = getSingleChatsFromResultSet(resultSet);
//            resultSet.close();
//            preparedStatement.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
       return singleChats;

    }

    @Override
    public boolean updateSingleChat(SingleChat singleChat) {
        String sql = "UPDATE  SINGLE_CHAT SET USER_ONE_ID= ?  , USER_TWO_ID= ?" +
                " where SINGLE_CHAT_ID= ? ";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, singleChat.getUserOneId());
            preparedStatement.setInt(2, singleChat.getUserTwoId());

            preparedStatement.setInt(3, singleChat.getSingleChatId());
            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected != 0) {

                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean addSingleChatMessage(int singleChatMessageId) {

        return false;
    }

    @Override
    public boolean deleteSingleChat(int singleChatId) {
        try {
            String sql = "delete from SINGLE_CHAT where SINGLE_CHAT_ID= ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, singleChatId);


            int affectedRow = stmt.executeUpdate();
            if (affectedRow != 0) {
                return true;
            }


        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return false;

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
