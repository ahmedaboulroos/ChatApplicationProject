package eg.gov.iti.jets.views;

import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class LeftViewController implements Initializable {


    @FXML
    private Tab contactsTab;

    @FXML
    private Tab groupChatTab;

    @FXML
    private ListView<GroupChat> groupChatsLv;

    @FXML
    private ListView<SingleChat> singleChatsLv;

    @FXML
    private Tab singleChatTab;

    @FXML
    private Accordion groupsAccordion;

    private UserDao userDao = RMIConnection.getInstance().getUserDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSingleChats();
        loadGroupChats();
    }

    private void loadSingleChats() {
        try {
            List<SingleChat> singleChats = userDao.getUserSingleChats(StageCoordinator.getInstance().currentUser.getUserId());
            System.out.println(singleChats);
            singleChatsLv.setItems(FXCollections.observableList(singleChats));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void loadGroupChats() {
        try {
            List<GroupChat> groupChats = userDao.getUserGroupChats(StageCoordinator.getInstance().currentUser.getUserId());
            System.out.println(groupChats);
            groupChatsLv.setItems(FXCollections.observableList(groupChats));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
