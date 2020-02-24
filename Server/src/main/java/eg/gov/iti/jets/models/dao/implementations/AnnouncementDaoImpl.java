package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.network.implementations.ServerService;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDaoImpl extends UnicastRemoteObject implements AnnouncementDao {

    private static AnnouncementDao instance;
    private static Connection dbConnection;

    protected AnnouncementDaoImpl() throws RemoteException {
    }

    public static AnnouncementDao getInstance(Connection connection) {
        if (instance == null) {
            try {
                instance = new AnnouncementDaoImpl();
                dbConnection = connection;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public int createAnnouncement(Announcement announcement) {
        int id = -1;
        String[] key = {"ID"};
        String insertSQL = "INSERT INTO ANNOUNCEMENTS(ID, TITLE, CONTENT, SEND_DATE_TIME) VALUES ( ID_SEQ.nextval , ? , ? ,?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(insertSQL, key)) {
            ps.setString(1, announcement.getTitle());
            ps.setString(2, announcement.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(announcement.getSendDateTime()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            for (ClientInterface c : ServerService.getAllOnlineClients()) {
                c.receiveNewAnnouncement(id);
            }
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Announcement getAnnouncement(int announcementId) {
        Announcement announcement = null;
        String selectSQL = "SELECT ID, TITLE, CONTENT, SEND_DATE_TIME FROM ANNOUNCEMENTS WHERE ID = ? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, announcementId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                announcement = new Announcement(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4).toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return announcement;
    }

    @Override
    public void updateAnnouncement(Announcement announcement) {
        String updateSQL = "UPDATE ANNOUNCEMENTS SET TITLE = ?, CONTENT = ?, SEND_DATE_TIME = ? WHERE ID = ? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(updateSQL)) {
            ps.setString(1, announcement.getTitle());
            ps.setString(2, announcement.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(announcement.getSendDateTime()));
            ps.setInt(4, announcement.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAnnouncement(int announcementId) {
        String deleteSQL = "DELETE FROM ANNOUNCEMENTS WHERE ID = ? ";
        try (PreparedStatement ps = dbConnection.prepareStatement(deleteSQL)) {
            ps.setInt(1, announcementId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        List<Announcement> announcements = new ArrayList<>();
        String selectSQL = "SELECT ID, TITLE, CONTENT, SEND_DATE_TIME FROM ANNOUNCEMENTS ";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                announcements.add(new Announcement(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4).toLocalDateTime()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return announcements;
    }

}
