package main;

import java.util.Map;
import java.util.Queue;

import simulation_type.Rule;

public class SimVars {

	private boolean success;
	private Rule rule;
	private Queue<Double> states;
	private int[] grid_dimensions;
	private Map<Double, String> color_map;
	private String error;
	private double frames_per_second;
	
	public boolean isSuccess() {
		return success;
	}

	public Rule getRule() {
		return rule;
	}

	public Queue<Double> getStates() {
		return states;
	}

	public Map<Double, String> getColor_map() {
		return color_map;
	}

	public String getError() {
		return error;
	}

	public double getFrames_per_second() {
		return frames_per_second;
	}

	public SimVars(boolean success, Rule rule, Queue<Double> states,
			int[] grid_dimensions, Map<Double, String> color_map,
			String error, double frames_per_second) {
		this.success = success;
		this.rule = rule;
		this.states = states;
		this.grid_dimensions = grid_dimensions;
		this.color_map = color_map;
		this.error = error;
		this.frames_per_second = frames_per_second;
	}
}
