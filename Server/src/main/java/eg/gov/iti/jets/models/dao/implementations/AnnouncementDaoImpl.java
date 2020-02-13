package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.entities.AnnouncementDelivery;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AnnouncementDaoImpl extends UnicastRemoteObject implements AnnouncementDao {

    protected AnnouncementDaoImpl() throws RemoteException {
    }

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
