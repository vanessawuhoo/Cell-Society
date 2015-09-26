package simulation_type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import cells.Cell;

public class SugarScapeReproductionRule extends SugarScapeRule{

	private int fertile_limit;
	private Random random;
	
	public SugarScapeReproductionRule(int sugar_grow_back_rate, int sugar_grow_back_interval, int fertile_limit) {
		super(sugar_grow_back_rate, sugar_grow_back_interval);
		this.fertile_limit = fertile_limit;
		random = new Random();
	}
	
	/* States: 
	 * all in SugarScapeRule plus (for agent):
	 * "init_sugar": initial sugar endowment level for agent
	 * "gender": 0.0 for male, 1.0 for female
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
		Map<Integer, Map<String, Double>> next_states_1 = agentReproduction(cells);
		super.setUpdates(cells, next_states_1);
	}
	
	private void ageAgents(Map<Integer, Cell> cells) {
		for (int id : cells.keySet()) {
			Cell c = cells.get(id);
			Map<String, Double> cell_state = c.getState();
			if (cell_state.get("Agent") == 1) {
				double agent_age = cell_state.get("age");
				agent_age++;
				if (agent_age == c.getState().get("max_age")) {
					cell_state.put("Agent", 0.0);
				} else {
					cell_state.put("age", agent_age);
				}
			}
			
		}
	}
	
	private Map<Integer, Map<String, Double>> agentReproduction(Map<Integer, Cell> cells) {
		Map<Integer, Map<String, Double>> next_states = new HashMap<Integer, Map<String, Double>>();
		Set<Integer> baby_indices = new HashSet<Integer>();
		Set<Set<Integer>> reproduced_together = new HashSet<Set<Integer>>();
		for (int curr_index: cells.keySet()) {
			Map<String, Double> curr_state;
			if (next_states.keySet().contains(curr_index) && !baby_indices.contains(curr_index)) {
				curr_state = next_states.get(curr_index);
			} else {
				curr_state = new HashMap<String, Double>(cells.get(curr_index).getState());
			}
			if (curr_state.get("Agent") == 1) {
				Map<Integer, Map<String, Double>> neighboring_states = getNeighboringStates(cells.get(curr_index));
				for (int neighbor_index: neighboring_states.keySet()) {
					Map<String, Double> neighbor_state;
					if (next_states.keySet().contains(neighbor_index)) {
						neighbor_state = next_states.get(neighbor_index);
					} else {
						neighbor_state = neighboring_states.get(neighbor_index);
					}
					if (!baby_indices.contains(neighbor_index) && neighbor_state.get("Agent") == 1
							&& curr_state.get("age") <= fertile_limit 
							&& neighbor_state.get("age") <= fertile_limit
							&& curr_state.get("gender") != (neighbor_state.get("gender")))	{
						Set<Integer> parent_pair = new HashSet<Integer>();
						parent_pair.add(curr_index);
						parent_pair.add(neighbor_index);
						if (!reproduced_together.contains(parent_pair)) {
							int random_empty_index = getRandomEmptyCellIndex(cells.get(curr_index),
									cells.get(neighbor_index), next_states);
							if (random_empty_index >= 0) {
								Map<String, Double> empty_state;
								if (next_states.containsKey(random_empty_index))
									empty_state = next_states.get(random_empty_index);
								else
									empty_state = new HashMap<String, Double>(cells.get(random_empty_index).getState());
								Map<String, Double> p1_state = curr_state;
								Map<String, Double> p2_state = neighbor_state;
								double endowment_1 = handleParent(p1_state);
								double endowment_2 = handleParent(p2_state);
								double total_endowment = endowment_1 + endowment_2;
								Map<String, Double> baby_state = createNewBabyState(empty_state, total_endowment);
								next_states.put(random_empty_index, baby_state);
								baby_indices.add(random_empty_index);
								reproduced_together.add(parent_pair);
							}
							
						}
					}
				}
			}
		}
		return next_states;
	}
	
	private int getRandomEmptyCellIndex(Cell c1, Cell c2, Map<Integer, Map<String, Double>> next_states) {
		List<Integer> empty_indices = new ArrayList<Integer>();
		Map<Integer, Map<String, Double>> neighboring_states_1 = getNeighboringStates(c1);
		Map<Integer, Map<String, Double>> neighboring_states_2 = getNeighboringStates(c2);
		addEmptyIndices(empty_indices, neighboring_states_1, next_states);
		addEmptyIndices(empty_indices, neighboring_states_2, next_states);
		if (empty_indices.size() > 0) {
			Collections.shuffle(empty_indices);
			int rand_empty_index = empty_indices.get(0);
			return rand_empty_index;
		} else {
			return -1;
		}
		
	}
	
	private void addEmptyIndices(List<Integer> empty_indices,
			Map<Integer, Map<String, Double>> neighboring_states, Map<Integer, Map<String, Double>> next_states) {
		for (int i: neighboring_states.keySet()) {
			if (!empty_indices.contains(i)) {
				boolean empty = false;
				if (next_states.keySet().contains(i)) {
					if (next_states.get(i).get("Agent") == 0)
						empty = true;
				} else {
					if (neighboring_states.get(i).get("Agent") == 0)
						empty = true;
				}
				if (empty)
					empty_indices.add(i);
			}
		}
	}
	
	private double handleParent(Map<String, Double> parent_state) {
		double init_sugar = parent_state.get("init_sugar");
		double sugar_level = parent_state.get("sugar_level");
		if (sugar_level > init_sugar) {
			parent_state.put("sugar_level", sugar_level - init_sugar);
			return init_sugar;
		} else {
			parent_state.put("Agent", 0.0);
			return sugar_level;
		}
	}
	
	private Map<String, Double> createNewBabyState(Map<String, Double> curr_state, double total_sugar_endowment) {
		Map<String, Double> new_state = new HashMap<String, Double>(curr_state);
		new_state.put("Agent", 1.0);
		new_state.put("sugar_level", total_sugar_endowment);
		new_state.put("sugar_metabolism", 1.0);
		new_state.put("vision", 1.0);
		if (random.nextDouble() > 0.5) {
			new_state.put("gender", 0.0);
		} else {
			new_state.put("gender", 1.0);
		}
		new_state.put("age", 0.0);
		new_state.put("max_age", 60.0 + random.nextInt(40));
		return new_state;
	}
}
