package eg.gov.iti.jets.controllers;


import com.jfoenix.controls.JFXListView;
import eg.gov.iti.jets.models.dao.implementations.StatisticsDaoDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class DashboardViewController implements Initializable {

    ObservableList<String> listview = FXCollections.observableArrayList("Statistics Of server", "1", "2");
    @FXML
    private StackPane userGenderChartPane;
    @FXML
    private StackPane userstatusChartPane;
    @FXML
    private BarChart usresCountryChart;
    @FXML
    private PieChart usersStatusChart;
    @FXML
    private JFXListView<String> list;
    private ObservableList<XYChart.Series<String, Number>> usersCountriestdata = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> usersGenderdata = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> usersStatusrdata = FXCollections.observableArrayList();
    private StatisticsDaoDaoImpl statistics;

    public void setUsersDAO(StatisticsDaoDaoImpl statistics) {
        this.statistics = statistics;
        drawUsersCountryChart(usersCountriestdata);
        drawUsersStatusChart(userstatusChartPane, usersStatusrdata);
        drawUsersGenderChart(userGenderChartPane, usersGenderdata);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list.setItems(listview);
        list.setCellFactory(param -> new ListCell<String>() {
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(name);
                }
            }
        });
        setUsersDAO(StatisticsDaoDaoImpl.getInstance());
    }


    private void drawPieChart(StackPane stackPane, ObservableList<PieChart.Data> observableList, double usersNo, boolean genderChart) {
        String tooltipText = "";
        PieChart pieChart = new PieChart();
        pieChart.setData(observableList);
        stackPane.getChildren().add(pieChart);

        for (PieChart.Data data : pieChart.getData()) {
            Node slice = data.getNode();
            double percent = (data.getPieValue() / usersNo * 100);
            if (genderChart)
                tooltipText = data.getName() + " = " + String.format("%.2f", percent) + "%";
            else
                tooltipText = data.getName() + " = " + (int) data.getPieValue();
            Tooltip tooltip = new Tooltip(tooltipText);
            Tooltip.install(slice, tooltip);
        }
    }

    private void drawUsersGenderChart(StackPane stackPane, ObservableList<PieChart.Data> usersGenderList) {
        int UsersNum = 0;
        Map<String, Integer> map = statistics.getUsersByGender();
        if (map != null) {
            for (Map.Entry m : map.entrySet()) {
                usersGenderList.add(new PieChart.Data(m.getKey().toString(), Integer.parseInt(m.getValue().toString())));
                UsersNum += Integer.parseInt(m.getValue().toString());
            }
            drawPieChart(stackPane, usersGenderList, UsersNum, true);
        } else {
            System.out.println("Empty users map");
        }
    }

    private void drawUsersStatusChart(StackPane stackPane, ObservableList<PieChart.Data> usersGenderList) {
        int UsersNum = 0;
        Map<String, Integer> map = statistics.getUsersByStatus();
        if (map != null) {

            for (Map.Entry m : map.entrySet()) {
                if (m.getKey().toString().equals("Offline"))
                    usersGenderList.add(new PieChart.Data("OFF-Line", Integer.parseInt(m.getValue().toString())));
                else
                    usersGenderList.add(new PieChart.Data("ON-Line", Integer.parseInt(m.getValue().toString())));
                UsersNum += Integer.parseInt(m.getValue().toString());
            }
            drawPieChart(stackPane, usersGenderList, UsersNum, false);
        } else {
            System.out.println("Empty users map");
        }
    }

    private void drawUsersCountryChart(ObservableList<XYChart.Series<String, Number>> usersCountriesList) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Countries");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("(NO.of Users)");
        XYChart.Series countriesSeries = new XYChart.Series();
        Map<String, Integer> map = statistics.getUsersNumByCountry();
        if (map != null) {
            for (Map.Entry m : map.entrySet()) {
                countriesSeries.getData().add(new XYChart.Data(m.getKey(), m.getValue()));
            }
            usersCountriestdata.add(countriesSeries);
            usresCountryChart.setData(usersCountriestdata);
            usresCountryChart.setTitle("statistics about the users’ country");
            usresCountryChart.setBarGap(50);
            usresCountryChart.setAnimated(true);
        } else {
            System.out.println("Empty users map");
        }

    }
}
