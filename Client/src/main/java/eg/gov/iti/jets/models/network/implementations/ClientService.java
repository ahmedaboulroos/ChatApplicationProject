package eg.gov.iti.jets.models.network.implementations;

import eg.gov.iti.jets.controllers.MainController;
import eg.gov.iti.jets.controllers.SingleChatMessageController;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;
import eg.gov.iti.jets.views.SingleChatViewController;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ClientService extends UnicastRemoteObject implements ClientInterface {
    private MainController mainController;
    private SingleChatMessageController singleChatMessageController;
    private SingleChatViewController singleChatViewController;


    public ClientService() throws RemoteException {
    }

    public ClientService(MainController mainController) throws RemoteException {
        this.mainController = mainController;
        //mainController.displayMsg("HI");
    }

    public ClientService(SingleChatMessageController singleChatMessageController) throws RemoteException {
        this.singleChatMessageController = singleChatMessageController;
        //mainController.displayMsg("HI");
    }

    public ClientService(int port) throws RemoteException {
        super(port);
    }

    public ClientService(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
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

    }

    @Override
    public void receiveGroupContact(int groupContactId) throws RemoteException {

    }

    @Override
    public void receiveAnnouncement(int announcementId) throws RemoteException {

    }
}
