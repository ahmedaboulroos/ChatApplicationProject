package eg.gov.iti.jets.models.entities;

import java.io.Serializable;

public class GroupContact implements Serializable {
    private int groupContactId;
    private int userId;
    private int groupId;

    public GroupContact(int userId, int groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public GroupContact(int groupContactId, int userId, int groupId) {
        this.groupContactId = groupContactId;
        this.userId = userId;
        this.groupId = groupId;
    }

    public int getGroupContactId() {
        return groupContactId;
    }

    public void setGroupContactId(int groupContactId) {
        this.groupContactId = groupContactId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "GroupContact{" +
                "groupContactId=" + groupContactId +
                ", userId=" + userId +
                ", groupId=" + groupId +
                '}';
    }
}
