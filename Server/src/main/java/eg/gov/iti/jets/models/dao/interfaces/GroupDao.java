package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.Group;
import eg.gov.iti.jets.models.entities.GroupContact;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GroupDao extends Remote {

    // Create
    boolean createGroup(Group group) throws RemoteException;

    // Read
    Group getGroup(int groupId) throws RemoteException;

    List<Group> getAllUserGroups(int userId) throws RemoteException;

    List<GroupContact> getGroupContacts(int groupId) throws RemoteException;

    // Update
    boolean updateGroup(Group group) throws RemoteException;

    // Delete
    boolean deleteGroup(int groupId) throws RemoteException;

}
