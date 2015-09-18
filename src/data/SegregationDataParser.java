package data;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException; 
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.*;

import cells.Cell;
import cells.CellGraph;
import simulation_type.Rule;
import simulation_type.SegregationRule;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class SegregationDataParser extends DataParser {
	private Map<Integer, Map<String, Double>> cellMap = new HashMap<Integer, Map<String, Double>>();
	private Map<Integer, Cell> cellMapGraph = new HashMap<Integer, Cell>();
	private CellGraph cellGraph;
	private Map<String, Double> parameters;
	private Map<Double, String> colorMap = new HashMap<Double, String>();
	private Node root;
	private Document doc;
	private SegregationRule sr;
	private int dimensions[] = new int[2];

	
	public SegregationDataParser(){
		
	}
	
	@Override
	public void parseData(Node head, Document document) {
		root = head;
		doc = document;
		this.setCellToMap();
		this.setDimensions();
		this.setRule();		
		this.setCelltoGraph();
	}
	
	private void setCellToMap(){
		NodeList cellNodeList = doc.getElementsByTagName("Cell");
		for(int i = 0; i<cellNodeList.getLength(); i++){
			Node tempNode = cellNodeList.item(i);
			int id = Integer.parseInt(this.getNodeValue("ID", tempNode.getChildNodes()));
			String stateValue = this.getNodeValue("State", tempNode.getChildNodes());
			Map<String, Double> parameterMap = new HashMap<String, Double>();
			parameterMap.put("State", Double.parseDouble(stateValue));
			cellMap.put(id, parameterMap);
		}
	}
	
	private void setColor(){
		NodeList colorNodeList = doc.getElementsByTagName("Color");
		for(int i = 0; i<colorNodeList.getLength(); i++){
			Node tempNode = colorNodeList.item(i);
			double state = Double.parseDouble(this.getNodeValue(tempNode));
			String color = this.getNodeAttr(Double.toString(state), tempNode);
			colorMap.put(state, color);
		}
	}
	
	private void setCelltoGraph(){
		for(int i: cellMap.keySet()){
			Cell tempCell = new Cell(i, cellMap.get(i));
			cellMapGraph.put(i, tempCell);
		}
		cellGraph = new CellGraph(cellMapGraph);
		
		//set neighbors
		for (int i = 1; i <= dimensions[0]; i++) {
			for (int j = 1; j <= dimensions[1]; j++) {
				int curr_index = dimensions[1]*(i-1) + j;
				List<Cell> neighbors = new ArrayList<Cell>();
				Cell c = cellMapGraph.get(curr_index);
				// handle up
				int up_index = curr_index - dimensions[1];
				if (up_index > 0) {
					neighbors.add(cellMapGraph.get(up_index));
				}
				// handle upright
				int upright_index = curr_index - dimensions[1] + 1;
				if (upright_index > 0 && upright_index % dimensions[1] != 1) {
					neighbors.add(cellMapGraph.get(upright_index));
				}
				// handle right
				int right_index = curr_index + 1;
				if (right_index % dimensions[1] != 1) {
					neighbors.add(cellMapGraph.get(right_index));
				}
				// handle rightdown
				int rightdown_index = curr_index + dimensions[1] + 1; 
				if (rightdown_index <= dimensions[0]*dimensions[1] && rightdown_index % dimensions[1] != 1) {
					neighbors.add(cellMapGraph.get(rightdown_index));
				}
				// handle down
				int down_index = curr_index + dimensions[1];
				if (down_index <= dimensions[0]*dimensions[1]) {
					neighbors.add(cellMapGraph.get(down_index));
				}
				// handle downleft
				int downleft_index = curr_index + dimensions[1] - 1;
				if (downleft_index <= dimensions[0]*dimensions[1] && downleft_index % dimensions[1] != 0) {
					neighbors.add(cellMapGraph.get(downleft_index));
				}
				//handle left
				int left_index = curr_index - 1;
				if (left_index % dimensions[1] != 0) {
					neighbors.add(cellMapGraph.get(left_index));
				}
				// handle upleft
				int upleft_index = curr_index - dimensions[1] - 1;
				if (upleft_index > 0 && upleft_index % dimensions[1] != 0) {
					neighbors.add(cellMapGraph.get(upleft_index));
				}
				c.setConnections(neighbors);
			}
		}
	}
	
	//Segregation has no parameters
	private void setParameters(){
		NodeList parameterNodeList = doc.getElementsByTagName("Parameters");
	}
	
	public void setDimensions(){
		Node dimension = getNode("Dimension", root.getChildNodes());
		dimensions[0] = Integer.parseInt(getNodeValue("X", dimension.getChildNodes()));
		dimensions[1] = Integer.parseInt(getNodeValue("y", dimension.getChildNodes()));
	}
	
	private void setRule(){
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
	

}
