package data;

import java.util.Map;

import cells.CellGraph;
import simulation_type.Rule;

public class AllData {
	private Rule rule;
	private CellGraph cellGraph;
	private Map<String, Double> parameter;
	private Map<Double, String> nameMap;

	public Rule getRule() {
		return rule;
	}

	public CellGraph getCellGraph() {
		return cellGraph;
	}

	public Map<String, Double> getParameter() {
		return parameter;
	}
	
	public Map<Double, String> getNameMap() {
		return nameMap;
	}

	public AllData(Rule r, CellGraph cg, Map<String, Double> p, Map<Double, String> n){
		rule = r;
		cellGraph = cg;
		parameter = p;
		nameMap = n;
	}
}