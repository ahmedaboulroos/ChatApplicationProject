package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SingleChatDao extends Remote {

    // Create
    boolean createSingleChat(SingleChat singleChat) throws RemoteException;

    // Read
    SingleChat getSingleChat(int singleChatId) throws RemoteException;

    List<User> getSingleChatTwoUsers(int singleChatId) throws RemoteException;

    List<SingleChat> getSingleChatMessages(int singleChatId) throws RemoteException;

    // Update
    boolean updateSingleChat(SingleChat singleChat) throws RemoteException;

    boolean addSingleChatMessage(int singleChatMessageId) throws RemoteException;

    // Delete
    boolean deleteSingleChat(int singleChatId) throws RemoteException;

}
