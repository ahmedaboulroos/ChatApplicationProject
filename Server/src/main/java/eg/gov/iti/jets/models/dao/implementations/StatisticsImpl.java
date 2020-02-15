package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.Statistics;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StatisticsImpl extends UnicastRemoteObject implements Statistics {
    private static Connection connection;

    public StatisticsImpl() throws RemoteException {
        DBConnection.getInstance().initConnection();
        connection = DBConnection.getInstance().getConnection();
    }

    public static void main(String[] args) throws RemoteException {
        DBConnection.getInstance().initConnection();
        connection = DBConnection.getInstance().getConnection();
        StatisticsImpl rr = new StatisticsImpl();
        Map<String, Integer> map = rr.getUsersByGender();
        System.out.println("The collection is: " + map.values());
    }

    @Override
    public Map<String, Integer> getUsersByGender() {
        Map<String, Integer> usersNumByGendermap = new HashMap<String, Integer>();
        String sql = "SELECT count(USER_ID) , USER_GENDER from APP_USER group by(USER_GENDER)";
        ResultSet resultSet = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                usersNumByGendermap.put(resultSet.getString(2), resultSet.getInt(1));
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usersNumByGendermap;
    }

    @Override
    public Map<String, Integer> getUsersByStatus() {
        Map<String, Integer> usersNumByStatusmap = new HashMap<String, Integer>();
        String sql = "Select Count(USER_ID),USER_STATUS from APP_USER where USER_STATUS in('Available','Offline') group by(USER_STATUS)";
        ResultSet resultSet = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                usersNumByStatusmap.put(resultSet.getString(2), resultSet.getInt(1));
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usersNumByStatusmap;
    }

    @Override
    public Map<String, Integer> getUsersNumByCountry() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        String sql = "SELECT count(USER_ID) , COUNTRY from APP_USER group by(COUNTRY)";
        ResultSet resultSet = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                map.put(resultSet.getString(2), resultSet.getInt(1));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


}




