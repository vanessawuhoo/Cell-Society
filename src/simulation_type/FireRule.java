package simulation_type;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cells.Cell;

public class FireRule extends Rule {
	
	private double prob_catch;
	private Random random;
	
	public FireRule(double prob_catch) {
		this.prob_catch = prob_catch;
		random = new Random();
	}

	/*
	 * States: 
	 * "State": 0=empty, 1=tree, 2=burning
	 */
	
	@Override
	public void updateCells(Map<Integer, Cell> cells) {
		Map<Integer, Map<String, Double>> current_states = super.getStates(cells);
		Map<Integer, Map<String, Double>> next_states = new HashMap<Integer, Map<String, Double>>();
		for (int id : cells.keySet()) {
			Cell c = cells.get(id);
			updateCell(c, current_states, next_states);
		}
		super.setUpdates(cells, next_states);
	}
	
	private void updateCell(Cell c,
			Map<Integer, Map<String, Double>> current_states, Map<Integer, Map<String, Double>> next_states) {
		int id = c.getId();
		Map<String, Double> cell_state = c.getState();
		Map<Integer, Map<String, Double>> neighboring_states = super.getNeighboringStates(c);
		double state = cell_state.get("State");
		if (state == 0 || state == 2) {
			next_states.put(id, makeNewState(0));
		} else {
			boolean fire_exists = false;
			for (int neighbor_state_id: neighboring_states.keySet()) {
				Map<String, Double> neighbor_state = neighboring_states.get(neighbor_state_id);
				double n_state = neighbor_state.get("State");
				if (n_state == 2) {
					fire_exists = true;
					break;
				}
			}
			if (fire_exists) {
				double rand_num = random.nextDouble();
				if (rand_num < prob_catch) {
					next_states.put(id, makeNewState(2));
				} else {
					next_states.put(id, makeNewState(1));
				}
			}
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