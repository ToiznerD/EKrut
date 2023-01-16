package controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import Entities.OrderProduct;
import Util.Msg;
import Util.Tasks;
import client.Config;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
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
		discountText.setText("Discount: " + decimalToInt.format(order.getDiscount() * 100)+"%");
		totalSumText.setText("Total price: " + decimal.format(order.getAfterDiscount()));
		switch (Config.getConfig()) {
		case "EK":
			methodText.setVisible(false);
			break;
		case "OL":
			methodText.setText("Method: " + order.getMethod());
		}
	}

	private void endDialog(boolean result, String code) {

		Alert alert = new Alert(AlertType.INFORMATION);

		if (result) {
			alert.setHeaderText("Order Created Sucsessfully");
			alert.setContentText("Enjoy your day");
		} else {
			alert.setHeaderText("Order error ");
			alert.setContentText("Order is not created because stock is changed, please try again");
		}

		if (code != null)
			alert.setContentText(alert.getContentText() + "\nYour code for pickup: " + code);
		alert.show();

	}

	@FXML
	public void sendOrder(ActionEvent event) {

		msg = new Msg(Tasks.Order, order);
		sendMsg(msg);
		endDialog(msg.getBool(), msg.getResponse());

		try {
			start("CustomerPanel", "Customer Dashboard");
		} catch (Exception e) {
			e.printStackTrace();
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
