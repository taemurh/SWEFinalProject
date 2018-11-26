package se2018.SWEFinalProject;

import java.util.HashMap;

public class Blackboard {
	
	/* 
	 * Int Story Id : new  Story()
	 * 
	 * 
	 */
	
	Integer totStories = 0;
	HashMap<Integer, Story> productBacklog;
	HashMap<Integer, Story> sprintBacklog;
	HashMap<Integer, Story> stories;
	
	public void addStory() {
		stories.put(totStories++, new Story(null, null, null, null, 0));
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
}

