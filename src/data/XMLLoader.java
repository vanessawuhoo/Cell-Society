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

import main.Hub;


public class XMLLoader extends nodeTraverser{
	private Hub hub;
	
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
	
	public void setHub(Hub h) {
		hub = h;
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
