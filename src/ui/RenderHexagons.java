package ui;

import java.util.Map;
import java.util.Queue;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class RenderHexagons extends RenderShapes {
	private double screenWidth, screenHeight;
	private int[] myParameters;
	private Map<Double,String> myColors;
	private HexagonShape[][] myArray;
	private Pane myGrid;

	public RenderHexagons(double width, double height, int[] parameters, Map<Double,String> key) {
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
	
	
	//initializes the grid upon loading an XML and inserts things into the pane
		public void initGrid(Queue<Double> states){
			myArray = new HexagonShape[myParameters[1]][myParameters[0]];
			int row = 0;
			int col = 0;
			int id = 1;
			while (!states.isEmpty()) {
				double currState = states.remove();
				HexagonShape hexagon = new HexagonShape(id, myColors.get(currState), currState);
				myArray[row][col] = hexagon;
				myGrid.getChildren().add(hexagon);
				double c = calcSideLength();
				double a = c/2;
				double b = c * Math.sqrt(3)/2;
				hexagon.getPoints().addAll(new Double[]{
					0.0, a+c,
					0.0, a,
					b, 0.0,
					2*b, a,
					2*b, a+c,
					b, 2*c
				});
				setLocation(hexagon, row, col);
				col++;
				id++;
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
				myArray[row][col].setFill(Color.web(myColors.get(currState)));;
				col++;
				if (col > myParameters[0]-1){
					row++;
					col=0;
				}
			}
		}
		public void updateColor(Map<Double, String> color) {
			myColors = color;
			for (int row = 0; row < myArray.length; row++){
				for (int col = 0; col < myArray[0].length;col++){
					myArray[row][col].setColor(myColors.get(myArray[row][col].state));
				}
			}
		}
		
		//helper method to set locations of squares in the grid
		private void setLocation(HexagonShape square, int row, int col){
			if(row%2!=0){
				square.setLayoutX(calcSideLength()*Math.sqrt(3)*col+calcSideLength()*Math.sqrt(3)/2);
			} else {
				square.setLayoutX(calcSideLength()*col*Math.sqrt(3));
			}
			square.setLayoutY(1.5*calcSideLength()*row);
		}
		
		//calculates the optimal side length based on scaling vertically or horizontally
		private double calcSideLength(){
			double maxGridWidth = screenWidth *2/3;
			double maxGridHeight = screenHeight*2/3;
			double cbyHeight = maxGridHeight / (2 + myParameters[1]);
			double cbyWidth = maxGridWidth/(myParameters[0]*2 + 0.5)*2/Math.sqrt(3);
			if (cbyHeight < cbyWidth){
				return cbyHeight;
			} else {
				return cbyWidth;
			}
		}

		
		
}
