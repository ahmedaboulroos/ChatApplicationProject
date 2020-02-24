package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.ContactsGroup;
import eg.gov.iti.jets.models.entities.ContactsGroupMembership;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ContactsGroupDao extends Remote {

    // Create
    int createContactsGroup(ContactsGroup contactsGroup) throws RemoteException;

    // Read
    ContactsGroup getContactsGroup(int contactsGroupId) throws RemoteException;

    List<ContactsGroupMembership> getContactsGroupMemberships(int contactsGroupId) throws RemoteException;

    // Update
    void updateContactsGroup(ContactsGroup contactsGroup) throws RemoteException;

    // Delete
    void deleteContactsGroup(int contactsGroupId) throws RemoteException;

}
