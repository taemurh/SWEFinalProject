package se2018.SWEFinalProject.Server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

public class TestClientTest {
	@Test
	public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() {
	    TestClient client = new TestClient();
	    try {
			client.startConnection("127.0.0.1", 6666);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String response;
		try {
			response = client.sendMessage("hello server");
			assertEquals("hello client", response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
}
