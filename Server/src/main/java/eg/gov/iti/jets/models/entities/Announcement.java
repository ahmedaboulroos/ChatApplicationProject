package eg.gov.iti.jets.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Announcement implements Serializable {
    private int announcementId;
    private String content;
    private LocalDateTime announcementTimestamp;

    public Announcement(String content) {
        this.content = content;
        this.announcementTimestamp = LocalDateTime.now();
    }

    // constructor for populating data from database
    public Announcement(int announcementId, String content, LocalDateTime announcementTimestamp) {
        this.announcementId = announcementId;
        this.content = content;
        this.announcementTimestamp = announcementTimestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getAnnouncementTimestamp() {
        return announcementTimestamp;
    }

    public void setAnnouncementTimestamp(LocalDateTime announcementTimestamp) {
        this.announcementTimestamp = announcementTimestamp;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }
}
