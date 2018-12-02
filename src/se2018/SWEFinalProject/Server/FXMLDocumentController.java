package se2018.SWEFinalProject.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import se2018.SWEFinalProject.Server.Blackboard;
import se2018.SWEFinalProject.Server.Story;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextArea textArea;
    
    private int clientNo = 0;
    private Transcript transcript;
    private Blackboard blackboard;
    
    private ServerSocket serverSocket;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      transcript = new Transcript();   
      blackboard = new Blackboard();
      new Thread( () -> {
      try {
        // Create a server socket
        serverSocket = new ServerSocket(8000);
        
        while (true) {
          // Listen for a new connection request
          Socket socket = serverSocket.accept();    
          // Increment clientNo
          clientNo++;
          
          Platform.runLater( () -> {
            // Display the client number
            textArea.appendText("Starting thread for client " + clientNo +
              " at " + new Date() + '\n');
            });
          
          // Create and start a new thread for the connection
          new Thread(new HandleAClient(socket,transcript,blackboard,textArea)).start();
          // new Thread(new HandleAClient(socket,transcript,,textArea)).start();
        }
      }
      catch(IOException ex) {
        System.err.println(ex);
      }
    }).start();
    }
    
}

class HandleAClient implements Runnable, se2018.SWEFinalProject.Chat.ChatConstants {
    private Socket socket; // A connected socket
    private Transcript transcript; // Reference to shared transcript
    private Blackboard blackboard; // Reference to shared blackboard
    private TextArea textArea;
    private String handle;

    public HandleAClient(Socket socket, Transcript transcript, Blackboard blackboard, TextArea textArea) {
      this.socket = socket;
      this.transcript = transcript;
      this.blackboard = blackboard;
      this.textArea = textArea;
    }

    public void run() {
      try {
        // Create reading and writing streams
        BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter outputToClient = new PrintWriter(socket.getOutputStream());

        // Continuously serve the client
        while (true) {
          // Receive request code from the client
          int request = Integer.parseInt(inputFromClient.readLine());
          // Process request
          switch(request) {
              case SEND_HANDLE: {
                  handle = inputFromClient.readLine();
                  break;
              }
              case SEND_COMMENT: {
                  String comment = inputFromClient.readLine();
                  transcript.addComment(handle + "> " + comment);
                  break;
              }
              case SEND_STORY: {
            	  String storyJSON = inputFromClient.readLine();
            	  ObjectMapper mapper = new ObjectMapper();
            	  Story s = mapper.readValue(storyJSON, Story.class);
            	  System.out.println("adding to balckboard");
            	  blackboard.addStory(s);
            	  break;
              }
              case GET_COMMENT_COUNT: {
                  outputToClient.println(transcript.getSize());
                  //outputToClient.println(blackboard.stories.size());
                  outputToClient.flush();
                  break;
              }
              case GET_COMMENT: {
                  int n = Integer.parseInt(inputFromClient.readLine());
                  outputToClient.println(transcript.getComment(n));
                  outputToClient.flush();
                  break;
              }
              case GET_STORY: {
            	  Integer id = Integer.parseInt(inputFromClient.readLine());
            	  Story story = blackboard.getStory(id);
            	  ObjectMapper mapper = new ObjectMapper();
            	  String jsonStr = mapper.writeValueAsString(story);
            	  outputToClient.println(jsonStr);
            	  outputToClient.flush();
            	  break;
              }
              
            
          }
        }
      }
      catch(IOException ex) {
          Platform.runLater(()->textArea.appendText("Exception in client thread: "+ex.toString()+"\n"));
      }
      try {
		socket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
  }
