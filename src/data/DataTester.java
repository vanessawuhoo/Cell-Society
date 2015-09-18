package data;

public class DataTester {
	public static void main(String[] args){
		XMLLoader loader = new XMLLoader();
		loader.setFileName("Data/Segregation.xml");
		loader.load();
		System.out.println(loader.getRuleName());
		loader.parseDataSpecific(loader.getRuleName());
		for(int i: loader.getParser(loader.getRuleName()).getCellMap().keySet()){
			System.out.println(i);
			for(String s: loader.getParser(loader.getRuleName()).getCellMap().get(i).keySet()){
				System.out.println("("+ s + ", " + loader.getParser(loader.getRuleName()).getCellMap().get(i).get(s) + ")");
			}
		}
		System.out.println("color");
		for(double i: loader.getParser(loader.getRuleName()).getColor().keySet()){
			System.out.println(i);
			System.out.println(loader.getParser(loader.getRuleName()).getColor().get(i));
		}
		
		loader.setFileName("Data/Segregation2.xml");
		loader.load();
		loader.parseDataSpecific(loader.getRuleName());
		System.out.println(loader.getRuleName());
		for(int i: loader.getParser(loader.getRuleName()).getCellMap().keySet()){
			System.out.println(i);
			for(String s: loader.getParser(loader.getRuleName()).getCellMap().get(i).keySet()){
				System.out.println("("+ s + ", " + loader.getParser(loader.getRuleName()).getCellMap().get(i).get(s) + ")");
			}
		}
		System.out.println("color");
		for(double i: loader.getParser(loader.getRuleName()).getColor().keySet()){
			System.out.println(i);
			System.out.println(loader.getParser(loader.getRuleName()).getColor().get(i));
		}
	}
}
