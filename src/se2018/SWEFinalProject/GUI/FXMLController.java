package se2018.SWEFinalProject.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class FXMLController {
	
	@FXML TextField authorField;
	@FXML TextField titleField;
	@FXML TextField pointsField;
	@FXML VBox todoColumnVBox;
	@FXML VBox inprogressColumnVBox;
	@FXML VBox testingColumnVBox;
	@FXML VBox doneColumnVBox;
	@FXML Button submitButton;
	 
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
    	storyPane.setPrefHeight(80);
    	storyPane.setPrefWidth(300);
    	storyPane.setStyle("-fx-background-color: RGB(130,229,130);");
    	
    	// TODO Set Text here for 
    	storyPane.getChildren().add(new Text("Author: "));
    	storyPane.getChildren().add(new Text("Title: "));
    	storyPane.getChildren().add(new Text("Points: "));
    	storyPane.setOnMouseClicked(e -> {
            System.out.println("Clicked");
            Parent root;
            try {
              
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/Story_Details.fxml"));
                
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Story Details");
                stage.setScene(new Scene(root, 450, 700));
                stage.show();
                
               
                StoryController controller = loader.getController();
                controller.displayAuthorField.setText("JeehadiJonny");
                controller.displayTitleField.setText("Get this Bread (yeet)");
                controller.displayPointsField.setText("5");
                
            }
            catch (IOException d) {
                d.printStackTrace();
            }
        });
    	
    	storyPane.setOnMousePressed(e -> {
    		double startDragX = e.getSceneX();
    		double startDragY = e.getSceneY();
    		System.out.println(startDragX + ", " + startDragY);
    	});
    	
    	storyPane.setOnMouseReleased(e -> {
    		double endDragX = e.getSceneX();
    		double endDragY = e.getSceneY();
    		System.out.println(endDragX + ", " + endDragY);
    		
    		Bounds boundsInScene = storyPane.localToScene(storyPane.getBoundsInLocal());
    		System.out.println(boundsInScene.getMaxX());
    		if (endDragX <= 300) {
    			System.out.println("Ended in Todo");
    			if (endDragX <= boundsInScene.getMaxX() && endDragX >= boundsInScene.getMinX()) {
    				System.out.println("Already in Todo");
    			}
    			else {
    				todoColumnVBox.getChildren().add(storyPane);
    			}
    		}
    		else if (endDragX <= 600) {
    			System.out.println("Ended in In Progress");
    			if (endDragX <= boundsInScene.getMaxX() && endDragX >= boundsInScene.getMinX()) {
    				System.out.println("Already in In Progress");
    			}
    			else {
    				inprogressColumnVBox.getChildren().add(storyPane);
    			}
    		}
    		else if (endDragX <= 900) {
    			System.out.println("Ended in Testing");
    			if (endDragX <= boundsInScene.getMaxX() && endDragX >= boundsInScene.getMinX()) {
    				System.out.println("Already in Testing");
    			}
    			else {
    				testingColumnVBox.getChildren().add(storyPane);
    			}
    		}
    		else if (endDragX <= 1200) {
    			System.out.println("Ended in Done");
    			if (endDragX <= boundsInScene.getMaxX() && endDragX >= boundsInScene.getMinX()) {
    				System.out.println("Already in Done");
    			}
    			else {
    				doneColumnVBox.getChildren().add(storyPane);
    			}
    		}
    	});
    	
    	todoColumnVBox.getChildren().add(storyPane);
    	
    	
    	System.out.println(storyPane.getParent().idProperty().getValue());
    	
    	
    }
    
    @FXML 
    protected void handleStoryClick(ActionEvent event) {
    	
    }
    
    @FXML
    protected void handleAddStorySubmitButtonAction(ActionEvent event) {
    	
    	if (authorField.getText().isEmpty() == false) {
    		if (titleField.getText().isEmpty() == false) {
    			if (pointsField.getText().isEmpty() == false) {
    				System.out.println(authorField.getText());
    		    	System.out.println(titleField.getText());
    		    	System.out.println(pointsField.getText());
    		    	
    		    	Stage stage = (Stage) submitButton.getScene().getWindow();
    		    	stage.close();
    		    	System.out.println("window closed");
    			}
    			else {
    				System.out.println("Missing points text");
    			}
    		}
    		else {
    			System.out.println("Missing title text");
    		}
    	}
    	else {
    		System.out.println("Missing author text");
    	}
    }


}
