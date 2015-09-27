package simulation_type;

import java.util.HashMap;
import java.util.Map;

import cells.Cell;

public class SegregationRule extends Rule {
	public SegregationRule() {}
	
	/*
	 * States: 
	 * "State": 0=empty, 1=X, 2=O
	 */
	@Override
	public void updateCells(Map<Integer, Cell> cells) {
		Map<Integer, Map<String, Double>> current_states = super.getStates(cells);
		Map<Integer, Map<String, Double>> next_states = new HashMap<Integer, Map<String, Double>>();
		for (int id : cells.keySet()) {
			Cell c = cells.get(id);
			updateCell(c, current_states, next_states);
		}
		for (int id: cells.keySet()) {
			if (!next_states.containsKey(id)) {
				next_states.put(id, makeNewState(0));
			}
		}
		super.setUpdates(cells, next_states);
	}

	private void updateCell(Cell c,
			Map<Integer, Map<String, Double>> current_states, 
			Map<Integer, Map<String, Double>> next_states) {
		int id = c.getId();
		Map<String, Double> cell_state = c.getState();
		Map<Integer, Map<String, Double>> neighboring_states = super.getNeighboringStates(c);
		if (next_states.containsKey(id))
			return;
		double state = cell_state.get("State");
		if (state == 0)
			return;
		int total_states = 0;
		int opposite_states = 0;
		for (int neighbor_state_id: neighboring_states.keySet()) {
			Map<String, Double> neighbor_state = neighboring_states.get(neighbor_state_id);
			double n_state = neighbor_state.get("State");
			if (n_state != 0) {
				total_states++;
				if (state != n_state)
					opposite_states++;
			}
		}
		double opposite_ratio = (double) opposite_states/ (double) total_states;
		if (opposite_ratio > 0.5) {
			for (int id_curr: current_states.keySet()) {
				if (next_states.containsKey(id_curr)) {
					double next_state = next_states.get(id_curr).get("State");
					if (next_state == 0) {
						next_states.put(id_curr, makeNewState(state));
						next_states.put(id, makeNewState(0));
						break;
					}
				} else {
					double curr_state = current_states.get(id_curr).get("State");
					if (curr_state == 0) {
						next_states.put(id_curr, makeNewState(state));
						next_states.put(id, makeNewState(0));
						break;
					}
				}
			}
		} else {
			next_states.put(id, makeNewState(state));
		}
	}
	
	private Map<String, Double> makeNewState(double s) {
		Map<String, Double> new_state = new HashMap<String, Double>();
		new_state.put("State", s);
		return new_state;
	}

	@Override
	public void updateParameter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCellParameters() {
		// TODO Auto-generated method stub

	}

}
