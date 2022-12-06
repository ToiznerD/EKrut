package server;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import DBHandler.Customer;
import DBHandler.DBController;
import ocsf.server.*;

public class EKServer extends AbstractServer {
	// Default port to listen
	final public static int DEFAULT_PORT = 5555;

	private static ServerController sc;

	public EKServer(int port, ServerController sc) {
		super(port);
		EKServer.sc = sc;

	}

	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgg = (String) msg;
		if (msgg.contains("UPDATE")) {
			Integer returnVal = DBController.runUpdate(msgg);
			sendToAllClients(returnVal);
		}

		else if (msgg.contains("SELECT")) {
			try {
				ResultSet rs = DBController.runQuery(msgg);
				ArrayList<Customer> customerArray = Customer.createCustomerArray(rs);
				sc.appendConsole("Sending list" + customerArray + "to " + client.getInetAddress().getHostAddress());
				sendToAllClients(customerArray);
			} catch (SQLException e) {
				sc.appendConsole(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				sc.appendConsole(e.getMessage());
				e.printStackTrace();
			}
		}
		else if (msgg.contains("disconnect")) {
			try {
				removeClient(client);
				sc.appendConsole("Client "+client.getInetAddress().getHostAddress()+" Disconnected from server");
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		sc.fillUserTableView(new clientConnectionData(client));
	}
	private void removeClient(ConnectionToClient client) {
		sc.removeUserFromTable(new clientConnectionData(client));
	}

	public static class clientConnectionData {

		private final String hostName;
		private final String ip;
		private final String status;

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
