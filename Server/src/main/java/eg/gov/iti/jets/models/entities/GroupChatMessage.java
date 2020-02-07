package eg.gov.iti.jets.models.entities;

import java.time.LocalDateTime;

public class GroupChatMessage {
    private int groupMessageId;
    private int userId;
    private String content;
    private LocalDateTime timestamp;

    public GroupChatMessage(int userId, String content) {
        this.userId = userId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // constructor for populating data from database
    public GroupChatMessage(int groupMessageId, int userId, String content, LocalDateTime timestamp) {
        this.groupMessageId = groupMessageId;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getGroupMessageId() {
        return groupMessageId;
    }

    public void setGroupMessageId(int groupMessageId) {
        this.groupMessageId = groupMessageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
