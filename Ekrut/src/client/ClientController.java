package client;

import ocsf.client.*;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.sql.PreparedStatement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientController {
	final public static int DEFAULT_PORT = 5555;
	ClientBackEnd client;
	
	private String Client_hostIP;

	@FXML
	private TextField hostIP;
	
	@FXML
	private Button displayUsersBtn;
	
	@FXML
	private TextField IDField;
	
	@FXML
	private TextField creditCardField;
	
	@FXML
	private TextField subscriberNumberField;
	
	@FXML
	private TextArea consoleArea;
	
	public void displayUsersBtnClick() {
		String host = hostIP.getText();
		if (host.equals("") || host == null) {
			host = "localhost";
		}
		
		try {
		client = new ClientBackEnd(host, DEFAULT_PORT);
		} catch (IOException e) { }
		
		try {
			if (client == null)
				client = new ClientBackEnd(host, DEFAULT_PORT);
		} catch (IOException e) {
			consoleArea.appendText("Could not open connection to Server");
		}
		
		try {
			client.handleMessageFromClientUI("1"); //("SELECT * FROM subscriber;");
		} catch (IOException e) {
			consoleArea.appendText("when trying to connect to: " + host + ":" + DEFAULT_PORT  + ", the following error occured:" + "\n");
			consoleArea.appendText(e.toString() + "\n" );
			e.printStackTrace();
		}
	}
}
