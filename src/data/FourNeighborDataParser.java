package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cells.Cell;
import cells.CellGraph;
import simulation_type.Rule;
import simulation_type.SegregationRule;

public abstract class FourNeighborDataParser extends NeighborDataParser {

	@Override
	protected void setCelltoGraph(){
		cellMapGraph = new HashMap<Integer, Cell>();
		for(int i: cellMap.keySet()){
			Cell tempCell = new Cell(i, cellMap.get(i));
			cellMapGraph.put(i, tempCell);
		}
		cellGraph = new CellGraph(cellMapGraph);
		
		int m = dimensions[1];
		int n = dimensions[0];
		//set neighbors
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				int curr_index = n*(i-1) + j;
				List<Cell> neighbors = new ArrayList<Cell>();
				Cell c = cellMapGraph.get(curr_index);
				// handle up
				int up_index = curr_index - n;
				if (up_index > 0) {
					neighbors.add(cellMapGraph.get(up_index));
				}
				// handle right
				int right_index = curr_index + 1;
				if (right_index % n != 1) {
					neighbors.add(cellMapGraph.get(right_index));
				}
				// handle down
				int down_index = curr_index + n;
				if (down_index <= m*n) {
					neighbors.add(cellMapGraph.get(down_index));
				}
				//handle left
				int left_index = curr_index - 1;
				if (left_index % n != 0) {
					neighbors.add(cellMapGraph.get(left_index));
				}
				c.setConnections(neighbors);
			}
		}
	}
}
