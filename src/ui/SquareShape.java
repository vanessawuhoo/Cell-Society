package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareShape extends Shape {
	private String hex;
	private Rectangle square;
	private double sideLength;
	
	//constructor
	SquareShape(double blockLength, String color, int[] myParameters){
		hex = color;
		sideLength = blockLength;
		square = new Rectangle(sideLength, sideLength);
		square.setFill(Color.web(hex));
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
	
	

	
	private double getBlockLength(){
		return sideLength;
	}
	
}
