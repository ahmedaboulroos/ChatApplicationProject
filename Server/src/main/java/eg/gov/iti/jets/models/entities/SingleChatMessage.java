package eg.gov.iti.jets.models.entities;

import java.time.LocalDateTime;

public class SingleChatMessage {
    private int singleMessageId;
    private int userId;
    private String content;
    private LocalDateTime timestamp;

    public SingleChatMessage(int userId, String content) {
        this.userId = userId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // constructor for populating data from database
    public SingleChatMessage(int singleMessageId, int userId, String content, LocalDateTime timestamp) {
        this.singleMessageId = singleMessageId;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getSingleMessageId() {
        return singleMessageId;
    }

    public void setSingleMessageId(int singleMessageId) {
        this.singleMessageId = singleMessageId;
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
