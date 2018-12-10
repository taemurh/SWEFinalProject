package se2018.SWEFinalProject.Client;

import java.util.Hashtable;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class BurndownChartController {
    final static String performance = "Performance:";

    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;

    @FXML
    LineChart<Number, Number> lineChart;
    
    XYChart.Series series;
    
    public void initialize(){
    	NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        lineChart = new LineChart<Number, Number>(xAxis,yAxis);
    	xAxis.setLabel("time");
    	yAxis.setLabel("Points");
    	series = new XYChart.Series();
    }
    
    public void fillChart(Hashtable<Integer, Integer> burndown) {
    	for (Integer key: burndown.keySet()) {
        	series.getData().add(new XYChart.Data<Integer, Integer>( key, burndown.get(key)));
    	}
    	lineChart.getData().add(series);
    }
    
}
