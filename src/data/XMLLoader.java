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
import java.util.List;
import java.util.Map;

import cells.Cell;


public class XMLLoader extends nodeTraverser{
	private String simulationType;
	private int dimensions[] = new int[2];

	private Map<String, String> parameters;
	private Map<Integer, Map<String, String>> cells;
	
	private String fileName;
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private NodeList rootList;
	private Node root;
	private DataParser[] parserList = {
			new SegregationDataParser(),
	};
	
	public XMLLoader(){
		dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public int[] getDimensions(){
		Node dimension = getNode("Dimension", root.getChildNodes());
		dimensions[0] = Integer.parseInt(getNodeValue("X", dimension.getChildNodes()));
		dimensions[1] = Integer.parseInt(getNodeValue("y", dimension.getChildNodes()));
		return dimensions;
	}

	public void parseDataSpecific(int index){
		parserList[index].parseData(root, doc);
		cells = parserList[index].getCellMap();
	}
	
	public Map<Integer, Map<String, String>> getCellMap(){
		return cells;
	}

}
