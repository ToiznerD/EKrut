package DBHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBController {
	private static Connection conn;
	private static String DB_Path;
	private static String DB_User;
	private static String DB_Password;
	private static boolean initalize = false;

	public static void init() throws Exception {
		if (initalize == false) {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			initalize = true;
		}
		conn = DriverManager.getConnection(DB_Path, DB_User, DB_Password);
	}

	public static boolean isConnected() {
		return (initalize == true && conn != null);
	}

	public static void setDB_Path(String dB_Path) {
		DB_Path = dB_Path;
	}

	public static void setDB_User(String dB_User) {
		DB_User = dB_User;
	}

	public static void setDB_Password(String dB_Password) {
		DB_Password = dB_Password;
	}

	public static Connection connection() throws Exception {
		if (conn == null)
			init();
		return conn;
	}
	
	public static void dropConnection() {
		conn = null;
	}

	public static ResultSet runQuery(String query) throws SQLException {
		Statement stmt;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return rs;
	}

	public static Integer runUpdate(String query) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			Integer queryReturnCode = stmt.executeUpdate(query);
			return queryReturnCode;
		} catch (Exception e) {
			return 0;
		}
	}
	
	
}