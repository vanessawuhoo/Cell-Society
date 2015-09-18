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
	
	//set object color to new Hex value passed by String
	public void setColor(String newColor){
		hex = newColor;
		square.setFill(Color.web(hex));
	}
	
	//get current hex value
	public String getColor(){
		return hex;
	}
	
	//retrieve a javafx shape representation of the object
	public Rectangle getObject(){
		return square;
	}
	
	//get javafx shape block side length
	private double getBlockLength(){
		return sideLength;
	}
	
}
