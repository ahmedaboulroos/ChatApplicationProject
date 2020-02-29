package eg.gov.iti.jets.models.network.implementations;


import eg.gov.iti.jets.controllers.*;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientService extends UnicastRemoteObject implements ClientInterface {

    private SingleChatViewController singleChatViewController = SingleChatViewController.getInstance();
    private GroupChatViewController groupChatViewController = GroupChatViewController.getInstance();
    private LeftViewController leftViewController = LeftViewController.getInstance();
    private RightViewController rightViewController = RightViewController.getInstance();


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
        Platform.runLater(() -> {
            ClientStageCoordinator.getInstance().displayUserLoginNotification(userId);
        });
    }

    @Override
    public void userLoggedOut(int userId) throws RemoteException {
        Platform.runLater(() -> {
            ClientStageCoordinator.getInstance().displayUserLogoutNotification(userId);
        });
    }

    @Override
    public void serverDisconnected() throws RemoteException {
        Platform.runLater(() -> {
            ClientStageCoordinator.getInstance().displayServerDisconnectedError();
        });
    }

    @Override
    public void receiveUserStatusChanged(int userId) throws RemoteException {
        Platform.runLater(() -> {
            ClientStageCoordinator.getInstance().displayUserStatusChange(userId);
        });
    }

    @Override
    public void receiveNewSingleChat(int singleChatId) throws RemoteException {
        System.out.println(">> New Single Chat :" + singleChatId);
        Platform.runLater(() -> {
            leftViewController.displayNewSingleChat(singleChatId);
        });
    }

    @Override
    public void receiveNewSingleChatMessage(int singleChatMessageId) throws RemoteException {
        System.out.println(">> New Single Chat Message :" + singleChatMessageId);
        Platform.runLater(() -> {
            leftViewController.updateSingleChat(singleChatMessageId);
        });
    }

    @Override
    public void receiveNewGroupChatMessage(int groupChatMessageId) throws RemoteException {
        System.out.println(">> New Group Chat Message :" + groupChatMessageId);
        Platform.runLater(() -> {
            leftViewController.updateGroupChat(groupChatMessageId);
        });
    }

    @Override
    public void receiveNewGroupChatMembership(int groupChatMembershipId) throws RemoteException {
        System.out.println("client srevice >> recieve New Group Chat Membership :" + groupChatMembershipId);
        if (groupChatMembershipId > 0) {
            Platform.runLater(() -> leftViewController.displayNewGroupChat());
        }

    }

    @Override
    public void receiveNewRelationship(int relationshipId) throws RemoteException {
        System.out.println(">> New Relationship :" + relationshipId);
        Platform.runLater(() -> {
            rightViewController.displayRelationship(relationshipId);
            leftViewController.displayRelationship(relationshipId);
        });

    }

    @Override
    public void receiveNewContactsGroup(int groupId) throws RemoteException {
        System.out.println(">> New Contacts Group :" + groupId);
        Platform.runLater(() -> {
            leftViewController.displayContactsGroup(groupId);
            leftViewController.getAddContactGroupViewController().displayContactsGroup(groupId);
        });

    }

    @Override
    public void receiveNewContactsGroupMembership(int contactsGroupMembershipId) throws RemoteException {
        System.out.println(">> New Contacts Group Membership :" + contactsGroupMembershipId);
        Platform.runLater(() -> {
            leftViewController.displayContactsGroupMembership(contactsGroupMembershipId);
            leftViewController.getAddContactGroupViewController()
                    .displayContactsGroupMembership(contactsGroupMembershipId);
        });
    }

    @Override
    public void receiveNewAnnouncement(int announcementId) throws RemoteException {
        Platform.runLater(() -> {
            ClientStageCoordinator.getInstance().displayServerAnnouncement(announcementId);
        });

    }

}
