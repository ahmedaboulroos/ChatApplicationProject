package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ServerDisconnectedViewController {

    @FXML
    private JFXButton goodbyeBtn;

    @FXML
    void handleGoodbyeBtn(ActionEvent event) {
        Stage stage = (Stage) goodbyeBtn.getScene().getWindow();
        stage.close();
    }
}
