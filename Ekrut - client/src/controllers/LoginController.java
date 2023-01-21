package controllers;

import java.io.IOException;
import java.util.Optional;

import Entities.User;
import Util.Msg;
import Util.Tasks;
import client.Config;
import controllers.LoginControllerTest.ConnectionServiceStub;
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

	private IConnectionService connectionService;
	
	public LoginController() {
		connectionService = new ConnectionService();
	}
	
	public LoginController(IConnectionService conService) {
		connectionService = conService;
	}
	
	public class ConnectionService implements IConnectionService {
		public void setUser(User user) {
			myUser = user;
		}

		/*
		 * connect method to login to the system this method responsible to check the
		 * login details
		 * 
		 * @param event the action event that triggered the method call
		 * @throws IOException if an error occurs while communicating with the app
		 */
		public void connect(String userId, String pass) throws IOException {
			// Connect to server
			// Create query based on UI input
			// String query = "SELECT * FROM users WHERE user = '" + userid + "' AND
			// password = " + password;
			String query = String.format("SELECT * FROM users WHERE user='%s' AND password = '%s'", userid, password);
			msg = new Msg(Tasks.Login, query);
			sendMsg(msg);
			
			// set user
			if (msg.getBool())
				setUser(msg.getArr(User.class).get(0));
			else
				setUser(null);
			

			if (myUser == null) // refactor erik
				errMsgLbl.setText(msg.getResponse());
			// EK Configuration
			else if (Config.getConfig().equals("EK")) {
				start("CustomerPanel", "Customer Dashboard");
			} else {
				// OL Configuration
				String role = myUser.getRole();
				switch (role) {
				case "new_user":
				case "customer":
					start("CustomerPanel", "Customer Dashboard");
					break;
				case "service":
					start("CustomerService", "Customer Service Dashboard");
					break;
				case "delivery":
					start("DeliveryOperatorPanel", "Delivery Operator Dashboard");
					break;
				case "marketing_manager":
					start("MarketingManagerPanel", "Marketing Manager Dashboard");
					break;
				case "marketing_employee":
					start("MarketingEmployeePanel", "Marketing Department Dashboard");
					break;
				case "region_manager":
					start("RegionManagerMainScreen", "Region Manager Dashboard");
					break;
				case "ceo":
					start("RegionManagerMainScreen", "CEO Dashboard");
					break;
				case "operation_employee":
					start("OperationEmpPanel", "Operation Employee Dashboard");
					break;
				default:
					start("UserPanel", "User Dashboard");
					break;
				}
			}
		}
	
		@Override
		public void appConnector(int id) {
			String query = "SELECT * FROM users WHERE id =" + id;
			msg = new Msg(Tasks.Select, query);
			sendMsg(msg);
		}
	}

	/**
	 * Logs in to the app with the given username and password.
	 *
	 * @param event the action event that triggered the method call
	 * @throws IOException if an error occurs while communicating with the app
	 */
	public void regulerLogin(ActionEvent event) throws IOException {
		// Get the username and password from the text fields
		userid = txtUserid.getText();
		password = txtPW.getText();

		// Connect to the app
		connectionService.connect(userid, password);
	}

	
	/**
	 * Connects to the app with the given ID.
	 *
	 * @param event the action event that triggered the method call
	 * @throws IOException if an I/O error occurs while communicating with the app
	 */
	public void ConnectWithApp(ActionEvent event) throws IOException {

		// Ask for Store
		String idString = null;
		while (idString == null || idString.equals("")) {
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


		
		connectionService.appConnector(Integer.parseInt(idString));
		if (!msg.getBool()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Invalid User ID");
			alert.setContentText("The user id you entered is not valid. Please try again.");

			// show the error dialog
			alert.showAndWait();
			return;
		}

		// Save the username and password
		userid = msg.getObj(1);
		password = msg.getObj(2);

		// Connect to the app
		connectionService.connect(userid, password);
	}

	public IConnectionService getConnectionService() {
		return connectionService;
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