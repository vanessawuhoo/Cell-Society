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
	protected int sugar_grow_back_rate;
	protected int sugar_grow_back_interval;
	protected int sugar_time;
	
	public SugarScapeRule(int sugar_grow_back_rate, int sugar_grow_back_interval) {
		this.sugar_grow_back_rate = sugar_grow_back_rate;
		this.sugar_grow_back_interval = sugar_grow_back_interval;
		sugar_time = sugar_grow_back_rate;
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
			if (c.getState().get("Agent") == 1)
				agentMovement(c, current_states, next_states);
		}
		super.setUpdates(cells, next_states);
	}
	
	protected void patchGrowBack(Map<Integer, Cell> cells) {
		sugar_time--;
		if (sugar_time == 0) {
			for (int id : cells.keySet()) {
				Cell c = cells.get(id);
				Map<String, Double> cell_state = c.getState();
				double sugar_level = cell_state.get("State");
				double max_sugar_level = cell_state.get("max_sugar_level");
				double temp_sugar_level = sugar_level + sugar_grow_back_interval;
				if (temp_sugar_level <= max_sugar_level) {
					sugar_level = temp_sugar_level;
				} else { 
					sugar_level = max_sugar_level;
				}
				cell_state.put("State", sugar_level);
			}
			sugar_time = sugar_grow_back_rate;
		}
	}
	
	protected void agentMovement(Cell c,
			Map<Integer, Map<String, Double>> current_states, Map<Integer, Map<String, Double>> next_states) {
		int id = c.getId();
		Map<String, Double> cell_state = c.getState();
		
		// Breadth first search
		Set<Cell> traversed_cells = new HashSet<Cell>();
		Queue<Cell> cells_to_traverse = new LinkedList<Cell>(c.getConnections());
		Queue<Cell> next_cells_to_traverse = new LinkedList<Cell>();
		Cell best_cell = c;
		double highest_sugar_value = cell_state.get("State");
		
		double vision = cell_state.get("vision");
		for (int i = 1; i <= vision; i++) {
			while(!cells_to_traverse.isEmpty()) {
				Cell next_cell = cells_to_traverse.poll();
				boolean consider_next_cell = true;
				int next_id = (next_cell.getId());
				if (next_states.keySet().contains(next_id)) {
					double agent_present = next_states.get(next_id).get("Agent");
					if (agent_present == 1) {
						consider_next_cell = false;
					}
				}
				if (consider_next_cell) {
					Map<String, Double> next_cell_state = next_cell.getState();
					traversed_cells.add(next_cell);
					double agent_present = next_cell_state.get("Agent");
					if (agent_present == 0) {
						double next_cell_sugar_value = next_cell_state.get("State");
						if (next_cell_sugar_value > highest_sugar_value) {
							best_cell = next_cell;
							highest_sugar_value = next_cell_sugar_value;
						}
					}
				}
				List<Cell> next_cell_neighbors = next_cell.getConnections();
				for (Cell next_cell_neighbor: next_cell_neighbors) {
					if (!traversed_cells.contains(next_cell_neighbor)) {
						next_cells_to_traverse.add(next_cell_neighbor);
					}
				}
				
			}
			cells_to_traverse = next_cells_to_traverse;
			next_cells_to_traverse = new LinkedList<Cell>();
		}
		
		double patch_sugar_level = cell_state.get("State");
		double max_sugar_level = cell_state.get("max_sugar_level");
		double agent_sugar_level = cell_state.get("sugar_level");
		double metabolism = cell_state.get("sugar_metabolism");
		if (best_cell == c) {
			double temp_patch_sugar_level = patch_sugar_level - metabolism;
			if (temp_patch_sugar_level > 0) {
				Map<String, Double> new_current_state = createNewAgentState(c, c,
						temp_patch_sugar_level, max_sugar_level, agent_sugar_level, metabolism);
				next_states.put(id, new_current_state);
			} else {
				Map<String, Double> new_current_state = createNewState(best_cell, 0,
						max_sugar_level, 0);
				next_states.put(id, new_current_state);
			}
		} else {
			Map<String, Double> new_current_state = createNewState(best_cell, patch_sugar_level,
					max_sugar_level, 0);
			next_states.put(id, new_current_state);
			Cell next_cell = best_cell;
			int next_id = next_cell.getId();
			Map<String, Double> next_cell_state = next_cell.getState();
			double next_patch_sugar_level = next_cell_state.get("State");
			double next_max_sugar_level = next_cell_state.get("max_sugar_level");
			double temp_patch_sugar_level = next_patch_sugar_level - metabolism;
			if (temp_patch_sugar_level > 0) {
				Map<String, Double> new_state = createNewAgentState(best_cell, c, temp_patch_sugar_level,
						next_max_sugar_level, agent_sugar_level, metabolism);
				next_states.put(next_id, new_state);
			} else {
				Map<String, Double> new_state = createNewState(best_cell, 0, next_max_sugar_level, 0);
				next_states.put(next_id, new_state);
			}
		}
	}

	protected Map<String, Double> createNewState(Cell c, double patch_sugar_level, double max_sugar_level,
			double agent_present) {
		Map<String, Double> new_state = new HashMap<String, Double>(c.getState());
		new_state.put("State", patch_sugar_level);
		new_state.put("max_sugar_level", max_sugar_level);
		new_state.put("Agent", agent_present);
		return new_state;
	}
	
	private Map<String, Double> createNewAgentState(Cell c, Cell prev_agent_cell, double patch_sugar_level,
			double max_sugar_level, double agent_sugar_level, double metabolism) {
		Map<String, Double> new_state = createNewState(c, patch_sugar_level, max_sugar_level, 1.0);
		for (String state_property: prev_agent_cell.getState().keySet()) {
			if (!state_property.equals("State") && !state_property.equals("max_sugar_level") 
					&& !state_property.equals("Agent") && !state_property.equals("agent_sugar_level")) {
				new_state.put(state_property, prev_agent_cell.getState().get(state_property));
			} else if (!state_property.equals("State") && !state_property.equals("max_sugar_level") 
					&& !state_property.equals("Agent")) { // state_property.equals("agent_sugar_level")
				new_state.put("agent_sugar_level", agent_sugar_level + metabolism);
			}
		}
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
