package se2018.SWEFinalProject.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
 
    public void startConnection(String ip, int port) throws UnknownHostException, IOException {
        try {
        	clientSocket = new Socket(ip, port);
        	out = new PrintWriter(clientSocket.getOutputStream(), true);
        	in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
    		System.err.println(e);
    	}
    }
 
    public String sendMessage(String msg) throws IOException {
    	try {
    		out.println(msg);
        	String resp = in.readLine();
        	return resp;
    	} catch (IOException e) {
    		System.err.println(e);
    		return "error";
    		// idk why i need to return string here
    	}
    }
 
    public void stopConnection() throws IOException {
    	try {
	        in.close();
	        out.close();
	        clientSocket.close();
    	} catch (IOException e) {
    		System.err.println(e);
    	}
    }
}

/*
public class TestClient {
	
	PrintWriter outputToServer;
	textArea = new JTextArea(20, 20);
	
	public TestClient(TextArea textArea) {
		//this.textArea = textArea;
		try {
			Socket socket = new Socket("localhost", 8000);
			outputToServer = new PrintWriter(socket.getOutputStream());
			BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException ex) {
			Platform.runLater(() -> textArea.appendText("Exception"));
		}
	}
	
	public void sendHandle(String handle) {
		outputToServer.println(handle);
		outputToServer.flush();
	}
	
	public void sendComment(String comment) {
		outputToServer.println(comment);
		outputToServer.flush();
	}
	
	
}
*/