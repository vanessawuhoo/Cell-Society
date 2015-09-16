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


public class XMLLoader {
	private String simulationType;
	private Map<String, String> parameters;
	private Map<Integer, Map<String, String>> cells;
	private String fileName;
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private List<DataParser> parserList;
	private Document doc;
	private NodeList rootList;
	private Node root;
	private int dimensions[] = new int[2];
	
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
	
	public void getRule(){
		simulationType = getNodeValue("Simulation", root.getChildNodes());
		System.out.println(simulationType);
	}
	
	public void getDimensions(){
		Node dimension = getNode("Dimension", root.getChildNodes());
		dimensions[0] = Integer.parseInt(getNodeValue("X", dimension.getChildNodes()));
		dimensions[1] = Integer.parseInt(getNodeValue("y", dimension.getChildNodes()));
		System.out.println("("+dimensions[0]+", "+dimensions[1]+")");
	}

	protected Node getNode(String tagName, NodeList nodes) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            return node;
	        }
	    }
	 
	    return null;
	}
	 
	protected String getNodeValue(String tagName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.TEXT_NODE )
	                    return data.getNodeValue();
	            }
	        }
	    }
	    return "";
	}

}
