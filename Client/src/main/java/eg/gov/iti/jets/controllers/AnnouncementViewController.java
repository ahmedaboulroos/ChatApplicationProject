package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.rmi.RemoteException;

public class AnnouncementViewController {

    @FXML
    private WebView content;

    @FXML
    private Text title;

    public void setAnnouncementId(int announcementId) {
        AnnouncementDao announcementDao = RMIConnection.getAnnouncementDao();
        try {
            Announcement announcement = announcementDao.getAnnouncement(announcementId);
            System.out.println(announcementId);
            if (announcement != null) {
                title.setText(announcement.getTitle());
                content.getEngine().loadContent(announcement.getContent());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
