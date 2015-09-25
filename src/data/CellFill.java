package data;

import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Node;



public abstract class CellFill extends NodeTraverser {
	protected Document doc;
	protected Node root;
	protected Map<Integer, Map<String, Double>> cellMap;
	protected int[] dimensions;

	
	public CellFill(Document document, Node r, int[] d){
		doc = document;
		root = r;
		dimensions = d;
	}	

	protected abstract void fill();
	
	public abstract Map<Integer, Map<String, Double>> getFilledMap();
}
