package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.AnnouncementDeliveryDao;
import eg.gov.iti.jets.models.entities.AnnouncementDelivery;
import eg.gov.iti.jets.models.entities.enums.AnnouncementDeliveryStatus;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnnouncementDeliveryDaoImpl extends UnicastRemoteObject implements AnnouncementDeliveryDao {

    private static AnnouncementDeliveryDao instance;

    protected AnnouncementDeliveryDaoImpl() throws RemoteException {
    }

    public static AnnouncementDeliveryDao getInstance() {
        if (instance == null) {
            try {
                instance = new AnnouncementDeliveryDaoImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public boolean createAnnouncementDelivery(AnnouncementDelivery announcementDelivery) {
        Connection connection = DBConnection.getInstance().getConnection();
        boolean result = false;
        String insertSQL = "INSERT INTO ANNOUNCEMENT_DELIVERY  \n" +
                "(ANNOUNCEMENT_DELIVERY_ID, ANNOUNCEMENT_USER_ID, ANNOUNCEMENT_ID , ANNOUNCEMENT_DELIVERY_STATUS) VALUES (" +
                " SEQ_ANNOUNCEMENT_DELIVERY_ID.nextval , ? , ? , ? )  ";

        try (PreparedStatement insert = connection.prepareStatement(insertSQL)) {
            insert.setInt(1, announcementDelivery.getUserId());
            insert.setInt(2, announcementDelivery.getAnnouncementId());
            insert.setString(3, announcementDelivery.getAnnouncementDeliveryStatus().toString());
            int flag = insert.executeUpdate();
            if (flag != -1)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    @Override
    public AnnouncementDelivery getAnnouncementDelivery(int announcementDeliveryId) {

        Connection connection = DBConnection.getInstance().getConnection();
        int announcDeliveryID = 0;
        int userID = 0;
        int announcID = 0;
        AnnouncementDeliveryStatus announcementDeliveryStatus = null;

        String selectSQL = "SELECT *  \n" +
                "FROM ANNOUNCEMENT_DELIVERY  \n" +
                "WHERE ANNOUNCEMENT_DELIVERY_ID = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, announcementDeliveryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                announcDeliveryID = resultSet.getInt(1);
                userID = resultSet.getInt(2);
                announcID = resultSet.getInt(3);
                announcementDeliveryStatus = AnnouncementDeliveryStatus.valueOf(resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AnnouncementDelivery announcementDelivery = new AnnouncementDelivery(announcDeliveryID, userID, announcID, announcementDeliveryStatus);

        return announcementDelivery;

    }

    @Override
    public boolean updateAnnouncementDelivery(AnnouncementDelivery announcementDelivery) {

        Connection connection = DBConnection.getInstance().getConnection();
        String updateSQL = "UPDATE ANNOUNCEMENT_DELIVERY \n" +
                "SET ANNOUNCEMENT_USER_ID  =  ?  ,  \n" +
                "ANNOUNCEMENT_ID = ?  \n" +
                "ANNOUNCEMENT_DELIVERY_STATUS = ?  \n" +
                "WHERE ANNOUNCEMENT_DELIVERY_ID = ? ";

        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, announcementDelivery.getUserId());
            preparedStatement.setInt(2, announcementDelivery.getAnnouncementId());
            preparedStatement.setString(3, announcementDelivery.getAnnouncementDeliveryStatus().toString());
            int rowEffected = preparedStatement.executeUpdate();
            System.out.println("row updated" + rowEffected);
            if (rowEffected != 0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean deleteAnnouncementDelivery(int announcementDeliveryId) {
        Connection connection = DBConnection.getInstance().getConnection();
        String deleteSQL = "DELETE FROM ANNOUNCEMENT_DELIVERY   \n" +
                "WHERE ANNOUNCEMENT_DELIVERY_ID = ? ";
        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, announcementDeliveryId);
            int rowEffected = preparedStatement.executeUpdate();
            System.out.println("row deleted" + rowEffected);
            if (rowEffected != 0)
                result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
