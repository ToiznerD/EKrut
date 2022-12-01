package client;

import java.io.IOException;
import java.util.ArrayList;

import DBHandler.Customer;
import ocsf.client.AbstractClient;

public class ClientBackEnd extends AbstractClient {
	private static ClientController clientController;

	public ClientBackEnd(String host, int port, ClientController clientController) throws IOException {
		super(host, port);
		this.clientController = clientController;
		openConnection();
	}
	
	@Override
	protected void handleMessageFromServer(Object msg) {
		ArrayList<Customer> CustomerArr = Customer.CreateCustomer2DArr((ArrayList<ArrayList<Object>>)msg);
		clientController.fillUserTableView(CustomerArr);
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
