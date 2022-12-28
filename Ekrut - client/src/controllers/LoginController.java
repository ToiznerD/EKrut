package controllers;

import java.io.IOException;

import Util.Msg;
import Util.Tasks;
import Util.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/*
 * Login controller class inheritting from AbstractController
 * This class is responsible to control the Login page
 */
public class LoginController extends AbstractController {

	@FXML
	private Button btnLogin;

	@FXML
	private TextField txtPW;

	@FXML
	private TextField txtUserid;

	@FXML
	private Label errMsgLbl;


	public static boolean result;

	public static String role;

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
		String query = "SELECT * FROM users WHERE user = '" + userid + "' AND password = " + password;
		msg = new Msg(Tasks.Login, Tasks.Select, query);
		sendMsg(msg);
		myUser = msg.getBool() ? msg.getArr(User.class).get(0) : null;
		
		if(myUser != null) {
			if (!myUser.isLogged()) {
				String role = myUser.getRole();
				
				//Update isLogged
				String loginQuery = "UPDATE users SET IsLogged = 1 WHERE id = " + myUser.getId();
				msg = new Msg(Tasks.Login, Tasks.Update, loginQuery);
				sendMsg(msg);
				
				switch (role) {
				case "customer":
					start("CustomerPanel", "Customer Dashboard");
					break;
				case "service":
					start("CustomerService", "Customer Service Dashboard");
				default:
					break;
				}
			} else {
				errMsgLbl.setText(userid + " is already logged in");
			}
		}
		else {
			errMsgLbl.setText("Wrong Details");
		}
	}

	@Override
	public void back(MouseEvent event) {
		//Not implemented
	}
	
}

