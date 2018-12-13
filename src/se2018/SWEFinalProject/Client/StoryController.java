package se2018.SWEFinalProject.Client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se2018.SWEFinalProject.Server.Story;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;


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
	
	// Comment FXML Objects
	@FXML ScrollPane commentPane;
	@FXML TextArea displayCommentField;
	@FXML VBox commentSectionVBox;
	
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
	protected void handleSendCommentButtonAction(ActionEvent event) {
		String sendComment = IDField.getText() + "-" + displayCommentField.getText();
		gateway.addComment(sendComment);
		commentSectionRefresh(IDField.getText());
	}
	
	public void commentSectionRefresh(String id) {
		commentSectionVBox.getChildren().clear();

		for(int i = 0; i < gateway.getCommentCount(id); i++) {
	
			VBox commentPane = new VBox();
			commentPane.setPrefHeight(100);
			commentPane.setPrefWidth(100);
			commentPane.getChildren().add(new Label(gateway.getComment(id + "-" + Integer.toString(i))));
			commentSectionVBox.getChildren().add(commentPane);
			System.out.println("storycontroller: " + gateway.getComment(id + "-" + Integer.toString(i)));
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		gateway = new ChatGateway(textArea);
	    // Start the transcript check thread
	    //new Thread(new StoryControllerRefresh(gateway)).start();
	}
	  
	class StoryControllerRefresh implements Runnable, se2018.SWEFinalProject.Chat.ChatConstants {
		private ChatGateway gateway; // Gateway to the server
		
		public StoryControllerRefresh(ChatGateway gateway) {
		    this.gateway = gateway;
		}

		public void run() {
			/*
	     	while(true) {
//	   			dc.refresh();
	     		Platform.runLater(()->commentSectionRefresh());
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

