package cells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import simulation_type.Rule;

public class CellGraph {

	private Map<Integer, Cell> all_cells;
	
	public CellGraph(Map<Integer, Cell> cells) {
		all_cells = cells;
	}
	
	public Map<Integer, Map<String, Double>> getStates() {
		Map<Integer, Map<String, Double>> states = 
				new HashMap<Integer, Map<String, Double>>();
		for (int id: all_cells.keySet()) {
			Cell c = all_cells.get(id);
			Map<String, Double> s = c.getState();
			states.put(id, s);
		}
		return states;
	}
	
	public void updateCells(Rule r) {
		Map<Integer, Map<String, Double>> current_states = 
				new HashMap<Integer, Map<String, Double>>();
		for (int id: all_cells.keySet()) {
			Cell c = all_cells.get(id);
			Map<String, Double> s = c.getState();
			current_states.put(id, s);
		}
		Map<Integer, Map<String, Double>> next_states = 
				new HashMap<Integer, Map<String, Double>>();
		// calculate updates
		for (int id: all_cells.keySet()) {
			Cell c = all_cells.get(id);
			List<Cell> connections = c.getConnections();
			List<Map<String, Double>> neighboring_states = 
					new ArrayList<Map<String, Double>>();
			for (Cell connection: connections) {
				neighboring_states.add(connection.getState());
			}
			r.updateCell(id, c.getState(), neighboring_states,
					current_states, next_states);
		}
		Set<Integer> ids = all_cells.keySet();
		r.fillVoids(ids, next_states);
		// set updates
		for (int id: next_states.keySet()) {
			Map<String, Double> next_state = next_states.get(id);
			Cell c = all_cells.get(id);
			c.setState(next_state);
		}
	}
}
