package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.persistence.TDBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class AnnouncementDeliveryDaoImplTest {

    private static AnnouncementDeliveryDao announcementDeliveryDao;

    @BeforeAll
    static void beforeAll() {
        announcementDeliveryDao = AnnouncementDeliveryDaoImpl.getInstance(TDBConnection.getConnection());
        if (announcementDeliveryDao == null) {
            fail(">> Couldn't Connect to Database...");
        }
    }

    @AfterAll
    static void afterAll() {
        if (announcementDeliveryDao != null) {
            TDBConnection.stopConnection();
        } else {
            fail(">> Couldn't even Connect to Database...");
        }
    }

    @Test
    void testingCreateRetrieveAnAnnouncementDelivery() {
    }

    @Test
    void testingRetrieveDeleteAllAnnouncementDeliveries() {
    }

    @Test
    void testingCreateUpdateRetrieveAnAnnouncementDelivery() {
    }


}
