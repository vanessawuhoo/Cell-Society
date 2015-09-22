package ui;

import java.util.Map;
import java.util.Queue;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public abstract class RenderShapes {
	private double screenWidth, screenHeight;
	private int[] myParameters;
	private Map<Double,String> myColors;
	private Shape[][] myArray;
	private Pane myGrid;
	
	abstract Pane getPane();
	
	abstract void initGrid(Queue<Double> states);
	
	abstract void updateStep(Queue<Double> states);

	abstract void setGridOutline(boolean on);
}
