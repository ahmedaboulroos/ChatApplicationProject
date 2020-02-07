package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.Relationship;

import java.util.List;

public interface RelationshipDao {

    // Create
    boolean createRelationship(Relationship relationship);

    // Read
    List<Relationship> getUserRelationships(int userId);
    Relationship getRelationship(int relationshipId);
    Relationship getRelationshipBetween(int userIdOne, int userIdTwo);

    // Update
    boolean updateRelationship(Relationship relationship);

    // Delete
    boolean deleteRelationship(int relationshipId);

}
