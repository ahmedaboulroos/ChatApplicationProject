package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.Membership;
import eg.gov.iti.jets.models.entities.User;

public interface MembershipDao {
    // Create
    boolean createMembership(Membership membership);

    // Read
    Membership getMembership(int membershipId);
    User getUser(int membershipId);
    GroupChat getGroupChat(int membershipId);

    // Update
    boolean updateMembership(Membership membership);

    // Delete
    boolean deleteMembership(int membershipId);

}
