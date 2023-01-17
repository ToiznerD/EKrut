package tasker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DBHandler.DBController;
import Util.Msg;
import ocsf.server.ConnectionToClient;
import server.UserManager;

/**
 * Tasker class used to manage the different tasks in the system. 
 * It handles 3 Tasks for DB actions: Select, Update, Insert.
 * It handles special tasks performed in system: Login, Logout, Order.
 * It sets messages on server console when user Login and Logout with current time.
 */
public class Tasker {
	private static String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
	private static String loginMsg = time + ": <Login>: Client {ip} {result} logged in";
	private static String logoutMsg = time + ": <Logout>: Client {ip} logged out";

	/**
	 * Handles the specific task passed with Msg object.
	 * @param msg the message object containing the task and necessary data for the task
	 * @param client socket of client (ConnectionToClient)
	 * @throws SQLException on error while perform actions with mysql DB
	 */
	public static void taskerHandler(Msg msg, ConnectionToClient client) throws SQLException {
		switch (msg.getTask()) {
		case Select:
			runSelect(msg);
			break;
		case Update:
		case Insert:
			runUpdate(msg);
			break;
		case Login:
			runSelect(msg);
			if (msg.getBool()) { 
				if (!UserManager.isConnected(msg.getObj(0))) { //client not connected - add it to UserManager map
					int id = msg.getObj(0);
					UserManager.addClient(id, client);
				} else { //client already connected
					msg.setBool(false);
					msg.setResponse("User already logged in");
				}
			} else //details mismatch
				msg.setResponse("Wrong details");
			String consoleMsg = msg.getBool() ? loginMsg.replace("{result}", "sucsessfully")
					: loginMsg.replace("{result}", "faild to");
			msg.setConsole(consoleMsg);
			break;
		case Logout:
			UserManager.removeByClient(client); //remove client from UserManager map
			msg.setConsole(logoutMsg);
			break;
		case Order:
			OrderHandler.createOrder(msg);
		default:
			break;
		}
	}

	/**
	* Runs an update with the query passed with Msg object.
	* The return value is stored in Msg object - indicates if the action successed
	* @param msg the Msg object that contains the update query
	*/
	public static void runUpdate(Msg msg) {
		int returnVal = DBController.update(msg.getQuery());
		msg.setInt(returnVal);
		msg.setBool(returnVal > 0 ? true : false);
	}


	/**
	* Runs a select with the query passed with Msg object.
	* The return value is stored in Msg object List with desired values from DB
	* @param msg the Msg object that contains the select query
	* @throws SQLException on error while perform actions with mysql DB
	*/
	public static void runSelect(Msg msg) throws SQLException {
		ResultSet rs = DBController.select(msg.getQuery());
		int columnCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			List<Object> row = new ArrayList<>();
			for (int i = 1; i <= columnCount; i++) {
				row.add(rs.getObject(i));
			}
			msg.getRawArray().add(row);
		}
		msg.setBool(msg.getRawArray().size() == 0 ? false : true);
	}

}