package eg.gov.iti.jets.models.network;

import eg.gov.iti.jets.models.dao.implementations.*;
import eg.gov.iti.jets.models.dao.interfaces.*;
import eg.gov.iti.jets.models.network.implementations.ServerService;
import eg.gov.iti.jets.models.network.interfaces.ServerInterface;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;

public class RMIConnection {

    private static RMIConnection instance;
    private static Connection connection = DBConnection.getConnection();
    private AnnouncementDao announcementDao = AnnouncementDaoImpl.getInstance(connection);
    private GroupChatDao groupChatDao = GroupChatDaoImpl.getInstance(connection);
    private GroupChatMessageDao groupChatMessageDao = GroupChatMessageDaoImpl.getInstance(connection);
    private ContactsGroupMembershipDao contactsGroupMembershipDao = ContactsGroupMembershipDaoImpl.getInstance(connection);
    private ContactsGroupDao contactsGroupDao = ContactsGroupDaoImpl.getInstance(connection);
    private GroupChatMembershipDao groupChatMembershipDao = GroupChatMembershipDaoImpl.getInstance(connection);
    private RelationshipDao relationshipDao = RelationshipDaoImpl.getInstance(connection);
    private SingleChatDao singleChatDao = SingleChatDaoImpl.getInstance(connection);
    private SingleChatMessageDao singleChatMessageDao = SingleChatMessageDaoImpl.getInstance(connection);
    private UserDao userDao = UserDaoImpl.getInstance(connection);
    private ServerInterface serverService;

    private Registry registry;

    private RMIConnection() {
        try {
            serverService = new ServerService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static synchronized RMIConnection getInstance() {
        if (instance == null)
            instance = new RMIConnection();
        return instance;
    }

    public boolean initConnection() {
        if (registry == null) {
            try {
                this.registry = LocateRegistry.createRegistry(1234);
                System.out.println(">> RMI-Registry Connection Established...");
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println(">> RMI-Registry Connection Already Established...");
            return true;
        }
    }

    public void startConnection() {
        try {
            this.registry.rebind("AnnouncementDao", announcementDao);
            this.registry.rebind("GroupChatDao", groupChatDao);
            this.registry.rebind("GroupChatMessageDao", groupChatMessageDao);
            this.registry.rebind("ContactsGroupMembershipDao", contactsGroupMembershipDao);
            this.registry.rebind("ContactsGroupDao", contactsGroupDao);
            this.registry.rebind("GroupChatMembershipDao", groupChatMembershipDao);
            this.registry.rebind("RelationshipDao", relationshipDao);
            this.registry.rebind("SingleChatDao", singleChatDao);
            this.registry.rebind("SingleChatMessageDao", singleChatMessageDao);
            this.registry.rebind("UserDao", userDao);
            this.registry.rebind("ServerService", serverService);

            System.out.println(">> RMI-Registry Services Bounded...");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection() {
        if (registry != null) {
            try {
                for (String service : this.registry.list()) {
                    this.registry.unbind(service);
                    System.out.println(">> RMI-Registry Service" + service + " Unbounded...");
                }
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(">> RMI-Registry Connection Already Closed");
        }
    }
}
