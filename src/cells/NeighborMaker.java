package cells;

import java.util.List;
import java.util.Map;

public interface NeighborMaker {

	/* misc_parameters:
	 * 1. # neighbors: four, eight
	 * 2. grid option: toroidal
	 */
	public void getNeighbors(Map<Integer, Cell> cells, int m, int n, List<String> misc_parameters);
	
}
