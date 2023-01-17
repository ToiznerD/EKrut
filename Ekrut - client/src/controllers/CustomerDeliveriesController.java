package controllers;

import java.io.IOException;
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
 * CustomerDeliveriesController is a controller class for a customer delivery orders.
 * It extends AbstractController and overrides methods of it.
 */
public class CustomerDeliveriesController extends AbstractController {

	private ObservableList<TableOrders> ordersList = FXCollections.observableArrayList();
	private HashMap<Integer, String> dataMap = new HashMap<>();

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
	private TableColumn<TableOrders, String> addressCell, dateAndTimeCell, statusCell, deliveryCell;

	@FXML
	private TableView<TableOrders> ordersTable;

	/**
	 * initialize method is a protected method that is called automatically when the FXML file is loaded.
	 * It sets the cell value factories for each column in the table view
	 * and set the data with orderList observable list.
	 */
	@FXML
	protected void initialize() {
		setOrdersList();
		idCell.setCellValueFactory(new PropertyValueFactory<TableOrders, Integer>("OrderID"));
		addressCell.setCellValueFactory(new PropertyValueFactory<TableOrders, String>("RecieverAddress"));
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
		String query ="select o.cid, o.oid, u.name, d.shipping_address, u.phone, o.ord_date, o.ord_time,d.status, d.estimated_date, d.estimated_time\r\n"
				+ "	from users u, orders o,deliveries d\r\n"
				+ "	where o.cid=u.id and o.oid=d.oid and o.method=\"Delivery\" and o.cid=" + myUser.getId()+";";
		msg = new Msg(Tasks.Select, query);
		sendMsg(msg);
		ordersList.clear();
		for (TableOrders order : msg.getArr(TableOrders.class)) {
			ordersList.add(order);
			dataMap.put(Integer.valueOf(order.getOrderID()), order.getStatus());
		}
	}

	/**
	 * approveDeliveryAccepted private method is called when a mouse event occurs.
	 * It used when customer want to confirm his delivery arrived.
	 * It updates the status of the order and delivery in it DB tables.
	 * when Msg indicates that the action is a success (getBool) it will initialize the table and send the pop up to customer
	 * @param event The MouseEvent that triggers the method.
	 */	
	@FXML
	public void approveDeliveryAccepted(MouseEvent event) { 
		String labelInput = OrderIdLbl.getText();
		if (!checkInput(labelInput))
			return;
		int orderID = Integer.parseInt(labelInput);
		String query = "UPDATE orders o ,deliveries d set d.status = \"Completed\" , o.ord_status = \"Completed\""
					+ " WHERE o.oid=d.oid and o.oid= "+ orderID + " and o.cid = " + myUser.getId();
		msg = new Msg(Tasks.Update, query);
		sendMsg(msg);
		if (msg.getBool()) {
			errorLbl.setTextFill(Color.web("Green"));
			errorLbl.setText("Success: You approved that delivery accepted");
			initialize();
		}
	}

	/**
	 * checkInput private method is called when customer try to approve delivery arrived. It checks if the input is valid.
	 * It checks if the input is filled, if it is an integer, and the status of that order.
	 * @param text: the text input got from the label
	 * @return true if status is in progress, false otherwise
	 */	
	private boolean checkInput(String text) {
		errorLbl.setTextFill(Color.web("Red"));
		int labelInput = text.isEmpty() ? 0 : !text.matches("[0-9]+") ? -1 : Integer.parseInt(text);
		boolean orderExist = dataMap.containsKey(labelInput);
		String Status = orderExist ? (String) dataMap.get(labelInput) : "";
		if (Status.equals("In Progress"))
			return true;
		if (labelInput == 0)
			errorLbl.setText("Error: Order ID cannot be empty");
		else if (labelInput == -1)
			errorLbl.setText("Error: Input must be numbers [0-9]");
		else if (!orderExist)
			errorLbl.setText("Order ID not found");
		else if (Status.equals("Pending"))
			errorLbl.setText("Error: Order not approved yet");
		else if (Status.equals("Completed"))
			errorLbl.setText("Error: Order completed");
		return false;
	}

    /**
     * Handles the mouse event of the back button.
     * @param event the mouse event that triggered this method
     */
	@Override
	public void back(MouseEvent event) {
		try {
			start("CustomerPanel", "Customer Dashboard");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setUp(Object... objects) {
		//not implemented
	}
}
