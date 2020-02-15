package eg.gov.iti.jets.views;

import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;

public class LeftViewController implements Initializable {


    @FXML
    private Tab contactsTab;

    @FXML
    private Tab groupChatTab;

    @FXML
    private ListView<?> groupChatsLv;

    @FXML
    private Tab singleChatTab;

    @FXML
    private Accordion groupsAccordion;

    @FXML
    private ListView<?> singleChatsLv;

    private UserDao userDao = RMIConnection.getInstance().getUserDao();

    private StageCoordinator stageCoordinator;

    public void setStageCoordinator(StageCoordinator stageCoordinator) {
        this.stageCoordinator = stageCoordinator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
