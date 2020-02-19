package eg.gov.iti.jets.models.network.implementations;

import eg.gov.iti.jets.models.dao.implementations.UserDaoImpl;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;
import eg.gov.iti.jets.models.network.interfaces.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class ServerService extends UnicastRemoteObject implements ServerInterface {

    private static ConcurrentHashMap<Integer, ClientInterface> clients = new ConcurrentHashMap<>();
    private static ServerService instance;

    public static ServerService getInstance() {
        if (instance == null) {
            try {
                instance = new ServerService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public ServerService() throws RemoteException {
    }

    public static Enumeration<Integer> getAllOnlineUsers() {
        return clients.keys();
    }

    public static Collection<ClientInterface> getAllOnlineClients() {
        return clients.values();
    }

    @Override
    public void login(int userId, ClientInterface client) throws RemoteException {

        UserDaoImpl userDao = new UserDaoImpl();
        //User user=userDao.getUser("","");
        User user = userDao.getUser(userId);
        if (user != null) {
            clients.put(user.getUserId(), client);
            //System.out.println(user.getUserId());
        } else {
            System.out.println("error in login");
        }
        // clients.put(userId, client);
    }

    @Override
    public void logout(int userId, ClientInterface client) throws RemoteException {
        clients.remove(userId, client);
    }


    public ClientInterface getClient(int userId) {
        ClientInterface client = clients.get(userId);
        return client;
    }


}
