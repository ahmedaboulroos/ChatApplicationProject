package eg.gov.iti.jets.models.entities;

import java.time.LocalDateTime;

public class Membership {
    private int groupChatUserId;
    private int userId;
    private int groupChatId;
    private LocalDateTime joinedTimestamp;

    public Membership(int userId, int groupChatId) {
        this.userId = userId;
        this.groupChatId = groupChatId;
        this.joinedTimestamp = LocalDateTime.now();
    }

    // constructor for populating data from database
    public Membership(int groupChatUserId, int userId, int groupChatId, LocalDateTime joinedTimestamp) {
        this.groupChatUserId = groupChatUserId;
        this.userId = userId;
        this.groupChatId = groupChatId;
        this.joinedTimestamp = joinedTimestamp;
    }

    public int getGroupChatUserId() {
        return groupChatUserId;
    }

    public void setGroupChatUserId(int groupChatUserId) {
        this.groupChatUserId = groupChatUserId;
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

    public LocalDateTime getJoinedTimestamp() {
        return joinedTimestamp;
    }

    public void setJoinedTimestamp(LocalDateTime joinedTimestamp) {
        this.joinedTimestamp = joinedTimestamp;
    }
}
