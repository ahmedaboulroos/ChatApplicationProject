package eg.gov.iti.jets.models.dao.interfaces;

import java.util.Map;

public interface StatisticsDao {
    Map<String, Integer> getUsersByGender();

    int getNumberOfAllUsers();

    Map<String, Integer> getUsersNumByCountry();
}
