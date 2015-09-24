package cells;

import java.util.List;
import java.util.Map;

public class SquareNeighborMaker implements NeighborMaker {
	
	@Override
	public void getNeighbors(Map<Integer, Cell> cells, int m, int n, List<String> misc_parameters) {
		String num_neighbors = misc_parameters.get(0);
		String grid_option = misc_parameters.get(1);
		if (num_neighbors.equals("four")) {
			
		} else if (num_neighbors.equals("eight")) {
			
		} else {
			
		}
	}
	
	private void fourNeighbor(Map<Integer, Cell> cells, int m, int n) {
		
	}
	
	private void eightNeighbor()
	

}
