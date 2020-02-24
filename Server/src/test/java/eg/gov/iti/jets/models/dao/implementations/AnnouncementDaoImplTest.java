package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.persistence.TDBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AnnouncementDaoImplTest {

    private static AnnouncementDao announcementDao;

    @BeforeAll
    static void beforeAll() {
        announcementDao = AnnouncementDaoImpl.getInstance(TDBConnection.getConnection());
        if (announcementDao == null) {
            fail(">> Couldn't Connect to Database...");
        }
    }

    @AfterAll
    static void afterAll() {
        if (announcementDao != null) {
            TDBConnection.stopConnection();
        } else {
            fail(">> Couldn't even Connect to Database...");
        }
    }

    @Test
    void testingAnnouncementCRUD() {
        try {
            // Create
            Announcement announcement = new Announcement("Testaro", "Testaro", LocalDateTime.of(1991, 1, 1, 1, 11));
            int id = announcementDao.createAnnouncement(announcement);
            assertNotEquals(-1, id);

            // Read
            Announcement dbAnnouncement = announcementDao.getAnnouncement(id);
            assertNotNull(dbAnnouncement);
            assertEquals("Testaro", dbAnnouncement.getTitle());
            assertEquals("Testaro", dbAnnouncement.getContent());
            assertEquals(LocalDateTime.of(1991, 1, 1, 1, 11), dbAnnouncement.getSendDateTime());

            // Update
            dbAnnouncement.setTitle("Amino");
            dbAnnouncement.setContent("Amino");
            dbAnnouncement.setSendDateTime(LocalDateTime.of(1995, 6, 24, 6, 24));
            announcementDao.updateAnnouncement(dbAnnouncement);
            Announcement updatedAnnouncement = announcementDao.getAnnouncement(id);
            assertEquals("Amino", updatedAnnouncement.getTitle());
            assertEquals("Amino", updatedAnnouncement.getContent());
            assertEquals(LocalDateTime.of(1995, 6, 24, 6, 24), updatedAnnouncement.getSendDateTime());

            // Delete
            announcementDao.deleteAnnouncement(id);
            assertNull(announcementDao.getAnnouncement(id));

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
