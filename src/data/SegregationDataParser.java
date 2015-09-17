package data;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException; 
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.*;

import simulation_type.SegregationRule;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class SegregationDataParser extends DataParser {
	private Map<Integer, Map<String, String>> cells = new HashMap<Integer, Map<String, String>>();
	private Map<String, String> parameters;
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
		this.parseCells();
		this.setDimensions();
		// TODO Auto-generated method stub
		
	}
	
	private void parseCells(){
		NodeList cellNodeList = doc.getElementsByTagName("Cell");
		for(int i = 0; i<cellNodeList.getLength(); i++){
			Node tempNode = cellNodeList.item(i);
			int id = Integer.parseInt(this.getNodeValue("ID", tempNode.getChildNodes()));
			String stateValue = this.getNodeValue("State", tempNode.getChildNodes());
			Map<String, String> parameterMap = new HashMap<String, String>();
			parameterMap.put("State", stateValue);
			cells.put(id, parameterMap);
		}
	}
	
	//Segregation has no parameters
	private void parceParameters(){
		NodeList parameterNodeList = doc.getElementsByTagName("Parameters");
	}
	
	public void setDimensions(){
		Node dimension = getNode("Dimension", root.getChildNodes());
		dimensions[0] = Integer.parseInt(getNodeValue("X", dimension.getChildNodes()));
		dimensions[1] = Integer.parseInt(getNodeValue("y", dimension.getChildNodes()));
	}
	
	public int[] getDimensions(){
		return dimensions;
	}
	
	public Map<Integer, Map<String, String>> getCellMap(){
		return cells;
	}
	

}
