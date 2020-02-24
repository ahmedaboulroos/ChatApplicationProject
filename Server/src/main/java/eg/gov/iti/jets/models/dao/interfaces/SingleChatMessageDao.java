package eg.gov.iti.jets.models.dao.interfaces;


import eg.gov.iti.jets.models.entities.SingleChatMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SingleChatMessageDao extends Remote {

    // Create
    int createSingleChatMessage(SingleChatMessage singleChatMessage) throws RemoteException;

    // Read
    SingleChatMessage getSingleChatMessage(int singleChatMessageId) throws RemoteException;

    // Update
    void updateSingleChatMessage(SingleChatMessage singleChatMessage) throws RemoteException;

    // Delete
    void deleteSingleChatMessage(int singleChatMessageId) throws RemoteException;
}
