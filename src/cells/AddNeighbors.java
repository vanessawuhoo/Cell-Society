package cells;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddNeighbors {
	
	private ResourceBundle myResources;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/Data";
	private Map<String, NeighborMaker> maker_map;
	
	public AddNeighbors() {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
		maker_map = new HashMap<String, NeighborMaker>();
		maker_map.put(myResources.getString("sq4"), new Square4NeighborMaker());
		maker_map.put(myResources.getString("sq8"), new Square8NeighborMaker());
		maker_map.put(myResources.getString("tri"), new TriangleNeighborMaker());
		maker_map.put(myResources.getString("hex"), new HexagonNeighborMaker());
	}
	
	public void addNeighborsToGraph(CellGraph graph) {
		NeighborMaker nm = maker_map.get(graph.getGrid_type());
		int[] dims = graph.getDimensions();
		int m = dims[0]; int n = dims[1];
		nm.getNeighbors(graph.getCells(), m, n, graph.isToroidal());
	}
}
