package DBHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.ServerController;

public class ServerConnection {
	private static Connection conn;
	private static String DB_Path;
	private static String DB_User;
	private static String DB_Password;
	private static ServerController sc;
	private static void Connection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			sc.appendConsole("Driver definition succeed");
		} catch (Exception e) {
        		//e.printStackTrace();
        		sc.appendConsole(e.getStackTrace().toString());
		 }
		 
		try {
			conn = DriverManager.getConnection(DB_Path,DB_User,DB_Password);
			sc.appendConsole("Database successfuly connected.");
		} catch (SQLException e) {
			sc.appendConsole("Database fail to connect");
		}
	}
	
	public static void setDB_Path(String path) {
		DB_Path = path;
	}
	
	public static String getDB_Path() {
		return DB_Path;
	}
	
	public static void setDB_User(String user) {
		DB_User = user;
	}
	
	public static void setDB_Password(String pw) {
		DB_Password = pw;
	}
	
	public static void setSC(ServerController s) {
		sc = s;
	}
	
	public static Connection getConnection(){
			if(conn == null)
				Connection();
			return conn;
	}
	
	public static void dropConnection() {
		conn = null;
	}
	
}
