package main;

import java.util.Map;

public class StepVars {

	public boolean sim_not_running;
	public Map<Integer, Map<String, Double>> states;
	
	public StepVars(boolean sim_not_running, 
			Map<Integer, Map<String, Double>> states) {
		this.sim_not_running = sim_not_running;
		this.states = states;
	}
}
