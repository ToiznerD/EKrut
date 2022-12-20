package server;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import DBHandler.DBController;
import Util.Tasks;
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
		ArrayList<Object> task = (ArrayList<Object>)msg;
		
		switch((Tasks)task.get(0)) {
			case Login:
				loginTask(client, task);
				break;
			case Disconnect:
				clientDisconnected(client);
				break;
			default:
				break;
		}
	}

	/*
	 * This method is responsible to handle with tasks related to login page
	 * @params client, task
	 */
	private void loginTask(ConnectionToClient client, ArrayList<Object> task) {
		
		//Run query
		ResultSet rs = DBController.select((String)task.get(1));
		
		try {
			if(rs.next()) { //Login details found
				ArrayList<Object> returnMsg = new ArrayList<>();
				returnMsg.add(Tasks.Login);
				returnMsg.add(true);
				returnMsg.add(rs.getString(4));
				sendMsg(client, returnMsg, "Sending positive login check to {ip}");
			}
			else { //Login details not found
				ArrayList<Object> returnMsg = new ArrayList<>();
				returnMsg.add(Tasks.Login);
				returnMsg.add(false);
				sendMsg(client, returnMsg, "Sending negative login check to {ip}");
			}
		} catch (SQLException e) {
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
	
	/*
	@Override
	protected void clientConnected(ConnectionToClient client) {
		sc.fillUserTableView(new clientConnectionData(client));
	}

	@Override
	protected void clientDisconnected(ConnectionToClient client) {
		sc.removeUserFromTable(new clientConnectionData(client));
		sc.appendConsole("Client " + client.getInetAddress().getHostAddress() + " is disconnected from server.");
	}*/

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
