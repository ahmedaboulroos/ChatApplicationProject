package eg.gov.iti.jets.models.network;

import eg.gov.iti.jets.models.dao.implementations.*;
import eg.gov.iti.jets.models.dao.interfaces.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {

    private static RMIConnection instance;
    private AnnouncementDao announcementDao = AnnouncementDaoImpl.getInstance();
    private AnnouncementDeliveryDao announcementDeliveryDao = AnnouncementDeliveryDaoImpl.getInstance();
    private GroupChatDao groupChatDao = GroupChatDaoImpl.getInstance();
    private GroupChatMessageDao groupChatMessageDao = GroupChatMessageDaoImpl.getInstance();
    private GroupContactDao groupContactDao = GroupContactDaoImpl.getInstance();
    private GroupDao groupDao = GroupDaoImpl.getInstance();
    private MembershipDao membershipDao = MembershipDaoImpl.getInstance();
    private RelationshipDao relationshipDao = RelationshipDaoImpl.getInstance();
    private SeenByStatusDao seenByStatusDao = SeenByStatusDaoImpl.getInstance();
    private SingleChatDao singleChatDao = SingleChatDaoImpl.getInstance();
    private SingleChatMessageDao singleChatMessageDao = SingleChatMessageDaoImpl.getInstance();
    private UserDao userDao = UserDaoImpl.getInstance();
    private Registry registry;

    private RMIConnection() {
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
            this.registry.rebind("AnnouncementDeliveryDao", announcementDeliveryDao);
            this.registry.rebind("GroupChatDao", groupChatDao);
            this.registry.rebind("GroupChatMessageDao", groupChatMessageDao);
            this.registry.rebind("GroupContactDao", groupContactDao);
            this.registry.rebind("GroupDao", groupDao);
            this.registry.rebind("MembershipDao", membershipDao);
            this.registry.rebind("RelationshipDao", relationshipDao);
            this.registry.rebind("SeenByStatusDao", seenByStatusDao);
            this.registry.rebind("SingleChatDao", singleChatDao);
            this.registry.rebind("SingleChatMessageDao", singleChatMessageDao);
            this.registry.rebind("UserDao", userDao);

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
