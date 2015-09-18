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

public class SegregationDataParser extends EightNeighborDataParser {

	@Override
	protected void setCellToMap(){
		cellMap = new HashMap<Integer, Map<String, Double>>();
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
	
	@Override
	protected void setParameters() {
		parameters = new HashMap<String, Double>();
	}
	
	@Override
	protected void setRule(){
		rule = new SegregationRule(dimensions[0], dimensions[1]);
	}
		
}
