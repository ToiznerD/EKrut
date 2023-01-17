package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import Entities.TableOrders;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * DeliveryOrdersController is a controller class for Order Deliveries Screen
 * for FXML file: DeliveryOrdersForm.fxml
 * It has FXML attributes for Buttons, Labels, TextFields, TableColumn and TableView
 * It has HashMap "dataMap" that store each OrderID (askey) and the order data (as value)
 * It has ObservableList that stores deliveries data at each new mysql 'SELECT' action
 * It has 3 final integer attributes for calculation of estimated delivery date and time
 * Method "setUp" is not implemented (extends AbstractController)
 */
public class DeliveryOrdersController extends AbstractController {

	private ObservableList<TableOrders> ordersList = FXCollections.observableArrayList();
	private HashMap<Integer, TableOrders> dataMap = new HashMap<>();

	@FXML
	private Button approveBtn;

	@FXML
	private ImageView refreshBtn, backBtn;

	@FXML
	private Label errorLbl;

	@FXML
	private TextField OrderIdLbl;

	@FXML
	private TableColumn<TableOrders, Integer> idCell;

	@FXML
	private TableColumn<TableOrders, String> nameCell, addressCell, phoneCell, dateAndTimeCell, statusCell,
			deliveryCell;

	@FXML
	private TableView<TableOrders> ordersTable;

	final int distance = 3;
	final int droneAvailability = 2;
	final int shippingTime = 2;

/**
 * initialize method is a protected method that is called automatically when the FXML file is loaded.
 * It sets the cell value factories for each column in the table view
 * and set the data with orderList observable list.
 */
	@FXML
	protected void initialize() {
		setOrdersList();
		idCell.setCellValueFactory(new PropertyValueFactory<TableOrders, Integer>("OrderID"));
		nameCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("RecieverName"));
		addressCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("RecieverAddress"));
		phoneCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("RecieverPhone"));
		dateAndTimeCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("OrderDateAndTime"));
		statusCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("Status"));
		deliveryCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("EstimatedDelivery"));
		ordersTable.setItems(ordersList);
	}

/**
 * setOrdersList method is a private method that is called from initialize method.
 * It builds a 'SELECT' query, sends it to server with Msg, gets the return data values
 * and puts it in orderList and dataMap
 */
	private void setOrdersList() {
		String query = "select o.cid, o.oid, u.name, d.shipping_address, u.phone, o.ord_date,"
				+ " o.ord_time,d.status, d.estimated_date, d.estimated_time\r\n"
				+ "	from users u,orders o,deliveries d\r\n"
				+ "	where o.cid=u.id and o.oid=d.oid and o.method=\"Delivery\";";
		msg = new Msg(Tasks.Select, query);
		sendMsg(msg);
		ordersList.clear();
		for (TableOrders order : msg.getArr(TableOrders.class)) {
			ordersList.add(order);
			dataMap.put(Integer.valueOf(order.getOrderID()), order);
		}
	}

/**
 * calculateEstimatedDate method is a private method that calculates the estimated delivery date for an order.
 * It takes the relevant orderID from dataMap, takes the orderDate and add days of 3 integers saved for delivery time.
 * @param orderID The ID of the relevant order
 * @return LocalDate the estimated delivery date for the order
 */
	private LocalDate calculateEstimatedDate(int orderID) {
		return dataMap.get(orderID).getOrderDate().plusDays(distance + droneAvailability + shippingTime);
	}

/**
 * approveDelivery private method is called when a mouse event occurs. It approves a existing delivery order and send
 * a pop up alert to the customer with the estimated date (only when this customer is conencted).
 * It takes the order ID to approve from the label input and checks if the input is valid (with checkInput method)
 * It updates the data of the relevant order in mysql with Msg that sent to server.
 * when Msg indicates that the action is a success (getBool) it will initialize the table and send the pop up to customer
 * @param event The MouseEvent that triggers the method.
 */	
	@FXML
	private void approveDelivery(MouseEvent event) {
		String labelInput = OrderIdLbl.getText();
		if (!checkInput(labelInput))
			return;
		int orderID = Integer.parseInt(labelInput);
		LocalDate EstimatedDate = calculateEstimatedDate(orderID);
		String query = "UPDATE orders o, deliveries d" + " SET d.status = \"In Progress\", d.estimated_date = '"
				+ EstimatedDate + "'," + " d.estimated_time = '" + dataMap.get(orderID).getOrderTime()
				+ "' WHERE o.oid = d.oid and o.oid = " + orderID;
		msg = new Msg(Tasks.Update, query);
		sendMsg(msg);
		if (msg.getBool()) {
			initialize();
			sendApproval(dataMap.get(orderID));
		}
	}

/**
 * sendApproval private method is called when 'UPDATE' action has successed, it sends a pop up alert to the relevant customer.
 * It sets in error label success message, and sends to server Msg of popUp with customer ID and the message to send.
 * @param order: the relevant order data.
 */	
	private void sendApproval(TableOrders order) {
		errorLbl.setTextFill(Color.web("Green"));
		errorLbl.setText("Success: Sent alert to client");
		String msgToSend = "Your delivery approved!\nEstimated date and time: " + order.getEstimatedDelivery();
		msg = new Msg(Tasks.popUp, order.getCustomerID(), msgToSend);
		sendMsg(msg);
	}

/**
 * checkInput private method is called when client try to approve delivery. It checks if the input is valid.
 * It checks if the input is filled, if it is an integer, and the status of that order.
 * @param text: the text input got from the label
 */	
	private boolean checkInput(String text) {
		errorLbl.setTextFill(Color.web("Red"));
		int labelInput = text.isEmpty() ? 0 : !text.matches("[0-9]+") ? -1 : Integer.parseInt(text);
		boolean orderExist = dataMap.containsKey(labelInput);
		String Status = orderExist ? (String) dataMap.get(labelInput).getStatus() : "";
		if (Status.equals("Pending"))
			return true;
		if (labelInput == 0)
			errorLbl.setText("Error: Order ID cannot be empty");
		else if (labelInput == -1)
			errorLbl.setText("Error: Input must be numbers [0-9]");
		else if (!orderExist)
			errorLbl.setText("Order ID not found");
		else if (Status.equals("In Progress"))
			errorLbl.setText("Error: Order already approved");
		else if (Status.equals("Completed"))
			errorLbl.setText("Error: Order completed");
		return false;
	}

    /**
     * Handles the mouse event of the back button.
     * @param event the mouse event that triggered this method
     * @throws IOException if there is an issue loading the FXML file
     */
	@Override
	public void back(MouseEvent event) {
		try {
			start("DeliveryOperatorPanel", "Delivery Operator Panel");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setUp(Object... objects) {
		//not implemented
	}
}
