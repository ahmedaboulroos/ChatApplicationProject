package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.GroupDao;
import eg.gov.iti.jets.models.entities.Group;
import eg.gov.iti.jets.models.entities.GroupContact;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl extends UnicastRemoteObject implements GroupDao {

    private Connection connection = DBConnection.getConnection();
    private static GroupDaoImpl instance;

    protected GroupDaoImpl() throws RemoteException {
    }

    public static GroupDao getInstance() {
        if (instance == null) {
            try {
                instance = new GroupDaoImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public boolean createGroup(Group group) {
        boolean result = false;
        String insertSQL = "INSERT INTO APP_USER_GROUP  \n" +
                "(GROUP_ID, USER_ID, GROUP_NAME ) VALUES (" +
                " SEQ_GROUP_CHAT_ID.nextval , ? , ? )  ";

        try (PreparedStatement insert = connection.prepareStatement(insertSQL)) {
            insert.setInt(1, group.getUserId());
            insert.setString(2, group.getGroupName());
            int flag = insert.executeUpdate();
            if (flag != -1)
                result = true;
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    @Override
    public Group getGroup(int groupId) {
        int groupID = 0;
        int userID = 0;
        String groupName = null;

        String selectSQL = "SELECT *  \n" +
                "FROM APP_USER_GROUP  \n" +
                "WHERE GROUP_ID = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupID = resultSet.getInt(1);
                userID = resultSet.getInt(2);
                groupName = resultSet.getString(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Group group = new Group(groupID, userID, groupName);

        return group;
    }

    @Override
    public List<Group> getAllUserGroups(int userId) throws RemoteException {
        List<Group> groupList = new ArrayList<>();
        int groupID = 0;
        int userID = 0;
        String groupName = null;

        String selectSQL = "SELECT *  \n" +
                "FROM APP_USER_GROUP  \n" +
                "WHERE USER_ID = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupID = resultSet.getInt(1);
                userID = resultSet.getInt(2);
                groupName = resultSet.getString(3);
                groupList.add(new Group(groupID, userID, groupName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groupList;
    }

    @Override
    public List<GroupContact> getGroupContacts(int groupId) {
        List<GroupContact> groupContactList = new ArrayList<>();
        String selectSQL = "SELECT *  \n" +
                "FROM GROUP_CONTACT  \n" +
                "WHERE GROUP_ID = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int groupContactId = resultSet.getInt(1);
                int userId = resultSet.getInt(2);
                int gropId = resultSet.getInt(3);
                GroupContact groupContact = new GroupContact(groupContactId, userId, gropId);
                groupContactList.add(groupContact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groupContactList;
    }

    @Override
    public boolean updateGroup(Group group) {
        String updateSQL = "UPDATE APP_USER_GROUP \n" +
                "SET USER_ID  =  ?  ,  \n" +
                "GROUP_NAME = ?  \n" +
                "WHERE GROUP_ID = ? ";

        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, group.getUserId());
            preparedStatement.setString(2, group.getGroupName());
            preparedStatement.setInt(3, group.getGroupId());
            int rowEffected = preparedStatement.executeUpdate();
            System.out.println("row updated" + rowEffected);
            if (rowEffected != 0)
                result = true;
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean deleteGroup(int groupId) {
        String deleteSQL = "DELETE FROM APP_USER_GROUP   \n" +
                "WHERE GROUP_ID = ? ";
        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, groupId);
            int rowEffected = preparedStatement.executeUpdate();
            System.out.println("row deleted" + rowEffected);
            if (rowEffected != 0)
                result = true;
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
