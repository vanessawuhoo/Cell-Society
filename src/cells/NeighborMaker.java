package cells;

import java.util.List;
import java.util.Map;

public interface NeighborMaker {

	public void getNeighbors(Map<Integer, Cell> cells, int m, int n, boolean toroidal);
	
}
