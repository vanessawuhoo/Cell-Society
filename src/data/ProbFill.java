package data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProbFill extends CellFill {
	protected Map<String, Map<Double, Double>> probMap;
	protected Map<String, Map<Double, Double>> cumeMap;

	public ProbFill(Document doc, Node root, int[] d) {
		super(doc, root, d);
		// TODO Auto-generated constructor stub
	}

	private void setProbMap(){
		probMap = new HashMap<String, Map<Double, Double>>();
		Node probFill = getNode("ProbFill", root.getChildNodes());
		NodeList probList = probFill.getChildNodes();
		for(int i = 0;i<probList.getLength(); i++){
			Node tempNode = probList.item(i);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String stateKey = tempNode.getNodeName();
				NodeList innerProbList = tempNode.getChildNodes();
				Map<Double, Double> tempMap = new HashMap<Double, Double>();
				double cume = 0;
				for(int j = 0; j<innerProbList.getLength(); j++){
					Node tempInnerNode = innerProbList.item(j);
					if (tempInnerNode.getNodeType() == Node.ELEMENT_NODE) {
						double value = Double.parseDouble(this.getNodeValue(tempInnerNode));
						double key = Double.parseDouble(this.getNodeAttr(stateKey, tempInnerNode));
						cume = cume + value;
						tempMap.put(key, cume);
					}
				}
				probMap.put(stateKey, tempMap);
			}
		}

	}
	
	@Override
	protected void fill() {
		cellMap = new HashMap<Integer, Map<String, Double>>();
		NodeList cellNodeList = doc.getElementsByTagName("Cell");
		for(int i = 1; i<=cellNodeList.getLength(); i++){
			Node tempNode = cellNodeList.item(i-1);
			Map<String, Double> parameterMap = new HashMap<String, Double>();
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				NodeList parameterList = tempNode.getChildNodes();
				for(int j = 0; j<parameterList.getLength(); j++){
					Node tempParameter = parameterList.item(j);
					if (tempParameter.getNodeType() == Node.ELEMENT_NODE) {
						if(probMap.containsKey(tempParameter.getNodeName())){
							Map<Double, Double> tempMap = probMap.get(tempParameter.getNodeName());
							Random generator = new Random();
							double number = generator.nextDouble();
							double lastProb = 0;
							for(double d: tempMap.keySet()){
								if(number >= lastProb && number <= tempMap.get(d)){
									parameterMap.put(tempParameter.getNodeName(), d);
									break;
								} else{
									lastProb = tempMap.get(d);
								}
							}

						} else{
							String stateValue = this.getNodeValue(tempParameter.getNodeName(), tempNode.getChildNodes());
							parameterMap.put(tempParameter.getNodeName(), Double.parseDouble(stateValue));
						}
					}

				}
			cellMap.put(i, parameterMap);
		    }
			
		}
	}

	@Override
	public Map<Integer, Map<String, Double>> getFilledMap() {
		this.setProbMap();
		this.fill();
		return cellMap;
	}

}
