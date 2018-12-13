package se2018.SWEFinalProject.Client;

import java.util.Hashtable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class BurndownChartController {

    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;

    @FXML
    LineChart<Number, Number> lineChart;
    
}
