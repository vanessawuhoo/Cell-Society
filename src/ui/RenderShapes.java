package ui;

import java.util.Map;
import java.util.Queue;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class RenderShapes {
	private double screenWidth, screenHeight;
	private int[] myParameters;
	private Map<Double,String> myColors;
	private Shape[][] myArray;
	private Pane myGrid;
	
	public abstract Pane getPane();
	
	public abstract void initGrid(Queue<Double> states);
	
	public abstract void setGridOutline(boolean on);

	public abstract void updateStep(Queue<Double> states);

	public abstract void updateColor(Map<Double, String> colors);

}
