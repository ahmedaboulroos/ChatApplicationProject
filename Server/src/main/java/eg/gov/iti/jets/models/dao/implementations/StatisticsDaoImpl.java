package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.StatisticsDao;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StatisticsDaoImpl implements StatisticsDao {

    private static StatisticsDaoImpl instance;

    private Connection connection = DBConnection.getConnection();

    public static StatisticsDaoImpl getInstance() {
        if (instance == null) {
            instance = new StatisticsDaoImpl();
        }
        return instance;
    }

    @Override
    public Map<String, Integer> getUsersByGender() {
        Map<String, Integer> usersNumByGenderMap = new HashMap<>();
        String sql = "SELECT count(ID), USER_GENDER from USERS group by(USER_GENDER)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                usersNumByGenderMap.put(resultSet.getString(2), resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usersNumByGenderMap;
    }

    @Override
    public int getNumberOfAllUsers() {
        int numberOfAllUsers = 0;
        String sql = "Select Count(ID) from USERS";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                numberOfAllUsers = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numberOfAllUsers;
    }

    @Override
    public Map<String, Integer> getUsersNumByCountry() {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT distinct count(ID) , COUNTRY from USERS group by(COUNTRY)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(2) != null)
                    map.put(resultSet.getString(2), resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return map;
    }


}




