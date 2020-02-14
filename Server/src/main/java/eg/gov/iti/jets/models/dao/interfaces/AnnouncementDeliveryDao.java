package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.AnnouncementDelivery;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AnnouncementDeliveryDao extends Remote {
    // Create
    boolean createAnnouncementDelivery(AnnouncementDelivery announcementDelivery) throws RemoteException;

    // Read
    AnnouncementDelivery getAnnouncementDelivery(int announcementDeliveryId) throws RemoteException;

    // Update
    boolean updateAnnouncementDelivery(AnnouncementDelivery announcementDelivery) throws RemoteException;

    // Delete
    boolean deleteAnnouncementDelivery(int announcementDeliveryId) throws RemoteException;
}
