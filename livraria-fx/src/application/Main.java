package application;
	
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Sistema de livraria com JavaFX");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
