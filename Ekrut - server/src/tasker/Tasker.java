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

public class Tasker {
	/**
	 * @throws Exception SQLException on select error
	 */
	private static String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
	private static String loginMsg = time + ": <Login>: Client {ip} {result} logged in";
	private static String logoutMsg = time + ": <Logout>: Client {ip} logged out";

	public static void taskerHandler(Msg msg, ConnectionToClient client) throws SQLException {
		// if(msg.Task == checkoutOrder)
		// call method Checkout
		switch (msg.getTask()) {
		case Select:
			runSelect(msg);
			break;
		case Update:
			runUpdate(msg);
			break;
		case Insert:
			runUpdate(msg);
			break;
		case Login:
			runSelect(msg);
			if (msg.getBool()) {
				if (!UserManager.isConnected(msg.getObj(0))) {
					int id = msg.getObj(0);
					UserManager.addClient(id, client);
				}
				else {
					msg.setBool(false);
					msg.setResponse("User already logged in");
				}
			}
				else
					msg.setResponse("Wrong details");
			String consoleMsg = msg.getBool() ? loginMsg.replace("{result}", "sucsessfully")
					: loginMsg.replace("{result}", "faild to");
			msg.setConsole(consoleMsg);
			break;
		case Logout:
			UserManager.removeByClient(client);
			msg.setConsole(logoutMsg);
			break;
		default:
			break;
		}
	}

	private static void runUpdate(Msg msg) {
		int returnVal = DBController.update(msg.getQuery());
		msg.setInt(returnVal);
		msg.setBool(returnVal > 0 ? true : false);
	}

	/**
	 * @throws SQLException error in DB
	 */
	private static void runSelect(Msg msg) throws SQLException {
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

	// Checkout method
	// Get the updated catalog from the DB
	// check if available to consume
}