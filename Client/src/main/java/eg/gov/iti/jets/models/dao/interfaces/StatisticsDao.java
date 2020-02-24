package eg.gov.iti.jets.models.dao.interfaces;

import java.util.Map;

public interface StatisticsDao {
    Map<String, Integer> getUsersByGender();

    Map<String, Integer> getUsersByStatus();

    Map<String, Integer> getUsersNumByCountry();
}
