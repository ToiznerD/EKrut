package DBHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
	private Connection con;

	public MySQLConnection() {
		con = ServerConnection.getConnection();
	}

	
	public ResultSet runQuery(String query) {
			
		Statement stmt;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			return rs;
		} catch (SQLException e) { e.printStackTrace(); return null; }	
	}

}
