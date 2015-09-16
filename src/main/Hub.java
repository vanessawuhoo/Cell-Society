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
import javafx.util.Duration;
import simulation_type.Rule;
import simulation_type.SegregationRule;
import ui.Display;

public class Hub {

	private XMLLoader xml_loader;
	private Display display;
	
	private Timeline animation;
	private int FRAMES_PER_SECOND = 2;
	
	private CellGraph cell_graph;
	private Rule rule;
	
	private boolean simulation_running;
	private boolean simulation_loaded;
	
	public Hub(XMLLoader xml_loader, Display display) {
		this.xml_loader = xml_loader;
		this.display = display;
		simulation_running = false;
		simulation_loaded = false;
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
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
			if ((Boolean) cell_graph_and_rule[0]) {
				Map<Integer, Cell> cell_graph_init_map =
						(Map<Integer, Cell>) cell_graph_and_rule[1];
				cell_graph = new CellGraph(cell_graph_init_map);
				rule = (Rule) cell_graph_and_rule[2];
				simulation_loaded = true;
				Map<Integer, List<Double>> states = cell_graph.getStates();
				return new SimVars(true, rule, states, "");
			} else {
				// Note, errors should be stored in resource file
				return new SimVars(false, null, null, "XML file parsing failed");
			}	
		}
		return new SimVars(false, null, null, "Simulation running");
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
		Map<Integer, List<Double>> states = cell_graph.getStates();
		return new SimVars(true, rule, states, "");
	}
	private Cell getTestCell(int id, int segregation_state) {
		List<Double> s = new ArrayList<Double>();
		s.add((double) segregation_state);
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
		animation.getKeyFrames().add(getStepKeyFrame());
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
	
	public boolean simulationStep() {
		if (!simulation_loaded | simulation_running)
			return false;
		step();
		return true;
	}
	
	private void step() {
		cell_graph.updateCells(rule);
		Map<Integer, List<Double>> states = cell_graph.getStates();
		display.update(states);
	}

	private KeyFrame getStepKeyFrame() {
		double second_delay = 1 / FRAMES_PER_SECOND;
		KeyFrame frame = new KeyFrame(Duration.seconds(second_delay),
				e -> step());
		return frame;
	}
	

}
