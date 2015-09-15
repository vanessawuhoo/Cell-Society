package ui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UserInterface {
	private String TITLE = "Group 1 Cellular Automata Simulator";
	private Button start, stop, step, slow, fast, load;
	private Scene myUserInterface; 
	private Group root;
	
	//give the display the title
	public String getTitle() {
		return TITLE;
	}
	
	//initialize all UI elements
	public Scene init(Stage stage, double width, double height) {
		root = new Group();
		myUserInterface = new Scene(root, width, height, Color.WHITE);
		buttonInit(load, "Load", width/7, height/15);
		buttonInit(start, "Start", width*2/7, height/15);
		buttonInit(stop, "Stop", width*3/7, height/15);
		buttonInit(step, "Step", width*4/7, height/15);
		buttonInit(slow, "Slower", width*5/7, height/15);
		buttonInit(fast, "Faster", width*6/7, height/15);
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
	
	
}
