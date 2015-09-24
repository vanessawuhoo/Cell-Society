package data;

import java.util.Map;

import org.w3c.dom.Document;

public class ProbFill extends CellFill {
	protected Map<String, Double> probMap;
	protected Map<String, Double> cumeMap;

	public ProbFill(Document doc) {
		super(doc);
		// TODO Auto-generated constructor stub
	}

	private void setProbMap(){
		
	}
	
	private void makeCumeProbMap(){
		
	}
	
	@Override
	protected void fill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Integer, Map<String, Double>> getFilledMap() {
		this.fill();
		return cellMap;
	}

}
