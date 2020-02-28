package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.controllers.dto.AnnouncementsTableModel;
import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserProfileViewController implements Initializable {

    AnnouncementDao announcementDao = RMIConnection.getAnnouncementDao();

    @FXML
    private TableView<AnnouncementsTableModel> announcementsTv;
    @FXML
    private TableColumn<AnnouncementsTableModel, Integer> announcementId;
    @FXML
    private TableColumn<AnnouncementsTableModel, String> announcementTitle;
    @FXML
    private TableColumn<AnnouncementsTableModel, String> announcementTimestamp;
    @FXML
    private WebView contentWv;
    @FXML
    private AnchorPane profileViewAP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            announcementsTv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            announcementsTv.setItems(FXCollections.observableList(getAllAnnouncements()));

            announcementId.setCellValueFactory(new PropertyValueFactory<AnnouncementsTableModel, Integer>("announcementId"));
            announcementTitle.setCellValueFactory(new PropertyValueFactory<AnnouncementsTableModel, String>("announcementTitle"));
            announcementTimestamp.setCellValueFactory(new PropertyValueFactory<AnnouncementsTableModel, String>("announcementTimestamp"));
            addProfileIntoAncorPane();//
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<AnnouncementsTableModel> getAllAnnouncements() throws RemoteException {
        List<AnnouncementsTableModel> announcementList = new ArrayList<>();
        List<Announcement> announcements = announcementDao.getAllAnnouncements();
        for (int i = 0; i < announcements.size(); i++) {
            announcementList.add(new AnnouncementsTableModel(announcements.get(i).getId(), announcements.get(i).getTitle(), announcements.get(i).getSendDateTime().toLocalDate().toString()));
        }
        return announcementList;
    }

    @FXML
    void handleAnnouncementSelection(MouseEvent event) {
        AnnouncementsTableModel selectedItem = announcementsTv.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                contentWv.getEngine().loadContent(announcementDao.getAnnouncement(selectedItem.getAnnouncementId()).getContent());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void addProfileIntoAncorPane() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditProfileView.fxml."));
            Parent editProfileView = loader.load();
            //EditProfileViewController editProfileViewController  = loader.getController();
            profileViewAP.getChildren().add(editProfileView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
