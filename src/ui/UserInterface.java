package ui;

import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UserInterface {
	private String TITLE = "Group 1 Cellular Automata Simulator";
	private int[] myParameters;
	Queue<Double> q;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private double screenWidth, screenHeight,blockLength,offsetX,offsetY;
	private Button start, stop, step, slow, fast, load;
	private Scene myUserInterface;
	private ResourceBundle myResources;
	private Shape[][] myArray;
	private Group root;
	private UITester ui;

	//give the display the title
	public String getTitle() {
		return TITLE;
	}
	
	//initialize all UI elements
	public Scene init(Stage stage, double width, double height, int[] gridParameters, String resource) {
		root = new Group();
		ui = new UITester();
		ui.getQueueState();
		screenWidth = width;
		screenHeight = height;
		myParameters = gridParameters;
		calcSideLength();
		calcOffset();
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
		q = ui.getQueueState();
		initGrid(states);
		return myUserInterface;
	}
	
	private void initGrid(Map<String, Double> states){
		myArray = new Shape[myParameters[0]][myParameters[1]];
		int row = 0;
		int col = 0;
		while (!q.isEmpty()){
			double d = q.remove();
			String color = "";
			if (d%2==0){
				color = "#ff0000";
			} else {
				color = "#008080";
			}
			SquareShape squareShape = new SquareShape(blockLength, color, myParameters);
			myArray[row][col] = squareShape;
			//setlocation- function?
			root.getChildren().add(squareShape.getObject());
			setLocation(squareShape.getObject(), row, col);
			col++;
			if (col > myParameters[1]-1){
				row++;
				col=0;
			}
		}
//		for (Entry<String, Double> entry : states.entrySet()) {
//			//wrong implementation for now, FIX LATER loader.getParser(loader.getRuleName()).getColor().get("INSERT STATE DOUBLE")
//			String color ="";
//			if (entry.getValue()==1.0){
//				color = "#ff0000";
//			} else {
//				color = "#008080";
//			}
//			//end of wrong implementation
//			SquareShape squareShape = new SquareShape(blockLength, color, myParameters);
//			myArray[row][col] = squareShape;
//			//setlocation- function?
//			root.getChildren().add(squareShape.getObject());
//			setLocation(squareShape.getObject(), row, col);
//			col++;
//			if (col > myParameters[0]-1){
//				row++;
//				col=0;
//			}
//		}
	}
	
	private void calcOffset(){
		offsetX= (screenWidth - myParameters[0] * blockLength)/2;
		offsetY =(screenHeight - myParameters[1] * blockLength)/2;
	}
	
	private void calcSideLength(){
		double maxGridWidth = screenWidth *2/3;
		double maxGridHeight = screenHeight*2/3;
		double blockWidth = maxGridWidth/myParameters[0];
		double blockHeight = maxGridHeight/myParameters[1];
		if (blockWidth > blockHeight) {
			blockLength = blockHeight;
		} else {
			blockLength = blockWidth;
		}
	}
	
	private void setLocation(Rectangle square, int row, int col){
		square.setLayoutX(offsetX + row*blockLength);
		square.setLayoutY(offsetY + col*blockLength);
	}
	
	//helper method to clean up initializing and placing buttons
	private Button buttonInit(Button myButton, String text, double x, double y){
		myButton = new Button(text);
		myButton.setLayoutX(x);
		myButton.setLayoutY(y);
		root.getChildren().add(myButton);
		return myButton;	
	}

	public void replaceGrid(Map<String, Double> states) {
		//QUEUE IMPLEMENTATION
		q = ui.getQueueState();
		int row = 0; int col = 0;
		String color = "";
		while (!q.isEmpty()){
			double d = q.remove();
			if (d%2==0){
				color = "#ffff00";
			} else {
				color = "#c72780";
			}
			myArray[row][col].setColor(color);
			col++;
			if (col > myParameters[1]-1){
				row++;
				col=0;
			}
		}
	}
}
