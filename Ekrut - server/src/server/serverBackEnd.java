package server;

import java.io.IOException;
import java.util.Objects;

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
		try {
			taskMsg.taskerHandler();
			client.sendToClient(taskMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	protected void sendMsg(ConnectionToClient client, Object response, String consoleMsg) {
		if (response != null)
			try {
				client.sendToClient(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (consoleMsg != null)
			sc.appendConsole(consoleMsg.replace("{ip}", client.getInetAddress().getHostAddress()));
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		sc.fillUserTableView(new clientConnectionData(client));
		sc.appendConsole("Client " + client.getInetAddress().getHostAddress() + " is Connected to server.");
	}

	@Override
	protected void clientDisconnected(ConnectionToClient client) {
		sc.removeUserFromTable(new clientConnectionData(client));
		sc.appendConsole("Client " + client.getInetAddress().getHostAddress() + " is disconnected from server.");
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class clientConnectionData {

		private String hostName;
		private String ip;
		private String status;

		public clientConnectionData(ConnectionToClient client) {
			this.hostName = client.getInetAddress().getHostName();
			this.ip = client.getInetAddress().getHostAddress();
			this.status = "connected";
		}

		@Override
		public boolean equals(Object obj) {
			if (getClass() != obj.getClass())
				return false;
			clientConnectionData other = (clientConnectionData) obj;
			return Objects.equals(ip, other.ip);
		}

		public String getHostName() {
			return hostName;
		}

		public String getIp() {
			return ip;
		}

		public String getStatus() {
			return status;
		}

	}
}
