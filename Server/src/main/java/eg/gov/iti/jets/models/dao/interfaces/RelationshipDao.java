package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.Relationship;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RelationshipDao extends Remote {

    // Create
    boolean createRelationship(Relationship relationship) throws RemoteException;

    // Read
    List<Relationship> getUserRelationships(int userId) throws RemoteException;

    Relationship getRelationship(int relationshipId) throws RemoteException;

    Relationship getRelationshipBetween(int userIdOne, int userIdTwo) throws RemoteException;

    // Update
    boolean updateRelationship(Relationship relationship) throws RemoteException;

    // Delete
    boolean deleteRelationship(int relationshipId) throws RemoteException;

}
