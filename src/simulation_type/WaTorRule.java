package simulation_type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WaTorRule extends Rule {
	
	private int fish_breed_steps;
	private int shark_breed_steps;
	private int max_shark_health;
	private Random random;
	
	public WaTorRule(int dim_x, int dim_y, int fish_breed_steps, int shark_breed_steps, int max_shark_health) {
		super.setGrid_type("rectangle");
		super.setGrid_parameters(new int[] {dim_x, dim_y});
		this.fish_breed_steps = fish_breed_steps;
		this.shark_breed_steps = shark_breed_steps;
		this.max_shark_health = max_shark_health;
		random = new Random();
	}

	/*
	 * States: 
	 * "type": 0=empty, 1=fish, 2=shark
	 * "moves": number of moves the fish or shark has made
	 * "shark_health": number of health left in shark
	 */
	@Override
	public void updateCell(int id, Map<String, Double> cell_state, 
			Map<Integer, Map<String, Double>> neighboring_states,
			Map<Integer, Map<String, Double>> current_states, 
			Map<Integer, Map<String, Double>> next_states) {
		if (next_states.containsKey(id))
			return;
		double type = cell_state.get("type");
		double moves = 0;
		if (type == 1 || type == 2) {
			moves = cell_state.get("moves");
		}
		if (type == 1) {
			Object[] rand_empty = chooseRandomNeighborState(0, neighboring_states, next_states);
			if (moves == fish_breed_steps) {
				next_states.put(id, createNewState(1, 0, 0));
				if ((boolean) rand_empty[0]) {
					int empty_state_id = (int) rand_empty[1];
					next_states.put(empty_state_id, createNewState(1, 0, 0));
				}
			} else {
				if ((boolean) rand_empty[0]) {
					int empty_state_id = (int) rand_empty[1];
					next_states.put(id, createNewState(0, 0, 0));
					next_states.put(empty_state_id, createNewState(1, moves + 1, 0));
				} else {
					next_states.put(id, createNewState(1, moves + 1, 0));
				}
			}
		} else if (type == 2) {
			double health_left = cell_state.get("shark_health");
			double curr_moves = cell_state.get("moves");
			Object[] rand_fish = chooseRandomNeighborState(1, neighboring_states, next_states);
			if ((boolean) rand_fish[0]) {
				if (health_left != max_shark_health) {
					health_left++;
				}
				if (curr_moves == shark_breed_steps) {
					next_states.put((int) rand_fish[1], createNewState(2, 0, health_left));
					next_states.put(id, createNewState(2, 0, max_shark_health));
				} else {
					next_states.put((int) rand_fish[1], createNewState(2, curr_moves + 1, health_left));
				}
			} else {
				health_left--;
				if (health_left == 0) {
					next_states.put(id, createNewState(0, 0, 0));
				} else {
					Object[] rand_empty = chooseRandomNeighborState(0, neighboring_states, next_states);
					if ((boolean) rand_empty[0]) {
						if (curr_moves == shark_breed_steps) {
							next_states.put((int) rand_empty[1], createNewState(2, 0, health_left));
							next_states.put(id, createNewState(2, 0, max_shark_health)); 
						} else {
							next_states.put((int) rand_empty[1], createNewState(2, curr_moves + 1, health_left));
							next_states.put(id, createNewState(0, 0, 0));
						}
					} else {
						next_states.put(id, createNewState(2, curr_moves + 1, health_left));
					}
				}
			}
		}
	}
	// return values: 0- boolean if empty state exists, 1- index of random empty state
	public Object[] chooseRandomNeighborState(double state,
		Map<Integer, Map<String, Double>> neighboring_states,
		Map<Integer, Map<String, Double>> next_states) {
		List<Integer> states = getNeighborStates(state, neighboring_states, next_states);
		if (states.isEmpty()) {
			return new Object[] {false, 0};
		}
		int num_states = states.size();
		int random_state_index = random.nextInt(num_states);
		return new Object[] {true, states.get(random_state_index)};
	}
	public List<Integer> getNeighborStates(double state,
			Map<Integer, Map<String, Double>> neighboring_states,
			Map<Integer, Map<String, Double>> next_states) {
		List<Integer> states = new ArrayList<Integer>();
		for (int neighboring_state_id: neighboring_states.keySet()) {
			if (state == 0 && next_states.keySet().contains(neighboring_state_id)) {
				double next_type = next_states.get(neighboring_state_id).get("type");
				if (next_type == 0) {
					states.add(neighboring_state_id);
				}
			} else {
				double neighbor_type = neighboring_states.get(neighboring_state_id).get("type");
				if (neighbor_type == state) {
					states.add(neighboring_state_id);
				}
			}
		}
		return states;
	}
	public Map<String, Double> createNewState(double type, double moves, double health) {
		Map<String, Double> new_state = new HashMap<String, Double>();
		new_state.put("type", type);
		if (type == 1 || type == 2) {
			new_state.put("moves", moves);
			if (type == 2) {
				new_state.put("shark_health", health);
			}
		}
		return new_state;
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
