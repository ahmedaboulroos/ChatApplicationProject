package eg.gov.iti.jets.controllers;


import eg.gov.iti.jets.models.dao.implementations.StatisticsDaoImpl;
import eg.gov.iti.jets.models.dao.interfaces.StatisticsDao;
import eg.gov.iti.jets.models.network.implementations.ServerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class DashboardViewController implements Initializable {

    StatisticsDao statisticsDao = StatisticsDaoImpl.getInstance();

    @FXML
    private BarChart<CategoryAxis, NumberAxis> countryStatisticsBc;
    @FXML
    private PieChart onlineStatisticsPc;
    @FXML
    private PieChart genderStatisticsPc;
    @FXML
    private Label onlineUsersLbl;
    @FXML
    private Label usersGenderLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCountryStatisticsData();
        loadOnlineStatisticsData();
        loadGenderStatisticsData();
    }

    public void loadCountryStatisticsData() {
        countryStatisticsBc.getData().clear();

        countryStatisticsBc.getXAxis().setLabel("Country");
        countryStatisticsBc.getYAxis().setLabel("Users");

        XYChart.Series<CategoryAxis, NumberAxis> dataSeries = new XYChart.Series<>();
        Map<String, Integer> usersNumByCountry = statisticsDao.getUsersNumByCountry();

        LinkedHashMap<String, Integer> usersNumByCountrySorted = usersNumByCountry.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        usersNumByCountrySorted.forEach((countryName, usersCount) -> {
            dataSeries.getData().add(new XYChart.Data(countryName, usersCount));
        });
        countryStatisticsBc.getData().add(dataSeries);
    }

    public void loadOnlineStatisticsData() {
        onlineUsersLbl.setText("");
        List<PieChart.Data> onlineStatisticsList = new ArrayList<>();

        int numberOfOnlineUsers = ServerService.getAllOnlineClients().size();
        int numberOfOfflineUsers = statisticsDao.getNumberOfAllUsers() - ServerService.getAllOnlineClients().size();

        PieChart.Data onlineEntry = new PieChart.Data("Online", numberOfOnlineUsers);
        PieChart.Data offlineEntry = new PieChart.Data("Offline", numberOfOfflineUsers);

        onlineStatisticsList.add(onlineEntry);
        onlineStatisticsList.add(offlineEntry);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(onlineStatisticsList);
        onlineStatisticsPc.setData(pieChartData);

        onlineStatisticsPc.getData().forEach(data -> {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                onlineUsersLbl.setText("Number of " + data.getName() + " Users: " + (int) data.getPieValue());
            });
        });
    }

    public void loadGenderStatisticsData() {
        usersGenderLbl.setText("");
        List<PieChart.Data> genderStatisticsList = new ArrayList<>();
        Map<String, Integer> usersByGender = statisticsDao.getUsersByGender();

        if (usersByGender != null) {
            usersByGender.forEach((gender, number) -> {
                if (gender != null) {
                    PieChart.Data entry = new PieChart.Data(gender, number);
                    genderStatisticsList.add(entry);
                }
            });

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(genderStatisticsList);
            genderStatisticsPc.setData(pieChartData);

            genderStatisticsPc.getData().forEach(data -> {
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    usersGenderLbl.setText("Number of " + data.getName() + " Users: " + (int) data.getPieValue());
                });
            });
        }
    }

}
