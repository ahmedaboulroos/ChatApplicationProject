package eg.gov.iti.jets.models.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AnnouncementsTableModel {
    private SimpleIntegerProperty announcementId;
    private SimpleStringProperty announcementTitle;
    private SimpleStringProperty announcementTimestamp;


    public AnnouncementsTableModel(int id, String contentData, String timestamp) {
        announcementId = new SimpleIntegerProperty(id);
        announcementTitle = new SimpleStringProperty(contentData);
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

    public String getAnnouncementTitle() {
        return announcementTitle.get();
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle.set(announcementTitle);
    }

    public SimpleStringProperty announcementTitleProperty() {
        return announcementTitle;
    }

    @Override
    public String toString() {

        return (announcementId.get() + ", " + announcementTitle.get());
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
