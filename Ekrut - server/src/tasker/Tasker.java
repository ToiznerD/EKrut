package tasker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBHandler.DBController;
import Util.Msg;

public class Tasker {
	/**
	 * @throws Exception SQLException on select error
	 */
	public static void taskerHandler(Msg msg) throws SQLException {
		if (msg.getQuery().toLowerCase().startsWith("update") || msg.getQuery().toLowerCase().startsWith("insert"))
			runUpdate(msg);
		else if (msg.getQuery().toLowerCase().startsWith("select"))
			runSelect(msg);
		updateConsole(msg);
	}
	private static void updateConsole(Msg msg) {
		switch (msg.getTask()) {
		case Login:
			if (msg.getBool())
				msg.setConsole("Client {ip} Login successfully");
			break;
		case RequiredStock:
			if (msg.getBool()) 
				msg.setConsole("stock sent successfully to Client {ip}");
			break;
		default:
			break;	
		}
	}
	private static void runUpdate(Msg msg) {
		int returnVal = DBController.update(msg.getQuery());
		msg.setInt(returnVal);
	}
	/**
	 * @throws SQLException error in DB
	 */
	private static void runSelect(Msg msg) throws SQLException {
		ResultSet rs = DBController.select(msg.getQuery());
		int columnCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			List<Object> row = new ArrayList<>();
			for (int i = 1; i <= columnCount; i++)
				row.add(rs.getObject(i));
			msg.getRawArray().add(row);
		}
		msg.setBool(msg.getRawArray().size() == 0 ? false : true);
	}
}
