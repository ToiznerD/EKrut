package controllers;

import java.io.IOException;
import java.text.DecimalFormat;

import Entities.OrderProduct;
import Util.Msg;
import Util.Tasks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
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
    private ProgressBar pb;
	@FXML
	private Text addressText, nameText, phoneText, methodText, emailText;
	@FXML
	private Text totalSumText, discountText, cardText;
	@FXML
	private ImageView backBtn;
	@FXML
	private Button finishBtn;
	@FXML
	private CheckBox cboxDelayedPayment;

	@FXML
	protected void initialize() {
		String string = "";
		listView.setCellFactory(listView -> new OrderViewCell());
		listView.setItems(prodList);
		if(order.getDiscount() < 1.0 || order.getFirstOrder()) {
			string = "Discount: ";
			if(order.getDiscount() < 1.0)
				string += decimalToInt.format(order.getDiscount() * 100) + "%";
			if(order.getFirstOrder()) {
				if(order.getDiscount() < 1.0)
					string += " + ";
				string += "20%";
			}
		}
		discountText.setText("" + string);
		totalSumText.setText("Total price: " + decimal.format(order.getAfterDiscount()));
		//discountText.setVisible(order.hasDiscount());
		nameText.setText(myUser.getName());
		phoneText.setText("Phone: " + myUser.getPhone());
		emailText.setText("Email: " + myUser.getEmail());
		cardText.setText("Credit Card: " + getCreditCard());
		
		//checking if the customer is a subscriber
		msg = new Msg(Tasks.Select, "SELECT subscriber FROM customer WHERE id = " + myUser.getId());
		sendMsg(msg);
		if((int)msg.getObj(0) == 1)
			cboxDelayedPayment.setVisible(true);
		
		if (order.getMethod() == "Delivery" || order.getMethod() == "Pickup")
			methodText.setText("Method: " + order.getMethod());
		else
			methodText.setVisible(false);
		if (order.getMethod() == "Delivery")
			addressText.setText("Address: " + order.getAddress());
		else
			addressText.setVisible(false);
	}

	@Override
	public void setUp(Object... objects) {
		super.setUp(objects);
		prodList.addAll(order.getItems());
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
		if(cboxDelayedPayment.isSelected())
			order.setDelayed_paymentTrue();
		msg = new Msg(Tasks.Order, order);
		sendMsg(msg);
		endDialog(msg.getBool(), msg.getResponse());
		updateFirstOrder();
		cleanOrder();
		try {
			start("CustomerPanel", "Customer Dashboard");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateFirstOrder() {
		if(!order.getFirstOrder())
			return;
		msg = new Msg(Tasks.Update, "UPDATE customer SET first_order = 0 WHERE id = " + myUser.getId());
		sendMsg(msg);
	}

	private String getCreditCard() {
		msg = new Msg(Tasks.Select, "SELECT credit_card FROM customer WHERE id = " + myUser.getId());
		sendMsg(msg);
		return msg.getObj(0);
	}

	private void cleanOrder() {
		order = null;
	}

    /**
     * Handles the mouse event of the back button.
     * @param event the mouse event that triggered this method
     * @throws IOException if there is an issue loading the FXML file
     */
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
