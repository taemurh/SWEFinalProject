package se2018.SWEFinalProject.Client;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
	@FXML TextField pointsField;
	@FXML VBox todoColumnVBox;
           
    
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
            root = FXMLLoader.load(getClass().getResource("burndown_window.fxml"));
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
    	for () {
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
              
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Story_Details.fxml"));
                
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Story Details");
                stage.setScene(new Scene(root, 450, 700));
                stage.show();
                
               
                StoryController controller = loader.getController();
                Story story = gateway.getStory(0);
                controller.displayAuthorField.setText(story.getAuthor());
                controller.displayTitleField.setText(story.getTitle());
                controller.displayPointsField.setText(Integer.toString(story.getStoryPoints()));
                
            }
            catch (IOException d) {
                d.printStackTrace();
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
