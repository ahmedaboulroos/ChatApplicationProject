package eg.gov.iti.jets.models.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AnnouncementDeliveryModel {

    private SimpleIntegerProperty announcementDeliveryId;
    private SimpleIntegerProperty userId;
    private SimpleIntegerProperty announcementDelId;
    private SimpleStringProperty announcementDeliveryStatus;

    public AnnouncementDeliveryModel(int deliveryID, int Id, int announceId, String status) {

        announcementDeliveryId = new SimpleIntegerProperty(deliveryID);
        announcementDelId = new SimpleIntegerProperty(announceId);
        userId = new SimpleIntegerProperty(Id);
        announcementDeliveryStatus = new SimpleStringProperty(status);

    }

    public int getAnnouncementDeliveryId() {
        return announcementDeliveryId.get();
    }

    public void setAnnouncementDeliveryId(int announcementDeliveryId) {
        this.announcementDeliveryId.set(announcementDeliveryId);
    }

    public SimpleIntegerProperty announcementDeliveryIdProperty() {
        return announcementDeliveryId;
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public int getAnnouncementDelId() {
        return announcementDelId.get();
    }

    public void setAnnouncementDelId(int announcementDelId) {
        this.announcementDelId.set(announcementDelId);
    }

    public SimpleIntegerProperty announcementDelIdProperty() {
        return announcementDelId;
    }

    public String getAnnouncementDeliveryStatus() {
        return announcementDeliveryStatus.get();
    }

    public void setAnnouncementDeliveryStatus(String announcementDeliveryStatus) {
        this.announcementDeliveryStatus.set(announcementDeliveryStatus);
    }

    public SimpleStringProperty announcementDeliveryStatusProperty() {
        return announcementDeliveryStatus;
    }

    @Override
    public String toString() {

        return (announcementDeliveryId.get() + ", " + userId.get() + "," + announcementDelId.get() + "," + announcementDeliveryStatus.get());
    }
}
