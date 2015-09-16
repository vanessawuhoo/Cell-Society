package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UserInterface {
	private String TITLE = "Group 1 Cellular Automata Simulator";
	private Button start, stop, step, slow, fast, load;
	private Scene myUserInterface; 
	private Group root;
	//replace with a variable later from XML reader
	private int GRID_DIMENSIONS = 40;
	
	
	//give the display the title
	public String getTitle() {
		return TITLE;
	}
	
	//initialize all UI elements
	public Scene init(Stage stage, double width, double height) {
		root = new Group();
		myUserInterface = new Scene(root, width, height, Color.WHITE);
		buttonInit(load, "Load", width/7, height/20);
		buttonInit(start, "Start", width*2/7, height/20);
		buttonInit(stop, "Stop", width*3/7, height/20);
		buttonInit(step, "Step", width*4/7, height/20);
		buttonInit(slow, "Slower", width*5/7, height/20);
		buttonInit(fast, "Faster", width*6/7, height/20);
		//test case
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
		initGrid(states, width, height);
		return myUserInterface;
	}
	
	//helper method to clean up initializing and placing buttons
	public Button buttonInit(Button myButton, String text, double x, double y){
		myButton = new Button(text);
		myButton.setLayoutX(x);
		myButton.setLayoutY(y);
		root.getChildren().add(myButton);
		return myButton;
		
	}
	
	public void initGrid(Map<Integer, List<Double>>states, double width, double height){
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefWrapLength(width/3*2);
		flowpane.setLayoutX((width-(width/3*2))/2);
		flowpane.setLayoutY(height/8);
	
		for (int i = 0; i < states.size(); i++) {
			Rectangle rectangle = new Rectangle(width/3*2/GRID_DIMENSIONS,width/3*2/GRID_DIMENSIONS);
			List<Double> hold = states.get(i);
			if (hold.get(0) == 2.0){
				rectangle.setFill(Color.PINK);
			} else{
				rectangle.setFill(Color.LIGHTBLUE);
			}
			flowpane.getChildren().add(rectangle);
		}
		root.getChildren().add(flowpane);
	}
}
