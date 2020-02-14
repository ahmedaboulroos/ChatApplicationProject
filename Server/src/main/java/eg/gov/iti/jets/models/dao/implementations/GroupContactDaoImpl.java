package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.GroupContactDao;
import eg.gov.iti.jets.models.entities.GroupContact;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupContactDaoImpl extends UnicastRemoteObject implements GroupContactDao {

    private static GroupContactDaoImpl instance;

    protected GroupContactDaoImpl() throws RemoteException {
    }

    public static GroupContactDao getInstance() {
        if (instance == null) {
            try {
                instance = new GroupContactDaoImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public boolean createGroupContact(GroupContact groupContact) {

        Connection connection = DBConnection.getInstance().getConnection();
        boolean flag = false;
        String sql = "INSERT INTO GROUP_CONTACT (GROUP_CONTACT_ID, USER_ID, GROUP_ID) VALUES (SEQ_GROUP_CONTACT_ID.NEXTVAL,?,?)";

        try {
            PreparedStatement insertpre = connection.prepareStatement(sql);


            insertpre.setInt(1, groupContact.getUserId());
            insertpre.setInt(2, groupContact.getGroupId());

            int affectedRow = insertpre.executeUpdate();
            if (affectedRow != -1) {
                flag = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    @Override
    public GroupContact getGroupContact(int groupContactId) {
        Connection connection = DBConnection.getInstance().getConnection();
        GroupContact groupContact = null;
        int groupContactID = 0;
        int userId = 0;
        int groupId = 0;
        try {

            PreparedStatement statement = connection.prepareStatement("select * from GROUP_CONTACT  where GROUP_CONTACT_ID=?");

            statement.setInt(1, groupContactId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                groupContactID = resultSet.getInt(1);
                userId = resultSet.getInt(2);
                groupId = resultSet.getInt(3);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        groupContact = new GroupContact(groupContactID, userId, groupId);

        return groupContact;

    }

    @Override
    public boolean updateGroupContact(GroupContact groupContact) {
        boolean flag = false;
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            String sql = "UPDATE GROUP_CONTACT SET USER_ID=? GROUP_ID=? where GROUP_CONTACT_ID";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, groupContact.getUserId());
            statement.setInt(2, groupContact.getGroupId());

            int affectedRow = statement.executeUpdate();
            System.out.println("Database updated successfully");
            if (affectedRow != 0) {
                flag = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteGroupContact(int groupContactId) {
        boolean flag = false;
        Connection connection = DBConnection.getInstance().getConnection();
        try {

            String sql = "Delete from GROUP_CONTACT where GROUP_CONTACT_ID=? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            int affectedRow = statement.executeUpdate();
            if (affectedRow != 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
