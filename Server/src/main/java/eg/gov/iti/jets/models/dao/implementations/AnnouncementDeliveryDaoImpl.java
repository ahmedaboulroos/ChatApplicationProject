package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDeliveryDao;
import eg.gov.iti.jets.models.entities.AnnouncementDelivery;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AnnouncementDeliveryDaoImpl extends UnicastRemoteObject implements AnnouncementDeliveryDao {

    protected AnnouncementDeliveryDaoImpl() throws RemoteException {
    }

    @Override
    public boolean createAnnouncementDelivery(AnnouncementDelivery announcementDelivery) {
        return false;
    }

    @Override
    public AnnouncementDelivery getAnnouncementDelivery(int announcementDeliveryId) {
        return null;
    }

    @Override
    public boolean updateAnnouncementDelivery(AnnouncementDelivery announcementDelivery) {
        return false;
    }

    @Override
    public boolean deleteAnnouncementDelivery(int announcementDeliveryId) {
        return false;
    }
}
