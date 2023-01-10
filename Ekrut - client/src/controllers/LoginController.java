package controllers;

import java.io.IOException;
import java.util.Optional;

import Entities.User;
import Util.Msg;
import Util.Tasks;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
				switch (role) {
					case "new_user":
						login();
						start("UserPanel", "User Dashboard");
						break;
						
					case "customer":
						//Get the customer status
						String customerQuery = "SELECT status, subscriber FROM customer WHERE id = " + myUser.getId();
						msg = new Msg(Tasks.Login, Tasks.CustomerStatus, customerQuery);
						sendMsg(msg);
						
						//Check if the customer has been approved
						//Validates that the customer is a subscriber
						if(msg.getBool()) {
							if(msg.getObj(0).equals("Not Approved")) {
								login();
								start("UserPanel", "User Dashboard");
								return;
							}
							else if((int)msg.getObj(1) == 0 && config.equals("OL")) {
								errMsgLbl.setText("You need to be a subscriber to login here");
								return;
							}
							login();
							start("Customer", "Customer Dashboard");
						}
						break;
						
					case "service":
						login();
						start("CustomerService", "Customer Service Dashboard");
						break;
						
					case "marketing_manager":
						login();
						start("MarketingManagerPanel", "Marketing Manager Dashboard");
						break;
						
					case "marketing_employee":
						login();
						start("MarketingEmployeePanel", "Marketing Department Dashboard");
						break;
						
					case "region_manager":
						login();
						start("RegionManagerMainScreen", "Region Manager Dashboard");
						break;

					case "ceo":
						start("RegionManagerMainScreen", "CEO Dashboard");
						break;

					case "operation_employee":
						login();
						start("OperationEmpPanel", "Operation Employee Dashboard");
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

	private void login() {
		//Update isLogged
		String loginQuery = "UPDATE users SET IsLogged = 1 WHERE id = " + myUser.getId();
		msg = new Msg(Tasks.Login, Tasks.Update, loginQuery);
		sendMsg(msg);
	}
	
	/**
	 * Connects to the app with the given ID.
	 *
	 * @param event the action event that triggered the method call
	 * @throws IOException if an I/O error occurs while communicating with the app
	 */
	public void ConnectWithApp(ActionEvent event) throws IOException {
			/*// Prompt the user to enter their ID
			String idString = JOptionPane.showInputDialog(null, "Please enter your ID:", "Login", JOptionPane.QUESTION_MESSAGE);
			
			// Parse the ID into an integer, or set it to 0 if the user cancelled the input dialog
			int id = idString != null ? Integer.parseInt(idString) : 0;
			*/
		
		// Ask for Store ID
	      String idString = null;
	      while(idString == null || idString.equals("")) {
	    	// create the text input dialog
	          TextInputDialog dialog2 = new TextInputDialog();
	          dialog2.setTitle("Connect with app");
	          dialog2.setHeaderText("Enter a user id");
	          dialog2.setContentText("user id:");

	          // show the dialog and get the user's response
	          Optional<String> result2 = dialog2.showAndWait();

	          // save the user's input
	          idString = "";
	          if (result2.isPresent()) {
	        	  idString = result2.get();
	          }
	      }
	      
	      String query = "SELECT * FROM users WHERE id =" + Integer.parseInt(idString);
	      msg = new Msg(Tasks.Select, query);
	      sendMsg(msg);
	      if(!msg.getBool()) {
		    	  Alert alert = new Alert(Alert.AlertType.ERROR);
		          alert.setTitle("Error Dialog");
		          alert.setHeaderText("Invalid User ID");
		          alert.setContentText("The user id you entered is not valid. Please try again.");

		          // show the error dialog
		          alert.showAndWait();
		          return;
	      }
			// Get the username and password for the given ID
//		    query = String.format("SELECT * FROM users WHERE id = %d", id);
//			msg = new Msg(Tasks.Login, Tasks.Select, query);
//			sendMsg(msg);
			
//			// Check if the given ID is valid
//			if(!msg.getBool()) {
//				JOptionPane.showMessageDialog(null, "Invalid ID", "Error", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
			
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

	@Override
	public void setUp(Object... objects) {
		// TODO Auto-generated method stub
		
	}
}