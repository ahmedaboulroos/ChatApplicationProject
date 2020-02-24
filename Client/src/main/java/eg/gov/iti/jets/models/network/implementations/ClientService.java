package eg.gov.iti.jets.models.network.implementations;


import eg.gov.iti.jets.models.network.interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientService extends UnicastRemoteObject implements ClientInterface {

    public ClientService() throws RemoteException {
    }

    private static ClientService instance;

    public static ClientService getInstance() {
        if (instance == null) {
            try {
                instance = new ClientService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }


    @Override
    public void userLoggedIn(int userId) throws RemoteException {
        System.out.println(">> User Logged In :" + userId);
    }

    @Override
    public void userLoggedOut(int userId) throws RemoteException {
        System.out.println(">> User Logged Out :" + userId);
    }

    @Override
    public void receiveUserStatusChanged(int userId) throws RemoteException {
        System.out.println(">> User Status Changed :" + userId);
    }

    @Override
    public void receiveNewSingleChat(int singleChatId) throws RemoteException {
        System.out.println(">> New Single Chat :" + singleChatId);
    }

    @Override
    public void receiveNewSingleChatMessage(int singleChatMessageId) throws RemoteException {
        System.out.println(">> New Single Chat Message :" + singleChatMessageId);
    }

    @Override
    public void receiveNewGroupChatMessage(int groupChatMessageId) throws RemoteException {
        System.out.println(">> New Group Chat Message :" + groupChatMessageId);
    }

    @Override
    public void receiveNewGroupChatMembership(int groupChatMembershipId) throws RemoteException {
        System.out.println(">> New Group Chat Membership :" + groupChatMembershipId);
    }

    @Override
    public void receiveNewSeenByStatus(int seenByStatusId) throws RemoteException {
        System.out.println(">> New Seen By Status :" + seenByStatusId);
    }

    @Override
    public void receiveNewRelationship(int relationshipId) throws RemoteException {
        System.out.println(">> New Relationship :" + relationshipId);
    }

    @Override
    public void receiveNewContactsGroup(int contactsGroupId) throws RemoteException {
        System.out.println(">> New Contacts Group :" + contactsGroupId);
    }

    @Override
    public void receiveNewContactsGroupMembership(int contactsGroupMembershipId) throws RemoteException {
        System.out.println(">> New Contacts Group Membership :" + contactsGroupMembershipId);
    }

    @Override
    public void receiveNewAnnouncement(int announcementId) throws RemoteException {
        System.out.println(">> New Announcement :" + announcementId);
    }

}
