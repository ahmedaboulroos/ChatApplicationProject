package eg.gov.iti.jets.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GroupChatMessage implements Serializable {
    private static final long serialVersionUID = 6829688898267758698L;
    private int groupChatMessageId;
    private int userId;
    private int groupChatId;
    private String content;
    private LocalDateTime messageTimestamp;

    public GroupChatMessage(int userId, int groupChatId, String content) {
        this.userId = userId;
        this.groupChatId = groupChatId;
        this.content = content;
        this.messageTimestamp = LocalDateTime.now();
    }

    public GroupChatMessage(int groupChatMessageId, int userId, int groupChatId, String content, LocalDateTime messageTimestamp) {
        this.groupChatMessageId = groupChatMessageId;
        this.userId = userId;
        this.groupChatId = groupChatId;
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

    public int getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(int groupChatId) {
        this.groupChatId = groupChatId;
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

    @Override
    public String toString() {
        return "GroupChatMessage{" +
                "groupChatMessageId=" + groupChatMessageId +
                ", userId=" + userId +
                ", groupChatId=" + groupChatId +
                ", content='" + content + '\'' +
                ", messageTimestamp=" + messageTimestamp +
                '}';
    }
}
