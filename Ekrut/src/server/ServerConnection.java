package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerConnection {
	private static Connection conn;
	private static String DB_Path;
	private static String DB_User;
	private static String DB_Password;
	private static void Connection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection(DB_Path,DB_User,DB_Password);
		}
		catch(Exception ex) {
			System.out.println("Fail to connect");
			ex.printStackTrace();
		}
	}
	
	public static void setDB_Path(String path) {
		DB_Path = path;
	}
	
	public static void setDB_User(String user) {
		DB_User = user;
	}
	
	public static void setDB_Password(String pw) {
		DB_Password = pw;
	}
	
	public static Connection getConnection() throws SQLException{
			if(conn == null)
				Connection();
			return conn;

	}
}
