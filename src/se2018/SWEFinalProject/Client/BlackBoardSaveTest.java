package se2018.SWEFinalProject.Client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import se2018.SWEFinalProject.Server.Blackboard;
import se2018.SWEFinalProject.Server.Story;

public class BlackBoardSaveTest {
	private final static String OUTPUT_FILE = "externalizable_file";
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		//save to file
		Blackboard bb = new Blackboard();
		bb.addStory(new Story(0, "author", "title", "description", 10));
		bb.addStory(new Story(1, "author1", "title1", "description1", 12));
		FileOutputStream outputStream = new FileOutputStream(OUTPUT_FILE);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		bb.writeExternal(objectOutputStream);
		objectOutputStream.flush();
		outputStream.close();
		
		// get from file
		Blackboard bb1 = new Blackboard();
		FileInputStream inputStream = new FileInputStream(OUTPUT_FILE);
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		bb1.readExternal(objectInputStream);
		objectInputStream.close();
		inputStream.close();
		
		System.out.println(bb1.getStory(0));
		System.out.println(bb1.getStory(1));

	}

}
