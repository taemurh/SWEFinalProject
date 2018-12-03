package se2018.SWEFinalProject.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Story {
	private Integer storyID;
	private String author;
	private String desc;
	private String status;
	private Integer storyPoints;
	private List<String> transcript = Collections.synchronizedList(new ArrayList<String>());
	private Integer sprint;

	public Story(Integer storyID, String author, String desc, String status, int storyPoints, int sprint) {
		this.setStoryID(storyID);
		this.author = author;
		this.desc = desc;
		this.status = status;
		this.storyPoints = storyPoints;
		this.sprint = sprint;
	}
	
	public synchronized void updateAuthor(String author) {
		this.author = author;
	}
	
	public synchronized void updateStoryPoints(int sp) {
		this.storyPoints = sp;
	}
	
	public synchronized void updateDescription(String desc) {
		this.desc = desc;
	}
	
	public synchronized String getAuthor() {
		return author;
	}
	
	public synchronized int getStoryPoints() {
		return storyPoints;
	}
	
	public synchronized String getDescription() {
		return desc;
	}
	
	public synchronized String getStatus() {
		return status;
	}

	public synchronized void setAuthor(String author) {
		this.author = author;
	}

	public synchronized void setDesc(String description) {
		this.desc = description;
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
	
    public int getSize() { 
    	return transcript.size(); 
    }
    
    public String getComment(int n) { 
    	return transcript.get(n); 
    }

	public Integer getStoryID() {
		return storyID;
	}

	public void setStoryID(Integer storyID) {
		this.storyID = storyID;
	}
	
	public Integer getSprint() {
		return sprint;
	}

	public void setSprint(Integer sprint) {
		this.sprint = sprint;
	}
	
	
}
