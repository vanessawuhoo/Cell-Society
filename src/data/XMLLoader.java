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
	DocumentBuilder db;
	private List<DataParser> parserList;
	
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
	}
	/*
	 * load xml 
	 */
	public void load(){
		try {
			Document doc = db.parse(new File(fileName));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	/*
	 * grab data
	 */
	public void parse(){
		
	}

}
