package simulation_type;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameOfLifeRule extends Rule {
	public GameOfLifeRule(int dim_x, int dim_y){
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
	 * "State": 0=white, 1= black
	 */
	@Override
	public void updateCell(int id, Map<String, Double> cell_state, 
			Map<Integer, Map<String, Double>> neighboring_states,
			Map<Integer, Map<String, Double>> current_states, 
			Map<Integer, Map<String, Double>> next_states) {
		if (next_states.containsKey(id))
			return;
		double state = cell_state.get("State");
		int alive = 0;
		int dead = 0;
		for (int neighbor_state_id: neighboring_states.keySet()) {
			Map<String, Double> neighbor_state = neighboring_states.get(neighbor_state_id);
			double n_state = neighbor_state.get("State");
			if (n_state == 0) {
				dead++;
			}
			if (n_state == 1){
				alive++;
			}
		}
		if (state == 0){
			if(alive == 3){
				//update state to 1
			} else{
				//keep state to 0
			}
		}
		if (state == 1){
			if(alive > 3){
				//update state to 0
			}
			if(alive < 2){
				//update state to 0
			}
			else{
				//keep state at 1
			}
		}
	
	}
	
	private Map<String, Double> makeNewState(double s) {
		Map<String, Double> new_state = new HashMap<String, Double>();
		new_state.put("State", s);
		return new_state;
	}
	
	@Override
	public void fillVoids(Set<Integer> ids, Map<Integer, Map<String, Double>> next_states) {
		for (int id: ids) {
			if (!next_states.containsKey(id)) {
				Map<String, Double> zero = new HashMap<String, Double>();
				zero.put("State", (double) 0);
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
