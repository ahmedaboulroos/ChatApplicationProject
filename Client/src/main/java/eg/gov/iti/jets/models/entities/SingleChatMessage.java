package eg.gov.iti.jets.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SingleChatMessage implements Serializable {
    private static final long serialVersionUID = 6569665098267657690L;
    private int id;
    private int singleChatId;
    private int userId;
    private String content;
    private LocalDateTime messageTimestamp;

    public SingleChatMessage(int singleChatId, int userId, String content) {
        this.singleChatId = singleChatId;
        this.userId = userId;
        this.content = content;
        this.messageTimestamp = LocalDateTime.now();
    }

    public SingleChatMessage(int singleChatId, int userId, String content, LocalDateTime messageTimestamp) {
        this.singleChatId = singleChatId;
        this.userId = userId;
        this.content = content;
        this.messageTimestamp = messageTimestamp;
    }

    public SingleChatMessage(int id, int singleChatId, int userId, String content, LocalDateTime messageTimestamp) {
        this.id = id;
        this.singleChatId = singleChatId;
        this.userId = userId;
        this.content = content;
        this.messageTimestamp = messageTimestamp;
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
                "singleChatMessageId=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", messageTimestamp=" + messageTimestamp +
                '}';
    }

    public int getSingleChatId() {
        return singleChatId;
    }

    public void setSingleChatId(int singleChatId) {
        this.singleChatId = singleChatId;
    }
}
