package server;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import DBHandler.DBController;
import tasker.Tasker;
import Util.Msg;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * serverBackEnd class used for managing the connections in the system and handling/sending messages from/to clients.
 * It extends OCSF AbstractServer and overrides methods of it.
 */
public class serverBackEnd extends AbstractServer {
	// Default port to listen
	final public static int DEFAULT_PORT = 5555;
	private static ServerController sc;

	/**
	* Constructor for serverBackEnd class that creates a server with AbstractServer constructor.
	* @param port the port to listen on.
	* @param sc the ServerController object.
	*/
	public serverBackEnd(int port, ServerController sc) {
		super(port);
		serverBackEnd.sc = sc;
	}

	/**
	* Handles messages sent from clients by performing tasks based on the Msg that recieved.
	* It sends a Msg back to a client when tasks performed (depends on the task)
	* @param msg the Msg sent from client.
	* @param client the socket of the sender (ConnectionToClient)
	*/
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Msg taskMsg = (Msg) msg;
		switch (taskMsg.getTask()) {
		case Disconnect:
			UserManager.removeByClient(client); //remove client from UserManager map
			clientDisconnected(client);
			break;
		case popUp: //send a pop up to other client socket got from UserManager map
			try {
				UserManager.getClient(taskMsg.getDestinationID()).sendToClient(taskMsg);
			} catch (Exception e1) {
				//User not connected
			}
			break;
		default: //all others tasks
			try {
				Tasker.taskerHandler(taskMsg, client);
				sendMsg(client, taskMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	* Sends a Msg object to a client.
	* It appends a message to the server console (depends on the task).
	* @param client the socket of the sender (ConnectionToClient)
	* @param taskMsg the Msg object to send the client.
	* @throws IOException when sending Msg failed.
	*/
	protected void sendMsg(ConnectionToClient client, Msg taskMsg) throws IOException {
		client.sendToClient(taskMsg);
		if (taskMsg.getConsole() != null)
			sc.appendConsole(taskMsg.getConsole().replace("{ip}", client.getInetAddress().getHostAddress()));
	}

	/**
	* Adding the client IP address to the list of connected clients appends message to server console.
	* @param client the socket of the sender (ConnectionToClient).
	*/
	@Override
	protected void clientConnected(ConnectionToClient client) {
		sc.addConnected(client.getInetAddress());
		sc.appendConsole(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + ": " + "Client "
				+ client.getInetAddress().getHostAddress() + " is Connected to server. ");
	}

	/**
	* Removes the client IP address from the list of connected clients appends message to server console.
	* @param client the socket of the sender (ConnectionToClient).
	*/
	@Override
	protected void clientDisconnected(ConnectionToClient client) {
		sc.removeConnected(client.getInetAddress());
		sc.appendConsole(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + ": " + "Client "
				+ client.getInetAddress().getHostAddress() + " is Disconnected from the server. ");
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	* Calls Utility import method.
	* @return return true of import successed, else return false;
	*/
	protected boolean importUsers() {
		return DBController.importUsers();
	}
}
