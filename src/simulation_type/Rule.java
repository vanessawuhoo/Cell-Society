package simulation_type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cells.Cell;

public abstract class Rule {
	
	public abstract void updateCells(Map<Integer, Cell> cells);

	protected void setUpdates(Map<Integer, Cell> cells, Map<Integer, Map<String, Double>> next_states) {
		for (int id : next_states.keySet()) {
			Map<String, Double> next_state = next_states.get(id);
			Cell c = cells.get(id);
			c.setState(next_state);
		}
	}
	
	protected Map<Integer, Map<String, Double>> getStates(Map<Integer, Cell> cells) {
		Map<Integer, Map<String, Double>> states = new HashMap<Integer, Map<String, Double>>();
		for (int id : cells.keySet()) {
			Cell c = cells.get(id);
			Map<String, Double> s = c.getState();
			states.put(id, s);
		}
		return states;
	}
	
	protected Map<Integer, Map<String, Double>> getNeighboringStates(Cell c) {
		List<Cell> connections = c.getConnections();
		Map<Integer, Map<String, Double>> neighboring_states = new HashMap<Integer, Map<String, Double>>();
		for (Cell connection : connections) {
			neighboring_states.put(connection.getId(), connection.getState());
		}
		return neighboring_states;
	}
	
	/*
	 * Update any general parameters
	 */
	public abstract void updateParameter();
	
	/*
	 * update cell parameters
	 */
	public abstract void updateCellParameters();
}
