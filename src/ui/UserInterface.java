package ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.Hub;
import main.SimVars;

//main UI class that initializes and orients all objects in the GUI
//dependent on Hub 
public class UserInterface {
	private String TITLE = "Group 1 Cellular Automata Simulator";
	private BorderPane layout;
	public Map<String, RenderShapes> myPossibleRenders;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private ResourceBundle myResources;
	private int[] myParameters;
	private ChoiceBox<String> selectCells, selectEdge, selectNeighborhood;
	private ChoiceBox<String> selectState;
	private TextField xmlField, hexField;
	private Map<Double,String> colors, stateMap;
	private double screenWidth, screenHeight;
	private Button start, stop, step, slow, fast, load;
	private Scene myUserInterface;
	private Map<Double,Queue<Integer>> graphData;
	private Hub hub;
	private CheckBox outlines;
	private String myCellShape = "SQUARE", myEdgeType = "FINITE", myNeighborhood = "ALL", myXML = "";
	
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
	
	//loads the horizontal bar of buttons at top of GUI
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
		sidebar.setSpacing(40);
		loadFileInput();
		xmlField.setText(myXML);
		VBox cellControl = loadCellShapeControl();
		outlines = new CheckBox(myResources.getString("gridvis"));
		outlines.setSelected(true);
		HBox colorControl = loadColorControl();
		Button getGraph = new Button(myResources.getString("graph"));
		getGraph.setOnMouseClicked(e->launchPopup(new Stage()));
		sidebar.getChildren().addAll(xmlField, cellControl, colorControl, getGraph, outlines);
		return sidebar;
	}

	//loads text field that asks for which XML file to open
	private void loadFileInput(){
		xmlField = new TextField();
		xmlField.setPromptText(myResources.getString("XMLInput"));
	}
	
	//loads the section of the sidebar that allows user to control cell parameters
	private VBox loadCellShapeControl(){
		VBox box = new VBox();
		HBox cellControl = new HBox();
		selectCells = new ChoiceBox<String>();
		selectCells.getItems().addAll(
				myResources.getString("sq"),
				myResources.getString("tri"),
				myResources.getString("hex"));
		selectCells.getSelectionModel().select(myCellShape);;
		selectEdge = new ChoiceBox<String>();
		selectEdge.getItems().addAll(
				myResources.getString("fin"),
				myResources.getString("tor"));
		selectEdge.getSelectionModel().select(myEdgeType);
		selectNeighborhood = new ChoiceBox<String>();
		selectNeighborhood.getItems().addAll(
				myResources.getString("card"),
				myResources.getString("all"));
		selectNeighborhood.getSelectionModel().select(myNeighborhood);
		Button parametersGo = new Button(myResources.getString("Trigger"));
		parametersGo.setOnMouseClicked(e-> changeCells());
		cellControl.getChildren().addAll(selectEdge, parametersGo);
		cellControl.setSpacing(10);
		if (myCellShape.equals(myResources.getString("sq"))){
			box.getChildren().addAll(selectCells,selectNeighborhood,cellControl);	
		} else {
			box.getChildren().addAll(selectCells,cellControl);	
		}
		box.setSpacing(5);
		return box;
	}
	
	//loads section that allows user to dynamically change the color representation of a state
	private HBox loadColorControl(){
		HBox colorControl = new HBox();
		colorControl.setSpacing(5);
		selectState = new ChoiceBox<String>();
		hexField = new TextField();
		hexField.setPromptText(myResources.getString("Hex"));
		hexField.setMaxWidth(70);
		Button colorSetGo = new Button(myResources.getString("Trigger"));
		colorSetGo.setOnMouseClicked(e->changeColorMap() );
		colorControl.getChildren().addAll(selectState, hexField, colorSetGo);
		return colorControl;
	}
	
	//helper method to help change the key map of colors in UI
	private void changeColorMap(){
		String newhex = hexField.getText();
		String stateMeaning = selectState.getSelectionModel().getSelectedItem();
		Double state = null;
		for (Entry<Double, String> e : stateMap.entrySet()){
			if (e.getValue().equals(stateMeaning)){
				state = e.getKey();
			}
		}
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
	
	//helper method to allow all the objects in the grid to be clickable to change state
	private void getShapeArray(){
		Shape[][] array = myPossibleRenders.get(myCellShape).getArray();
		for (int row = 0; row < array.length;row++){
			for (int col = 0; col < array[0].length;col++){
				int id = col + row * myParameters[0]+1;
				array[row][col].setOnMouseClicked(e->changeCell(id));
			}
		}
	}
	
	//helper method to call the method in hub that changes cell state
	private void changeCell(int ID){
		System.out.println(ID);
//		hub.changeCell(ID);
	}
	
	
	//set internal representation of hub so hub methods can be called
	public void setHub(Hub h){
		hub = h;
	}
	
	//main method to load in an XML file and its visual representation
	private void load(){
		myXML = xmlField.getText();
		if (myXML.equals("")){
			myXML = "Data/LargeGameOfLife.xml";
			System.out.println("No xml given; using default");
		} 
		SimVars variables = hub.loadSimulation(myXML);
		this.colors = variables.getColor_map();
		this.myParameters = variables.getGrid_dimensions();
		this.stateMap = variables.getName_map();
		Queue<Double> states = variables.getStates();
		loadRenders();
		initGraphData(states);
		myPossibleRenders.get(myCellShape).initGrid(states);
		Pane myGrid = myPossibleRenders.get(myCellShape).getPane();
		layout.setCenter(myGrid);
		BorderPane.setMargin(myGrid, new Insets(screenHeight/20,0,0,screenWidth/12));
		BorderPane.setAlignment(myGrid, Pos.CENTER);
		getShapeArray();
		selectState.getItems().clear();
		for (Double d : colors.keySet()){
			selectState.getItems().add(stateMap.get(d));
		}
	}
	
	//method to update cell parameters if the user decides to change it 
	private void loadNewCellParameters(Queue<Double> states){
		loadRenders();
		initGraphData(states);
		myPossibleRenders.get(myCellShape).initGrid(states);
		Pane myGrid = myPossibleRenders.get(myCellShape).getPane();
		layout.setCenter(myGrid);
		BorderPane.setMargin(myGrid, new Insets(screenHeight/20,0,0,screenWidth/12));
		BorderPane.setAlignment(myGrid, Pos.CENTER);
		getShapeArray();
		selectState.getItems().clear();
		for (Double d : colors.keySet()){
			selectState.getItems().add(stateMap.get(d));
		}
		myPossibleRenders.get(myCellShape).setGridOutline(true);
	}

	//helper method to initialize ability to track cell state population
	private void initGraphData(Queue<Double> states){
		graphData = new HashMap<Double,Queue<Integer>>();
		Map<Double,Integer> countOfStates = countStates(states);
		for (Double d : colors.keySet()){
			Queue<Integer> queue = new LinkedList<Integer>();
			queue.add(countOfStates.get(d));
			graphData.put(d, queue);
		}
	}
	
	//method that adds data to the data structure that tracks cell state population
	private void addGraphData(Map<Double,Integer> countOfStates){
		for (Double d : colors.keySet()){
			Queue<Integer> queue = graphData.get(d);
			queue.add(countOfStates.get(d));
			graphData.put(d, queue);
		}
	}
	
	//method that goes through all of the passed states and counts occurrence of each
	private Map<Double,Integer> countStates(Queue<Double> states){
		Queue<Double> copy = new LinkedList<Double>(states);
		Map<Double,Integer> counts = new HashMap<Double,Integer>();
		while (!copy.isEmpty()){
			double d = copy.remove();
			if (!counts.containsKey(d)){
				counts.put(d, 1);
			} else {
				counts.put(d, counts.get(d)+1);
			}
		}
		return counts;
	}
	
	//method to change the cell parameters of the GUI, calls hub
	private void changeCells(){
		myCellShape = selectCells.getSelectionModel().getSelectedItem();
		myEdgeType = selectEdge.getSelectionModel().getSelectedItem();
		if(!myCellShape.equals(myResources.getString("sq"))){
			myNeighborhood = selectNeighborhood.getSelectionModel().getSelectedItem();
		}
		load();
		VBox sidebar = loadSidebar();
		layout.setRight(sidebar);
		BorderPane.setMargin(sidebar, new Insets(screenHeight/20,screenWidth/20,screenHeight/20,screenWidth/20));
		System.out.println(myCellShape);
		System.out.println(myEdgeType);
		System.out.println(myNeighborhood);
		buttonEvents();
		if (myCellShape.equals(myResources.getString("sq"))){
			loadNewCellParameters(hub.updateGridSettings(myCellShape, myEdgeType, ""));
		} else {
			loadNewCellParameters(hub.updateGridSettings(myCellShape, myEdgeType, myNeighborhood));
		}
	}
	
	//method to update the colors map key in the render class 
	private void updateColor(Map<Double,String> newcolors){
		myPossibleRenders.get(myCellShape).updateColor(colors);
	}
	
	//method that launches a popup that holds a graph
	private void launchPopup(Stage s){
		s.setTitle(myResources.getString("chart"));
		Popup popup = new Popup();
		popup.show(s);
		Group r = new Group();
		Map<Double,Queue<Integer>> copy = new HashMap<Double,Queue<Integer>>(graphData);
		DrawGraph draw = new DrawGraph(new HashMap<Double,Queue<Integer>>(copy));
		r.getChildren().add(draw.getChart());
		s.setScene(new Scene(r));
		s.show();
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

	//method that updates the states of objects each step
	public void updateStep(Queue<Double> states) {
		Map<Double,Integer>countOfStates = countStates(states);
		myPossibleRenders.get(myCellShape).updateStep(states);
		addGraphData(countOfStates);
	}
}

