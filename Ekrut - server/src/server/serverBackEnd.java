package server;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import Util.Tasks;
import tasker.Tasker;
import Util.Msg;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class serverBackEnd extends AbstractServer {
	// Default port to listen
	final public static int DEFAULT_PORT = 5555;
	private static ServerController sc;
	private static HashMap<Integer, ConnectionToClient> connectedUsers;//erik

	public serverBackEnd(int port, ServerController sc) {
		super(port);
		serverBackEnd.sc = sc;
		connectedUsers = new HashMap<>();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Msg taskMsg = (Msg) msg;
		if(taskMsg.getTask() == Tasks.Logout) //erik
			connectedUsers.remove(Integer.valueOf(taskMsg.getQuery().substring(taskMsg.getQuery().lastIndexOf(" ")+1)));//IHS
		if (taskMsg.getTask() == Tasks.Disconnect)
			clientDisconnected(client);
		else if(taskMsg.getTask() == Tasks.popUp) { //erik
			msg = new Msg(Tasks.popUp, taskMsg.getID(),taskMsg.getAlertMsg());
			try {
				sendMsg(connectedUsers.get(taskMsg.getID()), taskMsg);
//				client.notifyAll();//without notify its crushes
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else  {
			try {
				Tasker.taskerHandler(taskMsg);
				sendMsg(client, taskMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(taskMsg.getTask() == Tasks.Login && taskMsg.getSubTask()==Tasks.Update) //erik
			connectedUsers.put(Integer.valueOf(taskMsg.getQuery().substring(taskMsg.getQuery().lastIndexOf(" ")+1)),client);//IHS
	}

	protected void sendMsg(ConnectionToClient client, Msg taskMsg) throws IOException {
		client.sendToClient(taskMsg);
		if (taskMsg.getConsole() != null)
			sc.appendConsole(taskMsg.getConsole().replace("{ip}", client.getInetAddress().getHostAddress()));
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		sc.addConnected(client.getInetAddress());
		sc.appendConsole("Client " + client.getInetAddress().getHostAddress() + " is Connected to server. ("
				+LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))+")");
	}

	@Override
	protected void clientDisconnected(ConnectionToClient client) {
		sc.removeConnected(client.getInetAddress());
		sc.appendConsole("Client " + client.getInetAddress().getHostAddress() + " is disconnected from server. ("
				+LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))+")");
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
