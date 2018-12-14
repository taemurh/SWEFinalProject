package se2018.SWEFinalProject.Server;
import org.junit.Test;

public class StoryTest{
	@Test
	// Test to validate the get methods of the story class
	public void testStoryGet() {
	   Story myStory = new Story(0, "test_master", "test story", "do unit test on story class",3);
	   // Test if Story ID properly set
	   assert(myStory.getStoryID()==0); 
	   // Test if Story Author properly set
	   assert(myStory.getAuthor()=="test_master");
	   // Test if Story Title properly set
	   assert(myStory.getTitle()=="test story");
	   // Test if Story Description properly set
	   assert(myStory.getDescription()=="do unit test on story class");
	   // Test if Story Points properly set
	   assert(myStory.getStoryPoints()==3); 
	}
	@Test
	public void testStorySet() {
		   Story myStory = new Story(0, "test_master", "test story", "do unit test on story class",3);
		   
		   // Test if Story ID properly changed
		   assert(myStory.getStoryID()==0);
		   myStory.setStoryID(1);
		   assert(myStory.getStoryID()==1); 
		   // Test if Story Author properly changed
		   assert(myStory.getAuthor()=="test_master");
		   myStory.updateAuthor("test_noob");
		   assert(myStory.getAuthor()=="test_noob");
		   // Test if Story Title properly changed
		   assert(myStory.getTitle()=="test story");
		   myStory.setTitle("test story again");
		   assert(myStory.getTitle()=="test story again");
		   // Test if Story Description properly changed
		   assert(myStory.getDescription()=="do unit test on story class");
		   myStory.setDesc("do unit test on story class again");
		   assert(myStory.getDescription()=="do unit test on story class again");
		   // Test if Story Points properly changed
		   assert(myStory.getStoryPoints()==3); 
		   myStory.setStoryPoints(4);
		   assert(myStory.getStoryPoints()==4); 
		}
}