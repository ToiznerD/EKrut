package client;

import java.io.IOException;

import Util.Msg;
import Util.Tasks;
import controllers.AbstractController;
import ocsf.client.AbstractClient;

/**
 * ClientBackEnd class serves as back end manager for client side in the program.
 * It handles communication with the server.
 * It extends OCSF AbstractClient.
 */
public class ClientBackEnd extends AbstractClient {

	private static AbstractController abstractController;
	private static ClientBackEnd instance;

	/**
	* @return the abstract controller.
	*/
	public static AbstractController getAbstractController() {
		return abstractController;
	}

	/**
	* Sets the abstract controller.
	* @param abstractController the abstract controller to set
	*/
	public static void setAbstractController(AbstractController abstractController) {
		ClientBackEnd.abstractController = abstractController;
	}

	/**
	* Constructor for the ClientBackEnd class that creates connection for a client.
	* @param host the host to connect
	* @param port the port to connect
	* @throws IOException when error occurs on connecting to the server
	*/
	private ClientBackEnd(String host, int port) throws IOException {
		super(host, port);
		openConnection();
	}

	/**
	* Initialize a new client back (with class constructor) end if not exist.
	* @param host the host to connect
	* @param port the port to connect
	* @throws IOException when error occurs on connecting to the server
	*/
	public static void initServer(String host, int port) throws IOException {
		if (instance == null)
			instance = new ClientBackEnd(host, port);
	}

	/**
	* @return the instance of the ClientBackEnd
	*/
	public static ClientBackEnd getInstance() {
		return instance;
	}

	/**
	* Overrides AbstractClient method.
	* Handle a message received from the server by setting it abstract controller msg and notifies it.
	* @param msg the message received from the server
	*/
	@Override
	protected void handleMessageFromServer(Object msg) {
		Msg taskMsg = (Msg) msg;
		if(taskMsg.getTask()==Tasks.popUp) { //When Msg task is pop-up call waitForAlert method and return
			AbstractController.waitForAlert(taskMsg.getAlertMsg());
			return;
		}
		AbstractController.msg = taskMsg;
		AbstractController.Notify();
	}

	/**
	* Sends to server the message received from client user interface
	* @param message the message object received from the client UI
	*/
	public void handleMessageFromClientUI(Object message) throws IOException {
		sendToServer(message);
	}

	/**
	* Close the connection of the client.
	* @throws IOException if error occured when sending Msg to server
	*/
	public void quit() {
		try {
			handleMessageFromClientUI(new Msg(Tasks.Disconnect));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

