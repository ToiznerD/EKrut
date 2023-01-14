package server;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import DBHandler.DBController;
import ocsf.server.ConnectionToClient;

public class UserManager {

	private static HashMap<Integer, ConnectionToClient> map = new HashMap<>();

	public static void addClient(int id, ConnectionToClient client) {
		map.put(id, client);
		DBController.update("UPDATE users SET isLogged = 1 WHERE id = " + id);
	}

	public static void removeClient(int id) {
		map.remove(id);
		DBController.update("UPDATE users SET isLogged = 0 WHERE id = " + id);
	}

	public static Set<Entry<Integer, ConnectionToClient>> getEntries() {
		return map.entrySet();
	}

	// returns null if no client id
	public static ConnectionToClient getClient(int id) {
		return map.get(id);
	}

	// reutnr -1 if no socket
	public static void removeByClient(ConnectionToClient client) {
		for (Entry<Integer, ConnectionToClient> entry : map.entrySet()) {
			if (client.equals(entry.getValue())) {
				removeClient(entry.getKey());
				return;
			}
		}
	}

	public static boolean isConnected(int id) {
		return map.containsKey(id);
	}
}
