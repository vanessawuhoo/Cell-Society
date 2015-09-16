package ui;

import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Display extends Application {
	
	private UserInterface myUserInterface;
	
	//get the screen resolution width of the computer
	public double getWidth() {
		return java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()*.6;
	}

	//get the screen resolution height of the computer
	public double getHeight(){
		return java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()*.8;
	}
	
	//initialize the UI and set the title
	public void start(Stage stage) {
        myUserInterface = new UserInterface();
        stage.setTitle(myUserInterface.getTitle());
        Scene myScene = myUserInterface.init(stage, getWidth(), getHeight());
        stage.setScene(myScene);
        stage.show();
	}	
	

	public void update(Map<Integer, List<Double>> states){
		
	}
	
    public static void main (String[] args) {
        launch(args);
    }


}
