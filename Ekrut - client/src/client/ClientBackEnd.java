package client;

import java.io.IOException;
import java.util.ArrayList;

import controllers.AbstractController;
import controllers.LoginController;
import controllers.ResupplyReqController;
import ocsf.client.AbstractClient;
import tables.TableProd;
import Util.Tasks;

public class ClientBackEnd extends AbstractClient {

	private static AbstractController abstractController;
	private static ClientBackEnd instance;

	public static AbstractController getAbstractController() {
		return abstractController;
	}

	public static void setAbstractController(AbstractController abstractController) {
		ClientBackEnd.abstractController = abstractController;
	}

	private ClientBackEnd(String host, int port) throws IOException {
		super(host, port);
		openConnection();
	}

	public static void initServer(String host, int port) throws IOException {
		if (instance == null)
			instance = new ClientBackEnd(host, port);
	}

	public static ClientBackEnd getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) {
		ArrayList<Object> returnMsg = (ArrayList<Object>) msg;
		switch ((Tasks) returnMsg.get(0)) {
		case Login:
			loginHandlers(returnMsg);
			break;
		case RequiredStock:
			ResupplyReqController.tprod = (ArrayList<TableProd>) returnMsg.get(2);
			break;
		case Update:
			ResupplyReqController.updateResult = (int) returnMsg.get(2);
			break;
		default:
			break;
		}
		AbstractController.Notify();
	}

	/*
	 * This method is the logic layer for handling the login page
	 * The method will update LoginController (login page UI) about the msg returned from the server
	 * @param msg
	 */
	private void loginHandlers(ArrayList<Object> msg) {
		if (msg.get(2) != null) {//Nave
			LoginController.result = true;
			LoginController.role = (String) msg.get(2); //Nave
		} else {
			LoginController.result = false;
		}
	}

	public void handleMessageFromClientUI(Object message) throws IOException {
		sendToServer(message);
	}

	public void quit() {
		try {
			sendToServer(Tasks.Disconnect);
			closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}
