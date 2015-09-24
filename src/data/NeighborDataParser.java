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
	protected Rule rule;
	protected Map<Integer, Map<String, Double>> cellMap;
	protected Map<Integer, Cell> cellMapGraph;
	protected CellGraph cellGraph;
	protected Map<String, Double> parameters;
	protected Map<Double, String> colorMap;
	protected Node root;
	protected Document doc;
	protected int dimensions[];
	protected AllData allData;
	protected String probFill;
	protected Map<String, CellFill> fillMap;

	@Override
	public void parseData(Node head, Document document) {
		this.reset();
		root = head;
		doc = document;
		this.makeFillMap();
		this.setCellToMap();
		this.setDimensions();
		this.setParameters();
		this.setRule();		
		this.setCelltoGraph();
		this.setColor();
		this.setAllData();
	}
	
	@Override
	protected void makeFillMap(){
		fillMap = new HashMap<String, CellFill>();
		fillMap.put("ProbFill", new ProbFill(doc, root));
		fillMap.put("ManualFill", new ManualFill(doc, root));
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
	protected void setDimensions(){
		dimensions = new int[2];
		Node dimension = getNode("Dimension", root.getChildNodes());
		dimensions[0] = Integer.parseInt(getNodeValue("X", dimension.getChildNodes()));
		dimensions[1] = Integer.parseInt(getNodeValue("y", dimension.getChildNodes()));
	}
		
	@Override
	public int[] getDimensions(){
		return dimensions;
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
	public Rule getRule(){
		return rule;
	}
	
	@Override
	protected void setAllData() {
		allData = new AllData(rule, cellGraph, parameters);		
	}
	
	@Override
	public AllData getAllData() {
		return allData;
	}
	
	@Override
	protected void setCellToMap(){
		String fill = getNodeValue("CellFill", root.getChildNodes());
		cellMap = fillMap.get(fill).getFilledMap();
	}
	
	@Override
	protected void setParameters() {
		parameters = new HashMap<String, Double>();
		Node dimension = getNode("Parameters", root.getChildNodes());
		NodeList parameterList = dimension.getChildNodes();
		for(int i = 0; i<parameterList.getLength(); i++){
			Node tempParameter = parameterList.item(i);
			if (tempParameter.getNodeType() == Node.ELEMENT_NODE) {
				parameters.put(tempParameter.getNodeName(), Double.parseDouble(getNodeValue(tempParameter.getNodeName(), dimension.getChildNodes())));
			}
		}
		

	}
}
