package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.GroupContact;

public interface GroupContactDao {

    // Create
    boolean createGroupContact(GroupContact groupContact);

    // Read
    GroupContact getGroupContact(int groupContactId);

    // Update
    boolean updateGroupContact(GroupContact groupContact);

    // Delete
    boolean deleteGroupContact(int groupContactId);

}
