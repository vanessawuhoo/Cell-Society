package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

//Extended class, cell holds more information than a regular polygon pertinent to the UI
public class TriangleShape extends Polygon {
	public double state;
	private String hex;
	
	//constructor that sets color and gives the object its state
	public TriangleShape(String color, double state){
		hex = color;
		this.state = state;
		this.setFill(Color.web(hex));
		this.setStroke(Color.BLACK);
	}
	//resets object color
	public void setColor(String newColor){
		hex = newColor;
		this.setFill(Color.web(hex));
	}

}
