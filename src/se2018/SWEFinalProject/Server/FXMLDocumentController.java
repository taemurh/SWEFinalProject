package se2018.SWEFinalProject.Server;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
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
    private Blackboard blackboard;
    private ServerSocket serverSocket;
    private final static String OUTPUT_FILE = "externalizable_file";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
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
          new Thread(new HandleAClient(socket, blackboard,textArea)).start();
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
    private Blackboard blackboard; // Reference to shared blackboard
    private TextArea textArea;
    private String handle;  
    private Story story;
    private final static String OUTPUT_FILE = "externalizable_file";
    public HandleAClient(Socket socket, Blackboard blackboard, TextArea textArea) {
      this.socket = socket;
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
              case SEND_STORY: {
            	  // send new story to server
            	  String storyJSON = inputFromClient.readLine();
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
            	  // get comment comment for a specific story
            	  Integer id = Integer.parseInt(inputFromClient.readLine());
            	  story = blackboard.getStory(id);
                  outputToClient.println(story.getSize()); // gets size of comment section
                  outputToClient.flush();
                  break;
              }
              case GET_COMMENT: {
            	  // get the nth comment in the story
                  String [] fields  = inputFromClient.readLine().split("-");
                  story = blackboard.getStory(Integer.parseInt(fields[0]));
                  String comment = story.getComment(Integer.parseInt(fields[1]));
                  outputToClient.println(comment);
                  outputToClient.flush();
                  break;
              }
              case GET_BURNDOWN: {
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
            	  Integer id = Integer.parseInt(inputFromClient.readLine());
            	  story = blackboard.getStory(id);
            	  String jsonStr = story.toString_();
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
            	  if (fields[1].equals("done")) {
            		  blackboard.completeStory(story.getStoryPoints());
            	  }
            	  break;
              }
              case UPDATE_STORY: {
            	  String storyJSON = inputFromClient.readLine();
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
            	  Integer id = Integer.parseInt(inputFromClient.readLine());
            	  blackboard.deleteStory(id);
            	  break;
              }
              case ADD_COMMENT: {
            	  String[] fields = inputFromClient.readLine().split("-");
            	  Story story = blackboard.getStory(Integer.parseInt(fields[0]));
            	  story.addComment(fields[1]);

              }
              case SAVE_STORIES: {
            	  FileOutputStream outputStream = new FileOutputStream(OUTPUT_FILE);
            	  ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            	  blackboard.writeExternal(objectOutputStream);
            	  objectOutputStream.flush();
            	  outputStream.close();
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
		e.printStackTrace();
	}
    }
  }
