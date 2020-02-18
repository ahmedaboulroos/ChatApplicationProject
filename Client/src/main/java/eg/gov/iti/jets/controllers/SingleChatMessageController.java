package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatMessageDao;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.network.RMIConnection;

import java.rmi.RemoteException;

public class SingleChatMessageController {
    private static SingleChatMessageController instance;

    public static SingleChatMessageController getInstance() {
        if (instance == null) {
            instance = new SingleChatMessageController();
        }
        return instance;
    }

    public void sendSingleChatMessage(int singleChatId, int userId, String msg) {
        try {
            SingleChatMessageDao singleChatDao = RMIConnection.getInstance().getSingleChatMessageDao();
            SingleChatMessage singleChatMessage = new SingleChatMessage(singleChatId, userId, msg);
            singleChatDao.createSingleChatMessage(singleChatMessage);
        } catch (
                RemoteException e) {
            e.printStackTrace();
        }

    }


}
