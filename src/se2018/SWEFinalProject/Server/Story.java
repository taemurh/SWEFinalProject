package se2018.SWEFinalProject.Server;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Story {
	private Integer storyID;
	private String author;
	private String title;
	private String description;
	private String status = "not started";
	private Integer storyPoints;
	private List<String> transcript = Collections.synchronizedList(new ArrayList<String>());
	
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
	
	
	public synchronized void addComment(String comment) { 
		transcript.add(comment); 
	}
	
    public synchronized int getSize() { 
    	return transcript.size(); 
    }
    
    public synchronized String getComment(int n) { 
    	return transcript.get(n); 
    }
    
    public synchronized List<String> getChat() { 
    	return transcript; 
    }

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
		String chat = "";
		for (String comment: transcript) {
			chat = comment + "-";
		}
        return (Integer.toString(storyID) + ","
        		+ author + "," +  
                title + "," +  
               description + "," +
               status + "," +
               Integer.toString(storyPoints) + ","
               + chat); 
    }
	
	
}
