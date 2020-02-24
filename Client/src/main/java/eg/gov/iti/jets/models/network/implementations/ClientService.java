package eg.gov.iti.jets.models.network.implementations;


import eg.gov.iti.jets.controllers.ChatAppViewController;
import eg.gov.iti.jets.controllers.SingleChatViewController;
import eg.gov.iti.jets.models.dao.interfaces.GroupContactDao;
import eg.gov.iti.jets.models.dao.interfaces.GroupDao;
import eg.gov.iti.jets.models.dao.interfaces.SingleChatMessageDao;
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

    public ClientService() throws RemoteException {
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
        UserDto userDto = new UserDto(getDisplayUsername(user), user.getProfileImage());
        chatAppViewController.loggedIn(userDto);
    }

    @Override
    public void userLoggedOut(int userId) throws RemoteException {
        System.out.println(">> User Logged Out :" + userId);
    }

    @Override
    public void receiveUserStatusChanged(int userId) throws RemoteException {
        System.out.println(">> User Status Changed :" + userId);
        User user = userDao.getUser(userId);
        UserDto userDto = new UserDto(getDisplayUsername(user), user.getProfileImage());
        chatAppViewController.loggedOut(userDto);
    }

    @Override
    public void receiveNewSingleChat(int singleChatId) throws RemoteException {
        System.out.println(">> New Single Chat :" + singleChatId);
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
        System.out.println(">> New Group Chat Message :" + groupChatMessageId);
    }

    @Override
    public void receiveNewGroupChatMembership(int groupChatMembershipId) throws RemoteException {
        System.out.println(">> New Group Chat Membership :" + groupChatMembershipId);
    }

    @Override
    public void receiveNewSeenByStatus(int seenByStatusId) throws RemoteException {
        System.out.println(">> New Seen By Status :" + seenByStatusId);
    }

    @Override
    public void receiveNewRelationship(int relationshipId) throws RemoteException {
        System.out.println(">> New Relationship :" + relationshipId);
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

}
