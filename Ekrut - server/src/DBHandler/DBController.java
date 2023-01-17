package DBHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.jdbc.ScriptRunner;

/**
 * DBController class used to connect to a mysql DB and perform actions related to the DB.
 */
public class DBController {
	private static Connection conn;
	private static String DB_Path;
	private static String DB_ip;
	private static String DB_User;
	private static String DB_Password;
	//private static Path SQL_PATH = Paths.get("DB.sql").toAbsolutePath();

	/**
	* Constructor for the DBController class.
	* It tries to connect to existed mysql DB, when it is not established it creates it with buildDB method 
	* @throws Exception when connection failed
	*/
	public DBController() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		try {
			conn = DriverManager.getConnection(DB_Path, DB_User, DB_Password);
		} catch (SQLException e) {
			buildDB();
			conn = DriverManager.getConnection(DB_Path, DB_User, DB_Password);
		}
	}

	/**
	 * buildDB method used to build a connection to mysql DB with sql script 'DB.sql'
	 * @throws RuntimeException when connection failed
	 */
	private void buildDB() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + DB_ip + ":?serverTimezone=IST", DB_User, DB_Password);
			InputStream in = getClass().getResourceAsStream("DB.sql");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			ScriptRunner runner = new ScriptRunner(conn);
			runner.setLogWriter(null);
			runner.runScript(reader);
			conn.close();
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Checks if mysql connection exist
	 * @return boolean : if connection exist returns true, else returns false
	 */
	public static boolean isConnected() {
		return conn != null;
	}

	/**
	* Sets the properties for connecting to mysql DB
	* @param ip IP address of mysql server.
	* @param dbName mysql connection name.
	* @param user mysql username.
	* @param password mysql password.
	*/
	public static void setDB_prop(String ip, String dbName, String user, String password) {
		DB_Path = "jdbc:mysql://" + ip + "/" + dbName + "?serverTimezone=IST";
		DB_ip = ip;
		DB_User = user;
		DB_Password = password;
	}

	/**
	* Returns a connection to the mysql DB.
	* If connection is not exist it call DBController constructor to create a connection.
	@return Connection of mysql DB.
	@throws Exception if new connection creation failed
	*/
	public static Connection connection() throws Exception {
		if (conn == null)
			new DBController();
		return conn;
	}

	/**
	 * Sets the current connection to null for closing mysql DB connection.
	 */
	public static void dropConnection() {
		conn = null;
	}

	/**
	* Perform Select from DB and returns quantity of a product.
	@param id ID of a product.
	@return The quantity of the product or -1 if an error occurs.
	*/
	public static int getQuant(int id) {
		ResultSet rs = DBController.select("SELECT quant FROM products WHERE id = " + id);
		try {
			rs.first();
			return rs.getInt(1);
		} catch (SQLException e) {
		}
		return -1;
	}

	/**
	* Perform Select from DB and returns Result Set.
	* @param query The Select statement to be executed.
	* @return results that returned from the SELECT statement, or null if an error occurs.
	*/
	public static ResultSet select(String query) {
		try {
			Statement stmt = connection().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	* Perform Update to DB and number of rows affected.
	* @param query The Update statement to be executed.
	* @return the number of rows affected.
	*/
	public static Integer update(String query) {
		try {
			Statement stmt = connection().createStatement();
			Integer queryReturnCode = stmt.executeUpdate(query);
			return queryReturnCode;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	* Utility import method.
	* Imports users and customers data from users.txt and customer.txt files to the DB by executing Update queries.
	* @return return true of import successed, else return false;
	*/
	public static boolean importUsers() {
		Statement stmt;
		if(conn == null)
			return false;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("SET GLOBAL local_infile=1");
			stmt.executeUpdate("load data local infile \"C:/users.txt\" into table users");
			stmt.executeUpdate("load data local infile \"C:/customer.txt\" into table customer");
			stmt.executeUpdate("load data local infile \"C:/region_employee.txt\" into table region_employee");

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}