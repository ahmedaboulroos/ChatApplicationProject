package eg.gov.iti.jets.views;

import eg.gov.iti.jets.models.dao.interfaces.SingleChatMessageDao;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.web.HTMLEditor;

import java.rmi.RemoteException;
import java.util.List;

public class SingleChatViewController {

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
            SingleChatMessageDao singleChatDao = RMIConnection.getInstance().getSingleChatMessageDao();
            SingleChatMessage singleChatMessage = new SingleChatMessage(this.singleChatId, currentUser.getUserId(), msg);
            singleChatDao.createSingleChatMessage(singleChatMessage);
            updateSingleChat();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateSingleChat() {
        try {
            singleChatMessagesLv.getItems().clear();
            List<SingleChatMessage> singleChatMessages = RMIConnection.getInstance().getSingleChatDao().getSingleChatMessages(singleChatId);
            singleChatMessagesLv.setItems(FXCollections.observableList(singleChatMessages));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
