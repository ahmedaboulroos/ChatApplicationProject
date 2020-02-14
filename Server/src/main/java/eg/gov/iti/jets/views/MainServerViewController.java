package eg.gov.iti.jets.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.controllers.MainServerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainServerViewController implements Initializable {

    @FXML
    private JFXButton dashboardBtn;

    @FXML
    private JFXButton usersBtn;

    @FXML
    private JFXButton announcementsBtn;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab welcomeTab;

    @FXML
    private Tab dashboardTab;

    @FXML
    private Tab usersTab;

    @FXML
    private Tab announcementsTab;

    @FXML
    private Label title;

    @FXML
    private JFXButton serverStatusBtn;

    @FXML
    private Circle serverStatusCircle;

    @FXML
    private JFXTextField internetAddressTf;

    @FXML
    private JFXTextField portNumberTf;

    private boolean serviceRunning = true;
    private MainServerController mainServerController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent welcomeScene = FXMLLoader.load(getClass().getResource("/views/WelcomeView.fxml"));
            welcomeTab.setContent(welcomeScene);
            stopService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDashboardBtnClick(ActionEvent event) {
        try {
            Parent announcementScene = FXMLLoader.load(getClass().getResource("/views/DashboardView.fxml"));
            announcementsTab.setContent(announcementScene);
            tabPane.getSelectionModel().select(dashboardTab);
            title.setText("Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleUsersBtnClick(ActionEvent event) {
        try {
            Parent announcementScene = FXMLLoader.load(getClass().getResource("/views/UsersView.fxml"));
            announcementsTab.setContent(announcementScene);
            tabPane.getSelectionModel().select(usersTab);
            title.setText("System Users");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAnnouncementsBtnClick(ActionEvent event) {
        try {
            Parent announcementScene = FXMLLoader.load(getClass().getResource("/views/AnnouncementsView.fxml"));
            announcementsTab.setContent(announcementScene);
            tabPane.getSelectionModel().select(announcementsTab);
            title.setText("Announcements");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleServiceBtnClick(ActionEvent event) {
        if (serviceRunning) {
            stopService();
        } else {
            startService();
        }
    }

    private void startService() {
        serverStatusBtn.setText("Service Running          ");
        serverStatusCircle.setFill(Color.LIGHTGREEN);
        dashboardBtn.setDisable(false);
        usersBtn.setDisable(false);
        announcementsBtn.setDisable(false);
        internetAddressTf.setDisable(true);
        portNumberTf.setDisable(true);
        serviceRunning = true;
    }

    private void stopService() {
        tabPane.getSelectionModel().select(welcomeTab);
        title.setText("Chat Application Service");
        serverStatusBtn.setText("Service Stopped          ");
        serverStatusCircle.setFill(Color.PINK);
        dashboardBtn.setDisable(true);
        usersBtn.setDisable(true);
        announcementsBtn.setDisable(true);
        internetAddressTf.setDisable(false);
        portNumberTf.setDisable(false);
        serviceRunning = false;
    }

    public void setController(MainServerController mainServerController) {
        this.mainServerController = mainServerController;
    }

}
