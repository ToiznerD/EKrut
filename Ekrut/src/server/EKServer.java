package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import java.sql.ResultSetMetaData;

import DBHandler.Customer;
import DBHandler.DBController;
import DBHandler.ServerConnection;
import ocsf.server.*;

public class EKServer extends AbstractServer{
	//Default port to listen
	final public static int DEFAULT_PORT = 5555;
	private static ServerController sc;

	private Connection SQLcon;
	
	public EKServer(int port, ServerController sc) {
		super(port);
		this.sc = sc;
		SQLcon = ServerConnection.getConnection();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgg = (String)msg;
		sc.appendConsole("Message recived on server side: " + msgg);

        if (msgg.contains("UPDATE")) {
			Integer returnVal = DBController.runUpdate(msgg);
			sendToAllClients(returnVal);
		}

		else if (msgg.contains("SELECT")) {
        	ResultSet rs = DBController.runQuery(msgg);
        	
        		//Dor changes
        		ArrayList<Customer> customerArray = createCustomerArray(rs);
        		sc.appendConsole("Sending list" + customerArray);
        		sendToAllClients(customerArray);

        }		
		
	}
	
	//Dor function to create ArrayList of serializable Customers
	private ArrayList<Customer> createCustomerArray(ResultSet rs) {
		ArrayList<Customer> customerArray = new ArrayList<>();
		try {
			while(rs.next()) {
				customerArray.add(new Customer(rs.getString(1), rs.getString(2),
												rs.getInt(3), rs.getString(4),
												rs.getString(5), rs.getString(6),
												rs.getInt(7)));
			}
			return customerArray;
		} catch (SQLException e) {
			sc.appendConsole(e.getStackTrace().toString());
			return null;
		}
		
	}
	
}
