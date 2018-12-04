package se2018.SWEFinalProject.Server;

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
	Hashtable<Integer, Sprint> sprints;
	
	public Blackboard() {
		productBacklog = new Hashtable <Integer, Story>();
		sprintBacklog = new Hashtable <Integer, Story>();
		stories = new Hashtable <Integer, Story>();
	}
	
	public synchronized void addStory(Story story) {
		System.out.println("story id in blackboard: " + story.getStoryID());
		stories.put(story.getStoryID(), story);
	}
	
	public synchronized void deleteStory(Integer storyId) {
		stories.remove(storyId);
	}
	
	public synchronized void editStory(Integer storyId, Story eStory) {
		Story oldStory = stories.get(storyId);
		oldStory.setAuthor(eStory.getAuthor());
		oldStory.setDesc(eStory.getDescription());
		oldStory.setStatus(eStory.getStatus());
		oldStory.setStoryPoints(eStory.getStoryPoints());
	}
	
	public synchronized Hashtable <Integer, Story> getStories() {
		return stories;
	}
	
	public synchronized Story getStory(Integer id) {
		System.out.println("in blackboard class: " +stories.get(id));

		return stories.get(id);
	}
}

