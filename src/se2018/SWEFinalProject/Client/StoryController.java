package se2018.SWEFinalProject.Client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se2018.SWEFinalProject.Client.FXMLDocumentController.TranscriptCheck;
import se2018.SWEFinalProject.Server.Story;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;


public class StoryController implements Initializable {
	@FXML TextField displayAuthorField;
	@FXML ComboBox statusDropDown;
	@FXML Label IDField;
	@FXML TextField displayPointsField;
	@FXML TextField displayTitleField;
	@FXML TextArea displayDescriptionField;
	@FXML Button updateStoryButton;
	@FXML Button deleteStoryButton;
	@FXML VBox chatColumnVBox;
	
	private ChatGateway gateway;
	@FXML private TextArea textArea;
	private List<String> transcript = Collections.synchronizedList(new ArrayList<String>());
	
	@FXML
	protected void handleUpdateStoryButtonAction(ActionEvent event) {
		int id = Integer.parseInt(IDField.getText());
		System.out.println("update id: " + id);
		Story story = gateway.getStory(id);
		story.setAuthor(displayAuthorField.getText());
		story.setDesc(displayDescriptionField.getText());
		story.setStoryPoints(Integer.parseInt(displayPointsField.getText()));
		story.setTitle(displayTitleField.getText());
		if(statusDropDown.getValue().equals("TODO")) {
			story.setStatus("todo");
		} else if(statusDropDown.getValue().equals("In Progress")) {
			story.setStatus("inprogress");
		} else if(statusDropDown.getValue().equals("Testing")) {
			story.setStatus("testing");
		} else if(statusDropDown.getValue().equals("Done")) {
			story.setStatus("done");
		} else {
			story.setStatus("not started");
		}
		System.out.println(story.getAuthor());
		System.out.println(story.getTitle());
		// SEND INFO TO SERVER
    	// Story story = new Story(0, authorField.getText(), titleField.getText(), descriptionField.getText(), Integer.parseInt(pointsField.getText()));
    	
		String storyJSON = "";

    	try {
    		storyJSON = story.toString_();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		System.out.println("storycontroller: " + storyJSON);
      	gateway.updateStory(storyJSON);
      	Stage stage = (Stage) updateStoryButton.getScene().getWindow();
    	stage.close();
      	
	}
	
	@FXML
	protected void handleDeleteStoryButtonAction(ActionEvent event) {
		int id = Integer.parseInt(IDField.getText());
		System.out.println("delete id: " + id);
		gateway.deleteStory(id);
      	Stage stage = (Stage) deleteStoryButton.getScene().getWindow();
    	stage.close();
	}
	
	@FXML
	protected void handleAddCommentButtonAction(ActionEvent event) {
		int id = Integer.parseInt(IDField.getText());
		Story story = gateway.getStory(id);
		// TODO: display chat before opening...happens where? OH IN CLIENT CONTROLLER refresh i think
		story.addComment(chatColumnVBox.getAccessibleText());
	}
	
	@FXML
	protected void handleRefreshCommentButtonAction(ActionEvent event) {
		/*doneColumnVBox.getChildren().clear();
    	
    	int storyCount = gateway.getStoryCount();
    	System.out.println("refresh: " + storyCount);
    	for (int i = 0; i < storyCount; i++) {
    		int j = i;
    		VBox storyPane = new VBox();
    		storyPane.setPrefHeight(80);
    		storyPane.setPrefWidth(300);
    	
    		// Set Text here for 
    		Story story = gateway.getStory(j);
    		if(story.getStoryPoints() < 5) {
    			storyPane.setStyle("-fx-background-color: RGB(130,229,130);");
    		}
    		else if(story.getStoryPoints() > 4 && story.getStoryPoints()< 8 ) {
    			storyPane.setStyle("-fx-background-color: RGB(255,255,100);");
    		}
    		else {
    			storyPane.setStyle("-fx-background-color: RGB(250,150,130);");
    		}
    		
    		
    		storyPane.getChildren().add(new Text("Author: " + story.getAuthor()));
    		storyPane.getChildren().add(new Text("Title: "+ story.getTitle()));
    		storyPane.getChildren().add(new Text("Points: " + Integer.toString(story.getStoryPoints())));
    		storyPane.getChildren().add(new Label(Integer.toString(story.getStoryID())));
    		System.out.println("DEBUG");
    	}
    	*/
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		gateway = new ChatGateway(textArea);
	    // Start the transcript check thread
	    new Thread(new TranscriptCheck(gateway, textArea, this)).start();
	}
	  
	class TranscriptCheck implements Runnable, se2018.SWEFinalProject.Chat.ChatConstants {
		private ChatGateway gateway; // Gateway to the server
		private TextArea textArea; // Where to display comments
		private int N; // How many comments we have read
		private StoryController dc;
		
		public TranscriptCheck(ChatGateway gateway, TextArea textArea, StoryController dc) {
		    this.gateway = gateway;
		    this.textArea = textArea;
		    this.N = 0;
		    this.dc = dc;
		}

		
		public void run() {
			/*
	     	while(true) {
	   			dc.refresh();
		    	System.out.println("fxml clients...");
		       	try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 	}
		 	*/
		         
		}
		      
	}
	
}

