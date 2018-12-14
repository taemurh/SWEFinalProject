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
import javafx.scene.layout.HBox;
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
		// update new story from fields in controller
		int id = Integer.parseInt(IDField.getText());
		Story story = gateway.getStory(id);
		story.setAuthor(displayAuthorField.getText());
		story.setDesc(displayDescriptionField.getText());
		story.setStoryPoints(Integer.parseInt(displayPointsField.getText()));
		story.setTitle(displayTitleField.getText());
		// update and set status off of drop down
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
		
		String storyJSON = "";

    	try {
    		storyJSON = story.toString_();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
      	gateway.updateStory(storyJSON);
      	Stage stage = (Stage) updateStoryButton.getScene().getWindow();
    	stage.close();
      	
	}
	
	@FXML
	protected void handleDeleteStoryButtonAction(ActionEvent event) {
		int id = Integer.parseInt(IDField.getText());
		gateway.deleteStory(id);
      	Stage stage = (Stage) deleteStoryButton.getScene().getWindow();
    	stage.close();
	}
	
	@FXML
	protected void handleSendCommentButtonAction(ActionEvent event) {
		// pass the story id and comment to add to it
		String sendComment = IDField.getText() + "-" + displayCommentField.getText();
		gateway.addComment(sendComment);
		commentSectionRefresh(IDField.getText());
	}
	
	public void commentSectionRefresh(String id) {
		commentSectionVBox.getChildren().clear();
		// fetch comments from server and populate the comment panes in comment section Vbox
		for(int i = 0; i < gateway.getCommentCount(id); i++) {
			HBox commentPane = new HBox();
			commentPane.setPrefHeight(1);
			commentPane.setPrefWidth(100);
			commentPane.getChildren().add(new Label(gateway.getComment(id + "-" + Integer.toString(i))));
			commentSectionVBox.getChildren().add(commentPane);
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		gateway = new ChatGateway(textArea);
	}
	
}

