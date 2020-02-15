package eg.gov.iti.jets.models.entities;

import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;

import java.io.Serializable;

public class Relationship implements Serializable {
    private static final long serialVersionUID = 6329385398367757693L;
    private int relationshipId;
    private int firstUserId;
    private int secondUserId;
    private RelationshipStatus relationshipStatus;

    public Relationship(int firstUserId, int secondUserId) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.relationshipStatus = RelationshipStatus.PENDING;
    }

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

    @Override
    public String toString() {
        return "Relationship{" +
                "relationshipId=" + relationshipId +
                ", firstUserId=" + firstUserId +
                ", secondUserId=" + secondUserId +
                ", relationshipStatus=" + relationshipStatus +
                '}';
    }
}
