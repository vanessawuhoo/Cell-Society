package main;


import data.XMLLoader;
import ui.Display;


public class Main {

	public static void main(String[] args) {
		XMLLoader xml_loader = new XMLLoader();
		Display display = new Display();
		Hub hub = new Hub(xml_loader, display);

	}
}
