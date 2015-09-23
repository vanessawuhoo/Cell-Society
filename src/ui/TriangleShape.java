package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriangleShape extends Polygon {
	private int id;
	public double state;
	private String hex;
	
	
	public TriangleShape(int id, String color, double state){
		hex = color;
		this.state = state;
		this.setFill(Color.web(hex));
		this.setStroke(Color.BLACK);
	}
	
	public void setColor(String newColor){
		hex = newColor;
		this.setFill(Color.web(hex));
	}

}
