package eg.gov.iti.jets.models.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AnnouncementsTableModel {
    private SimpleIntegerProperty announcementId;
    private SimpleStringProperty title;
    private SimpleStringProperty announcementTimestamp;


    public AnnouncementsTableModel(int id, String contentData, String timestamp) {
        announcementId = new SimpleIntegerProperty(id);
        title = new SimpleStringProperty(contentData);
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

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    @Override
    public String toString() {

        return (announcementId.get() + ", " + title.get());
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
