package ui;

import java.util.Map;
import java.util.Queue;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

//abstract class that dictates how the UI broadly renders a grid of shapes
public abstract class RenderShapes {
	private double screenWidth, screenHeight;
	private int[] myParameters;
	private Map<Double,String> myColors;
	private Shape[][] myArray;
	private Pane myGrid;
	
	//returns the pane of rendered shapes to the main UI class
	public abstract Pane getPane();
	
	//initializes the grids of both array to access the objects and the visual representation of the objects in the pane
	public abstract void initGrid(Queue<Double> states);
	
	//method to turn outlines on shapes on or off
	public abstract void setGridOutline(boolean on);

	//method to update the states of the objects in the pane by iterating through with the array to access each object
	//and resetting its color
	public abstract void updateStep(Queue<Double> states);

	//method to update the colors inside the map key itself 
	public abstract void updateColor(Map<Double, String> colors);
	
	//method to get the array in order to access the objects in the pane
	public abstract Shape[][] getArray();
	

}
