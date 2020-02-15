package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.entities.AnnouncementDelivery;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AnnouncementDao extends Remote {
    // Create
    boolean createAnnouncement(Announcement announcement) throws RemoteException;

    // Read
    Announcement getAnnouncement(int announcementId) throws RemoteException;

    List<Announcement> getAllAnnouncements() throws RemoteException;

    List<AnnouncementDelivery> getAnnouncementDeliveries(int announcementId) throws RemoteException;

    // Update
    boolean updateAnnouncement(Announcement announcement) throws RemoteException;

    // Delete
    boolean deleteAnnouncement(int announcementId) throws RemoteException;

}
