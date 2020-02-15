package eg.gov.iti.jets.models.network.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    void login(int userId, ClientInterface client) throws RemoteException;

    void logout(int userId, ClientInterface client) throws RemoteException;

}
