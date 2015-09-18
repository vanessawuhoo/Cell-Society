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
		
		//set neighbors
		for (int i = 1; i <= dimensions[0]; i++) {
			for (int j = 1; j <= dimensions[1]; j++) {
				int curr_index = dimensions[1]*(i-1) + j;
				List<Cell> neighbors = new ArrayList<Cell>();
				Cell c = cellMapGraph.get(curr_index);
				// handle up
				int up_index = curr_index - dimensions[1];
				if (up_index > 0) {
					neighbors.add(cellMapGraph.get(up_index));
				}
				// handle right
				int right_index = curr_index + 1;
				if (right_index % dimensions[1] != 1) {
					neighbors.add(cellMapGraph.get(right_index));
				}
				// handle down
				int down_index = curr_index + dimensions[1];
				if (down_index <= dimensions[0]*dimensions[1]) {
					neighbors.add(cellMapGraph.get(down_index));
				}
				//handle left
				int left_index = curr_index - 1;
				if (left_index % dimensions[1] != 0) {
					neighbors.add(cellMapGraph.get(left_index));
				}
				c.setConnections(neighbors);
			}
		}
	}
}
