package simulation_type;

import java.util.HashMap;
import java.util.Map;

import cells.Cell;

public class SugarScapeReproductionRule extends SugarScapeRule{

	private int fertile_limit;
	public SugarScapeReproductionRule(int sugar_grow_back_rate, int sugar_grow_back_interval, int fertile_limit) {
		super(sugar_grow_back_rate, sugar_grow_back_interval);
		this.fertile_limit = fertile_limit;
	}
	
	/* States: 
	 * all in SugarScapeRule plus:
	 * "gender": m for male, f for female
	 * "age": age of agent
	 * "max_age": age when agent dies
	 */
	@Override
	public void updateCells(Map<Integer, Cell> cells) {
		ageAgents(cells);
		patchGrowBack(cells);
		Map<Integer, Map<String, Double>> current_states = super.getStates(cells);
		Map<Integer, Map<String, Double>> next_states = new HashMap<Integer, Map<String, Double>>();
		for (int id : cells.keySet()) {
			Cell c = cells.get(id);
			agentMovement(c, current_states, next_states);
		}
		super.setUpdates(cells, next_states);
	}
	
	protected void ageAgents(Map<Integer, Cell> cells) {
		for (int id : cells.keySet()) {
			Cell c = cells.get(id);
			Map<String, Double> cell_state = c.getState();
			if (cell_state.get("Agent") == 1) {
				double agent_age = c.getState().get("age");
				agent_age++;
				if (agent_age == c.getState().get("max_age")) {
					cell_state.put("Agent", 0.0);
				} else {
					
				}
			}
			
		}
	}
}
