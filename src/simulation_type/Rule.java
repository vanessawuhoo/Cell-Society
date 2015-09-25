package simulation_type;

import java.util.Map;
import java.util.Set;

public abstract class Rule {
	/*
	 * Update cell states
	 */
	public abstract void updateCell(int id, Map<String, Double> cell_state,
			Map<Integer, Map<String, Double>> neighboring_states, 
			Map<Integer, Map<String, Double>> current_states,
			Map<Integer, Map<String, Double>> next_states);
	
	/*
	 * Fill in voids
	 */
	public abstract void fillVoids(Set<Integer> ids,
			Map<Integer, Map<String, Double>> next_states);
	
	/*
	 * Update any general parameters
	 */
	public abstract void updateParameter();
	
	/*
	 * update cell parameters
	 */
	public abstract void updateCellParameters();
}
