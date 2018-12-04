package se2018.SWEFinalProject.Server;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Story {
	//@JsonRawValue
	private Integer storyID;
	//@JsonRawValue
	private String author;
	//@JsonRawValue
	private String title;
	//@JsonRawValue
	private String description;
	//@JsonRawValue
	private String status = "not started";
	//@JsonRawValue
	private Integer storyPoints;
	//private List<String> transcript = Collections.synchronizedList(new ArrayList<String>());
	
	public Story(Integer storyID, String author, String title, String description, int storyPoints) {
		this.storyID = storyID;
		this.author = author;
		this.title = title;
		this.description = description;
		this.storyPoints = storyPoints;
	}
	
	public synchronized void updateAuthor(String author) {
		this.author = author;
	}
	
	public synchronized void updateStoryPoints(int sp) {
		this.storyPoints = sp;
	}
	
	public synchronized void updateDescription(String description) {
		this.description = description;
	}
	
	public synchronized String getAuthor() {
		return author;
	}
	
	public synchronized int getStoryPoints() {
		return storyPoints;
	}
	
	public synchronized String getDescription() {
		return description;
	}
	
	public synchronized String getStatus() {
		return status;
	}

	public synchronized void setAuthor(String author) {
		this.author = author;
	}

	public synchronized void setDesc(String description) {
		this.description = description;
	}

	public synchronized void setStatus(String status) {
		this.status = status;
	}

	public synchronized void setStoryPoints(int storyPoints) {
		this.storyPoints = storyPoints;
	}
	
	/*
	public synchronized void addComment(String comment) { 
		transcript.add(comment); 
	}
	
    public synchronized int getSize() { 
    	return transcript.size(); 
    }
    
    public synchronized String getComment(int n) { 
    	return transcript.get(n); 
    }
    */

	public synchronized Integer getStoryID() {
		return storyID;
	}

	public synchronized void setStoryID(Integer storyID) {
		this.storyID = storyID;
	}
	
	public synchronized String getTitle() {
		return title;
	}

	public synchronized void setTitle(String title) {
		this.title = title;
	}
	
	 
	public String toString_() 
    { 
        return (Integer.toString(storyID) + ","
        		+ author + "," +  
                title + "," +  
               description + "," +
               status + "," +
               Integer.toString(storyPoints)); 
    }
	
	
}
