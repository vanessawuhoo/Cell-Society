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
	private double max_shark_energy;
	private double food_energy;
	private double energy_loss;
	private Random random;
	
	public WaTorRule(int dim_x, int dim_y, int fish_breed_steps, int shark_breed_steps,
			double max_shark_energy, double food_energy, double energy_loss) {
		super.setGrid_type("rectangle");
		super.setGrid_parameters(new int[] {dim_x, dim_y});
		this.fish_breed_steps = fish_breed_steps;
		this.shark_breed_steps = shark_breed_steps;
		this.max_shark_energy = max_shark_energy;
		this.food_energy = food_energy;
		this.energy_loss = energy_loss;
		random = new Random();
	}

	/*
	 * States: 
	 * "State": 0=empty, 1=fish, 2=shark
	 * "moves": number of moves the fish or shark has made
	 * "shark_energy": number of energy left in shark
	 */
	@Override
	public void updateCell(int id, Map<String, Double> cell_state, 
			Map<Integer, Map<String, Double>> neighboring_states,
			Map<Integer, Map<String, Double>> current_states, 
			Map<Integer, Map<String, Double>> next_states) {
		if (next_states.containsKey(id))
			return;
		double type = cell_state.get("State");
		double moves = 0;
		if (type == 1 || type == 2) {
			if (cell_state.containsKey("moves"))
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
			double energy_left = max_shark_energy;
			if (cell_state.containsKey("shark_energy"))
				energy_left = cell_state.get("shark_energy");
			Object[] rand_fish = chooseRandomNeighborState(1, neighboring_states, next_states);
			if ((boolean) rand_fish[0]) {
				if (energy_left >= max_shark_energy) {
					energy_left += food_energy;
				}
				if (moves == shark_breed_steps) {
					next_states.put((int) rand_fish[1], createNewState(2, 0, energy_left));
					next_states.put(id, createNewState(2, 0, max_shark_energy));
				} else {
					next_states.put((int) rand_fish[1], createNewState(2, moves + 1, energy_left));
				}
			} else {
				energy_left -= energy_loss;
				if (energy_left <= 0) {
					next_states.put(id, createNewState(0, 0, 0));
				} else {
					Object[] rand_empty = chooseRandomNeighborState(0, neighboring_states, next_states);
					if ((boolean) rand_empty[0]) {
						if (moves == shark_breed_steps) {
							next_states.put((int) rand_empty[1], createNewState(2, 0, energy_left));
							next_states.put(id, createNewState(2, 0, max_shark_energy)); 
						} else {
							next_states.put((int) rand_empty[1], createNewState(2, moves + 1, energy_left));
							next_states.put(id, createNewState(0, 0, 0));
						}
					} else {
						next_states.put(id, createNewState(2, moves + 1, energy_left));
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
				double next_type = next_states.get(neighboring_state_id).get("State");
				if (next_type == 0) {
					states.add(neighboring_state_id);
				}
			} else {
				double neighbor_type = neighboring_states.get(neighboring_state_id).get("State");
				if (neighbor_type == state) {
					states.add(neighboring_state_id);
				}
			}
		}
		return states;
	}
	public Map<String, Double> createNewState(double type, double moves, double energy) {
		Map<String, Double> new_state = new HashMap<String, Double>();
		new_state.put("State", type);
		if (type == 1 || type == 2) {
			new_state.put("moves", moves);
			if (type == 2) {
				new_state.put("shark_energy", energy);
			}
		}
		return new_state;
	}

	@Override
	public void fillVoids(Set<Integer> ids, Map<Integer, Map<String, Double>> next_states) {
		for (int id: ids) {
			if (!next_states.containsKey(id)) {
				next_states.put(id, createNewState(0, 0, 0));
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
