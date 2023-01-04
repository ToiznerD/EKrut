package controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import Entities.User;
import Util.Msg;
import Util.Tasks;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/*
 * Login controller class inheriting from AbstractController
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
	
	private String userid;
	
	private String password;
	
	/**
	 * Logs in to the app with the given username and password.
	 *
	 * @param event the action event that triggered the method call
	 * @throws IOException if an I/O error occurs while communicating with the app
	 */
	public void regulerLogin(ActionEvent event) throws IOException {
			// Get the username and password from the text fields
			userid = txtUserid.getText();
			password = txtPW.getText();
			
			// Connect to the app
			connect(event);
		}

	/*
	 * connect method to login to the system
	 * this method responsible to check the login details
	 * @param event
	 */
	public void connect(ActionEvent event) throws IOException {
		//Connect to server
		//Create query based on UI input
		//String query = "SELECT * FROM users WHERE user = '" + userid + "' AND password = " + password;
		String query = String.format("SELECT * FROM users WHERE user='%s' AND password = '%s'", userid, password);

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
					case "new_user":
						start("UserPanel", "User Dashboard");
						break;
						
					case "customer":
						//Get the customer status
						String customerQuery = "SELECT status FROM customer WHERE id = " + myUser.getId();
						msg = new Msg(Tasks.Login, Tasks.CustomerStatus, customerQuery);
						sendMsg(msg);
						
						//Check if the customer has been approved
						if(msg.getBool()) {
							if(msg.getObj(0).equals("Not Approved"))
								start("UserPanel", "User Dashboard");
							else
								start("CustomerPanel", "Customer Dashboard");
						}
						break;
						
					case "service":
						start("CustomerService", "Customer Service Dashboard");
						break;
						
					case "marketing_manager":
						start("MarketingManagerPanel", "Marketing Manager Dashboard");
						break;
						
					case "marketing_department":
						start("MarketingSalesDepartmentPanel", "Marketing Department Dashboard");
						break;
						
					case "region_manager":
						start("RegionManagerMainScreen", "Region Manager Dashboard");
						break;
						
					default:
						break;
				}
			} else 
				errMsgLbl.setText(userid + " is already logged in");
		}
		else 
			errMsgLbl.setText("Wrong Details");
	}
	
	/**
	 * Connects to the app with the given ID.
	 *
	 * @param event the action event that triggered the method call
	 * @throws IOException if an I/O error occurs while communicating with the app
	 */
	public void ConnectWithApp(ActionEvent event) throws IOException {
			// Prompt the user to enter their ID
			String idString = JOptionPane.showInputDialog(null, "Please enter your ID:", "Login", JOptionPane.QUESTION_MESSAGE);
			
			// Parse the ID into an integer, or set it to 0 if the user cancelled the input dialog
			int id = idString != null ? Integer.parseInt(idString) : 0;
			
			// Get the username and password for the given ID
			String query = String.format("SELECT * FROM users WHERE id = %d", id);
			msg = new Msg(Tasks.Login, Tasks.Select, query);
			sendMsg(msg);
			
			// Check if the given ID is valid
			if(!msg.getBool()) {
				JOptionPane.showMessageDialog(null, "Invalid ID", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// Save the username and password
			userid = msg.getObj(1);
			password = msg.getObj(2);
			
			// Connect to the app
			connect(event);
		}


	@Override
	public void back(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
}