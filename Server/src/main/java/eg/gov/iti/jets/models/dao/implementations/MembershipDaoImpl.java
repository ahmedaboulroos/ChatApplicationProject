package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.MembershipDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.Membership;
import eg.gov.iti.jets.models.entities.User;

public class MembershipDaoImpl implements MembershipDao {
    @Override
    public boolean createMembership(Membership membership) {
        return false;
    }

    @Override
    public Membership getMembership(int membershipId) {
        return null;
    }

    @Override
    public User getUser(int membershipId) {
        return null;
    }

    @Override
    public GroupChat getGroupChat(int membershipId) {
        return null;
    }

    @Override
    public boolean updateMembership(Membership membership) {
        return false;
    }

    @Override
    public boolean deleteMembership(int membershipId) {
        return false;
    }
}
