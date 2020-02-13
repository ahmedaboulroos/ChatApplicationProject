package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.SeenByStatusDao;
import eg.gov.iti.jets.models.entities.SeenByStatus;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SeenByStatusDaoImpl extends UnicastRemoteObject implements SeenByStatusDao {

    protected SeenByStatusDaoImpl() throws RemoteException {
    }

    @Override
    public boolean createSeenByStatus(SeenByStatus seenByStatus) {
        return false;
    }

    @Override
    public SeenByStatus getSeenByStatus(int seenByStatusId) {
        return null;
    }

    @Override
    public boolean updateSeenByStatus(SeenByStatus seenByStatus) {
        return false;
    }

    @Override
    public boolean deleteSeenByStatus(int seenByStatusId) {
        return false;
    }
}
