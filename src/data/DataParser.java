package data;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import cells.Cell;
import cells.CellGraph;
import simulation_type.Rule;
import simulation_type.SegregationRule;


public abstract class DataParser extends NodeTraverser {

	public abstract void parseData(Node head, Document document);
	
	protected abstract void setCellToMap();
	
	protected abstract void setColor();
	
	protected abstract void setCelltoGraph();
	
	protected abstract void setParameters();
	
	protected abstract void setAllData();
	
	protected abstract void setDimensions();
	
	protected abstract void setRule();
	
	protected abstract void setShape();
	
	protected abstract String getShape();
	
	protected abstract void setToroidal();
	
	protected abstract boolean getToroidal();
	
	protected abstract void makeFillMap();
	
	public abstract Map<Integer, Map<String, Double>> getCellMap();

	public abstract int[] getDimensions();

	public abstract Rule getRule();
	
	public abstract Map<String, Double> getParameter();
	
	public abstract Map<Double, String> getColor();
		
	public abstract AllData getAllData();
	
	public abstract void reset();
	
	
	
}
