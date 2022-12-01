package client;


import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import DBHandler.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientController {
	final public static int DEFAULT_PORT = 5555;
	ClientBackEnd client;

	@FXML
	private TextField hostIP;
	
	@FXML
	private Button displayUsersBtn;
	
	@FXML
	private TextField IDToUpdateField;
	
	@FXML
	private TextField creditCardField;
	
	@FXML
	private TextField subscriberNumberField;
	
	@FXML
	private TableView<Customer> customerTableView;
	
	@FXML
	private TableColumn<Customer, Integer> customerID;
	
	@FXML
	private TableColumn<Customer, String> customerCreditCard;
	
	@FXML
	private TableColumn<Customer, Integer> customerSubscriberNum;
	
	@FXML
	private TextArea consoleArea;
	
	/**
	 * function is invoked when clicking Display Users Button in GUI
	 * parses parameters passed from GUI fields, instantiates a client, and sends a query 
	 * request to server
	 */
	public void displayUsersBtnClick() {
		String host = hostIP.getText();
		if (host.equals("") || host == null) {
			host = "localhost";
		}
		
		try {
		client = new ClientBackEnd(host, DEFAULT_PORT, this);
		} catch (IOException e) { }
		
		try {
			if (client == null)
				client = new ClientBackEnd(host, DEFAULT_PORT, this);
		} catch (IOException e) {
			appendConsole("Could not open connection to Server");
		}
		
		try {
			client.handleMessageFromClientUI("SELECT * FROM subscriber;");
		} catch (IOException e) {
			appendConsole("when trying to connect to: " + host + ":" + DEFAULT_PORT  + ", the following error occured:" + "\n");
			appendConsole(e.toString() + "\n" );
			e.printStackTrace();
		}
	}
	
	
	protected void fillUserTableView(ArrayList<Customer> CustomerArr) {
		
		ObservableList<Customer> CustomerObservableArr = FXCollections.observableArrayList(CustomerArr);
		
		customerID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
		customerCreditCard.setCellValueFactory(new PropertyValueFactory<Customer, String>("creditNum"));
		customerSubscriberNum.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("subNum"));
		
		customerTableView.setItems(CustomerObservableArr);
	}
	
	
    public void appendConsole(String str) {
    	consoleArea.appendText(str + "\n");
    }

}
