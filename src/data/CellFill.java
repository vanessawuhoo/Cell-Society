package data;

import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Node;



public abstract class CellFill extends NodeTraverser {
	protected Document doc;
	protected Node root;
	protected Map<Integer, Map<String, Double>> cellMap;

	
	public CellFill(Document document, Node r){
		doc = document;
		root = r;
	}	

	protected abstract void fill();
	
	public abstract Map<Integer, Map<String, Double>> getFilledMap();
}
