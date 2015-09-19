package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import cells.Cell;
import cells.CellGraph;
import data.AllData;
import data.XMLLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import simulation_type.Rule;
import simulation_type.SegregationRule;
import ui.Display;

public class Hub {

	private XMLLoader xml_loader;
	private Display display;
	
	private Timeline animation;
	private double frames_per_second = 2;
	private final double DELTA_FPS = .3;
	
	private CellGraph cell_graph;
	private Rule rule;
	
	private boolean testing;
	private boolean simulation_running;
	private boolean simulation_loaded;
	
	public Hub(XMLLoader xml_loader, Display display) {
		testing = false;
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
			cell_graph = data.cellGraph;
			rule = data.rule;
			simulation_loaded = true;
			Queue<Double> states = cell_graph.getRelevantStates();
			return new SimVars(true, rule, states, color_map, "", frames_per_second);
		}
		return new SimVars(false, null, null, null, "Simulation running",
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
		Queue<Double> states = cell_graph.getRelevantStates();
		return states;
	}
	
	public Rule getRule(){
		return rule;
	}
	
	public int[] getParameters(){
		return xml_loader.getParser(xml_loader.getRuleName()).getDimensions();
	}
	
	private KeyFrame getStepKeyFrame() {
		double second_delay = 1 / frames_per_second;
		KeyFrame frame = new KeyFrame(Duration.seconds(second_delay),
				e -> animationStep());
		return frame;
	}

	public double increaseRate() {
		animation.stop();
		Timeline newAnimation = new Timeline();
		frames_per_second += DELTA_FPS;
		newAnimation.getKeyFrames().add(getStepKeyFrame());
		newAnimation.setCycleCount(Timeline.INDEFINITE);
		newAnimation.play();
		animation = newAnimation;
		return frames_per_second;
	}
	
	public double decreaseRate() {
		animation.stop();
		Timeline newAnimation = new Timeline();
		double temp_fps = frames_per_second - DELTA_FPS;
		if (temp_fps > 0) {
			frames_per_second = temp_fps;
			newAnimation.getKeyFrames().add(getStepKeyFrame());
			newAnimation.setCycleCount(Timeline.INDEFINITE);
			newAnimation.play();
			animation = newAnimation;
		}
		return frames_per_second;
	}
	

}
