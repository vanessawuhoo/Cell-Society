package controller;
import main.Hub;
import ui.Display;

public class Controller {
	private Hub hub;
	private Display display;
	
	public Controller(Hub h, Display d) {
		this.display = d;
		this.hub = h;
	}
}
