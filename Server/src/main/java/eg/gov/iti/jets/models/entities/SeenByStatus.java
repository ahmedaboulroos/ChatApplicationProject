package eg.gov.iti.jets.models.entities;

import java.io.Serializable;

public class SeenByStatus implements Serializable {
    private int seenByStatusId;
    private int groupChatMessageId;
    private int userId;

    public SeenByStatus(int groupChatMessageId, int userId) {
        this.groupChatMessageId = groupChatMessageId;
        this.userId = userId;
    }

    public SeenByStatus(int seenByStatusId, int groupChatMessageId, int userId) {
        this.seenByStatusId = seenByStatusId;
        this.groupChatMessageId = groupChatMessageId;
        this.userId = userId;
    }

    public int getSeenByStatusId() {
        return seenByStatusId;
    }

    public void setSeenByStatusId(int seenByStatusId) {
        this.seenByStatusId = seenByStatusId;
    }

    public int getGroupChatMessageId() {
        return groupChatMessageId;
    }

    public void setGroupChatMessageId(int groupChatMessageId) {
        this.groupChatMessageId = groupChatMessageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
