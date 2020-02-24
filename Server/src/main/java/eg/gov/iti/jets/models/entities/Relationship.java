package eg.gov.iti.jets.models.entities;

import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;

import java.io.Serializable;

public class Relationship implements Serializable {
    private static final long serialVersionUID = 6329385398367757693L;
    private int id;
    private int firstUserId;
    private int secondUserId;
    private RelationshipStatus status;

    public Relationship(int firstUserId, int secondUserId) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.status = RelationshipStatus.PENDING;
    }

    public Relationship(int id, int firstUserId, int secondUserId, RelationshipStatus status) {
        this.id = id;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public RelationshipStatus getStatus() {
        return status;
    }

    public void setStatus(RelationshipStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "relationshipId=" + id +
                ", firstUserId=" + firstUserId +
                ", secondUserId=" + secondUserId +
                ", relationshipStatus=" + status +
                '}';
    }
}
