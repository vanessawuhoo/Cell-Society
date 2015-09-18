package ui;

import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.Hub;
import data.XMLLoader;

public class UserInterface {
	private String TITLE = "Group 1 Cellular Automata Simulator";
	private int[] myParameters;
	private Map<Double,String> colors;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private double screenWidth, screenHeight,blockLength,offsetX,offsetY;
	private Button start, stop, step, slow, fast, load;
	private Scene myUserInterface;
	private ResourceBundle myResources;
	private Shape[][] myArray;
	private Group root;
	private UITester ui;
	private Hub hub;

	//give the display the title
	public String getTitle() {
		return TITLE;
	}
	
	//set internal representation of hub so hub methods can be called
	public void setHub(Hub h){
		hub = h;
	}
	
	public void getColors(){
		XMLLoader loader = hub.getLoader();
		colors = loader.getParser(loader.getRuleName()).getColor();
	}
	
	//initialize all UI elements
	public Scene init(Stage stage, double width, double height, int[] gridParameters, String resource) {
		root = new Group();
//		ui = new UITester();
		getColors();
		calcSideLength();
		calcOffset();
		screenWidth = width;
		screenHeight = height;
		myParameters = gridParameters;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + resource);
		myUserInterface = new Scene(root, width, height, Color.WHITE);
		load = buttonInit(myResources.getString("LoadButton"), width/7, height/20);
		start = buttonInit(myResources.getString("StartButton"), width*2/7, height/20);
		stop = buttonInit(myResources.getString("StopButton"), width*3/7, height/20);
		step = buttonInit(myResources.getString("StepButton"), width*4/7, height/20);
		slow = buttonInit(myResources.getString("SlowButton"), width*5/7, height/20);
		fast = buttonInit(myResources.getString("FastButton"), width*6/7, height/20);
		initButtonEvents();
		//test case
		Map<Integer, Map<String, Double>> states = ui.getState();
		initGrid(states);
		return myUserInterface;
	}
	
	//helper method to initialize buttons with proper x and y placement 
	private Button buttonInit(String text, double x, double y){
		Button myButton = new Button(text);
		myButton.setLayoutX(x);
		myButton.setLayoutY(y);
		root.getChildren().add(myButton);
		return myButton;
	}

	//button event handler method
	private void initButtonEvents(){
		fast.setOnMouseClicked(e -> hub.increaseRate());
		slow.setOnMouseClicked(e->hub.decreaseRate());
		stop.setOnMouseClicked(e->hub.pauseSimulation());
		start.setOnMouseClicked(e->hub.playSimulation());
		step.setOnMouseClicked(e->hub.simulationStep());
		load.setOnMouseClicked(e->hub.loadTestSim());
	}
	
	//initializes the grid upon loading an XML
	private void initGrid(Map<Integer, Map<String,Double>> states){
		myArray = new Shape[myParameters[0]][myParameters[1]];
		int row = 0;
		int col = 0;
		for (int i = 0; i < states.size(); i++) {
			//wrong implementation for now, FIX LATER loader.getParser(loader.getRuleName()).getColor().get("INSERT STATE DOUBLE")
			double currState = states.get(i).get("state");
			String color = colors.get(currState);
			//end of wrong implementation
			SquareShape squareShape = new SquareShape(blockLength, color, myParameters);
			myArray[row][col] = squareShape;
			//setlocation- function?
			root.getChildren().add(squareShape.getObject());
			setLocation(squareShape.getObject(), row, col);
			col++;
			if (col > myParameters[0]-1){
				row++;
				col=0;
			}
		}
	}
	
	//calculates the placement of the grid depending on the size of the blocks
	private void calcOffset(){
		offsetX= (screenWidth - myParameters[0] * blockLength)/2;
		offsetY =(screenHeight - myParameters[1] * blockLength)/2;
	}
	
	//calculates the optimal side length based on scaling vertically or horizontally
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
	
	//helper method to set locations of squares in the grid
	private void setLocation(Rectangle square, int row, int col){
		square.setLayoutX(offsetX + row*blockLength);
		square.setLayoutY(offsetY + col*blockLength);
	}
	
	//method to run updates on the grid square states
	public void replaceGrid(Map<Integer,Map<String,Double>> newStates) {
		int row = 0;
		int col = 0;
		for (int i = 0; i < newStates.size(); i++) {
			//wrong implementation for now, FIX LATER loader.getParser(loader.getRuleName()).getColor().get("INSERT STATE DOUBLE")
			double currState = newStates.get(i).get("state");
			String color = colors.get(currState);
			//end of wrong implementation
			Shape square = myArray[row][col];
			square.setColor(color);
			col++;
			if (col > myParameters[0]-1){
				row++;
				col=0;
			}
		}
	}
}
