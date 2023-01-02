package controllers;

import java.io.IOException;

import Util.Msg;
import Util.Tasks;
import Entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
 * Login controller class inheritting from AbstractController
 * This class is responsible to control the Login page
 */
public class LoginController extends AbstractController {
	public static User user = null;

	@FXML
	private Button btnLogin;

	@FXML
	private TextField txtPW;

	@FXML
	private TextField txtUserid;

	@FXML
	private Label errMsgLbl;


	/*
	 * connect method to login to the system
	 * this method responsible to check the login details
	 * @param event
	 */
	public void connect(ActionEvent event) throws IOException {
		//Connect to server
		//Create query based on UI input
		String userid = txtUserid.getText();
		String password = txtPW.getText();
		String query = "SELECT * FROM users WHERE name = '" + userid + "' AND password = " + password;
		/*sendQuery(Tasks.Login, query);*/
		msg = new Msg(Tasks.Login, query);
		sendMsg(msg);
		//Build task to server
		if (msg.getBool()) {
			user = msg.getArr(User.class).get(0);
			switch ( LoginController.user.getRole() ) {
			case "customer":
				start("CustomerPanel", "Customer Dashboard");
				break;
			case "region_manager":
				start("RegionManagerMainScreen", "Region Manager Dashboard");
			default:
				break;
			}
		} else {
			errMsgLbl.setText("Wrong Details");
		}

	}
}
/*	
	* This method is the logic layer for handling the login page
	* The method will update LoginController (login page UI) about the msg returned from the server
	* @param msg
	
	private void loginHandlers() {
		if (msg.getArr(Object.class) != null)) {//Nave
			LoginController.result = true;
			LoginController.role = (String) msg.get(2); //Nave
		} else {
			LoginController.result = false;
		}
	}
}*/
