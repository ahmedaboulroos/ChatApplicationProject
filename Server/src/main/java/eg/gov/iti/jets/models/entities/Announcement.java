package eg.gov.iti.jets.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Announcement implements Serializable {
    private static final long serialVersionUID = 6529685498269757693L;
    private int id;
    private String title;
    private String content;
    private LocalDateTime sendDateTime;

    public Announcement(String title, String content, LocalDateTime sendDateTime) {
        this.title = title;
        this.content = content;
        this.sendDateTime = sendDateTime;
    }

    public Announcement(int id, String title, String content, LocalDateTime sendDateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.sendDateTime = sendDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(LocalDateTime sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sendDateTime=" + sendDateTime +
                '}';
    }
}
