package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.ContactsGroupDao;
import eg.gov.iti.jets.models.entities.ContactsGroup;
import eg.gov.iti.jets.models.entities.ContactsGroupMembership;
import eg.gov.iti.jets.models.network.implementations.ServerService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactsGroupDaoImpl extends UnicastRemoteObject implements ContactsGroupDao {

    private static Connection dbConnection;
    private static ContactsGroupDaoImpl instance;

    protected ContactsGroupDaoImpl() throws RemoteException {
    }

    public static ContactsGroupDao getInstance(Connection connection) {
        if (instance == null) {
            try {
                instance = new ContactsGroupDaoImpl();
                dbConnection = connection;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public int createContactsGroup(ContactsGroup contactsGroup) {
        int id = -1;
        String[] key = {"ID"};
        String insertSQL = "INSERT INTO CONTACTS_GROUPS (ID, USER_ID, GROUP_NAME) VALUES (ID_SEQ.nextval , ? , ? )  ";
        try (PreparedStatement ps = dbConnection.prepareStatement(insertSQL, key)) {
            ps.setInt(1, contactsGroup.getUserId());
            ps.setString(2, contactsGroup.getGroupName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            ServerService.getClient(contactsGroup.getUserId()).receiveNewContactsGroup(id);
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public ContactsGroup getContactsGroup(int contactsGroupId) {
        ContactsGroup contactsGroup = null;
        String selectSQL = "SELECT ID, USER_ID, GROUP_NAME FROM CONTACTS_GROUPS WHERE ID = ? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, contactsGroupId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                contactsGroup = new ContactsGroup(rs.getInt(1), rs.getInt(2), rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsGroup;
    }

    @Override
    public List<ContactsGroupMembership> getContactsGroupMemberships(int contactsGroupId) {
        List<ContactsGroupMembership> contactsGroupMembershipList = new ArrayList<>();
        String selectSQL = "SELECT ID, USER_ID, CONTACTS_GROUP_ID FROM CONTACTS_GROUP_MEMBERSHIPS WHERE CONTACTS_GROUP_ID = ? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, contactsGroupId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                contactsGroupMembershipList.add(new ContactsGroupMembership(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsGroupMembershipList;
    }

    @Override
    public void updateContactsGroup(ContactsGroup contactsGroup) {
        String updateSQL = "UPDATE CONTACTS_GROUPS SET USER_ID = ? , GROUP_NAME = ? WHERE ID = ? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(updateSQL)) {
            ps.setInt(1, contactsGroup.getUserId());
            ps.setString(2, contactsGroup.getGroupName());
            ps.setInt(3, contactsGroup.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteContactsGroup(int contactsGroupId) {
        String deleteSQL = "DELETE FROM CONTACTS_GROUPS WHERE ID = ? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(deleteSQL)) {
            ps.setInt(1, contactsGroupId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
