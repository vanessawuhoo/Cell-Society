package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cells.Cell;
import cells.CellGraph;
import data.XMLLoader;
import simulation_type.Rule;
import ui.Display;

public class Hub {

	private XMLLoader xml_loader;
	private Display display;
	
	private double REFRESH_RATE = 30;
	
	private CellGraph cell_graph;
	private Rule rule;
	
	private boolean simulation_running;
	
	public Hub(XMLLoader xml_loader, Display display) {
		this.xml_loader = xml_loader;
		this.display = display;
		simulation_running = false;
	}
	
	/* Initialize cell_graph and rule
	 * If successful, return true, rule, and initial states of cells
	 * If unsuccessful, return false and reason for error
	 */
	
	public Object[] loadSimulation(String xml_file_name) {
		if (!simulation_running) {
			Object[] cell_graph_and_rule = xml_loader.getData(xml_file_name);
			if ((Boolean) cell_graph_and_rule[0]) {
				Map<Integer, Cell> cell_graph_init_map =
						(Map<Integer, Cell>) cell_graph_and_rule[1];
				cell_graph = new CellGraph(cell_graph_init_map);
				rule = (Rule) cell_graph_and_rule[2];
				return new Object[] {true, rule, getStates()};
			} else {
				// Note, errors should be stored in resource file
				return new Object[] {false, "XML file parsing failed"};
			}
			
		}
		
		return new Object[] {false, "Simulation running"};
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
