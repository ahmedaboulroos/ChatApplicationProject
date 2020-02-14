package eg.gov.iti.jets.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.implementations.AnnouncementDaoImpl;
import eg.gov.iti.jets.models.dao.implementations.AnnouncementDeliveryDaoImpl;
import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDeliveryDao;
import eg.gov.iti.jets.models.entities.Announcement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class AnnouncementsViewController implements Initializable {

    AnnouncementDao announcementDao = AnnouncementDaoImpl.getInstance();
    AnnouncementDeliveryDao announcementDeliveryDao = AnnouncementDeliveryDaoImpl.getInstance();

    @FXML
    private JFXTextField announcementTf;

    @FXML
    private JFXButton sendAnnouncementBtn;

    @FXML
    private TableView<?> announcementsTv;

    @FXML
    private TableView<?> announcementsDeliveryTv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void handleSendBtn(ActionEvent event) {
        String content = announcementTf.getText();
        Announcement announcement = new Announcement(content);
        try {
            announcementDao.createAnnouncement(announcement);
            updateAnnouncementsTable();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        announcementTf.clear();
    }

    @FXML
    void handleAnnouncementsMc(MouseEvent event) {
//        announcementsTv.getSelectionModel().getSelectedItem();
    }

    void updateAnnouncementsTable() {
        try {
            System.out.println(announcementDao.getAllAnnouncements());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void updateAnnouncementsDeliveryTable(int id) {
        try {
            System.out.println(announcementDao.getAnnouncementDeliveries(id));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
