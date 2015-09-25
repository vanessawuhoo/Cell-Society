package main;

import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import cells.CellGraph;
import data.AllData;
import data.XMLLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import simulation_type.Rule;
import ui.Display;

public class Hub {

	private ResourceBundle myResources;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/Data";
	private XMLLoader xml_loader;
	private Display display;
	
	private Timeline animation;
	private double frames_per_second = 2;
	private final double DELTA_FPS = .3;
	
	private CellGraph cell_graph;
	private Rule rule;

	private boolean simulation_running;
	private boolean simulation_loaded;
	
	public Hub(XMLLoader xml_loader, Display display) {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
		this.xml_loader = xml_loader;
		this.display = display;
		simulation_running = false;
		simulation_loaded = false;
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(getStepKeyFrame());
	}
	
	public XMLLoader getLoader(){
		return xml_loader;
	}
	
	/* Initialize cell_graph and rule
	 * If successful, return true, rule, and initial states of cells
	 * If unsuccessful, return false and reason for error
	 */
	public SimVars loadSimulation(String xml_file_name) {
		System.out.println(xml_file_name);
		if (!simulation_running) {
			xml_loader.setFileName(xml_file_name);
			xml_loader.load();
			xml_loader.parseDataSpecific(xml_loader.getRuleName());
			AllData data = xml_loader.getParser(xml_loader.getRuleName()).getAllData();
			Map<Double, String> color_map = xml_loader.getParser(xml_loader.getRuleName()).getColor();
			cell_graph = data.getCellGraph();
			rule = data.getRule();
			simulation_loaded = true;
			Queue<Double> states = cell_graph.getRelevantStates();
			int[] dimensions = cell_graph.getDimensions();
			int m = dimensions[0]; int n = dimensions[1];
			int dim_x = n; int dim_y = m;
			return new SimVars(true, rule, states, new int[] {dim_x, dim_y}, color_map, "", frames_per_second);
		}
		return new SimVars(false, null, null, null, null, "Simulation running",
				frames_per_second);
	}

	public boolean playSimulation() {
		if (!simulation_loaded | simulation_running)
			return false;
		animation.play();
		simulation_running = true;
		return true;
	}
	
	public boolean pauseSimulation() {
		if (!simulation_loaded | !simulation_running)
			return false;
		animation.pause();
		simulation_running = false;
		return true;
	}
	
	public void simulationStep() {
		if (!simulation_loaded | simulation_running) {
			display.updateStep(new StepVars(false, null));
		}
		Queue<Double> states = step();
		display.updateStep(new StepVars(true, states));
	}
	
	public Queue<Double> testStep() {
		return step();
	}
	
	private void animationStep() {
		Queue<Double> states = step();
		display.update(states);
	}
	
	private Queue<Double> step() {
		cell_graph.updateCells(rule);
		return cell_graph.getRelevantStates();
	}
	
	public Queue<Double> updateGridSettings(String cell_shape, String edge_type, String neighborhood_type) {
		String grid_type = null;
		boolean toroidal = false;
		if (edge_type.equals(myResources.getString("tor"))) {
			toroidal = true;
		}
		if (cell_shape.equals(myResources.getString("sq"))) {
			if (edge_type.equals(myResources.getString("card"))) {
				grid_type = myResources.getString("sq4");
			} else {
				grid_type = myResources.getString("sq8");
			}
		} else {
			grid_type = cell_shape;
		}
		cell_graph.changeGridSettings(grid_type, toroidal);
		return cell_graph.getRelevantStates();
	}
	
	public Rule getRule(){
		return rule;
	}
	
	private KeyFrame getStepKeyFrame() {
		double second_delay = 1 / frames_per_second;
		KeyFrame frame = new KeyFrame(Duration.seconds(second_delay),
				e -> animationStep());
		return frame;
	}

	public double increaseRate() {
		double temp_fps = frames_per_second + DELTA_FPS;
		changeRateAndAnimation(temp_fps);
		return frames_per_second;
	}
	
	public double decreaseRate() {
		double temp_fps = frames_per_second - DELTA_FPS;
		changeRateAndAnimation(temp_fps);
		return frames_per_second;
	}
	
	private void changeRateAndAnimation(double temp_fps) {
		if (temp_fps > 0) {
			animation.stop();
			frames_per_second = temp_fps;
			Timeline newAnimation = new Timeline();
			newAnimation.getKeyFrames().add(getStepKeyFrame());
			newAnimation.setCycleCount(Timeline.INDEFINITE);
			newAnimation.play();
			animation = newAnimation;
		}
	}
}
