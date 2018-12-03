package se2018.SWEFinalProject.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXMLController {
	
	@FXML TextField authorField;
	@FXML TextField titleField;
	@FXML TextField pointsField;
	@FXML VBox todoColumnVBox;
	
    @FXML 
    protected void handleAddStoryButtonAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Layouts/add_story_window.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Story!");
            stage.setScene(new Scene(root, 450, 700));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML 
    protected void handleBacklogButtonAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Layouts/burndown_window.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Backlog");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML 
    protected void handleBurndownButtonAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Layouts/burndown_window.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Burndown Chart");
            stage.setScene(new Scene(root, 800, 700));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML 
    protected void handleRefreshButtonAction(ActionEvent event) {
    	
    	//TODO Make for loop and for every story retrieved from server create a new VBOX and append
    	
    	VBox storyPane = new VBox();
    	storyPane.setPrefHeight(100);
    	storyPane.setPrefWidth(300);
    	storyPane.setStyle("-fx-background-color: RGB(130,229,130);");
    	
    	todoColumnVBox.getChildren().add(storyPane);
    	
    	
    }
    
    @FXML
    protected void handleAddStorySubmitButtonAction(ActionEvent event) {
    	System.out.println("hey it works");
    	System.out.println(authorField.getText());
    	System.out.println(titleField.getText());
    	System.out.println(pointsField.getText());
    	System.out.println("What");
    	
    	
    }
    
    
}
