package main;
	
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			URL fxmlURL=getClass().getResource("/application/Accueil.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			Scene scene = new Scene( (Parent) root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Projet cpoa");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();	
		} 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
