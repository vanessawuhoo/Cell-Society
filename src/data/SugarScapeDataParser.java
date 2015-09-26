package data;

import java.util.HashMap;

import simulation_type.SegregationRule;
import simulation_type.SugarScapeRule;

public class SugarScapeDataParser extends NeighborDataParser {

	@Override
	protected void setRule() {
		rule = new SugarScapeRule(parameters.get("GrowBackRate").intValue(), parameters.get("GrowBackInterval").intValue());
		
	}

	@Override
	public void setDefaultParameter() {
		parameters = new HashMap<String, Double>();
		parameters.put("GrowBackRate", 2.0);
		parameters.put("GrowBackInterval", 2.0);
	}

}
