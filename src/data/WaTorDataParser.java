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

public class WaTorDataParser extends FourNeighborDataParser {

	@Override
	protected void setCellToMap(){
		cellMap = new HashMap<Integer, Map<String, Double>>();
		NodeList cellNodeList = doc.getElementsByTagName("Cell");
		for(int i = 0; i<cellNodeList.getLength(); i++){
			Node tempNode = cellNodeList.item(i);
			int id = Integer.parseInt(this.getNodeValue("ID", tempNode.getChildNodes()));
			String stateValue = this.getNodeValue("State", tempNode.getChildNodes());
			Map<String, Double> parameterMap = new HashMap<String, Double>();
			parameterMap.put("State", Double.parseDouble(stateValue));
			cellMap.put(id, parameterMap);
		}
	}
	
	@Override
	protected void setCelltoGraph(){
		cellMapGraph = new HashMap<Integer, Cell>();
		for(int i: cellMap.keySet()){
			Cell tempCell = new Cell(i, cellMap.get(i));
			cellMapGraph.put(i, tempCell);
		}
		cellGraph = new CellGraph(cellMapGraph);
		
		//set tourus neighbors
		for (int i = 1; i <= dimensions[0]; i++) {
			for (int j = 1; j <= dimensions[1]; j++) {
				int curr_index = dimensions[1]*(i-1) + j;
				List<Cell> neighbors = new ArrayList<Cell>();
				Cell c = cellMapGraph.get(curr_index);
				// handle up
				int up_index = curr_index - dimensions[1];
				if (up_index > 0) {
					
				} else{
					up_index = curr_index + (dimensions[0]*(dimensions[1]-1));
				}
				neighbors.add(cellMapGraph.get(up_index));

				// handle right
				int right_index = curr_index + 1;
				if (right_index % dimensions[1] != 1) {
					
				} else{
					right_index = curr_index + 1 - dimensions[0];
				}
				neighbors.add(cellMapGraph.get(right_index));
				// handle down
				int down_index = curr_index + dimensions[1];
				if (down_index <= dimensions[0]*dimensions[1]) {
					
				} else{
					down_index = curr_index - (dimensions[0]*(dimensions[1]-1));
				}
				neighbors.add(cellMapGraph.get(down_index));

				//handle left
				int left_index = curr_index - 1;
				if (left_index % dimensions[1] != 0) {
					
				} else{
					left_index = curr_index - 1 + dimensions[0];
				}
				neighbors.add(cellMapGraph.get(left_index));
				c.setConnections(neighbors);
			}
		}
	}
	
	@Override
	protected void setParameters() {
		parameters = new HashMap<String, Double>();
		Node dimension = getNode("Parameters", root.getChildNodes());
		parameters.put("FishStep", Double.parseDouble(getNodeValue("FishStep", dimension.getChildNodes())));
		parameters.put("SharkStep", Double.parseDouble(getNodeValue("SharkStep", dimension.getChildNodes())));
		parameters.put("SharkMax", Double.parseDouble(getNodeValue("SharkMax", dimension.getChildNodes())));
		parameters.put("SharkEatHealth", Double.parseDouble(getNodeValue("SharkEatHealth", dimension.getChildNodes())));
		parameters.put("SharkEnergyLoss", Double.parseDouble(getNodeValue("SharkEnergyLoss", dimension.getChildNodes())));

	}

}
