package data;

public class DataTester {
	public static void main(String[] args){
		XMLLoader loader = new XMLLoader();
		loader.setFileName("Data/sugarscape.xml");
		loader.load();
		System.out.println(loader.getRuleName());
		loader.parseDataSpecific(loader.getRuleName());
		for(String s: loader.getParser(loader.getRuleName()).getParameter().keySet()){
			System.out.print(s+ " ");
			System.out.println(loader.getParser(loader.getRuleName()).getParameter().get(s));
		}
		
		for(int i: loader.getParser(loader.getRuleName()).getCellMap().keySet()){
			System.out.print(i+" ");
			for(String s: loader.getParser(loader.getRuleName()).getCellMap().get(i).keySet()){
				System.out.println("("+ s + ", " + loader.getParser(loader.getRuleName()).getCellMap().get(i).get(s) + ")");
			}
		}
		System.out.println("color");
		for(double i: loader.getParser(loader.getRuleName()).getColor().keySet()){
			System.out.print(i + " ");
			System.out.println(loader.getParser(loader.getRuleName()).getColor().get(i));
		}
	}
}
