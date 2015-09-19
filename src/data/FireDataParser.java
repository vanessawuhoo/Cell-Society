package data;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cells.Cell;
import cells.CellGraph;
import simulation_type.Rule;
import simulation_type.SegregationRule;

public class FireDataParser extends FourNeighborDataParser {

	@Override
	protected void setCellToMap(){
		cellMap = new HashMap<Integer, Map<String, Double>>();
		NodeList cellNodeList = doc.getElementsByTagName("Cell");
		for(int i = 1; i<=cellNodeList.getLength(); i++){
			Node tempNode = cellNodeList.item(i-1);
			String stateValue = this.getNodeValue("State", tempNode.getChildNodes());
			Map<String, Double> parameterMap = new HashMap<String, Double>();
			parameterMap.put("State", Double.parseDouble(stateValue));
			cellMap.put(i, parameterMap);
		}
	}
	
	@Override
	protected void setParameters() {
		parameters = new HashMap<String, Double>();
		Node dimension = getNode("Parameters", root.getChildNodes());
		parameters.put("ProbCatch", Double.parseDouble(getNodeValue("ProbCatch", dimension.getChildNodes())));
	}
	
	@Override
	protected void setRule(){
//		rule = new FireRule(dimensions[0], dimensions[1]);
	}

}
