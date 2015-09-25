package ui;

import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

//Class that handles the functionality of drawing the graph obtained from the Get Graph button
public class DrawGraph {
	private LineChart<Number, Number> myChart;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private ResourceBundle myResources;
	private Map<Double, Queue<Integer>> graphData;

	//constructor to create class
	public DrawGraph(Map<Double, Queue<Integer>> data) {
		this.graphData = data;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Data");
		initGraph();
		addData();
	}

	//initializes the graph and all of its components
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

	//Adds all of the series into the graph
	private void addData() {
		for (Double d : graphData.keySet()) {
			Queue<Integer> queue = graphData.get(d);
			addSeries(queue, d);
		}
	}

	//Adds an individual series into the graph by state
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

	//returns the chart to be shown in a popup in the UI
	public LineChart<Number, Number> getChart() {
		return myChart;
	}
}
