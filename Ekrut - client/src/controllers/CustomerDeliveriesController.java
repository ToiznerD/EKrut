package controllers;

import java.io.IOException;
import java.util.HashMap;

import Entities.TableOrders;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

	private void setOrdersList() {
		String query ="select o.cid, o.oid, u.name, d.shipping_address, u.phone, o.ord_date, o.ord_time,d.status, d.estimated_date, d.estimated_time\r\n"
				+ "	from users u, orders o,deliveries d\r\n"
				+ "	where o.cid=u.id and o.oid=d.oid and o.method=\"delivery\" and o.cid=" + myUser.getId()+";";
		msg = new Msg(Tasks.Select, query);
		sendMsg(msg);
		ordersList.clear();
		for (TableOrders order : msg.getArr(TableOrders.class)) {
			ordersList.add(order);
			dataMap.put(Integer.valueOf(order.getOrderID()), order.getStatus());
		}
	}

	@FXML
	public void approveDeliveryAccepted(ActionEvent event) { 
		String labelInput = OrderIdLbl.getText();
		if (!checkInput(labelInput))
			return;
		int orderID = Integer.parseInt(labelInput);
		String query = "update orders o ,deliveries d set d.status = \"Completed\" , o.ord_status = \"Completed\"\r\n"
					+ "where o.oid=d.oid and o.oid= "+ orderID + " and o.cid = " + myUser.getId();
		msg = new Msg(Tasks.Update, query);
		sendMsg(msg);
		if (msg.getBool()) {
			errorLbl.setTextFill(Color.web("Green"));
			errorLbl.setText("Success: You approved that delivery accepted");
			initialize();
		}
	}

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

	@Override
	public void back(MouseEvent event) {
		try {
			start("CustomerPanel", "Customer Panel");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setUp(Object... objects) {
		// TODO Auto-generated method stub

	}
}
