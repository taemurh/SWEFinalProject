package se2018.SWEFinalProject.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.Hashtable;
import java.util.ResourceBundle;


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
import se2018.SWEFinalProject.Server.Transcript;

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
    private Story story;
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
            	  //System.out.println("send story");
            	  String storyJSON = inputFromClient.readLine();
            	  //System.out.println("story reached server" + storyJSON);
            	  story = null;
            	  try {
            		  String[] fields = storyJSON.split(",");
            		  Integer storyID = Integer.parseInt(fields[0]);
            		  String author = fields[1];
            		  String title = fields[2];
            		  String desc = fields[3];
            		  String status = fields[4];
            		  Integer storyPoints = Integer.parseInt(fields[5]);
            		  story = new Story(storyID, author, title, desc,  storyPoints);
            		  story.setStatus(status);

            	  } catch (Exception e) {
            		  System.out.println(e);
            	  }
            	  
            	  blackboard.addStory(story);
            	  if (story == null) {
            		  System.out.println("story is null");
            	  }
            	  break;
              }
              case GET_COMMENT_COUNT: {
                  outputToClient.println(transcript.getSize());
                  outputToClient.flush();
                  break;
              }
              case GET_COMMENT: {
                  int n = Integer.parseInt(inputFromClient.readLine());
                  outputToClient.println(transcript.getComment(n));
                  outputToClient.flush();
                  break;
              }
              case GET_BURNDOWN: {
            	  // System.out.println("server burndown");
            	  Hashtable<Integer, Integer> burndown = blackboard.getBurndown();
            	  String serialBurndown = "";
            	  for (Integer key : burndown.keySet()) {
            		  serialBurndown = serialBurndown + Integer.toString(key) + ":" + burndown.get(key) + ",";
            	  }
            	  outputToClient.println(serialBurndown);
            	  outputToClient.flush();
            	  break;
              }
              case GET_STORY: {
            	  // System.out.println("get story");
            	  Integer id = Integer.parseInt(inputFromClient.readLine());
            	  // System.out.println(">>>> " + id);
            	  story = blackboard.getStory(id);
            	  String jsonStr = story.toString_();
            	  // System.out.println(jsonStr);
            	  outputToClient.println(jsonStr);
            	  outputToClient.flush();
            	  break;
              }
              case GET_STORY_COUNT: {
            	  outputToClient.println(blackboard.stories.size());
            	  outputToClient.flush();
            	  break;
              }
              case CHANGE_STORY_STATUS: {
            	  String[] fields = inputFromClient.readLine().split("-");
            	  Story story = blackboard.getStory(Integer.parseInt(fields[0]));
            	  story.setStatus(fields[1]);
            	  // System.out.println(story.getStoryPoints());
            	  if (fields[1].equals("done")) {
            		  blackboard.completeStory(story.getStoryPoints());
            	  }
            	  break;
              }
              case UPDATE_STORY: {
            	  // System.out.println("updating story status");
            	  // System.out.println("send story");
            	  String storyJSON = inputFromClient.readLine();
            	  // System.out.println("story reached server" + storyJSON);
//            	  story = null;
            	  try {
            		  String[] fields = storyJSON.split(",");
            		  Integer storyID = Integer.parseInt(fields[0]);
            		  String author = fields[1];
            		  String title = fields[2];
            		  String desc = fields[3];
            		  String status = fields[4];
            		  Integer storyPoints = Integer.parseInt(fields[5]);
            		  story.setAuthor(author);
            		  story.setDesc(desc);
            		  story.setTitle(title);
            		  story.setStatus(status);
            		  story.setStoryPoints(storyPoints);
            		  // System.out.println("in server: " + story.getAuthor());
            		  blackboard.editStory(storyID, story);

            	  } catch (Exception e) {
            		  System.out.println(e);
            	  }
            	  
            	  
            	  if (story == null) {
            		  System.out.println("story is null");
            	  }
            	  break;
              }
              case DELETE_STORY: {
            	  // System.out.println("delete story");
            	  Integer id = Integer.parseInt(inputFromClient.readLine());
            	  blackboard.deleteStory(id);
            	  break;
              }
              case ADD_COMMENT: {
            	  String[] fields = inputFromClient.readLine().split("-");
            	  Story story = blackboard.getStory(Integer.parseInt(fields[0]));
            	  story.addComment(fields[1]);
            	  System.out.println("added comment server");
            	  for(int i = 0; i < story.getSize(); i++) {
            		  System.out.println("comment server: " + story.getComment(i));
            	  }
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
