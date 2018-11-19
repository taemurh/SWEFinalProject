package se2018.SWEFinalProject;

public class Story {
	private Integer storyPoints;
	private String author;
	private String desc;
	public Story(String author, String desc, int storyPoints) {
		this.storyPoints = storyPoints;
		this.author = author;
		this.desc = desc;
	}
	
	public void updateAuthor(String author) {
		this.author = author;
	}
	
	public void updateStoryPoints(int sp) {
		this.storyPoints = sp;
	}
	
	public void updateDescription(String desc) {
		this.desc = desc;
	}
}
