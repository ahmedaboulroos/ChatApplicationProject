package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.persistence.TDBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.fail;

class AnnouncementDaoImplTest {

    private static AnnouncementDao announcementDao;

    @BeforeAll
    static void beforeAll() {
        if (TDBConnection.getInstance().initConnection()) {
            announcementDao = AnnouncementDaoImpl.getInstance();
        } else {
            fail(">> Couldn't Connect to Database...");
        }
    }

    @AfterAll
    static void afterAll() {
        if (announcementDao != null) {
            TDBConnection.getInstance().stopConnection();
        } else {
            fail(">> Couldn't even Connect to Database...");
        }
    }

    @Test
    void createAnnouncement() {
        try {
            Announcement announcement = new Announcement(12345, "this is my announcement", LocalDateTime.now());
            announcementDao.createAnnouncement(announcement);
            Announcement dbAnnouncement = announcementDao.getAnnouncement(12345);
            Assertions.assertNotNull(dbAnnouncement);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAnnouncement() {

    }

    @Test
    void getAnnouncementDeliveries() {
    }

    @Test
    void updateAnnouncement() {
    }

    @Test
    void deleteAnnouncement() {
    }

    @Test
    void getAllAnnouncements() {
    }
}
