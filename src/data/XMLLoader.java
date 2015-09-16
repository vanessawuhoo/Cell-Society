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
	
	public XMLLoader(){
		dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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
		rootList = doc.getElementsByTagName("Data");
	}
	
	public void getRule(){
		Node tempNode = rootList.item(0);
		if(tempNode.getNodeType() == Node.ELEMENT_NODE){
			Element eElement = (Element) tempNode;
			simulationType = eElement.getElementsByTagName("Simulation").item(0).getTextContent();
			System.out.println(simulationType);
		}
	}

	public Object[] getData() {
		// TODO Auto-generated method stub
		return null;
	}


}
