package data;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException; 
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;


import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cells.Cell;
import simulation_type.Rule;
import simulation_type.SegregationRule;


public class XMLLoader extends nodeTraverser{
	private String simulationType;

	private Map<String, String> parameters;
	private Map<Integer, Map<String, String>> cellMap;
	
	private String fileName;
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private NodeList rootList;
	private Node root;
	private Map<String, DataParser> ruleMap = new HashMap<String, DataParser>();
	private int dimensions[];
	private Rule rule;
	
	public XMLLoader(){
		dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addRulesToMap();
	}
	
	private void addRulesToMap(){
		ruleMap.put("Segregation", new SegregationDataParser());
	}
	
	public Object[] getData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public void setFileName(String name){
		fileName = name;
		System.out.println(fileName);
	}
	
	public void load(){
		try {
			doc = db.parse(new File(fileName));
			System.out.println("File Loaded");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();
		this.getRoot();
	};
	
	private void getRoot(){
		rootList = doc.getChildNodes();
		root = getNode("Data", rootList);
	}
	
	public String getRule(){
		simulationType = getNodeValue("Simulation", root.getChildNodes());
		return simulationType;
	}
	
	public void printDimensions(){
		for(int i = 0; i<dimensions.length; i++){
			System.out.println(dimensions[i]);
		}
	}

	public void parseDataSpecific(String index){
		ruleMap.get(index).parseData(root, doc);
		cellMap = ruleMap.get(index).getCellMap();
		dimensions = ruleMap.get(index).getDimensions();
		rule = ruleMap.get(index).getRule();
	}
	
	public Map<Integer, Map<String, String>> getCellMap(){
		return cellMap;
	}

}
