package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.annotations.Results;

import com.mysql.cj.protocol.Resultset;

import DBHandler.DBController;
import ocsf.server.ConnectionToClient;

/**
 * UserManager is a class that manages the connected clients on server.
 * It is a static class with no constructor (created when server is up)
 * It saves the connections with HashMap: key-clientID, value-socket(ConnectionToClient)
 */
public class UserManager {

	private static HashMap<Integer, ConnectionToClient> map = new HashMap<>();

/**
 *addClient public static method is adding a connected client to the map and updates it in mysql
 *the "isLogged" column to 1.
 *@params id: client ID, client: relevant client socket (ConnectionToClient)
 */	
	public static void addClient(int id, ConnectionToClient client) {
		map.put(id, client);
		DBController.update("UPDATE users SET isLogged = 1 WHERE id = " + id);
	}
	
/**
 *addClient public static method is removing a connected client to the map and updates it in mysql
 *the "isLogged" column to 0.
 *@param id: client ID
 */	
	public static void removeClient(int id) {
		map.remove(id);
		DBController.update("UPDATE users SET isLogged = 0 WHERE id = " + id);
	}

/**
 *getEntries public static method for get the map data: keys and values
 * @return SetEntery of the map
 */	
	public static Set<Entry<Integer, ConnectionToClient>> getEntries() {
		return map.entrySet();
	}

/**
 *getClient public static method for getting the client socket
 *@param id: client ID
 *@return socket of the client (ConnectionToClient) or null client disconnected
 */	
	public static ConnectionToClient getClient(int id) {
		return map.get(id);
	}

/**
 *removeByClient public static method for removing from map with a value attribute(socket) if exist
 *@param client: socket(ConnectionToClient)
 */	
	public static void removeByClient(ConnectionToClient client) {
		for (Entry<Integer, ConnectionToClient> entry : map.entrySet()) {
			if (client.equals(entry.getValue())) {
				removeClient(entry.getKey());
				return;
			}
		}
	}
	
/**
 *isConnected public static method for checking if a client is connected.
 *It uses "SELECT" action for get "isLogged" value for the client ID
 *@param id: client ID
 *@return true: connected, false: disconnected
 */	
	public static boolean isConnected(int id) {
		ResultSet rs = DBController.select("SELECT isLogged FROM users WHERE id="+id);
		try {
			rs.first();
			return rs.getInt(1) == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
