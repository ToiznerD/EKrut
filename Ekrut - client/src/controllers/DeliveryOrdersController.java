package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import Entities.TableOrders;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DeliveryOrdersController extends AbstractController {

	private ObservableList<TableOrders> ordersList = FXCollections.observableArrayList();
	private HashMap<Integer, List<Object>> dataMap = new HashMap<>();

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

	private void setOrdersList() {
		String query = "select o.cid, o.oid, u.name, o.shipping_address, u.phone, o.ord_date,"
				+ " o.ord_time,d.status, d.estimated_date, d.estimated_time\r\n"
				+ "	from users u,orders o,deliveries d\r\n"
				+ "	where o.cid=u.id and o.oid=d.oid and o.method=\"delivery\";";
		msg = new Msg(Tasks.DeliveryOrders, query);
		sendMsg(msg);
		ordersList.clear();
		for (TableOrders order : msg.getArr(TableOrders.class)) {
			ordersList.add(order);
			dataMap.put(order.getOrderID(), Arrays.asList(order.getCustomerID(), order.getOrderDate(),
					order.getOrderTime(), order.getStatus()));
		}
	}

	private String calculateEstimatedDate(int orderID) {
		Calendar cal = Calendar.getInstance();
		cal.setTime((java.util.Date) dataMap.get(orderID).get(1));
		cal.add(Calendar.DAY_OF_MONTH, shippingTime + droneAvailability + distance);
		return new SimpleDateFormat("dd/MM/yy").format(cal.getTime());
	}

	@FXML
	private void approveDelivery(MouseEvent event) {
		String labelInput = OrderIdLbl.getText();
		if (!checkInput(labelInput))
			return;
		int orderID = Integer.parseInt(labelInput);
		String EstimatedDate = calculateEstimatedDate(orderID);
		String query = "UPDATE orders o, deliveries d" + " SET d.status = \"in progress\", d.estimated_date = '"
				+ EstimatedDate + "'," + " d.estimated_time = '" + dataMap.get(orderID).get(2)
				+ "' WHERE o.oid = d.oid and o.oid = " + orderID;
		msg = new Msg(Tasks.Update, query);
		sendMsg(msg);
		if (msg.getBool()) {
			String msgToSend = "Delivery approved!/nEstimated date and time: " + EstimatedDate;
			sendApproval((Integer) dataMap.get(orderID).get(0), msgToSend);
		}
	}

	private void sendApproval(int id, String alertMsg) {
		errorLbl.setTextFill(Color.web("Green"));
		errorLbl.setText("Success: Sent alert to client");
		initialize();
		msg = new Msg(Tasks.popUp, id, alertMsg);// erik
		sendMsg(msg);// erik
	}

	private boolean checkInput(String text) {
		errorLbl.setTextFill(Color.web("Red"));
		int labelInput = text.isEmpty() ? 0 : !text.matches("[0-9]+") ? -1 : Integer.parseInt(text);
		boolean orderExist = dataMap.containsKey(labelInput);
		String Status = orderExist ? (String) dataMap.get(labelInput).get(3) : "";
		if (Status.equals("pending"))
			return true;
		if (labelInput == 0)
			errorLbl.setText("Error: Order ID cannot be empty");
		else if (labelInput == -1)
			errorLbl.setText("Error: Input must be numbers [0-9]");
		else if (!orderExist)
			errorLbl.setText("Order ID not found");
		else if (Status.equals("in progress"))
			errorLbl.setText("Error: Order already approved");
		else if (Status.equals("completed"))
			errorLbl.setText("Error: Order completed");
		return false;
	}

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
		// TODO Auto-generated method stub

	}
}
