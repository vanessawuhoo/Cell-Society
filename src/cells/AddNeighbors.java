package cells;

import java.util.HashMap;
import java.util.Map;

public class AddNeighbors {
	
	private Map<String, NeighborMaker> maker_map;
	
	public AddNeighbors() {
		maker_map = new HashMap<String, NeighborMaker>();
		maker_map.put("square_4", new Square4NeighborMaker());
		maker_map.put("square_8", new Square8NeighborMaker());
		maker_map.put("triangle", new TriangleNeighborMaker());
		maker_map.put("hexagon", new HexagonNeighborMaker());
	}
	
	public void addNeighborsToGraph(CellGraph graph, String grid_type, boolean toroidal) {
		NeighborMaker nm = maker_map.get(grid_type);
		int[] dims = graph.getDimensions();
		int m = dims[0]; int n = dims[1];
		nm.getNeighbors(graph.getCells(), m, n, toroidal);
	}
}
