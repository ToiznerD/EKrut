package DBHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBController {
	private static Connection con = ServerConnection.getConnection();
	
	public static ResultSet runQuery(String query) {
		Statement stmt;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			return rs;
		} catch (SQLException e) { e.printStackTrace(); return null; }	
	}

	public static Integer runUpdate(String query) {
		Statement stmt;
		try
		{
			stmt = con.createStatement();
			Integer queryReturnCode = stmt.executeUpdate(query);

			return queryReturnCode;
		} catch (Exception e) {
			e.printStackTrace();
			Integer p = Integer.valueOf(1);
			return p;
		}
	}
}