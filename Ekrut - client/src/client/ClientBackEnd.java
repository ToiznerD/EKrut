package client;

import java.io.IOException;
import java.util.ArrayList;

import controllers.AbstractController;
import controllers.LoginController;
import ocsf.client.AbstractClient;
import Util.Tasks;

public class ClientBackEnd extends AbstractClient {

	private static AbstractController abstractController;
	

	public static AbstractController getAbstractController() {
		return abstractController;
	}

	public static void setAbstractController(AbstractController abstractController) {
		ClientBackEnd.abstractController = abstractController;
	}

	public ClientBackEnd(String host, int port) throws IOException {
		super(host, port);
		openConnection();
	}
	
	@Override
	protected void handleMessageFromServer(Object msg) {
		ArrayList<Object> returnMsg = (ArrayList<Object>)msg;

		//Check type of msg
		switch((Tasks) returnMsg.get(0)) {
			case Login:
				loginHandlers(returnMsg);
				break;
			default:
				break;
		}
	}
	
	/*
	 * This method is the logic layer for handling the login page
	 * The method will update LoginController (login page UI) about the msg returned from the server
	 * @param msg
	 */
	private void loginHandlers(ArrayList<Object> msg) {
		if((Boolean)msg.get(1)) {
			LoginController.result = true;
			LoginController.role = (String)msg.get(2);
		}
		else {
			LoginController.result = false;
		}
		synchronized(LoginController.monitor) {
			LoginController.monitor.notifyAll();
		}
	}

	public void handleMessageFromClientUI(Object message) throws IOException {
		sendToServer(message);
	}

	public void quit() {
		try {
			sendToServer("disconnect");
			closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}
