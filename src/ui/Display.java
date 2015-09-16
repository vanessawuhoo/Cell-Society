package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Display extends Application {
	
	private UserInterface myUserInterface;
	
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
        stage.setTitle(myUserInterface.getTitle());
        Scene myScene = myUserInterface.init(stage, getWidth(), getHeight());
        stage.setScene(myScene);
        stage.show();
        
        //tester
        Map<Integer, List<Double>> states = new HashMap<Integer,List<Double>>();
		List<Double> list1 = new ArrayList<Double>();
		List<Double> list2 = new ArrayList<Double>();
		list1.add(2.0);
		list2.add(3.0);
		for (int i = 0; i < 300; i++) {
			if (i%2==0) {
				states.put(i, list1);
			} else {
				states.put(i, list2);
			}
			
		}
        
        update(states);
	}	
	

	public void update(Map<Integer, List<Double>> states){
		myUserInterface.replaceGrid(states, getWidth(), getHeight());
	}
	
	public void startTestSim(){
		
	}
	
    public static void main (String[] args) {
        launch(args);
    }


}
