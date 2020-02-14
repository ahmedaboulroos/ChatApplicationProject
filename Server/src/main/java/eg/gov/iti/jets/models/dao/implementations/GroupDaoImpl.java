package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.GroupDao;
import eg.gov.iti.jets.models.entities.Group;
import eg.gov.iti.jets.models.entities.GroupContact;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class GroupDaoImpl extends UnicastRemoteObject implements GroupDao {

    protected GroupDaoImpl() throws RemoteException {
    }

    @Override
    public boolean createGroup(Group group) {
        return false;
    }

    @Override
    public Group getGroup(int groupId) {
        return null;
    }

    @Override
    public List<GroupContact> getGroupContacts(int groupId) {
        return null;
    }

    @Override
    public boolean updateGroup(Group group) {
        return false;
    }

    @Override
    public boolean deleteGroup(int groupId) {
        return false;
    }
}
