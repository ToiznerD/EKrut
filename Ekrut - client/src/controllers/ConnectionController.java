package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Util.Msg;
import Util.Tasks;
import client.ClientBackEnd;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
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
				
				ChoiceDialog<String> dialog = new ChoiceDialog<>("EK", "EK", "OL");
		        dialog.setTitle("Choice Dialog");
		        dialog.setHeaderText("Choose between EK and OL");
		        dialog.setContentText("Your choice:");

		        // show the dialog and get the user's response
		        Optional<String> result = dialog.showAndWait();

		        // save the user's choice
		        if (result.isPresent()) {
		            config = result.get();
		        }
		        if(config.equals("EK")) {
		        	String storeString = null;
		        	 String query = "SELECT name FROM store";
				     msg = new Msg(Tasks.Select, query);
				     sendMsg(msg);
				     while(storeString == null || storeString.equals("")) {
					     List<StringBuilder> stores = msg.getArr(StringBuilder.class);
			        	 List<String> stringStores = new ArrayList<>();
			        	 for(StringBuilder s : stores) stringStores.add(s.toString());
			        	 
					     ChoiceDialog<String> dialogStore = new ChoiceDialog<>("Store", stringStores);
					     dialogStore.setTitle("Choice Dialog");
					     dialogStore.setHeaderText("Choose Store");
					     dialogStore.setContentText("Your choice:");
					     
					     Optional<String> result2 = dialogStore.showAndWait();
					     
					     //save the user's input
				          storeString = "";
				          if (result2.isPresent()) {
				        	  storeString = result2.get();
				          }
				     }
				     query = "SELECT * FROM store WHERE name = '" + storeString + "'";
				     msg = new Msg(Tasks.Select, query);
				     sendMsg(msg);
				     storeID = msg.getObj(0); //change it to local field ? /////////////////////////////////////
		        }
				
				start("LoginForm", "Login", storeID);
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

	@Override
	public void setUp(Object... objects) {
		// TODO Auto-generated method stub
		
	}
}
