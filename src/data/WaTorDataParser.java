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
import simulation_type.WaTorRule;

public class WaTorDataParser extends NeighborDataParser {
	
	@Override
	protected void setRule(){
		rule = new WaTorRule(
				parameters.get("FishStep").intValue(), (int) parameters.get("SharkStep").intValue(),
				parameters.get("SharkMax").intValue(), parameters.get("SharkEatHealth"),
				parameters.get("SharkEnergyLoss"));
	}

	@Override
	public void setDefaultParameter() {
		parameters = new HashMap<String, Double>();
		parameters.put("FishStep", 4.0);
		parameters.put("SharkStep", 9.0);
		parameters.put("SharkMax", 9.0);
		parameters.put("SharkEatHealth", 1.0);
		parameters.put("SharkEnergyLoss", 2.0);
	}

}
