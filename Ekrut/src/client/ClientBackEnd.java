package client;

import java.io.IOException;

import ocsf.client.AbstractClient;

public class ClientBackEnd extends AbstractClient {

	public ClientBackEnd(String host, int port) throws IOException {
		super(host, port);
		openConnection();
	}
	
	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println(msg);
		
	}
	
	public void handleMessageFromClientUI(Object message) throws IOException {
		sendToServer(message);
	}
	
	public void quit()
	  {
	    try
	    {
	      closeConnection();
	    }
	    catch(IOException e) {}
	    System.exit(0);
	  }
	}
