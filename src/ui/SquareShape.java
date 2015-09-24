package ui;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class SquareShape extends Rectangle{
	public double state;
	private String hex;
	
	public SquareShape(double blockLength, String color, double state) {
		hex = color;
		this.setWidth(blockLength);
		this.state = state;
		this.setHeight(blockLength);
		this.setFill(Color.web(color));
		this.setStroke(Color.BLACK);
	}
	
	public double getState(){
		return state;
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
