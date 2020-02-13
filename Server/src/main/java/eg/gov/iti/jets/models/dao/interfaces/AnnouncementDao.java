package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.entities.AnnouncementDelivery;

import java.rmi.Remote;
import java.util.List;

public interface AnnouncementDao extends Remote {
    // Create
    boolean createAnnouncement(Announcement announcement);

    // Read
    Announcement getAnnouncement(int announcementId);

    List<AnnouncementDelivery> getAnnouncementDeliveries(int announcementId);

    // Update
    boolean updateAnnouncement(Announcement announcement);

    // Delete
    boolean deleteAnnouncement(int announcementId);

}
