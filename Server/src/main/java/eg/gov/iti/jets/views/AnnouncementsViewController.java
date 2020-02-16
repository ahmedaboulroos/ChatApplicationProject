package eg.gov.iti.jets.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.implementations.AnnouncementDaoImpl;
import eg.gov.iti.jets.models.dao.implementations.AnnouncementDeliveryDaoImpl;
import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDeliveryDao;
import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.entities.AnnouncementDelivery;
import eg.gov.iti.jets.views.models.AnnouncementViewModel;
import eg.gov.iti.jets.views.models.AnnouncementDeliveryModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AnnouncementsViewController implements Initializable {

    AnnouncementDao announcementDao = AnnouncementDaoImpl.getInstance();
    AnnouncementDeliveryDao announcementDeliveryDao = AnnouncementDeliveryDaoImpl.getInstance();

    @FXML
    private JFXTextField announcementTf;

    @FXML
    private JFXButton sendAnnouncementBtn;

    @FXML
    private TableView<AnnouncementViewModel> announcementsTv;
    @FXML
    private TableColumn<AnnouncementViewModel, Integer> announcementId;

    @FXML
    private TableColumn<AnnouncementViewModel, String> content;

    @FXML
    private TableColumn<AnnouncementViewModel, String> announcementTimestamp;

    @FXML
    private TableView<AnnouncementDeliveryModel> announcementsDeliveryTv;
    @FXML
    private TableColumn<AnnouncementDeliveryModel, Integer> announcementDeliveryId;

    @FXML
    private TableColumn<AnnouncementDeliveryModel, Integer> userId;

    @FXML
    private TableColumn<AnnouncementDeliveryModel, Integer> announcementDelId;

    @FXML
    private TableColumn<AnnouncementDeliveryModel, String> announcementDeliveryStatus;


    //@FXML
    //private TableView<?> announcementsDeliveryTv;

    public List<AnnouncementViewModel> getAllAnnouncements() throws RemoteException {
        List<AnnouncementViewModel> announcementList = new ArrayList<>();
        List<Announcement> announcements = announcementDao.getAllAnnouncements();
        for (int i = 0; i < announcements.size(); i++) {
            announcementList.add(new AnnouncementViewModel(announcements.get(i).getAnnouncementId(), announcements.get(i).getContent(), announcements.get(i).getAnnouncementTimestamp().toString()));
            System.out.println(announcements.get(i).getAnnouncementId() + " " + announcements.get(i).getContent() + " " + announcements.get(i).getAnnouncementTimestamp().toString());
        }
        return announcementList;
    }

    public List<AnnouncementDeliveryModel> getAnnouncementDeliveries(int announcementId) throws RemoteException {

        List<AnnouncementDeliveryModel> announcementDeliveryList = new ArrayList<>();
        List<AnnouncementDelivery> announcements = announcementDao.getAnnouncementDeliveries(1);
        for (int i = 0; i < announcements.size(); i++) {
            announcementDeliveryList.add(new AnnouncementDeliveryModel(announcements.get(i).getAnnouncementDeliveryId(), announcements.get(i).getUserId(), announcements.get(i).getAnnouncementId(), announcements.get(i).getAnnouncementDeliveryStatus().toString()));
            System.out.println(announcements.get(i).getAnnouncementDeliveryId() + " " + announcements.get(i).getUserId() + " " + announcements.get(i).getAnnouncementId() + "" + announcements.get(i).getAnnouncementDeliveryStatus().toString());
        }
        return announcementDeliveryList;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            announcementsTv.setItems(FXCollections.observableList(getAllAnnouncements()));
            announcementId.setCellValueFactory(new PropertyValueFactory<AnnouncementViewModel, Integer>("announcementId"));
            content.setCellValueFactory(new PropertyValueFactory<AnnouncementViewModel, String>("content"));
            announcementTimestamp.setCellValueFactory(new PropertyValueFactory<AnnouncementViewModel, String>("announcementTimestamp"));
            //announcementsTv.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());


            announcementsDeliveryTv.setItems(FXCollections.observableList(getAnnouncementDeliveries(11)));
            announcementDeliveryId.setCellValueFactory(new PropertyValueFactory<AnnouncementDeliveryModel, Integer>("announcementDeliveryId"));
            userId.setCellValueFactory(new PropertyValueFactory<AnnouncementDeliveryModel, Integer>("userId"));
            announcementDelId.setCellValueFactory(new PropertyValueFactory<AnnouncementDeliveryModel, Integer>("announcementDelId"));

            announcementDeliveryStatus.setCellValueFactory(new PropertyValueFactory<AnnouncementDeliveryModel, String>("announcementDeliveryStatus"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

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
        try {
            getAnnouncementDeliveries(1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
