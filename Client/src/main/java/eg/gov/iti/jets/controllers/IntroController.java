
package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class IntroController implements Initializable {
    @FXML
    private Circle circleIcon;
    @FXML
    private JFXTextField serverAddressTf;
    @FXML
    private JFXButton checkServerAddressBtn;
    @FXML
    private Label errorLbl;
    private boolean connectionEstablished = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            circleIcon.setFill(new ImagePattern(
                    new Image("images/vector-chat-icon.jpg")));
        } catch (Exception e) {
            System.out.println("Intro Icon did not load");
        }

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            serverAddressTf.setText(inetAddress.getHostAddress());
            serverAddressTf.setDisable(false);
            checkServerAddressBtn.setDisable(false);
            LoginViewController.getInstance().disable();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void closeBtnHandler(MouseEvent Event) {
        Platform.exit();
    }


    public void handleCheckServerConnection(ActionEvent actionEvent) {
        connectionEstablished = RMIConnection.startConnection(serverAddressTf.getText());
        if (connectionEstablished) {
            serverAddressTf.setDisable(true);
            checkServerAddressBtn.setDisable(true);
            LoginViewController.getInstance().enable();
            errorLbl.setText("Connection Established");
            errorLbl.setTextFill(Color.web("lightgreen"));
        } else {
            errorLbl.setText("Server Connection Error");
        }
    }

}
