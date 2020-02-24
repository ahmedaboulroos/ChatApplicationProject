package eg.gov.iti.jets.models.entities;

import java.io.Serializable;

public class ContactsGroupMembership implements Serializable {
    private static final long serialVersionUID = 6729686078767757697L;
    private int id;
    private int userId;
    private int contactsGroupId;

    public ContactsGroupMembership(int userId, int contactsGroupId) {
        this.userId = userId;
        this.contactsGroupId = contactsGroupId;
    }

    public ContactsGroupMembership(int id, int userId, int contactsGroupId) {
        this.id = id;
        this.userId = userId;
        this.contactsGroupId = contactsGroupId;
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

    public int getContactsGroupId() {
        return contactsGroupId;
    }

    public void setContactsGroupId(int contactsGroupId) {
        this.contactsGroupId = contactsGroupId;
    }

    @Override
    public String toString() {
        return "GroupContact{" +
                "groupContactId=" + id +
                ", userId=" + userId +
                ", groupId=" + contactsGroupId +
                '}';
    }
}
