package eg.gov.iti.jets.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GroupChatMembership implements Serializable {
    private static final long serialVersionUID = 6524685498264754694L;
    private int id;
    private int userId;
    private int groupChatId;
    private LocalDateTime joinedDateTime;

    public GroupChatMembership(int userId, int groupChatId) {
        this.userId = userId;
        this.groupChatId = groupChatId;
        this.joinedDateTime = LocalDateTime.now();
    }

    public GroupChatMembership(int id, int userId, int groupChatId, LocalDateTime joinedDateTime) {
        this.id = id;
        this.userId = userId;
        this.groupChatId = groupChatId;
        this.joinedDateTime = joinedDateTime;
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

    public int getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(int groupChatId) {
        this.groupChatId = groupChatId;
    }

    public LocalDateTime getJoinedDateTime() {
        return joinedDateTime;
    }

    public void setJoinedDateTime(LocalDateTime joinedDateTime) {
        this.joinedDateTime = joinedDateTime;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "membershipId=" + id +
                ", userId=" + userId +
                ", groupChatId=" + groupChatId +
                ", joinedTimestamp=" + joinedDateTime +
                '}';
    }
}
