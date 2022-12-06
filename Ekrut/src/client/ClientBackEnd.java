package client;

import java.io.IOException;
import java.util.ArrayList;
import DBHandler.Customer;
import ocsf.client.AbstractClient;

public class ClientBackEnd extends AbstractClient {
	private static ClientController clientController;
	
	public ClientBackEnd(String host, int port, ClientController clientController) throws IOException {
		super(host, port);
		ClientBackEnd.clientController = clientController;
		openConnection();
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		//Return message for update
		if (msg instanceof Integer) {
			Integer msgg = (Integer) msg;
			if (msgg == 0) {
				clientController.appendConsole("Update error");
			} else {
				clientController.appendConsole("Update was done successfully");
			}
		} else {
			//Return message for select
			@SuppressWarnings("unchecked")
			ArrayList<Customer> CustomerArr = (ArrayList<Customer>) msg; // New
			clientController.fillUserTableView(CustomerArr);
		}
	}

	public void handleMessageFromClientUI(Object message) throws IOException {
		sendToServer(message);
	}

	@SuppressWarnings("finally")
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			System.exit(0);
		}
	}

}
