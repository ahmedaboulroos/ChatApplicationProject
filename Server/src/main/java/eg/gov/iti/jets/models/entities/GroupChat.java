package eg.gov.iti.jets.models.entities;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GroupChat implements Serializable {
    private int groupChatId;
    private String title;
    private String description;
    private Image groupImage;
    private LocalDateTime creationTimestamp;

    public GroupChat(int groupChatId, String title, String description, Image groupImage) {
        this.groupChatId = groupChatId;
        this.title = title;
        this.description = description;
        this.groupImage = groupImage;
        this.creationTimestamp = LocalDateTime.now();
    }

    public GroupChat(int groupChatId, String title, String description, Image groupImage, LocalDateTime creationTimestamp) {
        this.groupChatId = groupChatId;
        this.title = title;
        this.description = description;
        this.groupImage = groupImage;
        this.creationTimestamp = creationTimestamp;
    }

    public GroupChat(String title) {
        this.title = title;
    }

    public int getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(int groupChatId) {
        this.groupChatId = groupChatId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(Image groupImage) {
        this.groupImage = groupImage;
    }
}
