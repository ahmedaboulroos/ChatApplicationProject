package eg.gov.iti.jets.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class AnnouncementsViewController implements Initializable {

    @FXML
    private JFXTextField announcementTf;

    @FXML
    private JFXButton sendAnnouncementBtn;

    @FXML
    private TableView<?> announcementsTv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void handleSendBtn(ActionEvent event) {

    }


}
