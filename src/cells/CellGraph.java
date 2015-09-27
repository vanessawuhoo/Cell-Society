package cells;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import simulation_type.Rule;

public class CellGraph {

	private Map<Integer, Cell> all_cells;
	private String grid_type;
	boolean toroidal;
	private int[] dimensions; // 0:m 1:n
	AddNeighbors neighbor_adder;

	/* Grid types:
	 * square_4, square_8, hexagon, triangle
	 */
	public CellGraph(Map<Integer, Cell> cells, String grid_type, int m, int n, boolean toroidal) {
		all_cells = cells;
		this.grid_type = grid_type;
		dimensions = new int[] {m, n};
		this.toroidal = toroidal;
		neighbor_adder = new AddNeighbors();
		connectCells();
	}

	public Map<Integer, Cell> getCells() {
		return all_cells;
	}
	public String getGrid_type() {
		return grid_type;
	}

	public boolean isToroidal() {
		return toroidal;
	}

	public void setToroidal(boolean toroidal) {
		this.toroidal = toroidal;
	}
	
	public int[] getDimensions() {
		return dimensions;
	}

	public void setDimensions(int m, int n) {
		dimensions = new int[] {m, n};
	}
	


	public Queue<Double> getRelevantStates() {
		Queue<Double> states = new LinkedList<Double>();
		for (int i = 1; i <= all_cells.keySet().size(); i++) {
			Cell c = all_cells.get(i);
			double s = 0;
			if (c.getState().containsKey("Agent")) {
				Double agent = c.getState().get("Agent");
				if (agent == 1) {
					if (c.getState().containsKey("gender")) {
						Double gender = c.getState().get("gender");
						if (gender == 0.0) {
							s = -1;
						} else {
							s = -2;
						}
					} else {
						s = -1;
					}
					
				} else {
					s = c.getState().get("State");
				}
			} else {
				s = c.getState().get("State");
			}
			states.add(s);
		}
		return states;
	}
	
	public void connectCells() {
		neighbor_adder.addNeighborsToGraph(this);
	}

	public void changeGridSettings(String grid_type, boolean toroidal) {
		this.grid_type = grid_type;
		this.toroidal = toroidal;
		neighbor_adder.addNeighborsToGraph(this);
	}
	
	public void updateCells(Rule r) {
		r.updateCells(all_cells);
	}
}
