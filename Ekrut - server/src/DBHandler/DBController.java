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

	public DBController() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection(DB_Path, DB_User, DB_Password);
	}

	public static boolean isConnected() {
		return conn != null;
	}
	
	public static void setDB_prop(String ip, String name, String user, String password) {
		DB_Path = "jdbc:mysql://" + ip + "/" + name + "?serverTimezone=IST";
		DB_User = user;
		DB_Password = password;
	}

	public static Connection connection() throws Exception {
		if (conn == null)
			new DBController();
		return conn;
	}

	public static void dropConnection() {
		conn = null;
	}

	public static int getQuant(int id) {
		//return quantity of product with given id
		
		ResultSet rs = DBController.select("SELECT quant FROM products WHERE id = " + id);
		try {
			rs.first();return rs.getInt(1);
		} catch (SQLException e) {}
		return -1;
	}
	public static ResultSet select(String query) {
		try {
		Statement stmt = connection().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return rs;
		}catch (Exception e) {return null;}
		
	}

	public static Integer update(String query) {
		try {
			Statement stmt = connection().createStatement();
			Integer queryReturnCode = stmt.executeUpdate(query);
			return queryReturnCode;
		} catch (Exception e) {
			return 0;
		}
	}

}