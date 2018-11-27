package se2018.SWEFinalProject;

import java.util.HashMap;
import java.util.Hashtable;

public class Blackboard {
	
	/* 
	 * Int Story Id : new  Story()
	 * 
	 * 
	 */
	
	Integer totStories = 0;
	Hashtable<Integer, Story> productBacklog;
	Hashtable<Integer, Story> sprintBacklog;
	Hashtable<Integer, Story> stories;
	
	public Blackboard() {
		productBacklog = new Hashtable <Integer, Story>();
		sprintBacklog = new Hashtable <Integer, Story>();
		stories = new Hashtable <Integer, Story>();
	}
	
	public void addStory() {
		//stories.put(totStories++, new Story(null, null, null, null, 0));
		stories.put(totStories++, new Story(0, "a", "b", "c", 0));
	}
	
	public void deleteStory(Integer storyId) {
		stories.remove(storyId);
	}
	
	public void editStory(Integer storyId, Story eStory) {
		Story oldStory = stories.get(storyId);
		oldStory.setAuthor(eStory.getAuthor());
		oldStory.setDesc(eStory.getDescription());
		oldStory.setStatus(eStory.getStatus());
		oldStory.setStoryPoints(eStory.getStoryPoints());
	}
	
	public Hashtable <Integer, Story> getStories() {
		return stories;
	}
	
	public Story getStory(Integer id) {
		return stories.get(id);
	}
}

