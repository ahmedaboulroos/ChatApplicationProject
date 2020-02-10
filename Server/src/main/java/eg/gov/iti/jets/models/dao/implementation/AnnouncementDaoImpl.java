package eg.gov.iti.jets.models.dao.implementation;

import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.entities.AnnouncementDelivery;

import java.util.List;

public class AnnouncementDaoImpl implements AnnouncementDao {
    @Override
    public boolean createAnnouncement(Announcement announcement) {
        return false;
    }

    @Override
    public Announcement getAnnouncement(int announcementId) {
        return null;
    }

    @Override
    public List<AnnouncementDelivery> getAnnouncementDeliveries(int announcementId) {
        return null;
    }

    @Override
    public boolean updateAnnouncement(Announcement announcement) {
        return false;
    }

    @Override
    public boolean deleteAnnouncement(int announcementId) {
        return false;
    }
}
