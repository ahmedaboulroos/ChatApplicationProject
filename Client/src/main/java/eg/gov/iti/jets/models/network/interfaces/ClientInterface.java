package eg.gov.iti.jets.models.network.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    // user login & logout
    void userLoggedIn(int userId) throws RemoteException;

    void userLoggedOut(int userId) throws RemoteException;


    // user status changed
    void receiveUserStatusChanged(int userId) throws RemoteException;


    // single chat notifications
    void receiveNewSingleChat(int singleChatId) throws RemoteException;

    void receiveNewSingleChatMessage(int singleChatMessageId) throws RemoteException;


    // group chat notifications
    void receiveNewGroupChatMembership(int groupChatMembershipId) throws RemoteException;

    void receiveNewGroupChatMessage(int groupChatMessageId) throws RemoteException;


    // relationship new | updated
    void receiveNewRelationship(int relationshipId) throws RemoteException;


    // Contacts list changes
    void receiveNewContactsGroup(int contactsGroupId) throws RemoteException;

    void receiveNewContactsGroupMembership(int contactsGroupMembershipId) throws RemoteException;


    // server announcements
    void receiveNewAnnouncement(int announcementId) throws RemoteException;

}
