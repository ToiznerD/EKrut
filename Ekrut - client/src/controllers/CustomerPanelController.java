package controllers;

import Util.Msg;
import Util.Tasks;
import client.Config;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CustomerPanelController extends AbstractController {
	private int store_id;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnMakeOrder;

	@FXML
	private Button btnPickup;

	@FXML
	private Label welcomeLbl;

	@FXML
	private Label errLbl;

	@FXML
	public void initialize() {
		// Editing welcome label
		String welcome = welcomeLbl.getText() + " ";
		welcome = myUser.getName() == null ? welcome + myUser.getUsername() : welcome + myUser.getName();
		welcomeLbl.setText(welcome);

		// Get the customer status and check if he is subscriber
		String customerQuery = "SELECT status, subscriber FROM customer WHERE id = " + myUser.getId();
		msg = new Msg(Tasks.Select, customerQuery);
		sendMsg(msg);

		if (myUser.getRole().equals("new_user") || !msg.getBool()) {
			// If a new user / not a customer
			errLbl.setText("You need to register as a customer to continue.");
		}

		else {
			// Customer
			if (msg.getObj(0).equals("Not Approved")) {
				// Customer not approved
				errLbl.setText("Your account has not been approved yet.");
			}

			else {
				// Customer approved
				if (Config.getConfig().equals("OL")) {
					// OL configuration
					if ((int) msg.getObj(1) == 0) {
						// Regular customer
						errLbl.setText("You need to be a subscriber to login here.");
					} else {
						// Subscriber
						btnMakeOrder.setVisible(true);
					}
				} else {
					// EK configuration
					btnMakeOrder.setVisible(true);
					// Subscriber customer
					btnPickup.setVisible((boolean) (msg.getObj(1)));
				}
			}
		}
	}

	@Override
	public void back(MouseEvent event) {
		// not implemented
	}

	public void MakeOrder() {
		if (Config.getConfig().equals("OL")) {
			try {
				start("OrderMethodForm", "Order Method Form");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {

				// I need to get the store id /////////////////////////// ***************
				start("OrderScreen", "Order Screen", store_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setUp(Object... objects) {
	}

}
