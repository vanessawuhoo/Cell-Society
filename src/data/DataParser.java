package data;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import simulation_type.Rule;


public abstract class DataParser extends nodeTraverser {

	public abstract void parseData(Node head, Document document);
	
	public abstract Map<Integer, Map<String, Double>> getCellMap();

	public abstract int[] getDimensions();

	public abstract Rule getRule();
	
	public abstract Map<String, Double> getParameter();
}
