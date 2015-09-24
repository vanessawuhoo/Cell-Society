package data;

import java.util.Map;
import org.w3c.dom.Document;


public abstract class CellFill extends NodeTraverser {
	protected Document doc;
	protected Map<Integer, Map<String, Double>> cellMap;

	
	public CellFill(Document document){
		doc = document;
	}	

	protected abstract void fill();
	
	public abstract Map<Integer, Map<String, Double>> getFilledMap();
}
