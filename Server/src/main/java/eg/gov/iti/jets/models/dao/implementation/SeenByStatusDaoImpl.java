package eg.gov.iti.jets.models.dao.implementation;

import eg.gov.iti.jets.models.dao.interfaces.SeenByStatusDao;
import eg.gov.iti.jets.models.entities.SeenByStatus;

public class SeenByStatusDaoImpl implements SeenByStatusDao {
    @Override
    public boolean createSeenByStatus(SeenByStatus seenByStatus) {
        return false;
    }

    @Override
    public SeenByStatus getSeenByStatus(int seenByStatusId) {
        return null;
    }

    @Override
    public boolean updateSeenByStatus(SeenByStatus seenByStatus) {
        return false;
    }

    @Override
    public boolean deleteSeenByStatus(int seenByStatusId) {
        return false;
    }
}
