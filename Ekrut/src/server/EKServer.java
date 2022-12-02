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
        	try {
	        	ResultSetMetaData rsmd = rs.getMetaData();
	        	int columnCount = rsmd.getColumnCount();
	        	ArrayList<ArrayList<Object>> TwoDdataArray = create2DArrFromRs(rs, columnCount);

        		sendToAllClients(TwoDdataArray);
        	} catch (SQLException e) { sc.appendConsole(e.getStackTrace().toString()); }
        }		
//		
//		
	}
	
	private ArrayList<ArrayList<Object>> create2DArrFromRs(ResultSet rs, int colNum) {
		ArrayList<ArrayList<Object>> returnVal = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> innerArr = new ArrayList<Object>();;
		
		try {
			while(rs.next()) {
				innerArr = new ArrayList<Object>();
				for (int i=1; i <= colNum; i++) {
					innerArr.add(rs.getObject(i));
				}
				
				returnVal.add(innerArr);
			}
			return returnVal;
			
		} catch (SQLException e) {
			sc.appendConsole(e.getStackTrace().toString());
			return null;
		}
	}
	
	//
	public static void main(String[] args) 
	  {
	    int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
		
	    EKServer sv = new EKServer(port, null);
	    
	    try 
	    {
	      sv.listen(); //Start listening for connections
	      System.out.println("LISTENING to port : " + DEFAULT_PORT);
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println(ex);
	      System.out.println("ERROR - Could not listen for clients!");
	    }
	  }
}
