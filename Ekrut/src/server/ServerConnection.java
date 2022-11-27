package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerConnection {
	private static Connection conn;
	
	private static void Connection() {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost/ekrut?serverTimezone=IST","root","1379825");
		}
		catch(Exception ex) {
			System.out.println("Fail to connect");
			ex.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
			if(conn == null)
				Connection();
			return conn;

	}
}
