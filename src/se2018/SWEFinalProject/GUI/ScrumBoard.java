package se2018.SWEFinalProject.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScrumBoard extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Layouts/scrum_board.fxml"));
		primaryStage.setTitle("Scrum Board Tool");
		primaryStage.setScene(new Scene(root, 1200, 800));
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

		
}
