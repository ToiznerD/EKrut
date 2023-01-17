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
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * This class handles the payment process for an order.
 * It shows the list of items and the total cost, and customer's 
 * contact information, the method of delivery and the delivery 
 * address. It also has a button to confirm the order, which sends it to the server.
 * extending AbstractController to add the functionality of the timer in "Order process" screens.
 */
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

	/**
	 * initialize all nodes inside the controller with the first initials value.
	 */
	@FXML
	protected void initialize() {
		listView.setCellFactory(listView -> new OrderViewCell());
		listView.setItems(prodList);
		discountText.setText("Discount: " + decimalToInt.format(order.getDiscount() * 100) + "%");
		totalSumText.setText("Total price: " + decimal.format(order.getAfterDiscount()));
		discountText.setVisible(order.hasDiscount());
		nameText.setText(myUser.getName());
		phoneText.setText("Phone: " + myUser.getPhone());
		emailText.setText("Email: " + myUser.getEmail());
		cardText.setText("Credit Card: " + getCreditCard());

		if (order.getMethod() == "Delivery" || order.getMethod() == "Pickup")
			methodText.setText("Method: " + order.getMethod());
		else
			methodText.setVisible(false);
		if (order.getMethod() == "Delivery")
			addressText.setText("Address: " + order.getAddress());
		else
			addressText.setVisible(false);
	}
    /**
     * This method sets up the order and adds all the order items to the observable list.
     * 
     * @param Object objects the order object containing the items 
     */
	@Override
	public void setUp(Object... objects) {
		super.setUp(objects);
		prodList.addAll(order.getItems());
	}
    /**
     * Shows a message on the screen telling if the order was created successfully or not, and if pickup, shows the pickup code.
     * 
     * @param boolean result true if order created successfully, false otherwise
     * @param String code the pickup code for the order.
     */
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
    /**
     * Sends the order to the server and shows a message to the user if the order was created successfully or not.
     * If pickup, it also shows a code for pickup.
     *
     * @param ActionEvent event button click event that triggers this method
     */
	@FXML
	public void sendOrder(ActionEvent event) {
		msg = new Msg(Tasks.Order, order);
		sendMsg(msg);
		endDialog(msg.getBool(), msg.getResponse());
		cleanOrder();
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
