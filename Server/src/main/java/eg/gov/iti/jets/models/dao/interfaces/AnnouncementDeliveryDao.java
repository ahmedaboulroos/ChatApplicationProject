package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.AnnouncementDelivery;

import java.rmi.Remote;

public interface AnnouncementDeliveryDao extends Remote {
    // Create
    boolean createAnnouncementDelivery(AnnouncementDelivery announcementDelivery);

    // Read
    AnnouncementDelivery getAnnouncementDelivery(int announcementDeliveryId);

    // Update
    boolean updateAnnouncementDelivery(AnnouncementDelivery announcementDelivery);

    // Delete
    boolean deleteAnnouncementDelivery(int announcementDeliveryId);
}
