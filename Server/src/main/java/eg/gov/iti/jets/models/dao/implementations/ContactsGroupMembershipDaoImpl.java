package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.ContactsGroupMembershipDao;
import eg.gov.iti.jets.models.entities.ContactsGroup;
import eg.gov.iti.jets.models.entities.ContactsGroupMembership;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.implementations.ServerService;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsGroupMembershipDaoImpl extends UnicastRemoteObject implements ContactsGroupMembershipDao {

    private static ContactsGroupMembershipDaoImpl instance;
    private static Connection dbConnection;

    protected ContactsGroupMembershipDaoImpl() throws RemoteException {
    }

    public static ContactsGroupMembershipDao getInstance(Connection connection) {
        if (instance == null) {
            try {
                instance = new ContactsGroupMembershipDaoImpl();
                dbConnection = connection;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public int createGroupContactMembership(ContactsGroupMembership contactsGroupMembership) {
        int id = -1;
        String[] key = {"ID"};
        String sql = "INSERT INTO CONTACTS_GROUP_MEMBERSHIPS (ID, USER_ID, CONTACTS_GROUP_ID) VALUES (ID_SEQ.NEXTVAL,?,?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, key)) {
            ps.setInt(1, contactsGroupMembership.getUserId());
            ps.setInt(2, contactsGroupMembership.getContactsGroupId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            /*List<ContactsGroupMembership> contactsGroupMemberships = ContactsGroupDaoImpl.getInstance(dbConnection).getContactsGroupMemberships(contactsGroupMembership.getContactsGroupId());
            for (ContactsGroupMembership m : contactsGroupMemberships) {
                ClientInterface client = ServerService.getClient(m.getUserId());
                if (client != null) {
                    client.receiveNewContactsGroupMembership(id);
                }
            }*/
            ContactsGroup contactsGroup = ContactsGroupDaoImpl.getInstance(dbConnection).getContactsGroup(contactsGroupMembership.getContactsGroupId());
            User user = UserDaoImpl.getInstance(dbConnection).getUser(contactsGroup.getUserId());
            ClientInterface client = ServerService.getClient(user.getId());
            if (client != null)
                client.receiveNewContactsGroupMembership(id);
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public ContactsGroupMembership getContactsGroupMembership(int contactsGroupMembershipId) {
        ContactsGroupMembership contactsGroupMembership = null;
        String sql = "select ID, USER_ID, CONTACTS_GROUP_ID from CONTACTS_GROUP_MEMBERSHIPS  where ID=?";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, contactsGroupMembershipId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contactsGroupMembership = new ContactsGroupMembership(rs.getInt(1), rs.getInt(2), rs.getInt(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contactsGroupMembership;
    }

    @Override
    public void updateGroupContactMembership(ContactsGroupMembership contactsGroupMembership) {
        String sql = "UPDATE CONTACTS_GROUP_MEMBERSHIPS SET USER_ID=?, CONTACTS_GROUP_ID=? where ID=?";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setInt(1, contactsGroupMembership.getUserId());
            ps.setInt(2, contactsGroupMembership.getContactsGroupId());
            ps.setInt(3, contactsGroupMembership.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGroupContactMembership(int contactsGroupMembershipId) {
        String sql = "Delete from CONTACTS_GROUP_MEMBERSHIPS where ID=? ";
        try {
            ContactsGroup contactsGroup = ContactsGroupDaoImpl.getInstance(dbConnection).getContactsGroup(
                    getContactsGroupMembership(contactsGroupMembershipId).getContactsGroupId());
            try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
                ps.setInt(1, contactsGroupMembershipId);
                ps.executeUpdate();
                ClientInterface client = ServerService.getClient(contactsGroup.getUserId());
                if (client != null) {
                    client.receiveNewContactsGroupMembership(contactsGroupMembershipId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
