package main;

import java.util.Queue;

public class StepVars {

	private boolean sim_not_running;
	private Queue<Double> states;
	
	public boolean isSim_not_running() {
		return sim_not_running;
	}

	public Queue<Double> getStates() {
		return states;
	}

	public StepVars(boolean sim_not_running, 
			Queue<Double> states) {
		this.sim_not_running = sim_not_running;
		this.states = states;
	}
}
