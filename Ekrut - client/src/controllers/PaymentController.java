package controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import Entities.OrderProduct;
import Util.Msg;
import Util.Tasks;
import client.Config;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class PaymentController extends AbstractOrderController {
	private static final DecimalFormat decimal = new DecimalFormat("0.00");
	private static final DecimalFormat decimalToInt = new DecimalFormat("0");
	private ObservableList<OrderProduct> prodList = FXCollections.observableArrayList();;
	@FXML
	private ListView<OrderProduct> listView;
	@FXML
	private Text addressText, nameText, phoneText, methodText, emailText;
	@FXML
	private Text totalSumText, discountText, cardText;
	@FXML
	private ImageView backBtn;
	@FXML	
	private Button finishBtn;
	private int lastOrder;

	@FXML
	protected void initialize() {
		listView.setCellFactory(listView -> new OrderViewCell());
		listView.setItems(prodList);
		nameText.setText(myUser.getName());
		phoneText.setText("Phone: " + myUser.getPhone());
		addressText.setText("Address: " + myUser.getAddress());
		emailText.setText("Email: " + myUser.getEmail());
		cardText.setText("Credit Card: " + getCreditCard());
	}

	@Override
	public void setUp(Object... objects) {
		super.setUp(objects);
		prodList.addAll(order.getItems());
		discountText.setVisible(order.hasDiscount());
		discountText.setText("Discount: " + decimalToInt.format(order.getDiscount() * 100));
		totalSumText.setText("Total price: " + decimal.format(order.getAfterDiscount()));
		switch (Config.getConfig()) {
		case "EK":
			methodText.setVisible(false);
			break;
		case "OL":
			methodText.setText("Method: " + order.getMethod());
		}

	}

	//pickup
	//delivery
	//Local
	private String insertOrderQuery() {
		String values = String.format("%d,%d,%.2f,'%s','%s','%s'", myUser.getId(), order.getStore_ID(),
				order.getAfterDiscount(), java.sql.Date.valueOf(LocalDate.now()),
				java.sql.Time.valueOf(LocalTime.now()), order.getMethod());
		if (order.getMethod() == "Local") {
			return "INSERT INTO orders (cid,sid,total_price,ord_date,ord_time,method,ord_status) VALUES (" + values
					+ ",'Completed'" + ")";
		} else {
			return "INSERT INTO orders (cid,sid,total_price,ord_date,ord_time,method) VALUES (" + values + ")";
		}
	}

	private String insertItemsQuery() {
		msg = new Msg(Tasks.Select, "SELECT oid FROM orders ORDER BY oid DESC LIMIT 1");
		sendMsg(msg);
		lastOrder = msg.getObj(0);
		StringBuilder query = new StringBuilder("INSERT INTO order_items VALUES ");
		for (OrderProduct p : order.getItems()) {
			query.append("(" + lastOrder + ",");
			query.append(p.getProductID() + ",");
			query.append(p.getCartQuant() + ",");
			query.append(decimal.format(order.getProductPrice(p) * p.getCartQuant()) + "),");
		}
		query.deleteCharAt(query.length() - 1);
		return query.toString();
	}

	private String createRandomCode() {
		String rndnumber = "";
		Random rnd = new Random();
		for (int i = 0; i < 6; i++)
			rndnumber = rndnumber + rnd.nextInt(9);
		return rndnumber;
	}

	@FXML
	public void sendOrder(ActionEvent event) {
		msg = new Msg(Tasks.Insert, insertOrderQuery());
		sendMsg(msg);
		msg = new Msg(Tasks.Insert, insertItemsQuery());
		sendMsg(msg);
		if (order.getMethod().equals("Delivery")) {
			msg = new Msg(Tasks.Insert, "INSERT INTO deliveries (oid,shipping_address) VALUES (" + lastOrder + ",'"
					+ order.getAddress() + "')");
			sendMsg(msg);
		}
		if (order.getMethod().equals("Pickup")) {
			msg = new Msg(Tasks.Insert, "INSERT INTO pickup (oid,sid,orderCode) VALUES (" + lastOrder + ","
					+ order.getStore_ID() + "," + createRandomCode() + ")");
			sendMsg(msg);
		}
	}

	private String getCreditCard() {
		msg = new Msg(Tasks.Select, "SELECT credit_card FROM customer WHERE id = " + myUser.getId());
		sendMsg(msg);
		return msg.getObj(0);
	}

	private void cleanOrder() {
		order.setItems(null);
		order.setDiscount(1.0);//default
		order.setTotal_price(0);
	}

	@Override
	@FXML
	public void back(MouseEvent event) {
		cleanOrder();
		try {
			start("OrderScreen", "Order");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
