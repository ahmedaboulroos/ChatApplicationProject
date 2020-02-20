package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.web.HTMLEditor;

import java.rmi.RemoteException;
import java.util.List;

public class GroupChatViewController {
    @FXML
    private ListView<GroupChatMessage> groupChatMessagesLv;

    @FXML
    private HTMLEditor groupChatMessageHtml;

    public void setGroupChatMessages(int groupChatId) {
        try {
            List<GroupChatMessage> groupChatMessages = RMIConnection.getGroupChatDao().getGroupChatMessages(groupChatId);
            groupChatMessagesLv.setItems(FXCollections.observableList(groupChatMessages));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
