package cells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNeighbors {
	
	private Map<String, NeighborMaker> maker_map;
	
	public AddNeighbors() {
		maker_map = new HashMap<String, NeighborMaker>();
		
	}

	public void addNeighbors(CellGraph graph, int dim_x, int dim_y, String grid_type, List<String> misc_parameters) {
		Map<Integer, Cell> cells = graph.getCells();
		
		int m = dim_y;
		int n = dim_x;
		//set neighbors
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				int curr_index = n*(i-1) + j;
				List<Cell> neighbors = new ArrayList<Cell>();
				Cell c = cells.get(curr_index);
				// handle up
				int up_index = curr_index - n;
				if (up_index > 0) {
					neighbors.add(cells.get(up_index));
				}
				// handle right
				int right_index = curr_index + 1;
				if (right_index % n != 1) {
					neighbors.add(cells.get(right_index));
				}
				// handle down
				int down_index = curr_index + n;
				if (down_index <= m*n) {
					neighbors.add(cells.get(down_index));
				}
				//handle left
				int left_index = curr_index - 1;
				if (left_index % n != 0) {
					neighbors.add(cells.get(left_index));
				}
				c.setConnections(neighbors);
			}
		}
	}

}
