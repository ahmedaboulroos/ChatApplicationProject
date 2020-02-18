package eg.gov.iti.jets.models.entities;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GroupChat implements Serializable {
    private static final long serialVersionUID = 3529985099267957690L;
    private int groupChatId;
    private String title;
    private String description;
    private Image groupImage;
    private LocalDateTime creationTimestamp;

    public GroupChat(String title, String description) {
        this.title = title;
        this.description = description;
        this.creationTimestamp = LocalDateTime.now();
    }

    public GroupChat(String title, String description, Image groupImage) {
        this.title = title;
        this.description = description;
        this.groupImage = groupImage;
        this.creationTimestamp = LocalDateTime.now();
    }

    public GroupChat(String title, String description, Image groupImage, LocalDateTime creationTimestamp) {
        this.title = title;
        this.description = description;
        this.groupImage = groupImage;
        this.creationTimestamp = creationTimestamp;
    }

    public GroupChat(int groupChatId, String title, String description, Image groupImage, LocalDateTime creationTimestamp) {
        this.groupChatId = groupChatId;
        this.title = title;
        this.description = description;
        this.groupImage = groupImage;
        this.creationTimestamp = creationTimestamp;
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

    @Override
    public String toString() {
        return "GroupChat{" +
                "groupChatId=" + groupChatId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", groupImage=" + groupImage +
                ", creationTimestamp=" + creationTimestamp +
                '}';
    }
}
