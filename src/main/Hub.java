package main;

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
import ui.Display;

public class Hub {

	private XMLLoader xml_loader;
	private Display display;
	
	private Timeline animation;
	private int FRAMES_PER_SECOND = 60;
	
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
	public Object[] loadSimulation(String xml_file_name) {
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
				return new Object[] {true, rule, getStates()};
			} else {
				// Note, errors should be stored in resource file
				return new Object[] {false, "XML file parsing failed"};
			}	
		}
		return new Object[] {false, "Simulation running"};
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
		display.update(getStates());
	}

	private KeyFrame getStepKeyFrame() {
		int millisecond_delay = 1000 / FRAMES_PER_SECOND;
		KeyFrame frame = new KeyFrame(Duration.millis(millisecond_delay),
				e -> step());
		return frame;
	}
	private Map<Integer, List<Double>> getStates() {
		Map<Integer, List<Double>> states = new HashMap<Integer, List<Double>>();
		Map<Integer, Cell> cell_map = cell_graph.getCells();
		for (int id: cell_map.keySet()) {
			Cell c = cell_map.get(id);
			List<Double> s = c.getState();
			states.put(id, s);
		}
		return states;
	}
}
