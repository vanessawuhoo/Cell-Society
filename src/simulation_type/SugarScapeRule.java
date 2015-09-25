package simulation_type;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import cells.Cell;

public class SugarScapeRule extends Rule {
	int sugar_grow_back_rate;
	int sugar_grow_back_interval;
	int sugar_time;
	
	public SugarScapeRule(int sugar_grow_back_rate, int sugar_grow_back_interval) {
		this.sugar_grow_back_rate = sugar_grow_back_rate;
		this.sugar_grow_back_interval = sugar_grow_back_interval;
		sugar_time = sugar_grow_back_interval;
	}
	
	/* States: 
	 * "State": int, 0->10, sugar levels of cell patch
	 * "max_sugar_level": maximum level of sugar
	 * "Agent": 0=empty, 1=agent present
	 * "sugar_level": agent's sugar level
	 * "sugar_metabolism": agent's sugar metabolism
	 * "vision": int, # tiles agent can see to
	 */
	public void updateCells(Map<Integer, Cell> cells) {
		patchGrowBack(cells);
		Map<Integer, Map<String, Double>> current_states = super.getStates(cells);
		Map<Integer, Map<String, Double>> next_states = new HashMap<Integer, Map<String, Double>>();
		for (int id : cells.keySet()) {
			Cell c = cells.get(id);
			agentMovement(c, current_states, next_states);
		}
	}
	
	private void patchGrowBack(Map<Integer, Cell> cells) {
		sugar_time--;
		if (sugar_time == 0) {
			for (int id : cells.keySet()) {
				Cell c = cells.get(id);
				Map<String, Double> cell_state = c.getState();
				double sugar_level = cell_state.get("sugar_level");
				double max_sugar_level = cell_state.get("max_sugar_level");
				double temp_sugar_level = sugar_level + sugar_grow_back_rate;
				if (temp_sugar_level <= max_sugar_level) {
					sugar_level = temp_sugar_level;
				} else { 
					sugar_level = max_sugar_level;
				}
				cell_state.put("sugar_level", sugar_level);
			}
			sugar_time = sugar_grow_back_rate;
		}
	}
	
	public void agentMovement(Cell c,
			Map<Integer, Map<String, Double>> current_states, Map<Integer, Map<String, Double>> next_states) {
		int id = c.getId();
		Map<String, Double> cell_state = c.getState();
		Map<Integer, Map<String, Double>> neighboring_states = super.getNeighboringStates(c);
		
		// Breadth first search
		Set<Cell> traversed_cells = new HashSet<Cell>();
		Queue<Cell> cells_to_traverse = new LinkedList<Cell>(c.getConnections());
		Queue<Cell> next_cells_to_traverse = new LinkedList<Cell>();
		Cell empty_sugar_patch_with_highest_sugar = c;
		double highest_sugar_value = cell_state.get("State");
		for (int i = 1; i < cell_state.get("vision"); i++) {
			while(!cells_to_traverse.isEmpty()) {
				Cell next_cell = cells_to_traverse.poll();
				Map<String, Double> next_cell_state = next_cell.getState();
				traversed_cells.add(next_cell);
				double agent_present = next_cell_state.get("Agent");
				if (agent_present == 0) {
					double next_cell_sugar_value = next_cell_state.get("State");
					if (next_cell_sugar_value > highest_sugar_value) {
						empty_sugar_patch_with_highest_sugar = next_cell;
						highest_sugar_value = next_cell_sugar_value;
					}
				}
				List<Cell> next_cell_neighbors = next_cell.getConnections();
				for (Cell next_cell_neighbor: next_cell_neighbors) {
					if (!traversed_cells.contains(next_cell_neighbor)) {
						next_cells_to_traverse.add(next_cell_neighbor);
					}
				}
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
