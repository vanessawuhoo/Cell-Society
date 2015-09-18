package data;

import cells.CellGraph;
import simulation_type.Rule;

public class AllData {
	public Rule rule;
	public CellGraph cellGraph;

	public AllData(Rule r, CellGraph cg){
		rule = r;
		cellGraph = cg;
	}
}