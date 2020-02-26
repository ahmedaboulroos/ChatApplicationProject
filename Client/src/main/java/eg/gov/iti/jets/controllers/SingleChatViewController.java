package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.dao.interfaces.SingleChatMessageDao;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.web.HTMLEditor;

import java.rmi.RemoteException;
import java.util.List;

public class SingleChatViewController {
    private static SingleChatViewController singleChatViewController;
    private SingleChatMessageDao singleChatMessageDao = RMIConnection.getSingleChatMessageDao();
    private SingleChatDao singleChatDao = RMIConnection.getSingleChatDao();

    public static SingleChatViewController getInstance() {
        return singleChatViewController;
    }

    public void setController(SingleChatViewController singleChatViewController) {
        SingleChatViewController.singleChatViewController = singleChatViewController;
    }

    private User currentUser = ClientStageCoordinator.getInstance().currentUser;
    private int singleChatId;
    @FXML
    private ListView<SingleChatMessage> singleChatMessagesLv;

    @FXML
    private HTMLEditor singleChatMessageHtml;

    public void setSingleChatMessages(int singleChatId) {
        this.singleChatId = singleChatId;
        updateSingleChat();
    }

    @FXML
    void handleSendBtn(ActionEvent event) {
        try {
            String msg = singleChatMessageHtml.getHtmlText();
            SingleChatMessageDao singleChatDao = RMIConnection.getSingleChatMessageDao();
            SingleChatMessage singleChatMessage = new SingleChatMessage(this.singleChatId, currentUser.getId(), msg);
            singleChatDao.createSingleChatMessage(singleChatMessage);
        } catch (
                RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateSingleChat() {
        try {
            singleChatMessagesLv.getItems().clear();
            List<SingleChatMessage> singleChatMessages = RMIConnection.getSingleChatDao().getSingleChatMessages(singleChatId);
            if (singleChatMessages != null) {
                singleChatMessagesLv.setItems(FXCollections.observableList(singleChatMessages));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addSingleChatMessage(SingleChatMessage singleChatMessage) {
        Platform.runLater(() -> singleChatMessagesLv.getItems().add(singleChatMessage));
    }

}
