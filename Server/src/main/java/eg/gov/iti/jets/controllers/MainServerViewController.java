package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.network.RMIConnection;
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
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class MainServerViewController implements Initializable {

    private AnnouncementsViewController announcementsViewController;
    private DashboardViewController dashboardViewController;
    private UsersViewController usersViewController;

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
    private JFXTextField serverAddressTf;

    private boolean serviceRunning = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            serverAddressTf.setText(inetAddress.getHostAddress());

            Parent welcomeScene = FXMLLoader.load(getClass().getResource("/views/WelcomeView.fxml"));
            welcomeTab.setContent(welcomeScene);
            startService();

            FXMLLoader dashboardFxmlLoader = new FXMLLoader(getClass().getResource("/views/DashboardView.fxml"));
            Parent dashboardView = dashboardFxmlLoader.load();
            dashboardViewController = dashboardFxmlLoader.getController();
            dashboardTab.setContent(dashboardView);

            FXMLLoader usersFxmlLoader = new FXMLLoader(getClass().getResource("/views/UsersView.fxml"));
            Parent usersScene = usersFxmlLoader.load();
            usersViewController = usersFxmlLoader.getController();
            usersTab.setContent(usersScene);

            FXMLLoader announcementsFxmlLoader = new FXMLLoader(getClass().getResource("/views/AnnouncementsView.fxml"));
            Parent announcementScene = announcementsFxmlLoader.load();
            announcementsViewController = announcementsFxmlLoader.getController();
            announcementsTab.setContent(announcementScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDashboardBtnClick(ActionEvent event) {
        tabPane.getSelectionModel().select(dashboardTab);
        title.setText("Dashboard");
    }

    @FXML
    void handleUsersBtnClick(ActionEvent event) {
        tabPane.getSelectionModel().select(usersTab);
        title.setText("System Users");
    }

    @FXML
    void handleAnnouncementsBtnClick(ActionEvent event) {
        tabPane.getSelectionModel().select(announcementsTab);
        title.setText("Announcements");
    }

    @FXML
    void handleServiceBtnClick(ActionEvent event) {
        if (serviceRunning) {
            stopService();
        } else {
            startService();
        }
    }

    @FXML
    void handleSettingsBtn(ActionEvent event) {
        System.out.println("Settings");
    }

    private void startService() {
        RMIConnection.getInstance().startConnection();
        serverStatusBtn.setText("Service Running          ");
        serverStatusCircle.setFill(Color.LIGHTGREEN);
        dashboardBtn.setDisable(false);
        usersBtn.setDisable(false);
        announcementsBtn.setDisable(false);
        serviceRunning = true;
    }

    private void stopService() {
        RMIConnection.getInstance().stopConnection();
        tabPane.getSelectionModel().select(welcomeTab);
        title.setText("Chat Application Service");
        serverStatusBtn.setText("Service Stopped          ");
        serverStatusCircle.setFill(Color.PINK);
        dashboardBtn.setDisable(true);
        usersBtn.setDisable(true);
        announcementsBtn.setDisable(true);
        serviceRunning = false;
    }

    public AnnouncementsViewController getAnnouncementsViewController() {
        return announcementsViewController;
    }

    public DashboardViewController getDashboardViewController() {
        return dashboardViewController;
    }

    public UsersViewController getUsersViewController() {
        return usersViewController;
    }
}
