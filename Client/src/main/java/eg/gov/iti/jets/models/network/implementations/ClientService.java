package eg.gov.iti.jets.models.network.implementations;


import eg.gov.iti.jets.controllers.ChatAppViewController;
import eg.gov.iti.jets.controllers.SingleChatViewController;
import eg.gov.iti.jets.models.dao.interfaces.GroupContactDao;
import eg.gov.iti.jets.models.dao.interfaces.GroupDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.dto.GroupDto;
import eg.gov.iti.jets.models.dto.UserDto;
import eg.gov.iti.jets.models.entities.Group;
import eg.gov.iti.jets.models.entities.GroupContact;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

public class ClientService extends UnicastRemoteObject implements ClientInterface {
    private ChatAppViewController chatAppViewController;
    private SingleChatViewController singleChatViewController;
    /* private SingleChatMessageController singleChatMessageController;
        /* private UserController userController;
         private SingleChatController singleChatController;
         private SeenByStatusController seenByStatusController;
         private RelationshipController relationshipController;
         private MembershipController membershipController;
         private GroupController groupController;
         private GroupContactController groupContactController;
         private GroupChatMessageController groupChatMessageController;
         private GroupChatController groupChatController;
         private AnnouncementDeliveryController announcementDeliveryController;
         private AnnouncementController announcementController;
     */
    private GroupDao groupDao = RMIConnection.getInstance().getGroupDao();
    private GroupContactDao groupContactDao = RMIConnection.getInstance().getGroupContactDao();
    private UserDao userDao = RMIConnection.getInstance().getUserDao();

    public ClientService() throws RemoteException {
    }

    /*public ClientService(MainController mainController) throws RemoteException {
        this.mainController = mainController;
        //mainController.displayMsg("HI");
        receiveGroup(1);
    }*/

    /*public ClientService(SingleChatMessageController singleChatMessageController) throws RemoteException {
        this.singleChatMessageController = singleChatMessageController;
        //mainController.displayMsg("HI");
    }*/

    public ClientService(int port) throws RemoteException {
        super(port);
    }

    public ClientService(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    /*public ClientService(AnnouncementController announcementController, AnnouncementDeliveryController announcementDeliveryController,
                         GroupChatController groupChatController, GroupChatMessageController groupChatMessageController,
                         GroupContactController groupContactController, GroupController groupController,
                         MembershipController membershipController, RelationshipController relationshipController,
                         SeenByStatusController seenByStatusController, SingleChatController singleChatController,
                         SingleChatMessageController singleChatMessageController, UserController userController) throws RemoteException {
        super();
        this.announcementController = announcementController;
        this.announcementDeliveryController = announcementDeliveryController;
        this.groupChatController = groupChatController;
        this.groupChatMessageController = groupChatMessageController;
        this.groupContactController = groupContactController;
        this.groupController = groupController;
        this.membershipController = membershipController;
        this.relationshipController = relationshipController;
        this.seenByStatusController = seenByStatusController;
        this.singleChatController = singleChatController;
        this.singleChatMessageController = singleChatMessageController;
        this.userController = userController;
    }*/

    public ClientService(ChatAppViewController chatAppViewController) throws RemoteException {
        super();
        this.chatAppViewController = chatAppViewController;
    }

    @Override
    public void userLoggedIn(int userId) throws RemoteException {

    }

    @Override
    public void userLoggedOut(int userId) throws RemoteException {

    }

    @Override
    public void receiveNewSingleChat(int singleChatId) throws RemoteException {

    }

    @Override
    public void receiveNewSingleChatMessage(int singleChatMessageId) throws RemoteException {
        SingleChatMessage singleChatMessage = RMIConnection.getInstance().getSingleChatMessageDao().getSingleChatMessage(singleChatMessageId);
        //singleChatMessageController.displayNewSingleChatMessage(singleChatMessage);
        singleChatViewController.displayNewSingleChatMessage(singleChatMessage);
    }

    @Override
    public void receiveNewGroupChat(int groupChatId) throws RemoteException {

    }

    @Override
    public void receiveNewGroupChatMessage(int groupChatMessageId) throws RemoteException {

    }

    @Override
    public void receiveNewSeenByStatus(int seenByStatusId) throws RemoteException {

    }

    @Override
    public void receiveRelationship(int relationshipId) throws RemoteException {

    }

    @Override
    public void receiveMembership(int membershipId) throws RemoteException {

    }

    @Override
    public void receiveGroup(int groupId) throws RemoteException {
        Group group = groupDao.getGroup(groupId);
        List<GroupContact> groupContactList = groupDao.getGroupContacts(groupId);
        List<UserDto> groupUsers = groupContactList.stream()
                .map(groupContact -> groupContact.getUserId())
                .map(userId -> {
                    User user = null;
                    try {
                        user = userDao.getUser(userId);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    return user;
                })
                .map(user -> new UserDto(getDisplayUsername(user), user.getProfileImage()))
                .collect(Collectors.toList());
        chatAppViewController.displayNewGroup(new GroupDto(group.getGroupName(), groupUsers));
    }

    private String getDisplayUsername(User user) {
        return user.getUsername() == null ? user.getPhoneNumber() : user.getUsername();
    }

    @Override
    public void receiveGroupContact(int groupContactId) throws RemoteException {

    }

    @Override
    public void receiveAnnouncement(int announcementId) throws RemoteException {

    }

    /*public void displayMsg(){
        userController.diplayMsg("HI HI");
    }*/
}
