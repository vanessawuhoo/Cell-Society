package cells;

import java.util.ArrayList;
import java.util.List;

public class Cell {

	private List<Double> state;
	private List<Cell> connections;
	
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
	
	public Cell(List<Double> s) {
		state = s;
		connections = new ArrayList<Cell>();
	}
}
