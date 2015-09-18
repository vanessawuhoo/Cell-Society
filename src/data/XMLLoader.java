package data;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cells.Cell;
import simulation_type.Rule;
import simulation_type.SegregationRule;
import main.Hub;


public class XMLLoader extends nodeTraverser{
	private Hub hub;
	
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
	
	public void setHub(Hub h) {
		hub = h;
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
	
	public String getRuleName(){
		simulationType = getNodeValue("Simulation", root.getChildNodes());
		return simulationType;
	}
	
	public void parseDataSpecific(String index){
		ruleMap.get(index).parseData(root, doc);
	}
	
	public DataParser getParser(String index){
		return ruleMap.get(index);
	}	
}
