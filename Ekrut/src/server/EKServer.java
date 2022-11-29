package server;

import java.sql.ResultSet;
import java.util.ArrayList;

import DBHandler.Customer;
import DBHandler.MySQLConnection;
import ocsf.server.*;

public class EKServer extends AbstractServer{
	//Default port to listen
	final public static int DEFAULT_PORT = 5555;
	private static ServerController sc;
	private MySQLConnection SQLcon;
	
	public EKServer(int port, ServerController sc) {
		super(port);
		this.sc = sc;
		this.SQLcon = new MySQLConnection();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgg = (String) msg;
        if (msgg.contains("UPDATE")) {
        	
        	
        } else if (msgg.contains("SELECT")) {
        	ResultSet rs = SQLcon.runQuery(msgg);
        	sendResultSet(rs);	
        }		
		
	}
	
	private void sendResultSet(ResultSet rs) {
		ArrayList<Customer> CustomerArr = new ArrayList<Customer>();
		
		try {
	    	while (rs.next()) {
	    		CustomerArr.add(Customer.CreateCustomerFromRS(rs));
	    	}
		} catch (Exception e) {
              e.printStackTrace();
		}
		
		sendToAllClients(CustomerArr);
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
