package DBHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBController {
	private static Connection con = ServerConnection.getConnection();

//	public DBController() {
//		con = ServerConnection.getConnection();
//	}
//
	
	public static ResultSet runQuery(String query) {
		Statement stmt;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			return rs;
		} catch (SQLException e) { e.printStackTrace(); return null; }	
	}
	

}