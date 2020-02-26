package eg.gov.iti.jets.models.network.implementations;


import eg.gov.iti.jets.controllers.ClientStageCoordinator;
import eg.gov.iti.jets.controllers.GroupChatViewController;
import eg.gov.iti.jets.controllers.LeftViewController;
import eg.gov.iti.jets.controllers.SingleChatViewController;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientService extends UnicastRemoteObject implements ClientInterface {

    private SingleChatViewController singleChatViewController = SingleChatViewController.getInstance();
    private GroupChatViewController groupChatViewController = GroupChatViewController.getInstance();
    private LeftViewController leftViewController = LeftViewController.getInstance();

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
        ClientStageCoordinator.getInstance().displayUserLoginNotification(userId);
    }

    @Override
    public void userLoggedOut(int userId) throws RemoteException {
        ClientStageCoordinator.getInstance().displayUserLogoutNotification(userId);
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
        //singleChatViewController.displayNewSingleChat(singleChatId);
        leftViewController.displayNewSingleChat(singleChatId);
    }

    @Override
    public void receiveNewSingleChatMessage(int singleChatMessageId) throws RemoteException {
        System.out.println(">> New Single Chat Message :" + singleChatMessageId);
        leftViewController.updateSingleChat(singleChatMessageId);
//        if(singleChatViewController!=null){
//            singleChatViewController.displayNewSingleChatMessage(singleChatMessageId);
//            System.out.println("ana new single chat m4 null");
//        }
    }

    @Override
    public void receiveNewGroupChatMessage(int groupChatMessageId) throws RemoteException {
        System.out.println(">> New Group Chat Message :" + groupChatMessageId);
        groupChatViewController.displayNewGroupChatMessage(groupChatMessageId);
    }

    @Override
    public void receiveNewGroupChatMembership(int groupChatMembershipId) throws RemoteException {
        System.out.println(">> New Group Chat Membership :" + groupChatMembershipId);
    }

    @Override
    public void receiveNewRelationship(int relationshipId) throws RemoteException {
        System.out.println(">> New Relationship :" + relationshipId);
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
