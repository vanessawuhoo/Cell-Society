package cells;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import simulation_type.Rule;

public class CellGraph {

	private Map<Integer, Cell> all_cells;
	private String grid_type;
	boolean toroidal;
	private int[] dimensions; // 0:m 1:n

	public CellGraph(Map<Integer, Cell> cells, String grid_type, int m, int n, boolean toroidal) {
		all_cells = cells;
		this.grid_type = grid_type;
		dimensions = new int[] {m, n};
		this.toroidal = toroidal;
	}

	public Map<Integer, Cell> getCells() {
		return all_cells;
	}
	public String getGrid_type() {
		return grid_type;
	}

	public void setGrid_type(String grid_type) {
		this.grid_type = grid_type;
	}

	public boolean isToroidal() {
		return toroidal;
	}

	public void setToroidal(boolean toroidal) {
		this.toroidal = toroidal;
	}
	
	public int[] getDimensions() {
		return dimensions;
	}

	public void setDimensions(int m, int n) {
		dimensions = new int[] {m, n};
	}
	
	public Map<Integer, Map<String, Double>> getStates() {
		Map<Integer, Map<String, Double>> states = new HashMap<Integer, Map<String, Double>>();
		for (int id : all_cells.keySet()) {
			Cell c = all_cells.get(id);
			Map<String, Double> s = c.getState();
			states.put(id, s);
		}
		return states;
	}

	public Queue<Double> getRelevantStates() {
		Queue<Double> states = new LinkedList<Double>();
		// Map<Integer, Map<String, Double>> states =
		// new HashMap<Integer, Map<String, Double>>();
		for (int i = 1; i <= all_cells.keySet().size(); i++) {
			Cell c = all_cells.get(i);
			Double s = c.getState().get("State");
			states.add(s);
		}
		return states;
	}

	public void updateCells(Rule r) {
		Map<Integer, Map<String, Double>> current_states = new HashMap<Integer, Map<String, Double>>();
		for (int id : all_cells.keySet()) {
			Cell c = all_cells.get(id);
			Map<String, Double> s = c.getState();
			current_states.put(id, s);
		}
		Map<Integer, Map<String, Double>> next_states = new HashMap<Integer, Map<String, Double>>();
		// calculate updates
		for (int id : all_cells.keySet()) {
			Cell c = all_cells.get(id);
			List<Cell> connections = c.getConnections();
			Map<Integer, Map<String, Double>> neighboring_states = new HashMap<Integer, Map<String, Double>>();
			for (Cell connection : connections) {
				neighboring_states.put(connection.getId(), connection.getState());
			}
			r.updateCell(id, c.getState(), neighboring_states, current_states, next_states);
		}
		Set<Integer> ids = all_cells.keySet();
		r.fillVoids(ids, next_states);
		// set updates
		for (int id : next_states.keySet()) {
			Map<String, Double> next_state = next_states.get(id);
			Cell c = all_cells.get(id);
			c.setState(next_state);
		}
	}
}
