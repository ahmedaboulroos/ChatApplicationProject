package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.Group;
import eg.gov.iti.jets.models.entities.GroupContact;

import java.util.List;

public interface GroupDao {

    // Create
    boolean createGroup(Group group);

    // Read
    Group getGroup(int groupId);
    List<GroupContact> getGroupContacts(int groupId);

    // Update
    boolean updateGroup(Group group);

    // Delete
    boolean deleteGroup(int groupId);

}
