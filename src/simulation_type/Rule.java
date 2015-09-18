package simulation_type;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Rule {
	
	private String grid_type;
	private int[] grid_parameters;
	private Map<Double, String> ui_state_map;
	
	public String getGrid_type() {
		return grid_type;
	}

	public void setGrid_type(String grid_type) {
		this.grid_type = grid_type;
	}

	public int[] getGrid_parameters() {
		return grid_parameters;
	}

	public void setGrid_parameters(int[] grid_parameters) {
		this.grid_parameters = grid_parameters;
	}
	
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
