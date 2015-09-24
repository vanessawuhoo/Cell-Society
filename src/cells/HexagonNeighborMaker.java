package cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HexagonNeighborMaker implements NeighborMaker {

	@Override
	public void getNeighbors(Map<Integer, Cell> cells, int m, int n, boolean toroidal) {
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				int curr_index = n*(i-1) + j;
				List<Cell> neighbors = new ArrayList<Cell>();
				Cell c = cells.get(curr_index);
				//handle left
				if (j != 1) {
					int left_index = curr_index - 1;
					neighbors.add(cells.get(left_index));
				} else if (toroidal) {
					int left_index = curr_index + n - 1;
					neighbors.add(cells.get(left_index));
				}
				// handle right
				if (j != n) {
					int right_index = curr_index + 1;
					neighbors.add(cells.get(right_index));
				} else if (toroidal) {
					int right_index = curr_index - n + 1;
					neighbors.add(cells.get(right_index));
				}
				
				if (i % 2 == 1) { // odd row
					// handle upleft
					if (i != 1 && j != 1) {
						int upleft_index = curr_index - n - 1;
						neighbors.add(cells.get(upleft_index));
					} else if (i != 1 && toroidal) { // j == 1
						int upleft_index = curr_index - 1;
						neighbors.add(cells.get(upleft_index));
					}
					// handle upright
					if (i != 1) {
						int upright_index = curr_index - n;
						neighbors.add(cells.get(upright_index));
					}
					// handle downleft
					if (i != m && j != 1) {
						int downleft_index = curr_index + n - 1;
						neighbors.add(cells.get(downleft_index));
					} else if (i != m && toroidal) { // j == 1
						int downleft_index = curr_index + 2*n - 1;
						neighbors.add(cells.get(downleft_index));
					}
					// handle downright
					if (i != m) {
						int downright_index = curr_index + 1;
						neighbors.add(cells.get(downright_index));
					}
				} else { // even row
					// handle upleft
					int upleft_index = curr_index - n;
					neighbors.add(cells.get(upleft_index));
					// handle upright
					if (j != n) {
						int upright_index = curr_index - n + 1;
						neighbors.add(cells.get(upright_index));
					} else if (toroidal) { // j == n
						int upright_index = curr_index - 2*n + 1;
						neighbors.add(cells.get(upright_index));
					}
					// handle downleft
					if (i != m) {
						int downleft_index = curr_index + n;
						neighbors.add(cells.get(downleft_index));
					}
					// handle downright
					if (i != m && j != n) {
						int downright_index = curr_index + n + 1;
						neighbors.add(cells.get(downright_index));
					} else if (i != m && toroidal) { // j == n
						int downright_index = curr_index + 1;
						neighbors.add(cells.get(downright_index));
					}
				}
				c.setConnections(neighbors);
			}
		}	
	}

}
