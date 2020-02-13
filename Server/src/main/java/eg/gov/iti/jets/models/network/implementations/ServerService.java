package eg.gov.iti.jets.models.network.implementations;

import eg.gov.iti.jets.models.network.interfaces.ClientInterface;
import eg.gov.iti.jets.models.network.interfaces.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class ServerService extends UnicastRemoteObject implements ServerInterface {

    private static ConcurrentHashMap<Integer, ClientInterface> clients = new ConcurrentHashMap<>();

    protected ServerService() throws RemoteException {
    }

    public static Enumeration<Integer> getAllOnlineUsers() {
        return clients.keys();
    }

    public static Collection<ClientInterface> getAllOnlineClients() {
        return clients.values();
    }

    @Override
    public void login(int userId, ClientInterface client) throws RemoteException {
        clients.put(userId, client);
    }

    @Override
    public void logout(int userId, ClientInterface client) throws RemoteException {
        clients.remove(userId, client);
    }

}
