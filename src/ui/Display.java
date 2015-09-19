package ui;

import java.util.Queue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Hub;
import main.StepVars;

public class Display extends Application {
	private Hub hub;
	private UserInterface myUserInterface;
	
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
        stage.setTitle(myUserInterface.getTitle());
        Scene myScene = myUserInterface.init(stage, getWidth(), getHeight(), "Data");
        stage.setScene(myScene);
        myUserInterface.setHub(hub);
        stage.show();
	}	
	
	//method to update the states of squares in the grid
	public void update(Queue<Double> states){
		myUserInterface.updateStep(states);
	}
	
	public void updateStep(StepVars step_var) {
		if (step_var.sim_not_running) {
			myUserInterface.updateStep(step_var.states);
		}
	}
	
	public void startTestSim(){
//		hub.loadTestSim();
//		hub.playSimulation();
	}
	

}
