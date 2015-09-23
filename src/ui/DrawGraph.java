package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class DrawGraph {
	private LineChart<Number, Number> myChart;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private ResourceBundle myResources;
	private Map<Double, Queue<Integer>> graphData;

	public DrawGraph(Map<Double, Queue<Integer>> data) {
		this.graphData = data;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Data");
		initGraph();
		addData();
	}

	private void initGraph() {
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setAutoRanging(true);
		yAxis.setAutoRanging(true);
		xAxis.setLabel(myResources.getString("x"));
		yAxis.setLabel(myResources.getString("y"));
		myChart = new LineChart<Number, Number>(xAxis, yAxis);
		myChart.setTitle(myResources.getString("Title"));
	}

	private void addData() {
		for (Double d : graphData.keySet()) {
			Queue<Integer> queue = graphData.get(d);
			addSeries(queue, d);
		}
	}

	private void addSeries(Queue<Integer> data, Double id) {
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName(id.toString());
		int count = 0;
		while (!data.isEmpty()) {
			series.getData().add(new XYChart.Data<>(count, data.remove()));
			count++;
		}
		myChart.getData().add(series);
	}

	public LineChart<Number, Number> getChart() {
		
		return myChart;
	}
}
