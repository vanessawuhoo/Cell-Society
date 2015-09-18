package data;

import java.util.Map;

import cells.CellGraph;
import simulation_type.Rule;

public class AllData {
	public Rule rule;
	public CellGraph cellGraph;
	public Map<String, Double> parameter;

	public AllData(Rule r, CellGraph cg, Map<String, Double> p){
		rule = r;
		cellGraph = cg;
		parameter = p;
	}
}