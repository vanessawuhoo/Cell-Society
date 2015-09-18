package ui;

public abstract class Shape {
	private String hex;
	
	public void setColor(String newColor){
		this.hex = newColor;
	}
	
	public String getColor(){
		return hex;
	}
	
	
}
