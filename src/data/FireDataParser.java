package data;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import simulation_type.FireRule;

public class FireDataParser extends NeighborDataParser {
		
	@Override
	protected void setRule(){
		rule = new FireRule(dimensions[0], dimensions[1], parameters.get("ProbCatch"));
	}

}
