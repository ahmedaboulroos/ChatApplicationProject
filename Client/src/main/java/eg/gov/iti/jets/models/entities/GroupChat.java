package eg.gov.iti.jets.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GroupChat implements Serializable {
    private static final long serialVersionUID = 3529985099267957690L;
    private int id;
    private String title;
    private String description;
    private byte[] groupImageBytes;
    private LocalDateTime creationDateTime;

    public GroupChat(String title, String description, byte[] groupImageBytes, LocalDateTime creationDateTime) {
        this.title = title;
        this.description = description;
        this.groupImageBytes = groupImageBytes;
        this.creationDateTime = creationDateTime;
    }

    public GroupChat(int id, String title, String description, byte[] groupImageBytes, LocalDateTime creationDateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.groupImageBytes = groupImageBytes;
        this.creationDateTime = creationDateTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getGroupImageBytes() {
        return groupImageBytes;
    }

    public void setGroupImageBytes(byte[] groupImageBytes) {
        this.groupImageBytes = groupImageBytes;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
