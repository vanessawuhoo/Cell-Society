package simulation_type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cells.Cell;

public class SegregationRule extends Rule {

	/*
	 * States: 0=empty, 1=X, 2=O
	 */
	@Override
	public void updateCell(int id, List<Double> cell_state, Map<Integer, Cell> all_cells, List<List<Double>> neighboring_states,
			Map<Integer, List<Double>> current_states, Map<Integer, List<Double>> next_states) {
		if (next_states.containsKey(id))
			return;
		double state = cell_state.get(0);
		if (state == 0)
			return;
		int total_states = 0;
		int opposite_states = 0;
		for (List<Double> neighbor_state: neighboring_states) {
			total_states++;
			double n_state = neighbor_state.get(0);
			if (n_state != 0) {
				if (state != n_state)
					opposite_states++;
			}
		}
		double opposite_ratio = opposite_states/total_states;
		if (opposite_ratio >= 0.5) {
			for (int id_curr: current_states.keySet()) {
				List<Double> curr_state = current_states.get(id_curr);
				if (curr_state.get(0) == 0) {
					if (!next_states.containsKey(id_curr))
						next_states.put(id_curr, cell_state);
				}
			}
		} else {
			next_states.put(id, cell_state);
		}

	}
	
	@Override
	public void fillVoids(Map<Integer, Cell> all_cells, Map<Integer, List<Double>> next_states) {
		for (int id: all_cells.keySet()) {
			if (!next_states.containsKey(id)) {
				ArrayList<Double> zero = new ArrayList<Double>();
				zero.add((double) 0);
				next_states.put(id, zero);
			}
		}
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
