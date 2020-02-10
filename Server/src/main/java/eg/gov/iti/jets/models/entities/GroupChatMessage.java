package eg.gov.iti.jets.models.entities;

import java.time.LocalDateTime;

public class GroupChatMessage {
    private int groupChatMessageId;
    private int userId;
    private String content;
    private LocalDateTime messageTimestamp;

    public GroupChatMessage(int userId, String content) {
        this.userId = userId;
        this.content = content;
        this.messageTimestamp = LocalDateTime.now();
    }

    // constructor for populating data from database
    public GroupChatMessage(int groupChatMessageId, int userId, String content, LocalDateTime messageTimestamp) {
        this.groupChatMessageId = groupChatMessageId;
        this.userId = userId;
        this.content = content;
        this.messageTimestamp = messageTimestamp;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(LocalDateTime messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }
}
