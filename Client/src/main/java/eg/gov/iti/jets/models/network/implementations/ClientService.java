package eg.gov.iti.jets.models.network.implementations;


import eg.gov.iti.jets.controllers.GroupChatViewController;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientService extends UnicastRemoteObject implements ClientInterface {
    private GroupChatViewController groupChatViewController = GroupChatViewController.getInstance();

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
        System.out.println(">> User Logged In :" + userId);
//        User user = userDao.getUser(userId);
//        UserDto userDto = new UserDto(getDisplayUsername(user), user.getProfileImage());
//        chatAppViewController.loggedIn(userDto);
    }

    @Override
    public void userLoggedOut(int userId) throws RemoteException {
        System.out.println(">> User Logged Out :" + userId);
    }

    @Override
    public void receiveUserStatusChanged(int userId) throws RemoteException {
        System.out.println(">> User Status Changed :" + userId);
//        User user = userDao.getUser(userId);
//        UserDto userDto = new UserDto(getDisplayUsername(user), user.getProfileImage());
//        chatAppViewController.loggedOut(userDto);
    }

    @Override
    public void receiveNewSingleChat(int singleChatId) throws RemoteException {
        System.out.println(">> New Single Chat :" + singleChatId);
//        System.out.println(singleChatId + "el id");
//        SingleChat singleChat = singleChatDao.getSingleChat(singleChatId);
//        if (singleChat != null) {
//            singleChatViewController.displayNewSingleChat(singleChat);
//        } else {
//            System.out.println("ana null" + singleChat);
//        }
    }

    @Override
    public void receiveNewSingleChatMessage(int singleChatMessageId) throws RemoteException {
        System.out.println(">> New Single Chat Message :" + singleChatMessageId);
//        System.out.println(singleChatMessageId + "el id");
//        SingleChatMessage singleChatMessage = singleChatMessageDao.getSingleChatMessage(singleChatMessageId);
//        User user = userDao.getUser(singleChatMessage.getUserId());
//        UserDto userDto = new UserDto(getDisplayUsername(user), user.getProfileImage());
//        System.out.println(singleChatMessage + "elobject");
//        if (singleChatMessage != null) {
//            singleChatViewController.displayNewSingleChatMessage(singleChatMessage, userDto);
//        } else {
//            System.out.println("ana null" + singleChatMessage);
//        }
    }

    @Override
    public void receiveNewGroupChatMessage(int groupChatMessageId) throws RemoteException {

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
//        Group group = groupDao.getGroup(groupId);
//        List<GroupContact> groupContactList = groupDao.getGroupContacts(groupId);
//        List<UserDto> groupUsers = groupContactList.stream()
//                .map(GroupContact::getUserId)
//                .map(userId -> {
//                    User user = null;
//                    try {
//                        user = userDao.getUser(userId);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                    return user;
//                })
//                .map(user -> new UserDto(getDisplayUsername(user), user.getProfileImage()))
//                .collect(Collectors.toList());
//        chatAppViewController.displayNewGroup(new GroupDto(group.getGroupName(), groupUsers));
    }

    @Override
    public void receiveNewContactsGroupMembership(int contactsGroupMembershipId) throws RemoteException {
        System.out.println(">> New Contacts Group Membership :" + contactsGroupMembershipId);

    }

    @Override
    public void receiveNewAnnouncement(int announcementId) throws RemoteException {
        System.out.println(">> New Announcement :" + announcementId);

    }

    private String getDisplayUsername(User user) {
        return user.getUsername() == null ? user.getPhoneNumber() : user.getUsername();
    }

}
