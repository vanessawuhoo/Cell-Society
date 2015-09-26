package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ShapeClass extends Polygon {
	public double state;
	private String hex;
	
	//resets object color
	public void setColor(String newColor){
		hex = newColor;
		this.setFill(Color.web(hex));
	}
}
