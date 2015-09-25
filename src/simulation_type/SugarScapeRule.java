package simulation_type;

import java.util.Map;
import java.util.Set;

public class SugarScapeRule extends Rule {
	int sugar_grow_back_rate;
	int sugar_grow_back_interval;
	
	public SugarScapeRule(int sugar_grow_back_rate, int sugar_grow_back_interval) {
		this.sugar_grow_back_rate = sugar_grow_back_rate;
		this.sugar_grow_back_interval = sugar_grow_back_interval;
	}

	/* States: 
	 * "State": int, 0->10, sugar levels of cell patch
	 * "max_sugar_level": maximum level of sugar
	 * "Agent": 0=empty, 1=agent present
	 * "sugar_level": agent's sugar level
	 * "sugar_metabolism": agent's sugar metabolism
	 * "vision": int, # tiles agent can see to
	 */
	@Override
	public void updateCell(int id, Map<String, Double> cell_state, Map<Integer, Map<String, Double>> neighboring_states,
			Map<Integer, Map<String, Double>> current_states, Map<Integer, Map<String, Double>> next_states) {
		
	}

	@Override
	public void fillVoids(Set<Integer> ids, Map<Integer, Map<String, Double>> next_states) {
		// TODO Auto-generated method stub
		
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
