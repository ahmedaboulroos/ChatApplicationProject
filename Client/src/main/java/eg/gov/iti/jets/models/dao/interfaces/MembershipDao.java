package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.Membership;
import eg.gov.iti.jets.models.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MembershipDao extends Remote {
    // Create
    boolean createMembership(Membership membership) throws RemoteException;

    // Read
    Membership getMembership(int membershipId) throws RemoteException;

    User getUser(int membershipId) throws RemoteException;

    GroupChat getGroupChat(int membershipId) throws RemoteException;

    // Update
    boolean updateMembership(Membership membership) throws RemoteException;

    // Delete
    boolean deleteMembership(int membershipId) throws RemoteException;

}
