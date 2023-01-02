package controllers;

import java.io.IOException;

import client.ClientBackEnd;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ConnectionController extends AbstractController {
	public final int DEFAULT_PORT = 5555;
	private final String DEFAULT_IP = "localhost";
	@FXML
	private Button connectBtn;

	@FXML
	private TextField ipEntry;

	@FXML
	private TextField portEntry;
	@FXML
	private Label errorLbl;

	/**
	 * @param event ActionEvent
	 */
	public void connect(ActionEvent event) {
		// connect to server init ClientBackEnd singleton.

		String ip = ipEntry.getText();
		int port = getPort();
		if (ip.isEmpty())
			ip = DEFAULT_IP;
		if (port != -1) {
			try {
				ClientBackEnd.initServer(ip, port); //Initiate client connection instance.
				start("LoginForm", "Login");
				//start("ChooseReportScreen", "Choose Report");
			} catch (IOException e) {
				errorLbl.setText("Error: cannot connect to remote\n" + ip + ":" + port);
			}
		}

	}

	/**
	 * @return port int -1 on fail
	 */
	private int getPort() {
		// return port from portEntry TextField
		// -1 on error.

		if (portEntry.getText().isEmpty())
			return DEFAULT_PORT;
		try {
			return Integer.parseInt(portEntry.getText());
		} catch (NumberFormatException e) {
			errorLbl.setText("Error: Given port must be\n a decimal number [0-9]");
		}
		return -1;
	}
	@Override
	public void back(MouseEvent event) {
		// Not implemented
	}
}
