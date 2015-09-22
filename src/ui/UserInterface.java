package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Hub;
import main.SimVars;

public class UserInterface {
	private String TITLE = "Group 1 Cellular Automata Simulator";
	private BorderPane layout;
	private Map<String, RenderShapes> myPossibleRenders;
	public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private ResourceBundle myResources;
	private int[] myParameters;
	Group root;
	TextField textField;
	private Map<Double,String> colors;
	private double screenWidth, screenHeight;
	private Button start, stop, step, slow, fast, load;
	private Scene myUserInterface;
	private Hub hub;
	private VBox sidebar;

	//give the display the title
	public String getTitle() {
		return TITLE;
	}
	
	public void loadRenders(){
		myPossibleRenders = new HashMap<String, RenderShapes>();
		myPossibleRenders.put("RECTANGLE", new RenderSquares(screenWidth, screenHeight, myParameters, colors));
		myPossibleRenders.put("TRIANGLE", new RenderTriangles());
		myPossibleRenders.put("HEXAGON", new RenderHexagons());
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
		root.getChildren().add(loadLayout());
		initButtonEvents();
		return myUserInterface;
	}
	
	private void loadSidebar(){
		sidebar = new VBox();
		textField = new TextField();
		textField.setPromptText("Data File:");
		sidebar.getChildren().addAll(textField);
	}
	
	//helper method to initialize buttons with proper x and y placement 
	private Button buttonInit(String text, double x, double y){
		Button myButton = new Button(text);
		myButton.setLayoutX(x);
		myButton.setLayoutY(y);
		root.getChildren().add(myButton);
		return myButton;
	}

	
	private BorderPane loadLayout(){ 
		layout = new BorderPane();
		HBox control = loadHBox();
		layout.setTop(control);
		BorderPane.setMargin(control, new Insets(screenHeight/20,screenWidth/20,screenHeight/20,screenWidth/20));
		loadSidebar();
		layout.setRight(sidebar);
		layout.setMargin(sidebar, new Insets(0,screenWidth/20,screenHeight/20,screenWidth/20));
		return layout;
	}

	
	private void load(){
		SimVars variables = hub.loadSimulation(textField.getText());
		colors = variables.color_map;
		myParameters = variables.rule.getGrid_parameters();
		Queue<Double> states = variables.states;
		loadRenders();
		String myCellShape = "RECTANGLE";
		myPossibleRenders.get(myCellShape).initGrid(states);
		Pane myGrid = myPossibleRenders.get(myCellShape).getPane();
		layout.setCenter(myGrid);
		BorderPane.setMargin(myGrid, new Insets(screenHeight/12,0,0,screenWidth/12));
		layout.setAlignment(myGrid, Pos.CENTER);
		myPossibleRenders.get(myCellShape).setGridOutline(true);
	}
	
//	private String getFile(){
//		TextInputDialog input = new TextInputDialog("");
//		input.setTitle(myResources.getString("FilePromptTitle"));
//        input.setContentText(myResources.getString("FilePrompt"));
//        Optional<String> response = input.showAndWait();
//        if (response.isPresent()) {
//            return response.get();
//        }
//        //error
//        return "";
//	}
//	
	private HBox loadHBox() {
		HBox controlBar = new HBox();
		load = new Button(myResources.getString("LoadButton"));
		start = new Button(myResources.getString("StartButton"));
		stop = new Button(myResources.getString("StopButton"));
		step = new Button(myResources.getString("StepButton"));
		slow = new Button(myResources.getString("SlowButton"));
		fast = new Button(myResources.getString("FastButton"));
		controlBar.getChildren().addAll(load, start, stop, step, slow, fast);
		controlBar.setSpacing(screenWidth/20);
		return controlBar;
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

	public void updateStep(Queue<Double> states) {
		// TODO Auto-generated method stub
		String myCellShape = "RECTANGLE";
		myPossibleRenders.get(myCellShape).updateStep(states);
	}
}

