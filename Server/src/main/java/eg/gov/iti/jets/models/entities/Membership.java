package eg.gov.iti.jets.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Membership implements Serializable {
    private int membershipId;
    private int userId;
    private int groupChatId;
    private LocalDateTime joinedTimestamp;

    public Membership(int userId, int groupChatId) {
        this.userId = userId;
        this.groupChatId = groupChatId;
        this.joinedTimestamp = LocalDateTime.now();
    }

    // constructor for populating data from database
    public Membership(int membershipId, int userId, int groupChatId, LocalDateTime joinedTimestamp) {
        this.membershipId = membershipId;
        this.userId = userId;
        this.groupChatId = groupChatId;
        this.joinedTimestamp = joinedTimestamp;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
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
