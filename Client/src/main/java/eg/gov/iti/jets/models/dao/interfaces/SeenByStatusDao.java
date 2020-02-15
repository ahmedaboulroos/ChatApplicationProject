package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.SeenByStatus;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SeenByStatusDao extends Remote {

    // Create
    boolean createSeenByStatus(SeenByStatus seenByStatus) throws RemoteException;

    // Read
    SeenByStatus getSeenByStatus(int seenByStatusId) throws RemoteException;

    // Update
    boolean updateSeenByStatus(SeenByStatus seenByStatus) throws RemoteException;

    // Delete
    boolean deleteSeenByStatus(int seenByStatusId) throws RemoteException;

}
