package client;

import java.io.IOException;
import java.util.ArrayList;
import DBHandler.Customer;
import clientControllers.ClientController;
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
		if (msg instanceof Integer) {
			Integer msgg = (Integer) msg;
			if (msgg.intValue() == 0) {
				clientController.appendConsole("Update error");
			} else {
				clientController.appendConsole("Update was done successfully");
			}
		} else {
			@SuppressWarnings("unchecked")
			ArrayList<Customer> CustomerArr = (ArrayList<Customer>) msg; // New
			clientController.fillUserTableView(CustomerArr);
		}
	}

	public void handleMessageFromClientUI(Object message) throws IOException {
		sendToServer(message);
	}

	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}
