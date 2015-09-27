package data;

import java.util.HashMap;

import simulation_type.SugarScapeReproductionRule;
import simulation_type.SugarScapeRule;

public class SugarScapeReproductionDataParser extends NeighborDataParser {

	@Override
	protected void setRule() {
		rule = new SugarScapeReproductionRule(parameters.get("GrowBackRate").intValue(), parameters.get("GrowBackInterval").intValue(), parameters.get("FertileLimit").intValue());
	}

	@Override
	public void setDefaultParameter() {
		// TODO Auto-generated method stub
		parameters = new HashMap<String, Double>();
		parameters.put("GrowBackRate", 2.0);
		parameters.put("GrowBackInterval", 2.0);
		parameters.put("FertileLimit", 3.0);
	}

}
