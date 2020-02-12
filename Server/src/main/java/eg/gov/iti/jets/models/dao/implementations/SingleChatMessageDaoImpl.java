package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatMessageDao;
import eg.gov.iti.jets.models.entities.SingleChatMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SingleChatMessageDaoImpl extends UnicastRemoteObject implements SingleChatMessageDao {

    protected SingleChatMessageDaoImpl() throws RemoteException {
    }

    @Override
    public boolean createSingleChatMessage(SingleChatMessage singleChatMessage) {
        return false;
    }

    @Override
    public SingleChatMessage getSingleChatMessage(int singleChatMessageId) {
        return null;
    }

    @Override
    public boolean updateSingleChatMessage(SingleChatMessage singleChatMessage) {
        return false;
    }

    @Override
    public boolean deleteSingleChatMessage(int singleChatMessageId) {
        return false;
    }
}
