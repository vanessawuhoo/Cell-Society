package data;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cells.Cell;
import cells.CellGraph;
import simulation_type.GameOfLifeRule;
import simulation_type.Rule;
import simulation_type.SegregationRule;

public class GameOfLifeDataParser extends NeighborDataParser {
	

	@Override
	protected void setRule(){
		rule = new GameOfLifeRule();
	}

	@Override
	public void setDefaultParameter() {
		parameters = new HashMap<String, Double>();
		
	}
	
}
