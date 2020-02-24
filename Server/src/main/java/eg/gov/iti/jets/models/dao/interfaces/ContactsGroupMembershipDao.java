package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.ContactsGroupMembership;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ContactsGroupMembershipDao extends Remote {

    // Create
    int createGroupContactMembership(ContactsGroupMembership contactsGroupMembership) throws RemoteException;

    // Read
    ContactsGroupMembership getContactsGroupMembership(int contactsGroupMembershipId) throws RemoteException;

    // Update
    void updateGroupContactMembership(ContactsGroupMembership contactsGroupMembership) throws RemoteException;

    // Delete
    void deleteGroupContactMembership(int contactsGroupMembershipId) throws RemoteException;

}
