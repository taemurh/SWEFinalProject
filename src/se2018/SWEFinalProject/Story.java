package se2018.SWEFinalProject;

public class Story {
	private Integer storyID;
	private Integer storyPoints;
	private String author;
	private String desc;
	private String status;
	
	public Story(Integer storyID, String author, String desc, String status, int storyPoints) {
		this.storyID = storyID;
		this.author = author;
		this.desc = desc;
		this.status = status;
		this.storyPoints = storyPoints;
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
	
	public String getAuthor() {
		return author;
	}
	
	public int getStoryPoints() {
		return storyPoints;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public String getStatus() {
		return status;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setDesc(String description) {
		this.desc = description;
	}

	public void setStatus(String status) {
		this.status = status;
		
	}

	public void setStoryPoints(int storyPoints) {
		this.storyPoints = storyPoints;
	}
	
	
}
