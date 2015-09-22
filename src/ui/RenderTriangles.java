package ui;

import java.util.Map;
import java.util.Queue;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class RenderTriangles extends RenderShapes {
	private double screenWidth, screenHeight;
	private int[] myParameters;
	private Map<Double,String> myColors;
	private Polygon[][] myArray;
	private Pane myGrid;
	
	public RenderTriangles(double width, double height, int[] parameters, Map<Double,String>key){
		this.screenHeight=height;
		this.screenWidth=width;
		this.myParameters=parameters;
		this.myColors=key;
		initPane();
	}

	public void initPane() {
		myGrid = new Pane();
		myGrid.setMaxHeight(screenHeight*2/3);
		myGrid.setMaxWidth(screenWidth*2/3);
	}
	
	
	public void initGrid(Queue<Double> states) {
		myArray = new TriangleShape[myParameters[1]][myParameters[0]];
		int row = 0;
		int col = 0;
		int id = 1;
		while (!states.isEmpty()) {
			double currState = states.remove();
			TriangleShape triangle = new TriangleShape(id, myColors.get(currState));
			myArray[row][col] = triangle;
			myGrid.getChildren().add(triangle);
			if (calcOrientation(id)){
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
	
	public boolean calcOrientation(int id){
		if (myParameters[0]%2!=0){
			if (id%2==0){
				return true;
			} else {
				return false;
			}
		} else {
			//row is even
			if (myParameters[1]%2==0) {
				if (id%2==0){
					return true;
				}else {
					return false;
				}
			} else {
				if (id%2==0) {
					return false;
				} else {
					return true;
				}
			}
		}
	}

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

	public void setGridOutline(boolean on) {
		for (int row = 0; row < myArray.length; row++) {
			for (int col = 0; col < myArray[0].length; col++){
				if (on){
//					myArray[row][col].setStroke(Color.BLACK);
				} else {
//					myArray[row][col].setStroke(myArray[row][col].getFill());
				}
			}
		}
	}
	
	private double calcBaseLength(){
		double baseLength = 0;
		double maxGridWidth = screenWidth *2/3;
		double maxGridHeight = screenHeight*2/3;
		double base = maxGridWidth/myParameters[0];
		double height = maxGridHeight/myParameters[1];
		if (base > height){
			baseLength = height * 2 / Math.sqrt(3);
		} else {
			baseLength = base;
		}
		return baseLength;
	}

	private void setLocation(TriangleShape triangle, int row, int col){
		triangle.setLayoutX(col*calcBaseLength()/2);
		triangle.setLayoutY(row*calcHeight());
	}
	
	private double calcHeight(){
		return calcBaseLength()*Math.sqrt(3)/2;
	}

	public Pane getPane() {
		return myGrid;
	}


}
