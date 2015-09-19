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

public abstract class EightNeighborDataParser extends NeighborDataParser {
	
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
				// handle upright
				int upright_index = curr_index - n + 1;
				if (upright_index > 0 && upright_index % m != 1) {
					neighbors.add(cellMapGraph.get(upright_index));
				}
				// handle right
				int right_index = curr_index + 1;
				if (right_index % n != 1) {
					neighbors.add(cellMapGraph.get(right_index));
				}
				// handle rightdown
				int rightdown_index = curr_index + n + 1; 
				if (rightdown_index <= n*m && rightdown_index % n != 1) {
					neighbors.add(cellMapGraph.get(rightdown_index));
				}
				// handle down
				int down_index = curr_index + n;
				if (down_index <= n*m) {
					neighbors.add(cellMapGraph.get(down_index));
				}
				// handle downleft
				int downleft_index = curr_index + n - 1;
				if (downleft_index <= n*m && downleft_index % n != 0) {
					neighbors.add(cellMapGraph.get(downleft_index));
				}
				//handle left
				int left_index = curr_index - 1;
				if (left_index % n != 0) {
					neighbors.add(cellMapGraph.get(left_index));
				}
				// handle upleft
				int upleft_index = curr_index - n - 1;
				if (upleft_index > 0 && upleft_index % n != 0) {
					neighbors.add(cellMapGraph.get(upleft_index));
				}
				c.setConnections(neighbors);
			}
		}
	}
}
