package data;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ManualFill extends CellFill {

	public ManualFill(Document doc, Node root, int[] d) {
		super(doc, root, d);
	}

	@Override
	protected void fill(){
		cellMap = new HashMap<Integer, Map<String, Double>>();
		NodeList cellNodeList = doc.getElementsByTagName("Cell");
		if(cellNodeList.getLength() < (dimensions[0]*dimensions[1])){
			System.out.println("NEED TO ADD MORE CELLS");
		} else{
			for(int i = 1; i<=(dimensions[0]*dimensions[1]); i++){	
				Node tempNode = cellNodeList.item(i-1);
				Map<String, Double> parameterMap = new HashMap<String, Double>();
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
					NodeList parameterList = tempNode.getChildNodes();
					for(int j = 0; j<parameterList.getLength(); j++){
						Node tempParameter = parameterList.item(j);
						if (tempParameter.getNodeType() == Node.ELEMENT_NODE) {
							String stateValue = this.getNodeValue(tempParameter.getNodeName(), tempNode.getChildNodes());
							parameterMap.put(tempParameter.getNodeName(), Double.parseDouble(stateValue));
						}

					}
				cellMap.put(i, parameterMap);
			    }
				
			}
		}
		
	}

	@Override
	public Map<Integer, Map<String, Double>> getFilledMap() {
		this.fill();
		return cellMap;
	}
}
