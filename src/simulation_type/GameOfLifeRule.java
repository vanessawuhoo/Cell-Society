package simulation_type;

import java.util.HashMap;
import java.util.Map;

import cells.Cell;

public class GameOfLifeRule extends Rule {
	public GameOfLifeRule(){}
	
	/*
	 * States: 
	 * "State": 0=dead, 1=alive
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
			Map<Integer, Map<String, Double>> current_states, 
			Map<Integer, Map<String, Double>> next_states) {
		
		int id = c.getId();
		Map<String, Double> cell_state = c.getState();
		Map<Integer, Map<String, Double>> neighboring_states = super.getNeighboringStates(c);
		if (next_states.containsKey(id))
			return;
		double state = cell_state.get("State");
		int alive = 0;
		for (int neighbor_state_id: neighboring_states.keySet()) {
			double n_state = neighboring_states.get(neighbor_state_id).get("State");
			if (n_state == 1){
				alive++;
			}
		}
		if (state == 0){
			if(alive == 3){
				next_states.put(id, makeNewState(1));
			} else{
				next_states.put(id, makeNewState(0));
			}
		}
		if (state == 1){
			if (alive > 3){
				next_states.put(id, makeNewState(0));
			} else if (alive < 2) {
				next_states.put(id, makeNewState(0));
			} else {
				next_states.put(id, makeNewState(1));
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
