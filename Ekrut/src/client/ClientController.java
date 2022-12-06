package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DBHandler.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
	private TableColumn<Customer, String> customerEmail;

	@FXML
	private TableColumn<Customer, String> customerPhone;

	@FXML
	private TableColumn<Customer, String> customerName;

	@FXML
	private TableColumn<Customer, String> customerLName;

	@FXML
	private TextArea consoleArea;

	private ObservableList<Customer> CustomerObservableArr = FXCollections.observableArrayList();
	private Map<Integer, Customer> customerMap = new HashMap<Integer, Customer>();
	private boolean isTableInit;

	/**
	 * function is invoked when clicking Display Users Button in GUI parses
	 * parameters passed from GUI fields, instantiates a client, and sends a query
	 * request to server
	 */

	public void displayUsersBtnClick() {
		if (!isTableInit)
			initTable();
		try {
			String query = "SELECT * FROM subscriber;";
			client.handleMessageFromClientUI(query);
		} catch (IOException e) {
			logError(e);
		}
	}

	public void updateBtnClick() {
		if (checkValidTextField() && checkParseable()) {
			try {
				String query = buildString();
				client.handleMessageFromClientUI(query);
				updateTable();
				clearTextField();
			} catch (IOException e) {
				logError(e);
			}
		} else
			appendConsole("Error: must provide ID(int) and new Credit Number(int) or new Subscriber Number(int)");
	}

	private void clearTextField() {
		IDToUpdateField.clear();
		creditCardUpdateField.clear();
		subscriberNumberUpdateField.clear();
	}

	private boolean checkParseable() {
		String credit = creditCardUpdateField.getText(), sub = subscriberNumberUpdateField.getText();
		try {
			if (!credit.isEmpty())
				Integer.parseInt(credit);
			if (!sub.isEmpty())
				Integer.parseInt(sub);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private boolean checkValidTextField() {
		return !IDToUpdateField.getText().isEmpty() && !creditCardUpdateField.getText().isEmpty()
				|| !subscriberNumberUpdateField.getText().isEmpty();
	}

	private String buildString() {
		StringBuilder query = new StringBuilder("UPDATE subscriber SET ");
		String credit = creditCardUpdateField.getText(), sub = subscriberNumberUpdateField.getText();
		if (!credit.isEmpty())
			query.append("creditNum = \"" + credit + "\", ");
		if (!sub.isEmpty())
			query.append("subNum = \"" + subscriberNumberUpdateField.getText() + "\"");
		else
			query.delete(query.length() - ", ".length(), query.length());
		query.append(" WHERE id=\"" + IDToUpdateField.getText() + "\"");
		return query.toString();
	}

	public void disconnect() {
		try {
			client.handleMessageFromClientUI("disconnect");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initTable() {
		customerID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
		customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("fName"));
		customerLName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lName"));
		customerEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
		customerPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNum"));
		customerCreditCard.setCellValueFactory(new PropertyValueFactory<Customer, String>("creditNum"));
		customerSubscriberNum.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("subNum"));
		customerTableView.setItems(CustomerObservableArr);
	}

	protected void updateTable() {
		String credit = creditCardUpdateField.getText(), sub = subscriberNumberUpdateField.getText();
		int id = Integer.valueOf(IDToUpdateField.getText());
		Customer customerToChange = customerMap.get(id);
		if (customerToChange == null)
			return;
		if (!sub.isEmpty())
			customerToChange.setSubNum(Integer.valueOf(sub));
		if (!credit.isEmpty())
			customerToChange.setCreditNum(credit);
		customerTableView.refresh();
	}

	protected void fillUserTableView(ArrayList<Customer> CustomerArr) {
		CustomerObservableArr.clear();
		for (Customer c : CustomerArr) {
			customerMap.put(c.getId(), c);
			CustomerObservableArr.add(c);
		}
	}

	public void logError(Exception e) {
		appendConsole("when trying to send a message to server, the following error occured: ");
		appendConsole(e.toString() + "\n");
		e.printStackTrace();
	}

	public void appendConsole(String str) {
		consoleArea.appendText(str + "\n");
	}

	public void setClient(ClientBackEnd client) {
		this.client = client;
	}

}
