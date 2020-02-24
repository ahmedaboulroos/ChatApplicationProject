package eg.gov.iti.jets.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SingleChatMessage implements Serializable {
    private static final long serialVersionUID = 6569665098267657690L;
    private int id;
    private int singleChatId;
    private int userId;
    private String content;
    private LocalDateTime messageDateTime;

    public SingleChatMessage(int singleChatId, int userId, String content) {
        this.singleChatId = singleChatId;
        this.userId = userId;
        this.content = content;
        this.messageDateTime = LocalDateTime.now();
    }

    public SingleChatMessage(int singleChatId, int userId, String content, LocalDateTime messageDateTime) {
        this.singleChatId = singleChatId;
        this.userId = userId;
        this.content = content;
        this.messageDateTime = messageDateTime;
    }

    public SingleChatMessage(int id, int singleChatId, int userId, String content, LocalDateTime messageDateTime) {
        this.id = id;
        this.singleChatId = singleChatId;
        this.userId = userId;
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
        return "SingleChatMessage{" +
                "singleChatMessageId=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", messageTimestamp=" + messageDateTime +
                '}';
    }

    public int getSingleChatId() {
        return singleChatId;
    }

    public void setSingleChatId(int singleChatId) {
        this.singleChatId = singleChatId;
    }
}
