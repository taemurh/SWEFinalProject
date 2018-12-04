package se2018.SWEFinalProject.Client;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class BurndownChartController {
    final static String performance = "Performance:";

    @FXML
    NumberAxis xAxis;
    @FXML
    CategoryAxis yAxis;

    @FXML
    BarChart<String, Number> bc;
    
    public void initialize(){
        xAxis.setLabel("Percent");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Performance");

        XYChart.Series<String, Number> series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data( performance, 80));
        bc.getData().add(series1);
    }
    
}
