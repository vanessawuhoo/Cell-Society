package main;

import java.util.Map;

import simulation_type.Rule;

public class SimVars {

	public boolean success;
	public Rule rule;
	public Map<Integer, Map<String, Double>> states;
	public String error;
	
	public SimVars(boolean success, Rule rule, Map<Integer, Map<String, Double>> states,
			String error) {
		this.success = success;
		this.rule = rule;
		this.states = states;
		this.error = error;
	}
}
