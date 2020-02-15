package eg.gov.iti.jets.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SingleChatMessage implements Serializable {
    private static final long serialVersionUID = 6569665098267657690L;
    private int singleChatMessageId;
    private int userId;
    private String content;
    private LocalDateTime messageTimestamp;

    public SingleChatMessage(int userId, String content) {
        this.userId = userId;
        this.content = content;
        this.messageTimestamp = LocalDateTime.now();
    }

    public SingleChatMessage(int singleChatMessageId, int userId, String content, LocalDateTime messageTimestamp) {
        this.singleChatMessageId = singleChatMessageId;
        this.userId = userId;
        this.content = content;
        this.messageTimestamp = messageTimestamp;
    }

    public int getSingleChatMessageId() {
        return singleChatMessageId;
    }

    public void setSingleChatMessageId(int singleChatMessageId) {
        this.singleChatMessageId = singleChatMessageId;
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

    @Override
    public String toString() {
        return "SingleChatMessage{" +
                "singleChatMessageId=" + singleChatMessageId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", messageTimestamp=" + messageTimestamp +
                '}';
    }
}