package ui;

public abstract class Shape {
	//abstract class extendable to different shapes
	
	private String hex;
	
	//sets the color of the shape to a hex value
	public void setColor(String newColor){
		this.hex = newColor;
	}
	
	
	//returns the color of the Shape
	public String getColor(){
		return hex;
	}
	
}
