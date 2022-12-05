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
	private Button displayUsersBtn;
	
	@FXML
	private TextField IDToUpdateField;
	
	@FXML
	private TextField creditCardUpdateField;
	
	@FXML
	private TextField subscriberNumberUpdateField;

	@FXML
	private Button UpdateBtn;

	@FXML
	private TableView<Customer> customerTableView;
	
	@FXML
	private TableColumn<Customer, Integer> customerID;
	
	@FXML
	private TableColumn<Customer, String> customerCreditCard;
	
	@FXML
	private TableColumn<Customer, Integer> customerSubscriberNum;
	
	@FXML
	private TableColumn<Customer, String> customerName;
	
	@FXML
	private TableColumn<Customer, String> customerLName;
	
	@FXML
	private TextArea consoleArea;
	
	/**
	 * function is invoked when clicking Display Users Button in GUI
	 * parses parameters passed from GUI fields, instantiates a client, and sends a query 
	 * request to server
	 */


	public void displayUsersBtnClick() {
		try {
			String query = "SELECT * FROM subscriber;";
			client.handleMessageFromClientUI(query);
		} catch (IOException e) {
			logError(e);
		}
	}
	
	public void updateBtnClick() {
		try {
			StringBuilder query = new StringBuilder("UPDATE subscriber SET credit_card_number = \"" + creditCardUpdateField.getText() + "\"");
			query.append(", subscriber_number = \"" + subscriberNumberUpdateField.getText() + "\"");
			query.append(" WHERE id=\"" + IDToUpdateField.getText() + "\"");
			client.handleMessageFromClientUI(query.toString());
			displayUsersBtnClick();
		} catch (IOException e) {
			logError(e);
		}
	}

	protected void fillUserTableView(ArrayList<Customer> CustomerArr) {
		
		ObservableList<Customer> CustomerObservableArr = FXCollections.observableArrayList(CustomerArr);
		
		customerID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
		customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("fName"));
		customerLName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lName"));
		customerCreditCard.setCellValueFactory(new PropertyValueFactory<Customer, String>("creditNum"));
		customerSubscriberNum.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("subNum"));
		
		customerTableView.setItems(CustomerObservableArr);
	}

	public void logError (Exception e) {
		appendConsole("when trying to send a message to server, the following error occured: ");
		appendConsole(e.toString() + "\n" );
		e.printStackTrace();
	}
	
    public void appendConsole(String str) {
    	consoleArea.appendText(str + "\n");
    }

	public void setClient(ClientBackEnd client) {
		this.client = client;
	}


}
