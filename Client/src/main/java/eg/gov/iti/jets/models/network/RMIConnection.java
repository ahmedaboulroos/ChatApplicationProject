package eg.gov.iti.jets.models.network;

import eg.gov.iti.jets.models.dao.interfaces.*;
import eg.gov.iti.jets.models.network.interfaces.ServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {

    private static AnnouncementDao announcementDao;
    private static GroupChatDao groupChatDao;
    private static GroupChatMessageDao groupChatMessageDao;
    private static ContactsGroupMembershipDao contactsGroupMembershipDao;
    private static ContactsGroupDao contactsGroupDao;
    private static GroupChatMembershipDao groupChatMembershipDao;
    private static RelationshipDao relationshipDao;
    private static SingleChatDao singleChatDao;
    private static SingleChatMessageDao singleChatMessageDao;
    private static UserDao userDao;

    private static ServerInterface serverService;
    private static Registry registry;

    private RMIConnection() {
    }

    public static AnnouncementDao getAnnouncementDao() {
        return announcementDao;
    }

    public static GroupChatDao getGroupChatDao() {
        return groupChatDao;
    }

    public static GroupChatMessageDao getGroupChatMessageDao() {
        return groupChatMessageDao;
    }

    public static ContactsGroupMembershipDao getContactsGroupMembershipDao() {
        return contactsGroupMembershipDao;
    }

    public static ContactsGroupDao getContactsGroupDao() {
        return contactsGroupDao;
    }

    public static GroupChatMembershipDao getGroupChatMembershipDao() {
        return groupChatMembershipDao;
    }

    public static RelationshipDao getRelationshipDao() {
        return relationshipDao;
    }

    public static SingleChatDao getSingleChatDao() {
        return singleChatDao;
    }

    public static SingleChatMessageDao getSingleChatMessageDao() {
        return singleChatMessageDao;
    }

    public static UserDao getUserDao() {
        return userDao;
    }

    public static ServerInterface getServerService() {
        return serverService;
    }

    public static boolean startConnection() {
        try {
            if (registry == null) {
                try {
                    registry = LocateRegistry.getRegistry(1234);
                    announcementDao = (AnnouncementDao) registry.lookup("AnnouncementDao");
                    groupChatDao = (GroupChatDao) registry.lookup("GroupChatDao");
                    groupChatMessageDao = (GroupChatMessageDao) registry.lookup("GroupChatMessageDao");
                    contactsGroupMembershipDao = (ContactsGroupMembershipDao) registry.lookup("ContactsGroupMembershipDao");
                    contactsGroupDao = (ContactsGroupDao) registry.lookup("ContactsGroupDao");
                    groupChatMembershipDao = (GroupChatMembershipDao) registry.lookup("GroupChatMembershipDao");
                    relationshipDao = (RelationshipDao) registry.lookup("RelationshipDao");
                    singleChatDao = (SingleChatDao) registry.lookup("SingleChatDao");
                    singleChatMessageDao = (SingleChatMessageDao) registry.lookup("SingleChatMessageDao");
                    userDao = (UserDao) registry.lookup("UserDao");
                    serverService = (ServerInterface) registry.lookup("ServerService");
                    System.out.println(">> RMI-Registry Services Bounded...");
                    return true;
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                System.out.println(">> RMI-Registry Connection Already Established...");
                return true;
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
