package se2018.SWEFinalProject.Client;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se2018.SWEFinalProject.Client.StoryController;
import se2018.SWEFinalProject.Server.Story;

public class FXMLDocumentController implements Initializable {
    private ChatGateway gateway;
    
    @FXML
    private TextArea textArea;
    @FXML
    private TextField comment;
    
    @FXML TextField authorField;
	@FXML TextField titleField;
	@FXML VBox inprogressColumnVBox;
	@FXML VBox testingColumnVBox;
	@FXML VBox doneColumnVBox;
	@FXML TextField pointsField;
	@FXML VBox todoColumnVBox;
	@FXML Button submitButton;
           
    
    @FXML
    private void sendComment(ActionEvent event) {
        String text = comment.getText();
        gateway.sendComment(text);
    }
    
    @FXML 
    protected void handleAddStoryButtonAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("add_story_window.fxml"));
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
            root = FXMLLoader.load(getClass().getResource("backlog_window.fxml"));
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
            root = FXMLLoader.load(getClass().getResource("burndown_window.fxml"));
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
    	
    	// SEND INFO TO SERVER
    	
    	Story story = new Story(0, authorField.getText(), titleField.getText(), "this stories description", Integer.parseInt(pointsField.getText()));
    	String storyJSON = "";

    	try {
    		storyJSON = story.toString_();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		System.out.println(storyJSON);
      	gateway.sendStory(storyJSON);

    }
    
    @FXML 
    protected void handleRefreshButtonAction(ActionEvent event) {
    	
    	//TODO Make for loop and for every story retrieved from server create a new VBOX and append
    	todoColumnVBox.getChildren().clear();
    	
    	int storyCount = gateway.getStoryCount();
    	for (int i = 0; i < storyCount; i++) {
    		int j = i;
    		VBox storyPane = new VBox();
    		storyPane.setPrefHeight(80);
    		storyPane.setPrefWidth(300);
    		storyPane.setStyle("-fx-background-color: RGB(130,229,130);");
    	
    		// Set Text here for 
    		Story story = gateway.getStory(j);
    		
    		storyPane.getChildren().add(new Text("Author: " + story.getAuthor()));
    		storyPane.getChildren().add(new Text("Title: "+ story.getTitle()));
    		storyPane.getChildren().add(new Text("Points: " + Integer.toString(story.getStoryPoints())));
    		storyPane.setOnMouseClicked(e -> {
    		System.out.println("Clicked");
            Parent root;
            try {
              
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Story_Details.fxml"));
                
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Story Details");
                stage.setScene(new Scene(root, 450, 700));
                stage.show();
                
               
                StoryController controller = loader.getController();
              
                controller.displayAuthorField.setText(story.getAuthor());
                controller.displayTitleField.setText(story.getTitle());
                controller.displayPointsField.setText(Integer.toString(story.getStoryPoints()));
                
            }
            catch (IOException d) {
                d.printStackTrace();
            }
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
    	
    }
    
    @FXML 
    protected void handleStoryClick(ActionEvent event) {
    	
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gateway = new ChatGateway(textArea);
        // Start the transcript check thread
        new Thread(new TranscriptCheck(gateway,textArea)).start();
    }        
}

class TranscriptCheck implements Runnable, se2018.SWEFinalProject.Chat.ChatConstants {
    private ChatGateway gateway; // Gateway to the server
    private TextArea textArea; // Where to display comments
    private int N; // How many comments we have read
    
    /** Construct a thread */
    public TranscriptCheck(ChatGateway gateway,TextArea textArea) {
      this.gateway = gateway;
      this.textArea = textArea;
      this.N = 0;
    }

    /** Run a thread */
    public void run() {
      while(true) {
    	  
        try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
          
      }
    }
