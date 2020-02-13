package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.GroupContactDao;
import eg.gov.iti.jets.models.entities.GroupContact;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GroupContactDaoImpl extends UnicastRemoteObject implements GroupContactDao {

    protected GroupContactDaoImpl() throws RemoteException {
    }

    @Override
    public boolean createGroupContact(GroupContact groupContact) {
        return false;
    }

    @Override
    public GroupContact getGroupContact(int groupContactId) {
        return null;
    }

    @Override
    public boolean updateGroupContact(GroupContact groupContact) {
        return false;
    }

    @Override
    public boolean deleteGroupContact(int groupContactId) {
        return false;
    }
}
