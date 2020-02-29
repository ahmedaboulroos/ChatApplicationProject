package eg.gov.iti.jets.models.network.implementations;


import eg.gov.iti.jets.controllers.*;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientService extends UnicastRemoteObject implements ClientInterface {

    private SingleChatViewController singleChatViewController = SingleChatViewController.getInstance();
    private GroupChatViewController groupChatViewController = GroupChatViewController.getInstance();
    private LeftViewController leftViewController = LeftViewController.getInstance();
    private RightViewController rightViewController = RightViewController.getInstance();
    private UserDao userDao = RMIConnection.getUserDao();

    private ClientService() throws RemoteException {
    }

    private static ClientService instance;

    public static ClientService getInstance() {
        if (instance == null) {
            try {
                instance = new ClientService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public void userLoggedIn(int userId) throws RemoteException {
        User user = userDao.getUser(userId);
        if (user.getUsername() != null) {
            ClientStageCoordinator.getInstance().displayUserLoginNotification(user.getUsername());
        } else {

            ClientStageCoordinator.getInstance().displayUserLoginNotification(user.getPhoneNumber());
        }

    }

    @Override
    public void userLoggedOut(int userId) throws RemoteException {


        User user = userDao.getUser(userId);
        if (user.getUsername() != null) {
            ClientStageCoordinator.getInstance().displayUserLogoutNotification(user.getUsername());
        } else {
            ClientStageCoordinator.getInstance().displayUserLogoutNotification(user.getPhoneNumber());
        }

    }

    @Override
    public void serverDisconnected() throws RemoteException {
        ClientStageCoordinator.getInstance().displayServerDisconnectedError();
    }

    @Override
    public void receiveUserStatusChanged(int userId) throws RemoteException {
        ClientStageCoordinator.getInstance().displayUserStatusChange(userId);
    }

    @Override
    public void receiveNewSingleChat(int singleChatId) throws RemoteException {
        System.out.println(">> New Single Chat :" + singleChatId);
        leftViewController.displayNewSingleChat(singleChatId);
    }

    @Override
    public void receiveNewSingleChatMessage(int singleChatMessageId) throws RemoteException {
        System.out.println(">> New Single Chat Message :" + singleChatMessageId);
        leftViewController.updateSingleChat(singleChatMessageId);
    }

    @Override
    public void receiveNewGroupChatMessage(int groupChatMessageId) throws RemoteException {
        System.out.println(">> New Group Chat Message :" + groupChatMessageId);
        leftViewController.updateGroupChat(groupChatMessageId);
    }


    @Override
    public void receiveNewGroupChatMembership(int groupChatMembershipId) throws RemoteException {
        System.out.println("client srevice >> recieve New Group Chat Membership :" + groupChatMembershipId);
        if (groupChatMembershipId > 0) {
            leftViewController.displayNewGroupChat();
        }

    }

    @Override
    public void receiveNewRelationship(int relationshipId) throws RemoteException {
        System.out.println(">> New Relationship :" + relationshipId);
        rightViewController.displayRelationship(relationshipId);
        leftViewController.displayRelationship(relationshipId);
    }

    @Override
    public void receiveNewContactsGroup(int groupId) throws RemoteException {
        System.out.println(">> New Contacts Group :" + groupId);
        leftViewController.displayContactsGroup(groupId);
        leftViewController.getAddContactGroupViewController().displayContactsGroup(groupId);
    }

    @Override
    public void receiveNewContactsGroupMembership(int contactsGroupMembershipId) throws RemoteException {
        System.out.println(">> New Contacts Group Membership :" + contactsGroupMembershipId);
        leftViewController.displayContactsGroupMembership(contactsGroupMembershipId);
        leftViewController.getAddContactGroupViewController()
                .displayContactsGroupMembership(contactsGroupMembershipId);
    }

    @Override
    public void receiveNewAnnouncement(int announcementId) throws RemoteException {
        ClientStageCoordinator.getInstance().displayServerAnnouncement(announcementId);
    }

}
