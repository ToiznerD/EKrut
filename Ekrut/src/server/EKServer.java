package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import java.sql.ResultSetMetaData;

import DBHandler.Customer;
import DBHandler.DBController;
import ocsf.server.*;

public class EKServer extends AbstractServer{
	//Default port to listen
	final public static int DEFAULT_PORT = 5555;
	private static ServerController sc;

	
	public EKServer(int port, ServerController sc) {
		super(port);
		EKServer.sc = sc;
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
        	ResultSet rs = null;
			try {
				rs = DBController.runQuery(msgg);
			} catch (SQLException e) {
				sc.appendConsole(e.getMessage());
				e.printStackTrace();
			}

        		ArrayList<Customer> customerArray = null;
				try {
					customerArray = Customer.createCustomerArray(rs);
					sc.appendConsole("Sending list" + customerArray);
	        		sendToAllClients(customerArray);
	        		
				} catch (SQLException e) {
					sc.appendConsole(e.getStackTrace().toString());
				}
        }		
		
	}
	
	
}
