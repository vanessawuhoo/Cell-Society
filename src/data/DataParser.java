package data;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


public abstract class DataParser extends nodeTraverser {

	public abstract void parseData(Node head, Document document);
	
	public abstract Map<Integer, Map<String, String>> getCellMap();
	
}
