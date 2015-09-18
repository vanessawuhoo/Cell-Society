package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Hub;
import ui.UITester;
import simulation_type.Rule;

public class Display extends Application {
	private Hub hub;
	private UserInterface myUserInterface;
	private Rule rule;
	private int[] gridParameters;
	
	public void setHub(Hub h) {
		hub = h;
	}
	
	//get the screen resolution width of the computer
	public double getWidth() {
		return java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()*.6;
	}

	//get the screen resolution height of the computer
	public double getHeight(){
		return java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()*.8;
	}

	//initialize the UI and set the title
	public void start(Stage stage) {
        myUserInterface = new UserInterface();
//        UITester ui = new UITester();
//        gridParameters = ui.getParameters();
        gridParameters = hub.getParameters();
        stage.setTitle(myUserInterface.getTitle());
        Scene myScene = myUserInterface.init(stage, getWidth(), getHeight(), gridParameters, "Data");
        stage.setScene(myScene);
//        Map<String, Double> m = new HashMap<String, Double>();
//        myUserInterface.replaceGrid(m);
        myUserInterface.setHub(hub);
        stage.show();
	}	
	
	//method to update the states of squares in the grid
	public void update(Map<Integer, Map<String, Double>> states){
//		myUserInterface.replaceGrid(states);
	}
	
	public void startTestSim(){
//		hub.loadTestSim();
//		hub.playSimulation();
	}
	

}
