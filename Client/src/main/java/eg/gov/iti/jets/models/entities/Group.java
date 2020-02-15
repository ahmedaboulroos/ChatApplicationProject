package eg.gov.iti.jets.models.entities;

import java.io.Serializable;

public class Group implements Serializable {
    private static final long serialVersionUID = 6539685698278757695L;
    private int groupId;
    private int userId;
    private String groupName;

    public Group(int userId, String groupName) {
        this.userId = userId;
        this.groupName = groupName;
    }

    public Group(int groupId, int userId, String groupName) {
        this.groupId = groupId;
        this.userId = userId;
        this.groupName = groupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", userId=" + userId +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
