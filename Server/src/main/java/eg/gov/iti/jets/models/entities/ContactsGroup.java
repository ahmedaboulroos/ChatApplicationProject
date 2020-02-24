package eg.gov.iti.jets.models.entities;

import java.io.Serializable;

public class ContactsGroup implements Serializable {
    private static final long serialVersionUID = 6539685698278757695L;
    private int id;
    private int userId;
    private String groupName;

    public ContactsGroup(int userId, String groupName) {
        this.userId = userId;
        this.groupName = groupName;
    }

    public ContactsGroup(int id, int userId, String groupName) {
        this.id = id;
        this.userId = userId;
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "groupId=" + id +
                ", userId=" + userId +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
