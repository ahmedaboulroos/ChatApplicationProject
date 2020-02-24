package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.Announcement;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AnnouncementDao extends Remote {
    // Create
    int createAnnouncement(Announcement announcement) throws RemoteException;

    // Read
    Announcement getAnnouncement(int announcementId) throws RemoteException;

    List<Announcement> getAllAnnouncements() throws RemoteException;

    // Update
    void updateAnnouncement(Announcement announcement) throws RemoteException;

    // Delete
    void deleteAnnouncement(int announcementId) throws RemoteException;

}
