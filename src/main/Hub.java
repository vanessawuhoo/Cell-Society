package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cells.Cell;
import cells.CellGraph;
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
	
	public Hub() {
		loadTestSim();
		testing = true;
	}
	
	public Hub(XMLLoader xml_loader, Display display) {
		testing = false;
		this.xml_loader = xml_loader;
		this.display = display;
		simulation_running = false;
		simulation_loaded = false;
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(getStepKeyFrame());
		//display.startTestSim();
	}
	
	/* Initialize cell_graph and rule
	 * If successful, return true, rule, and initial states of cells
	 * If unsuccessful, return false and reason for error
	 */
	public SimVars loadSimulation(String xml_file_name) {
		if (!simulation_running) {
			xml_loader.setFileName(xml_file_name);
			xml_loader.load();
			Object[] cell_graph_and_rule = xml_loader.getData();
			//xml_loader calls graph, rule, 
			if ((Boolean) cell_graph_and_rule[0]) {
				Map<Integer, Cell> cell_graph_init_map =
						(Map<Integer, Cell>) cell_graph_and_rule[1];
				cell_graph = new CellGraph(cell_graph_init_map);
				rule = (Rule) cell_graph_and_rule[2];
				simulation_loaded = true;
				Map<Integer, Map<String, Double>> states = cell_graph.getStates();
				return new SimVars(true, rule, states, "", frames_per_second);
			} else {
				// Note, errors should be stored in resource file
				return new SimVars(false, null, null, 
						"XML file parsing failed", frames_per_second);
			}	
		}
		return new SimVars(false, null, null, "Simulation running",
				frames_per_second);
	}
	
	/* Test simulation
	 * 
	 */
	public SimVars loadTestSim() {
		Map<Integer, Cell> cell_graph_init_map = new HashMap<Integer, Cell>();
		Cell c1 = getTestCell(1, 0);
		Cell c2 = getTestCell(2, 1);
		Cell c3 = getTestCell(3, 1);
		Cell c4 = getTestCell(4, 2);
		addConnections(c1, new Cell[] {c2,c3});
		addConnections(c2, new Cell[] {c1,c4});
		addConnections(c3, new Cell[] {c1,c4});
		addConnections(c4, new Cell[] {c2,c3});
		cell_graph_init_map.put(1, c1);
		cell_graph_init_map.put(2, c2);
		cell_graph_init_map.put(3, c3);
		cell_graph_init_map.put(4, c4);
		cell_graph = new CellGraph(cell_graph_init_map);
		rule = new SegregationRule(2,2);
		simulation_loaded = true;
		Map<Integer, Map<String, Double>> states = cell_graph.getStates();
		return new SimVars(true, rule, states, "", frames_per_second);
	}
	private Cell getTestCell(int id, int segregation_state) {
		Map<String, Double> s = new HashMap<String, Double>();
		s.put("state", (double) segregation_state);
		Cell c = new Cell(id, s);
		return c;
	}
	private void addConnections(Cell curr_cell, Cell[] conn_array) {
		List<Cell> connections = new ArrayList<Cell>(Arrays.asList(conn_array));
		curr_cell.setConnections(connections);
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
			//display.updateStep(new StepVars(false, null));
		}
		Map<Integer, Map<String, Double>> states = step();
		//display.updateStep(new StepVars(true, states));
	}
	
	public Map<Integer, Map<String, Double>> testStep() {
		return step();
	}
	
	private void animationStep() {
		Map<Integer, Map<String, Double>> states = step();
		//display.update(states);
	}
	
	private Map<Integer, Map<String, Double>> step() {
		cell_graph.updateCells(rule);
		Map<Integer, Map<String, Double>> states = cell_graph.getStates();
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
				e -> step());
		return frame;
	}
	
	public double increaseRate() {
		frames_per_second += DELTA_FPS;
		updateKeyFrame();
		return frames_per_second;
	}
	
	public double decreaseRate() {
		double temp_fps = frames_per_second - DELTA_FPS;
		if (temp_fps > 0) {
			frames_per_second = temp_fps;
			updateKeyFrame();
		}
		return frames_per_second;
	}
	
	private void updateKeyFrame() {
		ObservableList<KeyFrame> keyFrames = animation.getKeyFrames();
		if (!keyFrames.isEmpty()) {
			keyFrames.removeAll();
		}
		keyFrames.add(getStepKeyFrame());
	}
}
