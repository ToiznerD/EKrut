package tasker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DBHandler.DBController;
import Util.Msg;

public class Tasker {
	/**
	 * @throws Exception SQLException on select error
	 */
	public static void taskerHandler(Msg msg) throws SQLException {
		//if(msg.Task == checkoutOrder)
		//call method Checkout
		if (msg.getQuery().toLowerCase().startsWith("update") || msg.getQuery().toLowerCase().startsWith("insert"))
			runUpdate(msg);
		else if (msg.getQuery().toLowerCase().startsWith("select"))
			runSelect(msg);
		else if(msg.getQuery().toLowerCase().startsWith("insert"))
			runUpdate(msg);
		updateConsole(msg);
	}
	private static void updateConsole(Msg msg) {
		String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
		switch (msg.getTask()) {
		case Login:
			switch(msg.getSubTask()) {
				case Select:
					msg.setConsole(time + ": <Login>: Client {ip} trying to login");
					break;
				case Update:
					msg.setConsole(time + ": <Login>: Client {ip} Login successfuly");
					break;
				default:
					break;
			}
			break;
		case CreateCustomer:
			switch(msg.getSubTask()) {
				case Select:
					msg.setConsole(time + ": <Create Customer>: Client {ip} asked for id");
					break;
				case CreateCustomer_Insert_Customer:
					msg.setConsole(time + ": <Create Customer>: Client {ip} adding new customer");
					break;
				case CreateCustomer_Update_User:
					msg.setConsole(time + ": <Create Customer>: Client {ip} adding new user");
					break;
				default:
					break;
			}
			break;
		case Logout:
			msg.setConsole(time + ": <Logout>: Client {ip} has logged out.");
			break;
		case RequiredStock:
			if (msg.getBool()) 
				msg.setConsole(time + ": <RequiredStock>: stock sent successfully to Client {ip}");
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

	
	//Checkout method
	//Get the updated catalog from the DB
	//check if available to consume
}
