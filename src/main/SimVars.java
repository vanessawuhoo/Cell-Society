package main;

import java.util.Map;

import simulation_type.Rule;

public class SimVars {

	public boolean success;
	public Rule rule;
	public Map<Integer, Map<String, Double>> states;
	public Map<Double, String> color_map;
	public String error;
	public double frames_per_second;
	
	public SimVars(boolean success, Rule rule, Map<Integer, 
			Map<String, Double>> states, Map<Double, String> color_map,
			String error, double frames_per_second) {
		this.success = success;
		this.rule = rule;
		this.states = states;
		this.color_map = color_map;
		this.error = error;
		this.frames_per_second = frames_per_second;
	}
}
