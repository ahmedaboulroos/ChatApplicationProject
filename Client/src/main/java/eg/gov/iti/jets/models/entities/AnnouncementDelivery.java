package eg.gov.iti.jets.models.entities;

import eg.gov.iti.jets.models.entities.enums.AnnouncementDeliveryStatus;

import java.io.Serializable;

public class AnnouncementDelivery implements Serializable {
    private static final long serialVersionUID = 6551685598261757691L;
    private int announcementDeliveryId;
    private int userId;
    private int announcementId;
    private AnnouncementDeliveryStatus announcementDeliveryStatus;

    public AnnouncementDelivery(int userId, int announcementId) {
        this.userId = userId;
        this.announcementId = announcementId;
        this.announcementDeliveryStatus = AnnouncementDeliveryStatus.DELIVERED;
    }

    public AnnouncementDelivery(int announcementDeliveryId, int userId, int announcementId, AnnouncementDeliveryStatus announcementDeliveryStatus) {
        this.announcementDeliveryId = announcementDeliveryId;
        this.userId = userId;
        this.announcementId = announcementId;
        this.announcementDeliveryStatus = announcementDeliveryStatus;
    }

    public int getAnnouncementDeliveryId() {
        return announcementDeliveryId;
    }

    public void setAnnouncementDeliveryId(int announcementDeliveryId) {
        this.announcementDeliveryId = announcementDeliveryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public AnnouncementDeliveryStatus getAnnouncementDeliveryStatus() {
        return announcementDeliveryStatus;
    }

    public void setAnnouncementDeliveryStatus(AnnouncementDeliveryStatus announcementDeliveryStatus) {
        this.announcementDeliveryStatus = announcementDeliveryStatus;
    }

    @Override
    public String toString() {
        return "AnnouncementDelivery{" +
                "announcementDeliveryId=" + announcementDeliveryId +
                ", userId=" + userId +
                ", announcementId=" + announcementId +
                ", announcementDeliveryStatus=" + announcementDeliveryStatus +
                '}';
    }
}
