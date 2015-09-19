package main;

import java.util.Queue;

public class StepVars {

	public boolean sim_not_running;
	public Queue<Double> states;
	
	public StepVars(boolean sim_not_running, 
			Queue<Double> states) {
		this.sim_not_running = sim_not_running;
		this.states = states;
	}
}
