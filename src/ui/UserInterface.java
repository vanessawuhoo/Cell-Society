package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ui.UITester;

public class UserInterface {
	private String TITLE = "Group 1 Cellular Automata Simulator";
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private double screenWidth, screenHeight, maxGridWidth, maxGridHeight;
	private Button start, stop, step, slow, fast, load;
	private Scene myUserInterface;
	private ResourceBundle myResources;
	private int[] myParameters;
	private FlowPane flowpane;
	private Group root;
	private double blockLength;
	private UITester ui;

	//give the display the title
	public String getTitle() {
		return TITLE;
	}
	
	//initialize all UI elements
	public Scene init(Stage stage, double width, double height, int[] gridParameters, String resource) {
		root = new Group();
//		myParameters = gridParameters;
		ui = new UITester();
		screenWidth = width;
		screenHeight = height;
		maxGridWidth = (screenWidth*2/3);
		maxGridHeight = (screenHeight*2/3);
		myParameters = gridParameters;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + resource);
		myUserInterface = new Scene(root, width, height, Color.WHITE);
		buttonInit(load, myResources.getString("LoadButton"), width/7, height/20);
		buttonInit(start, myResources.getString("StartButton"), width*2/7, height/20);
		buttonInit(stop, myResources.getString("StopButton"), width*3/7, height/20);
		buttonInit(step, myResources.getString("StepButton"), width*4/7, height/20);
		buttonInit(slow, myResources.getString("SlowButton"), width*5/7, height/20);
		buttonInit(fast, myResources.getString("FastButton"), width*6/7, height/20);
		
		//test case
		Map<String, Double> states = ui.getState();
		return myUserInterface;
	}

	
//	public FlowPane initGrid(Map<String, Double>states, double width, double height){
//		FlowPane flowpane = new FlowPane();
//		flowpane.setPrefWrapLength(blockLength* ((double)myParameters[0]+1));
//		System.out.println(blockLength * myParameters[0]);
//		flowpane.setLayoutX((width-(width/3*2))/2);
//		flowpane.setLayoutY(height/8);
//		//test
//		for (Map.Entry<String, Double>entry : states.entrySet()) {
//			double state = entry.getValue();
//			Rectangle rectangle = new Rectangle(blockLength, blockLength);
//			if (state == 2.0) {
//				rectangle.setFill(Color.GREEN);
//			} else {
//				rectangle.setFill(Color.PURPLE);
//			}
//			flowpane.getChildren().add(rectangle);
//		}
//		return flowpane;
//	}
	
	//helper method to clean up initializing and placing buttons
	public Button buttonInit(Button myButton, String text, double x, double y){
		myButton = new Button(text);
		myButton.setLayoutX(x);
		myButton.setLayoutY(y);
		root.getChildren().add(myButton);
		return myButton;
		
	}

	

	public void replaceGrid(Map<String, Double> states, double width, double height) {
		flowpane.getChildren().clear();
		for (int i = 0; i < states.size(); i++) {
			Rectangle rectangle = new Rectangle(blockLength, blockLength);
			flowpane.getChildren().add(rectangle);
		}
	}
}
