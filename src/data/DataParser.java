package data;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import simulation_type.Rule;


public abstract class DataParser extends nodeTraverser {

	public abstract void parseData(Node head, Document document);
	
	protected abstract void setCellToMap();
	
	protected abstract void setColor();
	
	protected abstract void setCelltoGraph();
	
	protected abstract void setParameters();
	
	protected abstract void setAllData();
	
	protected abstract void setDimensions();
	
	protected abstract void setRule();
	
	public abstract Map<Integer, Map<String, Double>> getCellMap();

	public abstract int[] getDimensions();

	public abstract Rule getRule();
	
	public abstract Map<String, Double> getParameter();
	
	public abstract Map<Double, String> getColor();
		
	public abstract AllData getAllData();
	
	public abstract void reset();
	
}
