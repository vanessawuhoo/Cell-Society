package ui;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class SquareShape extends Rectangle{
	private int id;
	private String hex;
	
	//constructor
	public SquareShape(int id, double blockLength, String color) {
		hex = color;
		this.setWidth(blockLength);
		this.setHeight(blockLength);
		this.setFill(Color.web(color));
		this.setStroke(Color.BLACK);
	}
	
	//set object color to new Hex value passed by String
	public void setColor(String newColor){
		hex = newColor;
		this.setFill(Color.web(hex));
	}	
	
	public String getColor(){
		return hex;
	}
}
