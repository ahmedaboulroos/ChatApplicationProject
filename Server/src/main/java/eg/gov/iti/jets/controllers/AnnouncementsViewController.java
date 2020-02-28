package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.implementations.AnnouncementDaoImpl;
import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AnnouncementsViewController implements Initializable {

    AnnouncementDao announcementDao = AnnouncementDaoImpl.getInstance(DBConnection.getConnection());

    @FXML
    private HTMLEditor announcementContentHtml;
    @FXML
    private WebView contentWv;
    @FXML
    private JFXTextField announcementTitleTf;
    @FXML
    private JFXButton sendAnnouncementBtn;
    @FXML
    private TableView<Announcement> announcementsTv;
    @FXML
    private TableColumn<Announcement, Integer> announcementId;
    @FXML
    private TableColumn<Announcement, String> announcementTitle;
    @FXML
    private TableColumn<Announcement, String> announcementTimestamp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            announcementsTv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            announcementsTv.setItems(FXCollections.observableList(announcementDao.getAllAnnouncements()));

            announcementId.setCellValueFactory(new PropertyValueFactory<>("id"));
            announcementTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            announcementTimestamp.setCellValueFactory(new PropertyValueFactory<>("sendDateTime"));

            announcementsTv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        contentWv.getEngine().loadContent(announcementDao.getAnnouncement(newSelection.getId()).getContent());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSendBtn(ActionEvent event) {
        String title = announcementTitleTf.getText();
        String content = announcementContentHtml.getHtmlText();
        Announcement announcement = new Announcement(title, content, LocalDateTime.now());
        try {
            announcementDao.createAnnouncement(announcement);
            updateAnnouncementsTable();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        announcementTitleTf.clear();
    }

    void updateAnnouncementsTable() {
        try {
            System.out.println(announcementDao.getAllAnnouncements());
            announcementsTv.setItems(FXCollections.observableList(announcementDao.getAllAnnouncements()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
