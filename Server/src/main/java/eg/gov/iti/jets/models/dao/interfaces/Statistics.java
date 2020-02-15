package eg.gov.iti.jets.models.dao.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Statistics extends Remote {
    Map<String, Integer> getUsersByGender() throws RemoteException;

    Map<String, Integer> getUsersByStatus() throws RemoteException;

    Map<String, Integer> getUsersNumByCountry() throws RemoteException;
}
