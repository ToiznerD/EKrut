package server;

import ocsf.server.*;

public class EKServer extends AbstractServer{
	//Default port to listen
	final public static int DEFAULT_PORT = 5555;
	
	public EKServer(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		// TODO Auto-generated method stub
		
	}
	
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
		
	    EKServer sv = new EKServer(port);
	    
	    try 
	    {
	      sv.listen(); //Start listening for connections
	      System.out.println("LISTENING");
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println("ERROR - Could not listen for clients!");
	    }
	  }
}
