package eg.gov.iti.jets.views.models;

import eg.gov.iti.jets.models.entities.AnnouncementDelivery;
import eg.gov.iti.jets.models.entities.enums.AnnouncementDeliveryStatus;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDeliveryModel {

    private SimpleIntegerProperty announcementDeliveryId;
    private SimpleIntegerProperty userId;
    private SimpleIntegerProperty announcementId;
    private SimpleStringProperty announcementDeliveryStatus;

    public AnnouncementDeliveryModel(int deliveryID, int Id, int announceId, String status) {

        announcementDeliveryId = new SimpleIntegerProperty(deliveryID);
        announcementId = new SimpleIntegerProperty(announceId);
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

    public int getAnnouncementId() {
        return announcementId.get();
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId.set(announcementId);
    }

    public SimpleIntegerProperty announcementIdProperty() {
        return announcementId;
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

        return (announcementDeliveryId.get() + ", " + userId.get() + "," + announcementId.get() + "," + announcementDeliveryStatus.get());
    }
}