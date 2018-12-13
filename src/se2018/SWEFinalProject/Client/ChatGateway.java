package se2018.SWEFinalProject.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import se2018.SWEFinalProject.Server.Story;

public class ChatGateway implements se2018.SWEFinalProject.Chat.ChatConstants {

    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private TextArea textArea;

    // Establish the connection to the server.
    public ChatGateway(TextArea textArea) {
        this.textArea = textArea;
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an output stream to send data to the server
            outputToServer = new PrintWriter(socket.getOutputStream());

            // Create an input stream to read data from the server
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Exception in gateway constructor: " + ex.toString() + "\n"));
        }
    }

    // Start the chat by sending in the user's handle.
    public void sendHandle(String handle) {
        outputToServer.println(SEND_HANDLE);
        outputToServer.println(handle);
        outputToServer.flush();
    }

    // Send a new comment to the server.
    public void sendComment(String comment) {
        outputToServer.println(SEND_COMMENT);
        outputToServer.println(comment);
        outputToServer.flush();
    }
    
    // send a new user story to server
    public void sendStory(String story) {
    	outputToServer.println(SEND_STORY);
    	outputToServer.println(story);
    	outputToServer.flush();
    }
    
    public Story getStory(int id) {
    	outputToServer.println(GET_STORY);
    	outputToServer.println(Integer.toString(id));
    	outputToServer.flush();
    	String storyJSON = "";
    	try {
			storyJSON = inputFromServer.readLine();

		} catch (IOException e) {
			e.printStackTrace();
			storyJSON = "Error Fetching Story: " + e;

		}
    	
  	  	Story story = null;
  	  	String[] fields = storyJSON.split(",");
  	  	Integer storyID = Integer.parseInt(fields[0]);
  	  	String author = fields[1];
  	  	String title = fields[2];
  	  	String desc = fields[3];
  	  	String status = fields[4];
  	  	Integer storyPoints = Integer.parseInt(fields[5]);
  	  	story = new Story(storyID, author, title, desc,  storyPoints);
  	  	story.setStatus(status);
  	  	
    	return story;
    }
    
    // Get count of stories from server
    public int getStoryCount() {
        outputToServer.println(GET_STORY_COUNT);
        outputToServer.flush();
        int count = 0;
        try {
            count = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            // Platform.runLater(() -> textArea.appendText("Error in getStoryCount: " + ex.toString() + "\n"));
        	ex.printStackTrace();
        }
        return count;
    }

    // Ask the server to send us a count of how many comments are
    // currently in the transcript.
    public int getCommentCount() {
        outputToServer.println(GET_COMMENT_COUNT);
        outputToServer.flush();
        int count = 0;
        try {
            count = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getCommentCount: " + ex.toString() + "\n"));
        }
        return count;
    }

    // Fetch comment n of the transcript from the server.
    public String getComment(int n) {
        outputToServer.println(GET_COMMENT);
        outputToServer.println(n);
        outputToServer.flush();
        String comment = "";
        try {
            comment = inputFromServer.readLine();
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getComment: " + ex.toString() + "\n"));
        }
        return comment;
    }
    
    public void changeStoryStatus(String story) {
    	outputToServer.println(CHANGE_STORY_STATUS);
        outputToServer.println(story);
        outputToServer.flush();
    }
    
    public void updateStory(String story) {
    	outputToServer.println(UPDATE_STORY);
        outputToServer.println(story);
        outputToServer.flush();
    }
    
    public void deleteStory(int id) {
    	outputToServer.println(DELETE_STORY);
        outputToServer.println(id);
        outputToServer.flush();
    }
    
    public Hashtable<Integer, Integer> getBurndown() {
    	outputToServer.println(GET_BURNDOWN);
    	outputToServer.flush();
    	System.out.println("gateway burndown");

    	Hashtable<Integer, Integer> burndown = new Hashtable<Integer, Integer>();
    	String burndownString = "";
    	try {
    		burndownString = inputFromServer.readLine();
    		String [] entries = burndownString.split(",");
    		
    		for (String entry : entries) {
    			if (entry.contains(":")) {
    				String [] keyval = entry.split(":");
    				burndown.put(Integer.parseInt(keyval[0]), Integer.parseInt(keyval[1]));
    			} else {
    				continue;
    			}
    		}
    		
    	} catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in get burndwon: " + ex.toString() + "\n"));
    	}
    	return burndown;
    }
    

}