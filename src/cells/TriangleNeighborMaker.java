package cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TriangleNeighborMaker implements NeighborMaker {

	@Override
	public void getNeighbors(Map<Integer, Cell> cells, int m, int n, boolean toroidal) {
		
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				int curr_index = (i-1)*n + j;
				Cell c = cells.get(curr_index);
				List<Cell> neighbors = new ArrayList<Cell>();
				// left
				if (j != 1) {
					Cell left_c = cells.get(curr_index - 1);
					neighbors.add(left_c);
				} else if (toroidal && m % 2 == 0) {
					int left_index = curr_index - 1;
					neighbors.add(cells.get(left_index));
				}
				// right
				if (j != n) {
					Cell right_c = cells.get(curr_index + 1);
					neighbors.add(right_c);
				} else if (toroidal && m % 2 == 0) {
					int right_index = curr_index - n + 1;
					neighbors.add(cells.get(right_index));
				}
				if (i % 2 == 1) { // odd row
					if (j % 2 == 1) {
						if (i != 1) {
							Cell up_c = cells.get(curr_index - n);
							neighbors.add(up_c);
						}
					} else {
						if (i != m) {
							Cell down_c = cells.get(curr_index + n);
							neighbors.add(down_c);
						}
					}
				} else { // even row
					if (j % 2 == 1) {
						if (i != m) {
							Cell down_c = cells.get(curr_index + n);
							neighbors.add(down_c);
						}
					} else {
						// i is never 1
						Cell up_c = cells.get(curr_index - n);
						neighbors.add(up_c);
					}
				}
				c.setConnections(neighbors);
			}
		}
	}
	
}
