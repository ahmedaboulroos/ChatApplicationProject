package eg.gov.iti.jets.models.network.implementations;

import eg.gov.iti.jets.models.dao.implementations.UserDaoImpl;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;
import eg.gov.iti.jets.models.network.interfaces.ServerInterface;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
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

    public static ClientInterface getClient(int userId) {
        return clients.get(userId);
    }

    public static int getUserId(ClientInterface client) {
        return clients.entrySet().stream()
                .filter(e -> e.getValue().equals(client))
                .map(Map.Entry::getKey)
                .findFirst().orElse(-1);
    }

    @Override
    public void login(int userId, ClientInterface client) throws RemoteException {
        UserDao userDao = UserDaoImpl.getInstance(DBConnection.getConnection());
        User user = userDao.getUser(userId);
        if (user != null) {
            clients.put(user.getId(), client);
            List<Relationship> userRelationships = userDao.getUserRelationships(userId);
            if (userRelationships != null) {
                for (Relationship r : userRelationships) {
                    if (r.getFirstUserId() == userId) {
                        ClientInterface clientInterface = getClient(r.getSecondUserId());
                        if (clientInterface != null) {
                            clientInterface.userLoggedIn(userId);
                        }
                    } else {
                        ClientInterface clientInterface = getClient(r.getFirstUserId());
                        if (clientInterface != null) {
                            clientInterface.userLoggedIn(userId);
                        }
                    }
                }
            }
        } else {
            System.out.println("error in login");
        }
    }

    @Override
    public void logout(int userId, ClientInterface client) throws RemoteException {
        UserDao userDao = UserDaoImpl.getInstance(DBConnection.getConnection());
        User user = userDao.getUser(userId);
        if (user != null) {
            clients.remove(userId, client);
            List<Relationship> userRelationships = userDao.getUserRelationships(userId);
            if (userRelationships != null) {
                for (Relationship r : userRelationships) {
                    if (r.getFirstUserId() == userId) {
                        ClientInterface clientInterface = getClient(r.getSecondUserId());
                        if (clientInterface != null) {
                            clientInterface.userLoggedOut(userId);
                        }
                    } else {
                        ClientInterface clientInterface = getClient(r.getFirstUserId());
                        if (clientInterface != null) {
                            clientInterface.userLoggedOut(userId);
                        }
                    }
                }
            }
        } else {
            System.out.println("error in login");
        }
    }

}
