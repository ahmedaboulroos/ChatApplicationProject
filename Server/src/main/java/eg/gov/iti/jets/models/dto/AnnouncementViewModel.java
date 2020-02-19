package eg.gov.iti.jets.models.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AnnouncementViewModel {
    private SimpleIntegerProperty announcementId;
    private SimpleStringProperty content;
    private SimpleStringProperty announcementTimestamp;


    public AnnouncementViewModel(int id, String contentData, String timestamp) {
        announcementId = new SimpleIntegerProperty(id);
        content = new SimpleStringProperty(contentData);
        announcementTimestamp = new SimpleStringProperty(timestamp);
    }

    public int getAnnouncementId() {
        return announcementId.get();
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId.set(announcementId);
    }

    public SimpleIntegerProperty announcementIdProperty() {
        return announcementId;
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    @Override
    public String toString() {

        return (announcementId.get() + ", " + content.get());
    }

    public String getAnnouncementTimestamp() {
        return announcementTimestamp.get();
    }

    public void setAnnouncementTimestamp(String announcementTimestamp) {
        this.announcementTimestamp.set(announcementTimestamp);
    }

    public SimpleStringProperty announcementTimestampProperty() {
        return announcementTimestamp;
    }
}
