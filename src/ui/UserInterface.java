package ui;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.Hub;
import main.SimVars;

public class UserInterface {
	private String TITLE = "Group 1 Cellular Automata Simulator"; 
	private String xmlTitle;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private ResourceBundle myResources;
	private Shape[][] myArray;
	private int[] myParameters;
	private Map<Double,String> colors;
	private double screenWidth, screenHeight,blockLength,offsetX,offsetY;
	private Button start, stop, step, slow, fast, load;
	private Scene myUserInterface;
	private Group root;
	private Hub hub;

	//give the display the title
	public String getTitle() {
		return TITLE;
	}
	
	//set internal representation of hub so hub methods can be called
	public void setHub(Hub h){
		hub = h;
	}
	
	//initialize all UI elements
	public Scene init(Stage stage, double width, double height, String resource) {
		root = new Group();
		screenWidth = width;
		screenHeight = height;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + resource);
		myUserInterface = new Scene(root, width, height, Color.WHITE);
		load = buttonInit(myResources.getString("LoadButton"), width/7, height/20);
		start = buttonInit(myResources.getString("StartButton"), width*2/7, height/20);
		stop = buttonInit(myResources.getString("StopButton"), width*3/7, height/20);
		step = buttonInit(myResources.getString("StepButton"), width*4/7, height/20);
		slow = buttonInit(myResources.getString("SlowButton"), width*5/7, height/20);
		fast = buttonInit(myResources.getString("FastButton"), width*6/7, height/20);
		initButtonEvents();
		return myUserInterface;
	}
	

	private void load(){
		SimVars variables = hub.loadSimulation(getFile());
		colors = variables.color_map;
		myParameters = variables.rule.getGrid_parameters();
		calcSideLength();
		calcOffset();
		initGrid(variables.states);
	}
	
	private String getFile(){
		TextInputDialog input = new TextInputDialog("");
		input.setTitle(myResources.getString("FilePromptTitle"));
        input.setContentText(myResources.getString("FilePrompt"));
        Optional<String> response = input.showAndWait();
        if (response.isPresent()) {
            return response.get();
        }
        //error
        return "";
	}
	
	//initializes the grid upon loading an XML
	private void initGrid(Queue<Double> states){
		myArray = new Shape[myParameters[0]][myParameters[1]];
		int row = 0;
		int col = 0;
		while (!states.isEmpty()) {
			double currState = states.remove();
			System.out.println("state: " + currState);
			String color = colors.get(currState);
			SquareShape squareShape = new SquareShape(blockLength, color, myParameters);
			myArray[row][col] = squareShape;
			root.getChildren().add(squareShape.getObject());
			setLocation(squareShape.getObject(), row, col);
			col++;
			if (col > myParameters[0]-1){
				row++;
				col=0;
			}
		}
	}

	//method to run updates on the grid square states
	public void updateStep(Queue<Double> newStates) {
		int row = 0;
		int col = 0;
		while (!newStates.isEmpty()) {
			//wrong implementation for now, FIX LATER loader.getParser(loader.getRuleName()).getColor().get("INSERT STATE DOUBLE")
			double currState = newStates.remove();
			String color = colors.get(currState);
			Shape square = myArray[row][col];
			square.setColor(color);
			col++;
			if (col > myParameters[0]-1){
				row++;
				col=0;
			}
		}
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
		load.setOnMouseClicked(e->load());
	}
	
	//helper method to set locations of squares in the grid
	private void setLocation(Rectangle square, int row, int col){
		square.setLayoutX(offsetX + col*blockLength);
		square.setLayoutY(offsetY + row*blockLength);
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
	

	//calculates the placement of the grid depending on the size of the blocks
	private void calcOffset(){
		offsetX= (screenWidth - myParameters[0] * blockLength)/2;
		offsetY =(screenHeight - myParameters[1] * blockLength)/2;
	}

	
}

