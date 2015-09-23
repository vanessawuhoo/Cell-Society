package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private ResourceBundle myResources;
	private int[] myParameters;
	private ChoiceBox<String> selectCells;
	private ChoiceBox<Double> selectState;
	private TextField xmlField, hexField;
	private Map<Double,String> colors;
	private double screenWidth, screenHeight;
	private Button start, stop, step, slow, fast, load;
	private Scene myUserInterface;
	private Hub hub;
	private CheckBox outlines;
	//test
	private String myCellShape = "TRIANGLE";

	//give the display the title
	public String getTitle() {
		return TITLE;
	}
	
	//initialize all UI elements
	public Scene init(Stage stage, double width, double height, String resource) {
		Group root = new Group();
		screenWidth = width;
		screenHeight = height;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + resource);
		myUserInterface = new Scene(root, width, height, Color.WHITE);
		root.getChildren().add(loadLayout());
		buttonEvents();
		return myUserInterface;
	}
	
	//load the overarching layout
	private BorderPane loadLayout(){ 
		layout = new BorderPane();
		HBox control = loadHBox();
		layout.setTop(control);
		BorderPane.setMargin(control, new Insets(screenHeight/20,screenWidth/20,screenHeight/20,screenWidth/20));
		VBox sidebar = loadSidebar();
		layout.setRight(sidebar);
		BorderPane.setMargin(sidebar, new Insets(screenHeight/20,screenWidth/20,screenHeight/20,screenWidth/20));
		return layout;
	}
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
	
	//load the UI sidebar to control the simulation
	private VBox loadSidebar(){
		VBox sidebar = new VBox();
		sidebar.setSpacing(30);
		loadFileInput();
		HBox cellControl = loadCellShapeControl();
		outlines = new CheckBox(myResources.getString("gridvis"));
		outlines.setSelected(true);
		HBox colorControl = loadColorControl();
		sidebar.getChildren().addAll(xmlField, cellControl, outlines, colorControl);
		return sidebar;
	}
	
	private void loadFileInput(){
		xmlField = new TextField();
		xmlField.setPromptText(myResources.getString("XMLInput"));
	}
	
	private HBox loadCellShapeControl(){
		HBox cellControl = new HBox();
		selectCells = new ChoiceBox<String>();
		selectCells.getItems().addAll(
				myResources.getString("sq"),
				myResources.getString("tri"),
				myResources.getString("hex"));
		Button cellShapeGo = new Button(myResources.getString("Trigger"));
		cellShapeGo.setOnMouseClicked(e-> changeCells());
		cellControl.getChildren().addAll(selectCells, cellShapeGo);
		cellControl.setSpacing(10);
		return cellControl;
	}
	
	private HBox loadColorControl(){
		HBox colorControl = new HBox();
		colorControl.setSpacing(5);
		selectState = new ChoiceBox<Double>();
		hexField = new TextField();
		hexField.setPromptText(myResources.getString("Hex"));
		hexField.setMaxWidth(70);
		Button colorSetGo = new Button(myResources.getString("Trigger"));
		colorSetGo.setOnMouseClicked(e->changeColorMap() );
		colorControl.getChildren().addAll(selectState, hexField, colorSetGo);
		return colorControl;
	}
	
	private void changeColorMap(){
		String newhex = hexField.getText();
		Double state = selectState.getSelectionModel().getSelectedItem().doubleValue();
		colors.put(state, newhex);
		updateColor(colors);
	}
	
	//load list of possible cell state rendering classes
	private void loadRenders(){
		myPossibleRenders = new HashMap<String, RenderShapes>();
		myPossibleRenders.put("SQUARE", new RenderSquares(screenWidth, screenHeight, myParameters, colors));
		myPossibleRenders.put("TRIANGLE", new RenderTriangles(screenWidth, screenHeight, myParameters, colors));
		myPossibleRenders.put("HEXAGON", new RenderHexagons(screenWidth, screenHeight, myParameters, colors));
	}
	
	//set internal representation of hub so hub methods can be called
	public void setHub(Hub h){
		hub = h;
	}
	
	private void changeCells(){
		myCellShape = selectCells.getSelectionModel().getSelectedItem().toString();
		load();
	}

	
	private void load(){
		SimVars variables = hub.loadSimulation(xmlField.getText());
		colors = variables.color_map;
		myParameters = variables.rule.getGrid_parameters();
		Queue<Double> states = variables.states;
		loadRenders();
		myPossibleRenders.get(myCellShape).initGrid(states);
		Pane myGrid = myPossibleRenders.get(myCellShape).getPane();
		layout.setCenter(myGrid);
		BorderPane.setMargin(myGrid, new Insets(screenHeight/20,0,0,screenWidth/12));
		layout.setAlignment(myGrid, Pos.CENTER);
		selectState.getItems().clear();
		for (Double d : colors.keySet()){
			selectState.getItems().add(d);
		}
		myPossibleRenders.get(myCellShape).setGridOutline(true);
	}
		
	
	private void updateColor(Map<Double,String> newcolors){
		myPossibleRenders.get(myCellShape).updateColor(colors);
	}
	

	//button event handler method
	private void buttonEvents(){
		fast.setOnMouseClicked(e -> hub.increaseRate());
		slow.setOnMouseClicked(e->hub.decreaseRate());
		stop.setOnMouseClicked(e->hub.pauseSimulation());
		start.setOnMouseClicked(e->hub.playSimulation());
		step.setOnMouseClicked(e->hub.simulationStep());
		load.setOnMouseClicked(e->load());
        outlines.selectedProperty().addListener(new ChangeListener<Boolean>() {
           public void changed(ObservableValue<? extends Boolean> ov,
             Boolean old_val, Boolean new_val) {
             myPossibleRenders.get(myCellShape).setGridOutline(outlines.isSelected());
          }
        });
	}

	public void updateStep(Queue<Double> states) {
		myPossibleRenders.get(myCellShape).updateStep(states);
	}
}

