package data;

public class DataTester {
	public static void main(String[] args){
		XMLLoader loader = new XMLLoader();
		loader.setFileName("Data/Segregation.xml");
		loader.load();
		loader.getRule();
		loader.getDimensions();
	}
}
