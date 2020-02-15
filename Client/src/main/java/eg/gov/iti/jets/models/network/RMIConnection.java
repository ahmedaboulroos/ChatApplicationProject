package eg.gov.iti.jets.models.network;

import eg.gov.iti.jets.models.dao.interfaces.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {

    private static RMIConnection instance;
    private AnnouncementDao announcementDao;
    private AnnouncementDeliveryDao announcementDeliveryDao;
    private GroupChatDao groupChatDao;
    private GroupChatMessageDao groupChatMessageDao;
    private GroupContactDao groupContactDao;
    private GroupDao groupDao;
    private MembershipDao membershipDao;
    private RelationshipDao relationshipDao;
    private SeenByStatusDao seenByStatusDao;
    private SingleChatDao singleChatDao;
    private SingleChatMessageDao singleChatMessageDao;
    private UserDao userDao;
    private Registry registry;

    private RMIConnection() {
    }

    public static synchronized RMIConnection getInstance() {
        if (instance == null)
            instance = new RMIConnection();
        return instance;
    }

    public AnnouncementDao getAnnouncementDao() {
        return this.announcementDao;
    }

    public AnnouncementDeliveryDao getAnnouncementDeliveryDao() {
        return this.announcementDeliveryDao;
    }

    public GroupChatDao getGroupChatDao() {
        return this.groupChatDao;
    }

    public GroupChatMessageDao getGroupChatMessageDao() {
        return this.groupChatMessageDao;
    }

    public GroupContactDao getGroupContactDao() {
        return this.groupContactDao;
    }

    public GroupDao getGroupDao() {
        return this.groupDao;
    }

    public MembershipDao getMembershipDao() {
        return this.membershipDao;
    }

    public RelationshipDao getRelationshipDao() {
        return this.relationshipDao;
    }

    public SeenByStatusDao getSeenByStatusDao() {
        return this.seenByStatusDao;
    }

    public SingleChatDao getSingleChatDao() {
        return this.singleChatDao;
    }

    public SingleChatMessageDao getSingleChatMessageDao() {
        return this.singleChatMessageDao;
    }

    public UserDao getUserDao() {
        return this.userDao;
    }

    public boolean initConnection() {
        if (registry == null) {
            try {
                this.registry = LocateRegistry.getRegistry(1234);
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
            announcementDao = (AnnouncementDao) this.registry.lookup("AnnouncementDao");
            announcementDeliveryDao = (AnnouncementDeliveryDao) this.registry.lookup("AnnouncementDeliveryDao");
            groupChatDao = (GroupChatDao) this.registry.lookup("GroupChatDao");
            groupChatMessageDao = (GroupChatMessageDao) this.registry.lookup("GroupChatMessageDao");
            groupContactDao = (GroupContactDao) this.registry.lookup("GroupContactDao");
            groupDao = (GroupDao) this.registry.lookup("GroupDao");
            membershipDao = (MembershipDao) this.registry.lookup("MembershipDao");
            relationshipDao = (RelationshipDao) this.registry.lookup("RelationshipDao");
            seenByStatusDao = (SeenByStatusDao) this.registry.lookup("SeenByStatusDao");
            singleChatDao = (SingleChatDao) this.registry.lookup("SingleChatDao");
            singleChatMessageDao = (SingleChatMessageDao) this.registry.lookup("SingleChatMessageDao");
            userDao = (UserDao) this.registry.lookup("UserDao");
            System.out.println(">> RMI-Registry Services Bounded...");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection() {
        System.out.println(">> RMI-Registry Connection Already Closed");
    }

}
