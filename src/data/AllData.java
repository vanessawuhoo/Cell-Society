package data;

import java.util.Map;

import cells.CellGraph;
import simulation_type.Rule;

public class AllData {
	private Rule rule;
	private CellGraph cellGraph;
	private Map<String, Double> parameter;

	public Rule getRule() {
		return rule;
	}

	public CellGraph getCellGraph() {
		return cellGraph;
	}

	public Map<String, Double> getParameter() {
		return parameter;
	}

	public AllData(Rule r, CellGraph cg, Map<String, Double> p){
		rule = r;
		cellGraph = cg;
		parameter = p;
	}
}