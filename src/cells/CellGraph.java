package cells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulation_type.Rule;

public class CellGraph {

	private Map<Integer, Cell> all_cells;
	
	public CellGraph(Map<Integer, Cell> cells) {
		all_cells = cells;
	}
	
	public Map<Integer, Cell> getCells() {
		return all_cells;
	}
	
	public void updateCells(Rule r) {
		Map<Integer, List<Double>> current_states = new HashMap<Integer, List<Double>>();
		for (int id: all_cells.keySet()) {
			Cell c = all_cells.get(id);
			List<Double> s = c.getState();
			current_states.put(id, s);
		}
		Map<Integer, List<Double>> next_states = new HashMap<Integer, List<Double>>();
		// calculate updates
		for (int id: all_cells.keySet()) {
			Cell c = all_cells.get(id);
			List<Cell> connections = c.getConnections();
			List<List<Double>> neighboring_states = new ArrayList<List<Double>>();
			for (Cell connection: connections) {
				neighboring_states.add(connection.getState());
			}
			r.updateCell(id, c.getState(), all_cells, neighboring_states,
					current_states, next_states);
		}
		r.fillVoids(all_cells, next_states);
		// set updates
		for (int id: next_states.keySet()) {
			List<Double> next_state = next_states.get(id);
			Cell c = all_cells.get(id);
			c.setState(next_state);
		}
	}
}