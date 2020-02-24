package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RelationshipDao extends Remote {

    // Create
    int createRelationship(Relationship relationship) throws RemoteException;

    // Read
    List<User> getRelationshipTwoUsers(int relationshipId) throws RemoteException;

    Relationship getRelationship(int relationshipId) throws RemoteException;

    Relationship getRelationshipBetween(int firstUserId, int secondUserId) throws RemoteException;

    // Update
    void updateRelationship(Relationship relationship) throws RemoteException;

    // Delete
    void deleteRelationship(int relationshipId) throws RemoteException;

}
