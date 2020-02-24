package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.implementations.AnnouncementDaoImpl;
import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.dto.AnnouncementsTableModel;
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

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AnnouncementsViewController implements Initializable {

    AnnouncementDao announcementDao = AnnouncementDaoImpl.getInstance(DBConnection.getConnection());

    @FXML
    private JFXTextField announcementTf;
    @FXML
    private JFXButton sendAnnouncementBtn;
    @FXML
    private TableView<AnnouncementsTableModel> announcementsTv;
    @FXML
    private TableColumn<AnnouncementsTableModel, Integer> announcementId;
    @FXML
    private TableColumn<AnnouncementsTableModel, String> title;
    @FXML
    private TableColumn<AnnouncementsTableModel, String> announcementTimestamp;

    public List<AnnouncementsTableModel> getAllAnnouncements() throws RemoteException {
        List<AnnouncementsTableModel> announcementList = new ArrayList<>();
        List<Announcement> announcements = announcementDao.getAllAnnouncements();
        for (int i = 0; i < announcements.size(); i++) {
            announcementList.add(new AnnouncementsTableModel(announcements.get(i).getId(), announcements.get(i).getContent(), announcements.get(i).getSendDateTime().toString()));
            System.out.println(announcements.get(i).getId() + " " + announcements.get(i).getContent() + " " + announcements.get(i).getSendDateTime().toString());
        }
        return announcementList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            announcementsTv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            announcementsTv.setItems(FXCollections.observableList(getAllAnnouncements()));
            announcementId.setCellValueFactory(new PropertyValueFactory<AnnouncementsTableModel, Integer>("announcementId"));
            title.setCellValueFactory(new PropertyValueFactory<AnnouncementsTableModel, String>("content"));
            announcementTimestamp.setCellValueFactory(new PropertyValueFactory<AnnouncementsTableModel, String>("announcementTimestamp"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSendBtn(ActionEvent event) {
        String content = announcementTf.getText();
        Announcement announcement = new Announcement("title", content, LocalDateTime.now());
        try {
            announcementDao.createAnnouncement(announcement);
            updateAnnouncementsTable();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        announcementTf.clear();
    }

    void updateAnnouncementsTable() {
        try {
            System.out.println(announcementDao.getAllAnnouncements());
            announcementsTv.setItems(FXCollections.observableList(getAllAnnouncements()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
