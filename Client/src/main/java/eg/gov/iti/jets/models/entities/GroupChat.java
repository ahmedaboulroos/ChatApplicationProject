package eg.gov.iti.jets.models.entities;

import java.io.Serializable;
import java.sql.Timestamp;

public class GroupChat implements Serializable {
    private static final long serialVersionUID = 3529985099267957690L;
    private int groupChatId;
    private String title;
    private String description;
    private byte[] groupImageBytes;
    private Timestamp creationTimestamp;


    public GroupChat(String title, String description) {
        this.title = title;
        this.description = description;
        this.creationTimestamp = new Timestamp(System.currentTimeMillis());
    }

    public GroupChat(String title, String description, byte[] groupImageBytes) {
        this.title = title;
        this.description = description;
        this.groupImageBytes = groupImageBytes;
        this.creationTimestamp = new Timestamp(System.currentTimeMillis());
    }


    public GroupChat(String title, String description, byte[] groupImageBytes, Timestamp creationTimestamp) {
        this.title = title;
        this.description = description;
        this.groupImageBytes = groupImageBytes;
        this.creationTimestamp = creationTimestamp;
    }

    public GroupChat(int groupChatId, String title, String description, byte[] groupImageBytes, Timestamp creationTimestamp) {
        this.groupChatId = groupChatId;
        this.title = title;
        this.description = description;
        this.groupImageBytes = groupImageBytes;
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

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
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

    @Override
    public String toString() {
        return "GroupChat{" +
                "groupChatId=" + groupChatId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", groupImageEncodedString=" + groupImageBytes.length +
                ", creationTimestamp=" + creationTimestamp +
                '}';
    }
}
