package eg.gov.iti.jets.views;

import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;

public class LeftViewController {

    @FXML
    private Accordion groupsAccordion;

    @FXML
    private ListView<?> groupChatsLv;

    @FXML
    private ListView<?> singleChatsLv;

    private UserDao userDao = RMIConnection.getInstance().getUserDao();

    private StageCoordinator stageCoordinator;

    public void setStageCoordinator(StageCoordinator stageCoordinator) {
        this.stageCoordinator = stageCoordinator;
    }

}
