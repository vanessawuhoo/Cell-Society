package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareShape extends Shape {
	private String hex;
	private Rectangle square;
	private double sideLength;
	
	//constructor
	SquareShape(double width, double height, int[] myParameters){
		sideLength = calcSideLength(width, height, myParameters);
		square = new Rectangle(sideLength, sideLength);	
	}
	
	public void setColor(String newColor){
		hex = newColor;
		square.setFill(Color.web(hex));
	}
	
	public String getColor(){
		return super.getColor();
	}
	

	
	public Rectangle getObject(){
		return square;
	}
	
	
	private double calcSideLength(double width, double height, int[]myParameters){
		double widthLimit = width/3*2;
		double heightLimit = height/3*2;
		double blockWidth = widthLimit/myParameters[0];
		double blockHeight = heightLimit/myParameters[1];
		if (blockWidth > blockHeight) {
			return blockHeight;
		} else {
			return blockWidth;
		}
	}
	
}
