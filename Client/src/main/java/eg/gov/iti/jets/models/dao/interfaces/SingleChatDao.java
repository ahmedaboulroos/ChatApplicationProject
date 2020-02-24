package eg.gov.iti.jets.models.dao.interfaces;

import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SingleChatDao extends Remote {

    // Create
    int createSingleChat(SingleChat singleChat) throws RemoteException;

    // Read
    SingleChat getSingleChat(int singleChatId) throws RemoteException;

    List<User> getSingleChatTwoUsers(int singleChatId) throws RemoteException;

    List<SingleChatMessage> getSingleChatMessages(int singleChatId) throws RemoteException;

    // Update
    void updateSingleChat(SingleChat singleChat) throws RemoteException;

    // Delete
    void deleteSingleChat(int singleChatId) throws RemoteException;

}
