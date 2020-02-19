package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDao;
import eg.gov.iti.jets.models.entities.Announcement;
import eg.gov.iti.jets.models.entities.AnnouncementDelivery;
import eg.gov.iti.jets.models.entities.enums.AnnouncementDeliveryStatus;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDaoImpl extends UnicastRemoteObject implements AnnouncementDao {

    private static AnnouncementDao instance;

    protected AnnouncementDaoImpl() throws RemoteException {
    }

    public static AnnouncementDao getInstance() {
        if (instance == null) {
            try {
                instance = new AnnouncementDaoImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public boolean createAnnouncement(Announcement announcement) {
        Connection connection = DBConnection.getInstance().getConnection();

        boolean result = false;
        String insertSQL = "INSERT INTO ANNOUNCEMENT  \n" +
                "(ANNOUNCEMENT_ID,CONTENT,ANNOUNCEMENT_TIMESTAMP ) VALUES (" +
                " SEQ_ANNOUNCEMENT_ID.nextval , ? , ? )  ";

        try (PreparedStatement insert = connection.prepareStatement(insertSQL)) {
            insert.setString(1, announcement.getContent());
            insert.setTimestamp(2, Timestamp.valueOf(announcement.getAnnouncementTimestamp()));
            int flag = insert.executeUpdate();
            if (flag != -1)
                result = true;
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;

    }

    @Override
    public Announcement getAnnouncement(int announcementId) {
        Connection connection = DBConnection.getInstance().getConnection();
        int announcementID = 0;
        String content = null;
        Timestamp announcTimestamp = null;

        String selectSQL = "SELECT *  \n" +
                "FROM ANNOUNCEMENT  \n" +
                "WHERE ANNOUNCEMENT_ID = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, announcementId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                announcementID = resultSet.getInt(1);
                content = resultSet.getString(2);
                announcTimestamp = resultSet.getTimestamp(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Announcement announcement = new Announcement(announcementID, content, announcTimestamp.toLocalDateTime());

        return announcement;
    }

    @Override
    public List<AnnouncementDelivery> getAnnouncementDeliveries(int announcementId) {

        List<AnnouncementDelivery> announcementDeliveryList = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        String selectSQL = "SELECT *  \n" +
                "FROM ANNOUNCEMENT_DELIVERY  \n" +
                "WHERE ANNOUNCEMENT_ID = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, announcementId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int announDelivryID = resultSet.getInt(1);
                int announcUserID = resultSet.getInt(2);
                int announId = resultSet.getInt(3);
                AnnouncementDeliveryStatus announcDeliverySatus = AnnouncementDeliveryStatus.valueOf(resultSet.getString(4));
                AnnouncementDelivery announcementDelivery = new AnnouncementDelivery(announDelivryID, announcUserID, announId, announcDeliverySatus);
                announcementDeliveryList.add(announcementDelivery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return announcementDeliveryList;

    }

    @Override
    public boolean updateAnnouncement(Announcement announcement) {
        Connection connection = DBConnection.getInstance().getConnection();
        String updateSQL = "UPDATE ANNOUNCEMENT \n" +
                "SET CONTENT  =  ?  ,  \n" +
                "ANNOUNCEMENT_TIMESTAMP = ?  \n" +
                "WHERE ANNOUNCEMENT_ID = ? ";

        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, announcement.getContent());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(announcement.getAnnouncementTimestamp()));
            preparedStatement.setInt(3, announcement.getAnnouncementId());
            int rowEffected = preparedStatement.executeUpdate();
            System.out.println("row updated" + rowEffected);
            if (rowEffected != 0)
                result = true;
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean deleteAnnouncement(int announcementId) {
        Connection connection = DBConnection.getInstance().getConnection();
        String deleteSQL = "DELETE FROM ANNOUNCEMENT  \n" +
                "WHERE ANNOUNCEMENT_ID = ? ";
        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, announcementId);
            int rowEffected = preparedStatement.executeUpdate();
            System.out.println("row deleted" + rowEffected);
            if (rowEffected != 0)
                result = true;
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        List<Announcement> announcements = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        int announcementID = 0;
        String content = null;
        Timestamp announcTimestamp = null;

        String selectSQL = "SELECT *  \n" +
                "FROM ANNOUNCEMENT ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                announcementID = resultSet.getInt(1);
                content = resultSet.getString(2);
                announcTimestamp = resultSet.getTimestamp(3);
                Announcement announcement = new Announcement(announcementID, content, announcTimestamp.toLocalDateTime());
                announcements.add(announcement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return announcements;
    }


    /*public static void main(String[]args){
        DBConnection.getInstance().initConnection();
        System.out.println(Instant.now());
        AnnouncementDaoImpl obj= new AnnouncementDaoImpl();

        Announcement announcement;
        announcement=obj.getAnnouncement(9);
        announcement.setContent("update announcment");

        obj.updateAnnouncement(announcement);
        obj.deleteAnnouncement(10);

    }*/

}
