package se2018.SWEFinalProject.Server;
import org.junit.Test;
public class BlackBoardTest {
	@Test
	// Test to check the critical black board methods    
	public void testBlackBoard() {
		Blackboard myBB= new Blackboard();
		Story testStory = new Story(0, "test_master", "test story", "do unit test on story class",3);
		Story testStory2 = new Story(1, "test_noob", "test story again", "do unit test on story class again",4);

		
		// Test to check if the number of stories is 0 at the start
		assert(myBB.totStories == 0);
		// Add one story
		myBB.addStory(testStory);
		// Test to check that the number of stories has changed
		assert(myBB.totStories == 1);
		// Test to check if we can get stories by ID
		assert(myBB.getStory(0) == testStory);
		
		// Add a second unique story
		myBB.addStory(testStory2);
		// Test to check if number of stories has gone up
		assert(myBB.totStories == 2);
		// Test to check if second story can be retrieved by ID
		assert(myBB.getStory(1) == testStory2);
		


		
		
	}

}
