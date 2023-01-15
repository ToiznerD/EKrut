package server;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import DBHandler.DBController;
import tasker.Tasker;
import Util.Msg;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class serverBackEnd extends AbstractServer {
	// Default port to listen
	final public static int DEFAULT_PORT = 5555;
	private static ServerController sc;

	public serverBackEnd(int port, ServerController sc) {
		super(port);
		serverBackEnd.sc = sc;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Msg taskMsg = (Msg) msg;
		switch (taskMsg.getTask()) {
		case Disconnect:
			UserManager.removeByClient(client);
			clientDisconnected(client);
			break;
		case popUp:
			try {
				UserManager.getClient(taskMsg.getDestinationID()).sendToClient(taskMsg);
			} catch (Exception e1) {
				//User not connected
			}
			break;
		default:
			try {
				Tasker.taskerHandler(taskMsg, client);
				sendMsg(client, taskMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	protected void sendMsg(ConnectionToClient client, Msg taskMsg) throws IOException {
		client.sendToClient(taskMsg);
		if (taskMsg.getConsole() != null)
			sc.appendConsole(taskMsg.getConsole().replace("{ip}", client.getInetAddress().getHostAddress()));
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		sc.addConnected(client.getInetAddress());
		sc.appendConsole(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + ": " + "Client "
				+ client.getInetAddress().getHostAddress() + " is Connected to server. ");
	}

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

	protected boolean importUsers() {
		return DBController.importUsers();
	}
}
