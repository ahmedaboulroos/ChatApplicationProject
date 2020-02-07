package eg.gov.iti.jets.models.entities;

import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;

public class Relationship {
    private int relationshipId;
    private int firstUserId;
    private int secondUserId;
    private RelationshipStatus relationshipStatus;

    public Relationship(int firstUserId, int secondUserId) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.relationshipStatus = RelationshipStatus.PENDING;
    }

    // constructor for populating data from database
    public Relationship(int relationshipId, int firstUserId, int secondUserId, RelationshipStatus relationshipStatus) {
        this.relationshipId = relationshipId;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.relationshipStatus = relationshipStatus;
    }

    public int getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(int relationshipId) {
        this.relationshipId = relationshipId;
    }

    public int getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(int firstUserId) {
        this.firstUserId = firstUserId;
    }

    public int getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(int secondUserId) {
        this.secondUserId = secondUserId;
    }

    public RelationshipStatus getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

}
