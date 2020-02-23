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
    private ChatAppViewController chatAppViewController;
    private SingleChatViewController singleChatViewController = SingleChatViewController.getInstance();
    private GroupDao groupDao = RMIConnection.getGroupDao();
    private GroupContactDao groupContactDao = RMIConnection.getGroupContactDao();
    private UserDao userDao = RMIConnection.getUserDao();
    private SingleChatMessageDao singleChatMessageDao = RMIConnection.getSingleChatMessageDao();
    private SingleChatDao singleChatDao = RMIConnection.getSingleChatDao();
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
        System.out.println(singleChatId + "el id");
        SingleChat singleChat = singleChatDao.getSingleChat(singleChatId);
        if (singleChat != null) {
            singleChatViewController.displayNewSingleChat(singleChat);
        } else {
            System.out.println("ana null" + singleChat);
        }
    }

    @Override
    public void receiveNewSingleChatMessage(int singleChatMessageId) throws RemoteException {
        System.out.println(singleChatMessageId + "el id");
        SingleChatMessage singleChatMessage = singleChatMessageDao.getSingleChatMessage(singleChatMessageId);
        User user = userDao.getUser(singleChatMessage.getUserId());
        UserDto userDto = new UserDto(getDisplayUsername(user), user.getProfileImage());
        System.out.println(singleChatMessage + "elobject");
        if (singleChatMessage != null) {
            singleChatViewController.displayNewSingleChatMessage(singleChatMessage, userDto);
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
