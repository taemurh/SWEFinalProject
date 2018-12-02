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
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    private ChatGateway gateway;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField comment;
    
    @FXML TextField authorField;
           
    
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
    	System.out.println("hey it works");
    	System.out.println(authorField.getText());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gateway = new ChatGateway(textArea);

        // Put up a dialog to get a handle from the user
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Start Chat");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter a handle:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> gateway.sendHandle(name));

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
          if(gateway.getCommentCount() > N) {
              String newComment = gateway.getComment(N);
              Platform.runLater(()->textArea.appendText(newComment + "\n"));
              N++;
          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }
      }
    }
  }
