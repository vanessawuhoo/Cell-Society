package cells;

import java.util.ArrayList;
import java.util.List;

public class Cell {

	// the state list may also hold cell type for the information
	private int id;
	private List<Double> state;
	private List<Cell> connections;
	
	public int getId() {
		return id;
	}
	public List<Double> getState() {
		return state;
	}

	public void setState(List<Double> state) {
		this.state = state;
	}

	public List<Cell> getConnections() {
		return connections;
	}

	public void setConnections(List<Cell> connections) {
		this.connections = connections;
	}
	
	public Cell(int id, List<Double> s) {
		this.id = id;
		state = s;
		connections = new ArrayList<Cell>();
	}
}
