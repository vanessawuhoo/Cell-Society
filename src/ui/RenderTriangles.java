package ui;

import java.util.Map;
import java.util.Queue;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

//class to render a grid of triangles 
public class RenderTriangles extends RenderShapes {
	private double screenWidth, screenHeight;
	private int[] myParameters;
	private Map<Double,String> myColors;
	private TriangleShape[][] myArray;
	private Pane myGrid;
	
	//constructor
	public RenderTriangles(double width, double height, int[] parameters, Map<Double,String>key){
		this.screenHeight=height;
		this.screenWidth=width;
		this.myParameters=parameters;
		this.myColors=key;
		initPane();
	}

	//intializes the pane and its parameters
	public void initPane() {
		myGrid = new Pane();
		myGrid.setMaxHeight(screenHeight*2/3);
		myGrid.setMaxWidth(screenWidth*2/3);
	}
	
	//updates the colors of each triangle to match the new state after a step
	public void updateColor(Map<Double, String> color) {
		myColors = color;
		for (int row = 0; row < myArray.length; row++){
			for (int col = 0; col < myArray[0].length;col++){
				myArray[row][col].setColor(myColors.get(myArray[row][col].state));
			}
		}
	}
	
	//intializes the grid of triangles and the array to access each one
	public void initGrid(Queue<Double> states) {
		myArray = new TriangleShape[myParameters[1]][myParameters[0]];
		int row = 0;
		int col = 0;
		int id = 1;
		while (!states.isEmpty()) {
			double currState = states.remove();
			TriangleShape triangle = new TriangleShape(myColors.get(currState), currState);
			myArray[row][col] = triangle;
			myGrid.getChildren().add(triangle);
			if (calcOrientation(id,row)){
				triangle.getPoints().addAll(new Double[]{
					calcBaseLength()/2, 0.0,
					0.0, calcHeight(),
					calcBaseLength(), calcHeight()
				});
			} else {
				triangle.getPoints().addAll(new Double[]{
					0.0,0.0,
					calcBaseLength()/2, calcHeight(),
					calcBaseLength(), 0.0
				});
			}
			setLocation(triangle, row, col);
			col++;
			id++;
			if (col > myParameters[0]-1){
				row++;
				col=0;
			}
		}
	}
	
	//helper method to calculate whether a triangle points up or down
	public boolean calcOrientation(int id, int row){
		if (myParameters[0]%2==0) {
			if (row%2==0){
				if (id%2==0){
					return true;
				}
				else {
					return false;
				}
			} else {
				if (id%2==0){
					return false;
				} else {
					return true;
				}
			}
		} else {
			if (row%2==0){
				if (id%2==0){
					return true;
				} else {
					return false;
				}
			} else {
				if (id%2==0){
					return true;
				} else {
					return false;
				}
			}
		}
	}

	//method to update the triangle colors
	public void updateStep(Queue<Double> newStates) {
		int row = 0;
		int col = 0;
		while (!newStates.isEmpty()) {
			double currState = newStates.remove();
			myArray[row][col].setFill(Color.web(myColors.get(currState)));;
			col++;
			if (col > myParameters[0]-1){
				row++;
				col=0;
			}
		}
	}
	
	//helper method to calculate optimal base length of equilateral triangle
	private double calcBaseLength(){
		double baseLength = 0;
		double maxGridWidth = screenWidth *7/12;
		double maxGridHeight = screenHeight*7/12;
		double base = maxGridWidth/myParameters[0];
		double height = maxGridHeight/myParameters[1];
		if (base > height){
			baseLength = height * 2 / Math.sqrt(3);
		} else {
			baseLength = base;
		}
		return baseLength;
	}

	//method to turn outlines on shapes on or off
	public void setGridOutline(boolean on){
		for (int row = 0; row < myArray.length; row++) {
			for (int col = 0; col < myArray[0].length; col++){
				if (on){
					myArray[row][col].setStroke(Color.BLACK);
				} else {
					myArray[row][col].setStroke(myArray[row][col].getFill());
				}
			}
		}
}
	//method to set individual location of each triangle in pane
	private void setLocation(TriangleShape triangle, int row, int col){
		triangle.setLayoutX(col*calcBaseLength()/2);
		triangle.setLayoutY(row*calcHeight());
	}
	
	//method to calculate optimal height of triangles
	private double calcHeight(){
		return calcBaseLength()*Math.sqrt(3)/2;
	}

	//getter for pane
	public Pane getPane() {
		return myGrid;
	}

	//getter for triangle array
	public Shape[][] getArray() {
		return myArray;
	}

}
