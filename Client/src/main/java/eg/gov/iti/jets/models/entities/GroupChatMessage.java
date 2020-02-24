package eg.gov.iti.jets.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GroupChatMessage implements Serializable {
    private static final long serialVersionUID = 6829688898267758698L;
    private int id;
    private int userId;
    private int groupChatId;
    private String content;
    private LocalDateTime messageDateTime;

    public GroupChatMessage(int userId, int groupChatId, String content) {
        this.userId = userId;
        this.groupChatId = groupChatId;
        this.content = content;
        this.messageDateTime = LocalDateTime.now();
    }

    public GroupChatMessage(int id, int userId, int groupChatId, String content, LocalDateTime messageDateTime) {
        this.id = id;
        this.userId = userId;
        this.groupChatId = groupChatId;
        this.content = content;
        this.messageDateTime = messageDateTime;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getMessageDateTime() {
        return messageDateTime;
    }

    public void setMessageDateTime(LocalDateTime messageDateTime) {
        this.messageDateTime = messageDateTime;
    }

    @Override
    public String toString() {
        return "GroupChatMessage{" +
                "groupChatMessageId=" + id +
                ", userId=" + userId +
                ", groupChatId=" + groupChatId +
                ", content='" + content + '\'' +
                ", messageTimestamp=" + messageDateTime +
                '}';
    }
}
