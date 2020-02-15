package eg.gov.iti.jets.models.network.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    void userLoggedIn(int userId) throws RemoteException;

    void userLoggedOut(int userId) throws RemoteException;

    void receiveNewSingleChat(int singleChatId) throws RemoteException;

    void receiveNewSingleChatMessage(int singleChatMessageId) throws RemoteException;

    void receiveNewGroupChat(int groupChatId) throws RemoteException;

    void receiveNewGroupChatMessage(int groupChatMessageId) throws RemoteException;

    void receiveNewSeenByStatus(int seenByStatusId) throws RemoteException;

    void receiveRelationship(int relationshipId) throws RemoteException;

    void receiveMembership(int membershipId) throws RemoteException;

    void receiveGroup(int groupId) throws RemoteException;

    void receiveGroupContact(int groupContactId) throws RemoteException;

    void receiveAnnouncement(int announcementId) throws RemoteException;

}
