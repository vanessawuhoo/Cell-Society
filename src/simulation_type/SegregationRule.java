package simulation_type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SegregationRule extends Rule {
	public SegregationRule(int dim_x, int dim_y) {
		super.setGrid_type("rectangle");
		super.setGrid_parameters(new int[] {dim_x, dim_y});
	}
	
	public String getGridType() {
		return super.getGrid_type();
	}
	
	public int[] getGridParameters() {
		return super.getGrid_parameters();
	}
	
	/*
	 * States: 
	 * "state": 0=empty, 1=X, 2=O
	 */
	@Override
	public void updateCell(int id, Map<String, Double> cell_state, 
			List<Map<String, Double>> neighboring_states,
			Map<Integer, Map<String, Double>> current_states, 
			Map<Integer, Map<String, Double>> next_states) {
		if (next_states.containsKey(id))
			return;
		double state = cell_state.get("state");
		if (state == 0)
			return;
		int total_states = 0;
		int opposite_states = 0;
		for (Map<String, Double> neighbor_state: neighboring_states) {
			total_states++;
			double n_state = neighbor_state.get("state");
			if (n_state != 0) {
				if (state != n_state)
					opposite_states++;
			}
		}
		double opposite_ratio = opposite_states/total_states;
		if (opposite_ratio >= 0.5) {
			for (int id_curr: current_states.keySet()) {
				double curr_state = current_states.get(id_curr).get("state");
				if (curr_state == 0) {
					if (!next_states.containsKey(id_curr)) {
						next_states.put(id_curr, makeNewState(curr_state));
					}
				}
			}
		} else {
			next_states.put(id, makeNewState(state));
		}
	}
	
	private Map<String, Double> makeNewState(double s){
		Map<String, Double> new_state = new HashMap<String, Double>();
		new_state.put("state", s);
		return new_state;
	}
	
	@Override
	public void fillVoids(Set<Integer> ids, Map<Integer, Map<String, Double>> next_states) {
		for (int id: ids) {
			if (!next_states.containsKey(id)) {
				Map<String, Double> zero = new HashMap<String, Double>();
				zero.put("state", 0.0);
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
