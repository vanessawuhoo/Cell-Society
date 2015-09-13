package simulation_type;

import java.util.List;
import java.util.Map;

import cells.Cell;

public abstract class Rule {
	
	/*
	 * Update cell states
	 */
	public abstract void updateCell(int id, List<Double> cell_state,
			Map<Integer, Cell> all_cells,
			List<List<Double>> neighboring_states, 
			Map<Integer, List<Double>> current_states,
			Map<Integer, List<Double>> next_states);
	
	/*
	 * Fill in voids
	 */
	public abstract void fillVoids(Map<Integer, Cell> all_cells,
			Map<Integer, List<Double>> next_states);
	
	/*
	 * Update any general parameters
	 */
	public abstract void updateParameter();
	
	/*
	 * update cell parameters
	 */
	public abstract void updateCellParameters();
}
