package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.network.implementations.ClientService;
import eg.gov.iti.jets.views.ChatAppViewController;

import java.rmi.RemoteException;

public class MainController {
    private AnnouncementController announcementController;
    private AnnouncementDeliveryController announcementDeliveryController;
    private GroupChatController groupChatController;
    private GroupChatMessageController groupChatMessageController;
    private GroupContactController groupContactController;
    private GroupController groupController;
    private MembershipController membershipController;
    private RelationshipController relationshipController;
    private SeenByStatusController seenByStatusController;
    private SingleChatController singleChatController;
    private SingleChatMessageController singleChatMessageController;
    private UserController userController;

    private ClientService clientService;
    private ChatAppViewController chatAppViewController;

    public MainController(ChatAppViewController chatAppViewController) {
        this.chatAppViewController = chatAppViewController;
        chatAppViewController.setController(this);
        try {
            this.clientService = new ClientService(this);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        initializeControllers();
    }

    private void initializeControllers() {
        announcementController = new AnnouncementController();
        announcementDeliveryController = new AnnouncementDeliveryController();
        groupChatController = new GroupChatController();
        groupChatMessageController = new GroupChatMessageController();
        groupContactController = new GroupContactController();
        groupController = new GroupController();
        membershipController = new MembershipController();
        relationshipController = new RelationshipController();
        seenByStatusController = new SeenByStatusController();
        singleChatController = new SingleChatController();
        singleChatMessageController = new SingleChatMessageController();
        userController = new UserController();
    }

    public AnnouncementController getAnnouncementController() {
        return announcementController;
    }

    public AnnouncementDeliveryController getAnnouncementDeliveryController() {
        return announcementDeliveryController;
    }

    public GroupChatController getGroupChatController() {
        return groupChatController;
    }

    public GroupChatMessageController getGroupChatMessageController() {
        return groupChatMessageController;
    }

    public GroupContactController getGroupContactController() {
        return groupContactController;
    }

    public GroupController getGroupController() {
        return groupController;
    }

    public MembershipController getMembershipController() {
        return membershipController;
    }

    public RelationshipController getRelationshipController() {
        return relationshipController;
    }

    public SeenByStatusController getSeenByStatusController() {
        return seenByStatusController;
    }

    public SingleChatController getSingleChatController() {
        return singleChatController;
    }

    public SingleChatMessageController getSingleChatMessageController() {
        return singleChatMessageController;
    }

    public UserController getUserController() {
        return userController;
    }

    public ChatAppViewController getMainChatAppViewController() {
        return chatAppViewController;
    }

    public void displayMsg(String hi) {
        chatAppViewController.displayMsg(hi);
    }

    public void sendMsg(String hi_hi) {
        System.out.println(hi_hi);
    }
}
