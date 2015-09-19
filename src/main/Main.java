package main;


import data.XMLLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.Display;


public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage s) {
		XMLLoader xml_loader = new XMLLoader();
		Display display = new Display();
		Hub hub = new Hub(xml_loader, display);
		xml_loader.setHub(hub);
		display.setHub(hub);
		display.start(s);
	}
}
