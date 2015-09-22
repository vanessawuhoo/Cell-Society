package ui;

import java.util.Map;
import java.util.Queue;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public class RenderSquares extends RenderShapes {
	private double screenWidth, screenHeight;
	private int[] myParameters;
	private Map<Double,String> myColors;
	private Shape[][] myArray;
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
					myArray[row][col].setOutline(on);
				}
			}
	}
	
	public void initPane() {
		myGrid = new Pane();
		myGrid.setMaxHeight(screenHeight*2/3);
		myGrid.setMaxWidth(screenWidth*2/3);
	}
	
	//initializes the grid upon loading an XML and inserts things into the pane
		public void initGrid(Queue<Double> states){
			myArray = new Shape[myParameters[1]][myParameters[0]];
			int row = 0;
			int col = 0;
			while (!states.isEmpty()) {
				double currState = states.remove();
				String color = myColors.get(currState);
				SquareShape squareShape = new SquareShape(calcSideLength(), color, myParameters);
				myArray[row][col] = squareShape;
				myGrid.getChildren().add(squareShape.getObject());
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
				double currState = newStates.remove();
				String color = myColors.get(currState);
				Shape square = myArray[row][col];
				square.setColor(color);
				col++;
				if (col > myParameters[0]-1){
					row++;
					col=0;
				}
			}
		}
		
		
		//helper method to set locations of squares in the grid
		private void setLocation(Rectangle square, int row, int col){
			square.setLayoutX(calcSideLength()*col);
			square.setLayoutY(calcSideLength()*row);
		}
		
		
		//calculates the optimal side length based on scaling vertically or horizontally
		private double calcSideLength(){
			double blockLength = 0;
			double maxGridWidth = screenWidth *2/3;
			double maxGridHeight = screenHeight*2/3;
			double blockWidth = maxGridWidth/myParameters[0];
			double blockHeight = maxGridHeight/myParameters[1];
			if (blockWidth > blockHeight) {
				blockLength = blockHeight;
			} else {
				blockLength = blockWidth;
			}
			return blockLength;
		}
		
//		//calculates the placement of the grid depending on the size of the blocks
//		private double calcOffsetX(double blockLength){
//			return blockLength;
//		}
//	
//		private double calcOffsetY(double blockLength){
//			return screenHeight - myParameters[1] * calcSideLength()/2;
//		}
		
}
