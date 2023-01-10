package client;

import java.io.IOException;

import Util.Msg;
import Util.Tasks;
import controllers.AbstractController;
import ocsf.client.AbstractClient;

public class ClientBackEnd extends AbstractClient {

	private static AbstractController abstractController;
	private static ClientBackEnd instance;

	public static AbstractController getAbstractController() {
		return abstractController;
	}

	public static void setAbstractController(AbstractController abstractController) {
		ClientBackEnd.abstractController = abstractController;
	}

	private ClientBackEnd(String host, int port) throws IOException {
		super(host, port);
		openConnection();
	}

	public static void initServer(String host, int port) throws IOException {
		if (instance == null)
			instance = new ClientBackEnd(host, port);
	}

	public static ClientBackEnd getInstance() {
		return instance;
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		Msg taskMsg = (Msg) msg;
		if(taskMsg.getTask()==Tasks.popUp) 
			abstractController.popupAlert("AA");
		AbstractController.msg = taskMsg;
		AbstractController.Notify();
	}

	public void handleMessageFromClientUI(Object message) throws IOException {
		sendToServer(message);
	}

	public void quit() {
		try {
			handleMessageFromClientUI(new Msg(Tasks.Disconnect,null));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

