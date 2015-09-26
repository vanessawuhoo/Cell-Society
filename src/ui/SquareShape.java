package ui;
import javafx.scene.paint.Color;

//Extended class, cell holds more information than a regular rectangle pertinent to the UI
public class SquareShape extends ShapeClass{
	public double state;
	private String hex;
	
	//constructor
	public SquareShape(double blockLength, String color, double state) {
		hex = color;
//		this.setWidth(blockLength);
		this.state = state;
//		this.setHeight(blockLength);
		this.setFill(Color.web(color));
		this.setStroke(Color.BLACK);
	}

}
