package cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Square4NeighborMaker implements NeighborMaker {
	
	@Override
	public void getNeighbors(Map<Integer, Cell> cells, int m, int n, boolean toroidal) {
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				int curr_index = n*(i-1) + j;
				Cell c = cells.get(curr_index);
				List<Cell> neighbors = new ArrayList<Cell>();
				// handle up
				if (i != 1) {
					int up_index = curr_index - n;
					neighbors.add(cells.get(up_index));
				}
				// handle right
				if (j != n) {
					int right_index = curr_index + 1;
					neighbors.add(cells.get(right_index));
				} else {
					if (toroidal) {
						int right_index = curr_index - n + 1;
						neighbors.add(cells.get(right_index));
					}
				}
				// handle down
				if (i != m) {
					int down_index = curr_index + n;
					neighbors.add(cells.get(down_index));
				}
				//handle left
				if (j != 1) {
					int left_index = curr_index - 1;
					neighbors.add(cells.get(left_index));
				} else {
					if (toroidal) {
						int left_index = curr_index + n - 1;
						neighbors.add(cells.get(left_index));
					}
				}
				c.setConnections(neighbors);
			}
		}
	}
}
