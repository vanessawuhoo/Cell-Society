package ui;

import java.util.Map;
import java.util.Queue;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class RenderSquares extends RenderShapes {
	private double screenWidth, screenHeight;
	private int[] myParameters;
	private Map<Double,String> myColors;
	private SquareShape[][] myArray;
	private Pane myGrid;

	public RenderSquares(double width, double height, int[] parameters, Map<Double,String> key) {
		this.screenHeight=height;
		this.screenWidth=width;
		this.myParameters=parameters;
		this.myColors=key;
		initPane();
	}
	
	public Pane getPane() {
		return myGrid;
	}
	
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
	
	public void initPane() {
		myGrid = new Pane();
		myGrid.setMaxHeight(screenHeight*2/3);
		myGrid.setMaxWidth(screenWidth*2/3);
	}
	
	public void updateColor(Map<Double, String> color) {
		myColors = color;
		for (int row = 0; row < myArray.length; row++){
			for (int col = 0; col < myArray[0].length;col++){
				myArray[row][col].setColor(myColors.get(myArray[row][col].state));
			}
		}
	}
	
	
	//initializes the grid upon loading an XML and inserts things into the pane
		public void initGrid(Queue<Double> states){
			myArray = new SquareShape[myParameters[1]][myParameters[0]];
			int row = 0;
			int col = 0;
			while (!states.isEmpty()) {
				double currState = states.remove();
				SquareShape square = new SquareShape(calcSideLength(), myColors.get(currState), currState);
				myArray[row][col] = square;
				myGrid.getChildren().add(square);
				setLocation(square, row, col);
				col++;
				if (col > myParameters[0]-1){
					row++;
					col=0;
				}
			}
		}
		public SquareShape[][] getArray(){
			return myArray;
		}
	
		//method to run updates on the grid square states
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
		
		//helper method to set locations of squares in the grid
		private void setLocation(SquareShape square, int row, int col){
			square.setLayoutX(calcSideLength()*col);
			square.setLayoutY(calcSideLength()*row);
		}
		
		//calculates the optimal side length based on scaling vertically or horizontally
		private double calcSideLength(){
			double blockLength = 0;
			double maxGridWidth = screenWidth *7/12;
			double maxGridHeight = screenHeight*7/12;
			double blockWidth = maxGridWidth/myParameters[0];
			double blockHeight = maxGridHeight/myParameters[1];
			if (blockWidth > blockHeight) {
				blockLength = blockHeight;
			} else {
				blockLength = blockWidth;
			}
			return blockLength;
		}
		
}
