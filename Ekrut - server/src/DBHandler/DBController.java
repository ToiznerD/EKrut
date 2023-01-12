package DBHandler;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.jdbc.ScriptRunner;

public class DBController {
	private static Connection conn;
	private static String DB_Path;
	private static String DB_ip;
	private static String DB_User;
	private static String DB_Password;
	private static Path SQL_PATH = Paths.get("src\\DBHandler\\DB.sql").toAbsolutePath();

	public DBController() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + DB_ip + ":?serverTimezone=IST", DB_User, DB_Password);
			Reader reader = new FileReader(SQL_PATH.toString());
			ScriptRunner runner = new ScriptRunner(conn);
			runner.setLogWriter(null);
			runner.runScript(reader);
			conn.close();
			conn = DriverManager.getConnection(DB_Path, DB_User, DB_Password);
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isConnected() {
		return conn != null;
	}

	public static void setDB_prop(String ip, String dbName, String user, String password) {
		DB_Path = "jdbc:mysql://" + ip + "/" + dbName + "?serverTimezone=IST";
		DB_ip = ip;
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
			rs.first();
			return rs.getInt(1);
		} catch (SQLException e) {
		}
		return -1;
	}

	public static ResultSet select(String query) {
		try {
			Statement stmt = connection().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (Exception e) {
			return null;
		}

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

	public static boolean importUsers() {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("SET GLOBAL local_infile=1");
			stmt.executeUpdate("load data local infile \"users.txt\" into table users");
			stmt.executeUpdate("load data local infile \"customer.txt\" into table customer");

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}