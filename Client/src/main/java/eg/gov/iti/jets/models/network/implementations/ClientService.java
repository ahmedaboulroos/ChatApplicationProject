package eg.gov.iti.jets.models.network.implementations;


import eg.gov.iti.jets.controllers.ChatAppViewController;
import eg.gov.iti.jets.controllers.SingleChatViewController;
import eg.gov.iti.jets.models.dao.interfaces.*;
import eg.gov.iti.jets.models.dto.GroupDto;
import eg.gov.iti.jets.models.dto.UserDto;
import eg.gov.iti.jets.models.entities.*;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

public class ClientService extends UnicastRemoteObject implements ClientInterface {
    private User currentUser;
    private ChatAppViewController chatAppViewController;
    private SingleChatViewController singleChatViewController = SingleChatViewController.getInstance();
    private GroupDao groupDao = RMIConnection.getGroupDao();
    private RelationshipDao relationshipDao = RMIConnection.getRelationshipDao();
    private GroupContactDao groupContactDao = RMIConnection.getGroupContactDao();
    private UserDao userDao = RMIConnection.getUserDao();
    private SingleChatMessageDao singleChatMessageDao = RMIConnection.getSingleChatMessageDao();

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

    public ClientService(ChatAppViewController chatAppViewController, User currentUser) throws RemoteException {
        super();
        this.chatAppViewController = chatAppViewController;
        this.currentUser = currentUser;
    }

    @Override
    public void userLoggedIn(int userId) throws RemoteException {


        User user = userDao.getUser(userId);
        UserDto userDto = new UserDto(getDisplayUsername(user), user.getProfileImage());
        chatAppViewController.loggedIn(userDto);
    }


    @Override
    public void userLoggedOut(int userId) throws RemoteException {
        User user = userDao.getUser(userId);
        UserDto userDto = new UserDto(getDisplayUsername(user), user.getProfileImage());
        chatAppViewController.loggedOut(userDto);
    }

    @Override
    public void receiveNewSingleChat(int singleChatId) throws RemoteException {

    }

    @Override
    public void receiveNewSingleChatMessage(int singleChatMessageId) throws RemoteException {
        System.out.println(singleChatMessageId + "el id");
        SingleChatMessage singleChatMessage = singleChatMessageDao.getSingleChatMessage(singleChatMessageId);
        System.out.println(singleChatMessage + "elobject");
        if (singleChatMessage != null) {
            singleChatViewController.displayNewSingleChatMessage(singleChatMessage);
        } else {
            System.out.println("ana null" + singleChatMessage);
        }
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
        Relationship relationship = relationshipDao.getRelationship(relationshipId);
        List<User> userList = relationshipDao.getRelationshipTwoUsers(relationshipId);

        User user;
        if (currentUser.getUserId() == relationship.getFirstUserId()) {
            user = userList.get(1);
        } else {
            user = userList.get(0);
        }

        UserDto userDto = new UserDto(getDisplayUsername(user), user.getProfileImage());
        chatAppViewController.displayRelationship(userDto);
    }

    @Override
    public void receiveMembership(int membershipId) throws RemoteException {

    }

    @Override
    public void receiveGroup(int groupId) throws RemoteException {
        Group group = groupDao.getGroup(groupId);
        List<GroupContact> groupContactList = groupDao.getGroupContacts(groupId);
        List<UserDto> groupUsers = groupContactList.stream()
                .map(GroupContact::getUserId)
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
