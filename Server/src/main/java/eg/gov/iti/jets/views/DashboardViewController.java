package eg.gov.iti.jets.views;


import eg.gov.iti.jets.models.dao.implementations.StatisticsImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Map;
import java.util.ResourceBundle;


public class DashboardViewController implements Initializable {
    private static Connection connection;
    @FXML
    private StackPane userGenderChartPane;
    @FXML
    private StackPane userstatusChartPane;
    @FXML
    private BarChart usresCountryChart;
    @FXML
    private PieChart usersStatusChart;
    private ObservableList<PieChart.Data> usersGenderdata = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> usersStatusrdata = FXCollections.observableArrayList();
    private StatisticsImpl statistics;

    public void setUsersDAO(StatisticsImpl statistics) throws RemoteException {
        this.statistics = statistics;

        drawUsersStatusChart(userstatusChartPane, usersStatusrdata);
        drawUsersGenderChart(userGenderChartPane, usersGenderdata);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            setUsersDAO(new StatisticsImpl());
        } catch (RemoteException e) {
            e.printStackTrace();
        }


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
        for (Map.Entry m : map.entrySet()) {
            usersGenderList.add(new PieChart.Data(m.getKey().toString(), Integer.parseInt(m.getValue().toString())));
            UsersNum += Integer.parseInt(m.getValue().toString());
        }
        drawPieChart(stackPane, usersGenderList, UsersNum, true);

    }

    private void drawUsersStatusChart(StackPane stackPane, ObservableList<PieChart.Data> usersGenderList) {
        int UsersNum = 0;
        Map<String, Integer> map = statistics.getUsersByStatus();
        for (Map.Entry m : map.entrySet()) {
            if (m.getKey().toString().equals("Offline"))
                usersGenderList.add(new PieChart.Data("OFF-Line", Integer.parseInt(m.getValue().toString())));
            else
                usersGenderList.add(new PieChart.Data("ON-Line", Integer.parseInt(m.getValue().toString())));
            UsersNum += Integer.parseInt(m.getValue().toString());
        }
        drawPieChart(stackPane, usersGenderList, UsersNum, false);

    }


}

