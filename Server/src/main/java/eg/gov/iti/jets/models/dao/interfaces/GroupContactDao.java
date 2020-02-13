package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.GroupContact;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GroupContactDao extends Remote {

    // Create
    boolean createGroupContact(GroupContact groupContact) throws RemoteException;

    // Read
    GroupContact getGroupContact(int groupContactId) throws RemoteException;

    // Update
    boolean updateGroupContact(GroupContact groupContact) throws RemoteException;

    // Delete
    boolean deleteGroupContact(int groupContactId) throws RemoteException;

}
