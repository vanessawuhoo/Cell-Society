package data;

public class DataTester {
	public static void main(String[] args){
		XMLLoader loader = new XMLLoader();
		loader.setFileName("Data/Segregation.xml");
		loader.load();
		System.out.println(loader.getRule());
		loader.parseDataSpecific(loader.getRule());
		loader.printDimensions();
		for(int i: loader.getCellMap().keySet()){
			System.out.println(i);
			for(String s: loader.getCellMap().get(i).keySet()){
				System.out.println("("+ s + ", " + loader.getCellMap().get(i).get(s) + ")");
			}
		}
	}
}
