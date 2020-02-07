package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.SeenByStatus;

public interface SeenByStatusDao {

    // Create
    boolean createSeenByStatus(SeenByStatus seenByStatus);

    // Read
    SeenByStatus getSeenByStatus(int seenByStatusId);

    // Update
    boolean updateSeenByStatus(SeenByStatus seenByStatus);

    // Delete
    boolean deleteSeenByStatus(int seenByStatusId);

}
