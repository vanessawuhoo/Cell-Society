package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cells.Cell;
import cells.CellGraph;
import simulation_type.Rule;
import simulation_type.SegregationRule;

public abstract class NeighborDataParser extends DataParser{
	protected Map<Integer, Map<String, Double>> cellMap;
	protected Map<Integer, Cell> cellMapGraph;
	protected CellGraph cellGraph;
	protected Map<String, Double> parameters;
	protected Map<Double, String> colorMap;
	protected Node root;
	protected Document doc;
	protected SegregationRule sr;
	protected int dimensions[];
	protected AllData allData;

	@Override
	public void parseData(Node head, Document document) {
		this.reset();
		root = head;
		doc = document;
		this.setCellToMap();
		this.setDimensions();
		this.setRule();		
		this.setCelltoGraph();
		this.setColor();
		this.setAllData();
	}
		
	@Override
	protected void setColor(){
		colorMap = new HashMap<Double, String>();
		NodeList colorNodeList = doc.getElementsByTagName("Color");
		for(int i = 0; i<colorNodeList.getLength(); i++){
			Node tempNode = colorNodeList.item(i);
			String color = this.getNodeValue(tempNode);
			double state = Double.parseDouble(this.getNodeAttr("state", tempNode));
			colorMap.put(state, color);
		}
	}
	
	@Override
	protected void setAllData() {
		allData = new AllData(sr, cellGraph);		
	}
	
	@Override
	protected void setDimensions(){
		dimensions = new int[2];
		Node dimension = getNode("Dimension", root.getChildNodes());
		dimensions[0] = Integer.parseInt(getNodeValue("X", dimension.getChildNodes()));
		dimensions[1] = Integer.parseInt(getNodeValue("y", dimension.getChildNodes()));
	}
	
	@Override
	protected void setRule(){
		sr = new SegregationRule(dimensions[0], dimensions[1]);
	}
	
	@Override
	public int[] getDimensions(){
		return dimensions;
	}
	
	@Override
	public Rule getRule(){
		return sr;
	}
	
	@Override
	public Map<Integer, Map<String, Double>> getCellMap(){
		return cellMap;
	}

	@Override
	public Map<String, Double> getParameter() {
		return parameters;
	}

	@Override
	public Map<Double, String> getColor() {
		return colorMap;
	}

	@Override
	public void reset() {
		dimensions = null;
		parameters = null;
		colorMap = null;
		cellMap = null;
		cellGraph = null;
		cellMapGraph = null;
		
	}


	@Override
	public AllData getAllData() {
		return allData;
	}
}
