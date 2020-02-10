package eg.gov.iti.jets.models.dao.implementation;

import eg.gov.iti.jets.models.dao.interfaces.RelationshipDao;
import eg.gov.iti.jets.models.entities.Relationship;

import java.util.List;

public class RelationshipDaoImpl implements RelationshipDao {
    @Override
    public boolean createRelationship(Relationship relationship) {
        return false;
    }

    @Override
    public List<Relationship> getUserRelationships(int userId) {
        return null;
    }

    @Override
    public Relationship getRelationship(int relationshipId) {
        return null;
    }

    @Override
    public Relationship getRelationshipBetween(int userIdOne, int userIdTwo) {
        return null;
    }

    @Override
    public boolean updateRelationship(Relationship relationship) {
        return false;
    }

    @Override
    public boolean deleteRelationship(int relationshipId) {
        return false;
    }
}
